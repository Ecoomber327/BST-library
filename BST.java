/***************************************************************************
Author: Ethan Coomber
Middlebury College

This is the code that pairs with the file titled Library.java. The functions
here are all a part of the active library.
 **************************************************************************/
import java.util.Scanner;
import java.io.File;
import java.util.Vector;

public class BST{
  private int size;
  public Node root;

  class Node{
    Book data;
    Node parent;
    Node left, right;

    //Initializes the node with book as a parameter
    public Node(Book book){
      data = book;
      left = null;
      right = null;
    }
  }

  public BST(){
    size = 0;
    root = null;
  }

  //Uses a recursive method to print all the elements of the tree
  public void print(){
   Node parent;
   parent = root;
   inOrderPrint(parent);
  }

  //Helper function to print
  public void inOrderPrint(Node node){
    //Checks if there is something to print
    if(node == null){
      return;
    }
    //Prints the left side of the tree
    inOrderPrint(node.left);
    //Prints the root
    System.out.println(node.data);
    //Prints the right side of the tree
    inOrderPrint(node.right);
  }

  //Returns how many books are available
  public int size(){
    return size;
  }

  //Adds a book back into the library
  public void add(Book book){
    boolean placed = false;
    Node current;
    current = root;
    //If the root is empty, we made a new node
    if (root == null) {
			root = new Node(book);
			return;
		}

    //Will continue to loop until we have placed a node
    while (placed == false){
      //Checks if the node we're adding has a ddn number less than the one we're checking
      if (current.data.ddn > book.ddn) {
        //If the node to the left is null, we place it there
  			if (current.left == null){
          current.left = new Node(book);
          current.left.parent = current;
          size++;
          return;
        }
  			else{
          //Otherwise we move and check the information of the node to the left
          current = current.left;
        }
  		} else {
        //Checks if the node to the right is empty, if it is we place it
        if (current.right == null){
          current.right = new Node(book);
          current.right.parent = current;
          size++;
          return;
        }
        else{
          //Otherwise we move to check the information of the node to the right
          current = current.right;
        }
      }
    }
  }

  //Finds the node with the minimum value node of the right side of the tree
  public Node min(Node root){
    if (root.left == null)
			return root;
		else {
			return min(root.left);
		}
  }

  //Recursively calls a remove helper to remove a node
  public Book remove(double ddn){
    Node current;
    current = removeNode(root, ddn);
    size--;
    return current.data;
  }

  //Helper method to the remove method
  public Node removeNode(Node current, double ddn){

    Node parent = current;
    Node temp1 = root;
    Node temp2 = root;
    Node removedNode;
    boolean found = false;
    Node testing = root;
    Node rootCopy = new Node(root.data);

    //Checks if the node we want to remove is the root
    if (ddn == root.data.ddn){
      //Assigns the node temp1 to the minimum value of the right side of the tree
      temp1 = min(root.right);
      //Removes the minimum value
      remove(temp1.data.ddn);
      //Increases the size by 1 size remove decreases the size
      //This is because we are not removing a book at this step
      size++;
      //Assigns the information of the minimum from theright side of the tree to the root
      root.data = temp1.data;
      //Returns the data from the origional root to be stored in checked out books
      return rootCopy;
    }

    //The tree is empty
    if (root == null){
      return null;
    }

    /*
      Checks if the information of the node we
      want to remove with the
      current node we're looking at
      to find the node we want to remove
    */
    if (ddn < current.data.ddn){
      return removeNode(current.left, ddn);
    }
    else if (ddn > current.data.ddn){
      return removeNode(current.right, ddn);
    }
    else {
      // Looks at if the node has two children
      if (current.left != null && current.right != null){
        //Creates a node with information from the node we may be removing
        Node removeNodeCopy = new Node(current.data);

        //Assigns temp2 to the minimum value of the node to the right side
        //of the node we may be removing
        temp2 = min(current.right);

        //Removes the minimum node from the right
        remove(temp2.data.ddn);
        //Increases the size by 1 size remove decreases the size
        //This is because we are not removing a book at this step
        size++;

        //Assigns current to the data from the node we just removed
        current.data = temp2.data;

        //Returns the node that we removed from the copy above
        return removeNodeCopy;
      }
      // if the node we're removing only has a left child
      else if (current.left != null){
        //These conditions basically tell the parent to skip over the node we removed
        if (current.parent.right == current){
          current.parent.right = current.left;
        }
        if (current.parent.left == current){
          current.parent.left = current.left;
        }
      }
      // if the node we're removing only has a right child
      else if (current.right != null){
        //These conditions basically tell the parent to skip over the node we removed
        if (current.parent.right == current){
          current.parent.right = current.right;
        }
        if (current.parent.left == current){
          current.parent.left = current.right;
        }
      }
      // if node we're deleting does not have child or is a leaf
      else{
        Node copy = current;
        //Chops of the node we want to remove by telling the parent there is nothing there
        if (current.parent.right == current){
          current.parent.right = null;
        }
        if (current.parent.left == current){
          current.parent.left = null;
        }
        current = null;
        return copy;
      }
    }
    return current;
  }

  //Checks if the book we want to remove is in the tree
  public boolean isAvailable(double ddn){
    boolean found = false;
    Node current = root;

    //If the root is the book we're looking for we return true
    if(current.data.ddn == ddn){
      return true;
    }
    //Will continue to check until we find the node we want
    while (found == false){
      //If the node we're looking at has a ddn greater than the one we want we move left
      if (current.data.ddn > ddn) {
        //If there is nothing in the node to the left we return false
  			if (current.left == null){
          return false;
        }
  			else{
          //If the node to the left has the ddn we want we return true
          if(current.left.data.ddn == ddn){
            return true;
          }
          else{
            //Otherwise we move left and continue looking
            current = current.left;
          }
        }
  		} else {
        //If there is no node to the right and the ddn is greater than the one we're
        //looking at, we return false
        if (current.right == null){
          return false;
        }
        else{
          //If the node to the right is what we're looing for, we return true
          if(current.right.data.ddn == ddn){
            return true;
          }
          else{
            //Otherwise we move right
            current = current.right;
          }
        }
      }
    }
    return true;
  }
}

class Book{
  public double ddn; // Dewey Decimal Number
  public String author;
  public String title;

  public Book(double _ddn, String _author, String _title){
    //Initializes the elements of the book class
    ddn = _ddn;
    author = _author;
    title = _title;
  }

  //Allows us to print the elements of the node
  public String toString(){
    return("Call Number: " + ddn + " \t" + author + ", " + title);
  }
}
