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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;

public class IndividualInterlineReportController {

    //IndividualInterlineReportController links to IndividualInterline.fxml

    //placeholders in the top left for staff details/
    public Text namePlaceholder;
    public Text staffNumberPlaceholder;
    public Text rolePlaceholder;

    //used for calculating the fincal totals.
    public float finalTotalFareUSD = 0;
    public float finalTotalFareLocal = 0;
    public float finallocalTaxTotal = 0;
    public float finalotherTaxTotal = 0;
    public float finalTotalAmount = 0;
    public float finalcashAmount = 0;
    public float finalTotalPaid = 0;
    public float finalTotalCommission = 0;

    //making a connection to the database.
    DatabaseConnection databaseConnection = new DatabaseConnection();
    Connection connection = databaseConnection.getConnection();

    //string and resultset regarding sql
    public String sqlQuery;
    public ResultSet resultSet;

    //all the text fields on the fxml file.
    public Text totalNumber;
    public Text totalTaxLocal;
    public Text totalTaxOther;
    public Text totalPaidAmount;
    public Text totalCommission;
    public Text totalCashAmount;
    public Text netAmount;
    public Text usdFareTotal;
    public Text localFareTotal;
    public Text totalAmount;

    //caluclation counter.
    int counter = 1;

    //tableview from fxml and all of its columns.
    @FXML
    private TableView<IndividualInterlineDataModel> IndividualInterlineTable;
    @FXML
    private TableColumn<IndividualInterlineDataModel, Integer> numberColumn;
    @FXML
    private TableColumn<IndividualInterlineDataModel, String> blankNumberColumn;
    @FXML
    private TableColumn<IndividualInterlineDataModel, String> fareUSDColumn;
    @FXML
    private TableColumn<IndividualInterlineDataModel, String> exchangeRateColumn;
    @FXML
    private TableColumn<IndividualInterlineDataModel, String> fareLocalColumn;
    @FXML
    private TableColumn<IndividualInterlineDataModel, String> taxLocalColumn;
    @FXML
    private TableColumn<IndividualInterlineDataModel, String> taxOtherColumn;
    @FXML
    private TableColumn<IndividualInterlineDataModel, String> totalAmountColumn;
    @FXML
    private TableColumn<IndividualInterlineDataModel, String> totalCashAmountColumn;
    @FXML
    private TableColumn<IndividualInterlineDataModel, String> ccNumberColumn;
    @FXML
    private TableColumn<IndividualInterlineDataModel, String> totalPaidColumn;
    @FXML
    private TableColumn<IndividualInterlineDataModel, String> commissionColumn;

    //new observable list
    ObservableList<IndividualInterlineDataModel> oblist = FXCollections.observableArrayList();

