import java.io.*;
import java.util.Scanner;
class Book{
    int id;
    String b_name;
    String a_name;
    float price;
    int quantity;
    // Default constructor
    Book() {}
    // Parameterized constructor
    Book(int id, String b_name, String a_name, float price, int quantity){
        this.id = id;
        this.b_name = b_name;
        this.a_name = a_name;
        this.price = price;
        this.quantity = quantity;
    }
    @Override
    public String toString(){
        return id + "," + b_name + "," + a_name + "," + price + "," + quantity;
    }
}
public class BookStore{
    static int i = 0,quantity = 0;
    static float bill = 0,sum = 0,tax = 0,total = 0;
    static Book[] bookArray = new Book[100];

    static final String FILE_NAME = "bookstore.txt";
    static Scanner sc = new Scanner(System.in);

    // Load books from the file when the program starts
    static{
        loadBooksFromFile();
    }
    public static void main(String[] args){
        menu();
    }
    // Function to load book data from a file
    public static void loadBooksFromFile(){
        File file = new File(FILE_NAME);
        if (file.exists()){
            try (BufferedReader br = new BufferedReader(new FileReader(file))){
                String line;
                while ((line = br.readLine()) != null){
                    String[] bookData = line.split(",");
                    if (bookData.length == 5){
                        int id = Integer.parseInt(bookData[0]);
                        String b_name = bookData[1];
                        String a_name = bookData[2];
                        float price = Float.parseFloat(bookData[3]);
                        int quantity = Integer.parseInt(bookData[4]);
                        bookArray[i++] = new Book(id, b_name, a_name, price, quantity);
                    }
                }
            } catch (IOException e){
                System.out.println("Error loading books from file: " + e.getMessage());
            }
        }
    }
    // Function to save book data to a file
    public static void saveBooksToFile(){
        try (FileWriter writer = new FileWriter(FILE_NAME)){
            for (int j = 0; j < i; j++){
                writer.write(bookArray[j].toString() + "\n");
            }
            System.out.println("Book data saved to file.");
        } catch (IOException e){
            System.out.println("Error saving books to file: " + e.getMessage());
        }
    }
    public static void menu(){
        heading();
        int choice;

        while(true){
            System.out.println("\t\tpress 1 for Admin");
            System.out.println("\t\tpress 2 for Customer");
            System.out.println("\t\tpress 3 for Exit");
            System.out.print("\n\t\tenter your choice (only 1 to 3): ");

            while (!sc.hasNextInt()){
                System.out.println("Invalid input.");
                System.out.print("Enter Again: ");
                sc.next();
            }
            choice = sc.nextInt();

            switch (choice){
                case 1:
                    adminMenu();
                    break;
                case 2:
                    customerMenu();
                    break;
                case 3:
                    System.out.println("\n\n\tGoodbye!\n\n");
                    saveBooksToFile();  // Save books when exiting
                    System.exit(0);
                default:
                    System.out.println("\n\n\tInvalid choice\n\n");
            }
        }
    }
    public static void adminMenu(){
        heading();
        String pass;
        int choice;

        while (true){
            System.out.println("Enter the password to login as admin:");
            pass = sc.next();

            if (pass.equals("admin123")){
                System.out.println("\t\tpress 1 for add book");
                System.out.println("\t\tpress 2 for Search Book");
                System.out.println("\t\tpress 3 for update book");
                System.out.println("\t\tpress 4 for show all books");
                System.out.println("\t\tpress 5 for add quantity");
                System.out.println("\t\tpress 6 for delete book");
                System.out.println("\t\tpress 7 for Exit");
                System.out.print("\n\t\tenter your choice (only 1 to 7): ");

                while (!sc.hasNextInt()){
                    System.out.println("Invalid input.");
                    System.out.print("Enter Again: ");
                    sc.next();
                }
                choice = sc.nextInt();

                switch (choice){
                    case 1:
                        addition();
                        break;
                    case 2:
                        search();
                        break;
                    case 3:
                        update();
                        break;
                    case 4:
                        showAll();
                        break;
                    case 5:
                        addQuantity();
                        break;
                    case 6:
                        deleteBook();
                        break;
                    case 7:
                        System.out.println("\n\n\tGoodbye!\n\n");
                        menu();
                        break;
                    default:
                        System.out.println("\n\n\tInvalid choice\n\n");
                }
            } else{
                System.out.println("\t\tYou have entered the wrong password");
            }
        }
    }
    public static void customerMenu(){
        heading();
        int choice;

        while(true){
            System.out.println("\t\tpress 1 for Search Book");
            System.out.println("\t\tpress 2 for show all books");
            System.out.println("\t\tpress 3 for buy book");
            System.out.println("\t\tpress 4 for Exit");
            System.out.print("\n\t\tenter your choice (only 1 to 4): ");

            while(!sc.hasNextInt()){
                System.out.println("Invalid input.");
                System.out.print("Enter Again: ");
                sc.next();
            }
            choice = sc.nextInt();

            switch (choice){
                case 1:
                    search();
                    break;
                case 2:
                    showAll();
                    break;
                case 3:
                    buy();
                    break;
                case 4:
                    System.out.println("\n\n\tGoodbye Customer\n\n");
                    menu();
                    break;
                default:
                    System.out.println("\n\n\tInvalid choice\n\n");
            }
        }
    }
    public static void addition(){
        System.out.print("\n\nEnter the number of books you want to add: ");
        int numBooks;

        while (!sc.hasNextInt()){
            System.out.println("Invalid input.");
            System.out.print("Enter Again: ");
            sc.next();
        }
        numBooks = sc.nextInt();

        for (int j = 0; j < numBooks; j++){
            if (i >= 100){
                System.out.println("You can only add up to 100 books. Exiting...");
                break;
            }
            System.out.println("The book: " + (j + 1));

            System.out.print("\nEnter book id: ");
            int newBookId = sc.nextInt();

            boolean idExists = false;
            for (int k = 0; k < i; k++){
                if (newBookId == bookArray[k].id){
                    idExists = true;
                    System.out.println("Error: Book ID already exists. Please enter a different ID.");
                    break;
                }
            }
            if (idExists){
                j--;
                continue;
            }
            sc.nextLine(); // consume the leftover newline character
            bookArray[i] = new Book();
            bookArray[i].id = newBookId;

            System.out.print("Enter book name: ");
            bookArray[i].b_name = sc.nextLine();

            System.out.print("Enter book author name: ");
            bookArray[i].a_name = sc.nextLine();

            System.out.print("Enter book price: ");
            while (!sc.hasNextFloat()){
                System.out.println("Invalid input.");
                System.out.print("Enter Again: ");
                sc.next();
            }
            bookArray[i].price = sc.nextFloat();

            System.out.print("Enter book quantity: ");
            while (!sc.hasNextInt()){
                System.out.println("Invalid input.");
                System.out.print("Enter Again: ");
                sc.next();
            }
            bookArray[i].quantity = sc.nextInt();

            i++;
            System.out.println("\n\n\tNew book added successfully");
        }
        saveBooksToFile();  // Save after adding books
    }
    public static void search(){
        if (i == 0){
            System.out.println("\n\nNo books found.");
        } else{
            System.out.println("\n\nSearch Options:");
            System.out.println("1. By book id");
            System.out.println("2. By book name");
            System.out.print("Enter choice: ");

            int searchOption = sc.nextInt();

            if (searchOption == 1){
                System.out.print("Enter book id: ");
                int t_id = sc.nextInt();
                boolean found = false;
                for (int a = 0; a < i; a++){
                    if (t_id == bookArray[a].id){
                        displayBook(a);
                        found = true;
                        break;
                    }
                }
                if (!found){
                    System.out.println("\n\nBook ID not found");
                }
            } else if (searchOption == 2){
                System.out.print("Enter book name: ");
                sc.nextLine(); // Consume newline
                String b_name = sc.nextLine();
                boolean found = false;
                for (int a = 0; a < i; a++){
                    if (b_name.equalsIgnoreCase(bookArray[a].b_name)){
                        displayBook(a);
                        found = true;
                    }
                }
                if (!found){
                    System.out.println("\n\nBook name not found");
                }
            } else{
                System.out.println("Invalid choice.");
            }
        }
    }
    public static void displayBook(int index){
        System.out.println("\n\nBook ID: " + bookArray[index].id);
        System.out.println("Book Name: " + bookArray[index].b_name);
        System.out.println("Author Name: " + bookArray[index].a_name);
        System.out.println("Price: " + bookArray[index].price);
        System.out.println("Quantity: " + bookArray[index].quantity);
    }
    public static void update(){
        if (i == 0){
            System.out.println("\n\nNo books found.");
            return;
        }
        System.out.print("Enter the book id you want to update: ");
        int updateId = sc.nextInt();
        boolean found = false;

        for (int j = 0; j < i; j++){
            if(bookArray[j].id == updateId){
                found = true;
                System.out.print("Enter new book name: ");
                sc.nextLine(); // Consume newline
                bookArray[j].b_name = sc.nextLine();

                System.out.print("Enter new author name: ");
                bookArray[j].a_name = sc.nextLine();

                System.out.print("Enter new price: ");
                while (!sc.hasNextFloat()){
                    System.out.println("Invalid input.");
                    System.out.print("Enter Again: ");
                    sc.next();
                }
                bookArray[j].price = sc.nextFloat();

                System.out.print("Enter new quantity: ");
                while (!sc.hasNextInt()){
                    System.out.println("Invalid input.");
                    System.out.print("Enter Again: ");
                    sc.next();
                }
                bookArray[j].quantity = sc.nextInt();

                System.out.println("\n\n\tBook updated successfully");
                break;
            }
        }
        if (!found){
            System.out.println("\n\nBook not found.");
        }
        saveBooksToFile();  // Save after updating
    }
    public static void showAll(){
        if (i == 0){
            System.out.println("\n\nNo books available.");
        } else{
            System.out.println("\n\nAll Available Books:");
            for (int j = 0; j < i; j++){
                System.out.println("Book ID: " + bookArray[j].id);
                System.out.println("Book Name: " + bookArray[j].b_name);
                System.out.println("Author Name: " + bookArray[j].a_name);
                System.out.println("Price: " + bookArray[j].price);
                System.out.println("Quantity: " + bookArray[j].quantity);
                System.out.println("---------------------------------");
            }
        }
    }
    public static void addQuantity(){
        if (i == 0){
            System.out.println("\n\nNo books found.");
            return;
        }
        System.out.print("Enter the book id to add quantity: ");
        int bookId = sc.nextInt();
        boolean found = false;

        for (int j = 0; j < i; j++){
            if (bookArray[j].id == bookId){
                found = true;
                System.out.print("Enter quantity to add: ");
                int qtyToAdd = sc.nextInt();
                bookArray[j].quantity += qtyToAdd;
                System.out.println("\n\n\tQuantity updated successfully");
                break;
            }
        }
        if (!found){
            System.out.println("\n\nBook not found.");
        }
        saveBooksToFile();  // Save after updating quantity
    }
    public static void deleteBook(){
        if (i == 0){
            System.out.println("\n\nNo books available to delete.");
            return;
        }
        System.out.println("Choose an option to delete:");
        System.out.println("1. Delete a specific book");
        System.out.println("2. Delete all books");
        System.out.print("Enter your choice: ");

        int choice = sc.nextInt();

        if(choice == 1){
            System.out.print("Enter the book id to delete: ");
            int bookId = sc.nextInt();
            boolean found = false;

            for (int j = 0; j < i; j++){
                if(bookArray[j].id == bookId){
                    found = true;
                    for (int k = j; k < i - 1; k++){
                        bookArray[k] = bookArray[k + 1]; // Shift left
                    }
                    bookArray[i - 1] = null; // Nullify last book
                    i--; // Decrease book count
                    System.out.println("\n\n\tBook deleted successfully");
                    break;
                }
            }
            if (!found){
                System.out.println("\n\nBook not found.");
            }
        } else if (choice == 2){
            i = 0; // Reset book count
            System.out.println("\n\n\tAll books deleted successfully");
        } else {
            System.out.println("Invalid choice.");
        }
        saveBooksToFile();  // Save after deletion
    }
    public static void buy(){
        if (i == 0){
            System.out.println("\n\nNo books available to buy.");
            return;
        }
        System.out.print("Enter the book id to buy: ");
        int bookId = sc.nextInt();
        boolean found = false;

        for (int j = 0; j < i; j++){
            if (bookArray[j].id == bookId){
                found = true;
                System.out.print("Enter quantity to buy: ");
                quantity = sc.nextInt();
                if (quantity > bookArray[j].quantity){
                    System.out.println("Not enough stock available.");
                } else{
                    // Calculate bill details
                    float cost = bookArray[j].price * quantity;
                    bill += cost;
                    bookArray[j].quantity -= quantity;

                    // Print bill details
                    System.out.println("\n--- Bill Details ---");
                    System.out.println("Book Name: " + bookArray[j].b_name);
                    System.out.println("Author Name: " + bookArray[j].a_name);
                    System.out.println("Quantity Bought: " + quantity);
                    System.out.println("Price per Book: " + bookArray[j].price);
                    System.out.println("Total Cost: " + cost); 
                    System.out.println("---------------------");
                    System.out.println("Thank you for your purchase!");

                    // Show remaining quantity after purchase
                    System.out.println("Remaining Quantity: " + bookArray[j].quantity);
                }
                break;
            }
        }
        if (!found){
            System.out.println("\n\nBook not found.");
        }
        saveBooksToFile();  // Save after purchasing
    }
    public static void heading() {
        System.out.println("***** Welcome to Book Store *****");
    }
}