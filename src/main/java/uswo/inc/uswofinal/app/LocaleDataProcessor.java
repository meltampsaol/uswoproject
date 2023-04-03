package uswo.inc.uswofinal.app;

import java.util.List;

import org.springframework.stereotype.Component;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import uswo.inc.uswofinal.model.LocaleData;

@Component
public
class LocaleDataProcessor {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void saveLocaleData(List<LocaleData> localeDataList) {
        for (LocaleData localeData : localeDataList) {
            localeData.setDid(localeData.getThDistrict() > 0 ? 9 : 4);
            entityManager.persist(localeData);
        }
    }
}