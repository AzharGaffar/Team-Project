package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
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

public class LatePaymentsController {

    //These are the staff details that are supposed to be replaced later on.
    public Text namePlaceholder;
    public Text staffNumberPlaceholder;
    public Text rolePlaceholder;

    public String blankNumber;

    //dateOfRefund needs to be stored.
    public String dateOfRefund;

    public boolean latepaymentConfirmed = false;
    public boolean latepaymentContinue = false;

    //SQL query will go in this string.
    public String sqlQuery;
    public String newQuery = "";

    //On the FXML file there is a searchbar textfield.
    public TextField searchBar;

    //resultset data type for results to be stored in.
    public ResultSet resultSet;

    //Linking the FXML table to the java program.
    @FXML
    private TableView<LatePaymentsDataModel> LatePaymentsTable;
    @FXML
    private TableColumn<LatePaymentsDataModel, String> blankNumberColumn;
    @FXML
    private TableColumn<LatePaymentsDataModel, String> ownerColumn;
    @FXML
    private TableColumn<LatePaymentsDataModel, String> statusColumn;
    @FXML
    private TableColumn<LatePaymentsDataModel, String> dateOfSaleColumn;
    @FXML
    private TableColumn<LatePaymentsDataModel, String> dueDateColumn;
    @FXML
    private TableColumn<LatePaymentsDataModel, String> balanceOutstandingColumn;
    @FXML
    private TableColumn<LatePaymentsDataModel, String> seenByOMColumn;

    //A new list called an observable list is created.
    ObservableList<LatePaymentsDataModel> oblist = FXCollections.observableArrayList();

    //Establishing a connection to the database.
    DatabaseConnection databaseConnection = new DatabaseConnection();
    Connection connection = databaseConnection.getConnection();

    //Initialize method executes an sql statement to populate the table.
    @FXML
    public void initialize() {
        //Replace the old placeholders with the correct logged in information.
        namePlaceholder.setText(LoginStorage.getFirstName());
        staffNumberPlaceholder.setText(LoginStorage.getStaffNumber());
        rolePlaceholder.setText(LoginStorage.getRole());

        try {
            LatePaymentsTable.getItems().clear(); //used for refreshing the table

            //I want everything selected from the users table and put into a resultset.
            resultSet = connection.createStatement().executeQuery("SELECT * FROM LatePayment"); //select everything from latepayment and store.

            //While result set has a next bit
            while (resultSet.next()) {
                oblist.add(new LatePaymentsDataModel(resultSet.getString("Blank_Number"), resultSet.getString("Owner"), resultSet.getString("Status"), resultSet.getDate("Date_of_Sale"), resultSet.getDate("Due_Date"), resultSet.getString("Balance_Outstanding"), resultSet.getString("Old_Status"), resultSet.getString("Seen_By_OM")));
                //Add the results to the oblist.
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Linking the FXML Columns to the "LatePaymentDataModel" class.
        blankNumberColumn.setCellValueFactory(new PropertyValueFactory<>("blankNumber"));
        ownerColumn.setCellValueFactory(new PropertyValueFactory<>("owner"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        balanceOutstandingColumn.setCellValueFactory(new PropertyValueFactory<>("balanceOustanding"));
        dateOfSaleColumn.setCellValueFactory(new PropertyValueFactory<>("dateOfSale"));
        dueDateColumn.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
        seenByOMColumn.setCellValueFactory(new PropertyValueFactory<>("seenByOM"));

        //Display the items in JavaFX table view.
        LatePaymentsTable.setItems(oblist);

    }

    //Button to set the user as paid.
    @FXML
    public void Paid() {
        if (LatePaymentsTable.getSelectionModel().getSelectedItem() != null) { //if Selected item from late payments table is not null.
            if (!LatePaymentsTable.getSelectionModel().getSelectedItem().getBalanceOustanding().equals("0")) {
                LatePaymentConfirmed.setHolder(LatePaymentsTable.getSelectionModel().getSelectedItem().getBlankNumber());//if the balance outstanding is not zero, as that indicates they've already paid.
                LatePaymentConfirmed.setHolder2(LatePaymentsTable.getSelectionModel().getSelectedItem().getOldStatus());
                LatePaymentConfirmed.setHolder3(LatePaymentsTable.getSelectionModel().getSelectedItem().getOwner());
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("LatePaymentDate.fxml"));
                    Parent root1 = (Parent) fxmlLoader.load();
                    Stage stage = new Stage();
                    stage.setScene(new Scene(root1));
                    stage.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }


    //Search latepayment records for blank number.
    @FXML
    public void Search() {
        if (searchBar.getText().length() != 0) {
            LatePaymentsTable.getItems().stream().filter(item -> item.getBlankNumber().equals(searchBar.getText())).findAny().ifPresentOrElse(item -> { //if present
                LatePaymentsTable.scrollTo(item); //scroll to the item.
                LatePaymentsTable.getSelectionModel().clearSelection(); //clear what was previously selected.
                LatePaymentsTable.getSelectionModel().select(item); //select new item.
            }, () -> {
                //Else inform the user that their search query is not in the database.
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
                    } //close the secondary stage.
                });
            });
        } else {
            //Nothing entered into the search bar window
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
                } //close the secondary stage.
            });
        }

    }

    //Deletes the late payment notification from the database.
    @FXML
    public void Delete() {
        try {
            sqlQuery = "DELETE FROM LatePayment WHERE Blank_Number = '" + LatePaymentsTable.getSelectionModel().getSelectedItem().getBlankNumber() + "'"; //SQL query for delete.
            Statement statement = connection.createStatement();
            statement.execute(sqlQuery); //send it to the database.
            initialize(); //refreshes the database.
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //Back method loads the last page/
    @FXML
    public void Back() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MainScreenOfficeManager.fxml")); //Load the main screen of the office manager.
            Stage stage = (Stage) namePlaceholder.getScene().getWindow();
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
        } catch (IOException io) {
            io.printStackTrace();
        }
    }

}

