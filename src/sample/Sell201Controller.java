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

public class Sell201Controller {

    //Controller Links to Sell201.fxml


    //placeholders for the top left/
    public Text namePlaceholder;
    public Text staffNumberPlaceholder;
    public Text rolePlaceholder;

    //Text and textfields that are on the fxml file.
    public TextField day;
    public TextField month;
    public TextField year;
    public TextField firstCoupon;
    public TextField secondCoupon;
    public TextField localCurrency;
    public TextField taxLocal;
    public Text creditCardLabel;
    public TextField creditCardField;
    public Text warningText;
    public TextField usdCurrency;

    //continue statement is initally set to false.
    public boolean continueStatement = false;

    //storage for customer number
    public int CustomerNumber;

    //storage
    public String valuedStatus;
    public String customerUsername;
    public int couponIDStorage;
    public String latePaymentCustomer;

    //Strings and doubles for the text that is on the fxml file.
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

    //drop down for cash or credit.
    public ComboBox<String> cashOrCredit;

    //SQL queries and resultset.
    public String sqlQuery, InsertQuery, CouponQuery, LatePaymentQuery;
    public ResultSet resultSet;

    //establishing a connection to the database.
    DatabaseConnection databaseConnection = new DatabaseConnection();
    Connection connection = databaseConnection.getConnection();

