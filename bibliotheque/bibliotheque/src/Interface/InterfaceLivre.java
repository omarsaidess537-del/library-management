package Interface;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.beans.property.SimpleStringProperty;
import main.Biblio;
import pack.Livres;
public class InterfaceLivre {
    private Parent view;
    private Biblio biblio;
    private TableView<Livres> table;
    private ObservableList<Livres> data;
    public InterfaceLivre(Biblio biblio) {
        this.biblio = biblio;
        table = new TableView<>();
        TableColumn<Livres, String> isbnCol = new TableColumn<>("ISBN");
        isbnCol.setCellValueFactory(row -> new SimpleStringProperty(row.getValue().getIsbn()));
        TableColumn<Livres, String> titreCol = new TableColumn<>("Titre");
        titreCol.setCellValueFactory(row -> new SimpleStringProperty(row.getValue().getTitre()));
        TableColumn<Livres, String> auteurCol = new TableColumn<>("Auteur");
        auteurCol.setCellValueFactory(row -> new SimpleStringProperty(row.getValue().getAuteur()));
        TableColumn<Livres, String> categorieCol = new TableColumn<>("Categorie");
        categorieCol.setCellValueFactory(row -> new SimpleStringProperty(row.getValue().getCategorie()));
        TableColumn<Livres, String> dispoCol = new TableColumn<>("Disponible");
        dispoCol.setCellValueFactory(row -> new SimpleStringProperty(row.getValue().getDisponible() ? "Oui" : "Non"));
        table.getColumns().addAll(isbnCol, titreCol, auteurCol,categorieCol, dispoCol);
        refreshTable();
        TextField isbnField = new TextField();
        isbnField.setPromptText("ISBN");
        TextField titreField = new TextField();
        titreField.setPromptText("Titre");
        TextField auteurField = new TextField();
        auteurField.setPromptText("Auteur");
        TextField categorieField = new TextField();
        categorieField.setPromptText("Categorie");
        CheckBox dispoBox = new CheckBox("Disponible");
        dispoBox.setSelected(true);
        Button addBtn = new Button("Ajouter");
        Button updateBtn = new Button("Modifier");
        Button delBtn = new Button("Supprimer");
        addBtn.setOnAction(e -> {
            if (isbnField.getText().isEmpty()) {
                showAlert("ISBN requis");
                return;
            }
            Livres l = new Livres(isbnField.getText(), titreField.getText(), auteurField.getText(),categorieField.getText(), dispoBox.isSelected());
            biblio.ajouterLivre(l);
            refreshTable();
            clear(isbnField, titreField, auteurField);
        });
        updateBtn.setOnAction(e -> {
            Livres sel = table.getSelectionModel().getSelectedItem();
            if (sel == null) { showAlert("Sélectionne un livre"); return; }
            sel.setTitre(titreField.getText());
            sel.setAuteur(auteurField.getText());
            sel.setCategorie(categorieField.getText());
            sel.setDisponible(dispoBox.isSelected());
            biblio.modifierLivre(sel);
            refreshTable();
        });
        delBtn.setOnAction(e -> {
            Livres sel = table.getSelectionModel().getSelectedItem();
            if (sel == null) { showAlert("Sélectionne un livre"); return; }
            biblio.supprimerLivre(sel.getIsbn());
            refreshTable();
        });
        table.getSelectionModel().selectedItemProperty().addListener((obs, oldV, newV) -> {
            if (newV != null) {
                isbnField.setText(newV.getIsbn());
                titreField.setText(newV.getTitre());
                auteurField.setText(newV.getAuteur());
                categorieField.setText(newV.getCategorie());
                dispoBox.setSelected(newV.getDisponible());
            }
        });
        HBox form = new HBox(8, isbnField, titreField, auteurField,categorieField, dispoBox, addBtn, updateBtn, delBtn);
        form.setPadding(new Insets(10));
        VBox root = new VBox(10, table, form);
        root.setPadding(new Insets(10));
        view = root;
    }
    private void refreshTable() {
        data = FXCollections.observableArrayList(biblio.getAllLivres());
        table.setItems(data);
    }
    private void clear(TextField... tfs) {
        for (TextField t : tfs) t.clear();
    }
    private void showAlert(String msg) {
        Alert a = new Alert(Alert.AlertType.INFORMATION, msg);
        a.showAndWait();
    }
    public Parent getView() { return view; }
}