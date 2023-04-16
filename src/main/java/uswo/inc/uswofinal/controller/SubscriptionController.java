package uswo.inc.uswofinal.controller;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpServletRequest;
import uswo.inc.uswofinal.mapper.BalancesRowMapper;
import uswo.inc.uswofinal.model.Balances;
import uswo.inc.uswofinal.model.District;
import uswo.inc.uswofinal.model.Expense;
import uswo.inc.uswofinal.model.Lokal;
import uswo.inc.uswofinal.model.Subscription;
import uswo.inc.uswofinal.repository.BalancesRepository;
import uswo.inc.uswofinal.repository.DistrictRepository;
import uswo.inc.uswofinal.repository.ExpenseRepository;
import uswo.inc.uswofinal.repository.LokalRepository;
import uswo.inc.uswofinal.repository.SubscriptionRepository;

@Controller
@RequestMapping("/pasugo")
public class SubscriptionController {

    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private BalancesRepository balancesRepository;

    @Autowired
    private LokalRepository lokalRepository;

    @Autowired
    private DistrictRepository districtRepository;

    @GetMapping("/recent")
    public String getRecentExpenses(Model model) {

        List<Expense> expenses = expenseRepository.findRecentExpenses();
        model.addAttribute("expenses", expenses);

        return "recent";
    }

    @GetMapping("/{id}")
    public Expense getExpenseById(@PathVariable("id") String id) {
        int newid = Integer.parseInt(id);
        return expenseRepository.findById(newid);
    }

    @PostMapping("/subscription/save")
    public String createSubscription(@ModelAttribute("pasugo") Subscription pasugo, Model model) {
        try {
            subscriptionRepository.save(pasugo);
        } catch (DataIntegrityViolationException e) {
            if (e.getCause() instanceof ConstraintViolationException) {
                ConstraintViolationException cve = (ConstraintViolationException) e.getCause();
                if (cve.getConstraintName().equals("subscription_lcode_foryear_idx")) {
                    // Unique index violation occurred on lcode and foryear
                    Lokal lokal = lokalRepository.findByLokalCode(pasugo.getLokal().getLcode());
                    Subscription existingPasugo = subscriptionRepository.findByLokalAndForyear(lokal,
                            pasugo.getForyear());
                    existingPasugo.setBalance(pasugo.getBalance()); // Update the balance
                    subscriptionRepository.save(existingPasugo); // Save the updated row
                }
            }
        }
        List<Subscription> psg = subscriptionRepository.findAll();
        model.addAttribute("pasugo", psg);
        return "pasugolist";
    }

    @PutMapping("/{id}")
    public Expense updateExpense(@PathVariable("id") int id, @RequestBody Expense expenseData) {
        Expense expense = expenseRepository.findById(id);
        if (expense == null) {
            return null;
        }
        expense.setLokal(expenseData.getLokal());
        expense.setDistrict(expenseData.getDistrict());
        expense.setWkno(expenseData.getWkno());
        expense.setDescription(expenseData.getDescription());
        expense.setF10(expenseData.getF10());
        expense.setAmountRequested(expenseData.getAmountRequested());
        expense.setActualExpenses(expenseData.getActualExpenses());
        expense.setDatePurchased(expenseData.getDatePurchased());
        expense.setRemarks(expenseData.getRemarks());
        expense.setQty(expenseData.getQty());
        expense.setExptype(expenseData.getExptype());
        return expenseRepository.save(expense);
    }

    @PostMapping("/delete/{id}")
    @ResponseBody
    public String deleteExpense(@PathVariable("id") int id) {
        expenseRepository.deleteById(id);
        return "Record deleted successfully";
    }

    @GetMapping("/subscription-add")
    public String addSubscription(Model model) {
        // Add the District and Lokal models to the attributes
        List<District> districts = districtRepository.findAll();
        List<Lokal> locales = lokalRepository.findAll();
        model.addAttribute("districts", districts);
        model.addAttribute("locales", locales);

        // Add other attributes and return the view
        model.addAttribute("psg", new Subscription());
        return "pasugo-add";
    }

