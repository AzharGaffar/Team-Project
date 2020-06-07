package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Sell101Controller {

    //Controller Links to Sell101.fxml

    //Boolean for continue statement given initial value of false.
    public boolean continueStatement = false;

    //Text in the fxml file that will be replaced.
    public Text namePlaceholder;
    public Text staffNumberPlaceholder;
    public Text rolePlaceholder;

    //Textfields and text that is in the fxml file.
    public TextField day;
    public TextField month;
    public TextField year;
    public TextField firstCoupon;
    public TextField localCurrency;
    public TextField taxLocal;
    public Text creditCardLabel;
    public TextField creditCardField;
    public Text warningText;

    //customer integer storage.
    public int CustomerNumber;

    //storage
    public String valuedStatus;
    public String customerUsername;
    public int couponIDStorage;
    public String latePaymentCustomer;

    //Strings and doubles.
    public String BlankNumber;
    public Double Fare_USD;
    public String Fare_USDString;
    public Double Conversion_Rate;
    public String Conversion_RateString;
    public Double Fare_Local;
    public String Fare_LocalString;
    public Double Tax_LZ;
    public String Tax_LZString;
    public Double Tax_OTHS;
    public String Tax_OTHSString;
    public Double Total_Amount;
    public String Total_AmountString;
    public String CC_number;
    public Double Total_Paid;
    public String Total_PaidString;
    public Double Commission_Rate;
    public String Commission_RateString;
    public String Sold_By_TA;
    public String Blank_Type;
    public int Sold_To;
    public Date Date_Of_Sale;
    public String date;

    //List view for customer list.
    public ListView<String> customerList = new ListView<>();

    //DropDown for cash or credit.
    public ComboBox<String> cashOrCredit;

    //sql query strings and result sets.
    public String sqlQuery, InsertQuery, CouponQuery, LatePaymentQuery;
    public ResultSet resultSet;

    //establish a connection to the database.
    DatabaseConnection databaseConnection = new DatabaseConnection();
    Connection connection = databaseConnection.getConnection();

    //initialzie method is the first thing to be called.
    @FXML
    public void initialize() {

        //replace the placeholders.
        namePlaceholder.setText(LoginStorage.getFirstName());
        staffNumberPlaceholder.setText(LoginStorage.getStaffNumber());
        rolePlaceholder.setText(LoginStorage.getRole());

        try {
            sqlQuery = "SELECT Alias FROM Customer"; //select all the alias.
            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery(sqlQuery);

            while (resultSet.next()) {
                customerUsername = resultSet.getString("Alias"); //storing the alias
                customerList.getItems().addAll(customerUsername); //adding to customer list.
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        creditCardField.setVisible(false); //creditcardfield is invisible
        creditCardLabel.setVisible(false);//creditcardlabel is invisible

        cashOrCredit.setPromptText("Cash or Credit?"); //prompt text
        cashOrCredit.getItems().setAll("Cash", "Credit"); //add options
    }

    //Submit button method.
    @FXML
    public void Submit() {
        if (customerList.getSelectionModel().getSelectedItem() != null) { //check if they have selected a customer
            if (localCurrency.getText().isEmpty() == false) { //if local currency is not empty.
                try {
                    sqlQuery = "SELECT CustomerID FROM Customer WHERE Alias='" + customerList.getSelectionModel().getSelectedItem() + "'"; //select from customer where alias is, get the selected customer.
                    Statement statement = connection.createStatement();
                    resultSet = statement.executeQuery(sqlQuery);
                    while (resultSet.next()) {
                        CustomerNumber = resultSet.getInt("CustomerID"); //Set customer number to that customer id.
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (firstCoupon.getText().isEmpty() == false) { //make sure coupon fields are not empty.
                    if (taxLocal.getText().isEmpty() == false) { //make sure tax fields are not empty.
                        if (cashOrCredit.getSelectionModel().getSelectedItem() != null) { //make sure cash or credit has been selected.
                            if (day.getText().isEmpty() && month.getText().isEmpty() && year.getText().isEmpty()) { //if date field is empty
                                java.sql.Date sqlCurrentDate = new java.sql.Date(Calendar.getInstance().getTime().getTime()); //use the current date.
                                date = sqlCurrentDate.toString();
                                date = date.replace("-", "");
                                if (cashOrCredit.getValue().equals("Cash")) { //if cash selected.
                                    try {
                                        continueStatement = true;
                                        InsertQuery = "UPDATE Blanks SET Status = 'Sold' WHERE Blank ='" + BlankStorage.getBlankNumber() + "'"; //update blank sold status
                                        Statement statement = connection.createStatement();
                                        statement.execute(InsertQuery);
                                        calculator();
                                        InsertQuery = "INSERT INTO Sold(Blank_Number, Fare_USD, Conversion_Rate, Fare_Local, Tax_LZ, Tax_OTHS, Total_Amount, Cash_Amount, CC_Number, Total_Paid, Commission_Rate, Sold_By_TA, Blank_Type, Sold_To, Date_Of_Sale) VALUES('" + BlankNumber + "','" + Fare_USDString + "','" + Conversion_RateString + "','" + Fare_LocalString + "','" + Tax_LZString + "','" + Tax_OTHSString + "','" + Total_AmountString + "','" + Total_AmountString + "',null,'" + Total_PaidString + "','" + Commission_RateString + "','" + Sold_By_TA + "','" + Blank_Type + "','" + Sold_To + "'," + date + ")";
                                        //insert into sold.
                                        statement.execute(InsertQuery);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                } else if (cashOrCredit.getValue().equals("Credit")) { //if credit selected.
                                    if (creditCardField.getText().isEmpty() == false) { //make sure not empty.
                                        continueStatement = true; //continue is true.
                                        try {
                                            InsertQuery = "UPDATE Blanks SET Status = 'Sold' WHERE Blank ='" + BlankStorage.getBlankNumber() + "'"; //update blank sold status.
                                            Statement statement = connection.createStatement();
                                            statement.execute(InsertQuery);
                                            calculator();
                                            InsertQuery = "INSERT INTO Sold(Blank_Number, Fare_USD, Conversion_Rate, Fare_Local, Tax_LZ, Tax_OTHS, Total_Amount, Cash_Amount, CC_Number, Total_Paid, Commission_Rate, Sold_By_TA, Blank_Type, Sold_To, Date_Of_Sale) VALUES('" + BlankNumber + "','" + Fare_USDString + "','" + Conversion_RateString + "','" + Fare_LocalString + "','" + Tax_LZString + "','" + Tax_OTHSString + "','" + Total_AmountString + "',null,'" + creditCardField.getText() + "','" + Total_PaidString + "','" + Commission_RateString + "','" + Sold_By_TA + "','" + Blank_Type + "','" + Sold_To + "'," + date + ")";
                                            //insert into sold table.
                                            statement.execute(InsertQuery);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    } else {
                                        warningText.setText("Fill in your card details");
                                    }
                                }
                            } else {
                                date = year.getText() + month.getText() + day.getText(); //else get the year month and day
                                if (ValidDateChecker(date) == true) { //check if valid date.
                                    continueStatement = true; //continue statement is true
                                    if (cashOrCredit.getValue().equals("Cash")) {
                                        try {
                                            InsertQuery = "UPDATE Blanks SET Status = 'Sold' WHERE Blank ='" + BlankStorage.getBlankNumber() + "'"; //updaet sold status.
                                            Statement statement = connection.createStatement();
                                            statement.execute(InsertQuery);
                                            calculator();
                                            InsertQuery = "INSERT INTO Sold(Blank_Number, Fare_USD, Conversion_Rate, Fare_Local, Tax_LZ, Tax_OTHS, Total_Amount, Cash_Amount, CC_Number, Total_Paid, Commission_Rate, Sold_By_TA, Blank_Type, Sold_To, Date_Of_Sale) VALUES('" + BlankNumber + "','" + Fare_USDString + "','" + Conversion_RateString + "','" + Fare_LocalString + "','" + Tax_LZString + "','" + Tax_OTHSString + "','" + Total_AmountString + "','" + Total_AmountString + "',null,'" + Total_PaidString + "','" + Commission_RateString + "','" + Sold_By_TA + "','" + Blank_Type + "','" + Sold_To + "'," + date + ")";
                                            //insert with custom date.
                                            statement.execute(InsertQuery);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    } else if (cashOrCredit.getValue().equals("Credit")) { //if credit
                                        if (creditCardField.getText().isEmpty() == false) { //credit card field is not empty.
                                            continueStatement = true;
                                            try {
                                                InsertQuery = "UPDATE Blanks SET Status = 'Sold' WHERE Blank ='" + BlankStorage.getBlankNumber() + "'";
                                                //update sold status.
                                                Statement statement = connection.createStatement();
                                                statement.execute(InsertQuery);
                                                calculator();
                                                InsertQuery = "INSERT INTO Sold(Blank_Number, Fare_USD, Conversion_Rate, Fare_Local, Tax_LZ, Tax_OTHS, Total_Amount, Cash_Amount, CC_Number, Total_Paid, Commission_Rate, Sold_By_TA, Blank_Type, Sold_To, Date_Of_Sale) VALUES('" + BlankNumber + "','" + Fare_USDString + "','" + Conversion_RateString + "','" + Fare_LocalString + "','" + Tax_LZString + "','" + Tax_OTHSString + "','" + Total_AmountString + "',null,'" + creditCardField.getText() + "','" + Total_PaidString + "','" + Commission_RateString + "','" + Sold_By_TA + "','" + Blank_Type + "','" + Sold_To + "'," + date + ")";
                                                //insert into sold table.
                                                statement.execute(InsertQuery);
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }
                                } else {
                                    warningText.setText("Enter a valid date or leave blank."); //warning text.
                                    continueStatement = false;
                                }
                            }

                        } else {
                            warningText.setText("You haven't selected cash or credit"); //warning text.
                            continueStatement = false;
                        }
                    } else {
                        warningText.setText("You Haven't filled in all the fields"); //warning text.
                        continueStatement = false;
                    }
                } else {
                    warningText.setText("You haven't filled in all the fields"); //warning text.
                    continueStatement = false;

                }
            } else {
                warningText.setText("Fill in the Local Currency!"); //warning text.
                continueStatement = false;
            }
        } else {
            warningText.setText("Select a Customer");
            continueStatement = false;
        }
        if (continueStatement == true) {
            try {
                InsertQuery = "SELECT * FROM Coupon WHERE Belongs_To='" + BlankStorage.getBlankNumber() + "'"; //inserting the coupon information to the coupon table
                Statement statement = connection.createStatement();
                ResultSet finalResultSet = statement.executeQuery(InsertQuery);
                int counter = 1;
                while (finalResultSet.next()) {
                    Statement newStatement = connection.createStatement();
                    couponIDStorage = finalResultSet.getInt("CouponID");
                    if (counter == 1) {
                        CouponQuery = "UPDATE Coupon SET Coupon_Itinerary='" + firstCoupon.getText() + "' WHERE CouponID=" + couponIDStorage;//Update coupons
                    }
                    newStatement.execute(CouponQuery);
                    counter++;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                //store status and alias.
                LatePaymentQuery = "SELECT Alias, Status FROM Customer WHERE CustomerID=" + CustomerNumber;
                Statement statement = connection.createStatement();
                resultSet = statement.executeQuery(LatePaymentQuery);

                while (resultSet.next()) {
                    valuedStatus = resultSet.getString("Status");
                    latePaymentCustomer = resultSet.getString("Alias");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            //check their status and display a window if they want a late payment.
            if (!valuedStatus.equals("Casual")) {
                Stage secondaryStage = new Stage();
                secondaryStage.setAlwaysOnTop(true);
                secondaryStage.setTitle("Late Payment?");
                Text confirmationMessage = new Text();
                Text warningMessage = new Text();
                confirmationMessage.setText("Do you want this sale to be a late payment?");
                confirmationMessage.setLayoutX(75);
                confirmationMessage.setLayoutY(80);
                Button acceptButton = new Button();
                Button declineButton = new Button();
                declineButton.setOnAction(new EventHandler<ActionEvent>() {

                    @Override
                    public void handle(ActionEvent event) {
                        secondaryStage.close();
                    } //if not close window.
                });
                acceptButton.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        //if they do
                        String customerStatus = null;
                        try {
                            Statement statement = connection.createStatement();
                            sqlQuery = "SELECT Status FROM Customer WHERE Alias='" + latePaymentCustomer + "'";
                            resultSet = statement.executeQuery(sqlQuery);
                            while (resultSet.next() != false) {
                                customerStatus = resultSet.getString("Status");
                            }
                            LatePaymentQuery = "INSERT INTO LatePayment(Blank_Number, Owner, Status, Date_of_Sale, Due_Date, Balance_Outstanding, Old_Status, Seen_By_OM) VALUES('" + BlankStorage.getBlankNumber() + "','" + latePaymentCustomer + "','" + latePaymentCustomer + " is yet to make a payment. But may purchase tickets as a casual customer'," + date + ",DATE_ADD('" + date + "' ,INTERVAL 30 DAY),'" + Total_AmountString + "','" + customerStatus + "','No')";
                            //insert into late payments table.
                            statement.execute(LatePaymentQuery);
                            sqlQuery = "UPDATE Customer SET Status = 'Casual' WHERE Alias='" + latePaymentCustomer + "'";
                            statement.execute(sqlQuery);
                            afterChecker(BlankStorage.getBlankNumber()); //call checker to see if they have already exceeded their allowed time.
                            secondaryStage.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

                declineButton.setText("No, Take me back!");
                acceptButton.setText("Yes");
                Pane root1 = new Pane();
                root1.getChildren().add(acceptButton);
                root1.getChildren().add(declineButton);
                root1.getChildren().add(confirmationMessage);
                root1.getChildren().add(warningMessage);
                declineButton.setLayoutX(70);
                declineButton.setLayoutY(120);
                acceptButton.setLayoutX(275);
                acceptButton.setLayoutY(120);

                secondaryStage.setScene(new Scene(root1, 400, 200));
                secondaryStage.show();
                secondaryStage.setResizable(false);
            }
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("SellBlanksPage.fxml")); //Load sell blanks fxml file.
                Stage stage = (Stage) localCurrency.getScene().getWindow();
                Scene scene = new Scene(loader.load());
                stage.setScene(scene);
            } catch (IOException io) {
                io.printStackTrace();
            }
        }
    }


    //setting the card label visible
    @FXML
    public void selection() {
        if (cashOrCredit.getValue().equals("Credit")) {
            creditCardLabel.setVisible(true);
            creditCardField.setVisible(true);
        } else if (cashOrCredit.getValue().equals("Cash")) {
            creditCardLabel.setVisible(false);
            creditCardField.setVisible(false);
        }
    }

    //Calculates all the relevant information that goes into the sales table.
    public void calculator() {
        BlankNumber = BlankStorage.getBlankNumber();
        try {
            sqlQuery = "SELECT Current_Currency FROM Currency";
            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery(sqlQuery);
            while (resultSet.next()) {
                Conversion_Rate = Double.parseDouble(resultSet.getString("Current_Currency"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Fare_Local = Double.parseDouble(localCurrency.getText());
        Fare_USD = Fare_Local / Conversion_Rate;
        Tax_LZ = Double.parseDouble(taxLocal.getText());
        Tax_OTHS = Double.parseDouble("0");
        Total_Amount = Fare_Local + Tax_LZ + Tax_OTHS;
        Total_Paid = Total_Amount;
        try {
            sqlQuery = "SELECT Domestic FROM Commission";
            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery(sqlQuery);
            while (resultSet.next()) {
                Commission_Rate = Double.parseDouble(resultSet.getString("Domestic"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Sold_By_TA = LoginStorage.getStaffNumber();
        Blank_Type = "Domestic";
        Sold_To = CustomerNumber;
        Fare_USDString = Fare_USD.toString();
        Conversion_RateString = Conversion_Rate.toString();
        Fare_LocalString = Fare_Local.toString();
        Tax_LZString = Tax_LZ.toString();
        Tax_OTHSString = Tax_OTHS.toString();
        Total_AmountString = Total_Amount.toString();
        Total_PaidString = Total_Paid.toString();
        Commission_RateString = Commission_Rate.toString();
    }

    //Checks if the date has passed.
    private void afterChecker(String BlankNumber) {
        String StringDueDate = null;
        String sqlCurrentDate = new java.sql.Date(Calendar.getInstance().getTime().getTime()).toString();
        try {
            sqlQuery = "SELECT Due_Date FROM LatePayment WHERE Blank_Number ='" + BlankNumber + "'";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlQuery);
            while (resultSet.next() != false) {
                StringDueDate = resultSet.getString("Due_Date");
            }
            SimpleDateFormat formatOfDate = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date finalDueDate = formatOfDate.parse(StringDueDate);
            java.util.Date finalCurrentDate = formatOfDate.parse(sqlCurrentDate);
            if (finalCurrentDate.after(finalDueDate)) {
                sqlQuery = "UPDATE LatePayment SET Status='" + latePaymentCustomer + " failed to pay in 30 days period.', Seen_By_OM='No' WHERE Blank_Number='" + BlankNumber + "'"; //if date has update the status of the late payment.
                statement.execute(sqlQuery);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //check if date is valid.
    private static boolean ValidDateChecker(String date) {
        String formatString = "yyyyMMdd";

        try {
            SimpleDateFormat formatOfDate = new SimpleDateFormat(formatString);
            formatOfDate.setLenient(false);
            formatOfDate.parse(date);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    //Back method loads the last blank page.
    @FXML
    public void Back() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("SellBlanksPage.fxml"));
            Stage stage = (Stage) namePlaceholder.getScene().getWindow();
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
        } catch (IOException io) {
            io.printStackTrace();
        }
    }
}
