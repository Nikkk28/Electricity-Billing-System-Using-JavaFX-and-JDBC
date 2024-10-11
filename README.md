# Electricity-Billing-System-Using-JavaFX-and-JDBC
This project is a Java-based desktop application designed to manage and automate electricity billing processes. Developed using JavaFX for the user interface and JDBC for database connectivity, the system allows users to perform essential operations such as user registration, login authentication, and bill generation.

# Technologies Used
Java SE: The core programming language used for application development.
JavaFX: Utilized for creating the graphical user interface (GUI), providing a rich and interactive user experience.
JDBC (Java Database Connectivity): Used for establishing connections to the SQLite database, allowing for data manipulation and retrieval.
SQLite: A lightweight database engine used to store user data, transaction records, and billing information.
Maven: Employed for dependency management, facilitating project setup, and streamlining the build process.

# Key Features
User Authentication: Secure login and registration process to protect user data and manage access.
Payment History Tracking: Users can view and manage their past transactions, enhancing financial transparency.
Transaction Management: Ability to record and retrieve transaction details, ensuring accurate billing and expense tracking.

# Technical Details
Architecture
The application follows a modular design, separating the GUI components from the backend logic. This separation promotes maintainability and scalability.

Database Schema
The SQLite database consists of the following tables:
Users: Stores user information, including usernames, passwords, meter numbers, and pin codes.
Transactions: Records transaction details, including transaction IDs, amounts, and associated usernames.

Connectivity
The application establishes a connection to the SQLite database using JDBC. Proper connection handling and error management are implemented to ensure reliable database interactions.
Security Measures
Passwords are securely hashed using the BCrypt library, enhancing user data protection.
Input validation is implemented to prevent SQL injection and ensure data integrity

# Development Environment
I recommend using IntelliJ IDEA as the IDE for this project. Its powerful features and built-in support for Java and JavaFX significantly enhance the development experience, making it easier to manage dependencies and build the application.
