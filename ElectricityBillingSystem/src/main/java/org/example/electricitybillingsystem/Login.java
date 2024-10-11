package org.example.electricitybillingsystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;

public class Login {
    private Parent root;
    private Scene scene;
    private Stage stage;

    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private PasswordField repassword;
    @FXML
    private Button login;
    @FXML
    private Label label; // Label for login errors
    @FXML
    private TextField meter;
    @FXML
    private TextField pincode;

    public void credentials(ActionEvent event){
        try {
            root=FXMLLoader.load(getClass().getResource("login.fxml"));
            stage=(Stage)((Node)event.getSource()).getScene().getWindow();
            scene=new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    // Login method handles both login and credentials
    public void login(ActionEvent event) {
        try {
            String name = username.getText();
            String pass = password.getText();

            // Check if fields are empty
            if (name.isEmpty() || pass.isEmpty()) {
                label.setText("Username and password cannot be empty.");
                return;
            }

            // Call the loginUser method from Validate_User class
            if (Validate_User.loginUser(name, pass)) {
                // If login is successful, load the welcome scene
                root = FXMLLoader.load(getClass().getResource("welcome.fxml"));
                stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } else {
                // Display error message if login fails
                label.setText("Invalid username or password.");
            }
        } catch (IOException e) {
            // Handle IOException from FXMLLoader
            System.out.println("Error loading the welcome scene: " + e.getMessage());
            label.setText("Error loading the welcome page. Please try again.");
        } catch (Exception e) {
            // Handle any other exceptions
            System.out.println("An error occurred: " + e.getMessage());
            label.setText("An unexpected error occurred. Please try again.");
        }
    }

    // Method for navigating to the signup page
    public void registerhere(ActionEvent event) {
        try {
            root = FXMLLoader.load(getClass().getResource("signup.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.out.println("Error loading the signup page: " + e.getMessage());
            label.setText("Error loading the signup page.");
        }
    }

    // Signup method with error handling and field validation
    public void signup(ActionEvent event) {
        try {
            String name = username.getText();
            String passwordField = password.getText();
            String repasswordField = repassword.getText();
            String meterno = meter.getText();
            String pincodeno = pincode.getText();

            // Check if any field is empty
            if (name.isEmpty() || passwordField.isEmpty() || repasswordField.isEmpty()
                    || meterno.isEmpty() || pincodeno.isEmpty()) {
                label.setText("All fields are required!");
                return;
            }

            // Check if the passwords match
            if (!passwordField.equals(repasswordField)) {
                label.setText("Passwords do not match!");
                return;
            }

            // Input user into the database with meter number and pincode
            Validate_User.inputUser(name, passwordField, meterno, pincodeno);
            System.out.println("User registered successfully!");

            // After successful signup, load the welcome screen
            root = FXMLLoader.load(getClass().getResource("welcome.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            System.out.println("Error loading the welcome scene: " + e.getMessage());
            e.printStackTrace();  // Print the stack trace for debugging
            label.setText("Error loading the welcome page.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();  // Print the stack trace for debugging
            label.setText("An unexpected error occurred. Please try again.");
        }
    }

}
