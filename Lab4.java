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
   static final String nazwa_tabeli = "employees";
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

      System.out.println(" Welcome to test\n");
        Scanner scan = new Scanner(System.in);
        scan.next();



      try {
         Class.forName(jdbcDriver);
         conn = DriverManager.getConnection(dbAddress + dbName, userName, password);
                   System.out.println(" main function: connectrion created \n");

            st = conn.createStatement();


            //Utworzenie bazy danych
            st.executeUpdate("CREATE DATABASE IF NOT EXISTS " + dbName);
            //Przejście do tej bazy
            st.executeUpdate("USE "+ dbName);
              String new_table = "CREATE TABLE IF NOT EXISTS " + nazwa_tabeli + " ("
                                + "ID INT(4) NOT NULL AUTO_INCREMENT,"
                                + "NAME VARCHAR(30),"
                                + "SURNAME VARCHAR(30),"
                                + "PESEL VARCHAR(11),"
                                + "PRIMARY KEY (ID))";

            //Utworzenie tabeli
            st.executeUpdate(new_table);

            System.out.println ("\nDatabase Connection Established...");

        boolean execute=true;
        while(execute){
                    System.out.println("\n\n\nMENU GLOWNE");
                    System.out.println("1: Wyswietl zawartosc tabeli.");
                    System.out.println("2. Dodaj do tabeli:");
                    System.out.println("3. Usun z tabeli:");
                    System.out.println("4. Edytuj dane z tabeli:");
                    System.out.println("0. Wyjscie");

                    System.out.println("Wybierz opcje: ");
                    int selection;
                    selection = scan.nextInt();

                                switch(selection) {
                                    case 1: { //WYświetlenie zawartości tabeli

                                        String query = "SELECT * FROM " + nazwa_tabeli;
                                        ResultSet rs =  st.executeQuery(query);
                                        System.out.println("\n\nID | NAME | SURNAME | PESEL");

                                      while(rs.next()) {
                                         int id = rs.getInt("id");
                                         String name = rs.getString("Name");
                                         String surname = rs.getString("Surname");
                                          String pesel = rs.getString("pesel");

                                      System.out.println(id + " | " + name + " | " + surname + " | " + pesel);
                                     }

                                        break;
                                    }
                                    case 2: { //Dodanie rekordów do tabeli (ograniczone maksimum)

                                        System.out.println("\nIle rekordow chcesz dodac (od 1 do 10)");
                                        int count = scan.nextInt();

                                        if(count > 10 || count == 0){
                                            System.out.println("\nNiepoprawna wartość. Wpisz liczbę z zakresu od 1 do 10: ");
                                            count = scan.nextInt();
                                        }
                                        while(count > 0){

                                             try
                                             {
                                                   Thread.sleep(1000);
                                              }
                                              catch(Exception e)
                                              {
                                                  System.out.println(e);
                                               }


                                        System.out.println("Podaj imie: ");
                                        String name = scan.next();

                                        System.out.println("Podaj nazwisko: ");
                                        String surname = scan.next();

                                        System.out.println("Podaj pesel: ");
                                        String pesel = scan.next();

                                        String query = "INSERT INTO " + nazwa_tabeli + " ( NAME, SURNAME, PESEL) VALUES (?, ?, ?)";
                                        PreparedStatement prpStmt = conn.prepareStatement(query);

                                        prpStmt.setString(1, name);
                                        prpStmt.setString(2, surname);
                                        prpStmt.setString(3, pesel);

                                        prpStmt.execute();

                                        System.out.println("\nDodano rekord.\n");

                                        count--;

                                        }
                                        break;
                                    }
                                    case 3: { //Usunięcie rekordu o zadanym ID
                                        System.out.println("\nWybierz rekord do usuniecia: ");
                                        int id = scan.nextInt();


                                        String query = "DELETE FROM " + nazwa_tabeli + " WHERE ID = ?";
                                        PreparedStatement prpStmt = conn.prepareStatement(query);

                                        prpStmt.setInt(1, id);

                                        prpStmt.execute();

                                        System.out.println("Usunięto rekord.");
                                        break;
                                    }
                                    case 4: {

                                        String query = "SELECT * FROM " + nazwa_tabeli;
                                        ResultSet rs =  st.executeQuery(query);
                                        System.out.println("\n\nID | NAME | SURNAME | PESEL");

                                      while(rs.next()) {
                                         int id = rs.getInt("id");
                                         String name = rs.getString("Name");
                                         String surname = rs.getString("Surname");
                                          String pesel = rs.getString("pesel");

                                      System.out.println(id + " | " + name + " | " + surname + " | " + pesel);
                                     }


                                        System.out.println("\nWybierz rekord do edytowania: ");

                                         String id = scan.next();
                                         System.out.println("\nWybierz kolumne do edytowania: ");
                                         String column = scan.next();

                                          System.out.println("\nWpisz nowa wartosc: ");
                                         String edit_value = scan.next();

                                         String query_update = " UPDATE ? SET  ? = WHERE ID = ?";
                                         PreparedStatement prpStmt = conn.prepareStatement(query_update);

                                        prpStmt.setString(1, column);
                                        prpStmt.setString(2, edit_value);
                                        prpStmt.setString(3, id);

                                         prpStmt.execute();

                                        System.out.println("Edytowano rekord.");

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