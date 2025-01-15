package Models;

import java.util.HashMap;
import java.util.Map;

public class Library {
	private Map<String, Book> books;

	public Library() {
		this.books = new HashMap<>();
	}

	public void addBook(Book book) {
		if (books.containsKey(book.getIsbn())) {
			System.out.println("Book with the same ISBN already exists.");
		} else {
			books.put(book.getIsbn(), book);
			System.out.println("Book added successfully!");
		}
	}

	public void removeBook(String isbn) {
		if (books.containsKey(isbn)) {
			books.remove(isbn);
			System.out.println("Book removed successfully!");
		} else {
			System.out.println("Book with this ISBN (" + isbn + ") does not exist.");
		}
	}

	public void removeBook(Book book) {
		removeBook(book.getIsbn());
	}

	public void listBooks() {
		if (books.isEmpty()) {
			System.out.println("No books available in the library.");
		} else {
			System.out.println("Books available in the library :");
			for (Book book : books.values()) {
				System.out.println("\t" + book.toString());
			}
		}
	}

	public boolean canDeleteBook() {
		return !books.isEmpty();
	}
}
