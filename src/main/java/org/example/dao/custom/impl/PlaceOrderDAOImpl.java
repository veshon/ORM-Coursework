package org.example.dao.custom.impl;

import org.example.dao.custom.PlaceOrderDAO;
import org.example.dto.PlaceOrderDTO;

import java.sql.SQLException;

public class PlaceOrderDAOImpl implements PlaceOrderDAO {
    @Override
    public boolean placeOrder(PlaceOrderDTO placeOrderDto) {
        return false;
    }
}
