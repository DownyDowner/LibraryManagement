package Models;

import java.util.ArrayList;
import java.util.List;

public class Library {
	private List<Book> books;

	public Library() {
		this.books = new ArrayList<Book>();
	}

	public void addBook(Book book) {
		if (canAddBook(book)) {
			books.add(book);
			System.out.println("Book added successfully!");
		} else {
			System.out.println("Book with the same ISBN already exists.");
		}
	}

	private boolean canAddBook(Book book) {
		return !books.contains(book);
	}

	public void listBooks() {
		for (Book book : books)
			System.out.println(book.toString());
	}
}
