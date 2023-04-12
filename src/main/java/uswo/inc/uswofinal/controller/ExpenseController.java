package uswo.inc.uswofinal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import uswo.inc.uswofinal.repository.DistrictRepository;
import uswo.inc.uswofinal.repository.ExpenseRepository;
import uswo.inc.uswofinal.repository.LokalRepository;

@Controller
@RequestMapping("/expenses")
public class ExpenseController {

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

    @PostMapping("/save")
    public String createExpense(@ModelAttribute("expense") Expense expense, Model model) {
        expenseRepository.save(expense);

        // Retrieve the updated recent expenses list
        //List<Expense> expenses = expenseRepository.findRecentExpenses();
        int lcode = expense.getLokal().getLcode();
        List<Expense> expenses = expenseRepository.findRecentPerLokal(lcode);
        model.addAttribute("expenses", expenses);

        return "recent_perlokal";
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

    @GetMapping("/expense-add")
    public String addNoteForm(Model model) {
        // Add the District and Lokal models to the attributes
        List<District> districts = districtRepository.findAll();
        List<Lokal> locales = lokalRepository.findAll();
        model.addAttribute("districts", districts);
        model.addAttribute("locales", locales);

        // Add other attributes and return the view
        model.addAttribute("expense", new Expense());
        return "expense-add";
    }

    @GetMapping("/expense-search/{searchText}")
    public String search(@PathVariable String searchText, Model model) {
        List<Expense> expenses = expenseRepository.findBySearchText(searchText);
        model.addAttribute("expenses", expenses);
        return "expense-search-result";
    }

    @GetMapping("/expense-search")
    public String search(Model model) {
        return "expense-search";
    }

    
}
