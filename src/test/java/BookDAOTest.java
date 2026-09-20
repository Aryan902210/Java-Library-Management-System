import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

class BookDAOTest {

    private BookDAO bookDAO;
    private final String testIsbn = "TEST-ISBN-001";

    @BeforeEach
    void setUp() {
        bookDAO = new BookDAO(true); // true = use test database
    }

    @AfterEach
    void tearDown() {
        bookDAO.deleteBook(testIsbn); // clean up after every test
    }

    @Test
    void testAddBook() {
        Book book = new Book("Test Title", "Test Author", testIsbn);
        boolean result = bookDAO.addBook(book);
        assertTrue(result, "addBook should return true on success");
    }

    @Test
    void testBookExistsReturnsTrueAfterAdding() {
        Book book = new Book("Test Title", "Test Author", testIsbn);
        bookDAO.addBook(book);

        boolean exists = bookDAO.bookExists(testIsbn);
        assertTrue(exists, "bookExists should return true for a book that was just added");
    }

    @Test
    void testBookExistsReturnsFalseForUnknownIsbn() {
        boolean exists = bookDAO.bookExists("NON-EXISTENT-ISBN");
        assertFalse(exists, "bookExists should return false for an ISBN that was never added");
    }

    @Test
    void testGetBookByIsbnReturnsCorrectBook() {
        Book book = new Book("Test Title", "Test Author", testIsbn);
        bookDAO.addBook(book);

        Book fetched = bookDAO.getBookByIsbn(testIsbn);
        assertNotNull(fetched, "Fetched book should not be null");
        assertEquals("Test Title", fetched.getTitle());
        assertEquals("Test Author", fetched.getAuthor());
        assertEquals(testIsbn, fetched.getIsbn());
        assertTrue(fetched.isAvailable(), "New book should be available by default");
    }

    @Test
    void testGetBookByIsbnReturnsNullForUnknownIsbn() {
        Book fetched = bookDAO.getBookByIsbn("NON-EXISTENT-ISBN");
        assertNull(fetched, "Fetching a non-existent ISBN should return null");
    }

    @Test
    void testUpdateAvailability() {
        Book book = new Book("Test Title", "Test Author", testIsbn);
        bookDAO.addBook(book);

        boolean updated = bookDAO.updateAvailability(testIsbn, false);
        assertTrue(updated, "updateAvailability should return true when the row exists");

        Book fetched = bookDAO.getBookByIsbn(testIsbn);
        assertFalse(fetched.isAvailable(), "Book should now show as unavailable");
    }

    @Test
    void testGetAllBooksIncludesAddedBook() {
        Book book = new Book("Test Title", "Test Author", testIsbn);
        bookDAO.addBook(book);

        List<Book> allBooks = bookDAO.getAllBooks();
        boolean found = allBooks.stream().anyMatch(b -> b.getIsbn().equals(testIsbn));
        assertTrue(found, "getAllBooks should include the book we just added");
    }
}