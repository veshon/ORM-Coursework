package org.example.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.dao.custom.impl.BookDAOImpl;
import org.example.dao.custom.impl.UserDAOImpl;
import org.example.dto.BooksManagementDTO;
import org.example.tm.BooksTM;

import java.sql.SQLException;
import java.util.List;
import java.util.regex.Pattern;

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
    private TableView<BooksTM> tble;

    @FXML
    private TextField txtAuthor;

    @FXML
    private TextField txtAvailability;

    @FXML
    private TextField txtGenre;

    @FXML
    private TextField txtTitle;

    @FXML
    private TableColumn<?, ?> colUserId;

    @FXML
    private TextField txtUserId;

    @FXML
    private TextField txtId;
    ObservableList<BooksTM> obList = FXCollections.observableArrayList();
    private BookDAOImpl bookDAOImpl = new BookDAOImpl();
    public void initialize() {
        loadAllCustomer();
        setCellValueFactory();
    }

    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colAuthor.setCellValueFactory(new PropertyValueFactory<>("author"));
        colGenre.setCellValueFactory(new PropertyValueFactory<>("genre"));
        colAvailability.setCellValueFactory(new PropertyValueFactory<>("availability"));
        colUserId.setCellValueFactory(new PropertyValueFactory<>("user_id"));
    }
    private void loadAllCustomer() {
        var model = new BookDAOImpl();

        try {
            List<BooksManagementDTO> dtoList = model.getAllBook();

            for (BooksManagementDTO dto : dtoList) {
                obList.add(
                        new BooksTM(
                                dto.getId(),
                                dto.getTitle(),
                                dto.getAuthor(),
                                dto.getGenre(),
                                dto.getAvailability_status(),
                                dto.getUser_id()
                        )
                );
            }

            tble.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();
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
        String user_id = txtUserId.getText();

        boolean isValidated = validateCustomer();
        if (isValidated) {
            new Alert(Alert.AlertType.INFORMATION, "Customer validated");
            var dto = new BooksManagementDTO(id, title, author, genre, availability, user_id);

            try {
                boolean isSaved = bookDAOImpl.saveBook(dto);

                if (isSaved) {

                    obList.clear();
                    loadAllCustomer();

                    new Alert(Alert.AlertType.CONFIRMATION, "customer saved!").show();
                    clearFields();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        }
    }
    private boolean validateCustomer() {

        String id = txtId.getText();
        boolean idValidate = Pattern.matches("[B0-9]{4,}", id);

        if (!idValidate) {
            new Alert(Alert.AlertType.ERROR, "Invalid id. Please try again!!").show();
            return false;
        }

        String name = txtTitle.getText();
        boolean nameValidated = Pattern.matches("[A-Za-z ]+", name);

        if (!nameValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid Title").show();
            return false;
        }

        String address = txtAuthor.getText();
        boolean addressValidated = Pattern.matches("[A-Za-z]{3,}", address);

        if (!addressValidated){
            new Alert(Alert.AlertType.ERROR,"Invalid address").show();
            return false;
        }

        String tel = txtGenre.getText();
        boolean telValidated = Pattern.matches("[A-Za-z]{3,}", tel);

        if (!telValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid Tel No").show();
            return false;
        }
        String avl = txtAvailability.getText();
        boolean avlValidated = Pattern.matches("[A-Za-z]{3,}", avl);

        if (!avlValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid Status").show();
            return false;
        }
        String uid = txtUserId.getText();
        boolean uidValidated = Pattern.matches("[U0-9]{4,}", uid);
        if (!uidValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid User ID ").show();
            return false;
        }

        return true;
    }
    private void clearFields() {
        txtId.setText("");
        txtAuthor.setText("");
        txtAvailability.setText("");
        txtGenre.setText("");
        txtTitle.setText("");
    }
    @FXML
    void btnUpdateOnAction(ActionEvent event) {

    }

}
