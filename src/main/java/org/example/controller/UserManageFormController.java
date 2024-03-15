package org.example.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.example.bo.custom.UserBO;
import org.example.bo.custom.impl.UserBOImpl;
import org.example.dao.custom.UserDAO;
import org.example.dao.custom.impl.UserDAOImpl;
import org.example.db.DbConnection;
import org.example.dto.BooksManagementDTO;
import org.example.dto.UserRegistrationDTO;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
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

    private static Stage stage;
    private static Scene scene;
    private static Parent parent;

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
                UserBO userBO = new UserBOImpl();
                userBO.deleteUser(id);

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
    void btnBackOnAction(ActionEvent actionEvent) throws IOException {
        parent = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
        stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(parent);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
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
        String id = txtId.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();
        String email = txtEmail.getText();
        String password = txtPassword.getText();

        boolean isValidated = validateUpdateUser();
        if (isValidated) {
            new Alert(Alert.AlertType.INFORMATION,"Validated");

            try {

                UserBO userBO = new UserBOImpl();
                userBO.updateUser(new UserRegistrationDTO(id, name, address, email, password));

                if (isValidated) {

                    new Alert(Alert.AlertType.CONFIRMATION, "User updated!").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        }
    }

    private boolean validateUpdateUser() {
        String id = txtId.getText();
        boolean isValidate = Pattern.matches("[U0-9]{4,}", id);
        if (!isValidate) {
            new Alert(Alert.AlertType.ERROR,"Invalid User ID. Please try again!!").show();
            return false;
        }
        return true;
    }

    private void clearFields() {
        txtId.setText("");
        txtName.setText("");
        txtAddress.setText("");
        txtEmail.setText("");
        txtPassword.setText("");
    }

    @FXML
    void btnReportOnAction(ActionEvent event) throws JRException, SQLException {
        InputStream inputStream = getClass().getResourceAsStream("/Reports/UserReport.jrxml");
        JasperDesign load = JRXmlLoader.load(inputStream);

        JasperReport jasperReport = JasperCompileManager.compileReport(load);
        JasperPrint jasperPrint = JasperFillManager.fillReport(
                jasperReport,
                null,
                DbConnection.getInstance().getConnection()
        );
        JasperViewer.viewReport(jasperPrint, false);
    }
}
