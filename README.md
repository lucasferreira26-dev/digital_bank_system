# 🏦 Digital Banking System (Java)

## 📌 Overview

This project is a **console-based banking system** developed in Java, designed to simulate real-world banking operations.  
It focuses on **clean architecture, object-oriented programming (OOP), and business rule implementation**.

The system allows users to manage clients, open accounts, perform transactions, and view account statements.

---

## 🚀 Features

- 👤 Client registration with validation  
- 🏦 Open and manage bank accounts (Checking & Savings)  
- 💰 Deposit and withdraw operations  
- 🔁 Money transfer between accounts (with rollback safety)  
- 📊 Transaction history (account statement)  
- 📈 Monthly interest applied to savings accounts  
- 🔒 Exception handling for business rules  
- 🖥️ Interactive console-based user interface  

---

## 🧠 Business Rules

- A client must be at least 18 years old  
- Each client is uniquely identified by SSN  
- Accounts must be active to perform transactions  
- Accounts can only be closed if the balance is zero  
- Checking accounts support overdraft limits  
- Savings accounts apply a fixed monthly interest rate  
- Transfers are atomic (rollback is applied on failure)  

---

## 🏗️ Project Structure

src/
- ├── app/ Application entry point
- ├── consoleUI/ User interface (console)
- ├── model/ Domain entities
- ├── service/ Business logic
- ├── util/ Input validation
- ├── exception/ Custom exceptions


---

## 🧩 Technologies & Concepts

- Java (OOP)
- Collections (Map, List)
- Exception Handling
- Encapsulation & Abstraction
- Polymorphism (withdraw behavior)
- LocalDateTime API
- Clean Code principles

---

## 💡 Key Highlights

- Use of **polymorphism** to handle different account behaviors  
- Implementation of **transaction history with timestamps**  
- Safe **transfer operation with rollback mechanism**  
- Separation of concerns (Model, Service, UI)  
- Input validation with a dedicated utility class  

---

## 📊 Account Statement Example

===== ACCOUNT STATEMENT =====

- Date: Apr. 14, 2026 10:30
- Type: DEPOSIT
- Amount: $500.00
- Description: Deposit
---
- Date: Apr. 14, 2026 11:00
- Type: TRANSFER
- Amount: $200.00
- Description: Transfer to account 2
---
- Current Balance: $300.00

---

## ▶️ How to Run

1. Clone the repository  
2. Open the project in your IDE (IntelliJ recommended)  
3. Run the main class:
 - app/test.java
4. Use the console menu to interact with the system  

---

## 📈 Future Improvements

- REST API with Spring Boot  
- Database integration (PostgreSQL or MySQL)  
- Authentication system  
- GUI (JavaFX or Web frontend)  
- Export account statements (PDF/CSV)  

---

## 👨‍💻 Author

Developed by **Pedro Lucas Ferreira de Sousa**

---

## 📌 Final Note

This project was built to strengthen backend development skills and simulate real-world financial system behavior using Java.
