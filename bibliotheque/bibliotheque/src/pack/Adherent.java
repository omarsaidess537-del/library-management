package pack;
import java.sql.Date;

public class Adherent {
    private int numtel;
    private String nom;
    private String prenom;
    private Date datenaiss;
    private String type;
    public int getNumtel(){
        return numtel;
    }
    public void setNumtel(int numtel){
        this.numtel=numtel;
    }
    public String getNom(){
        return nom;
    }
    public void setNom(String nom){
        this.nom=nom;
    }
    public String getPrenom(){
        return prenom;
    }
    public void setPrenom(String prenom){
        this.prenom=prenom;
    }
    public Date getDatenaiss(){
        return datenaiss;
    }
    public void setDatenaiss(Date datenaiss){
        this.datenaiss=datenaiss;
    }
    public String getType(){
        return type;
    }
    public void setType(String type){
        this.type=type;
    }
    public Adherent(int numtel,String nom,String prenom,Date datenaiss,String type){
        this.numtel=numtel;
        this.nom=nom;
        this.prenom=prenom;
        this.datenaiss=datenaiss;
        this.type=type;
    }
}