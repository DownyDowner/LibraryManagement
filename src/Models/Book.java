package Models;

import java.time.LocalDate;

public class Book {
	private final String title;
	private final String author;
	private final String isbn;
	private final LocalDate publicationYear;

	public Book(String title, String author, String isbn, LocalDate publicationYear) {
		this.title = title;
		this.author = author;
		this.isbn = isbn;
		this.publicationYear = publicationYear;
	}

	public String getIsbn() {
		return isbn;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		Book book = (Book) obj;
		return isbn.equals(book.isbn);
	}

	@Override
	public String toString() {
		return title + " - " + author + " (" + publicationYear.getYear() + ")";
	}
}
