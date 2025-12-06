package com.lunarsonic.authservice.dao;
import com.lunarsonic.authservice.entity.User;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class UserDAO {

    @PersistenceContext(name = "auth")
    private EntityManager entityManager;

    @Transactional
    public User findUserByUsername(String username) {
        try {
            return entityManager.createQuery("select u from User u where u.username = :username", User.class)
                    .setParameter("username", username)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Transactional
    public User findUserByRefreshToken(String token) {
        try {
            return entityManager.createQuery("select u from User u where u.refreshToken = :token", User.class)
                    .setParameter("token", token)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Transactional
    public void saveNewUser(User user) {
        entityManager.persist(user);
    }

    @Transactional
    public void updateRefreshToken(Long userId, String newToken) {
        entityManager.createQuery("update User u set u.refreshToken = :token where u.id = :id")
                    .setParameter("token", newToken)
                    .setParameter("id", userId)
                    .executeUpdate();
        }

    @Transactional
    public void clearRefreshToken(Long userId) {
        entityManager.createQuery("update User u set u.refreshToken = null where u.id = :id")
                .setParameter("id", userId)
                .executeUpdate();
    }
}
