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

public class AddBlanksController {

    //Controller Links to AddBlanksPage.fxml

    //declaring placeholders
    public Text namePlaceholder;
    public Text staffNumberPlaceholder;
    public Text rolePlaceholder;
    LoginStorage loginStorage;

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


    //Making a new list that everything can be stored in to populate table.
    ObservableList<BlankTableDataModel> oblist = FXCollections.observableArrayList();

    //Establishing a connection for SQL queries to go through.
    DatabaseConnection databaseConnection = new DatabaseConnection();
    Connection connection = databaseConnection.getConnection();

    //Strings for sql queries.
    String sqlQuery;
    //sqlqueries get stored in here.
    ResultSet resultSet;

    //textfield for the searchbar.
    public TextField searchBar;

    // Initialize method puts everything from observable list into database.
    @FXML
    public void initialize() {

        //overwriting placeholders.
        namePlaceholder.setText(LoginStorage.getFirstName());
        staffNumberPlaceholder.setText(LoginStorage.getStaffNumber());
        rolePlaceholder.setText(LoginStorage.getRole());

        try {
            BlankTable.getItems().clear(); //used for refreshing the table

            //I want everything selected from the users table and put into a resultset.
            resultSet = connection.createStatement().executeQuery("SELECT * FROM Blanks");


            //while there are still results in the database, add them to this observable list.
            while (resultSet.next()) {
                oblist.add(new BlankTableDataModel(resultSet.getString("Blank"), resultSet.getString("Number_of_Coupons"), resultSet.getString("Is_Void"), resultSet.getString("Status"), resultSet.getDate("Date_Added"), resultSet.getString("AssignedTo")));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Multiselect enabled
        BlankTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        //Linking the FXML Columns to the "StaffTableDataModel" class.
        blankColumn.setCellValueFactory(new PropertyValueFactory<>("Blank"));
        NoCColumn.setCellValueFactory(new PropertyValueFactory<>("Number_of_Coupons"));
        isVoidColumn.setCellValueFactory(new PropertyValueFactory<>("Is_Void"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("Status"));
        dateAddedColumn.setCellValueFactory(new PropertyValueFactory<>("Date"));

        //Display the items in JavaFX table view.
        BlankTable.setItems(oblist);

    }


    //Button uses an sql query to select all the 444 blanks and display them in the table, similar to the initialize method.
    @FXML
    public void Filter444() {
        try {
            BlankTable.getItems().clear();
            Statement statement = connection.createStatement();
            sqlQuery = "SELECT * FROM Blanks WHERE Blank LIKE '444%'";
            resultSet = statement.executeQuery(sqlQuery);
            while (resultSet.next()) {
                oblist.add(new BlankTableDataModel(resultSet.getString("Blank"), resultSet.getString("Number_of_Coupons"), resultSet.getString("Is_Void"), resultSet.getString("Status"), resultSet.getDate("Date_Added"), resultSet.getString("AssignedTo")));
            }
            BlankTable.setItems(oblist);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    //Button uses an sql query to select all the 420 blanks and display them in the table, similar to the initialize method.
    @FXML
    public void Filter420() {
        try {
            BlankTable.getItems().clear();
            Statement statement = connection.createStatement();
            sqlQuery = "SELECT * FROM Blanks WHERE Blank LIKE '420%'";
            resultSet = statement.executeQuery(sqlQuery);
            while (resultSet.next()) {
                oblist.add(new BlankTableDataModel(resultSet.getString("Blank"), resultSet.getString("Number_of_Coupons"), resultSet.getString("Is_Void"), resultSet.getString("Status"), resultSet.getDate("Date_Added"), resultSet.getString("AssignedTo")));
            }
            BlankTable.setItems(oblist);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //Button uses an sql query to select all the 201 blanks and display them in the table, similar to the initialize method.
    @FXML
    public void Filter201() {
        try {
            BlankTable.getItems().clear();
            Statement statement = connection.createStatement();
            sqlQuery = "SELECT * FROM Blanks WHERE Blank LIKE '201%'";
            resultSet = statement.executeQuery(sqlQuery);
            while (resultSet.next()) {
                oblist.add(new BlankTableDataModel(resultSet.getString("Blank"), resultSet.getString("Number_of_Coupons"), resultSet.getString("Is_Void"), resultSet.getString("Status"), resultSet.getDate("Date_Added"), resultSet.getString("AssignedTo")));
            }
            BlankTable.setItems(oblist);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //Button uses an sql query to select all the 101 blanks and display them in the table, similar to the initialize method.
    @FXML
    public void Filter101() {
        try {
            BlankTable.getItems().clear();
            Statement statement = connection.createStatement();
            sqlQuery = "SELECT * FROM Blanks WHERE Blank LIKE '101%'";
            resultSet = statement.executeQuery(sqlQuery);
            while (resultSet.next()) {
                oblist.add(new BlankTableDataModel(resultSet.getString("Blank"), resultSet.getString("Number_of_Coupons"), resultSet.getString("Is_Void"), resultSet.getString("Status"), resultSet.getDate("Date_Added"), resultSet.getString("AssignedTo")));
            }
            BlankTable.setItems(oblist);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //in case the user wants to go back to the original view, call the initialize method which resets everything.
    @FXML
    public void ResetToOriginalView() {
        initialize();
    }

    //add Blanks loads fxml page
    @FXML
    public void AddBlanks() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CreateBlanksPage.fxml")); //Load create blanks fxml page
            Stage stage = (Stage) searchBar.getScene().getWindow();
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
        } catch (IOException io) {
            io.printStackTrace();
        }

    }

    //Remove void selects from the database of blanks and gets all the voids and deletes them using sql.
    @FXML
    public void RemoveVoid() {
        try {
            Statement statement = connection.createStatement();
            sqlQuery = "DELETE FROM BLANKS WHERE Is_Void= 'Yes'";
            statement.execute(sqlQuery);
        } catch (Exception e) {
            e.printStackTrace();
        }
        initialize();//refresh
        //make a new screen to inform the user with an ok button
        Stage secondaryStage = new Stage();
        Text doneText = new Text();
        Button okButton = new Button();
        doneText.setText("Void Blanks Have been removed");
        okButton.setText("OK");
        secondaryStage.setTitle("Removed Void Blanks");
        Pane root2 = new Pane();
        secondaryStage.setScene(new Scene(root2, 400, 200));
        root2.getChildren().add(doneText);
        root2.getChildren().add(okButton);
        doneText.setLayoutX(120);
        doneText.setLayoutY(75);
        okButton.setLayoutX(190);
        okButton.setLayoutY(150);
        secondaryStage.show();
        secondaryStage.setResizable(false);
        okButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                secondaryStage.close();
            } //when ok button pressed, close new screen.
        });
    }

    //deletes blanks
    @FXML
    public void DeleteBlanks() {
        if (BlankTable.getSelectionModel().getSelectedItem().getStatus().equals("Sold")) { //if the status is sold
            //make a new window
            Stage secondaryStage = new Stage();
            Text doneText = new Text();
            Button okButton = new Button();
            doneText.setText("Refund used Blanks first before deletion!"); //refund them first
            okButton.setText("OK");
            secondaryStage.setTitle("Refund first");
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
                } //close the new screen made with ok button
            });
        } else {
            //make new screen asking them if they are sure
            Stage secondaryStage = new Stage();
            secondaryStage.setTitle("Are you sure?");
            Text confirmationMessage = new Text();
            Text warningMessage = new Text();
            confirmationMessage.setText("Are you sure you want to delete selected Blanks?");
            warningMessage.setFont(new Font("Segoe UI", 13));
            warningMessage.setVisible(false);
            warningMessage.setFill(Color.RED);
            warningMessage.setText("You haven't selected anything to delete!");
            warningMessage.setLayoutX(80);
            warningMessage.setLayoutY(100);
            confirmationMessage.setLayoutX(75);
            confirmationMessage.setLayoutY(80);
            Button acceptButton = new Button();
            Button declineButton = new Button();
            declineButton.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    secondaryStage.close();
                } //if no close stage
            });
            acceptButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    try {
                        Statement statement = connection.createStatement();
                        if (BlankTable.getSelectionModel().getSelectedItem() != null) {
                            for (BlankTableDataModel blankTableDataModel : BlankTable.getSelectionModel().getSelectedItems()) {
                                sqlQuery = "DELETE FROM Blanks WHERE Blank = '" + blankTableDataModel.getBlank()+"'"; //if yes do the deletion
                                try {
                                    statement.execute(sqlQuery); //execute the sql query
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                            secondaryStage.close();
                            initialize();
                        } else {
                            warningMessage.setVisible(true);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            declineButton.setText("No, Take me back!"); //setting the text for the buttons.
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
            secondaryStage.setResizable(false); //no resizing.

        }
    }

    //searches to find search query.
    @FXML
    public void Search() {
        if (searchBar.getText().length() != 0) { //if something is entered in to the search bar.
            BlankTable.getItems().stream().filter(item -> item.getBlank().equals(searchBar.getText())).findAny().ifPresentOrElse(item -> {
                BlankTable.scrollTo(item); //scroll to the item
                BlankTable.getSelectionModel().clearSelection(); //remove whats currently selected.
                BlankTable.getSelectionModel().select(item); //select the searched thing
            }, () -> {
                //or make a new dialog telling them it can't be found
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
                    } //close on the ok button
                });
            });
        } else { //else tell them they entered nothing.
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

    //back button just loads the fxml page for the Main screen of the system admin.
    @FXML
    public void Back() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MainScreenSystemAdministrator.fxml"));
            Stage stage = (Stage) searchBar.getScene().getWindow();
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
        } catch (IOException io) {
            io.printStackTrace();
        }
    }

}
