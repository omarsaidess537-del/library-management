package pack;
import java.sql.Date;
public class Emprunt{
    private int id;
    private String isbnlivre;
    private int numtelad;
    private Date dateemp;
    private Date dateretour;

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id=id;
    }

    public String getIsbnlivre(){
        return isbnlivre;
    }

    public void setIsbnlivre(String isbnlivre){
        this.isbnlivre=isbnlivre;
    }

    public int getNumtelad(){
        return numtelad;
    }

    public void setNumtelad(int numtelad){
        this.numtelad = numtelad;
    }

    public Date getDateemp(){
        return dateemp;
    }

    public void setDateemp(Date dateemp){
        this.dateemp=dateemp;
    }

    public Date getDateretour(){
        return dateretour;
    }

    public void setDateretour(Date dateretour){
        this.dateretour=dateretour;
    }

    public Emprunt(int id,String isbnlivre,int numtelad,Date dateemp,Date dateretour){
        this.id=id;
        this.isbnlivre=isbnlivre;
        this.numtelad=numtelad;
        this.dateemp=dateemp;
        this.dateretour=dateretour;
    }
}