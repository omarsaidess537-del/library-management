package base;
import pack.Utilisateur;
import util.Util;
import java.sql.*;
public class Utilisateurbase{
    private final Connection con=Util.getConnection();

    public Utilisateur login(String username, String password) {
        String sql="SELECT * FROM UTILISATEUR WHERE nomutilisateur=? AND motdepasse=?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Utilisateur(
                        rs.getInt("ident"),
                        rs.getString("nomutilisateur"),
                        rs.getString("motdepasse"),
                        "user"
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean utilisateurExiste(String username) {
        String sql="SELECT COUNT(*) FROM UTILISATEUR WHERE nomutilisateur=?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public boolean ajouterUtilisateur(String username, String password) {
        if (utilisateurExiste(username)) {
            System.err.println(" L'utilisateur '" + username + "' existe déjà!");
            return false;
        }
        String sql="INSERT INTO UTILISATEUR (nomutilisateur, motdepasse) VALUES (?, ?)";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);
            int result=ps.executeUpdate();
            if (result > 0) {
                System.out.println(" Utilisateur '" + username + "' ajouté avec succès");
                return true;
            }
        } catch (SQLException e) {
            System.err.println(" Erreur lors de l'ajout de l'utilisateur:");
            e.printStackTrace();
        }
        return false;
    }
}