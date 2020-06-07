package sample;


import java.sql.*;

public class DatabaseConnection {

    //Forms a database connection to the MYSQL database

    public Connection connection;

    public Connection getConnection() {

        String dbName = ""; //the database name
        String userName = ""; //the database username
        String password = ""; //the password
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost/" + dbName + "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=Europe/London", userName , password); //initializing time and credentials and getting connection
            System.out.println("Connection Established!");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Connection not Established!, Please check over source code! Specifically the Username and Password in the DatabaseConnection.java");
        }


        return connection; //returning the connection.

    }
}
