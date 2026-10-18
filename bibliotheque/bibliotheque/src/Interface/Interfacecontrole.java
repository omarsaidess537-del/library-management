package Interface;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
public class Interfacecontrole {
    private Parent view;
    private Runnable onLogoutClick;
    private Runnable onManageBooksClick;
    private Runnable onManageAdherentsClick;
    private Runnable onManageEmpruntsClick;
    public Interfacecontrole() {
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(20));
        root.setStyle("-fx-background-color: #f5f6fa;");
        HBox topBar = new HBox();
        topBar.setPadding(new Insets(15));
        topBar.setStyle("-fx-background-color: #4b3aa8;");
        Label title = new Label("Système de Gestion de Bibliothèque");
        title.setTextFill(javafx.scene.paint.Color.WHITE);
        title.setFont(new Font(22));
        topBar.getChildren().add(title);
        VBox centerBox = new VBox(20);
        centerBox.setAlignment(Pos.CENTER);
        Button booksBtn = createButton("📚  Gestion des Livres");
        booksBtn.setOnAction(e -> handleManageBooks());
        Button adherentsBtn = createButton("👤  Gestion des Adhérents");
        adherentsBtn.setOnAction(e -> handleManageAdherents());
        Button empruntsBtn = createButton("📖  Gestion des Emprunts");
        empruntsBtn.setOnAction(e -> handleManageEmprunts());
        centerBox.getChildren().addAll(booksBtn, adherentsBtn, empruntsBtn);
        Button logoutBtn = new Button("Déconnexion");
        logoutBtn.setStyle(
                "-fx-background-color: #d32f2f; -fx-text-fill: white; -fx-font-size: 14px;"
        );
        logoutBtn.setOnAction(e -> handleLogout());
        BorderPane.setAlignment(logoutBtn, Pos.CENTER);
        BorderPane.setMargin(logoutBtn, new Insets(10));
        root.setTop(topBar);
        root.setCenter(centerBox);
        root.setBottom(logoutBtn);
        view = root;
    }
    private Button createButton(String text) {
        Button btn = new Button(text);
        btn.setPrefWidth(250);
        btn.setStyle(
                "-fx-background-color: white;" +
                        "-fx-font-size: 16px;" +
                        "-fx-border-radius: 10;" +
                        "-fx-background-radius: 10;" +
                        "-fx-padding: 10;" +
                        "-fx-border-color: #4b3aa8;"
        );
        return btn;
    }
    private void handleLogout() {
        if (onLogoutClick != null) onLogoutClick.run();
    }
    private void handleManageBooks() {
        if (onManageBooksClick != null) onManageBooksClick.run();
    }
    private void handleManageAdherents() {
        if (onManageAdherentsClick != null) onManageAdherentsClick.run();
    }
    private void handleManageEmprunts() {
        if (onManageEmpruntsClick != null) onManageEmpruntsClick.run();
    }
    public void setOnLogoutClick(Runnable r) { this.onLogoutClick = r; }
    public void setOnManageBooksClick(Runnable r) { this.onManageBooksClick = r; }
    public void setOnManageAdherentsClick(Runnable r) { this.onManageAdherentsClick = r; }
    public void setOnManageEmpruntsClick(Runnable r) { this.onManageEmpruntsClick = r; }
    public Parent getView() {
        return view;
    }
}