package uswo.inc.uswofinal.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import uswo.inc.uswofinal.model.Note;

@Repository
public interface NoteRepository extends JpaRepository<Note, Integer> {

    List<Note> findAll();

    Optional<Note> findById(Integer id);

    void deleteById(Integer id);

    Note save(Note note);

    List<Note> findByLocale(String locale);

    List<Note> findByDistrict(String district);

    List<Note> findByWkno(String wkno);

    List<Note> findByActionDate(LocalDate actionDate);

    Note findByLcodeAndWknoAndConcerns(Integer lcode, String wkno, String concerns);
}
