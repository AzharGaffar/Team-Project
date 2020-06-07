package sample;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Sell444Controller {

    //Controller Links to Sell444.fxml

    //boolean continue statement
    public boolean continueStatement = false;

    //placeholders for the top left/
    public Text namePlaceholder;
    public Text staffNumberPlaceholder;
    public Text rolePlaceholder;

    //All text and textfields on the fxml page.
    public TextField day;
    public TextField month;
    public TextField year;
    public TextField firstCoupon;
    public TextField secondCoupon;
    public TextField thirdCoupon;
    public TextField fourthCoupon;
    public TextField localCurrency;
    public TextField taxLocal;
    public TextField taxOther;
    public Text creditCardLabel;
    public TextField creditCardField;
    public Text warningText;
    public TextField usdCurrency;

    //storage
    public int CustomerNumber;
    //storage
    public String valuedStatus;
    public String customerUsername;
    public String latePaymentCustomer;
    public int couponIDStorage;

    //Strings on the fxml file and doubles as storage.
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


    //List view for customers.
    public ListView<String> customerList = new ListView<>();

    //drop down for cash or credit.
    public ComboBox<String> cashOrCredit;

    //prepare sql queries and storage.
    public String sqlQuery, InsertQuery, CouponQuery, LatePaymentQuery;
    public ResultSet resultSet;

    //get connnection to database.
    DatabaseConnection databaseConnection = new DatabaseConnection();
    Connection connection = databaseConnection.getConnection();

    //Initalize method is called fist.
    @FXML
    public void initialize() {

        //replace placeholders with the relevant information.
        namePlaceholder.setText(LoginStorage.getFirstName());
        staffNumberPlaceholder.setText(LoginStorage.getStaffNumber());
        rolePlaceholder.setText(LoginStorage.getRole());

        try {
            sqlQuery = "SELECT Alias FROM Customer"; //get the alias of the customer
            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery(sqlQuery);

            while (resultSet.next()) {
                customerUsername = resultSet.getString("Alias"); //storage of the alias of the customer
                customerList.getItems().addAll(customerUsername); //poplate customer list.
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        creditCardField.setVisible(false); //credit card field is invisible
        creditCardLabel.setVisible(false); //credit card label is invisible

        //Populating drop down.
        cashOrCredit.setPromptText("Cash or Credit?");
        cashOrCredit.getItems().setAll("Cash", "Credit");
    }

    //When submit button is pressed.
    @FXML
    public void Submit() {
        if (customerList.getSelectionModel().getSelectedItem() != null) { //check if customer hasnt been selected.
            if (usdCurrency.getText().isEmpty() || localCurrency.getText().isEmpty()) { // check if local currency or usd currency is empty
                try {
                    sqlQuery = "SELECT CustomerID FROM Customer WHERE Alias='" + customerList.getSelectionModel().getSelectedItem() + "'";
                    Statement statement = connection.createStatement();
                    resultSet = statement.executeQuery(sqlQuery);
                    while (resultSet.next()) {
                        CustomerNumber = resultSet.getInt("CustomerID"); //store customer id.
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (firstCoupon.getText().isEmpty() == false && secondCoupon.getText().isEmpty() == false && thirdCoupon.getText().isEmpty() == false && fourthCoupon.getText().isEmpty() == false) { //check if coupon fields are empty
                    if (taxLocal.getText().isEmpty() == false && taxOther.getText().isEmpty() == false) { //check if tax is empty.
                        if (cashOrCredit.getSelectionModel().getSelectedItem() != null) { //check if cash or credit has been selected.
                            if (day.getText().isEmpty() && month.getText().isEmpty() && year.getText().isEmpty()) { //if date empty.
                                java.sql.Date sqlCurrentDate = new java.sql.Date(Calendar.getInstance().getTime().getTime()); //use current date
                                date = sqlCurrentDate.toString();
                                date = date.replace("-", "");
                                if (cashOrCredit.getValue().equals("Cash")) { //if cash selected
                                    continueStatement = true;
                                    try {
                                        InsertQuery = "UPDATE Blanks SET Status = 'Sold' WHERE Blank ='" + BlankStorage.getBlankNumber() + "'";
                                        //update status to sold.
                                        Statement statement = connection.createStatement();
                                        statement.execute(InsertQuery);
                                        //calculate relevant values.
                                        calculator();
                                        InsertQuery = "INSERT INTO Sold(Blank_Number, Fare_USD, Conversion_Rate, Fare_Local, Tax_LZ, Tax_OTHS, Total_Amount, Cash_Amount, CC_Number, Total_Paid, Commission_Rate, Sold_By_TA, Blank_Type, Sold_To, Date_Of_Sale) VALUES('" + BlankNumber + "','" + Fare_USDString + "','" + Conversion_RateString + "','" + Fare_LocalString + "','" + Tax_LZString + "','" + Tax_OTHSString + "','" + Total_AmountString + "','" + Total_AmountString + "',null,'" + Total_PaidString + "','" + Commission_RateString + "','" + Sold_By_TA + "','" + Blank_Type + "','" + Sold_To + "'," + date + ")";
                                        //insert into sold table with current date and cash
                                        statement.execute(InsertQuery);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                } else if (cashOrCredit.getValue().equals("Credit")) { //if credit selected
                                    if (creditCardField.getText().isEmpty() == false) {
                                        continueStatement = true;
                                        try {
                                            InsertQuery = "UPDATE Blanks SET Status = 'Sold' WHERE Blank ='" + BlankStorage.getBlankNumber() + "'";
                                            //update status to sold.
                                            Statement statement = connection.createStatement();
                                            statement.execute(InsertQuery);
                                            //calculate relevant values.
                                            calculator();
                                            InsertQuery = "INSERT INTO Sold(Blank_Number, Fare_USD, Conversion_Rate, Fare_Local, Tax_LZ, Tax_OTHS, Total_Amount, Cash_Amount, CC_Number, Total_Paid, Commission_Rate, Sold_By_TA, Blank_Type, Sold_To, Date_Of_Sale) VALUES('" + BlankNumber + "','" + Fare_USDString + "','" + Conversion_RateString + "','" + Fare_LocalString + "','" + Tax_LZString + "','" + Tax_OTHSString + "','" + Total_AmountString + "',null,'" + creditCardField.getText() + "','" + Total_PaidString + "','" + Commission_RateString + "','" + Sold_By_TA + "','" + Blank_Type + "','" + Sold_To + "'," + date + ")";
                                            //insert into sold table with current date and credit
                                            statement.execute(InsertQuery);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    } else {
                                        warningText.setText("Fill in your card details");
                                    }
                                }
                            } else {
                                date = year.getText() + month.getText() + day.getText(); //else the get the date the user has entered.
                                if (ValidDateChecker(date) == true) { //check if the date is valid.
                                    continueStatement = true;
                                    if (cashOrCredit.getValue().equals("Cash")) { //if cash selected.
                                        try {
                                            InsertQuery = "UPDATE Blanks SET Status = 'Sold' WHERE Blank ='" + BlankStorage.getBlankNumber() + "'";
                                            //update status to sold
                                            Statement statement = connection.createStatement();
                                            statement.execute(InsertQuery);
                                            calculator();
                                            InsertQuery = "INSERT INTO Sold(Blank_Number, Fare_USD, Conversion_Rate, Fare_Local, Tax_LZ, Tax_OTHS, Total_Amount, Cash_Amount, CC_Number, Total_Paid, Commission_Rate, Sold_By_TA, Blank_Type, Sold_To, Date_Of_Sale) VALUES('" + BlankNumber + "','" + Fare_USDString + "','" + Conversion_RateString + "','" + Fare_LocalString + "','" + Tax_LZString + "','" + Tax_OTHSString + "','" + Total_AmountString + "','" + Total_AmountString + "',null,'" + Total_PaidString + "','" + Commission_RateString + "','" + Sold_By_TA + "','" + Blank_Type + "','" + Sold_To + "'," + date + ")";
                                           //insert values to sold tabl with cash and other date.
                                            statement.execute(InsertQuery);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    } else if (cashOrCredit.getValue().equals("Credit")) { //if credit selected.
                                        if (creditCardField.getText().isEmpty() == false) { //check if credit is empty
                                            continueStatement = true;
                                            try {
                                                InsertQuery = "UPDATE Blanks SET Status = 'Sold' WHERE Blank ='" + BlankStorage.getBlankNumber() + "'";
                                                //update status of blank to sold.
                                                Statement statement = connection.createStatement();
                                                statement.execute(InsertQuery);
                                                calculator();
                                                InsertQuery = "INSERT INTO Sold(Blank_Number, Fare_USD, Conversion_Rate, Fare_Local, Tax_LZ, Tax_OTHS, Total_Amount, Cash_Amount, CC_Number, Total_Paid, Commission_Rate, Sold_By_TA, Blank_Type, Sold_To, Date_Of_Sale) VALUES('" + BlankNumber + "','" + Fare_USDString + "','" + Conversion_RateString + "','" + Fare_LocalString + "','" + Tax_LZString + "','" + Tax_OTHSString + "','" + Total_AmountString + "',null,'" + creditCardField.getText() + "','" + Total_PaidString + "','" + Commission_RateString + "','" + Sold_By_TA + "','" + Blank_Type + "','" + Sold_To + "'," + date + ")";
                                                //insert to sold table with credit and other date.
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
                            warningText.setText("You haven't selected cash or credit");//warning text.
                            continueStatement = false;
                        }
                    } else {
                        warningText.setText("You Haven't filled in all the fields");//warning text.
                        continueStatement = false;
                    }
                } else {
                    warningText.setText("You haven't filled in all the fields");//warning text.
                    continueStatement = false;

                }
            } else {
                warningText.setText("Don't Fill in both USD and Local");//warning text.
                continueStatement = false;
            }
        } else {
            warningText.setText("Select a Customer");//warning text.
            continueStatement = false;
        }
        if (continueStatement == true) { //if contunue statment is true.
            try {
                InsertQuery = "SELECT * FROM Coupon WHERE Belongs_To='" + BlankStorage.getBlankNumber() + "'";
                //get coupon
                Statement statement = connection.createStatement();
                ResultSet finalResultSet = statement.executeQuery(InsertQuery);
                int counter = 1;
                while (finalResultSet.next()) {
                    Statement newStatement = connection.createStatement();
                    couponIDStorage = finalResultSet.getInt("CouponID");
                    //insert to coupons table.
                    if (counter == 1) {
                        CouponQuery = "UPDATE Coupon SET Coupon_Itinerary='" + firstCoupon.getText() + "' WHERE CouponID=" + couponIDStorage;
                    } else if (counter == 2) {
                        CouponQuery = "UPDATE Coupon SET Coupon_Itinerary='" + secondCoupon.getText() + "' WHERE CouponID=" + couponIDStorage;
                    } else if (counter == 3) {
                        CouponQuery = "UPDATE Coupon SET Coupon_Itinerary='" + thirdCoupon.getText() + "' WHERE CouponID=" + couponIDStorage;
                    } else if (counter == 4) {
                        CouponQuery = "UPDATE Coupon SET Coupon_Itinerary='" + fourthCoupon.getText() + "' WHERE CouponID=" + couponIDStorage;
                    }
                    newStatement.execute(CouponQuery);
                    counter++;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                LatePaymentQuery = "SELECT Alias, Status FROM Customer WHERE CustomerID=" + CustomerNumber;
                Statement statement = connection.createStatement();
                resultSet = statement.executeQuery(LatePaymentQuery);

                while (resultSet.next()) {
                    valuedStatus = resultSet.getString("Status"); //store status
                    latePaymentCustomer = resultSet.getString("Alias"); //store alias
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (!valuedStatus.equals("Casual")) { //check status
                //make a new dialog asking if they want late payment.
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
                    }
                });
                acceptButton.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        String customerStatus = null;
                        try {
                            Statement statement = connection.createStatement();
                            sqlQuery = "SELECT Status FROM Customer WHERE Alias='" + latePaymentCustomer + "'";
                            resultSet = statement.executeQuery(sqlQuery);
                            while (resultSet.next() != false) {
                                customerStatus = resultSet.getString("Status");
                            }
                            LatePaymentQuery = "INSERT INTO LatePayment(Blank_Number, Owner, Status, Date_of_Sale, Due_Date, Balance_Outstanding, Old_Status, Seen_By_OM) VALUES('" + BlankStorage.getBlankNumber() + "','" + latePaymentCustomer + "','" + latePaymentCustomer + " is yet to make a payment. But may purchase tickets as a casual customer'," + date + ",DATE_ADD('" + date + "' ,INTERVAL 30 DAY),'" + Total_AmountString + "','" + customerStatus + "','No')";
                            //insert into late payment
                            statement.execute(LatePaymentQuery);
                            sqlQuery = "UPDATE Customer SET Status = 'Casual' WHERE Alias='" + latePaymentCustomer + "'"; //update customer set status to casual.
                            statement.execute(sqlQuery);
                            afterChecker(BlankStorage.getBlankNumber()); //check if time exceeded.
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
                FXMLLoader loader = new FXMLLoader(getClass().getResource("SellBlanksPage.fxml")); //load sell blanks page.
                Stage stage = (Stage) usdCurrency.getScene().getWindow();
                Scene scene = new Scene(loader.load());
                stage.setScene(scene);
            } catch (IOException io) {
                io.printStackTrace();
            }
        }
    }

//makes credit fields visible
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

    //calculate the relevant values to be inserted into the sold table.
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
        if (usdCurrency.getText().isEmpty() == true) {
            Fare_Local = Double.parseDouble(localCurrency.getText());
            Fare_USD = Fare_Local / Conversion_Rate;
        } else if (localCurrency.getText().isEmpty() == true) {
            Fare_USD = Double.parseDouble(usdCurrency.getText());
            Fare_Local = Fare_USD * Conversion_Rate;
        }
        Tax_LZ = Double.parseDouble(taxLocal.getText());
        Tax_OTHS = Double.parseDouble(taxOther.getText());
        Total_Amount = Fare_Local + Tax_LZ + Tax_OTHS;
        Total_Paid = Total_Amount;
        try {
            sqlQuery = "SELECT Interline FROM Commission";
            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery(sqlQuery);
            while (resultSet.next()) {
                Commission_Rate = Double.parseDouble(resultSet.getString("Interline"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Sold_By_TA = LoginStorage.getStaffNumber();
        Blank_Type = "Interline";
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

    //check if date has been exceeded.
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
                sqlQuery = "UPDATE LatePayment SET Status='" + latePaymentCustomer + " failed to pay in 30 days period.', Seen_By_OM='No' WHERE Blank_Number='" + BlankNumber + "'"; //update status
                statement.execute(sqlQuery);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //boolean is returned if date is valid.
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

    //back method loads the last page.
    @FXML
    public void Back() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("SellBlanksPage.fxml")); //load sell blanks page.
            Stage stage = (Stage) usdCurrency.getScene().getWindow();
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
        } catch (IOException io) {
            io.printStackTrace();
        }
    }

}
