package Interface;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.beans.property.SimpleStringProperty;
import main.Biblio;
import pack.Emprunt;
import pack.Livres;

public class InterfaceUtilisateur {
    private Parent view;
    private Biblio biblio;
    private Runnable onLogoutClick;
    private String currentUsername;
    private TableView<Livres> tableLivres;
    private TableView<Emprunt> tableEmprunts;

    public InterfaceUtilisateur(Biblio biblio, String username) {
        this.biblio = biblio;
        this.currentUsername = username;

        BorderPane root = new BorderPane();
        root.setPadding(new Insets(20));
        root.setStyle("-fx-background-color: #f5f6fa;");


        HBox topBar = new HBox();
        topBar.setPadding(new Insets(15));
        topBar.setStyle("-fx-background-color: #2b7cd3;");
        topBar.setSpacing(20);

        Label title = new Label("📚 Bibliothèque - Espace Utilisateur");
        title.setTextFill(javafx.scene.paint.Color.WHITE);
        title.setFont(new Font(20));

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Label userLabel = new Label("👤 " + username);
        userLabel.setTextFill(javafx.scene.paint.Color.WHITE);
        userLabel.setFont(new Font(14));

        topBar.getChildren().addAll(title, spacer, userLabel);


        TabPane tabPane = new TabPane();
        tabPane.setStyle("-fx-background-color: white;");


        Tab tabCatalogue = new Tab("📖 Catalogue");
        tabCatalogue.setClosable(false);
        tabCatalogue.setContent(createCatalogueView());


        Tab tabMesEmprunts = new Tab("📋 Mes Emprunts");
        tabMesEmprunts.setClosable(false);
        tabMesEmprunts.setContent(createMesEmpruntsView());
        tabPane.getTabs().addAll(tabCatalogue, tabMesEmprunts);

        Button logoutBtn = new Button("🚪 Déconnexion");
        logoutBtn.setStyle(
                "-fx-background-color: #d32f2f; " +
                        "-fx-text-fill: white; " +
                        "-fx-font-size: 14px; " +
                        "-fx-padding: 10 20; " +
                        "-fx-background-radius: 5;"
        );
        logoutBtn.setOnAction(e -> handleLogout());
        BorderPane.setAlignment(logoutBtn, Pos.CENTER);
        BorderPane.setMargin(logoutBtn, new Insets(10));
        root.setTop(topBar);
        root.setCenter(tabPane);
        root.setBottom(logoutBtn);
        view = root;
    }
    private Parent createCatalogueView() {
        VBox container = new VBox(10);
        container.setPadding(new Insets(15));
        Label titleLabel = new Label("Livres Disponibles");
        titleLabel.setFont(new Font(18));
        titleLabel.setStyle("-fx-font-weight: bold;");

        HBox searchBar = new HBox(10);
        searchBar.setAlignment(Pos.CENTER_LEFT);
        TextField searchField = new TextField();
        searchField.setPromptText("🔍 Rechercher un livre (titre, auteur, ISBN)");
        searchField.setPrefWidth(400);
        ComboBox<String> categorieFilter = new ComboBox<>();
        categorieFilter.setPromptText("Catégorie");
        categorieFilter.getItems().addAll("Toutes", "comedie", "Roman classique", "Fantasy", "Science-fiction", "Autre");
        categorieFilter.setValue("Toutes");
        Button searchBtn = new Button("Rechercher");
        searchBtn.setStyle("-fx-background-color: #4b3aa8; -fx-text-fill: white;");
        Button resetBtn = new Button("Réinitialiser");
        resetBtn.setStyle("-fx-background-color: #757575; -fx-text-fill: white;");
        searchBar.getChildren().addAll(searchField, categorieFilter, searchBtn, resetBtn);

        tableLivres = new TableView<>();
        tableLivres.setStyle("-fx-background-color: white;");
        TableColumn<Livres, String> isbnCol = new TableColumn<>("ISBN");
        isbnCol.setCellValueFactory(r -> new SimpleStringProperty(r.getValue().getIsbn()));
        isbnCol.setPrefWidth(150);
        TableColumn<Livres, String> titreCol = new TableColumn<>("Titre");
        titreCol.setCellValueFactory(r -> new SimpleStringProperty(r.getValue().getTitre()));
        titreCol.setPrefWidth(250);
        TableColumn<Livres, String> auteurCol = new TableColumn<>("Auteur");
        auteurCol.setCellValueFactory(r -> new SimpleStringProperty(r.getValue().getAuteur()));
        auteurCol.setPrefWidth(200);
        TableColumn<Livres, String> categorieCol = new TableColumn<>("Catégorie");
        categorieCol.setCellValueFactory(r -> new SimpleStringProperty(r.getValue().getCategorie()));
        categorieCol.setPrefWidth(150);
        TableColumn<Livres, String> dispoCol = new TableColumn<>("Disponibilité");
        dispoCol.setCellValueFactory(r -> new SimpleStringProperty(r.getValue().getDisponible() ? "✅ Disponible" : "❌ Emprunté"));
        dispoCol.setPrefWidth(150);
        tableLivres.getColumns().addAll(isbnCol, titreCol, auteurCol, categorieCol, dispoCol);
        refreshCatalogue();
        searchBtn.setOnAction(e -> {
            String search = searchField.getText().toLowerCase();
            String categorie = categorieFilter.getValue();

            ObservableList<Livres> filtered = FXCollections.observableArrayList(
                    biblio.getAllLivres().stream()
                            .filter(l -> {
                                boolean matchSearch = search.isEmpty() ||
                                        l.getTitre().toLowerCase().contains(search) ||
                                        l.getAuteur().toLowerCase().contains(search) ||
                                        l.getIsbn().toLowerCase().contains(search);

                                boolean matchCategorie = categorie.equals("Toutes") ||
                                        l.getCategorie().equals(categorie);

                                return matchSearch && matchCategorie;
                            })
                            .toList()
            );
            tableLivres.setItems(filtered);
        });
        resetBtn.setOnAction(e -> {
            searchField.clear();
            categorieFilter.setValue("Toutes");
            refreshCatalogue();
        });
        container.getChildren().addAll(titleLabel, searchBar, tableLivres);
        VBox.setVgrow(tableLivres, Priority.ALWAYS);
        return container;
    }
    private Parent createMesEmpruntsView() {
        VBox container = new VBox(10);
        container.setPadding(new Insets(15));
        Label titleLabel = new Label("Mes Emprunts en Cours");
        titleLabel.setFont(new Font(18));
        titleLabel.setStyle("-fx-font-weight: bold;");
        tableEmprunts = new TableView<>();
        tableEmprunts.setStyle("-fx-background-color: white;");
        TableColumn<Emprunt, String> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(r -> new SimpleStringProperty(String.valueOf(r.getValue().getId())));
        idCol.setPrefWidth(80);
        TableColumn<Emprunt, String> isbnCol = new TableColumn<>("ISBN");
        isbnCol.setCellValueFactory(r -> new SimpleStringProperty(r.getValue().getIsbnlivre()));
        isbnCol.setPrefWidth(150);
        TableColumn<Emprunt, String> titreCol = new TableColumn<>("Titre du Livre");
        titreCol.setCellValueFactory(r -> {
            Livres livre = biblio.rechercherLivre(r.getValue().getIsbnlivre());
            return new SimpleStringProperty(livre != null ? livre.getTitre() : "Inconnu");
        });
        titreCol.setPrefWidth(250);
        TableColumn<Emprunt, String> dateEmpCol = new TableColumn<>("Date d'Emprunt");
        dateEmpCol.setCellValueFactory(r -> new SimpleStringProperty(r.getValue().getDateemp().toString()));
        dateEmpCol.setPrefWidth(150);
        TableColumn<Emprunt, String> statutCol = new TableColumn<>("Statut");
        statutCol.setCellValueFactory(r -> new SimpleStringProperty(
                r.getValue().getDateretour() == null ? "📖 En cours" : "✅ Retourné"
        ));
        statutCol.setPrefWidth(150);
        tableEmprunts.getColumns().addAll(idCol, isbnCol, titreCol, dateEmpCol, statutCol);
        Button refreshBtn = new Button("🔄 Rafraîchir");
        refreshBtn.setStyle("-fx-background-color: #4b3aa8; -fx-text-fill: white;");
        refreshBtn.setOnAction(e -> refreshMesEmprunts());
        HBox buttonBar = new HBox(10);
        buttonBar.setAlignment(Pos.CENTER_LEFT);
        buttonBar.getChildren().add(refreshBtn);
        refreshMesEmprunts();
        container.getChildren().addAll(titleLabel, tableEmprunts, buttonBar);
        VBox.setVgrow(tableEmprunts, Priority.ALWAYS);
        return container;
    }
    private void refreshCatalogue() {
        ObservableList<Livres> livres = FXCollections.observableArrayList(biblio.getAllLivres());
        tableLivres.setItems(livres);
    }
    private void refreshMesEmprunts() {
        ObservableList<Emprunt> emprunts = FXCollections.observableArrayList(
                biblio.getAllEmprunts().stream()
                        .filter(e -> e.getDateretour() == null) // Seulement les emprunts en cours
                        .toList()
        );
        tableEmprunts.setItems(emprunts);
    }
    private void handleLogout() {
        if (onLogoutClick != null) {
            onLogoutClick.run();
        }
    }
    public void setOnLogoutClick(Runnable r) {
        this.onLogoutClick = r;
    }
    public Parent getView() {
        return view;
    }
}