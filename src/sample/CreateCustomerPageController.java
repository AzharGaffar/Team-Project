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

public class CreateCustomerPageController {

    //Controller Links to CreateCustomerPage.fxml

    //placeholders to be replaced
    public Text namePlaceholder;
    public Text staffNumberPlaceholder;
    public Text rolePlaceholder;

    //initializing the things needed.
    String sqlQuery;
    ResultSet resultSet;
    public TextField searchBar;
    public Button addCustomerButton;


    //Linking to database fxml table.
    @FXML
    private TableView<CustomerDataModel> CustomerTable;
    @FXML
    private TableColumn<CustomerDataModel, Integer> customerIDColumn;
    @FXML
    private TableColumn<CustomerDataModel, String> firstNameColumn;
    @FXML
    private TableColumn<CustomerDataModel, String> lastNameColumn;
    @FXML
    private TableColumn<CustomerDataModel, String> aliasColumn;
    @FXML
    private TableColumn<CustomerDataModel, String> emailColumn;
    @FXML
    private TableColumn<CustomerDataModel, String> telephoneColumn;
    @FXML
    private TableColumn<CustomerDataModel, String> statusColumn;


    //Making a new list that everything can be stored in.
    ObservableList<CustomerDataModel> oblist = FXCollections.observableArrayList();

    //Establishing a connection for SQL queries to go through.
    DatabaseConnection databaseConnection = new DatabaseConnection();
    Connection connection = databaseConnection.getConnection();

    // Initialize method puts everything from SQL table into the GUI Table.
    @FXML
    public void initialize() {

        //replace placeholders
        namePlaceholder.setText(LoginStorage.getFirstName());
        staffNumberPlaceholder.setText(LoginStorage.getStaffNumber());
        rolePlaceholder.setText(LoginStorage.getRole());

        try {
            CustomerTable.getItems().clear(); //used for refreshing the table

            //I want everything selected from the users table and put into a resultset.
            resultSet = connection.createStatement().executeQuery("SELECT * FROM Customer");

            //Keep adding results to the list.
            while (resultSet.next()) {
                oblist.add(new CustomerDataModel(resultSet.getInt("CustomerID"), resultSet.getString("First_Name"), resultSet.getString("Last_Name"), resultSet.getString("Alias"), resultSet.getString("Email"), resultSet.getString("Telephone"), resultSet.getString("Status"), resultSet.getString("Fixed_Discount_Rate")));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Multiselect enabled
        CustomerTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        //Linking the FXML Columns to the "StaffTableDataModel" class.
        customerIDColumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        aliasColumn.setCellValueFactory(new PropertyValueFactory<>("alias"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        telephoneColumn.setCellValueFactory(new PropertyValueFactory<>("telephone"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        //Display the items in JavaFX table view.
        CustomerTable.setItems(oblist);

    }

    //searches for the relevant search item
    @FXML
    public void Search() {
        if (searchBar.getText().length() != 0) {
            CustomerTable.getItems().stream().filter(item -> item.getAlias().equals(searchBar.getText())).findAny().ifPresentOrElse(item -> { //if present
                CustomerTable.scrollTo(item); //scroll to the item
                CustomerTable.getSelectionModel().clearSelection(); //clear what was previously selected.
                CustomerTable.getSelectionModel().select(item); //select new search query if found.
            }, () -> {
                //otherwise tell the user its not there
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
                    } //dismiss
                });
            });
        } else {
            //or if they haven't entered anything into the search bar, tell them.
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
                } //dismiss
            });
        }
    }

    //go to add customer page.
    @FXML
    public void AddCustomer() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CustomerRegisterForm.fxml")); //load fxml file.
            Stage stage = (Stage) addCustomerButton.getScene().getWindow();
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //deletes customers
    @FXML
    public void Delete() {
        //make a new window asking the user
        Stage secondaryStage = new Stage();
        secondaryStage.setTitle("Are you sure?");
        Text confirmationMessage = new Text();
        Text warningMessage = new Text();
        confirmationMessage.setText("Are you sure you want to delete selected Users?");
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
            } //if no dismiss window.
        });
        acceptButton.setOnAction(new EventHandler<ActionEvent>() { //if yes
            @Override
            public void handle(ActionEvent event) {
                try {
                    Statement statement = connection.createStatement();
                    if (CustomerTable.getSelectionModel().getSelectedItem() != null) { //if customer is not null.
                        for (CustomerDataModel customerDataModel : CustomerTable.getSelectionModel().getSelectedItems()) {
                            sqlQuery = "DELETE FROM Customer WHERE CustomerID = " + customerDataModel.CustomerID; //delete the customer using sql.
                            try {
                                statement.execute(sqlQuery); //execute sql.
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        secondaryStage.close(); //dismiss
                        initialize(); //refresh
                    } else {
                        warningMessage.setVisible(true); //set the warning message visible
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        declineButton.setText("No, Take me back!");
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
        secondaryStage.setResizable(false); //no resize
    }

    //load the last fxml page
    @FXML
    public void Back() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MainScreenTravelAdvisor.fxml")); //load the travel advisor page.
            Stage stage = (Stage) searchBar.getScene().getWindow();
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
        } catch (IOException io) {
            io.printStackTrace();
        }
    }
}

