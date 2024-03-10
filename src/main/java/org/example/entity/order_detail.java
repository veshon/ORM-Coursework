package org.example.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class order_detail {

    @Id
    @Column(nullable = false)

    private String order_id;
    private String book_id;
    private int qty;
    private double unit_price;

    public order_detail() {
    }

    public order_detail(String order_id, String book_id, int qty, double unit_price) {
        this.order_id = order_id;
        this.book_id = book_id;
        this.qty = qty;
        this.unit_price = unit_price;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getBook_id() {
        return book_id;
    }

    public void setBook_id(String book_id) {
        this.book_id = book_id;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getUnit_price() {
        return unit_price;
    }

    public void setUnit_price(double unit_price) {
        this.unit_price = unit_price;
    }
}
