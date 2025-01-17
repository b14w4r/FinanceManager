package org.example.b14w4r;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import org.example.b14w4r.wallet.*;
// import org.example.b14w4r.transaction.*;

public class FinanceManager {

  private static final Scanner scanner = new Scanner(System.in);
  private static final Map<String, User> users = new HashMap<>();
  private static User currentUser = null;

  public static void main(String[] args) {
    loadUsers();
    while (true) {
      if (currentUser == null) {
        displayLoginMenu();
      } else {
        displayMainMenu();
      }
    }
  }

  private static void displayLoginMenu() {
    System.out.println("1. Login");
    System.out.println("2. Register");
    System.out.println("3. Exit");
    System.out.print("Enter your choice: ");

    String choice = scanner.nextLine();
    switch (choice) {
      case "1":
        login();
        break;
      case "2":
        register();
        break;
      case "3":
        saveUsers();
        System.exit(0);
      default:
        System.out.println("Invalid choice. Try again.");
    }
  }

  private static void displayMainMenu() {
    System.out.println("1. Add Income");
    System.out.println("2. Add Expense");
    System.out.println("3. Set Budget");
    System.out.println("4. View Summary");
    System.out.println("5. Logout");
    System.out.print("Enter your choice: ");

    String choice = scanner.nextLine();
    switch (choice) {
      case "1":
        addIncome();
        break;
      case "2":
        addExpense();
        break;
      case "3":
        setBudget();
        break;
      case "4":
        viewSummary();
        break;
      case "5":
        currentUser = null;
        break;
      default:
        System.out.println("Invalid choice. Try again.");
    }
  }

  private static void login() {
    System.out.print("Enter username: ");
    String username = scanner.nextLine();
    System.out.print("Enter password: ");
    String password = scanner.nextLine();

    if (users.containsKey(username) && users.get(username).getPassword().equals(password)) {
      currentUser = users.get(username);
      System.out.println("Login successful.");
    } else {
      System.out.println("Invalid username or password.");
    }
  }

  private static void register() {
    System.out.print("Enter username: ");
    String username = scanner.nextLine();
    if (users.containsKey(username)) {
      System.out.println("Username already exists. Try another.");
      return;
    }

    System.out.print("Enter password: ");
    String password = scanner.nextLine();

    users.put(username, new User(username, password));
    System.out.println("Registration successful.");
  }

  private static void addIncome() {
    System.out.print("Enter income description: ");
    String description = scanner.nextLine();
    System.out.print("Enter amount: ");
    double amount = Double.parseDouble(scanner.nextLine());
    currentUser.getWallet().addTransaction(description, amount, true);
    System.out.println("Income added successfully.");
  }

  private static void addExpense() {
    System.out.print("Enter expense description: ");
    String description = scanner.nextLine();
    System.out.print("Enter amount: ");
    double amount = Double.parseDouble(scanner.nextLine());
    currentUser.getWallet().addTransaction(description, amount, false);
    System.out.println("Expense added successfully.");
  }

  private static void setBudget() {
    System.out.print("Enter category: ");
    String category = scanner.nextLine();
    System.out.print("Enter budget amount: ");
    double amount = Double.parseDouble(scanner.nextLine());
    currentUser.getWallet().setBudget(category, amount);
    System.out.println("Budget set successfully.");
  }

  private static void viewSummary() {
    currentUser.getWallet().printSummary();
  }

  private static void loadUsers() {
    try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("users.dat"))) {
      Map<String, User> loadedUsers = (Map<String, User>) ois.readObject();
      users.putAll(loadedUsers);
    } catch (IOException | ClassNotFoundException e) {
      System.out.println("No existing user data found.");
    }
  }

  private static void saveUsers() {
    try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("users.dat"))) {
      oos.writeObject(users);
    } catch (IOException e) {
      System.err.println("Error saving user data.");
    }
  }
}

class User implements Serializable {
  private final String username;
  private final String password;
  private final Wallet wallet;

  public User(String username, String password) {
    this.username = username;
    this.password = password;
    this.wallet = new Wallet();
  }

  public String getPassword() {
    return password;
  }

  public Wallet getWallet() {
    return wallet;
  }
}



