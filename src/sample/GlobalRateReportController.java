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

public class GlobalRateReportController {

    //soon to be replaced in top left placeholders.
    public Text namePlaceholder;
    public Text staffNumberPlaceholder;
    public Text rolePlaceholder;

    //used for calculating final amounts.
    public int finalTotalNumberOfSales = 0;

    //getting database connection to sql database.
    DatabaseConnection databaseConnection = new DatabaseConnection();
    Connection connection = databaseConnection.getConnection();

    //string and result set for sql storage and the like.
    public String sqlQuery;
    public ResultSet resultSet;

    //text labels that are in the fxml file
    public Text totalNumberOfSales;
    public Text NumberOfRates;

    //counter
    public int counter = 1;

    //tableview from fxml file with its columns.
    @FXML
    private TableView<GlobalRateDataModel> GlobalRateTable;
    @FXML
    private TableColumn<GlobalRateDataModel, Integer> numberColumn;
    @FXML
    private TableColumn<GlobalRateDataModel, String> exchangeRateColumn;
    @FXML
    private TableColumn<GlobalRateDataModel, String> totalNumberOfSalesColumn;

    //new list for everything to be added to.
    ObservableList<GlobalRateDataModel> oblist = FXCollections.observableArrayList();

    //first method to be called.
    @FXML
    public void initialize() {
        //replace placeholders in top left.
        namePlaceholder.setText(LoginStorage.getFirstName());
        staffNumberPlaceholder.setText(LoginStorage.getStaffNumber());
        rolePlaceholder.setText(LoginStorage.getRole());

        try {

            //I want everything relevant selected from sold table and put into a resultset.
            resultSet = connection.createStatement().executeQuery("SELECT Conversion_Rate, COUNT(Blank_Number) AS Total_Sales FROM Sold WHERE Date_of_Sale BETWEEN '" + GenerateReportStorage.getDate1() + "' AND '" + GenerateReportStorage.getDate2() + "' AND Blank_Type='Interline' Group BY Conversion_Rate");


            //Keep adding results to the list while there are records.
            while (resultSet.next()) {
                oblist.add(new GlobalRateDataModel(counter, resultSet.getString("Conversion_Rate"), resultSet.getInt("Total_Sales")));
                counter++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //linking to GlobalRateDataModel
        numberColumn.setCellValueFactory(new PropertyValueFactory<>("number"));
        exchangeRateColumn.setCellValueFactory(new PropertyValueFactory<>("exchangeRate"));
        totalNumberOfSalesColumn.setCellValueFactory(new PropertyValueFactory<>("totalNumberOfSales"));

        //Display the items in JavaFX table view.
        GlobalRateTable.setItems(oblist);
        try {
            resultSet.beforeFirst();
            while (resultSet.next() != false) {
                //calculate finals
                finalTotalNumberOfSales += resultSet.getInt("Total_Sales");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //setting the text for the finals to be displayed.
        NumberOfRates.setText(Integer.toString(counter - 1));
        totalNumberOfSales.setText(Integer.toString(finalTotalNumberOfSales));

    }

    //print to file.
    @FXML
    public void Print() {
        try {
            File GRR = new File("./GlobalRateReport.txt"); //name

            if (!GRR.exists()) {
                GRR.createNewFile();
            }

            FileWriter fw = new FileWriter(GRR, false);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(String.format("%20s %20s %20s \r\n", "Number", "ConversionRate", "TotalSales")); //format even columns with the headers.
            try {
                resultSet.beforeFirst();
                int newcounter = 1;
                while (resultSet.next()) {
                    bw.write(String.format("%20s %20s %20s \r\n", Integer.toString(newcounter), resultSet.getString("Conversion_Rate"), resultSet.getInt("Total_Sales"))); //add everything from result set to file.
                    newcounter++;
                }
                bw.write("Counted Number: " + Integer.toString(counter - 1) + "\n"); //write totals to file.
                bw.write("Total sales: " + finalTotalNumberOfSales + "\n");
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


    //load the last page
    @FXML
    private void goBack() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("GenerateReportsMenu.fxml")); //load generatereportsmenu fxml file.
            Stage stage = (Stage) namePlaceholder.getScene().getWindow();
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
        } catch (IOException io) {
            io.printStackTrace();
        }

    }


}
