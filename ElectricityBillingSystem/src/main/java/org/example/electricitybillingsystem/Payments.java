package org.example.electricitybillingsystem;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.util.List;

public class Payments {

    @FXML
    private ScrollPane scrollPane;  // ScrollPane from FXML

    @FXML
    private AnchorPane transactionPane;  // AnchorPane inside the ScrollPane

    // Method to display the payment history when the button is clicked
    public void paymentHistory(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("previous_bills.fxml"));
            Parent root = loader.load();

            // Access the Payments controller and call the display method
            Payments controller = loader.getController();
            controller.displayTransactions();  // Display transactions

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.out.println("Error loading previous bills scene: " + e.getMessage());
        }
    }

    // Method to fetch and display transactions
    public void displayTransactions() {
        List<Transaction> transactions = TransactionData.getUserTransactions();  // Fetch transactions from the database

        double layoutY = 20;  // Initial Y position for the first transaction(s)

        for (Transaction transaction : transactions) {
            // Create a label for Transaction ID
            Label transactionIdLabel = new Label("Transaction ID: " + transaction.getTransactionId());
            transactionIdLabel.setLayoutX(50);
            transactionIdLabel.setLayoutY(layoutY);

            // Create a label for Amount
            Label amountLabel = new Label("Amount: $" + transaction.getAmount());
            amountLabel.setLayoutX(250);
            amountLabel.setLayoutY(layoutY);

            // Add the labels to the AnchorPane
            transactionPane.getChildren().addAll(transactionIdLabel, amountLabel);

            // Adjust the Y position for the next transaction
            layoutY += 30;
        }

        // Set the prefHeight of the AnchorPane to fit all transactions
        transactionPane.setPrefHeight(layoutY);
    }

    // Method to load the current bill scene
    public void currentBill(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("current_bill.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.out.println("Error loading current bill scene: " + e.getMessage());
        }
    }
}
