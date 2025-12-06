package com.lunarsonic.geometryservice.dao;
import com.lunarsonic.geometryservice.entity.Point;
import com.lunarsonic.geometryservice.model.PointData;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class PointDAO {

    @PersistenceContext(name = "geometry")
    private EntityManager entityManager;

    @Transactional
    public List<Point> findPointsByGroupId(Long groupId) {
        return entityManager.createQuery("select p from Point p where p.pointGroup.id = :groupId", Point.class)
                .setParameter("groupId", groupId)
                .getResultList();
    }

    @Transactional
    public void savePoint(Point point) {
        entityManager.persist(point);
    }

    @Transactional
    public Point findPointByCoordinatesAndGroup(PointData point, Long groupId) {
        return entityManager.createQuery("select p from Point p where p.x = :x and p.y = :y and p.pointGroup.id = :groupId", Point.class)
                .setParameter("x", point.getX())
                .setParameter("y", point.getY())
                .setParameter("groupId", groupId)
                .getResultStream()
                .findFirst()
                .orElse(null);
    }
}
