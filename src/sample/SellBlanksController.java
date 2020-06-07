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

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class SellBlanksController {

    //Controller Links to SellBlanksPage.fxml

    //placeholders to be replaced in the top left.
    public Text namePlaceholder;
    public Text staffNumberPlaceholder;
    public Text rolePlaceholder;

    //Linking to FXML tables
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

    //text field search bar
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


    //Similar to initialize, filters 444 and displays
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

    //Similar to initialize, filters 420 and displays
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

    //Similar to initialize, filters 201 and displays
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

    //Reset to original view.
    @FXML
    public void ResetToOriginalView() {
        initialize();
    }

    //directs user to correct sale page.
    @FXML
    public void sellBlank() {
        if (BlankTable.getSelectionModel().getSelectedItem() != null) { //if blank selected
            if (BlankTable.getSelectionModel().getSelectedItem().getStatus().equals("Unsold")) { //and the status is unsold.
                String First3Characters = BlankTable.getSelectionModel().getSelectedItem().getBlank().substring(0, 3); //check the first 3 characters
                if (First3Characters.equals("444")) { //if 444
                    try {
                        BlankStorage.setBlankNumber(BlankTable.getSelectionModel().getSelectedItem().getBlank());
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("Sell444.fxml")); //direct to sell 444 fxml
                        Stage stage = (Stage) searchBar.getScene().getWindow();
                        Scene scene = new Scene(loader.load());
                        stage.setScene(scene);
                    } catch (IOException io) {
                        io.printStackTrace();
                    }
                } else if (First3Characters.equals("420")) { //if 420
                    try {
                        BlankStorage.setBlankNumber(BlankTable.getSelectionModel().getSelectedItem().getBlank());
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("Sell444.fxml")); //direct to 444 fxml
                        Stage stage = (Stage) searchBar.getScene().getWindow();
                        Scene scene = new Scene(loader.load());
                        stage.setScene(scene);
                    } catch (IOException io) {
                        io.printStackTrace();
                    }
                } else if (First3Characters.equals("201")) { //if 201
                    try {
                        BlankStorage.setBlankNumber(BlankTable.getSelectionModel().getSelectedItem().getBlank());
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("Sell201.fxml")); //direct to 201 fxml
                        Stage stage = (Stage) searchBar.getScene().getWindow();
                        Scene scene = new Scene(loader.load());
                        stage.setScene(scene);
                    } catch (IOException io) {
                        io.printStackTrace();
                    }
                } else if (First3Characters.equals("101")) { //if 101
                    try {
                        BlankStorage.setBlankNumber(BlankTable.getSelectionModel().getSelectedItem().getBlank());
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("Sell101.fxml"));//direct to 101 fxml
                        Stage stage = (Stage) searchBar.getScene().getWindow();
                        Scene scene = new Scene(loader.load());
                        stage.setScene(scene);
                    } catch (IOException io) {
                        io.printStackTrace();
                    }
                } else { //for custom
                    try {
                        BlankStorage.setBlankNumber(BlankTable.getSelectionModel().getSelectedItem().getBlank());
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("Sell101.fxml")); //direct to 101.
                        Stage stage = (Stage) searchBar.getScene().getWindow();
                        Scene scene = new Scene(loader.load());
                        stage.setScene(scene);
                    } catch (IOException io) {
                        io.printStackTrace();
                    }
                }

            } else {
                Stage secondaryStage = new Stage();
                Text doneText = new Text();
                Button okButton = new Button();
                doneText.setText("That Blank has already been Sold!"); //tells the user no need to sell blank as it has already been sold
                okButton.setText("OK");
                secondaryStage.setTitle("Already Sold!");
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
    }

    //Makes blanks void by updating status
    @FXML
    public void makeVoid() {
        if (BlankTable.getSelectionModel().getSelectedItem() != null) {
            try {
                sqlQuery = "UPDATE Blanks SET Is_Void = 'Yes' WHERE Blank='" + BlankTable.getSelectionModel().getSelectedItem().getBlank() + "'";
                Statement statement = connection.createStatement();
                statement.execute(sqlQuery);
                initialize();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    //Search for relevant blank
    @FXML
    public void Search() {
        if (searchBar.getText().length() != 0) { //if search is not empty
            BlankTable.getItems().stream().filter(item -> item.getBlank().equals(searchBar.getText())).findAny().ifPresentOrElse(item -> { //find it
                BlankTable.scrollTo(item); //scroll to item
                BlankTable.getSelectionModel().clearSelection(); //deselect
                BlankTable.getSelectionModel().select(item); //select new
            }, () -> {
                //else tell them its not there
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
            //else tell them they havent entered anything into search bar.
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

    //Go back method
    @FXML
    public void Back() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MainScreenTravelAdvisor.fxml")); //Load travel advisor main menu fxml
            Stage stage = (Stage) searchBar.getScene().getWindow();
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
        } catch (IOException io) {
            io.printStackTrace();
        }
    }
}
