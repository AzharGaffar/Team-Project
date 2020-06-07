package sample;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.w3c.dom.Document;

import java.io.*;
import java.sql.Connection;
import java.sql.ResultSet;

public class GlobalInterlineReportController {

    //soon to be replaced placeholders.
    public Text namePlaceholder;
    public Text staffNumberPlaceholder;
    public Text rolePlaceholder;

    //used in calculating final totals.
    public int finaltotalNumberofSales = 0;
    public float finallocalTaxTotal = 0;
    public float finalotherTaxTotal = 0;
    public float finalTotalAmount = 0;
    public float finalTotalPaid = 0;
    public float finalTotalCommission = 0;

    //linking to the database.
    DatabaseConnection databaseConnection = new DatabaseConnection();
    Connection connection = databaseConnection.getConnection();

    //declaring string and result set for sql queries and storage.
    public String sqlQuery;
    public ResultSet resultSet;

    //fxml text needs to be overwritten so declaring them here first.
    public Text totalNumber;
    public Text totalSales;
    public Text totalTaxLocal;
    public Text totalTaxOther;
    public Text totalAmount;
    public Text totalPaid;
    public Text totalCommission;
    public Text netAmount;

    //declaring counter.
    int counter = 1;

    //delcaring fxml tableview with columns.
    @FXML
    private TableView<GlobalInterlineDataModel> GlobalInterlineTable;
    @FXML
    private TableColumn<GlobalInterlineDataModel, Integer> numberColumn;
    @FXML
    private TableColumn<GlobalInterlineDataModel, String> advisorColumn;
    @FXML
    private TableColumn<GlobalInterlineDataModel, String> NoCColumn;
    @FXML
    private TableColumn<GlobalInterlineDataModel, String> localTaxColumn;
    @FXML
    private TableColumn<GlobalInterlineDataModel, String> taxOtherColumn;
    @FXML
    private TableColumn<GlobalInterlineDataModel, String> totalAmountColumn;
    @FXML
    private TableColumn<GlobalInterlineDataModel, String> totalCashCreditColumn;
    @FXML
    private TableColumn<GlobalInterlineDataModel, String> totalCommissionColumn;

    //making an observable list
    ObservableList<GlobalInterlineDataModel> oblist = FXCollections.observableArrayList();

