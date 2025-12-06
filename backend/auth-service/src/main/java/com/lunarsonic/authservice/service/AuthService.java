package com.lunarsonic.authservice.service;
import com.lunarsonic.authservice.dao.UserDAO;
import com.lunarsonic.authservice.entity.User;
import com.lunarsonic.authservice.exception.ApplicationError;
import com.lunarsonic.authservice.exception.ApplicationException;
import com.lunarsonic.authservice.utility.HashingUtil;
import com.lunarsonic.authservice.utility.JwtUtil;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class AuthService {

    @Inject
    private UserDAO userDAO;

    @Inject
    private JwtUtil jwtUtil;

    public void register(String username, String password) {
        if (userDAO.findUserByUsername(username) != null) {
            throw new ApplicationException(ApplicationError.INVALID_CREDENTIALS);
        }
        String hashedPassword = HashingUtil.hashPassword(password);
        User user = new User();
        user.setUsername(username);
        user.setPassword(hashedPassword);
        String refreshToken = jwtUtil.generateRefreshToken(user);
        user.setRefreshToken(refreshToken);
        userDAO.saveNewUser(user);
    }

    public Tokens login(String username, String password) {
        System.out.println("Попытка войти " + username);
        User user = userDAO.findUserByUsername(username);
        if (user == null) {
            throw new ApplicationException(ApplicationError.INVALID_CREDENTIALS);
        }
        boolean passwordValid = HashingUtil.verifyPassword(password, user.getPassword());
        if (!passwordValid) {
            throw new ApplicationException(ApplicationError.INVALID_CREDENTIALS);
        }
        String accessToken = jwtUtil.generateAccessToken(user);
        String refreshToken = jwtUtil.generateRefreshToken(user);
        user.setRefreshToken(refreshToken);
        userDAO.updateRefreshToken(user.getId(), refreshToken);
        return new Tokens(accessToken, refreshToken);
    }

    public String refresh(String refreshToken) {
        if (refreshToken == null) {
            throw new ApplicationException(ApplicationError.NO_TOKEN);
        }
        String username = jwtUtil.getUsernameFromToken(refreshToken, true);
        if (username == null) {
            throw new ApplicationException(ApplicationError.INVALID_TOKEN);
        }
        User user = userDAO.findUserByRefreshToken(refreshToken);
        return jwtUtil.generateAccessToken(user);
    }

    public void logout(String refreshToken) {
        if (refreshToken == null) {
            return;
        }
        User user = userDAO.findUserByRefreshToken(refreshToken);
        if (user != null) {
            userDAO.clearRefreshToken(user.getId());
        }
    }

    public record Tokens(String accessToken, String refreshToken) {}
}

