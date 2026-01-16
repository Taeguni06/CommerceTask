import java.text.DecimalFormat;
import java.util.ArrayList;

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
        String dfDone = df.format(amount);
        return dfDone;

    }
    public String getDetail() {
        return detail;
    }
    public int getQuantity() {
        return quantity;
    }
}
