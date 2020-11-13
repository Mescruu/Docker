// import java.sql.Connection;
import java.sql.*;
import java.util.Scanner;

public class Lab4 {

   static final String jdbcDriver = "com.mysql.jdbc.Driver";
   static final String dbAddress = "jdbc:mysql://10.0.10.3:3306/";
   static final String userPass = "?user=root&password=qwerty123&useUnicode=true&characterEncoding=UTF-8";
   static final String dbName = "Lab4";
   static final String userName = "AW";
   static final String password = "qwerty123";

   static Connection con;

   static void createDatabase() {

      try {
            Class.forName(jdbcDriver);
            con = DriverManager.getConnection(dbAddress + userPass);
            Statement s = con.createStatement();
            int myResult = s.executeUpdate("CREATE DATABASE IF NOT EXISTS " + dbName);

            System.out.println(" createDatabase function: Database created \n");

      }
      catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
      }
   }

   public static void main(String[] args) {

      System.out.println(" Welcome to test\n");
        Scanner scanner = new Scanner(System.in);
        String operation = scanner.next();

      try {
         Class.forName(jdbcDriver);
         con = DriverManager.getConnection(dbAddress + dbName, userName, password);

          System.out.println(" main function: connectrion created \n");

      }
      catch (ClassNotFoundException e) {
         e.printStackTrace();
      }
      catch (SQLException e) {
         createDatabase();
         // e.printStackTrace();
      }
   }
}