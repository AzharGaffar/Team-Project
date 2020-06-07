package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SystemAdministratorMenuController {

    //Controller Links to MainScreenSystemAdministrator.fxml

    //placeholders on top left of fxml page.
    public Text namePlaceholder;
    public Text staffNumberPlaceholder;
    public Text rolePlaceholder;

    //initialize is first method to be called.
    @FXML
    public void initialize() {

        //replace placeholders with relevant details
        namePlaceholder.setText(LoginStorage.getFirstName());
        staffNumberPlaceholder.setText(LoginStorage.getStaffNumber());
        rolePlaceholder.setText(LoginStorage.getRole());
    }

    //load addblankspage.fxml
    @FXML
    public void AddBlanks() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AddBlanksPage.fxml"));
            Stage stage = (Stage) namePlaceholder.getScene().getWindow();
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
        } catch (IOException io) {
            io.printStackTrace();
        }
    }

    //load maintenance of database page.
    @FXML
    public void Maintenance() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MaintenanceOfDatabasePage.fxml"));
            Stage stage = (Stage) namePlaceholder.getScene().getWindow();
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
        } catch (IOException io) {
            io.printStackTrace();
        }

    }

    @FXML
    public void setAutoBackup() {
        //to be done
    }

    @FXML
    public void UpdateTicket() {
        //to be done.

    }

    //loads a get date page for user to enter date
    @FXML
    public void GenerateStockTurnover() {
        Stage secondaryStage = new Stage();
        Text doneText = new Text();
        Button okButton = new Button();
        TextField date1 = new TextField();
        TextField date2 = new TextField();
        Text warningText = new Text();
        warningText.setFont(new Font("Segoe UI", 13));
        warningText.setFill(Color.RED);
        date1.setPromptText("DD/MM/YYYY");
        date2.setPromptText("DD/MM/YYYY");
        doneText.setText("Enter Dates Period of Report");
        okButton.setText("Generate");
        secondaryStage.setTitle("Enter Date");
        Pane root2 = new Pane();
        secondaryStage.setScene(new Scene(root2, 400, 200));
        root2.getChildren().add(doneText);
        root2.getChildren().add(okButton);
        root2.getChildren().add(date1);
        root2.getChildren().add(date2);
        root2.getChildren().add(warningText);
        doneText.setLayoutX(150);
        doneText.setLayoutY(75);
        okButton.setLayoutX(190);
        okButton.setLayoutY(150);
        warningText.setLayoutX(150);
        warningText.setLayoutY(140);
        date1.setLayoutX(30);
        date1.setLayoutY(90);
        date2.setLayoutX(190);
        date2.setLayoutY(90);
        secondaryStage.show();
        secondaryStage.setResizable(false);
        okButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    if (date1.getText().isEmpty() == false && date2.getText().isEmpty() == false) { //if date is not empty.
                        if (ValidDateChecker(date1.getText()) == true) { //check if date is valid
                            if (ValidDateChecker(date2.getText()) == true) {//check if date is valid
                                Date dateNumber1 = sdf.parse(date1.getText());
                                Date dateNumber2 = sdf.parse(date2.getText());
                                sdf.applyPattern("yyyy/MM/dd");
                                String finalDate1 = sdf.format(dateNumber1);
                                String finalDate2 = sdf.format(dateNumber2);
                                GenerateReportStorage.setDate1(finalDate1);
                                GenerateReportStorage.setDate2(finalDate2);
                                try {
                                    FXMLLoader loader = new FXMLLoader(getClass().getResource("StockReport.fxml")); //load stock report page.
                                    Stage stage = (Stage) namePlaceholder.getScene().getWindow();
                                    Scene scene = new Scene(loader.load());
                                    stage.setScene(scene);
                                } catch (IOException io) {
                                    io.printStackTrace();
                                }
                                secondaryStage.close();
                            } else {
                                warningText.setText("Please enter a valid date");
                            }
                        } else {
                            warningText.setText("Please enter a valid date");
                        }
                    } else {
                        warningText.setText("Please fill in the fields!");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        });
    }

    //Loads Update customerstatus page
    @FXML
    public void UpdateCustomer() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("UpdateCustomerStatus.fxml")); //load customer status .fxml page
            Stage stage = (Stage) namePlaceholder.getScene().getWindow();
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
        } catch (IOException io) {
            io.printStackTrace();
        }

    }

    //loads staff member table page
    @FXML
    public void AddStaffMember() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("StaffMemberTable.fxml"));
            Stage stage = (Stage) namePlaceholder.getScene().getWindow();
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
        } catch (IOException io) {
            io.printStackTrace();
        }

    }

    @FXML
    public void UpdateContact() {
        //to be done
    }

    //Checks and returns a boolean if date is valid.
    private static boolean ValidDateChecker(String date) {
        String formatString = "dd/MM/yyyy";
        try {
            SimpleDateFormat formatOfDate = new SimpleDateFormat(formatString);
            formatOfDate.setLenient(false);
            formatOfDate.parse(date);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    //Loads Login page
    @FXML
    public void Logout() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginPage.fxml")); //Loads login page.fxml
            Stage stage = (Stage) namePlaceholder.getScene().getWindow();
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
        } catch (IOException io) {
            io.printStackTrace();
        }

    }
}
