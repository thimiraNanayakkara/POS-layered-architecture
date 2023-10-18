package lk.ise.pos.view.tm;


import javafx.scene.control.Button;

public class ItemTM {
    private String code;
    private String description;
    private int QtyOnHand;
    private double unitPrice;
    private Button btn;

    public ItemTM(String code, String description, int qtyOnHand, double unitPrice, Button btn) {
        this.code = code;
        this.description = description;
        QtyOnHand = qtyOnHand;
        this.unitPrice = unitPrice;
        this.btn = btn;
    }

    public ItemTM() {
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

    public int getQtyOnHand() {
        return QtyOnHand;
    }

    public void setQtyOnHand(int qtyOnHand) {
        QtyOnHand = qtyOnHand;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Button getBtn() {
        return btn;
    }

    public void setBtn(Button btn) {
        this.btn = btn;
    }

    @Override
    public String toString() {
        return "ItemTM{" +
                "code='" + code + '\'' +
                ", description='" + description + '\'' +
                ", QtyOnHand=" + QtyOnHand +
                ", unitPrice=" + unitPrice +
                '}';
    }
}
