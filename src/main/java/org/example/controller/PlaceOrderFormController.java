package org.example.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.example.dao.custom.BookDAO;
import org.example.dao.custom.OrderDAO;
import org.example.dao.custom.PlaceOrderDAO;
import org.example.dao.custom.UserDAO;
import org.example.dao.custom.impl.BookDAOImpl;
import org.example.dao.custom.impl.OrderDAOImpl;
import org.example.dao.custom.impl.PlaceOrderDAOImpl;
import org.example.dao.custom.impl.UserDAOImpl;
import org.example.dto.BooksManagementDTO;
import org.example.dto.PlaceOrderDTO;
import org.example.dto.UserRegistrationDTO;
import org.example.tm.CartTm;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

public class PlaceOrderFormController {

    @FXML
    private JFXButton btnAddToCart;
    @FXML
    private JFXComboBox<String> cmbBookId;

    @FXML
    private JFXComboBox<String> cmbUserId;

    @FXML
    private TableColumn<?, ?> colAction;

    @FXML
    private TableColumn<?, ?> colBookId;

    @FXML
    private TableColumn<?, ?> colBookName;

    @FXML
    private TableColumn<?, ?> colQty;

    @FXML
    private TableColumn<?, ?> colTotal;

    @FXML
    private TableColumn<?, ?> colUnitPrice;

    @FXML
    private TextField txtQty;

    @FXML
    private Label lblBookName;

    @FXML
    private Label lblUnitPrice;

    @FXML
    private Label lblOrderDate;

    @FXML
    private Label lblOrderId;

    @FXML
    private Label lblUserName;

    @FXML
    private Label lblNetTotal;

    @FXML
    private TableView<CartTm> tblOrderCart;

    private static Stage stage;
    private static Scene scene;
    private static Parent parent;

    OrderDAO orderDAO = new OrderDAOImpl();
    UserDAO userDAO = new UserDAOImpl();
    BookDAO bookDAO = new BookDAOImpl();
    PlaceOrderDAO placeOrderDAO = new PlaceOrderDAOImpl();
    private ObservableList<CartTm> obList = FXCollections.observableArrayList();


    public void initialize() {
        setCellValueFactory();
        generateNextOrderId();
        setDate();
        loadUserIds();
        loadBookIds();
    }
    private void setCellValueFactory() {
        colBookId.setCellValueFactory(new PropertyValueFactory<>("code"));
        colBookName.setCellValueFactory(new PropertyValueFactory<>("description"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("tot"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("btn"));
    }
    private void setDate() {
        lblOrderDate.setText(String.valueOf(LocalDate.now()));
    }

    private void loadBookIds() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<BooksManagementDTO> booksManagementDTOS = bookDAO.getAll();

            for (BooksManagementDTO dto : booksManagementDTOS) {
                obList.add(dto.getId());
            }
            cmbBookId.setItems(obList);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadUserIds() {
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<UserRegistrationDTO> idList = userDAO.getAll();

            for (UserRegistrationDTO dto : idList) {
                obList.add(dto.getId());
            }

            cmbUserId.setItems(obList);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }



    private void generateNextOrderId() {
        try {
            String orderId = orderDAO.generateNextOrderId();
            lblOrderId.setText(orderId);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnAddToCartOnAction(ActionEvent event) {
        String code = cmbBookId.getValue();
        String description = lblBookName.getText();
        int qty = Integer.parseInt(txtQty.getText());
        double unitPrice = Double.parseDouble(lblUnitPrice.getText());
        double tot = unitPrice * qty;
        Button btn = new Button("Remove");

        setRemoveBtnAction(btn);
        btn.setCursor(Cursor.HAND);


        if (!obList.isEmpty()) {
            for (int i = 0; i < tblOrderCart.getItems().size(); i++) {
                if (colBookId.getCellData(i).equals(code)) {
                    int col_qty = (int) colQty.getCellData(i);
                    qty += col_qty;
                    tot = unitPrice * qty;

                    obList.get(i).setQty(qty);
                    obList.get(i).setTot(tot);

                    calculateTotal();
                    tblOrderCart.refresh();
                    return;
                }
            }
        }
        var cartTm = new CartTm(code, description, qty, unitPrice, tot, btn);

        obList.add(cartTm);

        tblOrderCart.setItems(obList);
        calculateTotal();
        txtQty.clear();
    }

    private void setRemoveBtnAction(Button btn) {
        btn.setOnAction((e) -> {
            ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

            Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure You want to remove?", yes, no).showAndWait();

            if (type.orElse(no) == yes) {
                int focusedIndex = tblOrderCart.getSelectionModel().getSelectedIndex();

                obList.remove(focusedIndex);
                tblOrderCart.refresh();
                calculateTotal();
            }
        });
    }

    private void calculateTotal() {
        double total = 0;
        for (int i = 0; i < tblOrderCart.getItems().size(); i++) {
            total += (double) colTotal.getCellData(i);
        }
        lblNetTotal.setText(String.valueOf(total));
    }

    @FXML
    void btnPlaceOrderOnAction(ActionEvent event) throws SQLException {
        String orderId = lblOrderId.getText();
        LocalDate date = LocalDate.parse(lblOrderDate.getText());
        String customerId = cmbUserId.getValue();

        List<CartTm> cartTmList = new ArrayList<>();
        for (int i = 0; i < tblOrderCart.getItems().size(); i++) {
            CartTm cartTm = obList.get(i);

            cartTmList.add(cartTm);
        }

        System.out.println("Place order form controller: " + cartTmList);
        var placeOrderDto = new PlaceOrderDTO(orderId, date, customerId, cartTmList);
        boolean isSuccess = placeOrderDAO.placeOrder(placeOrderDto);
        if (isSuccess) {
            new Alert(Alert.AlertType.CONFIRMATION, "Order Success!").show();
        }
    }

    @FXML
    void cmbBookOnAction(ActionEvent event) {
        String code = cmbBookId.getValue();

        txtQty.requestFocus();
        try {
            BooksManagementDTO dto = bookDAO.search(code);
            lblBookName.setText(dto.getTitle());
            lblUnitPrice.setText(String.valueOf(dto.getPrice()));
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void cmbUserOnAction(ActionEvent event) {
        String id = cmbUserId.getValue();

        try {
            UserRegistrationDTO userRegistrationDTO = userDAO.search(id);
            lblUserName.setText(userRegistrationDTO.getName());

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    void btnHomeOnAction(ActionEvent actionEvent) throws IOException {
        parent = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
        stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(parent);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    @FXML
    void btnAddOnAction(ActionEvent actionEvent) throws IOException {
        parent = FXMLLoader.load(getClass().getResource("/view/userRegistrationForm.fxml"));
        stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(parent);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    public void txtQtyOnAction(ActionEvent actionEvent) {
        btnAddToCartOnAction(actionEvent);
    }
}
