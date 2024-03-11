/*
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
    void btnLoginOnAction(ActionEvent event) throws ClassNotFoundException, SQLException, IOException {
        String name = txtUsername.getText();
        String pass = txtPassword.getText();

        // JDBC URL, username, and password of MySQL server
        String url = "jdbc:mysql://localhost:3306/librarymanagement2";
        String user = "root";
        String password = "1234";

        // SQL query to retrieve data from the database
        String query = "SELECT name, password FROM user WHERE id = U001";
        try {
            // Establish a connection to the database
            Connection connection = DriverManager.getConnection(url, user, password);

            // Create a prepared statement with the SQL query
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            // Set any parameters in the query
            preparedStatement.setString(1, "name");

            // Execute the query and get the result set
            ResultSet resultSet = preparedStatement.executeQuery();

            // Retrieve values from the result set
            while (resultSet.next()) {
                String value1FromDB = resultSet.getString("name");
                String value2FromDB = resultSet.getString("pass");

                // Get values from text fields (assuming textField1 and textField2 are your text fields)
                String value1FromTextField = txtUsername.getText();
                String value2FromTextField = txtPassword.getText();

                // Compare the values
                if (value1FromDB.equals(value1FromTextField) && value2FromDB.equals(value2FromTextField)) {
                    System.out.println("Values match!");
                } else {
                    System.out.println("Values don't match!");
                }
            }

            // Close the connections
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
*/
