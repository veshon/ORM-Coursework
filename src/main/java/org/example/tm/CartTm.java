package org.example.tm;

import javafx.scene.control.Button;

public class CartTm {
    private String code;
    private String description;
/*    private int qty;
    private double unitPrice;
    private double tot;*/
    private Button btn;

    public CartTm() {
    }

    public CartTm(String code, String description, Button btn) {
        this.code = code;
        this.description = description;
        this.btn = btn;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Button getBtn() {
        return btn;
    }

    public void setBtn(Button btn) {
        this.btn = btn;
    }
}
