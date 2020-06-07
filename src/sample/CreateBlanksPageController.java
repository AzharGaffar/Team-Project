package sample;

import javafx.collections.ObservableList;
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
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.*;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Collection;

public class CreateBlanksPageController {

    //Controller Links to CreateBlanksPage.fxml

    //setting up placeholders.
    public Text namePlaceholder;
    public Text staffNumberPlaceholder;
    public Text rolePlaceholder;

    //initializing all the textfields and textboxes that are on the fxml page.
    public TextField FourFourOhTextBox;
    public TextField FourTwoOhTextBox;
    public TextField TwoOhOneTextBox;
    public TextField OneOhOneTextBox;
    public TextField customType;
    public TextField CustomNumeric;
    public TextField day;
    public TextField month;
    public TextField year;

    //observable list will hold dates.
    public ObservableList<Date> oblist;

    //warning text and strings
    public Text warningText;
    String sqlQuery;
    String date;

    //make a connection to the database.
    public DatabaseConnection databaseConnection = new DatabaseConnection();
    Connection connection = databaseConnection.getConnection();

    //initialize method replaces the text with relevant information.
    @FXML
    public void initialize() {
        namePlaceholder.setText(LoginStorage.getFirstName());
        staffNumberPlaceholder.setText(LoginStorage.getStaffNumber());
        rolePlaceholder.setText(LoginStorage.getRole());
    }


