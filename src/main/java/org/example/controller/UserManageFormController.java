package org.example.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import org.example.dao.custom.UserDAO;
import org.example.dao.custom.impl.UserDAOImpl;
import org.example.dto.BooksManagementDTO;
import org.example.dto.UserRegistrationDTO;

import java.sql.SQLException;
import java.util.regex.Pattern;

public class UserManageFormController {
    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtPassword;

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();
    }
    UserDAO userDAO = new UserDAOImpl();

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String id = txtId.getText();

        boolean isvalidated = validateDeleteUser();
        if (isvalidated) {

            try {
                userDAO.delete(id);

                if(isvalidated) {
                    new Alert(Alert.AlertType.CONFIRMATION, "User deleted!").show();
                } else {
                    new Alert(Alert.AlertType.ERROR, "User is not deleted!").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        }

    }
    private boolean validateDeleteUser() {

        String id = txtId.getText();
        boolean idValidate = Pattern.matches("[U0-9]{4,}", id);
        if (!idValidate){
            new Alert(Alert.AlertType.ERROR,"Invalid User Id").show();
            return false;
        }

        return true;
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {

    }

    @FXML
    void btnSearchOnAction(ActionEvent event) {
        String id = txtId.getText();

        try {
            UserRegistrationDTO userRegistrationDTO = userDAO.search(id);
            if (userRegistrationDTO != null) {
                txtId.setText(userRegistrationDTO.getId());
                txtName.setText(userRegistrationDTO.getName());
                txtAddress.setText(userRegistrationDTO.getAddress());
                txtEmail.setText(userRegistrationDTO.getEmail());
                txtPassword.setText(userRegistrationDTO.getPassword());
            } else {
                new Alert(Alert.AlertType.INFORMATION, "User not found !!").show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {

    }
    private void clearFields() {
        txtId.setText("");
        txtName.setText("");
        txtAddress.setText("");
        txtEmail.setText("");
        txtPassword.setText("");
    }
}
