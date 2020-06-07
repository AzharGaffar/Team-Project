package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class RefundBlankController {

    //Controller Links to RefundBlanksPage.fxml

    //Placeholders from the fxml file top left.
    public Text namePlaceholder;
    public Text staffNumberPlaceholder;
    public Text rolePlaceholder;
    public String dateOfRefund;

    public TextField dateField;

    //Linking to FXML file
    @FXML
    private TableView<SoldDataModel> SoldTable;
    @FXML
    private TableColumn<SoldDataModel, String> blankColumn;
    @FXML
    private TableColumn<SoldDataModel, String> totalPaidColumn;
    @FXML
    private TableColumn<SoldDataModel, String> soldByColumn;
    @FXML
    private TableColumn<SoldDataModel, Integer> soldToColumn;
    @FXML
    private TableColumn<SoldDataModel, String> blankTypeColumn;


    //Making a new list that everything can be stored in.
    ObservableList<SoldDataModel> oblist = FXCollections.observableArrayList();

    //Establishing a connection for SQL queries to go through.
    DatabaseConnection databaseConnection = new DatabaseConnection();
    Connection connection = databaseConnection.getConnection();
    String sqlQuery;
    ResultSet resultSet;

    //Textfield search bar initialized that is from the FXML file.
    public TextField searchBar;

    // Initialize method puts everything from SQL table into the GUI Table.
    @FXML
    public void initialize() {

        //replace the placeholders.
        namePlaceholder.setText(LoginStorage.getFirstName());
        staffNumberPlaceholder.setText(LoginStorage.getStaffNumber());
        rolePlaceholder.setText(LoginStorage.getRole());

        try {
            SoldTable.getItems().clear(); //used for refreshing the table

            //I want everything selected from the sold table and put into a resultset.
            resultSet = connection.createStatement().executeQuery("SELECT * FROM Sold");


            //Keep adding results to the list.
            while (resultSet.next()) {
                oblist.add(new SoldDataModel(resultSet.getString("Blank_Number"), resultSet.getString("Total_Paid"), resultSet.getString("Sold_By_TA"), resultSet.getInt("Sold_To"), resultSet.getString("Blank_Type")));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Linking the FXML Columns to the SoldDateModel class.
        blankColumn.setCellValueFactory(new PropertyValueFactory<>("BlankNumber"));
        totalPaidColumn.setCellValueFactory(new PropertyValueFactory<>("TotalPaid"));
        soldByColumn.setCellValueFactory(new PropertyValueFactory<>("SoldBy"));
        soldToColumn.setCellValueFactory(new PropertyValueFactory<>("SoldToTA"));
        blankTypeColumn.setCellValueFactory(new PropertyValueFactory<>("BlankType"));

        //Display the items in JavaFX table view.
        SoldTable.setItems(oblist);

    }


    //Button filters all the blanks except 444, similar to initialize method.
    @FXML
    public void Filter444() {
        try {
            SoldTable.getItems().clear();
            Statement statement = connection.createStatement();
            sqlQuery = "SELECT * FROM Sold WHERE Blank_Number LIKE '444%'";
            resultSet = statement.executeQuery(sqlQuery);
            while (resultSet.next()) {
                oblist.add(new SoldDataModel(resultSet.getString("Blank_Number"), resultSet.getString("Total_Paid"), resultSet.getString("Sold_By_TA"), resultSet.getInt("Sold_To"), resultSet.getString("Blank_Type")));
            }
            SoldTable.setItems(oblist);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    //Button filters all the blanks except 420, similar to initialize method.
    @FXML
    public void Filter420() {
        try {
            SoldTable.getItems().clear();
            Statement statement = connection.createStatement();
            sqlQuery = "SELECT * FROM Sold WHERE Blank_Number LIKE '420%'";
            resultSet = statement.executeQuery(sqlQuery);
            while (resultSet.next()) {
                oblist.add(new SoldDataModel(resultSet.getString("Blank_Number"), resultSet.getString("Total_Paid"), resultSet.getString("Sold_By_TA"), resultSet.getInt("Sold_To"), resultSet.getString("Blank_Type")));
            }
            SoldTable.setItems(oblist);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    //Button filters all the blanks except 201, similar to initialize method.
    @FXML
    public void Filter201() {
        try {
            SoldTable.getItems().clear();
            Statement statement = connection.createStatement();
            sqlQuery = "SELECT * FROM Sold WHERE Blank_Number LIKE '201%'";
            resultSet = statement.executeQuery(sqlQuery);
            while (resultSet.next()) {
                oblist.add(new SoldDataModel(resultSet.getString("Blank_Number"), resultSet.getString("Total_Paid"), resultSet.getString("Sold_By_TA"), resultSet.getInt("Sold_To"), resultSet.getString("Blank_Type")));
            }
            SoldTable.setItems(oblist);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    //Button filters all the blanks except 101, similar to initialize method.
    @FXML
    public void Filter101() {
        try {
            SoldTable.getItems().clear();
            Statement statement = connection.createStatement();
            sqlQuery = "SELECT * FROM Sold WHERE Blank_Number LIKE '101%'";
            resultSet = statement.executeQuery(sqlQuery);
            while (resultSet.next()) {
                oblist.add(new SoldDataModel(resultSet.getString("Blank_Number"), resultSet.getString("Total_Paid"), resultSet.getString("Sold_By_TA"), resultSet.getInt("Sold_To"), resultSet.getString("Blank_Type")));
            }
            SoldTable.setItems(oblist);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    //calls initialize method as a refresh.
    @FXML
    public void ResetToOriginalView() {
        initialize();
    }

    //Refund method.
    @FXML
    public void Refund() {
        if (SoldTable.getSelectionModel().getSelectedItem() != null) { //if the user has selected something in the sold table.
            GenerateReportStorage.setDate1(dateField.getText());
            String selected = SoldTable.getSelectionModel().getSelectedItem().getBlankNumber(); //storing the blank number.
            try {
                sqlQuery = "DELETE FROM SOLD WHERE Blank_Number='" + selected + "'"; //delete from sold table.
                Statement statement = connection.createStatement();
                statement.execute(sqlQuery);
                sqlQuery = "UPDATE Blanks SET Status= 'Unsold'  WHERE Blank='" + selected + "'"; //set status unsold.
                statement.execute(sqlQuery);
                sqlQuery = "DElETE FROM LatePayment WHERE Blank_Number='" + selected + "'"; //delete from latepayment if exists anymore.
                statement.execute(sqlQuery);
                initialize();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                //write all the data to a log file.
                String data = '\n' + selected + " returned and full refund given as of " + GenerateReportStorage.getDate1();
                File refundFile = new File("./RefundLog.txt");

                if (!refundFile.exists()) {
                    refundFile.createNewFile();
                }

                FileWriter fw = new FileWriter(refundFile, true); //append is on.
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write(data);
                bw.close();
                System.out.println("Refund Data Written");
            } catch (IOException ioe) {
                System.out.println("Exception occurred:");
                ioe.printStackTrace();
            }
        }
        initialize();
    }

    //Search method looks for a search query in the current table.
    @FXML
    public void Search() {
        if (searchBar.getText().length() != 0) {
            SoldTable.getItems().stream().filter(item -> item.getBlankNumber().equals(searchBar.getText())).findAny().ifPresentOrElse(item -> { //if present
                SoldTable.scrollTo(item); //scroll to item
                SoldTable.getSelectionModel().clearSelection(); //deselect.
                SoldTable.getSelectionModel().select(item); //select new.
            }, () -> {
                //new window telling the user its not in the database.
                Stage secondaryStage = new Stage();
                Text doneText = new Text();
                Button okButton = new Button();
                doneText.setText("Not in the Database! Check spelling.");
                okButton.setText("OK");
                secondaryStage.setTitle("Enter into search bar!");
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
                    }
                });
            });
        } else {
            //new window telling the user they haven't entered anything into the search bar but they pressed the button.
            Stage secondaryStage = new Stage();
            Text doneText = new Text();
            Button okButton = new Button();
            doneText.setText("Nothing entered into search bar!");
            okButton.setText("OK");
            secondaryStage.setTitle("Enter into search bar!");
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
                }
            });
        }

    }

    //valid date checker returns boolean.
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

    //loads the last page.
    @FXML
    public void Back() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MainScreenTravelAdvisor.fxml")); //load te main screen travel advisor.fxml file.
            Stage stage = (Stage) searchBar.getScene().getWindow();
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
        } catch (IOException io) {
            io.printStackTrace();
        }
    }
}
