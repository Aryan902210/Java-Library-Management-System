import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

class UserDAOTest {

    private UserDAO userDAO;
    private final String testUserId = "TEST-USER-001";

    @BeforeEach
    void setUp() {
        userDAO = new UserDAO(true);
    }

    @AfterEach
    void tearDown() {
        userDAO.deleteUser(testUserId);
    }

    @Test
    void testAddUser() {
        User user = new User(testUserId, "Test Name");
        boolean result = userDAO.addUser(user);
        assertTrue(result, "addUser should return true on success");
    }

    @Test
    void testGetUserByIdReturnsCorrectUser() {
        User user = new User(testUserId, "Test Name");
        userDAO.addUser(user);

        User fetched = userDAO.getUserById(testUserId);
        assertNotNull(fetched, "Fetched user should not be null");
        assertEquals(testUserId, fetched.getUserId());
        assertEquals("Test Name", fetched.getName());
    }

    @Test
    void testGetUserByIdReturnsNullForUnknownUser() {
        User fetched = userDAO.getUserById("NON-EXISTENT-USER");
        assertNull(fetched, "Fetching a non-existent user should return null");
    }
}

