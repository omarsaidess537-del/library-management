package main;
import base.*;
import pack.*;
import java.sql.Date;
import java.util.List;
public class Biblio{
    private Livrebase livre;
    private Adherentbase adherent;
    private Empruntbase emprunt;
    private Utilisateurbase utilisateur;
    public Biblio(){
        livre=new Livrebase();
        adherent=new Adherentbase();
        emprunt=new Empruntbase();
        utilisateur=new Utilisateurbase();
    }
    public void ajouterLivre(Livres l) {
        livre.ajouter(l);
    }
    public void modifierLivre(Livres l) {
        livre.modifier(l);
    }
    public void supprimerLivre(String isbn) {
        livre.supprimer(isbn);
    }
    public Livres rechercherLivre(String isbn) {
        return livre.recherche(isbn);
    }
    public List<Livres> getAllLivres() {
        return livre.lister();
    }
    public void ajouterAdherent(Adherent a) {
        adherent.ajouter(a); }
    public void modifierAdherent(Adherent a){
        adherent.modifier(a);
    }
    public void supprimerAdherent(int tel){
        adherent.supprimer(tel);
    }
    public Adherent rechercherAdherent(int tel){
        return adherent.recherche(tel);
    }
    public List<Adherent> getAllAdherents(){
        return adherent.lister();
    }
    public boolean ajouterEmprunt(Emprunt e) {
        Livres l = livre.recherche(e.getIsbnlivre());
        if (l == null) {
            System.err.println("Erreur: Livre introuvable (ISBN: " + e.getIsbnlivre() + ")");
            return false;
        }
        if (!l.getDisponible()) {
            System.err.println("Erreur: Livre non disponible (ISBN: " + e.getIsbnlivre() + ")");
            return false;
        }
        Adherent a = adherent.recherche(e.getNumtelad());
        if (a == null) {
            System.err.println("Erreur: Adhérent introuvable (Tel: " + e.getNumtelad() + ")");
            return false;
        }
        boolean success = emprunt.ajouter(e);
        if (success) {
            l.setDisponible(false);
            livre.modifier(l);
            System.out.println("Emprunt ajouté avec succès: ID=" + e.getId());
        } else {
            System.err.println("Erreur lors de l'ajout de l'emprunt");
        }
        return success;
    }
    public boolean enregistrerRetour(int idEmprunt, Date dateRetour) {
        Emprunt e = emprunt.lister().stream()
                .filter(em -> em.getId() == idEmprunt)
                .findFirst()
                .orElse(null);
        if (e == null) {
            System.err.println("Erreur: Emprunt introuvable (ID: " + idEmprunt + ")");
            return false;
        }
        if (e.getDateretour() != null) {
            System.err.println("Erreur: Livre déjà retourné le " + e.getDateretour());
            return false;
        }
        boolean success = emprunt.enregistrerRetour(idEmprunt, dateRetour);
        if (success) {
            Livres l = livre.recherche(e.getIsbnlivre());
            if (l != null) {
                l.setDisponible(true);
                livre.modifier(l);
                System.out.println("Retour enregistré avec succès: Emprunt ID=" + idEmprunt);
            } else {
                System.err.println("Avertissement: Livre introuvable lors du retour (ISBN: " + e.getIsbnlivre() + ")");
            }
        } else {
            System.err.println("Erreur lors de l'enregistrement du retour");
        }
        return success;
    }
    public List<Emprunt> getAllEmprunts() {
        return emprunt.lister();
    }
    public Utilisateur login(String username, String password) {
        return utilisateur.login(username,password);
    }
    public boolean ajouterUtilisateur(String username, String password) {
        return utilisateur.ajouterUtilisateur(username, password);
    }
}