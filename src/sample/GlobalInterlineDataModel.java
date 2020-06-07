package sample;

public class GlobalInterlineDataModel {

    //global interline data model used for populating tableview

    //initializing
    int number;
    String advisorCode, numberOfSales, taxLocal, taxOther, totalAmount, totalCC, totalCommission;


    //constructor
    public GlobalInterlineDataModel(int number, String advisorCode, String numberOfSales, String taxLocal, String taxOther, String totalAmount, String totalCC, String totalCommission) {
        this.number = number;
        this.advisorCode = advisorCode;
        this.numberOfSales = numberOfSales;
        this.taxLocal = taxLocal;
        this.taxOther = taxOther;
        this.totalAmount = totalAmount;
        this.totalCC = totalCC;
        this.totalCommission = totalCommission;
    }

    //getters and setters
    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getAdvisorCode() {
        return advisorCode;
    }

    public void setAdvisorCode(String advisorCode) {
        this.advisorCode = advisorCode;
    }

    public String getNumberOfSales() {
        return numberOfSales;
    }

    public void setNumberOfSales(String numberOfSales) {
        this.numberOfSales = numberOfSales;
    }

    public String getTaxLocal() {
        return taxLocal;
    }

    public void setTaxLocal(String taxLocal) {
        this.taxLocal = taxLocal;
    }

    public String getTaxOther() {
        return taxOther;
    }

    public void setTaxOther(String taxOther) {
        this.taxOther = taxOther;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getTotalCC() {
        return totalCC;
    }

    public void setTotalCC(String totalCC) {
        this.totalCC = totalCC;
    }

    public String getTotalCommission() {
        return totalCommission;
    }

    public void setTotalCommission(String totalCommission) {
        this.totalCommission = totalCommission;
    }
}
