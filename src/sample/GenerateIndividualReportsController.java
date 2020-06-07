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

public class GenerateIndividualReportsController {

    //placeholders will be replaced.
    public Text namePlaceholder;
    public Text staffNumberPlaceholder;
    public Text rolePlaceholder;

    //first thing to be called, initialize removes placeholders and replaces with relevant information.
    @FXML
    public void initialize() {
        namePlaceholder.setText(LoginStorage.getFirstName());
        staffNumberPlaceholder.setText(LoginStorage.getStaffNumber());
        rolePlaceholder.setText(LoginStorage.getRole());
    }

    //generate interline report
    @FXML
    public void Interline() {
        //enter date
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
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy"); //check date format
                    if (date1.getText().isEmpty() == false && date2.getText().isEmpty() == false) { //make sure not empty
                        if (ValidDateChecker(date1.getText()) == true) { //if 1st valid
                            if (ValidDateChecker(date2.getText()) == true) { //if 2nd valid
                                Date dateNumber1 = sdf.parse(date1.getText()); //storage
                                Date dateNumber2 = sdf.parse(date2.getText());//storage
                                sdf.applyPattern("yyyy/MM/dd");//prep for converting the dates into another format
                                String finalDate1 = sdf.format(dateNumber1); //convert the dates into another format
                                String finalDate2 = sdf.format(dateNumber2);//convert the dates into another format
                                GenerateReportStorage.setDate1(finalDate1); //store in static class
                                GenerateReportStorage.setDate2(finalDate2); //store in static class
                                try {
                                    FXMLLoader loader = new FXMLLoader(getClass().getResource("IndividualInterline.fxml")); //load individualinterlinereport
                                    Stage stage = (Stage) namePlaceholder.getScene().getWindow();
                                    Scene scene = new Scene(loader.load());
                                    stage.setScene(scene);
                                } catch (IOException io) {
                                    io.printStackTrace();
                                }
                                secondaryStage.close();
                            } else {
                                warningText.setText("Please enter a valid date"); //warning text
                            }
                        } else {
                            warningText.setText("Please enter a valid date");//warning text
                        }
                    } else {
                        warningText.setText("Please fill in the fields!");//warning text
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        });
    }


    /*PLEASE NOTE GENERATE DOMESTIC DOES THE SAME THING BUT LINKS TO THE DOMESTIC REPORT PAGE*/
    @FXML
    public void Domestic() {
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
                    if (date1.getText().isEmpty() == false && date2.getText().isEmpty() == false) {
                        if (ValidDateChecker(date1.getText()) == true) {
                            if (ValidDateChecker(date2.getText()) == true) {
                                Date dateNumber1 = sdf.parse(date1.getText());
                                Date dateNumber2 = sdf.parse(date2.getText());
                                sdf.applyPattern("yyyy/MM/dd");
                                String finalDate1 = sdf.format(dateNumber1);
                                String finalDate2 = sdf.format(dateNumber2);
                                GenerateReportStorage.setDate1(finalDate1);
                                GenerateReportStorage.setDate2(finalDate2);
                                try {
                                    FXMLLoader loader = new FXMLLoader(getClass().getResource("IndividualDomestic.fxml"));
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

    //returns boolean if the date is legit.
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


    //back button goes back to the main screen of the Travel Advisor.
    @FXML
    public void Back() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MainScreenTravelAdvisor.fxml"));
            Stage stage = (Stage) namePlaceholder.getScene().getWindow();
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
        } catch (IOException io) {
            io.printStackTrace();
        }
    }


}
