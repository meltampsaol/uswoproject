package uswo.inc.uswofinal.controller;

import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uswo.inc.uswofinal.model.District;
import uswo.inc.uswofinal.model.Lokal;
import uswo.inc.uswofinal.model.Note;
import uswo.inc.uswofinal.repository.DistrictRepository;
import uswo.inc.uswofinal.repository.LokalRepository;
import uswo.inc.uswofinal.repository.NoteRepository;

@Controller
@RequestMapping("/note")
public class NoteController {
    private static final Logger logger = LoggerFactory.getLogger(NoteController.class);

    @Autowired
    private LokalRepository lokalRepository;

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private DistrictRepository districtRepository;
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
    @GetMapping("/note/delete/{id}")
    public String deleteNote(@PathVariable("id") int id, Model model) {
        noteRepository.deleteById(id);
        List<Note> notes = noteRepository.findAll();
        model.addAttribute("notes", notes);
        return "mynote";
    }
    @GetMapping("/newnote/{lcode}")
    public String newNoteForm(Model model,@PathVariable("lcode") Integer lcode) {
        // Add the District and Lokal models to the attributes
        List<District> districts = districtRepository.findAll();
        Lokal lokal = lokalRepository.findByLokalCode(lcode);
        List<Lokal> locales = lokalRepository.findAll();
        model.addAttribute("districts", districts);
        // Add other attributes and return the view
        Note note = new Note();
        note.setLcode(lcode);
        note.setDid(lokal.getDid());
        model.addAttribute("note", note);
        model.addAttribute("locales", locales);
        return "newnote";
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

            try {
                // insert a new note
                District district = districtRepository.findBydid(note.getDistrict().getDid());

                Note newNote = new Note();
                newNote.setDistrict(district);
                newNote.setLcode(note.getLcode());
                // newNote.setDid(note.getDistrict().getDid());
                newNote.setWkno(note.getWkno());
                newNote.setConcerns(note.getConcerns());
                newNote.setAction(note.getAction());
                newNote.setActionDate(note.getActionDate());
                logger.error("Failed to set district ID: " + note.getDistrict().getDid());
                noteRepository.save(newNote);
            } catch (NullPointerException e) {
                logger.error("Failed to set district ID: " + e.getMessage());
                // handle the exception gracefully, e.g. by returning an error message to the
                // user
            }

        }
        return "redirect:/mynote";
    }

}
