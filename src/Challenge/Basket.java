package Challenge;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class Basket {
    private List<Product> products;
    private String formatedTotalAmount = "0";
    private int totalAmount;
    private int volume;

    public String getTotalAmount() {
        return formatedTotalAmount;
    }

    public Basket() {
        this.products = new ArrayList<>();
    }

    public void addProduct(Product product) {
        this.products.add(product);
    }

    public List<Product> getProductList() {
        return this.products;
    }

    public void totalAmount(Product product) {
        DecimalFormat decimalFormat = new DecimalFormat("#,###");

        int amount = Integer.parseInt(product.getAmount().replace(",", ""));

        totalAmount += amount * volume;

        this.formatedTotalAmount = decimalFormat.format(totalAmount);
    }

    public void clearBasket() {
        products.clear();

        formatedTotalAmount = "0";
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public int getVolume() {
        return volume;
    }
}
