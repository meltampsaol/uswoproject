package uswo.inc.uswofinal.controller;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import uswo.inc.uswofinal.model.Lokal;
import uswo.inc.uswofinal.repository.LokalRepository;

@RestController
@RequestMapping("/api")
public class RestfulController {

    private static final Logger logger = LoggerFactory.getLogger(RestfulController.class);

    @Autowired
    private LokalRepository lokalRepository;

    @GetMapping(value = "/getlocales2/{districtId}", produces = "application/json")
    public List<Lokal> getLocalesByDistrict(@PathVariable Integer districtId) {
        List<Lokal> locales = lokalRepository.findByDistrictDid(districtId);
        return locales;
        
    }

    @PutMapping("/lokal/{lokalId}")
    public String updateLokal(@PathVariable("id") Integer lcode, @RequestParam("wscount") Integer wscount) {
    logger.info("Entering myEndpoint method");
    Lokal lokal = lokalRepository.findByLokalCode(lcode);
    if (lokal != null) {
        lokal.setWscount(wscount);
        lokalRepository.save(lokal);
        //return new ResponseEntity<Lokal>(lokal, HttpStatus.OK);
         
         return "Record Updated";
    } else {
        System.out.println("Record not found");
        return "Record Not Updated";
    }
    }
 

        
    
    

}
