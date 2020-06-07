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

public class CurrencyConversionController {

    //Controller Links to CurrrencyConversionPage.fxml

    //set up placeholders.
    public Text namePlaceholder;
    public Text staffNumberPlaceholder;
    public Text rolePlaceholder;

    //set up textfields and warning text and resultstrings.
    public TextField Currency;
    String CurrencyHolder; //will hold previous currency
    String sqlQuery;
    public Text warningText;
    ResultSet resultSet;

    //establish connection to the database so sql queries can go through.
    DatabaseConnection databaseConnection = new DatabaseConnection();
    Connection connection = databaseConnection.getConnection();

    //initialize method is the first method to be called.
    @FXML
    public void initialize() {

        //replace with the relevant information in the top left corner.
        namePlaceholder.setText(LoginStorage.getFirstName());
        staffNumberPlaceholder.setText(LoginStorage.getStaffNumber());
        rolePlaceholder.setText(LoginStorage.getRole());

        try {
            sqlQuery = "SELECT Current_Currency FROM Currency"; //select current currency
            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery(sqlQuery);

            while (resultSet.next()) {
                CurrencyHolder = resultSet.getString("Current_Currency"); //put it in the holder for a prompt text.
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        Currency.setPromptText(CurrencyHolder); //set prompt text.
    }

    //update currency.
    @FXML
    public void Update() {
        if (Currency.getText().matches("[0-9]+(\\.[0-9][0-9]?)?")) { //check if only numbers entered.
            try {
                sqlQuery = "DELETE FROM Currency";
                Statement statement = connection.createStatement();
                statement.execute(sqlQuery);
                sqlQuery = "INSERT INTO Currency(Current_Currency) VALUES('" + Currency.getText() + "')"; //new currency insert.
                statement.execute(sqlQuery); //execute
                //inform user.
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
                        if (LoginStorage.getRole().equals("Office Manager")) { //if office manager was the one that did it redirect them to their page.
                            try {
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("MainScreenOfficeManager.fxml"));
                                Stage stage = (Stage) Currency.getScene().getWindow();
                                Scene scene = new Scene(loader.load());
                                stage.setScene(scene);
                            } catch (IOException io) {
                                io.printStackTrace();
                            }
                        } else if (LoginStorage.getRole().equals("Travel Advisor")) {//if travel advisor was the one that did it redirect them to their page.
                            try {
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("MainScreenTravelAdvisor.fxml"));
                                Stage stage = (Stage) Currency.getScene().getWindow();
                                Scene scene = new Scene(loader.load());
                                stage.setScene(scene);
                            } catch (IOException io) {
                                io.printStackTrace();
                            }
                        }
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            warningText.setText("Enter a valid decimal"); //else tell user to enter valid decimal.
        }
    }


    //back button decides if office manager or travel advisor logged in and which page they should return to.
    @FXML
    public void Back() {
        if (LoginStorage.getRole().equals("Office Manager")) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("MainScreenOfficeManager.fxml")); //load office manager main menu
                Stage stage = (Stage) Currency.getScene().getWindow();
                Scene scene = new Scene(loader.load());
                stage.setScene(scene);
            } catch (IOException io) {
                io.printStackTrace();
            }
        } else if (LoginStorage.getRole().equals("Travel Advisor")) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("MainScreenTravelAdvisor.fxml")); //load travel advisor main menu
                Stage stage = (Stage) Currency.getScene().getWindow();
                Scene scene = new Scene(loader.load());
                stage.setScene(scene);
            } catch (IOException io) {
                io.printStackTrace();
            }
        }
    }
}
