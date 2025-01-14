import java.time.LocalDate;

import Models.Book;
import Models.Library;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Library library = new Library();

		Book book1 = new Book("Java Basics", "Author A", "12345", LocalDate.of(2020, 1, 1));
		Book book2 = new Book("Advanced Java", "Author B", "12345", LocalDate.of(2021, 1, 1));
		Book book3 = new Book("Spring Framework", "Author C", "67890", LocalDate.of(2022, 1, 1));

		library.addBook(book1);
		library.addBook(book2);
		library.addBook(book3);

		System.out.println();

		library.listBooks();
		library.removeBook(book1);
		library.listBooks();
	}
}
