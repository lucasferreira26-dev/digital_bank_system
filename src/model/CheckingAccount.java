package model;

import exception.InvalidAmountException;

public class CheckingAccount extends Account {

    private double overDraftLimit;

    public CheckingAccount(AccountAgency agency, Client owner, double overDraftLimit) {
        super(agency, owner);
        this.overDraftLimit = overDraftLimit;
    }

    public double getOverDraftLimit() {
        return overDraftLimit;
    }


    // The withdraw() method in Checking Accounts is a little bit of complicated, because might be
    // that we need to use its overdraft limit. So we used some "ifs" to help us in this task
    @Override
    public void withdraw(double amount) {

        // First we preserve the original balance of one Checking Account
        double balance = getBalance();

        // After we used one if to verify the amount value. If it is bigger than balance and over-
        // draft values, we throw the exception InvalidAmountException
        if (amount > balance + overDraftLimit) {
            throw new InvalidAmountException(
                    "You cannot withdraw this amount: balance and overdraft is insufficient."
            );
        }

        // This if aim to realize the withdrawAmount() normally, case the amount value is less than
        // balance value
        if (amount <= balance) {
            withdrawAmount(amount);
            return;
        }

        // This third and last case, analyze if we are going to need using the overdraft value
        // to make a withdraw()

        // This variable put away the results of amount - balance. Probably the value of this
        // calculation is a negative number, because all cases with positive balance
        // was analyzed previously
        double remaining = amount - balance;

        // We cannot forget to maintain the account balance value positive, so we just reset
        // its value, then it doesn't stay negative.
        resetBalance();

        // Finally, we alter the overdraft value, subtracting it with the variable remaining
        overDraftLimit -= remaining;
    }

    @Override
    public String toString() {
        return super.toString() + "Overdraft: R$" + overDraftLimit;
    }
}
