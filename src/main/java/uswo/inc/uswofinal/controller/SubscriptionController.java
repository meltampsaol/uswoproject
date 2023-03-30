package uswo.inc.uswofinal.controller;

import java.util.List;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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

import uswo.inc.uswofinal.model.District;
import uswo.inc.uswofinal.model.Expense;
import uswo.inc.uswofinal.model.Lokal;

import uswo.inc.uswofinal.model.Subscription;
import uswo.inc.uswofinal.repository.DistrictRepository;
import uswo.inc.uswofinal.repository.ExpenseRepository;
import uswo.inc.uswofinal.repository.LokalRepository;
import uswo.inc.uswofinal.repository.SubscriptionRepository;

@Controller
@RequestMapping("/pasugo")
public class SubscriptionController {

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private ExpenseRepository expenseRepository;
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
                Subscription existingPasugo = subscriptionRepository.findByLokalAndForyear(lokal, pasugo.getForyear());
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

    @GetMapping("/pasugolist")
    public String allPasugo(Model model) {
        List<Subscription> psg = subscriptionRepository.findAll();
        model.addAttribute("pasugo", psg);

        return "pasugolist";
    }
    
}
