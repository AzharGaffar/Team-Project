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


public class StaffTableController {

    //Controller Links to StaffMemberTable.fxml

    LoginStorage loginStorage;

    public Text namePlaceholder;
    public Text staffNumberPlaceholder;
    public Text rolePlaceholder;

    String sqlQuery;
    ResultSet resultSet;
    public TextField searchBar;
    public Button addStaffButton;


    //Linking to FXML file
    @FXML
    private TableView<StaffTableDataModel> StaffTable;
    @FXML
    private TableColumn<StaffTableDataModel, String> staffNumberColumn;
    @FXML
    private TableColumn<StaffTableDataModel, String> firstNameColumn;
    @FXML
    private TableColumn<StaffTableDataModel, String> lastNameColumn;
    @FXML
    private TableColumn<StaffTableDataModel, String> emailColumn;
    @FXML
    private TableColumn<StaffTableDataModel, String> usernameColumn;
    @FXML
    private TableColumn<StaffTableDataModel, String> roleColumn;

    //Making a new list that everything can be stored in.
    ObservableList<StaffTableDataModel> oblist = FXCollections.observableArrayList();

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
            StaffTable.getItems().clear(); //used for refreshing the table

            //I want everything selected from the users table and put into a resultset.
            resultSet = connection.createStatement().executeQuery("SELECT * FROM Users");

            //Keep adding results to the list.
            while (resultSet.next()) {
                oblist.add(new StaffTableDataModel(resultSet.getString("Staff_Number"), resultSet.getString("First_Name"), resultSet.getString("Last_Name"), resultSet.getString("Email"), resultSet.getString("Username"), resultSet.getString("Role")));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Multiselect enabled
        StaffTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        //Linking the FXML Columns to the "StaffTableDataModel" class.
        staffNumberColumn.setCellValueFactory(new PropertyValueFactory<>("staffNumber"));
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        roleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));

        //Display the items in JavaFX table view.
        StaffTable.setItems(oblist);

    }

    /* This method makes an alert screen that asks if the user is sure they want to delete the selected items.
        if they want to they press delete and the table gets the selected items and deletes them or they can go back */
    @FXML
    public void DeletePage() {
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
            }
        });
        acceptButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    Statement statement = connection.createStatement();
                    if (StaffTable.getSelectionModel().getSelectedItem() != null) {
                        for (StaffTableDataModel staffTableDataModel : StaffTable.getSelectionModel().getSelectedItems()) {
                            sqlQuery = "DELETE FROM Users WHERE Staff_Number = " + staffTableDataModel.getStaffNumber();
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

    /* ResetPassword first checks if the user has selected a row, if not then displays a dialog saying to select a row
        then one the user has selected a row, it displays a new dialog asking the user to enter their new password and
        confirm it, various checks are performed and once the data is updated using SQL, it displays a message informing
        the user that the password has been updated.
     */
    public void ResetPassword() {
        if (StaffTable.getSelectionModel().getSelectedItem() != null) {
            Stage secondaryStage = new Stage();
            Stage thirdStage = new Stage();
            Text confirmationMessage = new Text();
            Text warningMessage = new Text();
            PasswordField passwordField = new PasswordField();
            PasswordField confirmPasswordField = new PasswordField();
            Button goBackButton = new Button();
            Button submitButton = new Button();
            Button okButton = new Button();
            Text doneText = new Text();
            Pane root1 = new Pane();
            Pane root2 = new Pane();

            secondaryStage.setTitle("Change Password");
            thirdStage.setTitle("Done!");
            passwordField.setPromptText("Enter New Password");
            confirmPasswordField.setPromptText("Confirm New Password");
            confirmationMessage.setText("Enter the new Password for the selected User");
            goBackButton.setText("Cancel");
            submitButton.setText("Submit");
            okButton.setText("OK");
            doneText.setText("Updated User's Password!");
            warningMessage.setFont(new Font("Segoe UI", 13));
            warningMessage.setVisible(false);
            warningMessage.setFill(Color.RED);

            confirmationMessage.setLayoutX(75);
            confirmationMessage.setLayoutY(20);
            passwordField.setLayoutX(130);
            passwordField.setLayoutY(60);
            confirmPasswordField.setLayoutX(130);
            confirmPasswordField.setLayoutY(110);
            warningMessage.setLayoutX(130);
            warningMessage.setLayoutY(150);
            goBackButton.setLayoutX(70);
            goBackButton.setLayoutY(160);
            submitButton.setLayoutX(275);
            submitButton.setLayoutY(160);
            doneText.setLayoutX(120);
            doneText.setLayoutY(75);
            okButton.setLayoutX(190);
            okButton.setLayoutY(150);

            submitButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    try {
                        String password = passwordField.getText();
                        String confirmPassword = confirmPasswordField.getText();
                        if (password.length() != 0 && confirmPassword.length() != 0) {
                            if (password.equals(confirmPassword)) {
                                sqlQuery = "UPDATE Users SET Password = '" + password + "'" + " WHERE Staff_Number = " + StaffTable.getSelectionModel().getSelectedItem().getStaffNumber();
                                Statement statement = connection.createStatement();
                                statement.execute(sqlQuery);
                                secondaryStage.close();
                                root1.getChildren().add(doneText);
                                root1.getChildren().add(okButton);
                                thirdStage.setScene(new Scene(root1, 400, 200));
                                thirdStage.show();
                                thirdStage.setResizable(false);
                                okButton.setOnAction(new EventHandler<ActionEvent>() {
                                    @Override
                                    public void handle(ActionEvent event) {
                                        thirdStage.close();
                                    }
                                });
                                initialize();
                            } else {
                                warningMessage.setVisible(true);
                                warningMessage.setText("Passwords Don't Match!");
                            }
                        } else {
                            warningMessage.setVisible(true);
                            warningMessage.setText("Password Field Cannot be empty!");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            goBackButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    secondaryStage.close();
                    initialize();
                }
            });


            root2.getChildren().add(goBackButton);
            root2.getChildren().add(submitButton);
            root2.getChildren().add(confirmationMessage);
            root2.getChildren().add(warningMessage);
            root2.getChildren().add(passwordField);
            root2.getChildren().add(confirmPasswordField);
            secondaryStage.setScene(new Scene(root2, 400, 200));
            secondaryStage.show();
            secondaryStage.setResizable(false);

        } else {
            Stage secondaryStage = new Stage();
            Text doneText = new Text();
            Button okButton = new Button();
            doneText.setText("Please select a row first in order to change the password!");
            okButton.setText("OK");
            secondaryStage.setTitle("Select something first!");
            Pane root2 = new Pane();
            secondaryStage.setScene(new Scene(root2, 400, 200));
            root2.getChildren().add(doneText);
            root2.getChildren().add(okButton);
            doneText.setLayoutX(50);
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

    //search looks for username
    @FXML
    public void Search() {
        if (searchBar.getText().length() != 0) {
            StaffTable.getItems().stream().filter(item -> item.getUsername().equals(searchBar.getText())).findAny().ifPresentOrElse(item -> { //if present
                StaffTable.scrollTo(item); //scroll to it
                StaffTable.getSelectionModel().clearSelection(); //deselect old
                StaffTable.getSelectionModel().select(item); //select new
            }, () -> {
                //else not in database.
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
            //else nothing entered into search bar.
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

    //add staff load the register form page.
    @FXML
    public void AddStaff() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("RegisterForm.fxml"));
            Stage stage = (Stage) addStaffButton.getScene().getWindow();
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //back calls the last page, the system admin page
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