    @GetMapping("/encode-mass")
    public String addMassBalance(Model model) {
        // Add the District and Lokal models to the attributes
        List<District> districts = districtRepository.findAll();
        model.addAttribute("districts", districts);

        return "pasugo_encode_mass";
    }

    @GetMapping("/pasugolist")
    public String allPasugo(Model model) {
        List<Subscription> psg = subscriptionRepository.findAll();
        model.addAttribute("pasugo", psg);

        return "pasugolist";
    }

    @PostMapping("/save-mass")
    public String subscribe(HttpServletRequest request, Model model) {
        String[] lcodes = request.getParameterValues("lokal.lcode");
        String[] years = request.getParameterValues("foryear");
        String[] balances = request.getParameterValues("balance");
        logger.info("Entering save-mass method lcodes: {}", lcodes != null ? lcodes[0] : null);
        logger.info("Entering save-mass method years: {}", years != null ? years[0] : null);
        logger.info("Entering save-mass method balances: {}", balances != null ? balances[0] : null);
        if (lcodes != null && years != null && balances != null && lcodes.length == years.length
                && lcodes.length == balances.length) {
            for (int i = 0; i < lcodes.length; i++) {
                Lokal lokal = lokalRepository.findById(Integer.parseInt(lcodes[i])).orElse(null);
                if (lokal != null && years[i] != null && balances[i] != null && !years[i].isEmpty()
                        && !balances[i].isEmpty()) {
                    District district = lokal.getDistrict(); // Retrieve the district from the lokal object
                    Subscription subscription = new Subscription();
                    subscription.setLokal(lokal);
                    subscription.setDistrict(district);
                    subscription.setForyear(Integer.parseInt(years[i]));
                    subscription.setBalance(new BigDecimal(balances[i]));
                    try {
                        subscriptionRepository.save(subscription);
                    } catch (DataIntegrityViolationException e) {
                        if (e.getCause() instanceof ConstraintViolationException) {
                            ConstraintViolationException cve = (ConstraintViolationException) e.getCause();
                            logger.info("trying to save-mass", e.getMessage());
                            if (cve.getConstraintName().equals("subscription_lcode_foryear_idx")) {
                                // Unique index violation occurred on lcode and foryear
                                Subscription existingSubscription = subscriptionRepository.findByLokalAndForyear(lokal,
                                        subscription.getForyear());
                                existingSubscription.setBalance(subscription.getBalance()); // Update the balance
                                subscriptionRepository.save(existingSubscription); // Save the updated row
                                logger.info("save-mass");
                            }
                        }
                    }
                }
            }
        }

        List<Subscription> psg = subscriptionRepository.findAll();
        model.addAttribute("pasugo", psg);

        return "pasugolist";
    }

    @GetMapping("/search")
    public String search(Model model) {
        return "pasugo-search";
    }

    private final JdbcTemplate jdbcTemplate;

    public SubscriptionController(BalancesRepository balancesRepository, JdbcTemplate jdbcTemplate) {
        this.balancesRepository = balancesRepository;
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping("/search/{searchText}")
    public String search(@PathVariable String searchText, Model model) {
        String sql = "SELECT l.locale, d.district, coalesce(s.foryear, 0) AS foryear, " +
                "s.balance AS startingbalance, COALESCE(SUM(p.amount), 0) AS currentpayment, " +
                "s.balance - COALESCE(SUM(p.amount), 0) AS currentbalance " +
                "FROM subscription s " +
                "INNER JOIN lokal l ON l.lcode = s.lcode " +
                "LEFT JOIN payment p ON p.lcode = s.lcode and p.foryear=s.foryear " +
                "INNER JOIN districts d ON d.did = s.did " +
                "WHERE l.locale LIKE ? GROUP BY l.locale, s.foryear";
        List<Balances> balances = jdbcTemplate.query(sql, new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setString(1, "%" + searchText + "%");
            }
        }, new BalancesRowMapper());
        model.addAttribute("balances", balances);
        return "pasugo-search-result";
    }
    
    


    

}
