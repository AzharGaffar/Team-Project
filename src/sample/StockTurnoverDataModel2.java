package sample;

public class StockTurnoverDataModel2 {

    //Datamodel for the second table in the stockturnovercontroller

    //Intitialize
    String taCode, used444, used420, used201, used101;

    //constructor
    public StockTurnoverDataModel2(String taCode, String used444, String used420, String used201, String used101) {
        this.taCode = taCode;
        this.used444 = used444;
        this.used420 = used420;
        this.used201 = used201;
        this.used101 = used101;
    }

    //getters and setters
    public String getTaCode() {
        return taCode;
    }

    public void setTaCode(String taCode) {
        this.taCode = taCode;
    }

    public String getUsed444() {
        return used444;
    }

    public void setUsed444(String used444) {
        this.used444 = used444;
    }

    public String getUsed420() {
        return used420;
    }

    public void setUsed420(String used420) {
        this.used420 = used420;
    }

    public String getUsed201() {
        return used201;
    }

    public void setUsed201(String used201) {
        this.used201 = used201;
    }

    public String getUsed101() {
        return used101;
    }

    public void setUsed101(String used101) {
        this.used101 = used101;
    }
}
