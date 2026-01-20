package Challenge;

import Challenge.Category;
import Challenge.CategoryType;
import Challenge.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static Challenge.CategoryType.*;


public class CommerceSystem {
    private Scanner scanner;
    private List<Category> categories;
    private List<Basket> baskets;
    Basket basket = new Basket();
    CommerceSystem() {
        this.categories = new ArrayList<>();
        this.scanner = new Scanner(System.in);
        this.baskets = new ArrayList<>();
        setup();
    }

    private void setup() {

        Category electronics = new Category(ELECTRONICS);
        electronics.addProduct(new Product("Galaxy S24", 1200000, "최신 안드로이드 스마트폰", 50));
        electronics.addProduct(new Product("iPhone 15", 1350000, "Apple의 최신 스마트폰", 30));
        electronics.addProduct(new Product("MacBook Pro", 2400000, "M3 칩셋 탑재", 15));
        electronics.addProduct(new Product("AirPod Pro", 350000, "노이즈 캔슬링 무선 이어폰", 10));

        Category clothing = new Category(CLOTHING);
        clothing.addProduct(new Product("후드 티", 60000, "따뜻한 후드티", 100));

        Category food = new Category(FOOD);
        food.addProduct(new Product("사과 1박스", 30000, "꿀사과", 40));

        categories.add(electronics);
        categories.add(clothing);
        categories.add(food);
    }

    public void start() {
        while (true) {
            displayMainMenu();
            int choice = scanner.nextInt();

            if (choice == 0) {
                System.out.println("커머스 플랫폼을 종료합니다.");
                break;
            } else if (choice > 0 && choice <= categories.size()) {
                // 선택한 카테고리로 진입 (인덱스는 선택번호 - 1)
                showCategoryDetail(categories.get(choice - 1));
            } else if (choice == 9) {
                showBasket();
            } else {
                System.out.println("잘못된 입력입니다. 다시 선택해주세요.");
            }
        }
    }

    private void displayMainMenu() {
        System.out.println("\n[ 실시간 커머스 플랫폼 메인 ]");
        for (int i = 0; i < categories.size(); i++) {
            System.out.println((i + 1) + ". " + categories.get(i).getCategoryName());
        }
        System.out.println("9. 장바구니      | 장바구니 보기");
        System.out.println("0. 종료      | 프로그램 종료");
        System.out.print("입력: ");
    }

    private void showCategoryDetail(Category category) {

        while (true) {
            System.out.println("\n[ " + category.getCategoryName() + " 카테고리 ] ( 상품 번호를 입력하면 장바구니에 추가됩니다. )");
            List<Product> products = category.getProductList();

            for (int i = 0; i < products.size(); i++) {
                Product p = products.get(i);
                System.out.println((i + 1) + ". " + p.getName() + " | " + p.getAmount() + "원 | " + p.getDetail());
            }
            System.out.println("0. 뒤로가기");
            System.out.print("입력: ");

            int productChoice = scanner.nextInt();

            if (productChoice == 0) {
                break; // 메인 메뉴로 돌아감
            } else if (productChoice > 0 && productChoice <= products.size()) {
                Product selected = products.get(productChoice - 1);
                System.out.println("\n선택한 상품: " + selected.getName() + " | " +
                        selected.getAmount() + "원 | " +
                        selected.getDetail() + " | 재고: " +
                        selected.getQuantity() + "개 \n가 장바구니에 추가되었습니다.");

                basket.addProduct(selected);
                baskets.add(basket);
            } else {
                System.out.println("잘못된 입력입니다.");
            }
        }
    }

    private void showBasket(){
        System.out.println("[ 장바구니 ]");
        while (true) {
            List<Product> productList = basket.getProductList();
            for (Product p : productList) {
                System.out.println(p.getName() + " | " + p.getAmount() + "원 | " + p.getDetail());
            }
            System.out.println("0. 뒤로가기");
            int choice = scanner.nextInt();
            if (choice == 0) {
                break;
            } else {
                System.out.println("잘못된 입력입니다.");
            }
        }
    }
}


