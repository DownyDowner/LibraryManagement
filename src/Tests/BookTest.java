package Tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import Models.Book;

public class BookTest {

	@Test
	void testConstructorAndGetters() {
		Book book = new Book("  Title  ", "  Author  ", "  ISBN123  ", LocalDate.of(2023, 1, 1));
		assertEquals("Title", book.getTitle());
		assertEquals("Author", book.getAuthor());
		assertEquals("ISBN123", book.getIsbn());
		assertEquals(LocalDate.of(2023, 1, 1), book.getPublicationDate());
	}

	@Test
	void testEqualsSameIsbn() {
		Book book1 = new Book("Title1", "Author1", "ISBN123", LocalDate.of(2023, 1, 1));
		Book book2 = new Book("Title2", "Author2", "ISBN123", LocalDate.of(2023, 1, 1));
		assertEquals(book1, book2);
	}

	@Test
	void testEqualsDifferentIsbn() {
		Book book1 = new Book("Title1", "Author1", "ISBN123", LocalDate.of(2023, 1, 1));
		Book book2 = new Book("Title2", "Author2", "ISBN456", LocalDate.of(2023, 1, 1));
		assertNotEquals(book1, book2);
	}

	@Test
	void testEqualsWithNull() {
		Book book = new Book("Title", "Author", "ISBN123", LocalDate.of(2023, 1, 1));
		assertNotEquals(book, null);
	}

	@Test
	void testToString() {
		Book book = new Book("Title", "Author", "ISBN123", LocalDate.of(2023, 1, 1));
		assertEquals("ISBN123 : Title - Author (2023)", book.toString());
	}
}
