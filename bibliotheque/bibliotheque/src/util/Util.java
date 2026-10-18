package util;
import java.sql.Connection;
import java.sql.DriverManager;
public class Util{
    private static final String URL="jdbc:mysql://localhost:3306/bibliotheque";
    private static final String USER="root";
    private static final String PASSWORD="Sayada2025";
    private static Connection con=null;
    public static Connection getConnection(){
        try{
            if (con == null || con.isClosed()){
                Class.forName("com.mysql.cj.jdbc.Driver");
                con = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Connexion établie !");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return con;
    }

}