package Challenge;

import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

import static Challenge.CategoryType.*;

public class CommerceSystem {
    private final Scanner scanner;
    private final List<Category> categories;
    private final Basket basket = new Basket();
    private final Map<String, Customer> customerMap = new HashMap<>();
    private final DecimalFormat dF = new DecimalFormat("#,###");

    CommerceSystem() {
        this.categories = new ArrayList<>();
        this.scanner = new Scanner(System.in);
        setup();
    }

    private void setup() { // 기초 데이터
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

    public void start() { // main에서의 실행 메서드
        while (true) {
            try {
                displayMainMenu();
                int choice = scanner.nextInt();
                scanner.nextLine();

                if (choice == 0) {
                    System.out.println("커머스 플랫폼을 종료합니다.");
                    break;
                } else if (choice > 0 && choice <= categories.size()) {
                    showCategoryDetail(categories.get(choice - 1));
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
                if (scanner.hasNext()) scanner.next();
            }
        }
    }

    private void displayMainMenu() {
        System.out.println("\n[ 실시간 커머스 플랫폼 메인 ]");
        for (int i = 0; i < categories.size(); i++) {
            System.out.println((i + 1) + ". " + categories.get(i).getCategoryName());
        }
        System.out.println("\n[ 주문 관리 ]");
        System.out.println("7. 장바구니      | 장바구니 보기");
        System.out.println("8. 주문 취소");
        System.out.println("\n9. 관리자 모드");
        System.out.println("0. 종료      | 프로그램 종료");
        System.out.print("입력: ");
    }

    private void showCategoryDetail(Category category) { // 해당 카테고리를 선택했을 때 실행.
        while (true) {
            System.out.println("\n[ " + category.getCategoryName() + " 카테고리 ]");
            System.out.println("1. 전체 상품 보기");
            System.out.println("2. 100만원 이하 상품만 보기");
            System.out.println("3. 100만원 이상 상품만 보기");
            System.out.println("0. 뒤로가기");
            System.out.print("입력: ");

            int menuChoice = scanner.nextInt();
            if (menuChoice == 0) break;

            List<Product> products = category.getProductList();

            if (menuChoice == 2) {
                products = products.stream()
                        .filter(p -> Integer.parseInt(p.getPrice().replace(",", "")) <= 1000000)
                        .collect(Collectors.toList());
            }

            if (menuChoice == 3) {
                products = products.stream()
                        .filter(p -> Integer.parseInt(p.getPrice().replace(",", "")) > 1000000)
                        .collect(Collectors.toList());
            }

            if (products.isEmpty()) {
                System.out.println("해당 조건의 상품이 없습니다.");
                continue;
            }

            for (int i = 0; i < products.size(); i++) {
                Product p = products.get(i);
                System.out.println((i + 1) + ". " + p.getName() + " | " + p.getPrice() + "원 | " + p.getDetail() + " | 재고: " + p.getQuantity() + "개");
            }

            System.out.println("0. 뒤로가기");
            System.out.print("상품 선택 입력: ");
            int productChoice = scanner.nextInt();

            if (productChoice > 0 && productChoice <= products.size()) {
                Product selected = products.get(productChoice - 1);
                processAddToBasket(selected);
            }
        }
    }

    private void processAddToBasket(Product selected) { // 장바구니 담기
        System.out.println("\n선택한 상품: " + selected.getName() + " | " + selected.getPrice() + "원");
        System.out.println("위 상품을 장바구니에 추가하시겠습니까?\n1. 확인        0. 취소");
        int action = scanner.nextInt();
        if (action == 1) {
            System.out.print("담을 수량을 입력해주세요: ");
            int volume = scanner.nextInt();
            if (volume <= selected.getQuantity()) {
                basket.setVolume(volume);
                basket.addProduct(selected);
                basket.totalAmount(selected);
                System.out.println(selected.getName() + " " + volume + "개가 장바구니에 추가되었습니다.");
            } else {
                System.out.println("재고가 부족합니다.");
            }
        }
    }

    private void showBasket() { // 장바구니 보기, 주문, 삭제
        while (true) {
            List<Product> productList = basket.getProductList();
            if (productList.isEmpty()) {
                System.out.println("\n장바구니가 비어있습니다.");
                return;
            }

            System.out.println("\n[ 장바구니 목록 ]");
            productList.forEach(p -> System.out.println("- " + p.getName() + " | " + p.getPrice() + "원 | " + basket.getVolume() + "개"));
            System.out.println("\n[ 총 결제 예정 금액 ] " + basket.getTotalAmount() + "원");
            System.out.println("1. 주문하기  |  2. 특정 상품 제거 (Stream)  |  0. 뒤로가기");
            System.out.print("입력: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 1) {
                executeOrder(productList);
                return;
            } else if (choice == 2) {
                System.out.print("제거할 상품명을 입력하세요: ");
                String targetName = scanner.nextLine();
                basket.removeProductStream(targetName);
            } else if (choice == 0) {
                break;
            }
        }
    }

    private void executeOrder(List<Product> productList) {          // 주문 할때 이메일 입력
        int totalOrderPrice = Integer.parseInt(basket.getTotalAmount().replace(",", ""));

        System.out.print("\n결제를 위해 이메일을 입력해주세요: ");
        String email = scanner.nextLine();

        if (!customerMap.containsKey(email)) {
            customerMap.put(email, new Customer(email));
        }
        Customer customer = customerMap.get(email);

        CustomerGrade grade = customer.getGrade();
        int discount = grade.discount(totalOrderPrice);

        System.out.println("\n주문이 완료되었습니다!");
        System.out.println("할인 전 금액: " + dF.format(totalOrderPrice) + "원");
        System.out.println(grade.name() + " 등급 할인: -" + dF.format(discount) + "원");
        System.out.println("최종 결제 금액: " + dF.format(totalOrderPrice - discount) + "원");

        // 재고 차감
        productList.forEach(p -> p.setQuantity(p.getQuantity() - basket.getVolume()));
        customer.setTotalPrice(customer.getTotalPrice() + (totalOrderPrice - discount));
        basket.clearBasket();
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
        System.out.println("""
                [ 관리자 모드 ]
                1. 상품 추가
                2. 상품 수정
                3. 상품 삭제
                4. 전체 상품 현황
                0. 메인으로 돌아가기""");
    }

    private void addProduct(Category category) { // 어드민 상품 추가
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

    private void fixProduct(Category category) { //어드민 상품 수정
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


    private void removeProduct(Category category) { // 어드민 상품 삭제

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
            System.out.println("\n[ 전체 상품 현황 및 정렬 ]");
            System.out.println("1. 기본 순서 보기");
            System.out.println("2. 가격 낮은 순 보기");
            System.out.println("3. 가격 높은 순 보기");
            System.out.println("0. 뒤로가기");
            System.out.print("입력: ");

            String sortChoice = scanner.next();
            if (sortChoice.equals("0")) break;

            for (Category c : categories) {
                System.out.println("\n< " + c.getCategoryName() + " >");
                List<Product> products = c.getProductList();

                if (products.isEmpty()) {
                    System.out.println("등록된 상품이 없습니다.");
                    continue;
                }

                List<Product> sortedProducts = new ArrayList<>(products);

                if (sortChoice.equals("2")) {
                    // 가격 낮은 순
                    sortedProducts = products.stream()
                            .sorted(Comparator.comparingInt(p ->
                                    Integer.parseInt(p.getPrice().replace(",", ""))))
                            .collect(Collectors.toList());
                } else if (sortChoice.equals("3")) {
                    // 가격 높은 순
                    sortedProducts = products.stream()
                            .sorted(Comparator.comparingInt((Product p) ->
                                    Integer.parseInt(p.getPrice().replace(",", ""))).reversed())
                            .collect(Collectors.toList());
                }

                sortedProducts.forEach(p ->
                        System.out.println("- " + p.getName() + " | 가격: " + p.getPrice() + "원 | 재고: " + p.getQuantity() + "개")
                );
            }
        }
    }

    private int categoryFinder() { // 중복 코드가 많아서 생성함
        for (int i = 0; i < categories.size(); i++) {
            System.out.println((i + 1) + ". " + categories.get(i).getCategoryName());
        }
        System.out.print("입력: ");
        return scanner.nextInt() - 1;
    }
}
