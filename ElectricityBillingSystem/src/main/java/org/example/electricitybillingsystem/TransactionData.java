package org.example.electricitybillingsystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TransactionData {

    // Method to fetch transactions based on the logged-in user
    public static List<Transaction> getUserTransactions() {
        List<Transaction> transactions = new ArrayList<>();
        String username = Validate_User.loggedInUsername;  // Get the logged-in username

        // Check if the user is logged in
        if (username == null) {
            System.out.println("Error: No user is logged in.");
            return transactions;
        }

        // SQL query to fetch transactions for the logged-in user
        String query = "SELECT transaction_id, amount FROM transactions WHERE username = ?";  // Assuming the `transactions` table has these fields

        // Use try-with-resources to automatically close the connection, prepared statement, and result set
        try (Connection conn = Validate_User.connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, username);  // Bind the username to the SQL query
            ResultSet rs = pstmt.executeQuery();

            // Loop through the result set and add transactions to the list
            while (rs.next()) {
                int transactionId = rs.getInt("transaction_id");
                double amount = rs.getDouble("amount");

                // Create a Transaction object and add it to the transactions list
                transactions.add(new Transaction(transactionId, amount));
            }

        } catch (SQLException e) {
            System.out.println("Error fetching transactions: " + e.getMessage());
        }

        return transactions;
    }
}
