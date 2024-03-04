package org.example.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class BooksManageFormController {

    @FXML
    private TableColumn<?, ?> colAuthor;

    @FXML
    private TableColumn<?, ?> colAvailability;

    @FXML
    private TableColumn<?, ?> colGenre;

    @FXML
    private TableColumn<?, ?> colTitle;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableView<?> tble;

    @FXML
    private TextField txtAuthor;

    @FXML
    private TextField txtAvailability;

    @FXML
    private TextField txtGenre;

    @FXML
    private TextField txtTitle;

    @FXML
    private TextField txtId;

    public void initialize() {
        tble.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("id"));
        tble.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("title"));
        tble.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("author"));
        tble.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("genre"));
        tble.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("availability"));

        initUI();

        tble.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            btnDelete.setDisable(newValue == null);
            btnSave.setText(newValue != null ? "Update" : "Save");
            btnSave.setDisable(newValue == null);

            if (newValue != null) {
                txtId.setText(newValue.getId());
                txtTitle.setText(newValue.getTitle());
                txtAuthor.setText(newValue.getAuthor());
                txtGenre.setText(newValue.getGenre());
                txtAvailability.setText(newValue.getAvailability());

                txtId.setDisable(false);
                txtTitle.setDisable(false);
                txtAuthor.setDisable(false);
                txtGenre.setDisable(false);
                txtAvailability.setDisable(false);
            }
        });

        txtAuthor.setOnAction(event -> btnSave.fire());
        loadAllCustomers();
    }

    private void initUI() {
        txtId.clear();
        txtTitle.clear();
        txtAuthor.clear();
        txtGenre.clear();
        txtAvailability.clear();
        txtId.setDisable(true);
        txtTitle.setDisable(true);
        txtAuthor.setDisable(true);
        txtGenre.setDisable(true);
        txtAvailability.setDisable(true);
        txtId.setEditable(false);
        btnSave.setDisable(true);
        btnDelete.setDisable(true);
    }
    @FXML
    void btnAddNewOnAction(ActionEvent event) {
        txtId.setDisable(false);
        txtTitle.setDisable(false);
        txtAuthor.setDisable(false);
        txtGenre.setDisable(false);
        txtAvailability.setDisable(false);
        txtId.clear();
        txtId.setText(generateNewId());
        txtTitle.clear();
        txtGenre.clear();
        txtTitle.requestFocus();
        btnSave.setDisable(false);
        btnSave.setText("Save");
        tble.getSelectionModel().clearSelection();
    }

    private String generateNewId() {
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        txtId.setText("");
        txtAuthor.setText("");
        txtAvailability.setText("");
        txtGenre.setText("");
        txtTitle.setText("");
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {

    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String id = txtId.getText();
        String title = txtTitle.getText();
        String author = txtAuthor.getText();
        String genre = txtGenre.getText();
        String availability = txtAvailability.getText();

        if (!title.matches("[A-Za-z ]+")) {
            new Alert(Alert.AlertType.ERROR, "Invalid Title !!").show();
            txtTitle.requestFocus();
            return;
        } else if (!author.matches("[A-Za-z]{3,}")) {
            new Alert(Alert.AlertType.ERROR, "Author should contain at least 3 letters !!").show();
            txtAuthor.requestFocus();
            return;
        } else if (!genre.matches("[A-Za-z]{3,}")) {
            new Alert(Alert.AlertType.ERROR, "Genre should contain at least 3 letters !!").show();
            txtGenre.requestFocus();
            return;
        } else if (!availability.matches("[A-Za-z]{3,}")) {
            new Alert(Alert.AlertType.ERROR, "Availability should contain at least 3 letters !!").show();
            txtAvailability.requestFocus();
            return;
        } else if (!id.matches("[B0-9]{4,}")) {
            new Alert(Alert.AlertType.ERROR, "ID should contain at least 3 characters !!").show();
            txtAvailability.requestFocus();
            return;
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {

    }

}
