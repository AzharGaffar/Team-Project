package sample;

import javafx.beans.property.ReadOnlyStringWrapper;
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

public class StockTurnoverReportController {

    //placeholders for the top left
    public Text namePlaceholder;
    public Text staffNumberPlaceholder;
    public Text rolePlaceholder;

    //storage
    public float finalTotalFareUSD = 0;
    public float finalTotalFareLocal = 0;
    public float finallocalTaxTotal = 0;
    public float finalotherTaxTotal = 0;
    public float finalTotalAmount = 0;
    public float finalcashAmount = 0;
    public float finalTotalPaid = 0;
    public float finalTotalCommission = 0;

    //initialize a connection to the database.
    DatabaseConnection databaseConnection = new DatabaseConnection();
    Connection connection = databaseConnection.getConnection();

    //storage for resultset and sql.
    public String sqlQuery;
    public ResultSet resultSet, resultSet2, resultSet3;

    //All the text that is on the fxml page.
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

    //Linking FXMl table to java
    @FXML
    private TableView<StockTurnoverDataModel1> StockTurnoverTable1;
    @FXML
    private TableView<StockTurnoverDataModel2> StockTurnoverTable2;
    @FXML
    private TableView<StockTurnoverDataModel3> StockTurnoverTable3;
    @FXML
    private TableColumn<StockTurnoverDataModel1, String> received444Column;
    @FXML
    private TableColumn<StockTurnoverDataModel1, String> received420Column;
    @FXML
    private TableColumn<StockTurnoverDataModel1, String> received201Column;
    @FXML
    private TableColumn<StockTurnoverDataModel1, String> received101Column;
    @FXML
    private TableColumn<StockTurnoverDataModel2, String> advisorCodeColumn;
    @FXML
    private TableColumn<StockTurnoverDataModel2, String> used444Column;
    @FXML
    private TableColumn<StockTurnoverDataModel2, String> used420Column;
    @FXML
    private TableColumn<StockTurnoverDataModel2, String> used201Column;
    @FXML
    private TableColumn<StockTurnoverDataModel2, String> used101Column;
    @FXML
    private TableColumn<StockTurnoverDataModel3, String> unused444Column;
    @FXML
    private TableColumn<StockTurnoverDataModel3, String> unused420Column;
    @FXML
    private TableColumn<StockTurnoverDataModel3, String> unused201Column;
    @FXML
    private TableColumn<StockTurnoverDataModel3, String> unused101Column;

    //three new observable lists
    ObservableList<StockTurnoverDataModel1> oblist1 = FXCollections.observableArrayList();
    ObservableList<StockTurnoverDataModel2> oblist2 = FXCollections.observableArrayList();
    ObservableList<StockTurnoverDataModel3> oblist3 = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        //replace placeholders
        namePlaceholder.setText(LoginStorage.getFirstName());
        staffNumberPlaceholder.setText(LoginStorage.getStaffNumber());
        rolePlaceholder.setText(LoginStorage.getRole());

