package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.sql.Connection;
import java.sql.Statement;
import java.text.SimpleDateFormat;

public class latePaymentDateController {

    public TextField dateField;
    public Text warningText;

    DatabaseConnection databaseConnection = new DatabaseConnection();
    Connection connection = databaseConnection.getConnection();


    @FXML
    public void Submit() {
        String dateParser = dateField.getText();
        if (ValidDateChecker(dateParser) == true) {
            GenerateReportStorage.setDate2(dateParser);
            LatePaymentConfirmed.setConfimed(true);
            String blankNumber = LatePaymentConfirmed.getHolder();
            String AnotherOne = LatePaymentConfirmed.getHolder2();
            String LastOne = LatePaymentConfirmed.getHolder3();
            try {
                Statement statement = connection.createStatement();
                Statement newstatement = connection.createStatement();
                String newQuery = "UPDATE LatePayment SET Status = '" + LastOne + " Payment has been received in full as of " + GenerateReportStorage.getDate2() + "', Balance_Outstanding='0', Seen_By_OM='No' WHERE Blank_Number= '" + blankNumber + "'";
                newstatement.execute(newQuery);
                newQuery = "UPDATE Customer SET Status='" + AnotherOne + "' WHERE Alias='" + LastOne + "'"; //Customer is given their old LatePaymentStatus.
                statement.execute(newQuery);
            } catch (Exception e) {
                e.printStackTrace();
            }


        } else {
            warningText.setText("Please enter valid date");
        }
    }


    //Boolean is returned depending on if date is valid.
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
}

//    //make a new window that tells them to enter date.
//    blankNumber = LatePaymentsConfirmed.getHolder(); //store blankNumber
//                if (LatePaymentConfirmed.isConfimed() == true) {
//        try {
//            Statement statement = connection.createStatement();
//            Statement newstatement = connection.createStatement();
//            newQuery = "UPDATE LatePayment SET Status = '" + LatePaymentsTable.getSelectionModel().getSelectedItem().getOwner() + " Payment has been received in full as of " + GenerateReportStorage.getDate2() + "', Balance_Outstanding='0', Seen_By_OM='No' WHERE Blank_Number= '" + blankNumber + "'";
//            newstatement.execute(newQuery);
//            newQuery = "UPDATE Customer SET Status='" + LatePaymentsTable.getSelectionModel().getSelectedItem().getOldStatus() + "' WHERE Alias='" + LatePaymentsTable.getSelectionModel().getSelectedItem().getOwner() + "'"; //Customer is given their old LatePaymentStatus.
//            statement.execute(newQuery);
//            initialize();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//} else {
//        //else create a new window and tell them that that record has already been paid.
//        Stage secondaryStage = new Stage();
//        Text doneText = new Text();
//        Button okButton = new Button();
//        doneText.setText("That's Already been Paid!");
//        okButton.setText("OK");
//        secondaryStage.setTitle("Already Paid");
//        Pane root2 = new Pane();
//        secondaryStage.setScene(new Scene(root2, 400, 200));
//        root2.getChildren().add(doneText);
//        root2.getChildren().add(okButton);
//        doneText.setLayoutX(130);
//        doneText.setLayoutY(75);
//        okButton.setLayoutX(190);
//        okButton.setLayoutY(150);
//        secondaryStage.show();
//        secondaryStage.setResizable(false);
//        okButton.setOnAction(new EventHandler<ActionEvent>() {
//@Override
//public void handle(ActionEvent event) {
//        secondaryStage.close();
//        }
//        });
//        }
//        } else {
//        //tell the user to select a record first.
//        Stage secondaryStage = new Stage();
//        Text doneText = new Text();
//        Button newButton = new Button();
//        doneText.setText("Select something first!");
//        newButton.setText("OK");
//        secondaryStage.setTitle("Select First!");
//        Pane root2 = new Pane();
//        secondaryStage.setScene(new Scene(root2, 400, 200));
//        root2.getChildren().add(doneText);
//        root2.getChildren().add(newButton);
//        doneText.setLayoutX(150);
//        doneText.setLayoutY(75);
//        newButton.setLayoutX(190);
//        newButton.setLayoutY(150);
//        secondaryStage.show();
//        secondaryStage.setResizable(false);
//        newButton.setOnAction(new EventHandler<ActionEvent>() {
//@Override
//public void handle(ActionEvent event) {
//        secondaryStage.close();
//        }
//        });
//        }
//        LatePaymentConfirmed.setConfimed(false);
//}
