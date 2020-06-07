package sample;

public class StockTurnoverDataModel1 {

    //Datamodel for the first table in hte stockturnovercontroller

    //initialize
    String received444,received420,received201,received101;

    //Contructor
    public StockTurnoverDataModel1(String received444, String received420, String received201, String received101) {
        this.received444 = received444;
        this.received420 = received420;
        this.received201 = received201;
        this.received101 = received101;
    }

    //getters and setters.
    public String getReceived444() {
        return received444;
    }

    public void setReceived444(String received444) {
        this.received444 = received444;
    }

    public String getReceived420() {
        return received420;
    }

    public void setReceived420(String received420) {
        this.received420 = received420;
    }

    public String getReceived201() {
        return received201;
    }

    public void setReceived201(String received201) {
        this.received201 = received201;
    }

    public String getReceived101() {
        return received101;
    }

    public void setReceived101(String received101) {
        this.received101 = received101;
    }
}
