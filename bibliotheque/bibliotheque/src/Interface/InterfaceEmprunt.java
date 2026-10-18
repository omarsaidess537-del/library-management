package Interface;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.beans.property.SimpleStringProperty;
import main.Biblio;
import pack.Emprunt;
import pack.Livres;
import pack.Adherent;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public class InterfaceEmprunt {
    private Parent view;
    private Biblio biblio;
    private TableView<Emprunt> table;
    private ObservableList<Emprunt> data;

    public InterfaceEmprunt(Biblio biblio) {
        this.biblio = biblio;

        table = new TableView<>();

        TableColumn<Emprunt, String> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(r -> new SimpleStringProperty(String.valueOf(r.getValue().getId())));
        TableColumn<Emprunt, String> numtelCol = new TableColumn<>("NumTel");
        numtelCol.setCellValueFactory(r -> new SimpleStringProperty(String.valueOf(r.getValue().getNumtelad())));
        TableColumn<Emprunt, String> isbnCol = new TableColumn<>("ISBN livre");
        isbnCol.setCellValueFactory(r -> new SimpleStringProperty(r.getValue().getIsbnlivre()));
        TableColumn<Emprunt, String> dateEmpCol = new TableColumn<>("Date emprunt");
        dateEmpCol.setCellValueFactory(r -> new SimpleStringProperty(String.valueOf(r.getValue().getDateemp())));
        TableColumn<Emprunt, String> dateRetourCol = new TableColumn<>("Date retour");
        dateRetourCol.setCellValueFactory(r -> new SimpleStringProperty(
                r.getValue().getDateretour() != null ? r.getValue().getDateretour().toString() : "Non retourné"));
        table.getColumns().addAll(idCol, numtelCol, isbnCol, dateEmpCol, dateRetourCol);
        refreshTable();

        ComboBox<Adherent> adherentCombo = new ComboBox<>();
        ComboBox<Livres> livreCombo = new ComboBox<>();
        refreshCombos(adherentCombo, livreCombo);

        Button emprunterBtn = new Button("Enregistrer emprunt");
        Button retourBtn = new Button("Enregistrer retour");
        Button refreshBtn = new Button("Rafraîchir");

        emprunterBtn.setOnAction(e -> {
            Adherent a = adherentCombo.getSelectionModel().getSelectedItem();
            Livres l = livreCombo.getSelectionModel().getSelectedItem();
            if (a == null || l == null) { showAlert("Sélectionne adhérent et livre"); return; }
            if (!l.getDisponible()) { showAlert("Livre non disponible"); return; }
            int newId = generateNewId();
            Emprunt emp = new Emprunt(newId,l.getIsbn(), a.getNumtel(), Date.valueOf(LocalDate.now()), null);
            biblio.ajouterEmprunt(emp);
            refreshTable();
            refreshCombos(adherentCombo, livreCombo);
        });

        retourBtn.setOnAction(e -> {
            Emprunt sel = table.getSelectionModel().getSelectedItem();
            if (sel == null) { showAlert("Sélectionne un emprunt"); return; }
            if (sel.getDateretour() != null) { showAlert("Déjà retourné"); return; }
            biblio.enregistrerRetour(sel.getId(), Date.valueOf(LocalDate.now()));
            refreshTable();
            refreshCombos(adherentCombo, livreCombo);
        });

        refreshBtn.setOnAction(e -> {
            refreshTable();
            refreshCombos(adherentCombo, livreCombo);
        });

        HBox controls = new HBox(8, new Label("Adhérent:"), adherentCombo, new Label("Livre:"), livreCombo, emprunterBtn, retourBtn, refreshBtn);
        controls.setPadding(new Insets(10));
        VBox root = new VBox(10, table, controls);
        root.setPadding(new Insets(10));
        view = root;
    }
    private int generateNewId() {
        List<Emprunt> emprunts = biblio.getAllEmprunts();
        if (emprunts.isEmpty()) {
            return 1;
        }
        return emprunts.stream()
                .mapToInt(Emprunt::getId)
                .max()
                .orElse(0) + 1;
    }
    private void refreshTable() {
        List<Emprunt> list = biblio.getAllEmprunts();
        data = FXCollections.observableArrayList(list);
        table.setItems(data);
    }
    private void refreshCombos(ComboBox<Adherent> c1, ComboBox<Livres> c2) {
        ObservableList<Adherent> adh = FXCollections.observableArrayList(biblio.getAllAdherents());
        ObservableList<Livres> liv = FXCollections.observableArrayList(biblio.getAllLivres().stream().filter(Livres::getDisponible).toList());
        c1.setItems(adh);
        c2.setItems(liv);
        c1.setCellFactory(listView -> new ListCell<>() {
            @Override protected void updateItem(Adherent a, boolean empty) {
                super.updateItem(a, empty);
                setText(empty || a == null ? null : a.getNom() + " (" + a.getNumtel() + ")");
            }
        });
        c1.setButtonCell(new ListCell<>() {
            @Override protected void updateItem(Adherent a, boolean empty) {
                super.updateItem(a, empty);
                setText(empty || a == null ? null : a.getNom() + " (" + a.getNumtel() + ")");
            }
        });
        c2.setCellFactory(listView -> new ListCell<>() {
            @Override protected void updateItem(Livres l, boolean empty) {
                super.updateItem(l, empty);
                setText(empty || l == null ? null : l.getTitre() + " [" + l.getIsbn() + "]");
            }
        });
        c2.setButtonCell(new ListCell<>() {
            @Override protected void updateItem(Livres l, boolean empty) {
                super.updateItem(l, empty);
                setText(empty || l == null ? null : l.getTitre() + " [" + l.getIsbn() + "]");
            }
        });
    }
    private void showAlert(String msg) { new Alert(Alert.AlertType.INFORMATION, msg).showAndWait(); }
    public Parent getView() { return view; }
}