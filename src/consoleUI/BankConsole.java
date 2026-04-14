package consoleUI;

import exception.*;
import model.*;
import service.BankService;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class BankConsole {

    private BankService bankService;
    private Scanner scan;

    public BankConsole() {
        this.bankService = new BankService();
        this.scan = new Scanner(System.in);
    }

    public void start(){

        int option;

        do{
            showMainMenu();
            option = readOption();

            switch (option){
                case 1:
                    registerClient();
                    break;
                case 2:
                    openAccount();
                    break;
                case 3:
                    closeAccount();
                    break;
                case 4:
                    activateAccout();
                    break;
                case 5:
                    deposit();
                    break;
                case 6:
                    withdraw();
                    break;
                case 7:
                    transfer();
                    break;
                case 8:
                    listAccounts();
                    break;
                case 9:
                    applyMonthlyInterest();
                    break;
                case 10:
                    viewAccountStatement();
                    break;
                case 0:
                    closeProgram();
                    break;
                default:
                    invalidOption();
                    break;
            }
        } while (option != 0);
    }


    private void showMainMenu() {
        System.out.println("""
    ========================================
             🏦 DIGITAL BANK SYSTEM
    ========================================
    1 - Register Client
    2 - Open Account
    3 - Close Account
    4 - Active Account
    5 - Deposit
    6 - Withdraw
    7 - Transfer
    8 - List Accounts
    9 - Apply Monthly Interest
    10 - View Account Statement
    0 - Exit
    ========================================
    """);
    }

    private int readOption(){

        while (true){
            try{
                System.out.print("Choose an option (just write numbers): ");
                int option = scan.nextInt();
                System.out.println();
                return option;
            } catch (InputMismatchException e){
                System.out.println("❌ Invalid input! Use numbers only.");
                scan.nextLine();
            }
        }

    }

    private int enterAccountNumber(){

        while(true){
            try{
                System.out.print("Please, enter the account number (just write numbers): ");
                int option = scan.nextInt();
                return option;
            } catch(InputMismatchException e){
                System.out.println("❌ Invalid input! Use numbers only.");
                scan.nextLine();
            }
        }
    }

    private void registerClient(){
        System.out.println("===== 👤 Register Client =====");

        scan.nextLine();

        System.out.print("Name: ");
        String name = scan.nextLine();

        System.out.print("SSN: ");
        String ssn = scan.nextLine();

        System.out.print("Email: ");
        String email = scan.nextLine();

        System.out.print("Phone: ");
        String phone = scan.nextLine();

        System.out.print("Age: ");

        try{
            int age = scan.nextInt();

            bankService.createClient(name, ssn, email, phone, age);
            System.out.println("✅ Client registered successfully!");
        } catch (ClientAlreadyExistsException | InvalidClientException e){
            System.out.println(e.getMessage());
        } catch(InputMismatchException e){
            System.out.println("Please, just write numbers in the age field.");
            scan.nextLine();
        }
    }

    private AccountAgency chooseAgency(){

        AccountAgency agency = null;

        System.out.println("Choose one agency: ");
        System.out.println("1 - Street One 123");
        System.out.println("2 - Street Two 321");
        System.out.println("3 - Street Three 456");
        System.out.println("4 - Street Four 654");
        System.out.println("5 - Street Five 789");
        System.out.println("6 - Street Six 987");
        System.out.println("0 - Cancel");

        int option = readOption();

        switch (option){
            case 1:
                agency = AccountAgency.STREET_ONE_123;
                break;
            case 2:
                agency = AccountAgency.STREET_TWO_321;
                break;
            case 3:
                agency = AccountAgency.STREET_THREE_456;
                break;
            case 4:
                agency = AccountAgency.STREET_FOUR_654;
                break;
            case 5:
                agency = AccountAgency.STREET_FIVE_789;
                break;
            case 6:
                agency = AccountAgency.STREET_SIX_987;
                break;
            case 0:
                System.out.println("❌ Operation cancelled");
                break;
            default:
                invalidOption();
                break;
        }
        return agency;
    }

    private void openAccount(){
        System.out.println("===== \uD83D\uDCB0 Open Account =====");

        scan.nextLine();

        System.out.print("Write the client SSN: ");
        String ssn = scan.nextLine();

        try{
            Client client = bankService.findClientBySsn(ssn);

            System.out.println();

            System.out.println("✅ Client found with success! ");
            System.out.println(client);

            System.out.println();

            System.out.println("What kind of account would like open: ");

            System.out.println("1 - Checking Account");
            System.out.println("2 - Savings Account");
            System.out.println("0 - Return");

            int option = readOption();

            switch (option){
                case 1:
                    AccountAgency agencyChecking = chooseAgency();
                    bankService.openAccount(ssn, agencyChecking, AccountType.CHECKING);
                    System.out.println("✅ Your Checking Account was opened with success!");
                    for(Account account : client.getAccounts()){
                        System.out.println(account);
                        System.out.println("-----------------------------------");
                    }
                    break;
                case 2:
                    AccountAgency agencySavings = chooseAgency();
                    bankService.openAccount(ssn, agencySavings, AccountType.SAVINGS);
                    System.out.println("✅ Your Savings Account was opened with success!");
                    for(Account account : client.getAccounts()){
                        System.out.println(account);
                        System.out.println("-----------------------------------");
                    }
                    break;
                case 0:
                    System.out.println("Returning...");
                    break;
                default:
                    invalidOption();
                    break;
            }
        } catch (SsnNotFoundException | InvalidAccountTypeException | AgencyAccountExeception | InputMismatchException e){
            System.out.println(e.getMessage());
        }
    }

    private void closeAccount(){
        System.out.println("===== ❌ Close Account =====");

        scan.nextLine();

        int accountNumber = enterAccountNumber();

        try{
            Account account = bankService.findAccountByNumber(accountNumber);

            System.out.println();

            System.out.println("✅ We found your account: " + account);

            System.out.println();

            System.out.println("Are you sure do you want to close the account: ");
            System.out.println("1 - Close account");
            System.out.println("2 - Cancel close account");

            int option = readOption();

            switch (option){

                case 1:
                    bankService.closeAccount(accountNumber);
                    System.out.println("❌ Your account has been closed:");
                    System.out.println(account);
                    break;
                case 2:
                    System.out.println("✅ Your account remains active:");
                    System.out.println(account);
                    break;
                default:
                    invalidOption();
                    break;
            }
        } catch(AccountNotFoundException | AccountCannotBeClosedException | InputMismatchException e){
            System.out.println(e.getMessage());
        }
    }

    private void activateAccout(){
        System.out.println("===== ✅ Active Account =====");

        scan.nextLine();

        int accountNumber = enterAccountNumber();

        try{
            Account account = bankService.findAccountByNumber(accountNumber);

            System.out.println();

            System.out.println("✅ We found your Account: ");
            System.out.println(account);

            System.out.println();

            System.out.println("Are you sure do you want to active the account: ");
            System.out.println("1 - Active account");
            System.out.println("2 - Cancel active account");

            int option = readOption();

            switch (option){
                case 1:
                    bankService.activeAccount(accountNumber);
                    System.out.println("✅ Your account has been activity with success!");
                    System.out.println(account);
                    break;
                case 2:
                    System.out.println("❌ Operation cancelled.");
                    break;
                default:
                    invalidOption();
                    break;
            }
        } catch (InputMismatchException | AccountNotFoundException | AccountHasAlreadyBeenActiveException e){
            System.out.println(e.getMessage());
        }
    }

    private void deposit(){
        System.out.println("===== \uD83D\uDCB5 Deposit =====");

        scan.nextLine();

        int accountNumber = enterAccountNumber();

        try {
            Account account = bankService.findAccountByNumber(accountNumber);

            System.out.println(account);

            scan.nextLine();

            System.out.println();

            System.out.println("Do you want proceed on with your deposit? ");
            System.out.println("1 - Proceed with deposit");
            System.out.println("2 - Cancel deposit");
            int option = readOption();

            switch (option){
                case 1:

                    scan.nextLine();

                    System.out.print("Now, enter the amount that you are going to deposit in the account: ");

                    double amount = scan.nextDouble();

                    bankService.deposit(amount, accountNumber);
                    System.out.println("✅ Your deposit has been a success!");
                    System.out.println(account);
                    break;
                case 2:
                    System.out.println("❌ Operation cancelled!");
                    break;
                default:
                    invalidOption();
                    break;
            }

        } catch(InvalidAmountException | InactiveAccountException | AccountNotFoundException | InputMismatchException e){
            System.out.println(e.getMessage());
        }
    }

    private void withdraw(){
        System.out.println("===== \uD83D\uDCB0 Withdraw =====");

        scan.nextLine();

        int accountNumber = enterAccountNumber();

        try{
            Account account = bankService.findAccountByNumber(accountNumber);
            System.out.println(account);

            scan.nextLine();

            System.out.println();

            System.out.println("Do you want to proceed with your withdraw? ");
            System.out.println("1 - Proceed with withdraw");
            System.out.println("2 - Cancel withdraw");
            int option = readOption();

            switch (option){
                case 1:
                    scan.nextLine();

                    System.out.print("Now, enter the amount that you are going to withdraw in the account: ");

                    double amount = scan.nextDouble();

                    bankService.withdraw(amount, accountNumber);
                    System.out.println("✅ Your withdraw has been a success!");
                    System.out.println(account);
                    break;
                case 2:
                    System.out.println("❌ Operation cancelled!");
                    break;
                default:
                    invalidOption();
                    break;
            }
        } catch(AccountNotFoundException | InactiveAccountException | InvalidAmountException | InputMismatchException e){
            System.out.println(e.getMessage());
        }
    }

    private void transfer(){
        System.out.println("===== \uD83D\uDD01 \uD83D\uDCB5 Transfer =====");

        System.out.print("Write the Origin Account number: ");
        int originAccountNumber = scan.nextInt();

        System.out.print("Write the Destination Account number: ");
        int destinationAccountNumber = scan.nextInt();

        try{
            Account originAccount = bankService.findAccountByNumber(originAccountNumber);
            System.out.println(originAccount);

            System.out.println();

            Account destinationAccount = bankService.findAccountByNumber(destinationAccountNumber);
            System.out.println(destinationAccount);

            scan.nextLine();

            System.out.println();

            System.out.println("Do you want to proceed with the transfer? ");
            System.out.println("1 - Proceed with transfer");
            System.out.println("2 - Cancel transfer");
            int option = readOption();

            switch (option){
                case 1:
                    scan.nextLine();

                    System.out.print("Now, enter the amount that you are going to transfer to destination account: ");

                    double amount = scan.nextDouble();

                    bankService.transfer(originAccountNumber, destinationAccountNumber, amount);
                    System.out.println("✅ Your transfer has been a success!");
                    System.out.println(originAccount);
                    System.out.println(destinationAccount);
                    break;
                case 2:
                    System.out.println("❌ Operation cancelled!");
                    break;
                default:
                    invalidOption();
                    break;
            }
        } catch(AccountNotFoundException | AccountOriginEqualsDestinationException | InvalidAmountException | InactiveAccountException | InputMismatchException e){
            System.out.println(e.getMessage());
        }
    }

    private void viewAccountStatement(){
        System.out.println("===== Account Statement =====");

        scan.nextLine();

        int accountNumber = enterAccountNumber();

        try{
            Account account = bankService.findAccountByNumber(accountNumber);

            System.out.println("✅ We found your account!");
            System.out.println(account);

            System.out.println();

            System.out.println("Can we proceed with the statement?");
            System.out.println("1 - View statement");
            System.out.println("2 - Cancel view statement");

            int option = readOption();

            switch (option){
                case 1:

                   System.out.println("\n===== Account Transaction Statement =====");

                   List<Transaction> transactions = bankService.getAccountStatement(accountNumber);

                   List<Transaction> sortedTransactions = new ArrayList<>(transactions);
                   sortedTransactions.sort((t1, t2) ->
                           t2.getDateTime().compareTo(t1.getDateTime()));

                   DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy HH:mm");

                   for(Transaction t : sortedTransactions){

                       System.out.println("Date: " + t.getDateTime().format(formatter));
                       System.out.println("Type: " + t.getType());
                       System.out.println("Amount: $" + t.getAmount());
                       System.out.println("Description: " + t.getDescription());

                       System.out.println("-----------------------------------");
                   }

                   System.out.println("Current Balance: $" + account.getBalance());
                   break;
                case 2:
                    System.out.println("❌ Operation cancelled!");
                    break;
                default:
                    invalidOption();
                    break;
            }
        } catch (AccountNotFoundException | InactiveAccountException | InputMismatchException e){
            System.out.println(e.getMessage());
        }
    }

    private void listAccounts(){
        System.out.println("===== \uD83D\uDCCB Accounts List =====");

        for (Account acc : bankService.listAccounts().values()) {
                System.out.println(acc);
                System.out.println("-----------------------------------");
        }
    }

    private void applyMonthlyInterest(){
        System.out.println("===== Interest Rate of Savings Account =====");

        bankService.processMonthlyInterest();
    }

    private void closeProgram(){
        System.out.println("Closing program...");
    }

    private void invalidOption(){
        System.out.println("Invalid option. Try again...");
    }
}