    //initialize is first method called.
    @FXML
    public void initialize() {
        //replace text in top left.
        namePlaceholder.setText(LoginStorage.getFirstName());
        staffNumberPlaceholder.setText(LoginStorage.getStaffNumber());
        rolePlaceholder.setText(LoginStorage.getRole());

        try {

            //I want relevant columns selected from the sold table and put into a resultset.
            resultSet = connection.createStatement().executeQuery("SELECT Sold_By_TA, COUNT(Sold_By_TA) AS Number_of_Sales, SUM(Tax_LZ) AS Local_Total, SUM(Tax_OTHS) AS Other_Total, SUM(Total_Amount) AS Total_Amount, SUM(Total_Paid) AS Total_Paid, SUM(((Fare_Local/100)*Commission_Rate)) AS Commission FROM sold WHERE Blank_Type= 'Interline' AND Date_of_Sale BETWEEN '" + GenerateReportStorage.getDate1() + "' AND'" + GenerateReportStorage.getDate2() + "' GROUP BY Sold_By_TA; ");

            //Keep adding results to the list.
            while (resultSet.next()) {
                oblist.add(new GlobalInterlineDataModel(counter, resultSet.getString("Sold_By_TA"), resultSet.getString("Number_of_Sales"), resultSet.getString("Local_Total"), resultSet.getString("Other_Total"), resultSet.getString("Total_Amount"), resultSet.getString("Total_Paid"), resultSet.getString("Commission")));
                counter++;
                //add to observable list with counter.
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //linking to interline report data model.
        numberColumn.setCellValueFactory(new PropertyValueFactory<>("number"));
        advisorColumn.setCellValueFactory(new PropertyValueFactory<>("advisorCode"));
        NoCColumn.setCellValueFactory(new PropertyValueFactory<>("numberOfSales"));
        localTaxColumn.setCellValueFactory(new PropertyValueFactory<>("taxLocal"));
        taxOtherColumn.setCellValueFactory(new PropertyValueFactory<>("taxOther"));
        totalAmountColumn.setCellValueFactory(new PropertyValueFactory<>("totalAmount"));
        totalCashCreditColumn.setCellValueFactory(new PropertyValueFactory<>("totalCC"));
        totalCommissionColumn.setCellValueFactory(new PropertyValueFactory<>("totalCommission"));

        //Display the items in JavaFX table view.
        GlobalInterlineTable.setItems(oblist);
        try {
            resultSet.beforeFirst();
            while (resultSet.next() != false) {
                //calculating final amounts.
                finaltotalNumberofSales += Integer.parseInt(resultSet.getString("Number_of_Sales"));
                finallocalTaxTotal += Float.parseFloat(resultSet.getString("Local_Total"));
                finalotherTaxTotal += Float.parseFloat(resultSet.getString("Other_Total"));
                finalTotalAmount += Float.parseFloat(resultSet.getString("Total_Amount"));
                finalTotalPaid += Float.parseFloat(resultSet.getString("Total_Paid"));
                finalTotalCommission += Float.parseFloat(resultSet.getString("Commission"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //replacing text with those final amounts.
        totalNumber.setText(Integer.toString(counter - 1));
        totalSales.setText(Integer.toString(finaltotalNumberofSales));
        totalTaxLocal.setText(Float.toString(finallocalTaxTotal));
        totalTaxOther.setText(Float.toString(finalotherTaxTotal));
        totalAmount.setText(Float.toString(finalTotalAmount));
        totalPaid.setText(Float.toString(finalTotalPaid));
        totalCommission.setText(Float.toString(finalTotalCommission));
        netAmount.setText(Float.toString(finalTotalAmount - finalTotalCommission));

    }

    //print to text file.
    @FXML
    public void Print() {
        try {
            File GIR = new File("./GlobalInterlineReport.txt"); //file name.

            if (!GIR.exists()) {
                GIR.createNewFile();
            }

            FileWriter fw = new FileWriter(GIR, false);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(String.format("%20s %20s %20s %20s %20s %20s %20s %20s \r\n", "Number", "Advisor_Code", "Number_Of_Sales", "Tax_Total_Local", "Tax_Total_Other", "Total_Amount", "TotalCashCredit", "TotalCommission")); //space evenly with these columns.
            try {
                resultSet.beforeFirst();
                int newcounter = 1;
                while (resultSet.next()) {
                    bw.write(String.format("%20s %20s %20s %20s %20s %20s %20s %20s \r\n", Integer.toString(newcounter), resultSet.getString("Sold_By_TA"), resultSet.getString("Number_of_Sales"), resultSet.getString("Local_Total"), resultSet.getString("Other_Total"), resultSet.getString("Total_Amount"), resultSet.getString("Total_Paid"), resultSet.getString("Commission")));
                    newcounter++;
                    //write the result sets to the file.
                }
                //write the final amounts to the file.
                bw.write("Counted Number: " + Integer.toString(counter - 1) + "\n");
                bw.write("Total sales: " + finaltotalNumberofSales + "\n");
                bw.write("Total Tax Local: " + finallocalTaxTotal + "\n");
                bw.write("Total Tax Other: " + finalotherTaxTotal + "\n");
                bw.write("Total Amount: " + finalTotalAmount + "\n");
                bw.write("Total Paid: " + finalTotalPaid + "\n");
                bw.write("Total Commission: " + finalTotalCommission + "\n");
                bw.write("Net Amount: " + (finalTotalAmount - finalTotalCommission) + "\n");
            } catch (Exception e) {
                e.printStackTrace();
            }

            bw.close();
            System.out.println("Data Written");
        } catch (IOException ioe) {
            System.out.println("Exception occurred:");
            ioe.printStackTrace();
        }

    }


    //go back to the generatereports menu
    @FXML
    private void goBack() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("GenerateReportsMenu.fxml")); //load the generatereportsmenu.fxml
            Stage stage = (Stage) namePlaceholder.getScene().getWindow();
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
        } catch (IOException io) {
            io.printStackTrace();
        }

    }
}
