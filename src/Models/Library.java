package Models;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;

public class Library {
	private Map<String, Book> books;
	private static final String SAVE_FILE = "library.csv";
	private static final String CSV_DELIMITER = ",";

	public Library() {
		this.books = new HashMap<>();
		load();
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

	private void load() {
		File file = new File(SAVE_FILE);
		if (file.exists()) {
			try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
				String line;
				boolean isFirstLine = true;
				while ((line = bufferedReader.readLine()) != null) {
					String[] values = line.split(CSV_DELIMITER);
					if (isFirstLine) {
						isFirstLine = false;
					} else {
						if (values.length >= 4) {
							try {
								String isbn = values[0].trim();
								String title = values[1].trim();
								String author = values[2].trim();
								LocalDate publicationDate = LocalDate.parse(values[3].trim());
								Book book = new Book(title, author, isbn, publicationDate);
								books.put(isbn, book);
							} catch (DateTimeParseException e) {
								System.err.println("Invalid date format on line: " + line);
							} catch (Exception e) {
								System.err.println("Error creating Book object for line: " + line);
							}
						} else {
							System.err.println("Insufficient data on line: " + line);
						}
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public synchronized void save() {
		try (FileWriter fileWriter = new FileWriter(SAVE_FILE)) {
			fileWriter.append("ISBN").append(CSV_DELIMITER).append("Title").append(CSV_DELIMITER).append("Author")
					.append(CSV_DELIMITER).append("PublicationDate\n");
			for (Book book : books.values()) {
				fileWriter.append(escapeCsv(book.getIsbn())).append(CSV_DELIMITER).append(escapeCsv(book.getTitle()))
						.append(CSV_DELIMITER).append(escapeCsv(book.getAuthor())).append(CSV_DELIMITER)
						.append(escapeCsv(book.getPublicationDate().toString())).append("\n");
			}
			System.out.println("Save successfully");
		} catch (IOException e) {
			System.err.println("Error while saving the library");
		}
	}

	private String escapeCsv(String value) {
		if (value.contains(CSV_DELIMITER) || value.contains("\"") || value.contains("\n")) {
			value = value.replace("\"", "\"\"");
			return "\"" + value + "\"";
		}
		return value;
	}

	public Map<String, Book> getBooks() {
		return books;
	}

	public boolean hasBook(String isbn) {
		return books.containsKey(isbn);
	}

	public Book getBookByIsbn(String isbn) {
		return books.get(isbn);
	}

}
