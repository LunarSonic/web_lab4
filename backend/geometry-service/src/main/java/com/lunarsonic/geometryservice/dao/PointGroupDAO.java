package com.lunarsonic.geometryservice.dao;
import com.lunarsonic.geometryservice.entity.PointGroup;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class PointGroupDAO {

    @PersistenceContext(name = "geometry")
    private EntityManager entityManager;

    @Transactional
    public void saveGroup(PointGroup pointGroup) {
        entityManager.persist(pointGroup);
    }

    @Transactional
    public List<PointGroup> getAllGroupsByUserId(Long userId) {
        return entityManager.createQuery("select p from PointGroup p where p.userId = :userId", PointGroup.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    @Transactional
    public PointGroup getGroupByGroupIdAndUserId(Long groupId, Long userId) {
        return entityManager.createQuery("select p from PointGroup p where p.userId = :userId and p.id = :groupId", PointGroup.class)
                .setParameter("groupId", groupId)
                .setParameter("userId", userId)
                .getResultStream()
                .findFirst()
                .orElse(null);
    }

    @Transactional
    public PointGroup findActiveGroupByUserId(Long userId) {
        return entityManager.createQuery("select p from PointGroup p where p.userId = :userId and p.isActive = true", PointGroup.class)
                .setParameter("userId", userId)
                .getResultStream()
                .findFirst()
                .orElse(null);
    }

    @Transactional
    public void deactivateGroups(Long userId) {
        entityManager.createQuery("update PointGroup p set p.isActive = false  where p.userId = :userId")
                .setParameter("userId", userId)
                .executeUpdate();
    }

    @Transactional
    public void activateGroup(Long groupId, Long userId) {
        entityManager.createQuery("update PointGroup p set p.isActive = true where p.id = :groupId and p.userId = :userId")
                .setParameter("groupId", groupId)
                .setParameter("userId", userId)
                .executeUpdate();
    }

    @Transactional
    public void deleteGroup(Long groupId, Long userId) {
        entityManager.createQuery("delete from PointGroup p where p.id = :groupId and p.userId = :userId")
                .setParameter("groupId", groupId)
                .setParameter("userId", userId)
                .executeUpdate();
    }

    @Transactional
    public void deletePointsByGroupId(Long groupId) {
        entityManager.createQuery("delete from Point p where p.pointGroup.id = :groupId")
                .setParameter("groupId", groupId)
                .executeUpdate();
    }
}
