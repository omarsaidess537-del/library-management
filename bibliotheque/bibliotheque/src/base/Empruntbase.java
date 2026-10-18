package base;

import pack.Emprunt;
import util.Util;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Empruntbase{
    private final Connection con=Util.getConnection();
    public boolean ajouter(Emprunt e){
        String sql="INSERT INTO EMPRUNT (id,isbn, numtel, dateemp, dateretour) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps=con.prepareStatement(sql)){
            ps.setInt(1,e.getId());
            ps.setString(2,e.getIsbnlivre());
            ps.setInt(3,e.getNumtelad());
            ps.setDate(4,e.getDateemp());
            ps.setDate(5,e.getDateretour());
            return ps.executeUpdate()>0;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }
    public boolean enregistrerRetour(int id,Date dateretour){
        String sql="UPDATE EMPRUNT SET dateretour = ? WHERE id = ?";
        try(PreparedStatement ps = con.prepareStatement(sql)){
            ps.setDate(1,dateretour);
            ps.setInt(2,id);
            return ps.executeUpdate()>0;
        } catch(SQLException ex){
            ex.printStackTrace();
            return false;
        }
    }
    public List<Emprunt> lister(){
        List<Emprunt> liste=new ArrayList<>();
        String sql="SELECT * FROM EMPRUNT";
        try(PreparedStatement ps=con.prepareStatement(sql)){
            ResultSet rs=ps.executeQuery();
            while(rs.next()){
                liste.add(new Emprunt(
                        rs.getInt("id"),
                        rs.getString("isbn"),
                        rs.getInt("numtel"),
                        rs.getDate("dateemp"),
                        rs.getDate("dateretour")
                ));
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        return liste;
    }
}