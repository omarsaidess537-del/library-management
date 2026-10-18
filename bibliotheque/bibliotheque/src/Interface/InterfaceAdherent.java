package Interface;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.beans.property.SimpleStringProperty;
import main.Biblio;
import pack.Adherent;

public class InterfaceAdherent{
    private Parent view;
    private Biblio biblio;
    private TableView<Adherent> table;
    private ObservableList<Adherent> data;
    public InterfaceAdherent(Biblio biblio) {
        this.biblio = biblio;
        table = new TableView<>();
        TableColumn<Adherent, String> telCol = new TableColumn<>("Téléphone");
        telCol.setCellValueFactory(r -> new SimpleStringProperty(String.valueOf(r.getValue().getNumtel())));
        TableColumn<Adherent, String> nomCol = new TableColumn<>("Nom");
        nomCol.setCellValueFactory(r -> new SimpleStringProperty(r.getValue().getNom()));
        TableColumn<Adherent, String> prenomCol = new TableColumn<>("Prénom");
        prenomCol.setCellValueFactory(r -> new SimpleStringProperty(r.getValue().getPrenom()));
        table.getColumns().addAll(telCol, nomCol, prenomCol);
        refreshTable();
        TextField telField = new TextField();
        telField.setPromptText("Téléphone");
        TextField nomField = new TextField();
        nomField.setPromptText("Nom");
        TextField prenomField = new TextField();
        prenomField.setPromptText("Prénom");
        Button addBtn = new Button("Ajouter");
        Button updateBtn = new Button("Modifier");
        Button delBtn = new Button("Supprimer");

        addBtn.setOnAction(e -> {
            try {
                int tel = Integer.parseInt(telField.getText());
                Adherent a = new Adherent(tel, nomField.getText(), prenomField.getText(), new java.sql.Date(System.currentTimeMillis()), "Standard");
                biblio.ajouterAdherent(a);
                refreshTable();
                clear(telField, nomField, prenomField);
            } catch (NumberFormatException ex) {
                showAlert("Téléphone invalide");
            }
        });
        updateBtn.setOnAction(e -> {
            Adherent sel = table.getSelectionModel().getSelectedItem();
            if (sel == null) { showAlert("Sélectionne un adhérent"); return; }
            sel.setNom(nomField.getText());
            sel.setPrenom(prenomField.getText());
            biblio.modifierAdherent(sel);
            refreshTable();
        });
        delBtn.setOnAction(e -> {
            Adherent sel = table.getSelectionModel().getSelectedItem();
            if (sel == null) { showAlert("Sélectionne un adhérent"); return; }
            biblio.supprimerAdherent(sel.getNumtel());
            refreshTable();
        });
        table.getSelectionModel().selectedItemProperty().addListener((obs, o, n) -> {
            if (n != null) {
                telField.setText(String.valueOf(n.getNumtel()));
                nomField.setText(n.getNom());
                prenomField.setText(n.getPrenom());
            }
        });
        HBox form = new HBox(8, telField, nomField, prenomField, addBtn, updateBtn, delBtn);
        form.setPadding(new Insets(10));
        VBox root = new VBox(10, table, form);
        root.setPadding(new Insets(10));
        view = root;
    }
    private void refreshTable() {
        data = FXCollections.observableArrayList(biblio.getAllAdherents());
        table.setItems(data);
    }
    private void clear(TextField... tfs) { for (TextField t : tfs) t.clear(); }
    private void showAlert(String msg) { new Alert(Alert.AlertType.INFORMATION, msg).showAndWait(); }
    public Parent getView() { return view; }
}
