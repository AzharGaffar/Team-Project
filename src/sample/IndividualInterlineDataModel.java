package sample;

public class IndividualInterlineDataModel {

    //datamodel used for individual interline tableview.

    //initialize
    int number;
    String blankNumber, fareUSD, exchangeRate, fareLocal, taxLocal, taxOther, totalAmount, cashAmount, ccNumber, totalPaid, commission;

    //constuctor
    public IndividualInterlineDataModel(int number, String blankNumber, String fareUSD, String exchangeRate, String fareLocal, String taxLocal, String taxOther, String totalAmount, String cashAmount, String ccNumber, String totalPaid, String commission) {
        this.number = number;
        this.blankNumber = blankNumber;
        this.fareUSD = fareUSD;
        this.exchangeRate = exchangeRate;
        this.fareLocal = fareLocal;
        this.taxLocal = taxLocal;
        this.taxOther = taxOther;
        this.totalAmount = totalAmount;
        this.cashAmount = cashAmount;
        this.ccNumber = ccNumber;
        this.totalPaid = totalPaid;
        this.commission = commission;
    }

    //getters and setters.
    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getBlankNumber() {
        return blankNumber;
    }

    public void setBlankNumber(String blankNumber) {
        this.blankNumber = blankNumber;
    }

    public String getFareUSD() {
        return fareUSD;
    }

    public void setFareUSD(String fareUSD) {
        this.fareUSD = fareUSD;
    }

    public String getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(String exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public String getFareLocal() {
        return fareLocal;
    }

    public void setFareLocal(String fareLocal) {
        this.fareLocal = fareLocal;
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

    public String getCashAmount() {
        return cashAmount;
    }

    public void setCashAmount(String cashAmount) {
        this.cashAmount = cashAmount;
    }

    public String getCcNumber() {
        return ccNumber;
    }

    public void setCcNumber(String ccNumber) {
        this.ccNumber = ccNumber;
    }

    public String getTotalPaid() {
        return totalPaid;
    }

    public void setTotalPaid(String totalPaid) {
        this.totalPaid = totalPaid;
    }

    public String getCommission() {
        return commission;
    }

    public void setCommission(String commission) {
        this.commission = commission;
    }
}
