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

public class SetFlexibleDiscountController {

    //Controller Links to SetFlexibleDiscountPage.fxml

    //placeholder that is soon to be replaced.
    public Text namePlaceholder;
    public Text staffNumberPlaceholder;
    public Text rolePlaceholder;

    //text fields on the fxml file
    public TextField lessthousand;
    public TextField between;
    public TextField moretwothousand;

    //warning text.
    public Text warningText;

    //storage.
    String sqlQuery;
    ResultSet resultSet;
    String lessThousandHolder;
    String betweenHolder;
    String moreTwoThousandHolder;

    //etablish a connection to the database.
    DatabaseConnection databaseConnection = new DatabaseConnection();
    Connection connection = databaseConnection.getConnection();

    //initialize method first method to be called.
    @FXML
    public void initialize() {

        //placeholders replaced.
        namePlaceholder.setText(LoginStorage.getFirstName());
        staffNumberPlaceholder.setText(LoginStorage.getStaffNumber());
        rolePlaceholder.setText(LoginStorage.getRole());

        try {
            sqlQuery = "SELECT Less_Than_Thousand FROM FlexibleDiscount";
            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery(sqlQuery);

            while (resultSet.next()) {
                lessThousandHolder = resultSet.getString("Less_Than_Thousand"); //storage
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            sqlQuery = "SELECT BetweenThese FROM FlexibleDiscount";
            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery(sqlQuery);

            while (resultSet.next()) {
                betweenHolder = resultSet.getString("BetweenThese"); //storage
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            sqlQuery = "SELECT More_Than_Two_Thousand FROM FlexibleDiscount";
            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery(sqlQuery);

            while (resultSet.next()) {
                moreTwoThousandHolder = resultSet.getString("More_Than_Two_Thousand"); //storage
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Setting storage as prompt text.
        lessthousand.setPromptText(lessThousandHolder);
        between.setPromptText(betweenHolder);
        moretwothousand.setPromptText(moreTwoThousandHolder);
    }

    //Update button
    @FXML
    public void Update() {
        if (lessthousand.getText().isEmpty() == false && between.getText().isEmpty() == false && moretwothousand.getText().isEmpty() == false) { //if none of the fields are empty
            if (lessthousand.getText().matches("[0-9]+") && between.getText().matches("[0-9]+") && moretwothousand.getText().matches("[0-9]+")) { //if only numbers entered.
                try {
                    sqlQuery = "DELETE FROM FlexibleDiscount"; //delete old
                    Statement statement = connection.createStatement();
                    statement.execute(sqlQuery);
                    sqlQuery = "INSERT INTO FlexibleDiscount(Less_Than_Thousand ,BetweenThese, More_Than_Two_Thousand) VALUES('" + lessthousand.getText() + "','" + between.getText() + "','"+moretwothousand.getText()+"')"; //insert new.
                    statement.execute(sqlQuery);
                    //tell the user done.
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
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("MainScreenOfficeManager.fxml")); //load old screen office manager
                                Stage stage = (Stage) lessthousand.getScene().getWindow();
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
                warningText.setText("Enter a valid number");
            }
        } else {
            warningText.setText("All fields must be filled!");
        }
    }

    //back method calls the last page
    @FXML
    public void Back(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MainScreenOfficeManager.fxml")); //main screen office manager.
            Stage stage = (Stage) lessthousand.getScene().getWindow();
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
        } catch (IOException io) {
            io.printStackTrace();
        }
    }
}

