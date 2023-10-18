package lk.ise.pos.view.tm;

import javafx.scene.control.Button;

public class CartTM {
    private String itemCode;
    private String description;
    private double unitPrice;
    private int Qty;
    private double total;
    private Button btn;

    public CartTM() {
    }

    public CartTM(String itemCode, String description, double unitPrice, int qty, double total, Button btn) {
        this.itemCode = itemCode;
        this.description = description;
        this.unitPrice = unitPrice;
        Qty = qty;
        this.total = total;
        this.btn = btn;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getQty() {
        return Qty;
    }

    public void setQty(int qty) {
        Qty = qty;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public Button getBtn() {
        return btn;
    }

    public void setBtn(Button btn) {
        this.btn = btn;
    }

    @Override
    public String toString() {
        return "CartTM{" +
                "itemCode='" + itemCode + '\'' +
                ", description='" + description + '\'' +
                ", unitPrice=" + unitPrice +
                ", Qty=" + Qty +
                ", total=" + total +
                '}';
    }
}
