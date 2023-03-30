package uswo.inc.uswofinal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import uswo.inc.uswofinal.model.District;
import uswo.inc.uswofinal.model.Lokal;
import uswo.inc.uswofinal.model.Withdrawal;
import uswo.inc.uswofinal.repository.DistrictRepository;
import uswo.inc.uswofinal.repository.LokalRepository;
import uswo.inc.uswofinal.repository.WithdrawalRepository;

import java.util.List;

@Controller
@RequestMapping("/wlfr")
public class WithdrawalController {
    @Autowired
    private WithdrawalRepository withdrawalRepository;

    @Autowired
    private LokalRepository lokalRepository;

    @Autowired
    private DistrictRepository districtRepository;

    @GetMapping("/withdrawal")
    public String showWithdrawalForm(Model model) {
        List<Lokal> locals = lokalRepository.findAll();
        model.addAttribute("locals", locals);

        List<District> districts = districtRepository.findAll();
        model.addAttribute("districts", districts);

        model.addAttribute("withdrawal", new Withdrawal());

        return "withdrawalform";
    }

    @PostMapping("/withdrawal/save")
    public String createWithdrawal(@ModelAttribute("withdrawal") Withdrawal withdrawal) {
        withdrawalRepository.save(withdrawal);

        return "redirect:/withdrawal/list";
    }

    @GetMapping("/withdrawal/list")
    public String showWithdrawalList(Model model) {
        List<Withdrawal> withdrawals = withdrawalRepository.findAll();
        model.addAttribute("withdrawals", withdrawals);

        return "withdrawallist";
    }
}
