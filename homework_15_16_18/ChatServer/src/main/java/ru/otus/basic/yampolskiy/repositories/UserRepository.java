package ru.otus.basic.yampolskiy.repositories;

import ru.otus.basic.yampolskiy.entities.Role;
import ru.otus.basic.yampolskiy.entities.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRepository {

    private final ConnectionsManager connectionsManager = ConnectionsManager.getConnectionsManager();

    public String getUsernameByLoginAndPassword(String login, String password) {
        String query = "SELECT username FROM users WHERE login = ? AND password = ?";
        try (Connection conn = connectionsManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, login);
            pstmt.setString(2, password);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("username");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean isLoginAlreadyExist(String login) {
        String query = "SELECT COUNT(*) FROM users WHERE login = ?";
        try (Connection conn = connectionsManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, login);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next() && rs.getInt(1) > 0) {
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean isUsernameAlreadyExist(String username) {
        String query = "SELECT COUNT(*) FROM users WHERE username = ?";
        try (Connection conn = connectionsManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, username);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next() && rs.getInt(1) > 0) {
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public User getUserByUsername(String currentUsername) {
        String query = "SELECT u.login, u.password, u.username, r.role_name " +
                "FROM users u " +
                "JOIN user_roles ur ON u.id = ur.user_id " +
                "JOIN roles r ON ur.role_id = r.id " +
                "WHERE u.username = ?";
        try (Connection conn = connectionsManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, currentUsername);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String login = rs.getString("login");
                    String password = rs.getString("password");
                    String username = rs.getString("username");
                    Role role = Role.valueOf(rs.getString("role_name"));
                    return new User(login, password, username, role);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void createUser(String login, String password, String username) {
        String insertUserQuery = "INSERT INTO users (login, password, username) VALUES (?, ?, ?)";
        String getUserIdQuery = "SELECT id FROM users WHERE login = ?";
        String insertUserRoleQuery = "INSERT INTO user_roles (user_id, role_id) VALUES (?, ?)";
        int userId = -1;
        int roleId = 2;

        try (Connection conn = connectionsManager.getConnection();
             PreparedStatement pstmt1 = conn.prepareStatement(insertUserQuery);
             PreparedStatement pstmt2 = conn.prepareStatement(getUserIdQuery);
             PreparedStatement pstmt3 = conn.prepareStatement(insertUserRoleQuery)) {

            conn.setAutoCommit(false);

            pstmt1.setString(1, login);
            pstmt1.setString(2, password);
            pstmt1.setString(3, username);
            pstmt1.executeUpdate();

            pstmt2.setString(1, login);
            try (ResultSet rs = pstmt2.executeQuery()) {
                if (rs.next()) {
                    userId = rs.getInt("id");
                }
            }

            if (userId != -1) {
                pstmt3.setInt(1, userId);
                pstmt3.setInt(2, roleId);
                pstmt3.executeUpdate();
            }

            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

