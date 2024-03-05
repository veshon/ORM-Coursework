package org.example.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.example.dao.custom.BookDAO;
import org.example.dao.custom.impl.BookDAOImpl;
import org.example.dao.custom.impl.UserDAOImpl;
import org.example.dto.BooksManagementDTO;
import org.example.tm.BooksTM;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.regex.Pattern;

public class BooksManageFormController {
    private static Stage stage;
    private static Scene scene;
    private static Parent parent;

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
        generateNextBookId();
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
        BookDAO bookDAO = new BookDAOImpl();
        tble.getItems().clear();

        try {
            List<BooksManagementDTO> dtoList = model.getAll();

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
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String id = txtId.getText();

        boolean isValidated = validateDeleteBook();
        if (isValidated) {
            try {
                boolean isDeleted = bookDAOImpl.delete(id);
                if (isDeleted) {
                    obList.clear();
                    loadAllCustomer();

                    new Alert(Alert.AlertType.CONFIRMATION, "Book deleted").show();
                } else {
                    new Alert(Alert.AlertType.INFORMATION, "Book is not deleted !!").show();
                }
                clearFields();
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        }
    }

    private boolean validateDeleteBook() {
        String id = txtId.getText();
        boolean deleteCustomer = Pattern.matches("[B0-9]{4,}", id);
        if (!deleteCustomer){
            new Alert(Alert.AlertType.ERROR,"Invalid Book Id. Please Try again").show();
            return false;
        }
        return true;
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
            new Alert(Alert.AlertType.INFORMATION, "Book validated");
            var dto = new BooksManagementDTO(id, title, author, genre, availability, user_id);

            try {
                boolean isSaved = bookDAOImpl.save(dto);

                if (isSaved) {

                    obList.clear();
                    loadAllCustomer();

                    new Alert(Alert.AlertType.CONFIRMATION, "Book saved!").show();
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
            new Alert(Alert.AlertType.ERROR, "Invalid Book Id. Please try again!!").show();
            return false;
        }

        String name = txtTitle.getText();
        boolean nameValidated = Pattern.matches("[A-Za-z ]+", name);

        if (!nameValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid Title !!").show();
            return false;
        }

        String address = txtAuthor.getText();
        boolean addressValidated = Pattern.matches("[A-Za-z]{3,}", address);

        if (!addressValidated){
            new Alert(Alert.AlertType.ERROR,"Invalid author !!").show();
            return false;
        }

        String tel = txtGenre.getText();
        boolean telValidated = Pattern.matches("[A-Za-z]{3,}", tel);

        if (!telValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid Genre !!").show();
            return false;
        }
        String avl = txtAvailability.getText();
        boolean avlValidated = Pattern.matches("[A-Za-z]{3,}", avl);

        if (!avlValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid Status !!").show();
            return false;
        }
        String uid = txtUserId.getText();
        boolean uidValidated = Pattern.matches("[U0-9]{4,}", uid);
        if (!uidValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid User ID !!").show();
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
        txtUserId.setText("");
    }
    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String id = txtId.getText();
        String title = txtTitle.getText();
        String author = txtAuthor.getText();
        String genre = txtGenre.getText();
        String availability = txtAvailability.getText();
        String user_id = txtUserId.getText();

        boolean isValidated = validateUpdateBook();
        if (isValidated){
            new Alert(Alert.AlertType.INFORMATION,"Validated");

            var dto = new BooksManagementDTO(id, title, author, genre, availability, user_id);

            try {
                boolean isUpdated = bookDAOImpl.update(dto);
                if(isUpdated) {
                    obList.clear();
                    loadAllCustomer();

                    new Alert(Alert.AlertType.CONFIRMATION, "Book updated!").show();
                    clearFields();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        }
    }
    private boolean validateUpdateBook() {
        String id = txtId.getText();
        boolean isValidate = Pattern.matches("[B0-9]{4,}", id);
        if (!isValidate) {
            new Alert(Alert.AlertType.ERROR,"Invalid Book ID. Please try again!!").show();
            return false;
        }
        return true;
    }

    @FXML
    void txtSearchOnAction(ActionEvent event) {
        String id = txtId.getText();

        try {
            BooksManagementDTO booksManagementDTO = bookDAOImpl.search(id);
            if (booksManagementDTO != null) {
                txtId.setText(booksManagementDTO.getId());
                txtTitle.setText(booksManagementDTO.getTitle());
                txtAuthor.setText(booksManagementDTO.getAuthor());
                txtGenre.setText(booksManagementDTO.getGenre());
                txtAvailability.setText(booksManagementDTO.getAvailability_status());
                txtUserId.setText(booksManagementDTO.getUser_id());
            } else {
                new Alert(Alert.AlertType.INFORMATION, "Book not found !!").show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void btnHomeOnAction(ActionEvent actionEvent) throws IOException {
        parent = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
        stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(parent);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }
    private void generateNextBookId() {
        try {
            String userId = bookDAOImpl.generateNexBookId();
            txtId.setText(userId);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }
}
