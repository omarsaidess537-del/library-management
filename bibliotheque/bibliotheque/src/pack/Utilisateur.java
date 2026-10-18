package pack;

public class Utilisateur{
    private int ident;
    private String nomutilisateur;
    private String motdepasse;
    private String role;
    public int getIdent(){
        return ident;
    }
    public void setIdent(int ident){
        this.ident = ident;
    }
    public String getNomutilisateur(){
        return nomutilisateur;
    }
    public void setNomutilisateur(String nomutilisateur){
        this.nomutilisateur = nomutilisateur;
    }
    public String getMotdepasse(){
        return motdepasse;
    }
    public void setMotdepasse(String motdepasse){
        this.motdepasse=motdepasse;
    }
    public String getRole(){
        return role;
    }
    public void setRole(String role){
        this.role=role;
    }
    public Utilisateur(int ident,String nomutilisateur,String motdepasse,String role) {
        this.ident=ident;
        this.nomutilisateur=nomutilisateur;
        this.motdepasse=motdepasse;
        this.role=role;
    }
}