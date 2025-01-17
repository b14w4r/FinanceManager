package org.example.b14w4r.transaction;

import java.io.Serializable;

public class Transaction implements Serializable {
  private final String category;
  private final double amount;
  private final boolean isIncome;

  public Transaction(String category, double amount, boolean isIncome) {
    this.category = category;
    this.amount = amount;
    this.isIncome = isIncome;
  }

  public double getAmount() {
    return amount;
  }

  public boolean isIncome() {
    return isIncome;
  }
}

