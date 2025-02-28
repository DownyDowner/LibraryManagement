package Tests;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.time.LocalDate;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Models.Book;
import Models.Library;

public class LibraryTest {
	private Library library;

	@BeforeEach
	void setUp() {
		File file = new File(Library.SAVE_FILE);
		if (file.exists()) {
			file.delete();
		}
		library = new Library();
	}

	@Test
	void testAddBook() {
		Book book = new Book("Title", "Author", "ISBN123", LocalDate.of(2023, 1, 1));
		library.addBook(book);
		assertEquals(1, library.getBooks().size());
	}

	@Test
	void testAddDuplicateBook() {
		Book book1 = new Book("Title1", "Author1", "ISBN123", LocalDate.of(2023, 1, 1));
		Book book2 = new Book("Title2", "Author2", "ISBN123", LocalDate.of(2023, 2, 1));

		library.addBook(book1);
		library.addBook(book2);

		assertEquals(1, library.getBooks().size(), "Duplicate books with the same ISBN should not be added.");
	}

	@Test
	void testListBooks() {
		Book book1 = new Book("Title1", "Author1", "ISBN123", LocalDate.of(2023, 1, 1));
		Book book2 = new Book("Title2", "Author2", "ISBN456", LocalDate.of(2023, 2, 1));

		library.addBook(book1);
		library.addBook(book2);

		assertEquals(2, library.getBooks().size(), "Library should contain 2 books.");
	}

	@Test
	void testCanDeleteBook() {
		assertFalse(library.canDeleteBook(), "Library should initially not allow deletion.");

		Book book = new Book("Title", "Author", "ISBN123", LocalDate.of(2023, 1, 1));
		library.addBook(book);

		assertTrue(library.canDeleteBook(), "Library should allow deletion when it contains books.");
	}

	@Test
	void testSaveAndLoad() {
		Book book = new Book("Title", "Author", "ISBN123", LocalDate.of(2023, 1, 1));
		library.addBook(book);

		library.save();

		Library newLibrary = new Library();
		assertEquals(1, newLibrary.getBooks().size(), "Newly loaded library should contain the saved book.");
	}

	@Test
	void testGetBookByIsbn() {
		Book book = new Book("Title", "Author", "ISBN123", LocalDate.of(2023, 1, 1));
		library.addBook(book);

		Book retrievedBook = library.getBookByIsbn("ISBN123");
		assertEquals(book, retrievedBook, "The retrieved book should match the one added.");

		Book nonExistentBook = library.getBookByIsbn("NON_EXISTENT_ISBN");
		assertNull(nonExistentBook, "Retrieving a book with a non-existent ISBN should return null.");
	}

	@Test
	void testSaveSynchronization() {
		Book book1 = new Book("Title1", "Author1", "ISBN001", LocalDate.of(2021, 1, 1));
		Book book2 = new Book("Title2", "Author2", "ISBN002", LocalDate.of(2022, 2, 2));
		library.addBook(book1);
		library.addBook(book2);

		ExecutorService executor = Executors.newFixedThreadPool(3);

		for (int i = 0; i < 3; i++) {
			executor.submit(() -> {
				assertDoesNotThrow(() -> {
					library.save();
					System.out.println(Thread.currentThread().getName() + " - Save operation completed.");
				}, "Save operation should not throw any exceptions.");
			});
		}

		executor.shutdown();
		try {
			if (!executor.awaitTermination(10, TimeUnit.SECONDS)) {
				executor.shutdownNow();
				fail("Timeout waiting for save operations to complete.");
			}
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			fail("Test interrupted while waiting for save operations to complete.");
		}

		File file = new File(Library.SAVE_FILE);
		assertTrue(file.exists(), "The save file should exist after concurrent save operations.");
	}

}
