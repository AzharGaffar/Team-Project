package sample;

import java.sql.Date;

public class LatePaymentsDataModel {

    //LatePaymentsDataModel is used in LatePaymentsController.java

    //Initialize the stirngs and dates.
    String blankNumber, owner, status, balanceOustanding, oldStatus, seenByOM;
    Date dateOfSale, dueDate;

    //This is a constructor.
    public LatePaymentsDataModel(String blankNumber, String owner, String status, Date dateOfSale, Date dueDate, String balanceOustanding, String oldStatus, String seenByOM) {
        this.blankNumber = blankNumber;
        this.owner = owner;
        this.status = status;
        this.balanceOustanding = balanceOustanding;
        this.oldStatus = oldStatus;
        this.seenByOM = seenByOM;
        this.dateOfSale = dateOfSale;
        this.dueDate = dueDate;
    }

    //Series of getters and setters.
    public String getBlankNumber() {
        return blankNumber;
    }

    public void setBlankNumber(String blankNumber) {
        this.blankNumber = blankNumber;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBalanceOustanding() {
        return balanceOustanding;
    }

    public void setBalanceOustanding(String balanceOustanding) {
        this.balanceOustanding = balanceOustanding;
    }

    public String getOldStatus() {
        return oldStatus;
    }

    public void setOldStatus(String oldStatus) {
        this.oldStatus = oldStatus;
    }

    public String getSeenByOM() {
        return seenByOM;
    }

    public void setSeenByOM(String seenByOM) {
        this.seenByOM = seenByOM;
    }

    public Date getDateOfSale() {
        return dateOfSale;
    }

    public void setDateOfSale(Date dateOfSale) {
        this.dateOfSale = dateOfSale;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }
}
