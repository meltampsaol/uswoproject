package uswo.inc.uswofinal.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import uswo.inc.uswofinal.model.Note;


public interface NoteRepository extends JpaRepository<Note, Integer> {

    List<Note> findAll();

    Optional<Note> findById(Integer id);

    void deleteById(Integer id);

    List<Note> findByLokal(String locale);

    List<Note> findByDistrict(String district);

    List<Note> findByWkno(String wkno);

    List<Note> findByActionDate(LocalDate actionDate);

    Note findByLcodeAndWknoAndConcerns(Integer lcode, String wkno, String concerns);
    @Query("SELECT f FROM Note f WHERE f.concerns LIKE %:concerns% order by f.lokal.lcode, f.wkno")
    List<Note> findConcerns(@Param("concerns") String concern);
    
}
