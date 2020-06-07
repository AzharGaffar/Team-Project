package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class OfficeManagerMenuController {

    //Controller Links to MainScreenOfficeManager.fxml

    //sql queries go in this string.
    public String sqlQuery;

    //establish a connection to the database.
    DatabaseConnection databaseConnection = new DatabaseConnection();
    Connection connection = databaseConnection.getConnection();

    //Text from FXML File placeholders.
    public Text namePlaceholder;
    public Text staffNumberPlaceholder;
    public Text rolePlaceholder;

    //initialize is first method to be called.
    @FXML
    public void initialize() {
        //replace the placeholder fields.
        namePlaceholder.setText(LoginStorage.getFirstName());
        staffNumberPlaceholder.setText(LoginStorage.getStaffNumber());
        rolePlaceholder.setText(LoginStorage.getRole());

        //if this is their first login.
        if (LoginStorage.getFirstLogin() == true) {
            try {
                sqlQuery = "SELECT Blank_Number,Status FROM LatePayment WHERE Seen_By_OM='No'"; //get all the Not seen by Office Manager records.
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(sqlQuery);
                LoginStorage.setFirstLogin(false); //set first login to false now.
                while (resultSet.next() != false) {
                    String status = resultSet.getString("Status"); //get the status and store it.
                    String blankNumber = resultSet.getString("Blank_Number"); //get the blank number and store it.
                    Stage secondaryStage = new Stage();
                    secondaryStage.setAlwaysOnTop(true); //always on top window.
                    Text doneText = new Text();
                    Button okButton = new Button();
                    doneText.setText(status);
                    okButton.setText("OK");
                    secondaryStage.setTitle("Notification!");
                    Pane root2 = new Pane();
                    secondaryStage.setScene(new Scene(root2, 800, 200));
                    root2.getChildren().add(doneText);
                    root2.getChildren().add(okButton);
                    doneText.setLayoutX(200);
                    doneText.setLayoutY(75);
                    okButton.setLayoutX(400);
                    okButton.setLayoutY(150);
                    secondaryStage.show();
                    secondaryStage.setResizable(false);
                    okButton.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            try {
                                sqlQuery = "UPDATE LatePayment SET Seen_By_OM='Yes' WHERE Blank_Number='" + blankNumber + "'"; //update the late payment
                                statement.execute(sqlQuery);
                                secondaryStage.close(); //close window
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    @FXML
    public void AlterRecord() {
        //To be done.
    }

    //Load Assign blanks page.
    @FXML
    public void AssignBlanks() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AssignmentOfBlanks.fxml")); //load assignment of blanks fxml file.
            Stage stage = (Stage) namePlaceholder.getScene().getWindow();
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
        } catch (IOException io) {
            io.printStackTrace();
        }

    }

    //Load Late Payment list
    @FXML
    public void LatePaymentList() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("LatePaymentsPage.fxml")); //Load late payment fxml file.
            Stage stage = (Stage) namePlaceholder.getScene().getWindow();
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
        } catch (IOException io) {
            io.printStackTrace();
        }
    }

    //Load the set commission page.
    @FXML
    public void ViewCommission() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CommissionRatePage.fxml")); //Load commissionRatePage.fxml
            Stage stage = (Stage) namePlaceholder.getScene().getWindow();
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
        } catch (IOException io) {
            io.printStackTrace();
        }

    }

    //Load generateReport page
    @FXML
    public void GenerateReport() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("GenerateReportsMenu.fxml")); //Load generate Reports Menu fxml.
            Stage stage = (Stage) namePlaceholder.getScene().getWindow();
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
        } catch (IOException io) {
            io.printStackTrace();
        }

    }

    //Load set currency conversion page.
    @FXML
    public void SetCurrencyConversion() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CurrencyConversionPage.fxml")); //Loads currency conversion fxml page.
            Stage stage = (Stage) namePlaceholder.getScene().getWindow();
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
        } catch (IOException io) {
            io.printStackTrace();
        }

    }

    //Load set discount page.
    @FXML
    public void SetDiscount() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("SetFlexibleDiscountPage.fxml")); //Load set flexiblediscount page.
            Stage stage = (Stage) namePlaceholder.getScene().getWindow();
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
        } catch (IOException io) {
            io.printStackTrace();
        }

    }

    //Logout
    @FXML
    public void Logout() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginPage.fxml")); //Load LoginPage.fxml
            Stage stage = (Stage) namePlaceholder.getScene().getWindow();
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
        } catch (IOException io) {
            io.printStackTrace();
        }

    }
}
