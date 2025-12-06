package com.lunarsonic.historyservice.dao;
import com.lunarsonic.historyservice.entity.History;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class HistoryDAO {

    @PersistenceContext(name = "history")
    private EntityManager entityManager;

    @Transactional
    public void saveAction(History history) {
        entityManager.persist(history);
    }

    @Transactional
    public List<History> findHistoryByUserId(Long userId) {
        return entityManager.createQuery("select h from History h where h.userId = :userId", History.class)
                .setParameter("userId", userId)
                .getResultList();
    }
}
