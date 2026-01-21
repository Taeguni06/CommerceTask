package Challenge;

import Challenge.Category;
import Challenge.CategoryType;
import Challenge.Product;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static Challenge.CategoryType.*;


public class CommerceSystem {
    private Scanner scanner;
    private List<Category> categories;
    Basket basket = new Basket();
    Customer customer = new Customer();

    CommerceSystem() {
        this.categories = new ArrayList<>();
        this.scanner = new Scanner(System.in);
        setup();
    }

    private void setup() {

        Category electronics = new Category(ELECTRONICS);
        electronics.addProduct(new Product("Galaxy S25", 1200000, "최신 안드로이드 스마트폰", 50));
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
            } else if (choice == 8) {
                showBasket();
            } else if (choice == 9) {
                cancel();
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
        System.out.println("\n[ 주문 관리 ]");
        System.out.println("8. 장바구니      | 장바구니 보기");
        System.out.println("9. 주문 취소");
        System.out.println("0. 종료      | 프로그램 종료");
        System.out.print("입력: ");
    }

    private void showCategoryDetail(Category category) {

        while (true) {
            System.out.println("\n[ " + category.getCategoryName() + " 카테고리 ]");
            List<Product> products = category.getProductList();

            for (int i = 0; i < products.size(); i++) {
                Product p = products.get(i);
                System.out.println((i + 1) + ". " + p.getName() + " | " + p.getAmount() + "원 | " + p.getDetail() + " | 재고: "+ p.getQuantity() + "개");
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
                        selected.getDetail() + " | 재고: "+ selected.getQuantity() + "개");



                System.out.println("위 상품을 장바구니에 추가하시겠습니까?\n" +
                        "1. 확인        0. 취소");
                int action = scanner.nextInt();
                switch (action) {
                    case 1:
                        System.out.println("\n장바구니에 담을 수량을 입력해주세요");
                        int volume = scanner.nextInt();
                        basket.setVolume(volume);
                        if (volume <= selected.getQuantity()) {
                            basket.addProduct(selected);
                            basket.totalAmount(selected);
                            System.out.println(selected.getName() + "\n가 " + basket.getVolume() + "개가 장바구니에 추가되었습니다.");
                        } else System.out.println("재고가 부족합니다.");
                    case 0: break;
                    default:
                        System.out.println("잘못된 입력입니다.");
                }

            } else {
                System.out.println("잘못된 입력입니다.");
            }
        }
    }

    private void showBasket() {
        System.out.println("[ 장바구니 ]");
        while (true) {
            List<Product> productList = basket.getProductList();
            if (basket.getTotalAmount().equals("0")) {
                System.out.println("장바구니가 비어있습니다.");
                break;
            } else {
                for (Product p : productList) {
                    System.out.println(p.getName() + " | " + p.getAmount() + "원 | " + p.getDetail() + " | " + basket.getVolume() + "개");
                }
            }
            System.out.println("[ 총 주문 금액 ]\n" + basket.getTotalAmount() + "원");

            System.out.println("1. 주문하기");

            System.out.println("0. 뒤로가기");

            int choice = scanner.nextInt();
            if (choice == 0) {
                break;
            } else if (choice == 1) {
                for (Product p : productList) {
                    p.setQuantity(p.getQuantity() - basket.getVolume());
                    basket.clearBasket();
                    System.out.println("주문 완료되었습니다.");
                    break;
                }
                break;
            } else {
                System.out.println("잘못된 입력입니다.");
            }
        }
    }

    private void cancel () {
        basket.clearBasket();
    }

    private void adminMode() {
        String password = scanner.next();
        if (password.equals("admin")){
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
            }
        } else {
            System.out.println("비밀번호가 틀립니다.");
        }
    }

    private void adminCategoryMenu() {
        System.out.println("\n[ 관리자 모드 ]");
        for (int i = 0; i < categories.size(); i++) {
            System.out.println((i + 1) + ". " + categories.get(i).getCategoryName());
        }
        System.out.print("입력: ");
    }

    private void productAdd() {
        adminCategoryMenu();
        while (true) {

        }
    }
}


