package brainacad.model;

public class Drink
{
    private int id;
    private String nameEn;
    private String nameLocal;
    private double price;

    public Drink()
    {

    }

    public Drink(int id, String nameEn, String nameLocal, double price)
    {
        this.id = id;
        this.nameEn = nameEn;
        this.nameLocal = nameLocal;
        this.price = price;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNameEn() { return nameEn; }
    public void setNameEn(String nameEn) { this.nameEn = nameEn; }

    public String getNameLocal() { return nameLocal; }
    public void setNameLocal(String nameLocal) { this.nameLocal = nameLocal; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    @Override
    public String toString() {
        return "Drink{id=" + id + ", nameEn='" + nameEn + "', nameLocal='" + nameLocal + "', price=" + price + '}';
    }
}