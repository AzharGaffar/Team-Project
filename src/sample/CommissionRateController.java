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
import java.sql.Statement;

public class CommissionRateController {

    //Controller Links to CommissionRatePage.fxml

    //Setting up placeholders.
    public Text namePlaceholder;
    public Text staffNumberPlaceholder;
    public Text rolePlaceholder;

    //textfields for both rates.
    public TextField InterlineRate;
    public TextField DomesticRate;

    //warning text initialize.
    public Text warningText;

    //initialize strings and result set.
    String sqlQuery;
    ResultSet resultSet;
    String InterlineHolder; //used in prompt text.
    String DomesticHolder; //used in prompt text.

    //links to database to form a connection.
    DatabaseConnection databaseConnection = new DatabaseConnection();
    Connection connection = databaseConnection.getConnection();


    //initialize is the first method called automatically.
    @FXML
    public void initialize() {

        //replace the placeholders.
        namePlaceholder.setText(LoginStorage.getFirstName());
        staffNumberPlaceholder.setText(LoginStorage.getStaffNumber());
        rolePlaceholder.setText(LoginStorage.getRole());

        try {
            sqlQuery = "SELECT Interline FROM Commission";
            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery(sqlQuery); //execute select interline

            while (resultSet.next()) {
                InterlineHolder = resultSet.getString("Interline"); //store it in preparation for placeholder.
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            sqlQuery = "SELECT Domestic FROM Commission";
            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery(sqlQuery); //execute select domestic

            while (resultSet.next()) {
                DomesticHolder = resultSet.getString("Domestic"); //store it in preparation for placeholder.
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //set the prompt texts to inform the user.
        InterlineRate.setPromptText(InterlineHolder);
        DomesticRate.setPromptText(DomesticHolder);
    }

    //updates the rates.
    @FXML
    public void Update() {
        if (InterlineRate.getText().isEmpty() == false && DomesticRate.getText().isEmpty() == false) { //if neither are empty
            if (InterlineRate.getText().matches("[0-9]+") && DomesticRate.getText().matches("[0-9]+")) { //if only numbers have been entered.
                try {
                    sqlQuery = "DELETE FROM Commission"; //delete both records.
                    Statement statement = connection.createStatement();
                    statement.execute(sqlQuery);
                    sqlQuery = "INSERT INTO Commission(Interline,Domestic) VALUES('" + InterlineRate.getText() + "','" + DomesticRate.getText() + "')"; //create a new record.
                    statement.execute(sqlQuery);
                    //tell the user done
                    Stage secondaryStage = new Stage();
                    Text doneText = new Text();
                    Button okButton = new Button();
                    doneText.setText("Done!");
                    okButton.setText("OK");
                    secondaryStage.setTitle("Done!");
                    Pane root2 = new Pane();
                    secondaryStage.setScene(new Scene(root2, 400, 200));
                    root2.getChildren().add(doneText);
                    root2.getChildren().add(okButton);
                    doneText.setLayoutX(190);
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
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("MainScreenOfficeManager.fxml")); //redirect to office manager menu.
                                Stage stage = (Stage) DomesticRate.getScene().getWindow();
                                Scene scene = new Scene(loader.load());
                                stage.setScene(scene);
                            } catch (IOException io) {
                                io.printStackTrace();
                            }
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                warningText.setText("Enter a valid number"); //warning text
            }
        } else {
            warningText.setText("Both fields must be filled!"); //warning text
        }
    }

    //back method goes to the last menu
    @FXML
    public void Back() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MainScreenOfficeManager.fxml")); //load office manager fxml menu file.
            Stage stage = (Stage) DomesticRate.getScene().getWindow();
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
        } catch (IOException io) {
            io.printStackTrace();
        }
    }
}
