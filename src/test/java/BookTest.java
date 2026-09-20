import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BookTest {

    @Test
    void testNewBookIsAvailableByDefault() {
        Book book = new Book("Some Title", "Some Author", "ISBN-123");
        assertTrue(book.isAvailable(), "A newly created book should be available by default");
    }

    @Test
    void testSetAvailableChangesStatus() {
        Book book = new Book("Some Title", "Some Author", "ISBN-123");
        book.setAvailable(false);
        assertFalse(book.isAvailable());
    }

    @Test
    void testGettersReturnCorrectValues() {
        Book book = new Book("1984", "George Orwell", "ISBN-1984");
        assertEquals("1984", book.getTitle());
        assertEquals("George Orwell", book.getAuthor());
        assertEquals("ISBN-1984", book.getIsbn());
    }
}