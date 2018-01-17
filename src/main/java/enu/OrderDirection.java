package enu;

public enum  OrderDirection {

    Direction_ASC("asc","升序"),
    Direction_DESC("desc","降序");

    private String order;
    private String description;


    private OrderDirection(String order, String description) {
        this.order = order;
        this.description = description;
    }

    public String getOrder() {
        return order;
    }
    public String getDescription() {
        return description;
    }
}
