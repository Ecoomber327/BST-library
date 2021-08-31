/**********************************************************
Author: Ethan Coomber
Middlebury College

Compilation: javac Library.java BST.java
Execution: java Library library.txt

Uses a binary search tree to keep track of a library. Books are
"checked in and out". The tree then deletes the node containing the book
or re-installs it.
***********************************************************/
import java.util.Scanner;
import java.io.File;
import java.util.Vector;


class Library{

  static public int select(){
    System.out.println("\nWelcome to the CSCI 201 Library.");
    System.out.println("1: Check Availability");
    System.out.println("2: Check out a book");
    System.out.println("3: Return a book");
    System.out.println("4: Print your checked out books");
    System.out.println("5: Print catalog");
    System.out.println("6: Number of items in collection");
    System.out.println("7: Quit");
    System.out.print("Choose and option: ");

    Scanner sc = new Scanner(System.in);
    String line = sc.next();
    int option;
    try{
      option = Integer.parseInt(line);
      if(option > 7 || option < 1){
        throw new ArithmeticException("Selection is not in range.");
      }
    }
    catch(Exception e){
        System.out.println("Invalid Selection");
        return select();
    }

    return option;
  }

  public static void main(String[] args){

    File file = new File(args[0]);
    Scanner sc;

    try{
      sc = new Scanner(file);
    }
    catch(Exception e){
      return;
    }

    String line;
    String[] info;

    BST library = new BST();

    while(sc.hasNext()){
      line = sc.nextLine();
      info = line.split(",");
      Book bk = new Book(Double.parseDouble(info[0]),info[1],info[2]);
      library.add(bk);
    }

    Vector<Book> books = new Vector<Book>();
    Book book;
    double ddn = 000;
    int num = 0;

    Scanner user = new Scanner(System.in);

    int option = -1;
    while(option != 7){
      option = select();

      switch(option){

        case 1: // Check availability
          System.out.print("Enter a call number: ");
          ddn = user.nextDouble();
          if(library.isAvailable(ddn)){
            System.out.println("This title is available.");
          }
          else{
            System.out.println("This title is not available.");
          }
          break;

        case 2: // Check out a book
          System.out.print("Enter a call number: ");
          ddn = user.nextDouble();

          if(library.isAvailable(ddn)){

            book = library.remove(ddn);
            if (book != null){
              System.out.printf("Successfully checked out \"%s.\"\n", book.title);
            }
            books.add(book);
          }
          else{
            System.out.println("This title is not available.");
          }
          break;

        case 3: // Return a book
          if (books.size() < 1){
            System.out.println("You have no books checked out.");
            break;
          }
          System.out.println("\nYou currently have the following books checked out.");
          for(int ii = 0; ii < books.size(); ii++){
            System.out.printf("%d: %s\n", ii, books.elementAt(ii));
          }
          System.out.print("Which book are you returning (0,1,2,3...): ");
          num = user.nextInt();
          book = books.elementAt(num);
          books.remove(num);
          library.add(book);
          break;

        case 4: //Print books currently checked out
          for(Book b: books){
            System.out.println(b);
          }
          break;

        case 5: // Return the number of items in the collection
          library.print();
          break;

        case 6: // Quit
          System.out.printf("There are %d books in the library.\n", library.size());
          break;
      }
    }
  }
}
