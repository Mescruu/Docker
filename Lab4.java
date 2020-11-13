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

      System.out.println("\n Welcome to program\n");
        Scanner scan = new Scanner(System.in);
        scan.next();



      try {
         Class.forName(jdbcDriver);
         conn = DriverManager.getConnection(dbAddress + dbName, userName, password);
                   System.out.println("\n main function: connectrion created \n");

            st = conn.createStatement();


            //Utworzenie bazy danych
            st.executeUpdate("CREATE DATABASE IF NOT EXISTS " + dbName);
            //Przejście do tej bazy
            st.executeUpdate("USE "+ dbName);
              String new_table = "CREATE TABLE IF NOT EXISTS " + tableName + " ("
                                + "ID INT(4) NOT NULL AUTO_INCREMENT,"
                                + "NAME VARCHAR(30),"
                                + "SURNAME VARCHAR(30),"
                                + "DEPARTMENT VARCHAR(11),"
                                + "PRIMARY KEY (ID))";

            //Utworzenie tabeli
            st.executeUpdate(new_table);

            System.out.println ("\nDatabase Connection Established...");

        boolean execute=true;
        while(execute){
                    System.out.println("1: Wyswietl tabele.");
                    System.out.println("2. Dodaj rekord do tabeli:");
                    System.out.println("3. Usun rekord z tabeli:");
                    System.out.println("4. Edytuj dane w tabeli:");
                    System.out.println("0. Zakoncz dzialanie programu");

                    System.out.println("Wpisz numer opcji: ");
                    int selection;
                    selection = scan.nextInt();

                                switch(selection) {
                                    case 1: { //WYświetlenie zawartości tabeli

                                        String query = "SELECT * FROM " + tableName;
                                        ResultSet rs =  st.executeQuery(query);
                                        System.out.println("\n1 \t|\t 2 \t|\t 3 \t\t|\t 4");
                                        System.out.println("\nID \t|\t NAME \t|\t SURNAME \t|\t DEPARTMENT");

                                      while(rs.next()) {
                                         int id = rs.getInt("id");
                                         String name = rs.getString("Name");
                                         String surname = rs.getString("Surname");
                                          String department = rs.getString("Department");

                                      System.out.println(id + " \t|\t " + name + " \t|\t " + surname + " \t|\t " + department);
                                     }

                                        break;
                                    }
                                    case 2: { //Dodanie rekordów do tabeli (ograniczone maksimum)

                                        System.out.println("Podaj imie: ");
                                        String name = scan.next();

                                        System.out.println("Podaj nazwisko: ");
                                        String surname = scan.next();

                                        System.out.println("Podaj dzial: ");
                                        String department = scan.next();

                                        String query = "INSERT INTO " + tableName + " ( NAME, SURNAME, DEPARTMENT) VALUES (?, ?, ?)";
                                        PreparedStatement prpStmt = conn.prepareStatement(query);

                                        prpStmt.setString(1, name);
                                        prpStmt.setString(2, surname);
                                        prpStmt.setString(3, department);

                                        prpStmt.execute();

                                        System.out.println("\nRekord zostal dodany.\n");


                                        break;
                                    }
                                    case 3: { //Usunięcie rekordu o zadanym ID
                                        System.out.println("\nWybierz rekord do usuniecia: ");
                                        int id = scan.nextInt();


                                        String query = "DELETE FROM " + tableName + " WHERE ID = ?";
                                        PreparedStatement prpStmt = conn.prepareStatement(query);

                                        prpStmt.setInt(1, id);

                                        prpStmt.execute();

                                        System.out.println("Rekord zostal usuniety.");
                                        break;
                                    }
                                    case 4: {

                                        String query = "SELECT * FROM " + tableName;
                                        ResultSet rs =  st.executeQuery(query);
                                        System.out.println("\n1 \t|\t 2 \t|\t 3 \t\t|\t 4");
                                        System.out.println("\nID \t|\t NAME \t|\t SURNAME \t|\t DEPARTMENT");

                                      while(rs.next()) {
                                         int id = rs.getInt("id");
                                         String name = rs.getString("Name");
                                         String surname = rs.getString("Surname");
                                          String department = rs.getString("Department");

                                      System.out.println(id + " \t|\t " + name + " \t|\t " + surname + " \t|\t " + department);
                                     }


                                        System.out.println("\nWybierz ID rekordu do edycji: ");

                                         String id = scan.next();
                                         System.out.println("\nWybierz nuemr kolumny do edycji: ");
                                         String column = scan.next();

                                          System.out.println("\nWpisz nowa wartosc: ");
                                         String edit_value = scan.next();

                                         String query_update = " UPDATE " + tableName + " SET  ? = ? WHERE ID = ?";
                                         PreparedStatement prpStmt = conn.prepareStatement(query_update);

                                        prpStmt.setString(1, column);
                                        prpStmt.setString(2, edit_value);
                                        prpStmt.setString(3, id);

                                         prpStmt.execute();

                                        System.out.println("Rekord zostal edytowany.");

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
         // e.printStackTrace();
      }
     System.out.println("Koniec dzialania programu");
   }
}