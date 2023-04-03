package uswo.inc.uswofinal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import uswo.inc.uswofinal.model.LocaleData;

public interface LocaleDataRepository extends JpaRepository<LocaleData, Integer> {
    // add any custom query methods if needed
}
