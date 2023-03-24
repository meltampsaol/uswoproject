package uswo.inc.uswofinal.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import jakarta.servlet.http.HttpServletResponse;
import uswo.inc.uswofinal.dto.LokalDto;
import uswo.inc.uswofinal.model.CollectionPermit;
import uswo.inc.uswofinal.model.District;
import uswo.inc.uswofinal.model.FundReleaseRequest;
import uswo.inc.uswofinal.model.FundReleaseRequestForm;
import uswo.inc.uswofinal.model.FundStart;
import uswo.inc.uswofinal.model.Lokal;
import uswo.inc.uswofinal.model.Note;
import uswo.inc.uswofinal.model.UserDistrict;
import uswo.inc.uswofinal.model.UserInfo;
import uswo.inc.uswofinal.repository.CollectionPermitRepository;
import uswo.inc.uswofinal.repository.DistrictRepository;
import uswo.inc.uswofinal.repository.FundReleaseRequestRepository;
import uswo.inc.uswofinal.repository.FundStartRepository;
import uswo.inc.uswofinal.repository.LokalRepository;
import uswo.inc.uswofinal.repository.NoteRepository;
import uswo.inc.uswofinal.repository.UserDistrictRepository;
import uswo.inc.uswofinal.repository.UserInfoRepository;

@Controller
@RequestMapping("/")
public class HomeController {
    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    private SpringTemplateEngine templateEngine;

    @Autowired
    private FundStartRepository fundstartRepository;
    @Autowired
    private CollectionPermitRepository collectionpermitRepository;

    @Autowired
    private LokalRepository lokalRepository;

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private DistrictRepository districtRepository;

    @Autowired
    private UserDistrictRepository userDistrictRepository;

    @Autowired
    private FundReleaseRequestRepository fundReleaseRequestRepository;

    private static final String UPLOAD_DIR = "uploads/";

    @GetMapping("/viewrequest")
    public Object viewPdf(Model model) {
        return "viewrequest";
    }

    @GetMapping("/searchrequest")
    public Object searchRequest(Model model) {
        return "searchrequest";
    }

    @GetMapping("/searchresult/{approvalNumber}")
    public String searchResult(@PathVariable String approvalNumber, Model model) {
        model.addAttribute("approvalNumber", approvalNumber);
        return "searchresult";
    }
    @GetMapping("/searchpdf/{id}")
    public String searchPDF(@PathVariable Integer id, Model model) {
        model.addAttribute("id", id);
        return "searchpdf";
    }
    @GetMapping("/requests")
    public ResponseEntity<List<FundReleaseRequest>> searchRequests(@RequestParam String search) {
        List<FundReleaseRequest> requests = fundReleaseRequestRepository.searchRequests(search);
        return ResponseEntity.ok(requests);
    }

