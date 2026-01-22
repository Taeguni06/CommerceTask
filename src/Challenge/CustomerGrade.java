package Challenge;

import Step3.CategoryType;

public enum CustomerGrade {
    BRONZE("BRONZE", 0.0),
    SILVER("SILVER",0.05),
    GOLD("GOLD",0.1),
    PLATINUM("PLATINUM",0.15);

    private final double dis;
    private final String grade;
    CustomerGrade(String grade, double dis) {
        this.dis = dis;
        this.grade = grade;
    }


    public int discount(int price) {
        // (가격 * 할인율)을 계산 후 정수로 변환하여 반환
        return (int) (price * dis);
    }
    public double getDis() {
        return dis;
    }

    public String getGrade() {
        return grade;
    }
}




