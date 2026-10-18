package base;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import pack.Livres;
import util.Util;
public class Livrebase {
    private final Connection con=Util.getConnection();
    public boolean ajouter(Livres l){
        String sql="INSERT INTO LIVRES VALUES (?, ?, ?, ?, ?)";
        try{
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setString(1, l.getIsbn());
            ps.setString(2, l.getTitre());
            ps.setString(3, l.getAuteur());
            ps.setString(4, l.getCategorie());
            ps.setBoolean(5, l.getDisponible());
            return ps.executeUpdate() > 0;
        } catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }
    public boolean modifier(Livres l) {
        String sql = "UPDATE LIVRES SET titre=?, auteur=?, categorie=?, disponible=? WHERE isbn=?";
        try{
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, l.getTitre());
            ps.setString(2, l.getAuteur());
            ps.setString(3, l.getCategorie());
            ps.setBoolean(4, l.getDisponible());
            ps.setString(5, l.getIsbn());
            return ps.executeUpdate() > 0;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }
    public boolean supprimer(String isbn){
        String sql="DELETE FROM LIVRES WHERE isbn=?";
        try{
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setString(1, isbn);
            return ps.executeUpdate() > 0;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }
    public Livres recherche(String isbn) {
        String sql="SELECT * FROM LIVRES WHERE isbn=?";
        try{
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, isbn);
            ResultSet rs=ps.executeQuery();
            if(rs.next()){
                return new Livres(
                        rs.getString("isbn"),
                        rs.getString("titre"),
                        rs.getString("auteur"),
                        rs.getString("categorie"),
                        rs.getBoolean("disponible")
                );
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }
    public List<Livres> lister(){
        List<Livres> liste=new ArrayList<>();
        String sql="SELECT * FROM LIVRES";
        try {
            Statement st=con.createStatement();
            ResultSet rs=st.executeQuery(sql);
            while(rs.next()){
                liste.add(new Livres(
                        rs.getString("isbn"),
                        rs.getString("titre"),
                        rs.getString("auteur"),
                        rs.getString("categorie"),
                        rs.getBoolean("disponible")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return liste;
    }
}