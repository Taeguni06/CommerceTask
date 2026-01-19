package Step2;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    static void main() {
        Scanner sc = new Scanner(System.in);
        List<Product> products = new ArrayList<>();

        int j = 1;
        products.add(new Product("Galaxy S25", 1200000, "최신 안드로이드 스마트폰", 1));
        products.add(new Product("iPhone 16", 1350000, "Apple의 최신 스마트폰", 1));
        products.add(new Product("MacBook Pro", 2400000, "M3 칩셋이 탑재된 노트북", 1));
        products.add(new Product("AirPods Pro", 350000, "노이즈 캔슬링 무선 이어폰", 1));

        System.out.println("[ 실시간 커머스 플랫폼 - 전자제품 ]");
        while (true) {
            for (Product p : products) {
                System.out.println(j + ". " + p.getName() + " | " + p.getAmount() + "원" + " | " + p.getDetail());
                j++;
            }
            System.out.println("0. 종료 | 프로그램 종료");
            int next = sc.nextInt();
            if (next == 0) { break; }
        }
    }
}
