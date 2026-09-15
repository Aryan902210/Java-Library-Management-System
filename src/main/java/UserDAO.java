import java.sql.*;

public class UserDAO {

    public boolean addUser(User user) {
        String sql = "INSERT INTO users (user_id, name) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user.getUserId());
            stmt.setString(2, user.getName());
            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Error adding user: " + e.getMessage());
            return false;
        }
    }

    public User getUserById(String userId) {
        String sql = "SELECT * FROM users WHERE user_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new User(rs.getString("user_id"), rs.getString("name"));
            }
            return null;

        } catch (SQLException e) {
            System.out.println("Error fetching user: " + e.getMessage());
            return null;
        }
    }
}