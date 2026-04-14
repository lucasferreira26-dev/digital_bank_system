package model;

import java.util.ArrayList;
import java.util.List;

public abstract class Account {

    private static int counter = 0;

    private int accountNumber;
    private AccountAgency agency;
    private double balance;
    private Client owner;
    private boolean active;
    private List<Transaction> transactionHistory;

    // The Account constructor has some default values, when one account is opene
    public Account(AccountAgency agency, Client owner) {
        // The account number is generate by the attribute count assistance
        this.accountNumber = ++counter;
        this.agency = agency;
        // Each account balance begin with value 0
        this.balance = 0;
        this.owner = owner;
        // All accounts, when are created, they are active
        this.active = true;
        this.transactionHistory = new ArrayList<>();
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public AccountAgency getAgency() {
        return agency;
    }

    public double getBalance() {
        return balance;
    }

    public Client getOwner() {
        return owner;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isActive() {
        return active;
    }

    public List<Transaction> getTransactionHistory() {
        // A copy of transaction prevents the list from being in change out the class
        return List.copyOf(transactionHistory);
    }

    // This method change the states of the attribute balance, increasing its value
    public void deposit(double amount){
        this.balance += amount;
    }

    // This is an abstract method, it will be used in the classes Checking and Savings Accounts
    // To realize different means of withdraw operation
    public abstract void withdraw(double amount);

    // This method is used both for CheckingAccount and the SavingsAccount to realize a subtraction
    // for withdraw operation
    public void withdrawAmount(double amount){
        this.balance -= amount;
    }

    // This method is only used in CheckingAccount for withdraw method specific terms
    public void resetBalance(){
        this.balance = 0;
    }

    // This method add one type of transaction in account history
    public void addTransaction(Transaction transaction){

        this.transactionHistory.add(transaction);
    }

    @Override
    public String toString() {
        return "===== Account =====" +
                "\naccountNumber: " + accountNumber +
                "\nagency: " + agency +
                "\nbalance: $" + balance +
                "\nowner: " + owner.getSsn() + ", " + owner.getName() +
                "\nactive: " + active +
                "\n";
    }
}
