package uswo.inc.uswofinal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import uswo.inc.uswofinal.model.District;

import uswo.inc.uswofinal.model.Lokal;
import uswo.inc.uswofinal.model.Projectshare;
import uswo.inc.uswofinal.repository.DistrictRepository;

import uswo.inc.uswofinal.repository.LokalRepository;
import uswo.inc.uswofinal.repository.ProjectshareRepository;

@Controller
@RequestMapping("/share")
public class ProjectshareController {

    
    @Autowired
    private LokalRepository lokalRepository;

    @Autowired
    private DistrictRepository districtRepository;

    @Autowired
    private ProjectshareRepository projectshareRepository;

    

    @PostMapping("/projectshare/save")
    public String createExpense(@ModelAttribute("projectshare") Projectshare projectshare, Model model) {
        projectshareRepository.save(projectshare);

        // Retrieve the updated recent expenses list
        List<Projectshare> share = projectshareRepository.findAll();
        model.addAttribute("shares", share);

        return "sharelist";
    }

    

    @PostMapping("/delete/{id}")
    @ResponseBody
    public String deleteProjectshare(@PathVariable("id") int id) {
        projectshareRepository.deleteById(id);
        return "Record deleted successfully";
    }

    @GetMapping("/projectshare-add")
    public String addNoteForm(Model model) {
        // Add the District and Lokal models to the attributes
        List<District> districts = districtRepository.findAll();
        List<Lokal> locales = lokalRepository.findAll();
        model.addAttribute("districts", districts);
        model.addAttribute("locales", locales);

        // Add other attributes and return the view
        model.addAttribute("projectshare", new Projectshare());
        return "share-add";
    }
    @GetMapping("/sharelist")
    public String allShares(Model model) {
        List<Projectshare> share = projectshareRepository.findAll();
        model.addAttribute("shares", share);

        return "sharelist";
    }

    

    
}
