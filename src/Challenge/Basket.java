package Challenge;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    //장바구니에서 특정 상품 제거 기능
    public void removeProductStream(String productName) {
        int initialSize = products.size();

        // 스트림 필터를 사용하여 해당 이름을 제외한 상품들만 다시 리스트로 수집
        this.products = products.stream()
                .filter(p -> !p.getName().equalsIgnoreCase(productName))
                .collect(Collectors.toCollection(ArrayList::new));

        if (products.size() < initialSize) {
            System.out.println(productName + " 상품이 제거되었습니다.");
            recalculateTotal(); // 금액 재계산
        } else {
            System.out.println("장바구니에 해당 상품이 없습니다.");
        }
    }

    // 상품 삭제 시 총액을 다시 계산하는 로직
    private void recalculateTotal() {
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        this.totalAmount = products.stream()
                .mapToInt(p -> Integer.parseInt(p.getPrice().replace(",", "")) * volume)
                .sum();
        this.formatedTotalAmount = decimalFormat.format(totalAmount);
    }

    public void totalAmount(Product product) {
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        int amount = Integer.parseInt(product.getPrice().replace(",", ""));
        totalAmount += amount * volume;
        this.formatedTotalAmount = decimalFormat.format(totalAmount);
    }

    public void clearBasket() {
        products.clear();
        totalAmount = 0;
        formatedTotalAmount = "0";
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public int getVolume() {
        return volume;
    }
}