package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CustomerRegistrationController {

    //Controller Links to CustomerRegistrationForm.fxml

    //placeholders to be replaced.
    public Text namePlaceholder;
    public Text staffNumberPlaceholder;
    public Text rolePlaceholder;

    //all elements on the fxml page displayed here.
    public Button registerButton;
    public TextField firstNameField;
    public TextField lastNameField;
    public TextField aliasField;
    public TextField emailField;
    public TextField telephoneField;
    public Text warningText;

    //make a connection to the database.
    DatabaseConnection databaseConnection = new DatabaseConnection();
    Connection connection = databaseConnection.getConnection();

    //initialize an sql query and resultset.
    public String sqlQuery;
    public ResultSet resultSet;

    //initialize is the first thing to be called, replaces text in top left of window.
    @FXML
    public void initialize() {
        namePlaceholder.setText(LoginStorage.getFirstName());
        staffNumberPlaceholder.setText(LoginStorage.getStaffNumber());
        rolePlaceholder.setText(LoginStorage.getRole());
    }

    public void displayMessage() {
        //display message that a new customer has been added to the system.
        Stage secondaryStage = new Stage();
        Text doneText = new Text();
        Button okButton = new Button();
        doneText.setText("New Customer has been added to the System!");
        okButton.setText("OK");
        secondaryStage.setTitle("Customer added");
        Pane root2 = new Pane();
        secondaryStage.setScene(new Scene(root2, 400, 200));
        root2.getChildren().add(doneText);
        root2.getChildren().add(okButton);
        doneText.setLayoutX(100);
        doneText.setLayoutY(75);
        okButton.setLayoutX(190);
        okButton.setLayoutY(150);
        secondaryStage.show();
        secondaryStage.setResizable(false);
        okButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                secondaryStage.close(); //dismiss
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("CreateCustomerPage.fxml")); //redirect to createc customer page.
                    Stage stage = (Stage) registerButton.getScene().getWindow();
                    Scene scene = new Scene(loader.load());
                    stage.setScene(scene);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    //registers new user to system.
    @FXML
    public void Register() {
        if (registerButton.getText().length() != 0 && lastNameField.getText().length() != 0 && aliasField.getText().length() != 0 && emailField.getText().length() != 0 && telephoneField.getText().length() != 0) { //if no fields are empty
            if (emailField.getText().contains("@")) {
                if (telephoneField.getText().matches("[0-9]+")) { //only numbers entered in telephone field.
                    try {
                        Statement statement = connection.createStatement();
                        sqlQuery = "SELECT * FROM Customer WHERE Alias= '" + aliasField.getText() + "'"; //look for duplicates.
                        resultSet = statement.executeQuery(sqlQuery);
                        if (resultSet.next() != false) {
                            warningText.setText("There is already a Customer with that Alias! Try a different one."); //can't use that alias.
                        } else {
                            try {
                                sqlQuery = "INSERT INTO Customer(First_Name, Last_Name, Alias, Email, Telephone, Status, Fixed_Discount_Rate) VALUES('" + firstNameField.getText() + "','" + lastNameField.getText() + "','" + aliasField.getText() + "','" + emailField.getText() + "','" + telephoneField.getText() + "','Casual',null)"; //insert customer into database
                                Statement newCustomer = connection.createStatement();
                                newCustomer.execute(sqlQuery); //execute
                                displayMessage();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    warningText.setText("Only enter numbers into the Telephone field"); //warning message
                }
            } else {
                warningText.setText("You haven't entered a valid email. Email must contain an @ symbol."); //warning message
            }
        } else {
            warningText.setText("Please complete all the mandatory fields!"); //warning message.
        }
    }

    //back method loads the last page
    @FXML
    public void Back() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CreateCustomerPage.fxml")); //load create customer page.
            Stage stage = (Stage) telephoneField.getScene().getWindow();
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
        } catch (IOException io) {
            io.printStackTrace();
        }

    }
}
