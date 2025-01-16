import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.Scanner;

import Models.Book;
import Models.Library;

public class Main {

	public static void printMenu() {
		System.out.println("==========================");
		System.out.println("         LIBRARY MENU     ");
		System.out.println("==========================");
		System.out.println("1. Add a book");
		System.out.println("2. Remove a book by ISBN");
		System.out.println("3. List all books");
		System.out.println("4. Exit");
		System.out.println("==========================");
		System.out.print("Choose an option: ");
	}

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		boolean isRunning = true;
		Library library = new Library();

		while (isRunning) {
			printMenu();
			try {
				int optionChosen = scanner.nextInt();
				scanner.nextLine();

				switch (optionChosen) {
				case 1:
					System.out.print("Enter the title: ");
					String title = scanner.nextLine();
					System.out.print("Enter the author: ");
					String author = scanner.nextLine();
					System.out.print("Enter the ISBN: ");
					String isbn = scanner.nextLine();
					System.out.print("Enter the publication date (format: \"yyyy-mm-dd\"): ");
					String date = scanner.nextLine();
					library.addBook(new Book(title, author, isbn, LocalDate.parse(date)));
					break;

				case 2:
					library.listBooks();
					if (library.canDeleteBook()) {
						System.out.print("Enter the ISBN: ");
						String isbnToDelete = scanner.nextLine();
						library.removeBook(isbnToDelete);
					}
					break;

				case 3:
					library.listBooks();
					break;

				case 4:
					library.save();
					isRunning = false;
					break;

				default:
					System.out.println("Invalid option. Please choose a number between 1 and 4.");
					break;
				}
			} catch (InputMismatchException e) {
				System.out.println("Error: Please enter a valid number.");
				scanner.nextLine();
			} catch (Exception e) {
				System.out.println("An unexpected error occurred: " + e.getMessage());
			}
		}

		scanner.close();
	}
}
