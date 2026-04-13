package model;

import java.util.ArrayList;
import java.util.List;

public class Client {

    private static int counter = 0;

    private int id;
    private String name;
    private String ssn;
    private String email;
    private String phone;
    private int age;
    private List<Account> accounts;

    public Client(String name, String ssn, String email, String phone, int age) {
        // The client id is generated automatically by the attribute count assistance
        this.id = ++counter;
        this.name = name;
        this.ssn = ssn;
        this.email = email;
        this.phone = phone;
        this.age = age;
        this.accounts = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSsn() {
        return ssn;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public int getAge() {
        return age;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void addAccount(Account account){

        this.accounts.add(account);
    }

    @Override
    public String toString() {
        return "===== Client =====" +
                "\nid: " + id +
                "\nname: " + name +
                "\nSSN: " + ssn +
                "\nemail: " + email +
                "\nphone: " + phone +
                "\nage: " + age;
    }
}
