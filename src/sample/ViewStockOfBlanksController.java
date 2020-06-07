package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class ViewStockOfBlanksController {


    //Controller Links to SellBlanksPage.fxml

    //placeholders to be replaced.
    public Text namePlaceholder;
    public Text staffNumberPlaceholder;
    public Text rolePlaceholder;

    //Linking FXML tableview and columns to java
    @FXML
    private TableView<BlankTableDataModel> BlankTable;
    @FXML
    private TableColumn<BlankTableDataModel, String> blankColumn;
    @FXML
    private TableColumn<BlankTableDataModel, String> NoCColumn;
    @FXML
    private TableColumn<BlankTableDataModel, String> isVoidColumn;
    @FXML
    private TableColumn<BlankTableDataModel, String> statusColumn;
    @FXML
    private TableColumn<BlankTableDataModel, String> dateAddedColumn;


    //Making a new list that everything can be stored in.
    ObservableList<BlankTableDataModel> oblist = FXCollections.observableArrayList();

    //Establishing a connection for SQL queries to go through.
    DatabaseConnection databaseConnection = new DatabaseConnection();
    Connection connection = databaseConnection.getConnection();
    String sqlQuery;
    ResultSet resultSet;

    public TextField searchBar;

    // Initialize method puts everything from SQL table into the GUI Table.
    @FXML
    public void initialize() {

        namePlaceholder.setText(LoginStorage.getFirstName());
        staffNumberPlaceholder.setText(LoginStorage.getStaffNumber());
        rolePlaceholder.setText(LoginStorage.getRole());

        try {
            BlankTable.getItems().clear(); //used for refreshing the table

            //I want everything selected from the users table and put into a resultset.
            resultSet = connection.createStatement().executeQuery("SELECT * FROM Blanks WHERE AssignedTo= '" + LoginStorage.getUsername() + "'");


            //Keep adding results to the list.
            while (resultSet.next()) {
                oblist.add(new BlankTableDataModel(resultSet.getString("Blank"), resultSet.getString("Number_of_Coupons"), resultSet.getString("Is_Void"), resultSet.getString("Status"), resultSet.getDate("Date_Added"), resultSet.getString("AssignedTo")));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Linking the FXML Columns to the "StaffTableDataModel" class.
        blankColumn.setCellValueFactory(new PropertyValueFactory<>("Blank"));
        NoCColumn.setCellValueFactory(new PropertyValueFactory<>("Number_of_Coupons"));
        isVoidColumn.setCellValueFactory(new PropertyValueFactory<>("Is_Void"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("Status"));
        dateAddedColumn.setCellValueFactory(new PropertyValueFactory<>("Date"));

        //Display the items in JavaFX table view.
        BlankTable.setItems(oblist);

    }

    //filters 444 blanks similar to initialize method
    @FXML
    public void Filter444() {
        try {
            BlankTable.getItems().clear();
            Statement statement = connection.createStatement();
            sqlQuery = "SELECT * FROM Blanks WHERE Blank LIKE '444%' AND AssignedTo='" + LoginStorage.getUsername() + "'";
            resultSet = statement.executeQuery(sqlQuery);
            while (resultSet.next()) {
                oblist.add(new BlankTableDataModel(resultSet.getString("Blank"), resultSet.getString("Number_of_Coupons"), resultSet.getString("Is_Void"), resultSet.getString("Status"), resultSet.getDate("Date_Added"), resultSet.getString("AssignedTo")));
            }
            BlankTable.setItems(oblist);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    //filters 420 blanks similar to initialize method
    @FXML
    public void Filter420() {
        try {
            BlankTable.getItems().clear();
            Statement statement = connection.createStatement();
            sqlQuery = "SELECT * FROM Blanks WHERE Blank LIKE '420%' AND AssignedTo='" + LoginStorage.getUsername() + "'";
            resultSet = statement.executeQuery(sqlQuery);
            while (resultSet.next()) {
                oblist.add(new BlankTableDataModel(resultSet.getString("Blank"), resultSet.getString("Number_of_Coupons"), resultSet.getString("Is_Void"), resultSet.getString("Status"), resultSet.getDate("Date_Added"), resultSet.getString("AssignedTo")));
            }
            BlankTable.setItems(oblist);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //filters 201 blanks similar to initialize method
    @FXML
    public void Filter201() {
        try {
            BlankTable.getItems().clear();
            Statement statement = connection.createStatement();
            sqlQuery = "SELECT * FROM Blanks WHERE Blank LIKE '201%' AND AssignedTo='" + LoginStorage.getUsername() + "'";
            resultSet = statement.executeQuery(sqlQuery);
            while (resultSet.next()) {
                oblist.add(new BlankTableDataModel(resultSet.getString("Blank"), resultSet.getString("Number_of_Coupons"), resultSet.getString("Is_Void"), resultSet.getString("Status"), resultSet.getDate("Date_Added"), resultSet.getString("AssignedTo")));
            }
            BlankTable.setItems(oblist);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //filters 101 blanks similar to initialize method
    @FXML
    public void Filter101() {
        try {
            BlankTable.getItems().clear();
            Statement statement = connection.createStatement();
            sqlQuery = "SELECT * FROM Blanks WHERE Blank LIKE '101%' AND AssignedTo='" + LoginStorage.getUsername() + "'";
            resultSet = statement.executeQuery(sqlQuery);
            while (resultSet.next()) {
                oblist.add(new BlankTableDataModel(resultSet.getString("Blank"), resultSet.getString("Number_of_Coupons"), resultSet.getString("Is_Void"), resultSet.getString("Status"), resultSet.getDate("Date_Added"), resultSet.getString("AssignedTo")));
            }
            BlankTable.setItems(oblist);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //reset to original view by calling initialize method.
    @FXML
    public void ResetToOriginalView() {
        initialize();
    }


    //searches for blank.
    @FXML
    public void Search() {
        if (searchBar.getText().length() != 0) { //if something is entered into textfield
            BlankTable.getItems().stream().filter(item -> item.getBlank().equals(searchBar.getText())).findAny().ifPresentOrElse(item -> { //if present
                BlankTable.scrollTo(item); //scroll to it
                BlankTable.getSelectionModel().clearSelection(); //deselct old
                BlankTable.getSelectionModel().select(item); //select new
            }, () -> {
                // else display message not in database
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
            //pressed search button without entering anything
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

    //back method loads the main screen travel advisor
    @FXML
    public void Back() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MainScreenTravelAdvisor.fxml"));
            Stage stage = (Stage) searchBar.getScene().getWindow();
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
        } catch (IOException io) {
            io.printStackTrace();
        }
    }
}
