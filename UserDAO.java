package kokje;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO 
{
    public boolean validateUser(String username, String password) throws SQLException
    {
        String query = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (Connection conn = DataConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) 
        {
            stmt.setString(1, username);
            stmt.setString(2, password);  
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        }
    }

    public User getUserByUsername(String username) throws SQLException
    {
        String query = "SELECT * FROM users WHERE username = ?";
        try (Connection conn = DataConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) 
        {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setRole(rs.getString("role"));
                user.setCity(rs.getString("city"));
                user.setProfileComplete(rs.getBoolean("profile_complete"));
                return user;
            }
        }
        return null;
    }

    public List<User> getAllUsers() throws SQLException 
    {
        String query = "SELECT * FROM users";
        List<User> users = new ArrayList<>();
        try (Connection conn = DataConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) 
        {
            while (rs.next()) 
            {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setRole(rs.getString("role"));
                user.setCity(rs.getString("city"));
                user.setProfileComplete(rs.getBoolean("profile_complete"));
                users.add(user);
            }
        }
        return users;
    }

    public void addUser(User user) throws SQLException
    {
        String query = "INSERT INTO users (username, password, role, city) VALUES (?, ?, ?, ?)";
        try (Connection conn = DataConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) 
        {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getRole());
            stmt.setString(4, user.getCity());
            stmt.executeUpdate();
        }
    }

    public void deleteUser(int id) throws SQLException
    {
        String query = "DELETE FROM users WHERE id = ?";
        try (Connection conn = DataConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) 
        {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    
    public void updateUser(User user) throws SQLException 
    {
        String query = "UPDATE users SET username = ?, password = ?, role = ?, city = ? WHERE id = ?";
        try (Connection conn = DataConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) 
        {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getRole());
            stmt.setString(4, user.getCity());
            stmt.setInt(5, user.getId());
            stmt.executeUpdate();
        }
    }
}