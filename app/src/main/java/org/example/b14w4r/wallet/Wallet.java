package org.example.b14w4r.wallet;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.example.b14w4r.transaction.*;

public class Wallet implements Serializable {
  private final Map<String, List<Transaction>> transactions;
  private final Map<String, Double> budgets;

  public Wallet() {
    this.transactions = new HashMap<>();
    this.budgets = new HashMap<>();
  }

  public void addTransaction(String category, double amount, boolean isIncome) {
    transactions.putIfAbsent(category, new ArrayList<>());
    transactions.get(category).add(new Transaction(category, amount, isIncome));

    if (!isIncome && budgets.containsKey(category)) {
      double totalExpenses = transactions.get(category).stream()
          .filter(t -> !t.isIncome())
          .mapToDouble(Transaction::getAmount)
          .sum();

      if (totalExpenses > budgets.get(category)) {
        System.out.printf("Warning: Budget exceeded for category '%s'. Limit: %.2f, Current Expenses: %.2f%n",
            category, budgets.get(category), totalExpenses);
      }
    }
  }

  // public void addTransaction(String category, double amount, boolean isIncome)
  // {
  // transactions.putIfAbsent(category, new ArrayList<>());
  // transactions.get(category).add(new Transaction(category, amount, isIncome));
  // }

  public void setBudget(String category, double amount) {
    budgets.put(category, amount);
  }

  public void printSummary() {
    double totalIncome = 0;
    double totalExpense = 0;
    System.out.println("Income:");
    for (String category : transactions.keySet()) {
      double categoryIncome = transactions.get(category).stream()
          .filter(Transaction::isIncome)
          .mapToDouble(Transaction::getAmount)
          .sum();
      double categoryExpense = transactions.get(category).stream()
          .filter(t -> !t.isIncome())
          .mapToDouble(Transaction::getAmount)
          .sum();

      totalIncome += categoryIncome;
      totalExpense += categoryExpense;

      if (categoryIncome > 0 || categoryExpense > 0) {
        System.out.printf("%s: Income = %.2f, Expense = %.2f\n", category, categoryIncome, categoryExpense);
      }
    }

    System.out.printf("Total Income: %.2f\n", totalIncome);
    System.out.printf("Total Expense: %.2f\n", totalExpense);

    System.out.println("Budgets:");
    for (Map.Entry<String, Double> entry : budgets.entrySet()) {
      double remainingBudget = entry.getValue() - transactions.getOrDefault(entry.getKey(), new ArrayList<>())
          .stream()
          .filter(t -> !t.isIncome())
          .mapToDouble(Transaction::getAmount)
          .sum();
      System.out.printf("%s: Budget = %.2f, Remaining = %.2f\n", entry.getKey(), entry.getValue(), remainingBudget);
    }
  }
}
