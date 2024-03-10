package org.example.dto;

import org.example.tm.CartTm;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PlaceOrderDTO {
    private String orderId;
    private LocalDate date;
    private String userId;
    private List<CartTm> cartTmList = new ArrayList<>();

    public PlaceOrderDTO() {
    }

    public PlaceOrderDTO(String orderId, LocalDate date, String userId, List<CartTm> cartTmList) {
        this.orderId = orderId;
        this.date = date;
        this.userId = userId;
        this.cartTmList = cartTmList;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<CartTm> getCartTmList() {
        return cartTmList;
    }

    public void setCartTmList(List<CartTm> cartTmList) {
        this.cartTmList = cartTmList;
    }
}
