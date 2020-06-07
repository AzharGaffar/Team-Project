package sample;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

public class LoginPageController {

    //Controller Links to LoginPage.fxml

    public Button loginButton;
    public TextField usernameField;
    public TextField passwordField;
    public Text warningText;

    //You should always establish a connection to the Database in each controller otherwise queries will not go through.
    DatabaseConnection databaseConnection = new DatabaseConnection();
    Connection connection = databaseConnection.getConnection();

    //Declaration of a string that will be used for SQL statements.
    String sqlQuery;
    ResultSet resultSet;


    //LoginButton method.
    @FXML
    public void Login() {
        if (usernameField.getText().isEmpty() == false && passwordField.getText().isEmpty() == false) { //If fields are not empty.
            try {
                Statement statement = connection.createStatement();
                sqlQuery = "SELECT * FROM Users WHERE Username ='" + usernameField.getText() + "'"; //search for the relevant user.
                resultSet = statement.executeQuery(sqlQuery);
                if (resultSet.next() != false) {
                    resultSet.beforeFirst();
                    while (resultSet.next() != false) {
                        if (resultSet.getString("Username").equals(usernameField.getText())) { //If username equals what has been entered.
                            if (resultSet.getString("Password").equals((passwordField.getText()))) { //If password equals what has been entered.
                                if (resultSet.getString("Role").equals("System Administrator")) {
                                    //Set LoginStorage values so top left can be populated.
                                    LoginStorage.setFirstName(resultSet.getString("First_Name"));
                                    LoginStorage.setLastName(resultSet.getString("Last_Name"));
                                    LoginStorage.setStaffNumber(resultSet.getString("Staff_Number"));
                                    LoginStorage.setFirstLogin(true);
                                    LoginStorage.setRole(resultSet.getString("Role"));
                                    LoginStorage.setUsername(resultSet.getString("Username"));
                                    FXMLLoader loader = new FXMLLoader(getClass().getResource("MainScreenSystemAdministrator.fxml")); //Load System admin page if they are sys admin.
                                    Stage stage = (Stage) loginButton.getScene().getWindow();
                                    Scene scene = new Scene(loader.load());
                                    stage.setScene(scene);
                                } else if (resultSet.getString("Role").equals("Office Manager")) {
                                    LoginStorage.setFirstName(resultSet.getString("First_Name"));
                                    LoginStorage.setLastName(resultSet.getString("Last_Name"));
                                    LoginStorage.setStaffNumber(resultSet.getString("Staff_Number"));
                                    LoginStorage.setFirstLogin(true);
                                    LoginStorage.setRole(resultSet.getString("Role"));
                                    LoginStorage.setUsername(resultSet.getString("Username"));
                                    FXMLLoader loader = new FXMLLoader(getClass().getResource("MainScreenOfficeManager.fxml")); //else load office Manager page if they are office manager.
                                    Stage stage = (Stage) loginButton.getScene().getWindow();
                                    Scene scene = new Scene(loader.load());
                                    stage.setScene(scene);
                                } else if (resultSet.getString("Role").equals("Travel Advisor")) {
                                    LoginStorage.setFirstName(resultSet.getString("First_Name"));
                                    LoginStorage.setLastName(resultSet.getString("Last_Name"));
                                    LoginStorage.setStaffNumber(resultSet.getString("Staff_Number"));
                                    LoginStorage.setFirstLogin(true);
                                    LoginStorage.setRole(resultSet.getString("Role"));
                                    LoginStorage.setUsername(resultSet.getString("Username"));
                                    FXMLLoader loader = new FXMLLoader(getClass().getResource("MainScreenTravelAdvisor.fxml")); //else load travel advisor page if they are travel advisor.
                                    Stage stage = (Stage) loginButton.getScene().getWindow();
                                    Scene scene = new Scene(loader.load());
                                    stage.setScene(scene);
                                }
                            } else {
                                warningText.setText("The Account Exists, but the password is incorrect. Try Again."); //inform them password is incorrect.
                            }
                        }
                    }
                } else {
                    warningText.setText("That Account doesn't exist!"); //inform them the account doesnt exist.
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            warningText.setText("Fill in both fields!"); //tell user to fill in all fields before submitting.
        }
    }
}