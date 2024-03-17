package org.example.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import org.example.dao.custom.UserDAO;
import org.example.dao.custom.impl.UserDAOImpl;
import org.example.dto.UserRegistrationDTO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

public class UserRegistrationFormController {

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

    private UserDAOImpl userImpl = new UserDAOImpl();

    public void initialize(){
        generateNextUserId();
    }

    private void generateNextUserId() {
        try {
            String userId = userImpl.generateNexUserId();
            txtId.setText(userId);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }


    @FXML
    void btnClearOnAction(ActionEvent event) {
        txtId.setText("");
        txtName.setText("");
        txtAddress.setText("");
        txtEmail.setText("");
        txtPassword.setText("");
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String id = txtId.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();
        String email = txtEmail.getText();
        String password = txtPassword.getText();

        boolean isValidated = isUserValidate();
        if (isValidated) {
            new Alert(Alert.AlertType.INFORMATION,"Validated");
            var dto = new UserRegistrationDTO(id, name, address, email,password);

            try {
                UserDAO userDAO = new UserDAOImpl();
                UserRegistrationDTO userRegistrationDTO =  new UserRegistrationDTO(id, name, address, email,password);

                boolean isSaved = userDAO.save(userRegistrationDTO);

                if (isSaved){
                    new Alert(Alert.AlertType.INFORMATION,"User Registered Successfully !!").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }

            boolean isSelValidated =  isUserValidate();
            if (isSelValidated) {
                new Alert(Alert.AlertType.INFORMATION,"Validated");
            }
        }

    }
    private boolean isUserValidate() {
        String id = txtId.getText();
        boolean idValidate = Pattern.matches("[U0-9]{4,}", id);
        if (!idValidate) {
            new Alert(Alert.AlertType.ERROR,"Invalid ID Please try again!!").show();
            return false;
        }
        String name = txtName.getText();
        boolean nameValidated = Pattern.matches("[A-Za-z]{3,}", name);
        if (!nameValidated) {
            new Alert(Alert.AlertType.ERROR,"Invalid Name Please try again!!").show();
            return false;
        }
        String address = txtAddress.getText();
        boolean addressValidated = Pattern.matches("[A-Za-z]{3,}",address);
        if (!addressValidated){
            new Alert(Alert.AlertType.ERROR,"Invalid address!!").show();
            return false;
        }
        String email = txtEmail.getText();
        boolean emailValidate = Pattern.matches("^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$", email);
        if (!emailValidate){
            new Alert(Alert.AlertType.ERROR,"Invalid Email!!").show();
            return false;
        }
        String password = txtPassword.getText();
        boolean passwordValidate = Pattern.matches("[A-Za-z0-9]{3,}", password);
        if (!passwordValidate){
            new Alert(Alert.AlertType.ERROR,"Invalid Password!!").show();
            return false;
        }
        return true;
    }

    public void btnHomeOnAction(ActionEvent actionEvent) throws IOException {
        parent = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
        stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(parent);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    @FXML
    void btnLoginOnAction(ActionEvent actionEvent) throws IOException {
        parent = FXMLLoader.load(getClass().getResource("/view/loginForm.fxml"));
        stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(parent);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

}
