package model;

import exception.InvalidAmountException;

public class SavingsAccount extends Account  {

    private double interestRate;

    public SavingsAccount(AccountAgency agency, Client owner) {
        super(agency, owner);
        this.interestRate = 0.01; // By default, the interest rate is 1% for each Savings Account
    }

    public double getInterestRate() {
        return interestRate;
    }

    // This method apply the interest rate Savings Accounts balance
    public void applyMonthlyInterest(){

        // First, we preserve the original account balance value
        // The local variable "interest" will save index of interest
        double interest = getBalance() * interestRate;

        // We reused the method deposit, to increase the index value on original account balance
        deposit(interest);

        // We also register this operation in the Savings Account history
        Transaction interestTransaction = new Transaction(TransactionType.INTEREST, interest,
                "Monthly interest applied");

        addTransaction(interestTransaction);
    }

    // The withdraw() method in Savings Account is simpler than in Checking Account, because
    // Savings Account doesn't have overdraft, the balance is subtracted normally, using the
    // withdrawAmount() method
    @Override
    public void withdraw(double amount) {
        // Here, we prevent that one Savings Account balance is negative
        // So if the amount is bigger than the account balance, we use the exception InvalidAmountException
        if(amount > getBalance()){
            throw new InvalidAmountException("Balance account is insufficient.");
        }

        // If everything ok, we make the withdrawAmount() operation normally
        withdrawAmount(amount);
    }

    @Override
    public String toString() {
        return super.toString() + "Interest Rate: $" + interestRate;
    }
}
