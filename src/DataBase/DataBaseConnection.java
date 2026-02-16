package DataBase;



import java.sql.*;

public class DataBaseConnection {

    public static Connection makeConnection(){
        try{
            Class.forName(DataBaseConfig.DRIVER);
            System.out.println("Driver OK");
        }
        catch (ClassNotFoundException e) {
            System.out.println("erreur:" + e.getMessage());
        }
        try {
                Connection con = DriverManager.getConnection(
                DataBaseConfig.URL,
                DataBaseConfig.USER,
                DataBaseConfig.PASSWORD) ;
            System.out.println("Connection OK");
            return con;
        } catch (SQLException e) {
            System.out.println("erreur:" + e.getMessage());
        }


        return null;
    }
}
