# MoneyManager - Backend

MoneyManager is the backend component of a personal finance management software. It provides a robust RESTful API for managing income, expenses, budgets, and other financial data. Built with **Java 17**, **Spring Boot**, **Maven**, and **PostgreSQL**, this project serves as the core service layer for the MoneyManager application.

## 🚀 Features

- Income and expense tracking
- Category-based financial organization
- Monthly and yearly financial summaries
- Budget planning and management
- RESTful API endpoints
- PostgreSQL database integration

## 🛠 Tech Stack

- **Java 17**
- **Spring Boot**
- **Maven**
- **PostgreSQL**
- **Spring Data JPA**
- **Lombok**
- **Hibernate**

## 📦 Installation

1. **Clone the repository**

```bash
git clone https://github.com/J41R0JUNIOR/MoneyManager.git
cd MoneyManager
  ```

2. **Configure the database**

Make sure you have PostgreSQL installed and running. Then, create a database for the project:
```bash
CREATE DATABASE moneymanager;
```

Update the application.properties (or application.yml) with your PostgreSQL credentials:

```bash
spring.datasource.url=jdbc:postgresql://localhost:5432/moneymanager
spring.datasource.username=your_username
spring.datasource.password=your_password
```

3. **Build the project**

```bash
mvn clean install
```

4. **Run the application**
 
```bash
 mvn spring-boot:run
 ```

The server will start at http://localhost:8080.
