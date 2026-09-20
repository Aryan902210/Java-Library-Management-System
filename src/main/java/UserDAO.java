import java.sql.*;

public class UserDAO {

    private boolean useTestDb;

    public UserDAO() {
        this.useTestDb = false;
    }

    public UserDAO(boolean useTestDb) {
        this.useTestDb = useTestDb;
    }

    private Connection getConnection() throws SQLException {
        return DatabaseConnection.getConnection(useTestDb);
    }

    public boolean addUser(User user) {
        String sql = "INSERT INTO users (user_id, name) VALUES (?, ?)";
        try (Connection conn = getConnection();
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
        try (Connection conn = getConnection();
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

    // Used by tests to clean up after themselves
    public void deleteUser(String userId) {
        String sql = "DELETE FROM users WHERE user_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, userId);
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error deleting user: " + e.getMessage());
        }
    }
}