package brainacad.model;

public class OrderItem
{
    private int id;
    private int orderId;
    private String itemType; // drink или dessert
    private int itemId;
    private int quantity;

    public OrderItem()
    {

    }

    public OrderItem(int id, int orderId, String itemType, int itemId, int quantity)
    {
        this.id = id;
        this.orderId = orderId;
        this.itemType = itemType;
        this.itemId = itemId;
        this.quantity = quantity;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getOrderId() { return orderId; }
    public void setOrderId(int orderId) { this.orderId = orderId; }

    public String getItemType() { return itemType; }
    public void setItemType(String itemType) { this.itemType = itemType; }

    public int getItemId() { return itemId; }
    public void setItemId(int itemId) { this.itemId = itemId; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    @Override
    public String toString() {
        return "OrderItem{id=" + id + ", orderId=" + orderId + ", itemType='" +
                itemType + "', itemId=" + itemId + ", quantity=" + quantity + "}";
    }
}