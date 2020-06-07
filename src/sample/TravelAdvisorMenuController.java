package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class TravelAdvisorMenuController {

    //Controller Links to MainScreenTravelAdvisor.fxml

    //Load placeholders.
    public Text namePlaceholder;
    public Text staffNumberPlaceholder;
    public Text rolePlaceholder;

    //initialize is first method to be called
    @FXML
    public void initialize() {

        //replace placeholders
        namePlaceholder.setText(LoginStorage.getFirstName());
        staffNumberPlaceholder.setText(LoginStorage.getStaffNumber());
        rolePlaceholder.setText(LoginStorage.getRole());
    }

    //load create customer page
    @FXML
    public void CreateCustomerAccount() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CreateCustomerPage.fxml"));
            Stage stage = (Stage) namePlaceholder.getScene().getWindow();
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
        } catch (IOException io) {
            io.printStackTrace();
        }
    }

    //load sell blanks page
    @FXML
    public void SellTicket() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("SellBlanksPage.fxml"));
            Stage stage = (Stage) namePlaceholder.getScene().getWindow();
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
        } catch (IOException io) {
            io.printStackTrace();
        }

    }

    //load view stock page
    @FXML
    public void ViewStock() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ViewStockOfBlanks.fxml"));
            Stage stage = (Stage) namePlaceholder.getScene().getWindow();
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
        } catch (IOException io) {
            io.printStackTrace();
        }
    }

    //load view individual reports page.
    @FXML
    public void ViewIndividual() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("GenerateIndividualReports.fxml"));
            Stage stage = (Stage) namePlaceholder.getScene().getWindow();
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
        } catch (IOException io) {
            io.printStackTrace();
        }
    }

    //load currency conversion page
    @FXML
    public void setCurrency() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CurrencyConversionPage.fxml"));
            Stage stage = (Stage) namePlaceholder.getScene().getWindow();
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
        } catch (IOException io) {
            io.printStackTrace();
        }

    }

    //load refund page
    @FXML
    public void Refund() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("RefundBlanksPage.fxml"));
            Stage stage = (Stage) namePlaceholder.getScene().getWindow();
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
        } catch (IOException io) {
            io.printStackTrace();
        }

    }

    //redirect to login page.
    @FXML
    public void Logout() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginPage.fxml"));
            Stage stage = (Stage) namePlaceholder.getScene().getWindow();
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
        } catch (IOException io) {
            io.printStackTrace();
        }

    }
}
