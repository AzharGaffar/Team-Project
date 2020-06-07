package sample;

import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleIntegerProperty;
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

public class UpdateCustomerStatusController {

    //Controller Links to UpdateCustomerStatus.fxml

    //Placeholders in the top left.
    public Text namePlaceholder;
    public Text staffNumberPlaceholder;
    public Text rolePlaceholder;

    //Textfield and button that are on the fxml file
    public TextField searchBar;
    public Button upgradeButton;


    //sql query and result sets
    String sqlQuery;
    ResultSet OriginalCustomerSet, valuedSet, notValuedSet;


    //Linking FXMl table to java
    @FXML
    private TableView<CustomerDataModel> CustomerStatusTable;
    @FXML
    private TableColumn<CustomerDataModel, Integer> customerIDColumn;
    @FXML
    private TableColumn<CustomerDataModel, String> firstNameColumn;
    @FXML
    private TableColumn<CustomerDataModel, String> lastNameColumn;
    @FXML
    private TableColumn<CustomerDataModel, String> aliasColumn;
    @FXML
    private TableColumn<CustomerDataModel, String> statusColumn;
    @FXML
    private TableColumn<CustomerDataModel, String> discountColumn;


    //Making new lists that everything can be stored in.
    ObservableList<CustomerDataModel> customerList = FXCollections.observableArrayList();
    ObservableList<CustomerDataModel> notValuedList = FXCollections.observableArrayList();
    ObservableList<CustomerDataModel> valuedList = FXCollections.observableArrayList();


    //Establishing a connection for SQL queries to go through.
    DatabaseConnection databaseConnection = new DatabaseConnection();
    Connection connection = databaseConnection.getConnection();

