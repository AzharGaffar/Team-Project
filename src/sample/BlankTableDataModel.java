package sample;

import java.sql.Date;

public class BlankTableDataModel {

    //this class is used for the BlankTable in order to display the information in the table, a Data Model has to be used.

    String Blank, Is_Void, status, Number_of_Coupons, Assignedto;
    Date Date;

    //Constructor
    public BlankTableDataModel(String blank, String number_of_Coupons, String is_Void, String status, java.sql.Date date, String Assignedto) {
        this.Blank = blank;
        this.Number_of_Coupons = number_of_Coupons;
        this.Is_Void = is_Void;
        this.status = status;
        this.Date = date;
        this.Assignedto = Assignedto;

    }

    //Series of Getters and Setters.
    public String getBlank() {
        return Blank;
    }

    public void setBlank(String blank) {
        Blank = blank;
    }

    public String getIs_Void() {
        return Is_Void;
    }

    public void setIs_Void(String is_Void) {
        Is_Void = is_Void;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNumber_of_Coupons() {
        return Number_of_Coupons;
    }

    public void setNumber_of_Coupons(String number_of_Coupons) {
        Number_of_Coupons = number_of_Coupons;
    }

    public java.sql.Date getDate() {
        return Date;
    }

    public void setDate(java.sql.Date date) {
        Date = date;
    }

    public String getAssignedto() {
        return Assignedto;
    }

    public void setAssignedto(String assignedto) {
        Assignedto = assignedto;
    }
}
