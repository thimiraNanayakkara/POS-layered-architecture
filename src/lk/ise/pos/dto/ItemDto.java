package lk.ise.pos.dto;

public class ItemDto {
    private String code;
    private String description;
    private int QtyOnHand;
    private double unitPrice;

    public ItemDto(String code, String description, int qtyOnHand, double unitPrice) {
        this.code = code;
        this.description = description;
        QtyOnHand = qtyOnHand;
        this.unitPrice = unitPrice;
    }

    public ItemDto() {
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

    @Override
    public String toString() {
        return "Item{" +
                "code='" + code + '\'' +
                ", description='" + description + '\'' +
                ", QtyOnHand=" + QtyOnHand +
                ", unitPrice=" + unitPrice +
                '}';
    }
}
