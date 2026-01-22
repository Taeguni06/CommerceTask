package Challenge;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;

import static Challenge.CategoryType.*;


public class CommerceSystem {
    private Scanner scanner;
    private List<Category> categories;
    private Basket basket = new Basket();
    private Customer customer = new Customer();
    private Map<String, Customer> customerMap = new HashMap<>();


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
            try {
                displayMainMenu();
                int choice = scanner.nextInt();
                scanner.nextLine();

                if (choice == 0) {
                    System.out.println("커머스 플랫폼을 종료합니다.");
                    break;
                } else if (choice > 0 && choice <= categories.size()) {
                    // 선택한 카테고리로 진입 (인덱스는 선택번호 - 1)
                    showCategoryDetail(categories.get(choice - 1));
                } else if (choice == 6) {
                    processCustomerEmail();
                } else if (choice == 7) {
                    showBasket();
                } else if (choice == 8) {
                    reQuestCancel();
                } else if (choice == 9) {
                    adminStart();
                } else {
                    System.out.println("잘못된 입력입니다. 다시 선택해주세요.");
                }
            } catch (Exception e) {
                System.out.println("잘못된 입력입니다.");
                scanner.next();
            }
        }
    }

    private void displayMainMenu() {
        System.out.println("\n[ 실시간 커머스 플랫폼 메인 ]");
        for (int i = 0; i < categories.size(); i++) {
            System.out.println((i + 1) + ". " + categories.get(i).getCategoryName());
        }
        System.out.println("\n[ 주문 관리 ]");
        System.out.println("6. 로그인 및 회원가입 (이메일)");
        System.out.println("7. 장바구니      | 장바구니 보기");
        System.out.println("8. 주문 취소");
        System.out.println("\n9. 관리자 모드");
        System.out.println("0. 종료      | 프로그램 종료");
        System.out.print("입력: ");
    }

    private void showCategoryDetail(Category category) {

        while (true) {
            System.out.println("\n[ " + category.getCategoryName() + " 카테고리 ]");
            List<Product> products = category.getProductList();

            for (int i = 0; i < products.size(); i++) {
                Product p = products.get(i);
                System.out.println((i + 1) + ". " + p.getName() + " | " + p.getPrice() + "원 | " + p.getDetail() + " | 재고: " + p.getQuantity() + "개");
            }

            System.out.println("0. 뒤로가기");
            System.out.print("입력: ");

            int productChoice = scanner.nextInt();

            if (productChoice == 0) {
                break;
            } else if (productChoice > 0 && productChoice <= products.size()) {
                Product selected = products.get(productChoice - 1);
                System.out.println("\n선택한 상품: " + selected.getName() + " | " +
                        selected.getPrice() + "원 | " +
                        selected.getDetail() + " | 재고: " + selected.getQuantity() + "개");


                System.out.println("위 상품을 장바구니에 추가하시겠습니까?\n" +
                        "1. 확인        0. 취소");
                System.out.print("입력: ");
                int action = scanner.nextInt();
                switch (action) {
                    case 1:
                        System.out.println("\n장바구니에 담을 수량을 입력해주세요");
                        System.out.print("입력: ");
                        int volume = scanner.nextInt();
                        basket.setVolume(volume);
                        if (volume <= selected.getQuantity()) {
                            basket.addProduct(selected);
                            basket.totalAmount(selected);
                            System.out.println(selected.getName() + "\n가 " + basket.getVolume() + "개가 장바구니에 추가되었습니다.");
                        } else {
                            System.out.println("재고가 부족합니다.");
                        }
                        break;
                    case 0:
                        break;
                    default:
                        System.out.println("잘못된 입력입니다.");
                        break;
                }

            } else {
                System.out.println("잘못된 입력입니다.");
            }
        }
    }

    private void showBasket() {
        while (true) {
            List<Product> productList = basket.getProductList();

            if (productList.isEmpty()) {
                System.out.println("\n장바구니가 비어있습니다.");
                return;
            }

            System.out.println("\n[ 장바구니 목록 ]");
            for (Product p : productList) {
                System.out.println("- " + p.getName() + " | " + p.getPrice() + "원 | " + basket.getVolume() + "개");
            }

            String rawAmount = basket.getTotalAmount().replace(",", "");
            int totalOrderPrice = Integer.parseInt(rawAmount);

            System.out.println("\n[ 총 결제 예정 금액 ] " + basket.getTotalAmount() + "원");
            System.out.println("1. 주문하기  |  0. 뒤로가기");
            System.out.print("입력: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 1) {
                // 재고 차감 및 누적 금액 업데이트
                for (Product p : productList) {
                    p.setQuantity(p.getQuantity() - basket.getVolume());
                }
                customer.setTotalPrice(customer.getTotalPrice() + totalOrderPrice);

                System.out.println("\n주문이 완료되었습니다!");
                System.out.println("결제 금액: " + basket.getTotalAmount() + "원");
                System.out.println("누적 주문 금액: " + customer.getTotalPrice() + "원");

                basket.clearBasket();
                return;
            } else if (choice == 0) {
                break;
            }
        }
    }

    private void reQuestCancel() {
        basket.clearBasket();
        System.out.println("장바구니를 초기화했습니다.");
    }

    public void adminStart() {
        System.out.println("[ 3회 실패시 메인 메뉴로 돌아갑니다. ]");
        int j = 1;
        try {
            while (true) {
                System.out.print("비밀번호를 입력해주세요: ");
                System.out.print("입력: ");
                String password = scanner.nextLine();
                if (password.equals("admin123")) {
                    adminMainMenu();
                    System.out.print("입력: ");
                    int choice = scanner.nextInt();

                    if (choice == 0) {
                        System.out.println("관리자 모드를 종료합니다.");
                        break;
                    } else if (choice == 1) {
                        addProduct(categories.get(categoryFinder()));
                        break;
                    } else if (choice == 2) {
                        fixProduct(categories.get(categoryFinder()));
                        break;
                    } else if (choice == 3) {
                        System.out.println("삭제할 상품의 카테고리를 입력해주세요.");
                        removeProduct(categories.get(categoryFinder()));
                        break;
                    } else if (choice == 4) {
                        productCheck();
                        break;
                    } else {
                        System.out.println("잘못된 입력입니다. 다시 선택해주세요.");
                    }
                } else {
                    System.out.println("비밀번호가 틀렸습니다. (" + j + "회)");
                    j++;
                    if (j == 4) {
                        break;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("잘못된 입력입니다.");
            scanner.next();
        }
    }

    private void adminMainMenu() {
        System.out.println("[ 관리자 모드 ]\n" +
                "1. 상품 추가\n" +
                "2. 상품 수정\n" +
                "3. 상품 삭제\n" +
                "4. 전체 상품 현황\n" +
                "0. 메인으로 돌아가기");
    }

    private void addProduct(Category category) {
        try {
            scanner.nextLine();
            System.out.println("\n[ " + category.getCategoryName() + " 카테고리 ]");

            System.out.print("상품명을 입력해주세요: ");
            String name = scanner.nextLine();

            System.out.print("가격을 입력해주세요: ");
            int price = scanner.nextInt();

            scanner.nextLine();
            System.out.print("상품 설명을 입력해주세요: ");
            String detail = scanner.nextLine();

            System.out.print("재고 수량을 입력해주세요: ");
            int quantity = scanner.nextInt();
            scanner.nextLine();

            category.addProduct(new Product(name, price, detail, quantity));
            System.out.println(name + " 상품이 " + category.getCategoryName() + "카테고리에 추가되었습니다.");
        } catch (Exception e) {
            System.out.println("잘못된 입력입니다.");
            scanner.next();
        }
    }

    private void fixProduct(Category category) {
        scanner.nextLine();
        System.out.print("수정할 상품명을 입력해주세요: ");
        String targetName = scanner.nextLine();

        Product targetProduct = null;
        for (Product p : category.getProductList()) {
            if (p.getName().equals(targetName)) {
                targetProduct = p;
                break;
            }
        }

        if (targetProduct != null) {
            System.out.println("1. 가격 | 2. 설명 | 3. 재고수량");
            System.out.print("수정할 항목을 선택해주세요: ");
            System.out.print("입력: ");
            int menu = scanner.nextInt();
            scanner.nextLine();

            switch (menu) {
                case 1:
                    System.out.print("새 가격을 입력하세요: ");
                    targetProduct.setPrice(scanner.nextInt());
                    break;
                case 2:
                    System.out.print("새 설명을 입력하세요: ");
                    targetProduct.setDetail(scanner.nextLine());
                    break;
                case 3:
                    System.out.print("새 재고수량을 입력하세요: ");
                    targetProduct.setQuantity(scanner.nextInt());
                    break;
                default:
                    System.out.println("잘못된 선택입니다.");
            }
            System.out.println("수정이 완료되었습니다.");
        } else {
            System.out.println("존재하지 않는 상품입니다.");
        }
    }

    private void removeProduct(Category category) {

        List<Product> products = category.getProductList();

        for (int i = 0; i < products.size(); i++) {
            Product p = products.get(i);
            System.out.println((i + 1) + ". " + p.getName() + " | " + p.getPrice() + "원 | " + p.getDetail() + " | 재고: " + p.getQuantity() + "개");
        }

        System.out.println("삭제할 상품을 선택해주세요.");
        System.out.print("입력: ");
        int i = scanner.nextInt();
        category.removeProduct(i);

        System.out.println();
    }

    private void productCheck() {
        while (true) {
            for (Category c : categories) {
                System.out.println("[ " + c.getCategoryName() + " ]");
                List<Product> products = c.getProductList();

                if (products.isEmpty()) {
                    System.out.println("등록된 상품이 없습니다.");
                } else {
                    for (Product p : products) {
                        System.out.println("- " + p.getName() + " | 가격: " + p.getPrice() +
                                " | 재고: " + p.getQuantity() + "개");
                    }
                }
            }
            System.out.println("0. 뒤로가기");
            System.out.print("입력: ");
            String next = scanner.next();
            if (next.equals("0")) {
                break;
            } else {
                System.out.println("잘못된 입력입니다.");
            }
        }
    }

    private int categoryFinder() {
        for (int i = 0; i < categories.size(); i++) {
            System.out.println((i + 1) + ". " + categories.get(i).getCategoryName());
        }
        System.out.print("입력: ");
        return scanner.nextInt() - 1;
    }

    private void processCustomerEmail() {
        System.out.println("\n[ 고객 이메일 입력 ]");
        System.out.print("입력: ");
        String email = scanner.nextLine();

        // 고객 조회 및 세션 설정
        if (!customerMap.containsKey(email)) {
            customerMap.put(email, new Customer(email));
        }
        this.customer = customerMap.get(email);

        // 등급 및 할인율 정보 제공
        CustomerGrade grade = customer.getGrade();
        double discountRate = grade.getDis() * 100; // 예: 0.1 -> 10

        System.out.println("\n해당 유저는 " + grade.name() + " 등급이므로 "
                + (int)discountRate + "% 할인이 적용됩니다.");

        System.out.println("주문하시겠습니까?");
        System.out.println("1. 주문 확정(장바구니 이동)  2. 메인으로 돌아가기");
        System.out.print("입력: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        if (choice == 1) {
            showBasket();
        }
    }
}


