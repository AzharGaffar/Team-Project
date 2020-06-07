package sample;

import com.mysql.cj.protocol.Resultset;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class RegisterFormController {

    //Controller Links to RegisterForm.fxml

    //placeholders that will soon be replaced.
    public Text namePlaceholder;
    public Text staffNumberPlaceholder;
    public Text rolePlaceholder;

    //all of these elements are on the fxml page that is loaded.
    public TextField firstNameField;
    public TextField lastNameField;
    public TextField emailField;
    public TextField usernameField;
    public TextField passwordField;
    public TextField confirmPasswordField;
    public ComboBox<String> dropdownField;
    public TextField customNumberField;
    public Text warningText;
    public Button registerButton;

    //string and resultset for sql query.
    String sqlQuery;
    ResultSet resultSet;

    //Establishing a connection for SQL queries to go through.
    DatabaseConnection databaseConnection = new DatabaseConnection();
    Connection connection = databaseConnection.getConnection();

    //display message that the user has been added to the system.
    public void displayMessage() {
        Stage secondaryStage = new Stage();
        Text doneText = new Text();
        Button okButton = new Button();
        doneText.setText("User has been added to the System!");
        okButton.setText("OK");
        secondaryStage.setTitle("User added");
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
                secondaryStage.close();
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("StaffMemberTable.fxml")); //LOad the staff memeber table.
                    Stage stage = (Stage) registerButton.getScene().getWindow();
                    Scene scene = new Scene(loader.load());
                    stage.setScene(scene);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    //Initialize is the first method to be called.
    @FXML
    public void initialize() {
        //replace the placeholder names.
        namePlaceholder.setText(LoginStorage.getFirstName());
        staffNumberPlaceholder.setText(LoginStorage.getStaffNumber());
        rolePlaceholder.setText(LoginStorage.getRole());
        //Get all the roles and add them to the dropdown.
        dropdownField.getItems().addAll("System Administrator", "Office Manager", "Travel Advisor");
    }

    //Register button methods
    @FXML
    public void Register() {
        if (firstNameField.getText().length() != 0 && lastNameField.getText().length() != 0 && emailField.getText().length() != 0 && usernameField.getText().length() != 0 && passwordField.getText().length() != 0 && confirmPasswordField.getText().length() != 0) { //if none of the fields are empty.
            try {
                sqlQuery = "SELECT Username FROM Users WHERE Username ='" + usernameField.getText() + "'"; //Check if the username they want is taken/
                Statement usernameStatement = connection.createStatement();
                ResultSet usernameSet = usernameStatement.executeQuery(sqlQuery);
                if (usernameSet.next() != false) {
                    warningText.setText("That Username is already taken, pick another one."); //if it is tell the user.
                } else {
                    //else
                    if (emailField.getText().contains("@")) { //If the email contains an @ symbol
                        if (confirmPasswordField.getText().equals(passwordField.getText())) { //check if two passwords match
                            if (dropdownField.getValue() != null) { //check if they have selected a drop down element.
                                if (customNumberField.getText().length() != 0) {
                                    if (customNumberField.getText().matches("[0-9]+") && customNumberField.getText().length() == 3) { //they can only enter numbers in the custom number field and it can only be of length 3.
                                        try {
                                            Statement statement = connection.createStatement();
                                            sqlQuery = "SELECT * FROM Users WHERE Username= '" + usernameField.getText() + "'";
                                            resultSet = statement.executeQuery(sqlQuery); //getting results set.
                                            if (resultSet.next() == false) {
                                                try {
                                                    Statement newstatement = connection.createStatement();
                                                    sqlQuery = "SELECT * FROM Users WHERE Staff_Number= '" + customNumberField.getText() + "'"; //select from users with that staff number.
                                                    resultSet = newstatement.executeQuery(sqlQuery);
                                                    if (resultSet.next() != false) {
                                                        warningText.setText("There is already a User with that Staff Number! Try a different one or leave blank."); //tell the user staff member is already taken.
                                                    } else {
                                                        try {
                                                            //////////////////////////////////////////////////////////////////////////hash the password yet to be implemented./////////////////////////////////////////////
                                                            sqlQuery = "INSERT INTO Users VALUES('" + customNumberField.getText() + "','" + firstNameField.getText() + "','" + lastNameField.getText() + "','" + emailField.getText() + "','" + usernameField.getText() + "','" + /* change this*/confirmPasswordField.getText() + "','" + dropdownField.getValue() + "')";
                                                            //insert user into database.
                                                            Statement newUser = connection.createStatement();
                                                            newUser.execute(sqlQuery);
                                                            displayMessage();
                                                        } catch (Exception e) {
                                                            e.printStackTrace();
                                                        }
                                                    }
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }
                                            } else {
                                                warningText.setText("That Username is already being used by someone else! try a different one."); //warning text.
                                            }
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    } else {
                                        warningText.setText("Custom Staff Numbers can only be three numbers."); //warning text.
                                    }
                                } else {
                                    //Inserter algorithm to insert the user to the specific index they want.
                                    boolean search = true;
                                    int stringNumber = 1;
                                    while (search == true) {
                                        String userIndex = "";
                                        if (stringNumber >= 1 && stringNumber <= 9) {
                                            userIndex = "00" + stringNumber;
                                        } else if (stringNumber >= 10 && stringNumber <= 99) {
                                            userIndex = "0" + stringNumber;
                                        } else if (stringNumber >= 100 && stringNumber <= 999) {
                                            userIndex = "" + stringNumber;
                                        } else {
                                            warningText.setText("Too Many Users in the System. Delete unused accounts."); //too many accounts which will probably never be reached.
                                        }
                                        sqlQuery = "SELECT Staff_Number FROM Users WHERE Staff_Number='" + userIndex + "'";
                                        try {
                                            Statement finalStatement = connection.createStatement();
                                            resultSet = finalStatement.executeQuery(sqlQuery);
                                            if (resultSet.next() == false) {
                                                search = false;
                                                sqlQuery = "INSERT INTO Users VALUES('" + userIndex + "','" + firstNameField.getText() + "','" + lastNameField.getText() + "','" + emailField.getText() + "','" + usernameField.getText() + "','" + /* change this*/confirmPasswordField.getText() + "','" + dropdownField.getValue() + "')";
                                                //insert.
                                                finalStatement = connection.createStatement();
                                                finalStatement.execute(sqlQuery);
                                                //displaymessage method.
                                                displayMessage();
                                            } else {
                                                //iterate
                                                stringNumber++;
                                            }
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            } else {
                                warningText.setText("You haven't selected a role! Select one."); //warning text.
                            }
                        } else {
                            warningText.setText("Passwords don't match.");//warning text.
                        }
                    } else {
                        warningText.setText("You have not entered a valid email address! You need an @YourEmailProviderHere.com");//warning text.
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            warningText.setText("Not all the mandatory fields have been filled out! Please fill them.");//warning text.
        }
    }

    //loads the last fxml file.
    @FXML
    public void Back() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("StaffMemberTable.fxml")); //load the staff member table.fxml
            Stage stage = (Stage) registerButton.getScene().getWindow();
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
        } catch (IOException io) {
            io.printStackTrace();
        }
    }

}
