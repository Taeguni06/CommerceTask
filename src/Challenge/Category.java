package Challenge;

import java.util.ArrayList;
import java.util.List;

public class Category {
    private CategoryType type;      // 예: "전자제품"
    private List<Product> products;   // [요구사항] Product들을 리스트로 관리

    public Category(CategoryType type) {
        this.type = type;
        this.products = new ArrayList<>(); // 이 카테고리만의 상품 목록 생성
    }

    // [요구사항] 카테고리 이름을 반환하는 메서드
    public String getCategoryName() {
        return type.getName();
    }

    // 상품 리스트에 개별 상품(Product 객체)을 추가하는 기능
    public void addProduct(Product product) {
        this.products.add(product);
    }

    // 이 카테고리에 속한 전체 상품 리스트를 가져오는 기능
    public List<Product> getProductList() {
        return this.products;
    }
}
