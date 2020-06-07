package sample;

public class SoldDataModel {

    //Class is used for displaying sold information in the sold table and the refund table.

    //intialize
    String BlankNumber, TotalPaid, SoldBy, BlankType;
    int SoldToTA;

    //constructor
    public SoldDataModel(String blankNumber, String totalPaid, String soldBy, int soldToTA, String blankType) {
        BlankNumber = blankNumber;
        TotalPaid = totalPaid;
        SoldBy = soldBy;
        SoldToTA = soldToTA;
        BlankType = blankType;
    }

    //getters and setters.
    public String getBlankNumber() {
        return BlankNumber;
    }

    public void setBlankNumber(String blankNumber) {
        BlankNumber = blankNumber;
    }

    public String getTotalPaid() {
        return TotalPaid;
    }

    public void setTotalPaid(String totalPaid) {
        TotalPaid = totalPaid;
    }

    public String getSoldBy() {
        return SoldBy;
    }

    public void setSoldBy(String soldBy) {
        SoldBy = soldBy;
    }

    public String getBlankType() {
        return BlankType;
    }

    public void setBlankType(String blankType) {
        BlankType = blankType;
    }

    public int getSoldToTA() {
        return SoldToTA;
    }

    public void setSoldToTA(int soldToTA) {
        SoldToTA = soldToTA;
    }
}
