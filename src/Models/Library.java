package Models;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Library {
	private Map<String, Book> books;
	private final String saveFile = "library.csv";

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

	public void save() {
		try (FileWriter fileWriter = new FileWriter(saveFile)) {
			fileWriter.append("ISBN,Title,Author,PublicationDate\n");
			for (Book book : books.values()) {
				fileWriter.append(escapeCsv(book.getIsbn())).append(",");
				fileWriter.append(escapeCsv(book.getTitle())).append(",");
				fileWriter.append(escapeCsv(book.getAuthor())).append(",");
				fileWriter.append(escapeCsv(book.getPublicationDate().toString())).append("\n");
			}
			System.out.println("Save successfully");
		} catch (IOException e) {
			System.err.println("Error while saving the library");
		}
	}

	private String escapeCsv(String value) {
		if (value.contains(",") || value.contains("\"") || value.contains("\n")) {
			value = value.replace("\"", "\"\"");
			return "\"" + value + "\"";
		}
		return value;
	}
}
