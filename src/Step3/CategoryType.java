package Step3;

public enum CategoryType {
    ELECTRONICS("전자제품"),
    CLOTHING("의류"),
    FOOD("식품");

    private final String name;

    CategoryType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}