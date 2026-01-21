package Challenge;

import java.text.DecimalFormat;

public class Product {
    private String name;
    private int price;
    private String detail;
    private int quantity;

    Product(String name, int price, String detail, int quantity) {
        this.name = name;
        this.price = price;
        this.detail = detail;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        DecimalFormat df = new DecimalFormat("#,###");
        // 1,000,000 따옴표 추가 기능
        return df.format(price);

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

    public void setPrice(int price) {
        this.price = price;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
