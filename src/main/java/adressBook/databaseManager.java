package adressBook;

import java.sql.*;
import java.util.HashMap;

public class databaseManager {


    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/contacts";

    //  Database credentials
    static final String USER = "root";
    static final String PASS = "";


    public static HashMap<Integer, Contact> tryDBConnection() {
        Connection conn = null;
        Statement stmt = null;
        try {
            //STEP 2: Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            //STEP 3: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            //STEP 4: Execute a query
            System.out.println("Creating statement...");
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT id, name, forename, email, mobile, work, adress, town, zip FROM contacts";
            ResultSet rs = stmt.executeQuery(sql);

            HashMap<Integer, Contact> contactsById = new HashMap<>();

            //STEP 5: Extract data from result set
            while (rs.next()) {
                //Retrieve by column name
                int id = rs.getInt("id");
                String forename = rs.getString("forename");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String mobile = rs.getString("mobile");
                String work = rs.getString("work");
                String adress = rs.getString("adress");
                String town = rs.getString("town");
                String zip = rs.getString("zip");

                Contact contact = new Contact(id, forename, name, email, mobile, work, adress, town, zip);
                contactsById.put(contact.getId(), contact);

//                //Display values
//                System.out.print("ID: " + id);
//                System.out.print(", Age: " + email);
//                System.out.print(", First: " + first);
//                System.out.println(", Last: " + last);
//                System.out.println(", work: " + work);
            }
            //STEP 6: Clean-up environment
            rs.close();
            stmt.close();
            conn.close();
            return contactsById;
        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se2) {
            }// nothing we can do
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }//end finally try
        }//end try
        System.out.println("Goodbye!");
        return null;
    }

    public static void main(String[] args) {
        tryDBConnection();
        //end main
    }//end FirstExample

}
