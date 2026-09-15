import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnection {

    private static final String CONFIG_FILE = "config.properties";

    // Returns a new connection each time it's called
    public static Connection getConnection() throws SQLException {
        Properties props = new Properties();
        try (FileInputStream input = new FileInputStream(CONFIG_FILE)) {
            props.load(input);
        } catch (IOException e) {
            throw new SQLException("Could not load " + CONFIG_FILE + ". Did you create it? See config.properties.example.", e);
        }

        String url = props.getProperty("db.url");
        String user = props.getProperty("db.user");
        String password = props.getProperty("db.password");

        return DriverManager.getConnection(url, user, password);
    }
}