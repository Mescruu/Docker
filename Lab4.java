// import java.sql.Connection;
import java.sql.*;
import java.util.Scanner;

public class Lab4 {

   static final String jdbcDriver = "com.mysql.jdbc.Driver";
   static final String dbAddress = "jdbc:mysql://10.0.10.3:3306/";
   static final String userPass = "?user=root&password=qwerty123";
   static final String dbName = "Lab4";
   static final String userName = "AW";
   static final String password = "qwerty123";
   static final String tableName = "employees";
   static Connection conn;
   static Statement st;

   static void createDatabase() {

      try {
            Class.forName(jdbcDriver);
            conn = DriverManager.getConnection(dbAddress + userPass);
            Statement s = conn.createStatement();
            int myResult = s.executeUpdate("CREATE DATABASE IF NOT EXISTS " + dbName);
      }
      catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
      }
   }

   public static void main(String[] args) {

      System.out.println("\n Welcome to app\n");
        Scanner scan = new Scanner(System.in);
        scan.next();
      System.out.println("\n Welcome to app\n");

      try {
         Class.forName(jdbcDriver);
         conn = DriverManager.getConnection(dbAddress + dbName, userName, password);

          System.out.println("\n main function: connection created \n");

            st = conn.createStatement();
          System.out.println("\n main function: statement created \n");

            //Utworzenie bazy danych
            st.executeUpdate("CREATE DATABASE IF NOT EXISTS " + dbName);
            //Przejście do tej bazy
            st.executeUpdate("USE "+ dbName);
              String new_table = "CREATE TABLE IF NOT EXISTS " + tableName + " ("
                                + "ID INT(4) NOT NULL AUTO_INCREMENT,"
                                + "NAME VARCHAR(30),"
                                + "SURNAME VARCHAR(30),"
                                + "SALARY DOUBLE(11),"
                                + "PRIMARY KEY (ID))";

            //Utworzenie tabeli
            st.executeUpdate(new_table);

            System.out.println ("\nDatabase Connection Established...");

        boolean execute=true;
        while(execute){
                    System.out.println("\n\n\n Menu");
                    System.out.println("1: Display table.");
                    System.out.println("2. Add to table:");
                    System.out.println("3. Remove from table:");
                    System.out.println("4. Edit data in table:");
                    System.out.println("0. Exit");

                    System.out.println("Choose one of the options above: ");
                    int selection;
                    selection = scan.nextInt();

                                switch(selection) {
                                    case 1: {

                                        String query = "SELECT * FROM " + tableName;
                                        ResultSet rs =  st.executeQuery(query);
                                        System.out.println("\n\nID | NAME | SURNAME | SALARY");

                                      while(rs.next()) {
                                         int id = rs.getInt("id");
                                         String name = rs.getString("Name");
                                         String surname = rs.getString("Surname");
                                          String salary = rs.getString("Salary");

                                      System.out.println(id + " | " + name + " | " + surname + " | " + salary);
                                     }

                                        break;
                                    }
                                    case 2: {

                                             try
                                             {
                                                   Thread.sleep(1000);
                                              }
                                              catch(Exception e)
                                              {
                                                  System.out.println(e);
                                               }

                                        System.out.println("Write name: ");
                                        String name = scan.next();

                                        System.out.println("Write surname: ");
                                        String surname = scan.next();

                                        System.out.println("Write salary value: ");
                                        String salary = scan.next();

                                        String query = "INSERT INTO " + tableName + " ( NAME, SURNAME, SALARY) VALUES (?, ?, ?)";
                                        PreparedStatement prpStmt = conn.prepareStatement(query);

                                        prpStmt.setString(1, name);
                                        prpStmt.setString(2, surname);
                                        prpStmt.setString(3, salary);

                                        prpStmt.execute();

                                        System.out.println("\nPostition added.\n");

                                        break;
                                    }
                                    case 3: { //Usunięcie rekordu o zadanym ID
                                        System.out.println("\nSelect row to remove: ");
                                        int id = scan.nextInt();


                                        String query = "DELETE FROM " + tableName + " WHERE ID = ?";
                                        PreparedStatement prpStmt = conn.prepareStatement(query);

                                        prpStmt.setInt(1, id);

                                        prpStmt.execute();

                                        System.out.println("Record "+id+" removed.");
                                        break;
                                    }
                                    case 4: {

                                        String query = "SELECT * FROM " + tableName;
                                        ResultSet rs =  st.executeQuery(query);
                                        System.out.println("\n\nID | NAME | SURNAME | PESEL");

                                      while(rs.next()) {
                                         int id = rs.getInt("id");
                                         String name = rs.getString("Name");
                                         String surname = rs.getString("Surname");
                                          String pesel = rs.getString("pesel");

                                      System.out.println(id + " | " + name + " | " + surname + " | " + pesel);
                                     }


                                        System.out.println("\nSelect row to edit: ");

                                         String id = scan.next();
                                         System.out.println("\nSelect column name to edit: ");
                                         String column = scan.next();

                                          System.out.println("\nWrite new value: ");
                                         String edit_value = scan.next();

                                         String query_update = " UPDATE ? SET  ? = WHERE ID = ?";
                                         PreparedStatement prpStmt = conn.prepareStatement(query_update);

                                        prpStmt.setString(1, column);
                                        prpStmt.setString(2, edit_value);
                                        prpStmt.setString(3, id);

                                         prpStmt.execute();

                                        System.out.println("Row edited.");

                                        break;
                                    }
                                    case 0:
                                    default:
                                             try
                                             {
                                                   Thread.sleep(1000);
                                              }
                                              catch(Exception e)
                                              {
                                                  System.out.println(e);
                                               }
                                   execute=false;

                                }
        }
      }
      catch (ClassNotFoundException e) {
         e.printStackTrace();
      }
      catch (SQLException e) {
         createDatabase();
         e.printStackTrace();
      }
     System.out.println("Program's work finished");
   }
}