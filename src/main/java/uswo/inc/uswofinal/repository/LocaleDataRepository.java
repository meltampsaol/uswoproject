package uswo.inc.uswofinal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import uswo.inc.uswofinal.model.LocaleData;

public interface LocaleDataRepository extends JpaRepository<LocaleData, Integer> {
    List<LocaleData> findByLocale(String searchText);
}
