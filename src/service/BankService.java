package service;
import exception.*;
import model.*;
import util.AuthenticateClient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class BankService {

    // Map of clients, the key is a String
    private Map<String, Client> clients;
    // Map of account, the key is an Integer
    private Map<Integer, Account> accounts;

    public BankService() {
        this.clients = new HashMap<>();
        this.accounts = new HashMap<>();
    }

    // Method to create a client: no client, no account
    public void createClient(String name, String ssn, String email, String phone, int age){

        // called the method in th package util to validate the user input: Strings and int fields
        AuthenticateClient.validate(name, ssn, email, phone, age);

        // Business Rule: One client with a ssn already registered in the system,
        // cannot register the same ssn again
        if(this.clients.containsKey(ssn)){
            throw new ClientAlreadyExistsException("SSN key already exists in the system.");
        }

        Client client = new Client(name, ssn, email, phone, age);

        clients.put(ssn, client);
    }

    // Method to find a client registered in the system
    public Client findClientBySsn(String ssn){

        // get the client, based on the ssn
        Client client = clients.get(ssn);

        // Business Rule: if client doesn't exist, return the Exception SsnNotFoundException
        if (client == null) {
            throw new SsnNotFoundException("Client with given SSN not found.");
        }

        // if client exists, return your information
        return client;
    }

    // Method to view all clients registered in the system
    public Map<String, Client> listClients(){
        return clients;
    }

    // Method to generate a random overdraft value for checking account
    private double generatingOverDraftValue(int age){

        Random generateOverDraft = new Random();
        double min;
        double max;

        // Update min and max values based on age
        if (age < 25) {
            min = 100;
            max = 500;
        } else {
            min = 500;
            max = 1000;
        }

        // Generating and storing the random overdraft value
        double value = min + (max - min) * generateOverDraft.nextDouble();

        // Round value for two decimal places
        return Math.round(value * 100.0) / 100.0;
    }

    // Method to a client open an account
    public void openAccount(String ssn, AccountAgency agency, AccountType accountType){

        // first, we verify if client exists. If exists, we move on the program, else
        // the exception SsnNotFoundException is triggered
        Client client = findClientBySsn(ssn);

        // Second, we verify if client has already had one account in the agency informed
        // If it doesn't, ok, else the exception AgencyAccountException is triggered
        for (Account acc : client.getAccounts()) {
            if (acc.getAgency() == agency) {
                throw new AgencyAccountExeception(
                        "Client has already had an account in this agency."
                );
            }
        }

        // The client have two kinds of account he can choose to open: Checking and Savings account
        switch (accountType){
            case CHECKING:

                // case a checking account, one overdraft value will be generated
                double overDraft = generatingOverDraftValue(client.getAge());

                // Thas's a default constructor for checking account, it receives the agency,
                // client and overdraft value
                Account checkingAccount = new CheckingAccount(agency, client, overDraft);

                // Here, we link the account its respective owner
                client.addAccount(checkingAccount);

                // finally, we store the account int the Map of accounts, passing the key and
                // account values
                accounts.put(checkingAccount.getAccountNumber(), checkingAccount);

                break;
            case SAVINGS:

                // differently the checking accounts, savings account doesn't have overdraft
                // so its default constructor only receives the agency and client value
                Account savingsAccount = new SavingsAccount(agency, client);

                // link the account its respective owner
                client.addAccount(savingsAccount);

                // Store the account in the Map of accounts, passing the key and account values
                accounts.put(savingsAccount.getAccountNumber(), savingsAccount);

                break;
            default:
                // Case the value of account type is invalid, return the Exception InvalidAccountType
                throw new InvalidAccountTypeException("Account type invalid.");
        }
    }

    // Method to find an account based on its number.
    // We'll use and reuse this method many times in the system
    public Account findAccountByNumber(int accountNumber){

        // We store the account (save in the Map of accounts) at an instance the class Account
        Account account = accounts.get(accountNumber);

        // if account doesn't exist, the exception AccountNotFoundException is triggered
        if(account == null){
            throw new AccountNotFoundException("Account with given number not found.");
        }

        // if exists, we return it normally
        return account;
    }

    // This method shows us the balance of one Savings Account with Interest Rate per month
    public void processMonthlyInterest(){

        // We have a variable count to count the amount of savings account with interest rate
        int count = 0;

        for (Account account : accounts.values()) { // get the accounts and ignore the key

            // Our objective is got only savings account, because checking account
            // doesn't have interest rate, so we used instanceof to get only savings account
            if (account instanceof SavingsAccount savingsAccount) {

                // Rules Business: Savings account indicate should be active and your balance
                // should bigger than 0, we execute the apllyMonthlyInterest() method,
                // and the remaining of the processes, else, nothing happens.
                if (savingsAccount.isActive() && savingsAccount.getBalance() > 0) {
                    savingsAccount.applyMonthlyInterest();
                    System.out.println(savingsAccount);
                    System.out.println("-----------------------------------");
                    count++;
                }
            }
        }
        System.out.println("Amount of savings account with interest rate: " + count);
    }

    // This method closes one account based on its number
    public void closeAccount(int accountNumber){

        Account account = findAccountByNumber(accountNumber);

        // Business Rule 1: The account balance has to be necessarily 0 to close the account
        // if the account balance is different of 0, we called the exception AccountCannotBeClosedException
        if(account.getBalance() != 0){
            throw new AccountCannotBeClosedException("Account cannot be closed. Its balance must be 0.");
        }

        // Business Rule 2: if the account has already been closed, we cannot close it again
        // so, we called the exception AccountCannotBeClosedException
        if(!account.isActive()){
            throw new AccountCannotBeClosedException("Account has already been closed.");
        }

        // We used the setter method of the class Account to change the attribute "active" status
        // this attribute, by default, is true, so to close one account, we change "true" for "false"
        account.setActive(false);
    }

    // We can open an account, also close an account, and we can activate one account
    // based on its number as well. This method allow us to activate one closed account again
    // changing its active attribute value.
    public void activeAccount(int accountNumber){

        Account account = findAccountByNumber(accountNumber);

        // Business Rule: If the account has already been activated, we called the exception
        // AccountHasAlreadyBeenActiveException
        if(account.isActive()){
            throw new AccountHasAlreadyBeenActiveException("Your Account has already been active.");
        }

        account.setActive(true);
    }

    // This method shows us all registered accounts in the system
    public Map<Integer, Account> listAccounts() {
        // Creating a new map with the same datas, in other words, a copy of the original map
        // this guarantess the integrity of the original system
        return new HashMap<>(accounts);
    }

    // This one operation we can do with one account, maybe the most important operation
    // we can change the balance of one account based on one amount.
    // By default, all accounts, when they are created, their balance value are 0.
    public void deposit(double amount, int accountNumber){

        Account account = findAccountByNumber(accountNumber);

        // Business Rule 1: The method doesn't allow amounts less or equals 0.
        // So we called the exception InvalidAmountException.
        if(amount <= 0){
            throw new InvalidAmountException("The amount value is not acceptable");
        }

        // Business Rule 2: if the account is closed, we cannot proceed with operation.
        // So we called the exception InactiveAccountException. That's why is really important
        // maintain the accounts activate, because to do all accounts operation, they have to be
        // activated to prevent these exceptions
        if(!account.isActive()){
            throw new InactiveAccountException("Please, active your account to make a deposit");
        }

        // Everything ok, we used the method deposit of the class Account to change the
        // balance value the respective account
        account.deposit(amount);

        // We also registered the operation in the account history
        Transaction depositOperation = new Transaction(TransactionType.DEPOSIT, amount, "Deposit of: R$" + amount);

        account.addTransaction(depositOperation);

    }

    // Come on to another operation with balance value of one account.
    // This method has the intention to subtract the balance value of one account
    public void withdraw(double amount, int accountNumber){

        Account account = findAccountByNumber(accountNumber);

        // Business Rule 1: If the account is not active, it won't be possible withdraw one amount
        // of the account balance
        if(!account.isActive()){
            throw new InactiveAccountException("Please, active your account to make a withdraw.");
        }

        // Business Rule 2: The amount value cannot be less or equals by 0
        if (amount <= 0){
            throw new InvalidAmountException("Amount value is invalid for the operation");
        }

        // Everything ok, we can withdraw one amount of the account, using a method of the class
        // Account to do that. If the account is a Checking Account, although its balance is 0
        // we can withdraw the overdraft value, if it is within the limit.
        account.withdraw(amount);

        // We also register the operation in the class Transaction, such as the deposit operation
        Transaction withdrawOperation = new Transaction(TransactionType.WITHDRAW, amount, "Withdraw of: R$" + amount);

        account.addTransaction(withdrawOperation);
    }

    // This method is the last operation of the bank service. Its looks to realize a transfer between 2 accounts
    public void transfer(int originAccountNumber, int destinationAccountNumber, double amount){

        // Business Rule 1: First the transfer amount value cannot be less or equals to 0
        if(amount <= 0){
            throw new InvalidAmountException("The amount value is not acceptable.");
        }

        // We're looking for two account:
        // Origin Account -> The account whose objective is transfer the amount to another account based on his balance
        // Destination Account -> The account that receive the amount from Origin Account
        Account originAccount = findAccountByNumber(originAccountNumber);
        Account destinationAccount = findAccountByNumber(destinationAccountNumber);

        // Business Rule 2: We cannot do a transfer to the same account, for this reason
        // the Origin Account cannot be equals to Destination Account
        if (originAccountNumber == destinationAccountNumber){
            throw new AccountOriginEqualsDestinationException("You cannot transfer to the same account.");
        }

        // Business Rule 3 e 4: Both accounts have to be activated to realize the transfer operation;
        if(!originAccount.isActive()){
            throw new InactiveAccountException("ERROR! origin account is not active");
        }

        if(!destinationAccount.isActive()){
            throw new InactiveAccountException("ERROR! destination account is not active");
        }

        // Before we realize the operation, we created a flag to control the state
        // it is going to signal us if the operation has been a success or not
        boolean withdrawSuccess = false;

        // We used the tools try/catch, first to try the operation correctly, but if not,
        // we catch one exception
        try {

            // We also reused the methods withdraw() and deposit() to realize this operation
            // We didn't need create a new method to do this. The method withdraw() the amount of the
            // Origin Account and the method deposit() add the same amount in the Destination Account
            originAccount.withdraw(amount);

            // Case withdraw() has been a success, this variable change its state
            withdrawSuccess = true;

            destinationAccount.deposit(amount);

            // We cannot fail to register the operation in the class Transaction
            Transaction transferOperationOriginAccount = new Transaction(TransactionType.TRANSFER, amount,
                    "Transfer to account: " + destinationAccountNumber);

            Transaction transferOperationDestinationAccount = new Transaction(TransactionType.TRANSFER, amount,
                    "Transfer from account: " + originAccountNumber);

            originAccount.addTransaction(transferOperationOriginAccount);
            destinationAccount.addTransaction(transferOperationDestinationAccount);

        } catch (Exception e) {

            // If there is a problem with withdraw, the variable withdraw success don't change its
            // state, so the exception is caught, but we can do the rollback, in the other words
            // the amount value is not miss, it returns to the Origin Account
            if (withdrawSuccess) {
                originAccount.deposit(amount);
            }

            throw e;
        }

    }

    // The lost BankService's method. It has the objective to show us all transactions realized
    // by one account, in the other words, a transactions list
    public List<Transaction> getAccountStatement(int accountNumber){

        Account account = findAccountByNumber(accountNumber);

        // Business Rule: The account's transaction list is only gonna appear if the account is
        // active
        if(!account.isActive()){
            throw new InactiveAccountException("Your account is inactive.");
        }

        // We return account.getTransactionHistory, one attribute of the class Account
        return account.getTransactionHistory();
    }
}
