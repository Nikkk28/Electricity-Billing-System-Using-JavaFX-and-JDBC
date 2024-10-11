package org.example.electricitybillingsystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Validate_User {
    private static final String DATABASE_URL = "jdbc:sqlite:C:/ElectricityBillingSystem/customer.db";

    // Method to establish a connection
    public static Connection connect() {
        Connection conn = null;
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection(DATABASE_URL);
            System.out.println("Connection Established!");
        } catch (SQLException e) {
            System.out.println("Error connecting to database: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return conn;
    }

    // Method to create the table if it doesn't exist
    public static void createTable(Connection conn) {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS users ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "username TEXT NOT NULL UNIQUE, "
                + "password TEXT NOT NULL, "
                + "meter_number TEXT NOT NULL UNIQUE, "  // New field for meter number
                + "pincode TEXT NOT NULL);";  // New field for pincode

        String createTransactionTable = "CREATE TABLE IF NOT EXISTS transactions ("
                + "transaction_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "username TEXT NOT NULL,"
                + "amount REAL NOT NULL,"
                + "date TEXT NOT NULL,"
                + "FOREIGN KEY (username) REFERENCES users(username)"
                + ");";

        try (Statement stmt = conn.createStatement()) {
            stmt.execute(createTableSQL);
            stmt.execute(createTransactionTable);
            System.out.println("Table Created! (IF NOT EXISTED)");
        } catch (SQLException e) {
            System.out.println("Error creating table: " + e.getMessage());
        }
    }

    // Method to check if a username or meter number is already taken
    public static boolean isUsernameOrMeterTaken(Connection conn, String username, String meterNumber) {
        String checkSQL = "SELECT COUNT(*) FROM users WHERE LOWER(username) = LOWER(?) OR meter_number = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(checkSQL)) {
            pstmt.setString(1, username);
            pstmt.setString(2, meterNumber);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;  // If count > 0, username or meter number exists
            }
        } catch (SQLException e) {
            System.out.println("Error checking username or meter number: " + e.getMessage());
        }
        return false;
    }

    // Method to input a new user into the database
    public static void inputUser(String username, String password, String meterNumber, String pincode) {
        String insertSQL = "INSERT INTO users(username, password, meter_number, pincode) VALUES(?,?,?,?)";
        try (Connection conn = connect()) {
            if (conn != null) {
                createTable(conn);  // Create the table if it doesn't exist

                // Check if the username or meter number is already taken
                if (isUsernameOrMeterTaken(conn, username, meterNumber)) {
                    System.out.println("Error: Username or Meter Number is already taken!");
                    return;
                }

                // Validate the password
                if (!isPasswordValid(password)) {
                    System.out.println("Error: Password must be at least 8 characters long, "
                            + "contain at least one uppercase letter, one number, and one special character!");
                    return;
                }

                // Insert the user into the database if everything is valid
                try (PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
                    pstmt.setString(1, username);
                    pstmt.setString(2, password);
                    pstmt.setString(3, meterNumber);  // Insert meter number
                    pstmt.setString(4, pincode);      // Insert pincode
                    pstmt.executeUpdate();
                    System.out.println("User registered successfully!");
                }
            }
        } catch (Exception e) {
            System.out.println("Error inserting user: " + e.getMessage());
        }
    }

    // Method to validate user login
    public static String loggedInUsername = null;  // To store the logged-in user's username

    public static boolean loginUser(String username, String password) {
        String querySQL = "SELECT password FROM users WHERE LOWER(username) = LOWER(?)";  // Case-insensitive check
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(querySQL)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                String storedPassword = rs.getString("password");
                if (storedPassword.equals(password)) {
                    loggedInUsername = username;  // Store the username after successful login
                    System.out.println("Login successful! Welcome back, " + username + "!");
                    return true;
                } else {
                    System.out.println("Error: Incorrect password.");
                }
            } else {
                System.out.println("Error: Username not found.");
            }
        } catch (SQLException e) {
            System.out.println("Error logging in: " + e.getMessage());
        }
        return false;
    }

    // Method to validate the password with the new rules
    public static boolean isPasswordValid(String password) {
        // Regular expression to match password requirements
        String passwordRegex = "^(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?]).{8,}$";

        return password.matches(passwordRegex);
    }
}
