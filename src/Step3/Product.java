package Step3;

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
        String dfDone = df.format(amount); // 1,000,000 따옴표 추가 기능
        return dfDone;

    }
    public String getDetail() {
        return detail;
    }
    public int getQuantity() {
        return quantity;
    }
}
