package base;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import pack.Adherent;
import util.Util;
public class Adherentbase{
    private final Connection con =Util.getConnection();
    public boolean ajouter(Adherent a) {
        String sql="INSERT INTO ADHERENTS (numtel, nom, prenom, datenaiss, type) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, a.getNumtel());
            ps.setString(2, a.getNom());
            ps.setString(3, a.getPrenom());
            ps.setDate(4, a.getDatenaiss());
            ps.setString(5, a.getType());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public boolean modifier(Adherent a){
        String sql = "UPDATE ADHERENTS SET nom=?, prenom=?, datenaiss=?, type=? WHERE numtel=?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1,a.getNom());
            ps.setString(2,a.getPrenom());
            ps.setDate(3,a.getDatenaiss());
            ps.setString(4,a.getType());
            ps.setInt(5,a.getNumtel());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public boolean supprimer(int numtel) {
        String sql="DELETE FROM ADHERENTS WHERE numtel=?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, numtel);
            return ps.executeUpdate() > 0;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }
    public Adherent recherche(int numtel) {
        String sql="SELECT * FROM ADHERENTS WHERE numtel=?";
        try (PreparedStatement ps=con.prepareStatement(sql)) {
            ps.setInt(1, numtel);
            ResultSet rs=ps.executeQuery();
            if(rs.next()){
                return new Adherent(
                        rs.getInt("numtel"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getDate("datenaiss"),
                        rs.getString("type")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public List<Adherent> lister() {
        List<Adherent> liste = new ArrayList<>();
        String sql = "SELECT * FROM ADHERENTS";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                liste.add(new Adherent(
                        rs.getInt("numtel"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getDate("datenaiss"),
                        rs.getString("type")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return liste;
    }
}