    //initialize is the first method called.
    @FXML
    public void initialize() {
        //replace the text on the top left with the correct values.
        namePlaceholder.setText(LoginStorage.getFirstName());
        staffNumberPlaceholder.setText(LoginStorage.getStaffNumber());
        rolePlaceholder.setText(LoginStorage.getRole());

        try {

            //I want every relevent column selected from the sold table  table and put into a resultset.
            resultSet = connection.createStatement().executeQuery("SELECT Blank_Number, Fare_USD, Conversion_Rate, Fare_Local, Tax_LZ, Tax_OTHS, Total_Amount, Cash_Amount, CC_Number, Total_Paid, ((Fare_Local/100)*Commission_Rate) AS Commission FROM sold WHERE Sold_By_TA='" + LoginStorage.getStaffNumber() + "' AND Blank_Type= 'Interline' AND Date_of_Sale BETWEEN '" + GenerateReportStorage.getDate1() + "' AND'" + GenerateReportStorage.getDate2() + "'");


            //Keep adding results to the list.
            while (resultSet.next()) {
                oblist.add(new IndividualInterlineDataModel(counter, resultSet.getString("Blank_Number"), resultSet.getString("Fare_USD"), resultSet.getString("Conversion_Rate"), resultSet.getString("Fare_Local"), resultSet.getString("Tax_LZ"), resultSet.getString("Tax_OTHS"), resultSet.getString("Total_Amount"), resultSet.getString("Cash_Amount"), resultSet.getString("CC_Number"), resultSet.getString("Total_Paid"), resultSet.getString("Commission")));
                counter++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Linking to IndividualInterlineDataModel.
        numberColumn.setCellValueFactory(new PropertyValueFactory<>("number"));
        blankNumberColumn.setCellValueFactory(new PropertyValueFactory<>("blankNumber"));
        fareUSDColumn.setCellValueFactory(new PropertyValueFactory<>("fareUSD"));
        exchangeRateColumn.setCellValueFactory(new PropertyValueFactory<>("exchangeRate"));
        fareLocalColumn.setCellValueFactory(new PropertyValueFactory<>("fareLocal"));
        taxLocalColumn.setCellValueFactory(new PropertyValueFactory<>("taxLocal"));
        taxOtherColumn.setCellValueFactory(new PropertyValueFactory<>("taxOther"));
        totalAmountColumn.setCellValueFactory(new PropertyValueFactory<>("totalAmount"));
        totalCashAmountColumn.setCellValueFactory(new PropertyValueFactory<>("cashAmount"));
        ccNumberColumn.setCellValueFactory(new PropertyValueFactory<>("ccNumber"));
        totalPaidColumn.setCellValueFactory(new PropertyValueFactory<>("totalPaid"));
        commissionColumn.setCellValueFactory(new PropertyValueFactory<>("commission"));

        //Display the items in JavaFX table view.
        IndividualInterlineTable.setItems(oblist);
        try {
            resultSet.beforeFirst();
            while (resultSet.next() != false) {
                //calculating the totals.
                finalTotalFareUSD += Float.parseFloat(resultSet.getString("Fare_USD"));
                finalTotalFareLocal += Float.parseFloat(resultSet.getString("Fare_Local"));
                finallocalTaxTotal += Float.parseFloat(resultSet.getString("Tax_LZ"));
                finalotherTaxTotal += Float.parseFloat(resultSet.getString("Tax_OTHS"));
                finalTotalAmount += Float.parseFloat(resultSet.getString("Total_Amount"));
                if (resultSet.getString("Cash_Amount") != null) {
                    finalcashAmount += Float.parseFloat(resultSet.getString("Cash_Amount"));
                }
                finalTotalPaid += Float.parseFloat(resultSet.getString("Total_Paid"));
                finalTotalCommission += Float.parseFloat(resultSet.getString("Commission"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //setting the final totals to the text on the fxml file.
        totalNumber.setText(Integer.toString(counter - 1));
        usdFareTotal.setText(Float.toString(finalTotalFareUSD));
        localFareTotal.setText(Float.toString(finalTotalFareLocal));
        totalTaxLocal.setText(Float.toString(finallocalTaxTotal));
        totalTaxOther.setText(Float.toString(finalotherTaxTotal));
        totalAmount.setText(Float.toString(finalTotalAmount));
        totalCashAmount.setText(Float.toString(finalcashAmount));
        totalPaidAmount.setText(Float.toString(finalTotalPaid));
        totalCommission.setText(Float.toString(finalTotalCommission));
        netAmount.setText(Float.toString(finalTotalAmount - finalTotalCommission));

    }

    //print method for printing to text file.
    @FXML
    public void Print() {
        try {
            File IIR = new File("./" + LoginStorage.getStaffNumber() + "_IndividualInterline.txt"); //name of file will be staff numbers.

            if (!IIR.exists()) {
                IIR.createNewFile();
            }

            FileWriter fw = new FileWriter(IIR, false);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(String.format("%20s %20s %20s %20s %20s %20s %20s %20s %20s %20s %20s %20s \r\n", "Number", "Blank_Number", "Fare_USD", "ConversionRate", "Fare_Local", "LocalTax", "OtherTax", "TotalAmount", "Cash_Amount", "CC_Number", "TotalPaid", "Commission")); //formatting evenly
            try {
                resultSet.beforeFirst();
                int newcounter = 1;
                while (resultSet.next()) {
                    bw.write(String.format("%20s %20s %20s %20s %20s %20s %20s %20s %20s %20s %20s %20s \r\n", Integer.toString(newcounter), resultSet.getString("Blank_Number"), resultSet.getString("Fare_USD"), resultSet.getString("Conversion_Rate"), resultSet.getString("Fare_Local"), resultSet.getString("Tax_LZ"), resultSet.getString("Tax_OTHS"), resultSet.getString("Total_Amount"), resultSet.getString("Cash_Amount"), resultSet.getString("CC_Number"), resultSet.getString("Total_Paid"), resultSet.getString("Commission")));
                    newcounter++;
                    //write every row in the result set to the file.
                }
                //also write the totals to the file.
                bw.write("Counted Number: " + Integer.toString(counter - 1) + "\n");
                bw.write("Total USD Fare: " + finalTotalFareUSD + "\n");
                bw.write("Total Local Fare: " + finalTotalFareLocal + "\n");
                bw.write("Total Tax Local: " + finallocalTaxTotal + "\n");
                bw.write("Total Tax Other: " + finalotherTaxTotal + "\n");
                bw.write("Total Amount: " + finalTotalAmount + "\n");
                bw.write("Total Cash: " + finalcashAmount + "\n");
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

    //go back to the last page, the generate individual reports page
    @FXML
    private void goBack() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("GenerateIndividualReports.fxml")); // load the file generateIndividualReports.fxml
            Stage stage = (Stage) namePlaceholder.getScene().getWindow();
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
        } catch (IOException io) {
            io.printStackTrace();
        }

    }


}