    //initialize is the first method to be called.
    @FXML
    public void initialize() {

        //replace top left text
        namePlaceholder.setText(LoginStorage.getFirstName());
        staffNumberPlaceholder.setText(LoginStorage.getStaffNumber());
        rolePlaceholder.setText(LoginStorage.getRole());

        try {
            sqlQuery = "SELECT Alias FROM Customer";
            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery(sqlQuery);

            while (resultSet.next()) {
                customerUsername = resultSet.getString("Alias"); //store alias
                customerList.getItems().addAll(customerUsername); //add it to customer list.
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        //field and text is invisible.
        creditCardField.setVisible(false);
        creditCardLabel.setVisible(false);

        cashOrCredit.setPromptText("Cash or Credit?");
        cashOrCredit.getItems().setAll("Cash", "Credit"); //add cash or credit options to drop down.
    }

    //When submit button is pressed
    @FXML
    public void Submit() {
        if (customerList.getSelectionModel().getSelectedItem() != null) {
            if (localCurrency.getText().isEmpty() == false) {
                try {
                    sqlQuery = "SELECT CustomerID FROM Customer WHERE Alias='" + customerList.getSelectionModel().getSelectedItem() + "'";
                    Statement statement = connection.createStatement();
                    resultSet = statement.executeQuery(sqlQuery);
                    while (resultSet.next()) {
                        CustomerNumber = resultSet.getInt("CustomerID"); //get the customer id.
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (firstCoupon.getText().isEmpty() == false && secondCoupon.getText().isEmpty() == false) { //if coupons have been filled out.
                    if (taxLocal.getText().isEmpty() == false) {
                        if (cashOrCredit.getSelectionModel().getSelectedItem() != null) { //if cash or credit has been selected.
                            if (day.getText().isEmpty() && month.getText().isEmpty() && year.getText().isEmpty()) { //if date is empty.
                                java.sql.Date sqlCurrentDate = new java.sql.Date(Calendar.getInstance().getTime().getTime()); //store the current date.
                                date = sqlCurrentDate.toString();
                                date = date.replace("-", "");
                                if (cashOrCredit.getValue().equals("Cash")) { //if cash payment,
                                    try {
                                        continueStatement = true;
                                        InsertQuery = "UPDATE Blanks SET Status = 'Sold' WHERE Blank ='" + BlankStorage.getBlankNumber() + "'"; //Update sold status
                                        Statement statement = connection.createStatement();
                                        statement.execute(InsertQuery);
                                        calculator(); //calculate the relevent values.
                                        InsertQuery = "INSERT INTO Sold(Blank_Number, Fare_USD, Conversion_Rate, Fare_Local, Tax_LZ, Tax_OTHS, Total_Amount, Cash_Amount, CC_Number, Total_Paid, Commission_Rate, Sold_By_TA, Blank_Type, Sold_To, Date_Of_Sale) VALUES('" + BlankNumber + "','" + Fare_USDString + "','" + Conversion_RateString + "','" + Fare_LocalString + "','" + Tax_LZString + "','" + Tax_OTHSString + "','" + Total_AmountString + "','" + Total_AmountString + "',null,'" + Total_PaidString + "','" + Commission_RateString + "','" + Sold_By_TA + "','" + Blank_Type + "','" + Sold_To + "'," + date + ")";
                                        //Insert into sold table with cash and current date.
                                        statement.execute(InsertQuery);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                } else if (cashOrCredit.getValue().equals("Credit")) { //if credit selected.
                                    if (creditCardField.getText().isEmpty() == false) { //check if credit card is empty.
                                        try {
                                            continueStatement = true;
                                            InsertQuery = "UPDATE Blanks SET Status = 'Sold' WHERE Blank ='" + BlankStorage.getBlankNumber() + "'"; //update sold status.
                                            Statement statement = connection.createStatement();
                                            statement.execute(InsertQuery);
                                            calculator();
                                            InsertQuery = "INSERT INTO Sold(Blank_Number, Fare_USD, Conversion_Rate, Fare_Local, Tax_LZ, Tax_OTHS, Total_Amount, Cash_Amount, CC_Number, Total_Paid, Commission_Rate, Sold_By_TA, Blank_Type, Sold_To, Date_Of_Sale) VALUES('" + BlankNumber + "','" + Fare_USDString + "','" + Conversion_RateString + "','" + Fare_LocalString + "','" + Tax_LZString + "','" + Tax_OTHSString + "','" + Total_AmountString + "',null,'" + creditCardField.getText() + "','" + Total_PaidString + "','" + Commission_RateString + "','" + Sold_By_TA + "','" + Blank_Type + "','" + Sold_To + "'," + date + ")";
                                            //insert into sold table with card and current date.
                                            statement.execute(InsertQuery);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    } else {
                                        warningText.setText("Fill in your card details"); //fill in card details warning message.
                                    }
                                }
                            } else {
                                date = year.getText() + month.getText() + day.getText(); //otherwise store the dates.
                                if (ValidDateChecker(date) == true) { //check if the dates are valid.
                                    continueStatement = true;
                                    if (cashOrCredit.getValue().equals("Cash")) { //if cash
                                        try {
                                            InsertQuery = "UPDATE Blanks SET Status = 'Sold' WHERE Blank ='" + BlankStorage.getBlankNumber() + "'"; //set as sold.
                                            Statement statement = connection.createStatement();
                                            statement.execute(InsertQuery);
                                            calculator(); //calculate.
                                            InsertQuery = "INSERT INTO Sold(Blank_Number, Fare_USD, Conversion_Rate, Fare_Local, Tax_LZ, Tax_OTHS, Total_Amount, Cash_Amount, CC_Number, Total_Paid, Commission_Rate, Sold_By_TA, Blank_Type, Sold_To, Date_Of_Sale) VALUES('" + BlankNumber + "','" + Fare_USDString + "','" + Conversion_RateString + "','" + Fare_LocalString + "','" + Tax_LZString + "','" + Tax_OTHSString + "','" + Total_AmountString + "','" + Total_AmountString + "',null,'" + Total_PaidString + "','" + Commission_RateString + "','" + Sold_By_TA + "','" + Blank_Type + "','" + Sold_To + "'," + date + ")";
                                            //insert into sold table with cash and other date.
                                            statement.execute(InsertQuery);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    } else if (cashOrCredit.getValue().equals("Credit")) { //if credit sale
                                        if (creditCardField.getText().isEmpty() == false) { //check if credit is empty.
                                            continueStatement = true;
                                            try {
                                                InsertQuery = "UPDATE Blanks SET Status = 'Sold' WHERE Blank ='" + BlankStorage.getBlankNumber() + "'"; //update as sold.
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
                                    warningText.setText("Enter a valid date or leave blank.");//warning message
                                    continueStatement = false;
                                }
                            }

                        } else {
                            warningText.setText("You haven't selected cash or credit"); //warning message.
                            continueStatement = false;
                        }
                    } else {
                        warningText.setText("You Haven't filled in all the fields"); //warning message.
                        continueStatement = false;
                    }
                } else {
                    warningText.setText("You haven't filled in all the fields"); //warning message.
                    continueStatement = false;

                }
            } else {
                warningText.setText("Fill in Local Currency!");//warning message.
                continueStatement = false;
            }
        } else {
            warningText.setText("Select a Customer");//warning message.
            continueStatement = false;
        }
        if (continueStatement == true) {
            //INSERT TO COUPON
            try {
                InsertQuery = "SELECT * FROM Coupon WHERE Belongs_To='" + BlankStorage.getBlankNumber() + "'";
                Statement statement = connection.createStatement();
                ResultSet finalResultSet = statement.executeQuery(InsertQuery);

                int counter = 1;
                while (finalResultSet.next()) {
                    Statement newStatement = connection.createStatement();
                    couponIDStorage = finalResultSet.getInt("CouponID");
                    if (counter == 1) {
                        CouponQuery = "UPDATE Coupon SET Coupon_Itinerary='" + firstCoupon.getText() + "' WHERE CouponID=" + couponIDStorage;
                    } else if (counter == 2) {
                        CouponQuery = "UPDATE Coupon SET Coupon_Itinerary='" + secondCoupon.getText() + "' WHERE CouponID=" + couponIDStorage;
                    }
                    newStatement.execute(CouponQuery);
                    counter++;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                //CHECK IF LATE PAYMENT ELEGIBLE
                LatePaymentQuery = "SELECT Alias, Status FROM Customer WHERE CustomerID=" + CustomerNumber;
                //store alias and status
                Statement statement = connection.createStatement();
                resultSet = statement.executeQuery(LatePaymentQuery);

                while (resultSet.next()) {
                    valuedStatus = resultSet.getString("Status"); //store status and alias.
                    latePaymentCustomer = resultSet.getString("Alias");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (!valuedStatus.equals("Casual")) { //check status
                //ask if they want late payment.
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
                            //insert to late payment table
                            statement.execute(LatePaymentQuery);
                            sqlQuery = "UPDATE Customer SET Status = 'Casual' WHERE Alias='" + latePaymentCustomer + "'";
                            statement.execute(sqlQuery);
                            afterChecker(BlankStorage.getBlankNumber());
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
                FXMLLoader loader = new FXMLLoader(getClass().getResource("SellBlanksPage.fxml"));
                Stage stage = (Stage) localCurrency.getScene().getWindow();
                Scene scene = new Scene(loader.load());
                stage.setScene(scene);
            } catch (IOException io) {
                io.printStackTrace();
            }
        }
    }


    //Make credit card field visible.
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

    //calculates the relevant values for prep to insert into sold table.
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

    //Check if late payment has exceeded time limit and update status.
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

    //Boolean returned depending if date is valid or not.
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

    //Goes back to the sell blanks page.
    @FXML
    public void Back() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("SellBlanksPage.fxml")); //load sell blanks fxml page.
            Stage stage = (Stage) namePlaceholder.getScene().getWindow();
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
        } catch (IOException io) {
            io.printStackTrace();
        }
    }

}
