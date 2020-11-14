// import java.sql.Connection;
import java.sql.*;
import java.util.Scanner;

public class Lab4 {

   static final String jdbcDriver = "com.mysql.cj.jdbc.Driver";
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

        Scanner scan = new Scanner(System.in);
        scan.next();



      try {
         Class.forName(jdbcDriver);
         conn = DriverManager.getConnection(dbAddress + dbName, userName, password);

          System.out.println("\n=========================================================");
          System.out.println("\n===================BAZA PRACOWNIKOW======================");
          System.out.println("\n======================ALBERT WOS=========================");
          System.out.println("\n=========================================================");

          System.out.println("\nPolaczenie z baza utworzone \n");

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

            System.out.println ("Polaczenie z bazą danych nawiazane\n");

        boolean execute=true;
        while(execute){
                    System.out.println("\n==========================================================");
                    System.out.println("1. Wyswietl tabele");
                    System.out.println("2. Dodaj rekord do tabeli");
                    System.out.println("3. Usun rekord z tabeli");
                    System.out.println("4. Edytuj dane w tabeli");
                    System.out.println("0. Zakoncz dzialanie programu");

                    System.out.println("\nWpisz numer opcji: ");
                    int selection;
                    selection = scan.nextInt();

                                switch(selection) {
                                    case 1: { //WYświetlenie zawartości tabeli

                                        String query = "SELECT * FROM " + tableName;
                                        ResultSet rs =  st.executeQuery(query);
                                        System.out.println("|--------------------------------------------------------------------|");
                                        System.out.println("\n1 \t|\t 2 \t|\t 3 \t\t|\t 4");
                                        System.out.println("ID \t|\t NAME \t|\t SURNAME \t|\t DEPARTMENT\n");
                                        System.out.println("|--------------------------------------------------------------------|\n");

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
                                        int row_count = 0;

                                        String query = "SELECT * FROM " + tableName;
                                        ResultSet rs =  st.executeQuery(query);
                                        System.out.println("|--------------------------------------------------------------------|");
                                        System.out.println("\n1 \t|\t 2 \t|\t 3 \t\t|\t 4");
                                        System.out.println("ID \t|\t NAME \t|\t SURNAME \t|\t DEPARTMENT\n");
                                        System.out.println("|--------------------------------------------------------------------|\n");

                                      while(rs.next()) {
                                         int id = rs.getInt("id");
                                         String name = rs.getString("Name");
                                         String surname = rs.getString("Surname");
                                          String department = rs.getString("Department");

                                      System.out.println(id + " \t|\t " + name + " \t|\t " + surname + " \t|\t " + department);
                                     row_count++;
                                     }


                                        System.out.println("\nWybierz ID rekordu do edycji:  ( 0 - by powrocic)");

                                        int id = scan.nextInt();

                                         if(id ==0 )
                                         break;

                                        if(id>row_count){
                                         System.out.println("\nNieprawidłowe ID\n");
                                         break;
                                        }

                                         System.out.println("\nWpisz numer kolumny do edycji: ");
                                         int columnNumber = scan.nextInt();
                                         String columnName = "";

                                          switch(columnNumber) {
                                            case 1: {
                                             System.out.println("\nNie można zmienić ID wiersza\n");
                                            break;
                                            }
                                             case 2: {
                                             columnName = "NAME";
                                            break;
                                            }
                                            case 3: {
                                             columnName = "SURNAME";

                                            break;
                                            }
                                           case 4: {
                                             columnName = "DEPARTMENT";
                                            break;
                                            }
                                            default:
                                            System.out.println("\nNie poprawny numer kolumny\n");
                                            columnNumber = 0;
                                            break;
                                            }

                                         if(columnNumber ==0 )
                                         break;

                                         System.out.println("\nWpisz nowa wartosc: ");
                                         String edit_value = scan.next();


                                         String query_update = " UPDATE " + tableName
                                                             + " SET "+columnName
                                                             +" = ? WHERE ID = ?;";

                                         PreparedStatement prpStmt = conn.prepareStatement(query_update);

                                        prpStmt.setString(1, edit_value);
                                        prpStmt.setInt(2, id);
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
          e.printStackTrace();
      }
     System.out.println("Koniec dzialania programu");
   }
}