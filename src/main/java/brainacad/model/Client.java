package brainacad.model;

import java.time.LocalDate;

public class Client
{
    private int id;
    private String fullName;
    private LocalDate birthDate;
    private String phone;
    private String email;
    private double discount;

    public Client()
    {

    }

    public Client(int id, String fullName, LocalDate birthDate, String phone, String email, double discount)
    {
        this.id = id;
        this.fullName = fullName;
        this.birthDate = birthDate;
        this.phone = phone;
        this.email = email;
        this.discount = discount;
    }

    public int getId()
    {
        return id;
    }
    public void setId(int id)
    {
        this.id = id;
    }

    public String getFullName()
    {
        return fullName;
    }
    public void setFullName(String fullName)
    {
        this.fullName = fullName;
    }

    public LocalDate getBirthDate()
    {
        return birthDate;
    }
    public void setBirthDate(LocalDate birthDate)
    {
        this.birthDate = birthDate;
    }

    public String getPhone()
    {
        return phone;
    }
    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public String getEmail()
    {
        return email;
    }
    public void setEmail(String email)
    {
        this.email = email;
    }

    public double getDiscount()
    {
        return discount;
    }
    public void setDiscount(double discount)
    {
        this.discount = discount;
    }

    @Override
    public String toString() {
        return "Client{id=" + id + ", fullName='" + fullName + "', birthDate=" + birthDate +
                ", phone='" + phone + "', email='" + email + "', discount=" + discount + "}";
    }
}