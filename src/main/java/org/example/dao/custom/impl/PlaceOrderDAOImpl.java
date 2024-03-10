package org.example.dao.custom.impl;

import org.example.dao.custom.BookDAO;
import org.example.dao.custom.OrderDAO;
import org.example.dao.custom.OrderDetailDAO;
import org.example.dao.custom.PlaceOrderDAO;
import org.example.db.DbConnection;
import org.example.dto.BooksManagementDTO;
import org.example.dto.PlaceOrderDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;

public class PlaceOrderDAOImpl implements PlaceOrderDAO {
    private OrderDAOImpl orderDAO = new OrderDAOImpl();
    private BookDAO bookDAO = new BookDAOImpl();
    private OrderDetailDAOImpl orderDetailDAO = new OrderDetailDAOImpl();

    @Override
    public boolean placeOrder(PlaceOrderDTO placeOrderDto) throws SQLException {
        System.out.println(placeOrderDto);

        String orderId = placeOrderDto.getOrderId();
        String customerId = placeOrderDto.getUserId();
        LocalDate date = placeOrderDto.getDate();

        Connection connection = null;
        try {
            connection = DbConnection.getInstance().getConnection();
            connection.setAutoCommit(false);

            boolean isOrderSaved = orderDAO.saveOrder(orderId, customerId, date);
            if (isOrderSaved) {
                boolean isUpdated = bookDAO.update((BooksManagementDTO) placeOrderDto.getCartTmList());
                if(isUpdated) {
                    boolean isOrderDetailSaved = orderDetailDAO.saveOrderDetails(placeOrderDto.getOrderId(), placeOrderDto.getCartTmList());
                    if (isOrderDetailSaved) {
                        connection.commit();
                    }
                }
            }
        } catch (SQLException e) {
            connection.rollback();
        } finally {
            connection.setAutoCommit(true);
        }

        return true;
    }
}
