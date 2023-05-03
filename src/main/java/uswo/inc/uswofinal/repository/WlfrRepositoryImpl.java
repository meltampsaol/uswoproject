package uswo.inc.uswofinal.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery.FetchableFluentQuery;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.StoredProcedureQuery;
import jakarta.transaction.Transactional;
import uswo.inc.uswofinal.model.District;
import uswo.inc.uswofinal.model.Lokal;
import uswo.inc.uswofinal.model.Wlfr;

@Repository
public class WlfrRepositoryImpl implements WlfrRepository {

    @Autowired
    private EntityManager entityManager;

    @Transactional
    public List<Wlfr> getWlfrData(int xlcode, int xdcode) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("wlfrAccumulatedBalance2")
                .registerStoredProcedureParameter("xlcode", Integer.class, ParameterMode.IN)
                .registerStoredProcedureParameter("xdcode", Integer.class, ParameterMode.IN)
                .setParameter("xlcode", xlcode)
                .setParameter("xdcode", xdcode);

        query.execute();
        List<Object[]> results = query.getResultList();
        List<Wlfr> wlfrList = new ArrayList<>();
        for (Object[] row : results) {
            
            Wlfr wlfr = new Wlfr();
            wlfr.setLokal((String) row[0]);
            wlfr.setDid((Integer) row[1]);
            wlfr.setWkno((String) row[2]);
            wlfr.setBankstart((Double) row[3]);
            wlfr.setInterest((Double) row[4]);
            wlfr.setRetamount((Double) row[5]);
            wlfr.setMiscin((Double) row[6]);
            wlfr.setBankcharge((Double) row[7]);
            wlfr.setMiscout((Double) row[8]);
            wlfr.setBank_balance((Double) row[9]);
            wlfr.setLfstart((Double) row[10]);
            wlfr.setLfremit((Double) row[11]);
            wlfr.setLfappf10((Double) row[12]);
            wlfr.setLfmiscout((Double) row[13]);
            wlfr.setLf_balance((Double) row[14]);
            wlfr.setCfstart((Double) row[15]);
            wlfr.setCfremit((Double) row[16]);
            wlfr.setCfappf10((Double) row[17]);
            wlfr.setCfintl((Double) row[18]);
            wlfr.setCf_balance((Double) row[19]);
            wlfr.setResumen((Double) row[20]);
            wlfrList.add(wlfr);
        }
        entityManager.createNativeQuery("DELETE FROM wlfr_temp WHERE lcode = :xlcode AND did = :xdcode")
                .setParameter("xlcode", xlcode)
                .setParameter("xdcode", xdcode)
                .executeUpdate();
        return wlfrList;
    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Integer> ids) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteAllByIdInBatch'");
    }

    @Override
    public void deleteAllInBatch() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteAllInBatch'");
    }

    @Override
    public void deleteAllInBatch(Iterable<Wlfr> entities) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteAllInBatch'");
    }

    @Override
    public <S extends Wlfr> List<S> findAll(Example<S> example) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

    @Override
    public <S extends Wlfr> List<S> findAll(Example<S> example, Sort sort) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

    @Override
    public void flush() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'flush'");
    }

    @Override
    public Wlfr getById(Integer arg0) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getById'");
    }

    @Override
    public Wlfr getOne(Integer arg0) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getOne'");
    }

    @Override
    public Wlfr getReferenceById(Integer id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getReferenceById'");
    }

    @Override
    public <S extends Wlfr> List<S> saveAllAndFlush(Iterable<S> entities) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'saveAllAndFlush'");
    }

    @Override
    public <S extends Wlfr> S saveAndFlush(S entity) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'saveAndFlush'");
    }

    @Override
    public List<Wlfr> findAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

    @Override
    public List<Wlfr> findAllById(Iterable<Integer> ids) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAllById'");
    }

    @Override
    public <S extends Wlfr> List<S> saveAll(Iterable<S> entities) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'saveAll'");
    }

    @Override
    public long count() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'count'");
    }

    @Override
    public void delete(Wlfr entity) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

    @Override
    public void deleteAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteAll'");
    }

    @Override
    public void deleteAll(Iterable<? extends Wlfr> entities) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteAll'");
    }

    @Override
    public void deleteAllById(Iterable<? extends Integer> ids) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteAllById'");
    }

    @Override
    public void deleteById(Integer id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteById'");
    }

    @Override
    public boolean existsById(Integer id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'existsById'");
    }

    @Override
    public Optional<Wlfr> findById(Integer id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }

    @Override
    public <S extends Wlfr> S save(S entity) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'save'");
    }

    @Override
    public List<Wlfr> findAll(Sort sort) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

    @Override
    public Page<Wlfr> findAll(Pageable pageable) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

    @Override
    public <S extends Wlfr> long count(Example<S> example) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'count'");
    }

    @Override
    public <S extends Wlfr> boolean exists(Example<S> example) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'exists'");
    }

    @Override
    public <S extends Wlfr> Page<S> findAll(Example<S> example, Pageable pageable) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

    @Override
    public <S extends Wlfr, R> R findBy(Example<S> example, Function<FetchableFluentQuery<S>, R> queryFunction) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findBy'");
    }

    @Override
    public <S extends Wlfr> Optional<S> findOne(Example<S> example) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findOne'");
    }

    @Override
    public List<Wlfr> wlfrAccumulatedBalance2(Integer xlcode, Integer xdcode) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'wlfrAccumulatedBalance2'");
    }
}
