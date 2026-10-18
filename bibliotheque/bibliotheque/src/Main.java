import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/bibliotheque"; // votre base
        String user = "root"; // votre utilisateur
        String password = ""; // votre mot de passe

        try {
            Connection con = DriverManager.getConnection(url, user, password);
            if (con != null) {
                System.out.println("Connexion réussie !");
                con.close();
            } else {
                System.out.println("Connexion échouée !");
            }
        } catch (SQLException e) {
            System.out.println("Erreur de connexion : " + e.getMessage());
        }
    }
}