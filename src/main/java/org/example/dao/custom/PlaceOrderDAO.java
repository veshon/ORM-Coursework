package org.example.dao.custom;
import org.example.dto.PlaceOrderDTO;

import java.sql.SQLException;

public interface PlaceOrderDAO {
    boolean placeOrder(PlaceOrderDTO placeOrderDto) throws SQLException;
}
