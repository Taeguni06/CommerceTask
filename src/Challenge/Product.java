package Challenge;

import java.text.DecimalFormat;

public class Product {
    private String name;
    private int amount;
    private String detail;
    private int quantity;

    Product(String name, int amount, String detail, int quantity) {
        this.name = name;
        this.amount = amount;
        this.detail = detail;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public String getAmount() {
        DecimalFormat df = new DecimalFormat("#,###");
        // 1,000,000 따옴표 추가 기능
        return df.format(amount);

    }
    public String getDetail() {
        return detail;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
