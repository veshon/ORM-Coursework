package org.example.dao.custom;

import org.example.dao.CrudDAO;
import org.example.dto.PlaceOrderDTO;

import java.sql.SQLException;

public interface OrderDAO  {

    String generateNextOrderId() throws SQLException;
}