    @GetMapping("/requests/{approvalNumber}")
    public ResponseEntity<byte[]> getRequestByApprovalNumber(@PathVariable String approvalNumber,
            HttpServletResponse response)
            throws IOException {
        FundReleaseRequest request = fundReleaseRequestRepository.findByApprovalNumber(approvalNumber);
        if (request != null) {
            String fileName = request.getFileName();
            Path pdfPath = Paths.get(UPLOAD_DIR, fileName);
            byte[] pdfBytes = Files.readAllBytes(pdfPath);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("inline", fileName);
            headers.setContentLength(pdfBytes.length);
            return ResponseEntity.ok().headers(headers).body(pdfBytes);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/search/{id}")
    public ResponseEntity<byte[]> getRequestById(@PathVariable String id,
            HttpServletResponse response)
            throws IOException {
        Integer newid = Integer.parseInt(id);       
        FundReleaseRequest request = fundReleaseRequestRepository.findById(newid);
        if (request != null) {
            String fileName = request.getFileName();
            Path pdfPath = Paths.get(UPLOAD_DIR, fileName);
            byte[] pdfBytes = Files.readAllBytes(pdfPath);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("inline", fileName);
            headers.setContentLength(pdfBytes.length);
            return ResponseEntity.ok().headers(headers).body(pdfBytes);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/upload/")
    public String handleFileUpload(
            @RequestParam("file") MultipartFile file,
            @ModelAttribute("fundRequest") FundReleaseRequestForm fundRequestForm) {

        try {
            // Ensure the uploads directory exists
            File uploadDir = new File(UPLOAD_DIR);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            // Save the uploaded file to the local file system
            byte[] bytes = file.getBytes();
            Path path = Paths.get(UPLOAD_DIR + file.getOriginalFilename());
            Files.write(path, bytes);

            // Check if the combination of lcode, did, and approval_number already exists
            FundReleaseRequest existingFundReleaseRequest = fundReleaseRequestRepository
                    .findByLcodeAndDidAndApprovalNumber(
                            fundRequestForm.getLcode(), fundRequestForm.getDid(), fundRequestForm.getApprovalNumber());

            if (existingFundReleaseRequest != null) {
                // Update the existing record with new values
                existingFundReleaseRequest.setParticulars(fundRequestForm.getParticulars());
                existingFundReleaseRequest.setUploadDate(new Date());
                existingFundReleaseRequest.setFileName(file.getOriginalFilename());
                fundReleaseRequestRepository.save(existingFundReleaseRequest);
            } else {
                // Save the file details to the database
                FundReleaseRequest fundReleaseRequest = new FundReleaseRequest();
                fundReleaseRequest.setFileName(file.getOriginalFilename());
                fundReleaseRequest.setUploadDate(new Date());
                fundReleaseRequest.setApprovalNumber(fundRequestForm.getApprovalNumber());
                fundReleaseRequest.setParticulars(fundRequestForm.getParticulars());
                fundReleaseRequest.setLcode(fundRequestForm.getLcode());
                fundReleaseRequest.setDid(fundRequestForm.getDid());
                fundReleaseRequestRepository.save(fundReleaseRequest);
            }

            return "redirect:/viewrequest";
        } catch (IOException e) {
            e.printStackTrace();
            return "redirect:/error-page";
        }
    }

    @GetMapping("/uploadrequest")
    public String showUploadPage(Model model) {
        List<District> districts = districtRepository.findAll();
        List<Lokal> locales = lokalRepository.findAll();
        model.addAttribute("districts", districts);
        model.addAttribute("locales", locales);

        // Add other attributes and return the view
        model.addAttribute("fundRequest", new FundReleaseRequestForm());
        return "uploadrequest";
    }

    @GetMapping("/")
    public String landingPage(Model model) {
        model.addAttribute("message", "Welcome to USWO App!");
        return "landing";
    }

    @GetMapping("/login")
    public String loginEndpoint() {
        return "login";
    }

    @GetMapping("/admin")
    public String adminEndpoint() {
        return "admin";
    }

    @GetMapping("/user")
    public String userEndpoint() {
        return "user";
    }

    @GetMapping("/create-user")
    public String createUser(Model model) {
        model.addAttribute("userInfo", new UserInfo());
        return "create-user";
    }

    @PostMapping("/save-user")
    public String saveUser(UserInfo userInfo, String districts, boolean admin, RedirectAttributes redirectAttributes,
            Model model) {
        String encodedPassword = passwordEncoder.encode(userInfo.getPassword());
        userInfo.setPassword(encodedPassword);

        userInfoRepository.save(userInfo);
        if (StringUtils.hasText(districts)) {
            String[] districtArray = districts.split(",");
            Set<UserDistrict> userDistricts = new HashSet<>();
            for (String districtCode : districtArray) {
                UserDistrict userDistrict = new UserDistrict();
                userDistrict.setUserInfo(userInfo);
                userDistrict.setDistrictCode(districtCode.trim());
                userDistricts.add(userDistrict);
            }
            userDistrictRepository.saveAll(userDistricts);
        }
        redirectAttributes.addFlashAttribute("successMessage", "User saved successfully!");
        return "redirect:/create-user";

    }

    @GetMapping("/f1control")
    public String f1control(Model model, Authentication authentication) {
        List<District> districts = districtRepository.findAllDistricts();
        model.addAttribute("districts", districts);
        if (authentication != null && authentication.isAuthenticated()) {
            // user is authenticated, add content to model
            model.addAttribute("welcomeMessage", "Welcome, " + authentication.getName() + "!");
        } else {
            // user is not authenticated, add content to model
            model.addAttribute("welcomeMessage", "Welcome! Please log in to access the site.");
        }
        return "f1control";
    }

    @GetMapping("/home")
    public String home(Model model, Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            // user is authenticated, add content to model
            model.addAttribute("welcomeMessage", "Welcome, " + authentication.getName() + "!");
        } else {
            // user is not authenticated, add content to model
            model.addAttribute("welcomeMessage", "Welcome! Please log in to access the site.");
        }
        return "home";
    }

    @PostMapping("/save-ws")
    public ResponseEntity<String> saveWs(@ModelAttribute Lokal lokal, Model model) {
        try {
            // Get the existing record from the database
            Optional<Lokal> optionalLokal = lokalRepository.findByLcode(lokal.getLcode());

            if (!optionalLokal.isPresent()) {
                System.out.println("Record not found");
            }
            Lokal existingLokal = (Lokal) optionalLokal.get();

            // Update the existing record with the new values
            existingLokal.setWscount(lokal.getWscount());

            // Save the changes to the database
            Lokal savedLokal = lokalRepository.save(existingLokal);
            System.out.println("Saved Lokal object: " + savedLokal.toString());

            // Return the updated record as a Thymeleaf fragment
            String updatedRecord = fragment("updatedRecord :: updatedRecord", "locale", savedLokal.getLocale(),
                    "district", savedLokal.getDistrict().getDistrict(), "wscount", savedLokal.getWscount());

            // Return the updated record to the JavaScript function that called this method
            return ResponseEntity.ok(updatedRecord);
        } catch (Exception e) {
            // Log the error
            e.printStackTrace();
            // Return an error response to the client
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred while saving record: " + e.getMessage());
        }
    }

    private String fragment(String template, String localeName, String localeValue, String districtName,
            String districtValue,
            String wsCountName, Integer wscount) {
        Context context = new Context();
        context.setVariable(localeName, localeValue);
        context.setVariable(districtName, districtValue);
        context.setVariable(wsCountName, wscount);
        template = "newRecord :: newRecord";
        return templateEngine.process(template, context);
    }

    @GetMapping("/getlocales/{districtId}")
    @ResponseBody
    public List<Lokal> getLocalesByDistrict(@PathVariable Integer districtId) {
        logger.info("Entering myEndpoint method");
        List<Lokal> locales = lokalRepository.findByDistrict(districtId);
        return locales;
    }

    @PutMapping("/update-lokal/{id}")
    public ResponseEntity<String> updateLokal(@PathVariable("id") Integer lcode,
            @RequestParam("wscount") Integer wscount) {
        Optional<Lokal> lokalData = lokalRepository.findById(lcode);

        if (lokalData.isPresent()) {
            Lokal lokal = lokalData.get();
            lokal.setWscount(wscount);
            lokalRepository.save(lokal);

            return ResponseEntity.ok("Lokal updated successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/getlocales3/{districtId}")
    public String lokalList(Model model, @PathVariable Integer districtId) {
        List<Lokal> locales = lokalRepository.findAll();
        model.addAttribute("locales", locales);
        return "lokal_list";
    }

    @GetMapping("/lokal-list/{districtId}")
    public String getLocalesList(@PathVariable Integer districtId, Model model) {
        logger.info("Entering myEndpoint method");
        List<Lokal> locales = lokalRepository.findByDistrict(districtId);
        model.addAttribute("locales", locales);
        return "lokal-list";
    }

    @GetMapping("/fieldgenerator/{lcode}")
    public String getLocaleWsCount(@PathVariable Integer lcode, Model model) {
        logger.info("Entering myEndpoint method");
        Lokal lokal = lokalRepository.findByLokalCode(lcode);
        int wscount = lokal.getWscount();

        model.addAttribute("lcode", lcode);
        model.addAttribute("wscount", wscount);
        logger.info("Lcode: " + lokal.getLcode());
        logger.info("Midweek: " + lokal.getWscount());
        return "fieldgenerator";
    }

    @GetMapping("/f1generator/{dcode}")
    public String generateF1Auditing(@PathVariable String dcode, Model model) {
        logger.info("Entering myEndpoint method");
        District district = districtRepository.findByDcode(dcode);
        int districtId = district.getDid();
        List<Lokal> locales = lokalRepository.findByDistrict(districtId);
        model.addAttribute("locales", locales);
        return "f1generator";
    }

    @PutMapping("/updateLokal/{lcode}")
    public ResponseEntity<Lokal> updateLokal(@PathVariable Integer lcode, @RequestBody LokalDto lokalDto) {
        // Find the Lokal entity with the given lcode
        System.out.println("lcode value: " + lcode);
        logger.info("Entering myEndpoint method:/updateLokal/" + lcode);
        Optional<Lokal> optionalLokal = lokalRepository.findById(lcode);

        if (optionalLokal.isPresent()) {
            // Update the existing Lokal entity with the new data
            Lokal lokal = lokalRepository.findByLokalCode(lcode);
            lokal.setWscount(lokalDto.getWscount());
            logger.info("Wscount Value:" + lokalDto.getWscount());

            // Save the updated Lokal entity to the database
            Lokal updatedLokal = lokalRepository.save(lokal);

            // Return the updated Lokal entity as a response to the client
            return ResponseEntity.ok(updatedLokal);
        } else {
            // Return a 404 Not Found response if the Lokal entity was not found
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/f1auditor/{lcode}")
    public String F1Auditing(@PathVariable Integer lcode, Model model) {
        logger.info("Entering f1auditor/ " + lcode + " myEndpoint method ");
        Lokal lokal = lokalRepository.findByLokalCode(lcode);
        int did = lokal.getDid();
        District district = districtRepository.findBydid(did);
        String dname = district.getDistrict();
        int wscount = lokal.getWscount();
        model.addAttribute("wscount", wscount);
        model.addAttribute("dname", dname);
        model.addAttribute("lcode", lcode);
        logger.info("Lcode value:  " + lcode + " myEndpoint method ");
        model.addAttribute("lokal", lokal.getLocale());
        model.addAttribute("district", lokal.getDid());
        logger.info("Lokal value:  as" + lokal.getLocale() + " myEndpoint method ");
        return "f1auditor";
    }

    @GetMapping("/mynote")
    public String showNotes(Model model) {
        List<Note> notes = noteRepository.findAll();
        for (Note note : notes) {
            District district = districtRepository.findBydid(note.getDid());
            if (district != null) {
                note.setDistrict(district);
            }
            Lokal lokal = lokalRepository.findByLokalCode(note.getLcode());
            if (lokal != null) {
                note.setLokal(lokal);
            }
        }
        model.addAttribute("notes", notes);
        return "mynote";
    }

    @GetMapping("/addnote")
    public String addNoteForm(Model model) {
        // Add the District and Lokal models to the attributes
        List<District> districts = districtRepository.findAll();
        List<Lokal> locales = lokalRepository.findAll();
        model.addAttribute("districts", districts);
        model.addAttribute("locales", locales);

        // Add other attributes and return the view
        model.addAttribute("note", new Note());
        return "addnote";
    }

    @PostMapping("/update-note/")
    public String updateNote(Note updatedNote) {
        Optional<Note> noteOptional = noteRepository.findById(updatedNote.getId());

        if (noteOptional.isPresent()) {
            Note note = noteOptional.get();

            // Update the note with the new values
            note.setWkno(updatedNote.getWkno());
            note.setConcerns(updatedNote.getConcerns());
            note.setAction(updatedNote.getAction());
            note.setActionDate(updatedNote.getActionDate());
            note.setLcode(updatedNote.getLcode());

            noteRepository.save(note);
        }

        return "redirect:/mynote";
    }

    @GetMapping("/updatenote/{id}")
    public String showUpdateNoteForm(@PathVariable("id") Integer id, Model model) {
        Optional<Note> noteOptional = noteRepository.findById(id);

        if (noteOptional.isPresent()) {
            Note note = noteOptional.get();
            District district = note.getDistrict();
            Lokal lokal = note.getLokal();
            model.addAttribute("districtName", district.getDistrict());
            model.addAttribute("locale", lokal.getLocale());
            model.addAttribute("note", note);
            return "updatenote";
        } else {
            return "error-page";
        }
    }

    @PostMapping("/addnote/")
    public String addNote(@ModelAttribute("note") Note note) {
        Note existingNote = noteRepository.findByLcodeAndWknoAndConcerns(note.getLcode(), note.getWkno(),
                note.getConcerns());
        if (existingNote != null) {
            // update the existing note with new values
            existingNote.setAction(note.getAction());
            existingNote.setActionDate(note.getActionDate());
            // update any other fields as necessary
            noteRepository.save(existingNote);
        } else {
            // insert a new note
            noteRepository.save(note);
        }
        return "redirect:/mynote";
    }

    @GetMapping("/lokal-list-fundstart/{districtId}")
    public String getLocalesFundStart(@PathVariable Integer districtId, Model model) {
        logger.info("Entering myEndpoint method");

        // Load the Lokal records for the district
        List<Lokal> locales = lokalRepository.findByDistrict(districtId);
        model.addAttribute("locales", locales);

        // Create a map of FundStart objects keyed by the lcode
        Map<Integer, FundStart> fundStartMap = new HashMap<>();
        for (Lokal locale : locales) {
            FundStart fundStart = fundstartRepository.findByLcode(locale.getLcode());
            fundStartMap.put(locale.getLcode(), fundStart);
        }
        model.addAttribute("fundStartMap", fundStartMap);

        return "lokal-list-fundstart";
    }

    @GetMapping("/fundstart")
    public String fundstart(Model model, Authentication authentication) {
        List<District> districts = districtRepository.findAllDistricts();
        model.addAttribute("districts", districts);
        if (authentication != null && authentication.isAuthenticated()) {
            // user is authenticated, add content to model
            model.addAttribute("welcomeMessage", "Welcome, " + authentication.getName() + "!");
        } else {
            // user is not authenticated, add content to model
            model.addAttribute("welcomeMessage", "Welcome! Please log in to access the site.");
        }
        return "fundstart";
    }

    @PostMapping("/savefundstart/{lcode}")
    public String saveFundstart(@PathVariable Integer lcode, @RequestBody FundStart fundstart,
            Model model) {

        System.out.println("lcode value: " + lcode);
        logger.info("Entering myEndpoint method:/savefundstart/" + lcode);
        logger.info("F13 locale value" + fundstart.getF13Locale());
        logger.info("F13 District value" + fundstart.getF13District());
        // Check if a record with the same lcode and dcode already exists
        FundStart existingFundstart = fundstartRepository.findByLcodeAndDcode(lcode, fundstart.getDcode());

        if (existingFundstart != null) {
            // Update the existing record
            existingFundstart.setLingap(fundstart.getLingap());
            existingFundstart.setF13Locale(fundstart.getF13Locale());
            existingFundstart.setF13District(fundstart.getF13District());
            existingFundstart.setF9(fundstart.getF9());
            existingFundstart.setWkno(fundstart.getWkno());
            existingFundstart.setUsmo(fundstart.getUsmo());
            existingFundstart.setCfo(fundstart.getCfo());
            existingFundstart.setBank(fundstart.getBank());

            // set other fields here

            // Save the updated Fundstart object to the database
            fundstartRepository.save(existingFundstart);

        } else {
            // Set the lcode value of the fundstart object
            fundstart.setLcode(lcode);

            // Save the Fundstart object to the database
            fundstartRepository.save(fundstart);
        }

        // Add a success message to the model to display on the page
        FundStart fstart = fundstartRepository.findByLcode(lcode);
        model.addAttribute("fstart", fstart);

        // Redirect the user back to the original page
        return "redirect:/";

    }

    @GetMapping("/fundstartlist")
    public String fundstarlist(Model model, Authentication authentication) {

        if (authentication != null && authentication.isAuthenticated()) {
            // user is authenticated, add content to model
            model.addAttribute("welcomeMessage", "Welcome, " + authentication.getName() + "!");
        } else {
            // user is not authenticated, add content to model
            model.addAttribute("welcomeMessage", "Welcome! Please log in to access the site.");
        }
        return "fundstartlist";
    }

    @GetMapping("/collectionpermit")
    public String collpermit(Model model, Authentication authentication) {
        List<District> districts = districtRepository.findAllDistricts();
        model.addAttribute("districts", districts);
        if (authentication != null && authentication.isAuthenticated()) {
            // user is authenticated, add content to model
            model.addAttribute("welcomeMessage", "Welcome, " + authentication.getName() + "!");
        } else {
            // user is not authenticated, add content to model
            model.addAttribute("welcomeMessage", "Welcome! Please log in to access the site.");
        }
        return "permitcontrol";
    }

    @GetMapping("/lokal-list-permit/{districtId}")
    public String getLocalesPermit(@PathVariable Integer districtId, Model model) {
        logger.info("Entering myEndpoint method");

        // Load the Lokal records for the district
        List<Lokal> locales = lokalRepository.findByDistrict(districtId);
        model.addAttribute("locales", locales);

        // Create a map of CollectionPermit objects keyed by the lcode
        Map<Integer, CollectionPermit> collpermitMap = new HashMap<>();
        for (Lokal locale : locales) {
            CollectionPermit collPermit = collectionpermitRepository.findByLcode(locale.getLcode());
            collpermitMap.put(locale.getLcode(), collPermit);
        }
        model.addAttribute("collpermitMap", collpermitMap);

        return "lokal-list-permit";
    }

    @PostMapping("/savepermit/{lcode}")
    public String saveCollectionPermit(@PathVariable Integer lcode, @RequestBody CollectionPermit collectionPermit,
            Model model) {
        logger.info("Entering myEndpoint method: /savepermit/" + lcode);

        // Check if a record with the same lcode already exists
        CollectionPermit existingCollectionPermit = collectionpermitRepository.findByLcode(lcode);

        if (existingCollectionPermit != null) {
            // Update the existing record
            existingCollectionPermit.setLocale(collectionPermit.getLocale());
            existingCollectionPermit.setDistrict(collectionPermit.getDistrict());
            existingCollectionPermit.setLingap(collectionPermit.getLingap());
            existingCollectionPermit.setPermitnumber_locale(collectionPermit.getPermitnumber_locale());
            existingCollectionPermit.setPermitnumber_district(collectionPermit.getPermitnumber_district());

            // Save the updated CollectionPermit object to the database
            collectionpermitRepository.save(existingCollectionPermit);
        } else {
            // Set the lcode value of the CollectionPermit object
            collectionPermit.setLcode(lcode);

            // Set the default values for the permit numbers if they are null
            if (collectionPermit.getPermitnumber_locale() == null) {
                collectionPermit.setPermitnumber_locale("");
            }
            if (collectionPermit.getPermitnumber_district() == null) {
                collectionPermit.setPermitnumber_district("");
            }

            // Save the CollectionPermit object to the database
            collectionpermitRepository.save(collectionPermit);
        }

        // Add a success message to the model to display on the page
        CollectionPermit collPermit = collectionpermitRepository.findByLcode(lcode);
        model.addAttribute("collPermit", collPermit);

        // Redirect the user back to the original page
        return "redirect:/";
    }

}
