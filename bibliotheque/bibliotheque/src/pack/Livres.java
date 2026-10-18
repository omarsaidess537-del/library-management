package pack;

public class Livres {
    private String isbn;
    private String titre;
    private String auteur;
    private String categorie;
    private boolean disponible;

    public String getIsbn() {
        return isbn;
    }
    public void setIsbn(String isbn){
        this.isbn = isbn;
    }
    public String getTitre(){
        return titre;
    }
    public void setTitre(String titre){
        this.titre = titre;
    }
    public String getAuteur(){
        return auteur;
    }
    public void setAuteur(String auteur){
        this.auteur = auteur;
    }
    public String getCategorie(){
        return categorie;
    }
    public void setCategorie(String categorie){
        this.categorie = categorie;
    }
    public boolean getDisponible(){
        return disponible;
    }
    public void setDisponible(boolean disponible){
        this.disponible = disponible;
    }
    public Livres(String isbn,String titre,String auteur,String categorie,boolean disponible) {
        this.isbn=isbn;
        this.titre=titre;
        this.auteur=auteur;
        this.categorie=categorie;
        this.disponible=disponible;
    }
}
