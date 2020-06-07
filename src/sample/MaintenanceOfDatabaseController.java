package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class MaintenanceOfDatabaseController {

    //Controller Links to MaintenanceOfDatabasePage.fxml

    //Connection to DatabaseConnection Class and preparing a connection.
    DatabaseConnection databaseConnection = new DatabaseConnection();
    Connection connection = databaseConnection.getConnection();

    //Declaring all the buttons and strings needed for methods.
    public Button backupButton;
    String sqlStatement;

    //Statement needed for sending SQL statements
    Statement statement;

    //Will store strings that I make in these. One string for each table.
    String constructString, constructString2, constructString3, constructString4, constructString5, constructString6, constructString7, constructString8, constructString9;

    //Resultset will store sql statements results.
    ResultSet setOfColumns;

    //ArrayList will store ResultSet Strings to be made into normal text.
    ArrayList<String> blanksTableArray = new ArrayList();
    ArrayList<String> commissionTableArray = new ArrayList();
    ArrayList<String> couponTableArray = new ArrayList();
    ArrayList<String> currencyTableArray = new ArrayList();
    ArrayList<String> customerTableArray = new ArrayList();
    ArrayList<String> flexibleDiscountTableArray = new ArrayList();
    ArrayList<String> latePaymentTableArray = new ArrayList();
    ArrayList<String> soldTableArray = new ArrayList();
    ArrayList<String> usersTableArray = new ArrayList();

    @FXML
    public void Backup() throws Exception {
        /*This method essentially repeats the same steps again and again for each table so I will take you through
         the first steps and you can interpret what the other steps are doing. */

        //Selecting everything from Blanks Table.
        sqlStatement = "SELECT * FROM Blanks";

        //Linking statement for the connection to the Database Tables.
        statement = connection.createStatement();

        //Store in a resultset.
        setOfColumns = statement.executeQuery(sqlStatement);

        //Add every column with its records from the resultset into an arraylist.
        while (setOfColumns.next()) {
            blanksTableArray.add("('");
            blanksTableArray.add(setOfColumns.getString("Blank"));
            blanksTableArray.add("','");
            blanksTableArray.add(setOfColumns.getString("Number_Of_Coupons"));
            blanksTableArray.add("','");
            blanksTableArray.add(setOfColumns.getString("Is_Void"));
            blanksTableArray.add("','");
            blanksTableArray.add(setOfColumns.getString("Status"));
            blanksTableArray.add("','");
            blanksTableArray.add(setOfColumns.getString("Date_Added"));
            blanksTableArray.add("','");
            blanksTableArray.add(setOfColumns.getString("AssignedTo"));
            blanksTableArray.add("'),");
        }

        //For each loop constructing the string.
        for (String getColumn : blanksTableArray) {
            constructString += "" + getColumn;
        }

        //if the String is not empty.
        if (constructString != null) {
            if (constructString.startsWith("null")) { //if the string has a null at the beginning (unfortunate side effect of printing a resultset).
                constructString = constructString.substring(4); //Remove the null at the beginning.
            }
            constructString = constructString.substring(0, constructString.length() - 1) + ";"; //Replacing the final comma with a semi colon.
            constructString = constructString.replace("'null'", "null"); //Replacing string Nulls with literal nulls so SQL will format database correctly.
        }

        /* At this point everything is repeated until all the tables are formed into their own insert statements*/

        sqlStatement = "SELECT * FROM Commission";
        setOfColumns = statement.executeQuery(sqlStatement);

        while (setOfColumns.next()) {
            commissionTableArray.add("(");
            commissionTableArray.add(setOfColumns.getString("idCommission"));
            commissionTableArray.add(",'");
            commissionTableArray.add(setOfColumns.getString("Interline"));
            commissionTableArray.add("','");
            commissionTableArray.add(setOfColumns.getString("Domestic"));
            commissionTableArray.add("'),");
        }

        for (String getColumn : commissionTableArray) {
            constructString2 += "" + getColumn;
        }

        if (constructString2 != null) {
            if (constructString2.startsWith("null")) {
                constructString2 = constructString2.substring(4);
            }
            constructString2 = constructString2.substring(0, constructString2.length() - 1) + ";";
            constructString2 = constructString2.replace("'null'", "null");
        }
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        sqlStatement = "SELECT * FROM Coupon";
        setOfColumns = statement.executeQuery(sqlStatement);

        while (setOfColumns.next()) {
            couponTableArray.add("(");
            couponTableArray.add(setOfColumns.getString("CouponID"));
            couponTableArray.add(",'");
            couponTableArray.add(setOfColumns.getString("Coupon_Itinerary"));
            couponTableArray.add("','");
            couponTableArray.add(setOfColumns.getString("Belongs_To"));
            couponTableArray.add("'),");
        }

        for (String getColumn : couponTableArray) {
            constructString3 += "" + getColumn;
        }

        if (constructString3 != null) {
            if (constructString3.startsWith("null")) {
                constructString3 = constructString3.substring(4);
            }
            constructString3 = constructString3.substring(0, constructString3.length() - 1) + ";";
            constructString3 = constructString3.replace("'null'", "null");
        }
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        sqlStatement = "SELECT * FROM Currency";
        setOfColumns = statement.executeQuery(sqlStatement);

        while (setOfColumns.next()) {
            currencyTableArray.add("(");
            currencyTableArray.add(setOfColumns.getString("idCurrency"));
            currencyTableArray.add(",'");
            currencyTableArray.add(setOfColumns.getString("Current_Currency"));
            currencyTableArray.add("'),");
        }

        for (String getColumn : currencyTableArray) {
            constructString4 += "" + getColumn;
        }

        if (constructString4 != null) {
            if (constructString4.startsWith("null")) {
                constructString4 = constructString4.substring(4);
            }
            constructString4 = constructString4.substring(0, constructString4.length() - 1) + ";";
            constructString4 = constructString4.replace("'null'", "null");
        }
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////


        sqlStatement = "SELECT * FROM Customer";
        setOfColumns = statement.executeQuery(sqlStatement);

        while (setOfColumns.next()) {
            customerTableArray.add("(");
            customerTableArray.add(setOfColumns.getString("CustomerID"));
            customerTableArray.add(",'");
            customerTableArray.add(setOfColumns.getString("First_Name"));
            customerTableArray.add("','");
            customerTableArray.add(setOfColumns.getString("Last_Name"));
            customerTableArray.add("','");
            customerTableArray.add(setOfColumns.getString("Alias"));
            customerTableArray.add("','");
            customerTableArray.add(setOfColumns.getString("Email"));
            customerTableArray.add("','");
            customerTableArray.add(setOfColumns.getString("Telephone"));
            customerTableArray.add("','");
            customerTableArray.add(setOfColumns.getString("Status"));
            customerTableArray.add("','");
            customerTableArray.add(setOfColumns.getString("Fixed_Discount_Rate"));
            customerTableArray.add("'),");
        }

        for (String getColumn : customerTableArray) {
            constructString5 += "" + getColumn;
        }

        if (constructString5 != null) {
            if (constructString5.startsWith("null")) {
                constructString5 = constructString5.substring(4);
            }
            constructString5 = constructString5.substring(0, constructString5.length() - 1) + ";";
            constructString5 = constructString5.replace("'null'", "null");
        }

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        sqlStatement = "SELECT * FROM FlexibleDiscount";
        setOfColumns = statement.executeQuery(sqlStatement);

        while (setOfColumns.next()) {
            flexibleDiscountTableArray.add("(");
            flexibleDiscountTableArray.add(setOfColumns.getString("idFlexibleDiscount"));
            flexibleDiscountTableArray.add(",'");
            flexibleDiscountTableArray.add(setOfColumns.getString("Less_Than_Thousand"));
            flexibleDiscountTableArray.add("','");
            flexibleDiscountTableArray.add(setOfColumns.getString("BetweenThese"));
            flexibleDiscountTableArray.add("','");
            flexibleDiscountTableArray.add(setOfColumns.getString("More_Than_Two_Thousand"));
            flexibleDiscountTableArray.add("'),");
        }

        for (String getColumn : flexibleDiscountTableArray) {
            constructString6 += "" + getColumn;
        }

        if (constructString6 != null) {
            if (constructString6.startsWith("null")) {
                constructString6 = constructString6.substring(4);
            }
            constructString6 = constructString6.substring(0, constructString6.length() - 1) + ";";
            constructString6 = constructString6.replace("'null'", "null");
        }

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        sqlStatement = "SELECT * FROM LatePayment";
        setOfColumns = statement.executeQuery(sqlStatement);

        while (setOfColumns.next()) {
            latePaymentTableArray.add("('");
            latePaymentTableArray.add(setOfColumns.getString("Blank_Number"));
            latePaymentTableArray.add("','");
            latePaymentTableArray.add(setOfColumns.getString("Owner"));
            latePaymentTableArray.add("','");
            latePaymentTableArray.add(setOfColumns.getString("Status"));
            latePaymentTableArray.add("','");
            latePaymentTableArray.add(setOfColumns.getString("Date_of_Sale"));
            latePaymentTableArray.add("','");
            latePaymentTableArray.add(setOfColumns.getString("Due_Date"));
            latePaymentTableArray.add("','");
            latePaymentTableArray.add(setOfColumns.getString("Balance_Outstanding"));
            latePaymentTableArray.add("','");
            latePaymentTableArray.add(setOfColumns.getString("Old_Status"));
            latePaymentTableArray.add("','");
            latePaymentTableArray.add(setOfColumns.getString("Seen_By_OM"));
            latePaymentTableArray.add("'),");
        }

        for (String getColumn : latePaymentTableArray) {
            constructString7 += "" + getColumn;
        }

        if (constructString7 != null) {
            if (constructString7.startsWith("null")) {
                constructString7 = constructString7.substring(4);
            }
            constructString7 = constructString7.substring(0, constructString7.length() - 1) + ";";
            constructString7 = constructString7.replace("'null'", "null");
        }

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        sqlStatement = "SELECT * FROM Sold";
        setOfColumns = statement.executeQuery(sqlStatement);

        while (setOfColumns.next()) {
            soldTableArray.add("('");
            soldTableArray.add(setOfColumns.getString("Blank_Number"));
            soldTableArray.add("','");
            soldTableArray.add(setOfColumns.getString("Fare_USD"));
            soldTableArray.add("','");
            soldTableArray.add(setOfColumns.getString("Conversion_Rate"));
            soldTableArray.add("','");
            soldTableArray.add(setOfColumns.getString("Fare_Local"));
            soldTableArray.add("','");
            soldTableArray.add(setOfColumns.getString("Tax_LZ"));
            soldTableArray.add("','");
            soldTableArray.add(setOfColumns.getString("Tax_OTHS"));
            soldTableArray.add("','");
            soldTableArray.add(setOfColumns.getString("Total_Amount"));
            soldTableArray.add("','");
            soldTableArray.add(setOfColumns.getString("Cash_Amount"));
            soldTableArray.add("','");
            soldTableArray.add(setOfColumns.getString("CC_Number"));
            soldTableArray.add("','");
            soldTableArray.add(setOfColumns.getString("Total_Paid"));
            soldTableArray.add("','");
            soldTableArray.add(setOfColumns.getString("Commission_Rate"));
            soldTableArray.add("','");
            soldTableArray.add(setOfColumns.getString("Sold_By_TA"));
            soldTableArray.add("','");
            soldTableArray.add(setOfColumns.getString("Blank_Type"));
            soldTableArray.add("',");
            soldTableArray.add(setOfColumns.getString("Sold_To"));
            soldTableArray.add(",'");
            soldTableArray.add(setOfColumns.getString("Date_Of_Sale"));
            soldTableArray.add("'),");
        }

        for (String getColumn : soldTableArray) {
            constructString8 += "" + getColumn;
        }

        if (constructString8 != null) {
            if (constructString8.startsWith("null")) {
                constructString8 = constructString8.substring(4);
            }
            constructString8 = constructString8.substring(0, constructString8.length() - 1) + ";";
            constructString8 = constructString8.replace("'null'", "null");
        }

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        sqlStatement = "SELECT * FROM Users";
        setOfColumns = statement.executeQuery(sqlStatement);

        while (setOfColumns.next()) {
            usersTableArray.add("('");
            usersTableArray.add(setOfColumns.getString("Staff_Number"));
            usersTableArray.add("','");
            usersTableArray.add(setOfColumns.getString("First_Name"));
            usersTableArray.add("','");
            usersTableArray.add(setOfColumns.getString("Last_Name"));
            usersTableArray.add("','");
            usersTableArray.add(setOfColumns.getString("Email"));
            usersTableArray.add("','");
            usersTableArray.add(setOfColumns.getString("Username"));
            usersTableArray.add("','");
            usersTableArray.add(setOfColumns.getString("Password"));
            usersTableArray.add("','");
            usersTableArray.add(setOfColumns.getString("Role"));
            usersTableArray.add("'),");
        }

        for (String getColumn : usersTableArray) {
            constructString9 += "" + getColumn;
        }

        if (constructString9 != null) {
            if (constructString9.startsWith("null")) {
                constructString9 = constructString9.substring(4);
            }
            constructString9 = constructString9.substring(0, constructString9.length() - 1) + ";";
            constructString9 = constructString9.replace("'null'", "null");
        }

        writeToFile(constructString, constructString2, constructString3, constructString4, constructString5, constructString6, constructString7, constructString8, constructString9);


    }


    @FXML
    public void Restore() {
        try {
            Statement statement = connection.createStatement();
            sqlStatement="SET FOREIGN_KEY_CHECKS=0";
            statement.execute(sqlStatement);
            sqlStatement = "DELETE FROM Blanks";
            statement.execute(sqlStatement);
            sqlStatement = "DELETE FROM Commission";
            statement.execute(sqlStatement);
            sqlStatement = "DELETE FROM Coupon";
            statement.execute(sqlStatement);
            sqlStatement = "DELETE FROM Currency";
            statement.execute(sqlStatement);
            sqlStatement = "DELETE FROM Customer";
            statement.execute(sqlStatement);
            sqlStatement = "DELETE FROM FlexibleDiscount";
            statement.execute(sqlStatement);
            sqlStatement = "DELETE FROM LatePayment";
            statement.execute(sqlStatement);
            sqlStatement = "DELETE FROM Sold";
            statement.execute(sqlStatement);
            sqlStatement = "DELETE FROM Users";
            statement.execute(sqlStatement);

            BufferedReader br = new BufferedReader(new FileReader("./BackupOfDatabase.txt"));

            String line;

            while ((line = br.readLine()) != null) {
                sqlStatement = line;
                statement.execute(sqlStatement);
            }

            br.close();

            sqlStatement="SET FOREIGN_KEY_CHECKS=1";
            statement.execute(sqlStatement);
            System.out.println("Done!");
        } catch (Exception exception) {
            exception.printStackTrace();
        }

    }

    @FXML
    public void Back() {
        //Loads the last page.
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MainScreenSystemAdministrator.fxml"));
            Stage stage = (Stage) backupButton.getScene().getWindow();
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
        } catch (IOException io) {
            io.printStackTrace();
        }
    }


    public void writeToFile(String content, String content2, String content3, String content4, String content5, String content6, String content7, String content8, String content9) {
        String insertString, insertString2, insertString3, insertString4, insertString5, insertString6, insertString7, insertString8, insertString9;
        try {
            File BackupFile = new File("./BackupOfDatabase.txt");

            if (!BackupFile.exists()) {
                BackupFile.createNewFile();
            } else {
                BackupFile.delete(); //This stops the user from looping an append to the file and clears the file so data that is backed up is valid.
                BackupFile.createNewFile();
            }

            FileWriter fw = new FileWriter(BackupFile, true);
            BufferedWriter bw = new BufferedWriter(fw);
            if (content != null) {
                insertString = "INSERT INTO Blanks VALUES" + content;
                bw.write(insertString);
            }
            if (content2 != null) {
                insertString2 = "\nINSERT INTO Commission VALUES" + content2;
                bw.write(insertString2);
            }
            if (content3 != null) {
                insertString3 = "\nINSERT INTO Coupon VALUES" + content3;
                bw.write(insertString3);
            }
            if (content4 != null) {
                insertString4 = "\nINSERT INTO Currency VALUES" + content4;
                bw.write(insertString4);
            }
            if (content5 != null) {
                insertString5 = "\nINSERT INTO Customer VALUES" + content5;
                bw.write(insertString5);
            }
            if (content6 != null) {
                insertString6 = "\nINSERT INTO FlexibleDiscount VALUES" + content6;
                bw.write(insertString6);
            }
            if (content7 != null) {
                insertString7 = "\nINSERT INTO LatePayment VALUES" + content7;
                bw.write(insertString7);
            }
            if (content8 != null) {
                insertString8 = "\nINSERT INTO Sold VALUES" + content8;
                bw.write(insertString8);
            }
            if (content9 != null) {
                insertString9 = "\nINSERT INTO Users VALUES" + content9;
                bw.write(insertString9);
            }
            bw.close();
            System.out.println("Backup Data Written");
        } catch (IOException ioe) {
            System.out.println("Exception occurred:");
            ioe.printStackTrace();
        }
        reset();
    }

    public void reset() {
        //Resetting the strings in order for them to be made again. If this method were not called then an extra 4 characters would be cut off each call.
        constructString = "";
        constructString2 = "";
        constructString3 = "";
        constructString4 = "";
        constructString5 = "";
        constructString6 = "";
        constructString7 = "";
        constructString8 = "";
        constructString9 = "";

        //Resetting the arrays.
        blanksTableArray.clear();
        commissionTableArray.clear();
        couponTableArray.clear();
        currencyTableArray.clear();
        customerTableArray.clear();
        flexibleDiscountTableArray.clear();
        latePaymentTableArray.clear();
        soldTableArray.clear();
        usersTableArray.clear();

    }
}
