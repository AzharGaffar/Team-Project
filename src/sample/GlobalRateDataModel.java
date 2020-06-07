package sample;

public class GlobalRateDataModel {

    //data model for global rate tableview.

    //intializing
    int number, totalNumberOfSales;
    String exchangeRate;

    //constructor
    public GlobalRateDataModel(int number, String exchangeRate, int totalNumberOfSales) {
        this.number = number;
        this.totalNumberOfSales = totalNumberOfSales;
        this.exchangeRate = exchangeRate;
    }

    //getters and setters.
    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getTotalNumberOfSales() {
        return totalNumberOfSales;
    }

    public void setTotalNumberOfSales(int totalNumberOfSales) {
        this.totalNumberOfSales = totalNumberOfSales;
    }

    public String getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(String exchangeRate) {
        this.exchangeRate = exchangeRate;
    }
}
