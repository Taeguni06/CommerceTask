package Challenge;

import java.util.ArrayList;
import java.util.List;

public class Basket {
    private List<Product> products;   // [요구사항] Product들을 리스트로 관리

    public Basket() {
        this.products = new ArrayList<>(); // 이 카테고리만의 상품 목록 생성
    }

    public void addProduct(Product product) {
        this.products.add(product);
    }

    public List<Product> getProductList() {
        return this.products;
    }
}
