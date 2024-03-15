package org.example.controller;

import javafx.animation.Animation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javax.sql.*;
import java.sql.*;
import org.example.dao.custom.impl.LoginDAOImpl;
import org.example.db.DbConnection;
import org.example.dto.UserRegistrationDTO;

import java.io.IOException;
import java.sql.*;

public class LoginFormController {

    @FXML
    private TextField txtPassword;

    @FXML
    private TextField txtUsername;
    private static Stage stage;
    private static Scene scene;
    private static Parent parent;

    @FXML
    void btnLoginOnAction(ActionEvent actionEvent) throws ClassNotFoundException, SQLException, IOException {

        String name = txtUsername.getText();
        String pass = txtPassword.getText();

        String url = "jdbc:mysql://localhost:3306/librarymanagement2";
        String user = "root";
        String password = "1234";

        String query = "SELECT name, password FROM user WHERE id = ?";
        try {
            Connection connection = DriverManager.getConnection(url, user, password);
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, "id");
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String username = resultSet.getString("name");
                String password1 = resultSet.getString("password");

                String user_name = txtUsername.getText();
                String password_1 = txtPassword.getText();

                if (username.equals(user_name) && password1.equals(password_1)) {
                    System.out.println("Successful !!");
                    parent = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
                    stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                    scene = new Scene(parent);
                    stage.setScene(scene);
                    stage.centerOnScreen();
                    stage.show();
                } else {
                    System.out.println("Wrong Credentials !!");
                }
            }

            resultSet.close();
            preparedStatement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btnRegisterOnAction(ActionEvent actionEvent) throws IOException {
        parent = FXMLLoader.load(getClass().getResource("/view/userRegistrationForm.fxml"));
        stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(parent);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

}
