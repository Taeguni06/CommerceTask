package Challenge;

public class Customer {
    private String name;
    private String mail;
    private int totalPrice;
    private CustomerGrade grade;


    Customer () {

    }
    public Customer(String mail) {
        this.mail = mail;
        this.totalPrice = 0;
        this.grade = CustomerGrade.BRONZE;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public CustomerGrade getGrade() {
        if (totalPrice >= 500_000 && totalPrice < 1_000_000) {
            return CustomerGrade.SILVER;
        } else if (totalPrice >= 1_000_000 && totalPrice < 2_000_000) {
            return CustomerGrade.GOLD;
        } else if (totalPrice > 2000000) {
            return CustomerGrade.PLATINUM;
        } else return CustomerGrade.BRONZE;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }
}
