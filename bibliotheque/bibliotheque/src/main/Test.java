package main;
import Interface.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pack.Utilisateur;
public class Test extends Application {
    @Override
    public void start(Stage stage) {
        Biblio biblio = new Biblio();
        Logincontrole login = new Logincontrole();
        Scene loginScene = new Scene(login.getView(), 400, 500);

        Interfacecontrole dashboard = new Interfacecontrole();
        Scene dashboardScene = new Scene(dashboard.getView(), 600, 500);

        login.setOnLoginAttempt((username, password) -> {
            // CAS 1: Admin se connecte
            if (username.equals("admin") && password.equals("admin123")) {
                System.out.println("✅ Connexion ADMIN réussie");
                stage.setScene(dashboardScene);
                login.hideError();
                return;
            }

            Utilisateur user = biblio.login(username, password);
            if (user != null) {
                System.out.println("✅ Connexion utilisateur réussie: " + user.getNomutilisateur());
                InterfaceUtilisateur userInterface = new InterfaceUtilisateur(biblio, username);
                Scene userScene = new Scene(userInterface.getView(), 1000, 600);


                userInterface.setOnLogoutClick(() -> {
                    login.clearForm();
                    stage.setScene(loginScene);
                    System.out.println("🚪 Déconnexion de: " + username);
                });

                stage.setScene(userScene);
                login.hideError();

            } else {

                System.out.println("ℹ️  Utilisateur inexistant. Création du compte...");
                boolean ajoutReussi = biblio.ajouterUtilisateur(username, password);
                if (ajoutReussi) {
                    System.out.println("✅ Compte créé avec succès pour: " + username);
                    InterfaceUtilisateur userInterface = new InterfaceUtilisateur(biblio, username);
                    Scene userScene = new Scene(userInterface.getView(), 1000, 600);
                    userInterface.setOnLogoutClick(() -> {
                        login.clearForm();
                        stage.setScene(loginScene);
                        System.out.println("🚪 Déconnexion de: " + username);
                    });
                    stage.setScene(userScene);
                    login.hideError();
                } else {
                    System.err.println("❌ Erreur lors de la création du compte");
                    login.showError("Erreur lors de la création du compte!");
                }
            }
        });
        dashboard.setOnLogoutClick(() -> {
            login.clearForm();
            stage.setScene(loginScene);
        });
        dashboard.setOnManageBooksClick(() -> {
            Stage livresStage = new Stage();
            livresStage.setTitle("Gestion des livres");
            InterfaceLivre li = new InterfaceLivre(biblio);
            livresStage.setScene(new Scene(li.getView(), 800, 500));
            livresStage.show();
        });
        dashboard.setOnManageAdherentsClick(() -> {
            InterfaceAdherent ai = new InterfaceAdherent(biblio);
            Stage s = new Stage();
            s.setTitle("Gestion des adhérents");
            s.setScene(new Scene(ai.getView(), 900, 500));
            s.show();
        });
        dashboard.setOnManageEmpruntsClick(() -> {
            InterfaceEmprunt ei = new InterfaceEmprunt(biblio);
            Stage s = new Stage();
            s.setTitle("Gestion des emprunts");
            s.setScene(new Scene(ei.getView(), 1000, 550));
            s.show();
        });
        stage.setScene(loginScene);
        stage.setTitle("Application Bibliothèque");
        stage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