    //when the submit button is pressed.
    @FXML
    public void Submit() {
        if (FourFourOhTextBox.getText().contains("-") || FourTwoOhTextBox.getText().contains("-") || TwoOhOneTextBox.getText().contains("-") || OneOhOneTextBox.getText().contains("-") || CustomNumeric.getText().contains("-")) { //check if they have entered a negative
            warningText.setText("You can't add a negative number of Blanks to the System."); //tell them they can't do that.
        } else if (FourFourOhTextBox.getText().matches("[0-9]+") || FourTwoOhTextBox.getText().matches("[0-9]+") || TwoOhOneTextBox.getText().matches("[0-9]+") || OneOhOneTextBox.getText().matches("[0-9]+") || CustomNumeric.getText().matches("[0-9]+")) { //only enter numbers.
            if (FourFourOhTextBox.getText().length() <= 8 && FourTwoOhTextBox.getText().length() <= 8 && TwoOhOneTextBox.getText().length() <= 8 && OneOhOneTextBox.getText().length() <= 8) { //make sure they don't exceed maximum number of blanks that can be added.
                if (day.getText().length() > 0 || month.getText().length() > 0 || year.getText().length() > 0) { //if day month and year filled out
                    if (ValidDateChecker(day.getText() + month.getText() + year.getText()) == true) { //if date is valid
                        date = "" + year.getText() + "" + month.getText() + "" + day.getText(); //assign to date.
                        if (FourFourOhTextBox.getText().length() != 0) {
                            freeSpaceCheckerAndInserter(Integer.parseInt(FourFourOhTextBox.getText()), date, "444", "4"); //call freespacechecker method with these paramaters
                        }
                        if (FourTwoOhTextBox.getText().length() != 0) {
                            freeSpaceCheckerAndInserter(Integer.parseInt(FourTwoOhTextBox.getText()), date, "420", "4");//call freespacechecker method with these paramaters
                        }
                        if (TwoOhOneTextBox.getText().length() != 0) {
                            freeSpaceCheckerAndInserter(Integer.parseInt(TwoOhOneTextBox.getText()), date, "201", "2");//call freespacechecker method with these paramaters
                        }
                        if (OneOhOneTextBox.getText().length() != 0) {
                            freeSpaceCheckerAndInserter(Integer.parseInt(OneOhOneTextBox.getText()), date, "101", "1");//call freespacechecker method with these paramaters
                        }
                        if (customType.getText().isEmpty() == false) {
                            if (CustomNumeric.getText().length() != 0) {
                                freeSpaceCheckerAndInserter(Integer.parseInt(CustomNumeric.getText()), date, customType.getText(), "1"); //call freespacechecker method with these paramaters
                            }
                        }
                        //make a new dialogue to inform the user that blanks have been added
                        Stage secondaryStage = new Stage();
                        Text doneText = new Text();
                        Button okButton = new Button();
                        doneText.setText("Blanks Added to System with custom Date.");
                        okButton.setText("OK");
                        secondaryStage.setTitle("Added!");
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
                                    FXMLLoader loader = new FXMLLoader(getClass().getResource("AddBlanksPage.fxml")); //go to the add blanks page.
                                    Stage stage = (Stage) FourFourOhTextBox.getScene().getWindow();
                                    Scene scene = new Scene(loader.load());
                                    stage.setScene(scene);
                                } catch (IOException io) {
                                    io.printStackTrace();
                                }

                            }
                        });
                    } else {
                        warningText.setText("That Date is not valid. Try again or leave date field empty."); //inform user date is not valid if not entered properly.
                    }
                } else {
                    //else get the date and time and use that
                    java.sql.Date sqlCurrentDate = new java.sql.Date(Calendar.getInstance().getTime().getTime());
                    date = sqlCurrentDate.toString();
                    date = date.replace("-", "");
                    if (FourFourOhTextBox.getText().length() != 0) {
                        freeSpaceCheckerAndInserter(Integer.parseInt(FourFourOhTextBox.getText()), date, "444", "4");//call freespacechecker method with these paramaters
                    }
                    if (FourTwoOhTextBox.getText().length() != 0) {
                        freeSpaceCheckerAndInserter(Integer.parseInt(FourTwoOhTextBox.getText()), date, "420", "4");//call freespacechecker method with these paramaters
                    }
                    if (TwoOhOneTextBox.getText().length() != 0) {
                        freeSpaceCheckerAndInserter(Integer.parseInt(TwoOhOneTextBox.getText()), date, "201", "2");//call freespacechecker method with these paramaters
                    }
                    if (OneOhOneTextBox.getText().length() != 0) {
                        freeSpaceCheckerAndInserter(Integer.parseInt(OneOhOneTextBox.getText()), date, "101", "1");//call freespacechecker method with these paramaters
                    }
                    if (customType.getText().isEmpty() == false) {
                        if (CustomNumeric.getText().length() != 0) {
                            freeSpaceCheckerAndInserter(Integer.parseInt(CustomNumeric.getText()), date, customType.getText(), "1");//call freespacechecker method with these paramaters
                        }
                    }
                    //inform user blanks added with current date.
                    Stage secondaryStage = new Stage();
                    Text doneText = new Text();
                    Button okButton = new Button();
                    doneText.setText("Blanks Added to System with current Date.");
                    okButton.setText("OK");
                    secondaryStage.setTitle("Added!");
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
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("AddBlanksPage.fxml")); //redirect to blanks page.
                                Stage stage = (Stage) FourFourOhTextBox.getScene().getWindow();
                                Scene scene = new Scene(loader.load());
                                stage.setScene(scene);
                            } catch (IOException io) {
                                io.printStackTrace();
                            }

                        }
                    });
                }
            } else {
                warningText.setText("The number of Blanks, you are trying to enter into the System is too high!"); //warning text
            }
        } else {
            warningText.setText("Please Only enter numbers into the Numeric Quantity Fields!");// warning text
        }
    }


    //free space checker checks if there is an empty slot in the database and inserts the blank in.
    public void freeSpaceCheckerAndInserter(int Repeater, String dateofEntry, String TypeOfBlank, String NumberOfCoupons) {
        //repeats however many times the user wanted as many blanks
        for (int i = 1; i < Repeater + 1; i++) {
            int checkerForSpace = i;
            String BlankName = Integer.toString(checkerForSpace);
            boolean search = true;
            while (search == true) {
                while (BlankName.length() != 8) {
                    BlankName = "0" + BlankName;
                } //add zeros to the name while you can
                BlankName = TypeOfBlank + BlankName;
                try {
                    Statement statement = connection.createStatement();
                    sqlQuery = "SELECT * FROM Blanks WHERE Blank = '" + BlankName + "'";
                    ResultSet resultSet = statement.executeQuery(sqlQuery);
                    if (resultSet.next() == false) {
                        try {
                            search = false;
                            sqlQuery = "INSERT INTO Blanks VALUES('" + BlankName + "','" + NumberOfCoupons + "','No','Unsold'," + dateofEntry + ", null )"; //insert into the blanks
                            Statement finalStatement = connection.createStatement();
                            finalStatement.execute(sqlQuery);
                            if (TypeOfBlank.equals("444")) {
                                for (int couponCounter = 0; couponCounter < 4; couponCounter++) {
                                    sqlQuery = "INSERT INTO Coupon(Coupon_Itinerary, Belongs_To) Values(null,'" + BlankName + "')"; //insert relevant amount of coupons
                                    finalStatement.execute(sqlQuery);
                                }
                            } else if (TypeOfBlank.equals("420")) {
                                for (int couponCounter = 0; couponCounter < 4; couponCounter++) {
                                    sqlQuery = "INSERT INTO Coupon(Coupon_Itinerary, Belongs_To) Values(null,'" + BlankName + "')";//insert relevant amount of coupons
                                    finalStatement.execute(sqlQuery);
                                }
                            } else if (TypeOfBlank.equals("201")) {
                                for (int couponCounter = 0; couponCounter < 2; couponCounter++) {
                                    sqlQuery = "INSERT INTO Coupon(Coupon_Itinerary, Belongs_To) Values(null,'" + BlankName + "')";//insert relevant amount of coupons
                                    finalStatement.execute(sqlQuery);
                                }
                            } else if (TypeOfBlank.equals("101")) {
                                sqlQuery = "INSERT INTO Coupon(Coupon_Itinerary, Belongs_To) Values(null,'" + BlankName + "')";//insert relevant amount of coupons
                                finalStatement.execute(sqlQuery);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        checkerForSpace = checkerForSpace + 1; //iterate.
                        BlankName = Integer.toString(checkerForSpace); //convert
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
    }


    //back method loads the last page.
    @FXML
    public void back() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AddBlanksPage.fxml"));
            Stage stage = (Stage) day.getScene().getWindow();
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
        } catch (IOException io) {
            io.printStackTrace();
        }
    }

    //returns true or false if date is valid. doesnt take nonsense dates.
    private static boolean ValidDateChecker(String date) {
        String formatString = "ddMMyyyy";

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
