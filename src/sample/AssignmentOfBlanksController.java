package sample;

import javafx.beans.property.ReadOnlyStringWrapper;
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

public class AssignmentOfBlanksController {

    //Controller Links to AssignmentOfBlanks.fxml

    //initializing placeholders in javafx fxml file
    public Text namePlaceholder;
    public Text staffNumberPlaceholder;
    public Text rolePlaceholder;


    //Linking to to table in fxml file
    @FXML
    private TableView<BlankTableDataModel> AssignmentTable;
    @FXML
    private TableColumn<BlankTableDataModel, String> blankColumn;
    @FXML
    private TableColumn<BlankTableDataModel, String> statusColumn;
    @FXML
    private TableColumn<BlankTableDataModel, String> assignmentColumn;


    //Making a new list that everything can be stored in.
    ObservableList<BlankTableDataModel> oblist = FXCollections.observableArrayList();

    //Establishing a connection for SQL queries to go through.
    DatabaseConnection databaseConnection = new DatabaseConnection();
    Connection connection = databaseConnection.getConnection();

    //sql queries and resultsets are used for storage
    String sqlQuery;
    ResultSet resultSet;

    public TextField searchBar;


    // Initialize method puts everything from SQL table into the GUI Table.
    @FXML
    public void initialize() {

        //replace placeholders with correct values
        namePlaceholder.setText(LoginStorage.getFirstName());
        staffNumberPlaceholder.setText(LoginStorage.getStaffNumber());
        rolePlaceholder.setText(LoginStorage.getRole());

        try {
            AssignmentTable.getItems().clear(); //used for refreshing the table

            //I want everything selected from the users table and put into a resultset.
            resultSet = connection.createStatement().executeQuery("SELECT * FROM Blanks");


            //Keep adding results to the list while there are records in the database.
            while (resultSet.next()) {
                oblist.add(new BlankTableDataModel(resultSet.getString("Blank"), resultSet.getString("Number_of_Coupons"), resultSet.getString("Is_Void"), resultSet.getString("Status"), resultSet.getDate("Date_Added"), resultSet.getString("AssignedTo")));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Multiselect enabled
        AssignmentTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        //Linking the FXML Columns to the "StaffTableDataModel" class.
        blankColumn.setCellValueFactory(new PropertyValueFactory<>("Blank"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("Status"));
        assignmentColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getAssignedto()));


        //Display the items in JavaFX table view.
        AssignmentTable.setItems(oblist);

    }

    //Clears table then Filters 444 by using an sql statement to find the 444 blanks and display them similar to initialize method
    @FXML
    public void Filter444() {
        try {
            AssignmentTable.getItems().clear();
            Statement statement = connection.createStatement();
            sqlQuery = "SELECT * FROM Blanks WHERE Blank LIKE '444%'";
            resultSet = statement.executeQuery(sqlQuery);
            while (resultSet.next()) {
                oblist.add(new BlankTableDataModel(resultSet.getString("Blank"), resultSet.getString("Number_of_Coupons"), resultSet.getString("Is_Void"), resultSet.getString("Status"), resultSet.getDate("Date_Added"), resultSet.getString("AssignedTo")));
            }
            AssignmentTable.setItems(oblist);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    //Clears table then Filters 420 by using an sql statement to find the 444 blanks and display them similar to initialize method
    @FXML
    public void Filter420() {
        try {
            AssignmentTable.getItems().clear();
            Statement statement = connection.createStatement();
            sqlQuery = "SELECT * FROM Blanks WHERE Blank LIKE '420%'";
            resultSet = statement.executeQuery(sqlQuery);
            while (resultSet.next()) {
                oblist.add(new BlankTableDataModel(resultSet.getString("Blank"), resultSet.getString("Number_of_Coupons"), resultSet.getString("Is_Void"), resultSet.getString("Status"), resultSet.getDate("Date_Added"), resultSet.getString("AssignedTo")));
            }
            AssignmentTable.setItems(oblist);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //Clears table then Filters 201 by using an sql statement to find the 444 blanks and display them similar to initialize method
    @FXML
    public void Filter201() {
        try {
            AssignmentTable.getItems().clear();
            Statement statement = connection.createStatement();
            sqlQuery = "SELECT * FROM Blanks WHERE Blank LIKE '201%'";
            resultSet = statement.executeQuery(sqlQuery);
            while (resultSet.next()) {
                oblist.add(new BlankTableDataModel(resultSet.getString("Blank"), resultSet.getString("Number_of_Coupons"), resultSet.getString("Is_Void"), resultSet.getString("Status"), resultSet.getDate("Date_Added"), resultSet.getString("AssignedTo")));
            }
            AssignmentTable.setItems(oblist);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //Clears table then Filters 101 by using an sql statement to find the 444 blanks and display them similar to initialize method
    @FXML
    public void Filter101() {
        try {
            AssignmentTable.getItems().clear();
            Statement statement = connection.createStatement();
            sqlQuery = "SELECT * FROM Blanks WHERE Blank LIKE '101%'";
            resultSet = statement.executeQuery(sqlQuery);
            while (resultSet.next()) {
                oblist.add(new BlankTableDataModel(resultSet.getString("Blank"), resultSet.getString("Number_of_Coupons"), resultSet.getString("Is_Void"), resultSet.getString("Status"), resultSet.getDate("Date_Added"), resultSet.getString("AssignedTo")));
            }
            AssignmentTable.setItems(oblist);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //Calls initialize method to reset.
    @FXML
    public void ResetToOriginalView() {
        initialize();
    }

    //assigns blanks to TA's
    @FXML
    public void AssignBlanks() {
        if (AssignmentTable.getSelectionModel().getSelectedItems().isEmpty() == false) { //blank has been selected.
            ListView<String> travelAdvisorList = new ListView<>(); //initialize listview.
            Button submitButton = new Button();
            try {
                sqlQuery = "SELECT Username FROM Users WHERE Role = 'Travel Advisor'";
                Statement listViewStatement = connection.createStatement();
                ResultSet travelAdvisorSet = listViewStatement.executeQuery(sqlQuery);
                while (travelAdvisorSet.next() != false) {
                    String taUsername = travelAdvisorSet.getString("Username"); //get list of TA usernames.
                    travelAdvisorList.getItems().addAll(taUsername); //adding to list
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            //make new window that displays all the elements.
            Stage secondaryStage = new Stage();
            Button backButton = new Button();
            Text doneText = new Text();
            doneText.setText("Select a Travel Advisor To Give Blanks to");
            submitButton.setText("Submit");
            backButton.setText("Go Back");
            secondaryStage.setTitle("Choose an Advisor");
            Pane root2 = new Pane();
            secondaryStage.setScene(new Scene(root2, 250, 460));
            root2.getChildren().add(doneText);
            root2.getChildren().add(submitButton);
            root2.getChildren().add(travelAdvisorList);
            root2.getChildren().add(backButton);
            backButton.setLayoutX(50);
            backButton.setLayoutY(430);
            doneText.setLayoutX(10);
            doneText.setLayoutY(20);
            travelAdvisorList.setLayoutX(0);
            travelAdvisorList.setLayoutY(30);
            submitButton.setLayoutX(150);
            submitButton.setLayoutY(430);
            secondaryStage.show();
            secondaryStage.setResizable(false);
            submitButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    if (travelAdvisorList.getSelectionModel().getSelectedItems().isEmpty() == false) { //if travel advisor selected.
                        secondaryStage.close();
                        String selectedAdvisor = travelAdvisorList.getSelectionModel().getSelectedItem(); //get selected advisor
                        for (BlankTableDataModel blankModel : AssignmentTable.getSelectionModel().getSelectedItems()) { //for every blank thats selected
                            if (blankModel.getAssignedto() == null) { //if null then update who is assigned to the blank.
                                try {
                                    sqlQuery = "UPDATE Blanks SET AssignedTo = '" + selectedAdvisor + "' WHERE Blank ='" + blankModel.getBlank() + "'";
                                    Statement updateStatement = connection.createStatement();
                                    updateStatement.execute(sqlQuery);

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            } else {
                                //or inform that is has been reassigned.
                                String previousBlankName = blankModel.getAssignedto();
                                try {
                                    sqlQuery = "UPDATE Blanks SET AssignedTo = '" + selectedAdvisor + "' WHERE Blank ='" + blankModel.getBlank() + "'"; //sql query to reassign.
                                    Statement updateStatement = connection.createStatement();
                                    updateStatement.execute(sqlQuery);
                                    //make new window that informs them that it has been reassigned.
                                    Stage FourthStage = new Stage();
                                    Text doneText = new Text();
                                    Button okButton = new Button();
                                    doneText.setText(blankModel.getBlank() + " Reassigned from " + previousBlankName + " to " + selectedAdvisor);
                                    okButton.setText("OK");
                                    FourthStage.setTitle("Reassigned!");
                                    Pane root4 = new Pane();
                                    FourthStage.setScene(new Scene(root4, 400, 200));
                                    root4.getChildren().add(doneText);
                                    root4.getChildren().add(okButton);
                                    doneText.setLayoutX(80);
                                    doneText.setLayoutY(75);
                                    okButton.setLayoutX(190);
                                    okButton.setLayoutY(150);
                                    FourthStage.show();
                                    FourthStage.setResizable(false); //no resize
                                    okButton.setOnAction(new EventHandler<ActionEvent>() {
                                        @Override
                                        public void handle(ActionEvent event) {
                                            FourthStage.close();
                                        }
                                    });


                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                        //refresh
                        initialize();
                    } else {
                        //window informs user to select a travel advisor first.
                        Stage thirdStage = new Stage();
                        Text doneText = new Text();
                        Button okButton = new Button();
                        doneText.setText("Please select a Travel Advisor before pressing submit!");
                        okButton.setText("OK");
                        thirdStage.setTitle("Select something first!");
                        Pane root3 = new Pane();
                        thirdStage.setScene(new Scene(root3, 400, 200));
                        root3.getChildren().add(doneText);
                        root3.getChildren().add(okButton);
                        doneText.setLayoutX(50);
                        doneText.setLayoutY(75);
                        okButton.setLayoutX(190);
                        okButton.setLayoutY(150);
                        thirdStage.show();
                        thirdStage.setResizable(false);
                        okButton.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                thirdStage.close();
                            } //ok button close.
                        });
                    }
                }
            });
            backButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    secondaryStage.close(); //back button closes and resets view.
                    initialize();
                }
            });
        } else {
            //if no blanks selected show message in a new window
            Stage thirdStage = new Stage();
            Text doneText = new Text();
            Button okButton = new Button();
            doneText.setText("You Haven't selected any Blanks To Assign!");
            okButton.setText("OK");
            thirdStage.setTitle("Select some Blanks first!");
            Pane root3 = new Pane();
            thirdStage.setScene(new Scene(root3, 400, 200));
            root3.getChildren().add(doneText);
            root3.getChildren().add(okButton);
            doneText.setLayoutX(80);
            doneText.setLayoutY(75);
            okButton.setLayoutX(190);
            okButton.setLayoutY(150);
            thirdStage.show();
            thirdStage.setResizable(false);
            okButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    thirdStage.close();
                } //ok button closes window.
            });
        }
    }


    //search method looks for blank in table using search query.
    @FXML
    public void Search() {
        if (searchBar.getText().length() != 0) {
            AssignmentTable.getItems().stream().filter(item -> item.getBlank().equals(searchBar.getText())).findAny().ifPresentOrElse(item -> { //if present
                AssignmentTable.scrollTo(item); //scroll to the item
                AssignmentTable.getSelectionModel().clearSelection(); //deselect previous.
                AssignmentTable.getSelectionModel().select(item); //select searched.
            }, () -> {
                //else not in the database, display this to the user.
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
                    } //close stage
                });
            });
        } else {
            //if nothing is entered into the search bar, inform the user.
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
                } //close the stage.
            });
        }

    }

    //back button loads the office manager fxml file.
    @FXML
    public void Back() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MainScreenOfficeManager.fxml"));
            Stage stage = (Stage) searchBar.getScene().getWindow();
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
        } catch (IOException io) {
            io.printStackTrace();
        }
    }

}