        try {

            //I want everything selected from the relevant table and put into resultsets.
            resultSet = connection.createStatement().executeQuery("SELECT DISTINCT (SELECT Count(*) FROM Blanks WHERE Blank like '444%') as Received444, (SELECT Count(*) FROM Blanks WHERE Blank like '420%') as Received420, (SELECT Count(*) FROM Blanks WHERE Blank like '201%') as Received201,(SELECT Count(*) FROM Blanks WHERE Blank like '101%') as Received101 FROM Blanks WHERE Date_Added BETWEEN '" + GenerateReportStorage.getDate1() + "' AND '" + GenerateReportStorage.getDate2() + "'");
            resultSet2 = connection.createStatement().executeQuery("SELECT sold_by_Ta as TACode, Count(case when Blank_Number LIKE '444%' then 1 end) as Used444, Count(case when Blank_Number LIKE '420%' then 1 end) as Used420, Count(case when Blank_Number LIKE '201%' then 1 end) as Used201,  Count(case when Blank_Number LIKE '101%' then 1 end) as Used101 From Sold WHERE Date_Of_Sale BETWEEN '" + GenerateReportStorage.getDate1() + "' AND '" + GenerateReportStorage.getDate2() + "' group by Sold_By_TA");
            resultSet3 = connection.createStatement().executeQuery("SELECT Count(case when Blank LIKE '444%' and status='Unsold' then 1 end) as Unused444, Count(case when Blank LIKE '420%' and status='Unsold' then 1 end) as Unused420, Count(case when Blank LIKE '201%' and status='Unsold'  then 1 end) as Unused201,  Count(case when Blank LIKE '101%' and status='Unsold'  then 1 end) as Unused101 From Blanks WHERE Date_Added BETWEEN '" + GenerateReportStorage.getDate1() + "' AND '" + GenerateReportStorage.getDate2() + "'group by AssignedTo");


            //Keep adding results to the list.
            while (resultSet.next()) {
                oblist1.add(new StockTurnoverDataModel1(resultSet.getString("Received444"), resultSet.getString("Received420"), resultSet.getString("Received201"), resultSet.getString("Received101")));
            }
            //Keep adding results to the list.
            while (resultSet2.next()) {
                oblist2.add(new StockTurnoverDataModel2(resultSet2.getString("TACode"), resultSet2.getString("Used444"), resultSet2.getString("Used420"), resultSet2.getString("Used201"), resultSet2.getString("Used101")));
            }
            //Keep adding results to the list.
            while (resultSet3.next()) {
                oblist3.add(new StockTurnoverDataModel3(resultSet3.getString("Unused444"), resultSet3.getString("Unused420"), resultSet3.getString("Unused201"), resultSet3.getString("Unused101")));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        //Linking columns to data models
        received444Column.setCellValueFactory(new PropertyValueFactory<>("received444"));
        received420Column.setCellValueFactory(new PropertyValueFactory<>("received420"));
        received201Column.setCellValueFactory(new PropertyValueFactory<>("received201"));
        received101Column.setCellValueFactory(new PropertyValueFactory<>("received101"));
        advisorCodeColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getTaCode()));
        used444Column.setCellValueFactory(new PropertyValueFactory<>("used444"));
        used420Column.setCellValueFactory(new PropertyValueFactory<>("used420"));
        used201Column.setCellValueFactory(new PropertyValueFactory<>("used201"));
        used101Column.setCellValueFactory(new PropertyValueFactory<>("used101"));
        unused444Column.setCellValueFactory(new PropertyValueFactory<>("unused444"));
        unused420Column.setCellValueFactory(new PropertyValueFactory<>("unused420"));
        unused201Column.setCellValueFactory(new PropertyValueFactory<>("unused201"));
        unused101Column.setCellValueFactory(new PropertyValueFactory<>("unused101"));

        //Display the items in JavaFX table view.
        StockTurnoverTable1.setItems(oblist1);
        StockTurnoverTable2.setItems(oblist2);
        StockTurnoverTable3.setItems(oblist3);

    }

    //Print method prints to text file
    @FXML
    public void Print() {
        try {
            File STR = new File("./StockTurnoverReport.txt"); //Save as StockTurnoverReport.

            if (!STR.exists()) {
                STR.createNewFile();
            }

            FileWriter fw = new FileWriter(STR, false);
            BufferedWriter bw = new BufferedWriter(fw);
            try {
                resultSet.beforeFirst();
                int newcounter = 1;
                bw.write(String.format("%20s %20s %20s %20s\r\n", "Received444", "Received420", "Received201", "Received101")); //print to file format evenly.
                while (resultSet.next()) {
                    bw.write(String.format("%20s %20s %20s %20s\r\n", resultSet.getString("Received444"), resultSet.getString("Received420"), resultSet.getString("Received201"), resultSet.getString("Received101"))); //write result set to file.
                    newcounter++;
                }

                bw.write("\n");
                bw.write(String.format("%20s %20s %20s %20s %20s\r\n", "TACode", "Used444", "Used420", "Used201", "Used101")); //write to file
                resultSet2.beforeFirst();
                while (resultSet2.next()) {
                    bw.write(String.format("%20s %20s %20s %20s %20s  \r\n", resultSet2.getString("TACode"), resultSet2.getString("Used444"), resultSet2.getString("Used420"), resultSet2.getString("Used201"), resultSet2.getString("Used101")));
                    newcounter++;
                    //write result set to file evenly
                }

                bw.write("\n");
                bw.write(String.format("%20s %20s %20s %20s\r\n", "Unused444", "Unused420", "Used201", "Used101")); //write to file
                resultSet3.beforeFirst();
                while (resultSet3.next()) {
                    bw.write(String.format("%20s %20s %20s %20s \r\n", resultSet3.getString("Unused444"), resultSet3.getString("Unused420"), resultSet3.getString("Unused201"), resultSet3.getString("Unused101")));
                    newcounter++;
                    //write resultset to file evenly.
                }
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


    //go back method calls the last page
    @FXML
    private void goBack() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MainScreenSystemAdministrator.fxml")); //load system administrator main memu fxml
            Stage stage = (Stage) namePlaceholder.getScene().getWindow();
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
        } catch (IOException io) {
            io.printStackTrace();
        }

    }
}