    // Initialize method puts everything from SQL table into the GUI Table.
    @FXML
    public void initialize() {
        namePlaceholder.setText(LoginStorage.getFirstName());
        staffNumberPlaceholder.setText(LoginStorage.getStaffNumber());
        rolePlaceholder.setText(LoginStorage.getRole());

        try {
            CustomerStatusTable.getItems().clear(); //used for refreshing the table

            //I want everything selected from the customer table and put into a resultset.
            OriginalCustomerSet = connection.createStatement().executeQuery("SELECT * FROM Customer");

            //Keep adding results to the list.
            while (OriginalCustomerSet.next()) {
                customerList.add(new CustomerDataModel(OriginalCustomerSet.getInt("CustomerID"), OriginalCustomerSet.getString("First_Name"), OriginalCustomerSet.getString("Last_Name"), OriginalCustomerSet.getString("Alias"), OriginalCustomerSet.getString("Email"), OriginalCustomerSet.getString("Telephone"), OriginalCustomerSet.getString("Status"), OriginalCustomerSet.getString("Fixed_Discount_Rate")));

            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        //Linking the FXML Columns to the "CustomerDataModel" class.
        customerIDColumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        aliasColumn.setCellValueFactory(new PropertyValueFactory<>("alias"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        discountColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getFixedDiscountRate()));

        //Display the items in JavaFX table view.
        CustomerStatusTable.setItems(customerList);
    }

    //search looks for search query
    @FXML
    public void Search() {
        if (searchBar.getText().length() != 0) {
            CustomerStatusTable.getItems().stream().filter(item -> item.getAlias().equals(searchBar.getText())).findAny().ifPresentOrElse(item -> { //if present
                CustomerStatusTable.scrollTo(item); //scroll to item
                CustomerStatusTable.getSelectionModel().clearSelection(); //deselect
                CustomerStatusTable.getSelectionModel().select(item); //select new
            }, () -> {
                //else tell user its not in the database.
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
            //else tell them they entered nothing into the search bar
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

    //upgrade button
    @FXML
    public void Upgrade() {
        if (CustomerStatusTable.getSelectionModel().getSelectedItem() != null) { //if selected customer
            int Selected = CustomerStatusTable.getSelectionModel().getSelectedItem().getCustomerID(); //store their customer id
            //display type in fixed discount
            Stage secondaryStage = new Stage();
            secondaryStage.setTitle("Upgrade To Valued");
            Text confirmationMessage = new Text();
            TextField discountField = new TextField();
            Text warningMessage = new Text();
            warningMessage.setFont(new Font("Segoe UI", 13));
            warningMessage.setFill(Color.RED);
            warningMessage.setLayoutX(80);
            warningMessage.setLayoutY(80);
            confirmationMessage.setText("Type in the Fixed Discount Rate that the Customer will have");
            confirmationMessage.setLayoutX(40);
            confirmationMessage.setLayoutY(60);
            Button acceptButton = new Button();
            Button declineButton = new Button();
            declineButton.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    secondaryStage.close();
                }
            });
            acceptButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    if (discountField.getText().isEmpty() == false) { //if field is not empty
                        if (discountField.getText().matches("[0-9]+")) { //if only numbers are entered.
                            if (Integer.parseInt(discountField.getText()) <= 100) {
                                if (!discountField.getText().contains(".")) {
                                    try {
                                        sqlQuery = "UPDATE Customer SET Status = 'Valued', Fixed_Discount_Rate ='" + discountField.getText() + "' WHERE CustomerID =" + Selected;
                                        //send to sql database the update and the fixed discount.
                                        Statement statement = connection.createStatement();
                                        statement.execute(sqlQuery);
                                        secondaryStage.close();
                                        initialize();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                } else {
                                    warningMessage.setText("You can't have a decimal"); //warning message
                                }
                            } else {
                                warningMessage.setText("You can't have a discount bigger than 100 percent.");//warning message
                            }
                        } else {
                            warningMessage.setText("Only enter numbers please, no decimals");//warning message
                        }
                    } else {
                        warningMessage.setText("Please fill in the field");//warning message
                    }
                }
            });


            declineButton.setText("No, Take me back!");
            acceptButton.setText("Submit");
            Pane root1 = new Pane();
            root1.getChildren().

                    add(acceptButton);
            root1.getChildren().

                    add(declineButton);
            root1.getChildren().

                    add(confirmationMessage);
            root1.getChildren().

                    add(discountField);
            root1.getChildren().

                    add(warningMessage);
            declineButton.setLayoutX(70);
            declineButton.setLayoutY(150);
            acceptButton.setLayoutX(275);
            acceptButton.setLayoutY(150);
            discountField.setLayoutX(100);
            discountField.setLayoutY(100);

            secondaryStage.setScene(new

                    Scene(root1, 400, 200));
            secondaryStage.show();
            secondaryStage.setResizable(false);
        } else {
            //display that they haven't selected a customer
            Stage secondaryStage = new Stage();
            Text doneText = new Text();
            Button okButton = new Button();
            doneText.setText("You haven't selected anyone!");
            okButton.setText("OK");
            secondaryStage.setTitle("Select someone");
            Pane root2 = new Pane();
            secondaryStage.setScene(new Scene(root2, 400, 200));
            root2.getChildren().add(doneText);
            root2.getChildren().add(okButton);
            doneText.setLayoutX(130);
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

    //Downgrade customer
    @FXML
    public void Downgrade() {
        //display are you sure.
        Stage secondaryStage = new Stage();
        secondaryStage.setTitle("Are you sure?");
        Text confirmationMessage = new Text();
        Text warningMessage = new Text();
        confirmationMessage.setText("Are you sure you want to downgrade the selected User?");
        warningMessage.setFont(new Font("Segoe UI", 13));
        warningMessage.setVisible(false);
        warningMessage.setFill(Color.RED);
        warningMessage.setText("You haven't selected anyone to downgrade!");
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
            }
        });
        acceptButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    Statement statement = connection.createStatement();
                    if (CustomerStatusTable.getSelectionModel().getSelectedItem() != null) {
                        for (CustomerDataModel customerDataModel : CustomerStatusTable.getSelectionModel().getSelectedItems()) {
                            sqlQuery = "UPDATE Customer SET Status = 'Casual', Fixed_Discount_Rate = null WHERE CustomerID =" + customerDataModel.getCustomerID();
                            //downgrade query send to sql database
                            try {
                                statement.execute(sqlQuery);
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
        secondaryStage.setResizable(false);
    }

    //upgrade to regular button
    @FXML
    public void Regular() {
        if (CustomerStatusTable.getSelectionModel().getSelectedItem() != null) {
            int Selected = CustomerStatusTable.getSelectionModel().getSelectedItem().getCustomerID();
            try {
                sqlQuery = "UPDATE Customer SET Status = 'Regular', Fixed_Discount_Rate =null WHERE CustomerID =" + Selected;
                //send update to database.
                Statement statement = connection.createStatement();
                statement.execute(sqlQuery);
                initialize();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    //extra filter button to filter valued similar to initalize method.
    @FXML
    public void filterValued() {
        try {
            CustomerStatusTable.getItems().clear();
            Statement statement = connection.createStatement();
            sqlQuery = "SELECT * FROM Customer WHERE Status = 'Valued'";
            valuedSet = statement.executeQuery(sqlQuery);
            while (valuedSet.next()) {
                valuedList.add(new CustomerDataModel(valuedSet.getInt("CustomerID"), valuedSet.getString("First_Name"), valuedSet.getString("Last_Name"), valuedSet.getString("Alias"), valuedSet.getString("Email"), valuedSet.getString("Telephone"), valuedSet.getString("Status"), valuedSet.getString("Fixed_Discount_Rate")));
            }
            CustomerStatusTable.setItems(valuedList);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //extra filter button to filter  not valued similar to initalize method.
    @FXML
    public void filterNotValued() {
        try {
            CustomerStatusTable.getItems().clear();
            Statement newStatement = connection.createStatement();
            sqlQuery = "SELECT * FROM Customer WHERE Status <> 'Valued'";
            notValuedSet = newStatement.executeQuery(sqlQuery);
            while (notValuedSet.next()) {
                notValuedList.add(new CustomerDataModel(notValuedSet.getInt("CustomerID"), notValuedSet.getString("First_Name"), notValuedSet.getString("Last_Name"), notValuedSet.getString("Alias"), notValuedSet.getString("Email"), notValuedSet.getString("Telephone"), notValuedSet.getString("Status"), notValuedSet.getString("Fixed_Discount_Rate")));
            }
            CustomerStatusTable.setItems(notValuedList);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //reset to original view
    @FXML
    public void resetButton() {
        initialize();
    }


    //back button loads the last page.
    @FXML
    public void Back() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MainScreenSystemAdministrator.fxml")); //load system admin page
            Stage stage = (Stage) searchBar.getScene().getWindow();
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
        } catch (IOException io) {
            io.printStackTrace();
        }
    }
}

