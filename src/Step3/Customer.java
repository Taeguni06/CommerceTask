package Step3;

public class Customer {
    String name;
    String mail;
    String account;
    int totalPrice;

    public Customer(String name, String mail, int totalPrice) {
        this.name = name;
        this.mail = mail;
        this.totalPrice = totalPrice;
        if (totalPrice < 500000) {
            account = "브론즈";
        } else if (totalPrice >= 500000 && totalPrice < 1000000) {
            account = "실버";
        } else if (totalPrice >= 1000000 && totalPrice < 2000000) {
            account = "골드";
        } else if (totalPrice > 2000000) {
            account = "플래티넘";
        }
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

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }
}
