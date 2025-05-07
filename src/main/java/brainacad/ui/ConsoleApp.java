package brainacad.ui;

import brainacad.model.*;
import brainacad.service.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.time.LocalDate;
import java.util.Scanner;

// ВАЖНАЯ ЗАМЕТКА!!! Писать позиции работников нужно на русском! (т.e Бариста Официант Кондитер)
// staff id начинается с 4
// я также с напитками тестировал удаление по этому там некоторые начинаются на 6 и 7
public class ConsoleApp
{

    private final DrinkService drinkService;
    private final StaffService staffService;
    private final ClientService clientService;
    private final OrderService orderService;
    private final DessertService dessertService;
    private final ScheduleService staffScheduleService;
    private final OrderItemService orderItemService;

    private final Scanner scanner = new Scanner(System.in);

    public ConsoleApp(Connection connection) {
        this.drinkService = new DrinkService(connection);
        this.staffService = new StaffService(connection);
        this.clientService = new ClientService(connection);
        this.orderService = new OrderService(connection);
        this.dessertService = new DessertService(connection);
        this.staffScheduleService = new ScheduleService(connection);
        this.orderItemService = new OrderItemService(connection);
    }

    public void start() {
        while (true) {
            System.out.println("""
                === CAFE MENU ===
                1. Add Drink
                2. Delete Drink
                3. Add Staff
                4. Delete Staff
                5. Add Client
                6. Delete Client
                7. Add Order
                8. Add Dessert
                9. Delete Dessert
                10. Add Staff Schedule
                11. Add Order Item
                0. Exit
                Choose an option:
            """);
            switch (scanner.nextLine()) {
                case "1" -> addDrink();
                case "2" -> deleteDrink();
                case "3" -> addStaff();
                case "4" -> deleteStaff();
                case "5" -> addClient();
                case "6" -> deleteClient();
                case "7" -> addOrder();
                case "8" -> addDessert();
                case "9" -> deleteDessert();
                case "10" -> addStaffSchedule();
                case "11" -> addOrderItem();
                case "0" -> {
                    System.out.println("Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid option, please try again.");
            }
        }
    }

    private void addDrink() {
        System.out.println("Enter drink name (English): ");
        String nameEn = scanner.nextLine();
        System.out.println("Enter drink name (local): ");
        String nameLocal = scanner.nextLine();
        System.out.println("Enter price: ");
        double price = Double.parseDouble(scanner.nextLine());

        try {
            drinkService.addDrink(new Drink(0, nameEn, nameLocal, price));
            System.out.println("Drink added successfully.");
        } catch (Exception e) {
            System.out.println("Error adding drink: " + e.getMessage());
        }
    }

    private void deleteDrink() {
        System.out.println("Enter drink ID to delete: ");
        int id = Integer.parseInt(scanner.nextLine());

        try {
            drinkService.deleteDrink(id);
            System.out.println("Drink deleted.");
        } catch (Exception e) {
            System.out.println("Error deleting drink: " + e.getMessage());
        }
    }

    private void addStaff() {
        System.out.println("Enter staff full name: ");
        String name = scanner.nextLine();
        System.out.println("Enter phone: ");
        String phone = scanner.nextLine();
        System.out.println("Enter email: ");
        String email = scanner.nextLine();
        System.out.println("Enter position (Barista, Waiter, Confectioner): ");
        String position = scanner.nextLine();

        try {
            staffService.addStaff(new Staff(0, name, phone, email, position));
            System.out.println("Staff member added.");
        } catch (Exception e) {
            System.out.println("Error adding staff: " + e.getMessage());
        }
    }

    private void deleteStaff() {
        System.out.println("Enter staff ID to delete: ");
        int id = Integer.parseInt(scanner.nextLine());

        try {
            staffService.deleteStaff(id);
            System.out.println("Staff member deleted.");
        } catch (Exception e) {
            System.out.println("Error deleting staff: " + e.getMessage());
        }
    }

    private void addClient() {
        System.out.println("Enter client full name: ");
        String name = scanner.nextLine();
        System.out.println("Enter birth date (YYYY-MM-DD): ");
        LocalDate birth = LocalDate.parse(scanner.nextLine());
        System.out.println("Enter phone: ");
        String phone = scanner.nextLine();
        System.out.println("Enter email: ");
        String email = scanner.nextLine();
        System.out.println("Enter discount (%): ");
        double discount = Double.parseDouble(scanner.nextLine());

        try {
            clientService.addClient(new Client(0, name, birth, phone, email, discount));
            System.out.println("Client added.");
        } catch (Exception e) {
            System.out.println("Error adding client: " + e.getMessage());
        }
    }

    private void deleteClient() {
        System.out.println("Enter client ID to delete: ");
        int id = Integer.parseInt(scanner.nextLine());

        try {
            clientService.deleteClient(id);
            System.out.println("Client deleted.");
        } catch (Exception e) {
            System.out.println("Error deleting client: " + e.getMessage());
        }
    }

    private void addOrder() {
        System.out.println("Enter client ID (or leave empty for NULL): ");
        String input = scanner.nextLine();
        Integer clientId = input.isBlank() ? null : Integer.parseInt(input);

        try {
            Order order = new Order(0, clientId, java.time.LocalDateTime.now());
            orderService.addOrder(order);
            System.out.println("Order added.");
        } catch (Exception e) {
            System.out.println("Error adding order: " + e.getMessage());
        }
    }

    private void addDessert() {
        System.out.println("Enter dessert name (English): ");
        String nameEn = scanner.nextLine();
        System.out.println("Enter dessert name (local): ");
        String nameLocal = scanner.nextLine();
        System.out.println("Enter price: ");
        double price = Double.parseDouble(scanner.nextLine());

        try {
            dessertService.addDessert(new Dessert(0, nameEn, nameLocal, price));
            System.out.println("Dessert added.");
        } catch (Exception e) {
            System.out.println("Error adding dessert: " + e.getMessage());
        }
    }

    private void deleteDessert() {
        System.out.println("Enter dessert ID to delete: ");
        int id = Integer.parseInt(scanner.nextLine());

        try {
            dessertService.deleteDessert(id);
            System.out.println("Dessert deleted.");
        } catch (Exception e) {
            System.out.println("Error deleting dessert: " + e.getMessage());
        }
    }

    private void addStaffSchedule() {
        System.out.println("Enter staff ID: ");
        int staffId = Integer.parseInt(scanner.nextLine());
        System.out.println("Enter work date (YYYY-MM-DD): ");
        LocalDate date = LocalDate.parse(scanner.nextLine());
        System.out.println("Enter shift (e.g., morning, evening): ");
        String shift = scanner.nextLine();

        try {
            staffScheduleService.addSchedule(new Schedule(0, staffId, date, shift));
            System.out.println("Schedule added.");
        } catch (Exception e) {
            System.out.println("Error adding schedule: " + e.getMessage());
        }
    }

    private void addOrderItem() {
        System.out.println("Enter order ID: ");
        int orderId = Integer.parseInt(scanner.nextLine());
        System.out.println("Item type (drink/dessert): ");
        String type = scanner.nextLine();
        System.out.println("Item ID: ");
        int itemId = Integer.parseInt(scanner.nextLine());
        System.out.println("Quantity: ");
        int quantity = Integer.parseInt(scanner.nextLine());

        try {
            orderItemService.addOrderItem(new OrderItem(0, orderId, type, itemId, quantity));
            System.out.println("Order item added.");
        } catch (Exception e) {
            System.out.println("Error adding order item: " + e.getMessage());
        }
    }

    public static void main(String[] args)
    {
        try (Connection connection = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/coffeehouse",
                "postgres", "123456"))
        {
            new ConsoleApp(connection).start();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}

