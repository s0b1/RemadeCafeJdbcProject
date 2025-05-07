package brainacad.model;

import java.time.LocalDateTime;

public class Order {
    private int id;
    private Integer clientId;
    private LocalDateTime orderDate;

    public Order() {}

    public Order(int id, Integer clientId, LocalDateTime orderDate) {
        this.id = id;
        this.clientId = clientId;
        this.orderDate = orderDate;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Integer getClientId() { return clientId; }
    public void setClientId(Integer clientId) { this.clientId = clientId; }

    public LocalDateTime getOrderDate() { return orderDate; }
    public void setOrderDate(LocalDateTime orderDate) { this.orderDate = orderDate; }

    @Override
    public String toString() {
        return "Order{id=" + id + ", clientId=" + clientId + ", orderDate=" + orderDate + "}";
    }
}