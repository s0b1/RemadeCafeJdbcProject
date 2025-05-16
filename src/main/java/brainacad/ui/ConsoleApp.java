package brainacad.ui;

import brainacad.model.*;
import brainacad.service.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.time.LocalDate;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

// ВАЖНАЯ ЗАМЕТКА!!! Писать позиции работников нужно на русском! (т.e Бариста Официант Кондитер)
// staff id начинается с 4
// я также с напитками тестировал удаление по этому там некоторые начинаются на 6 и 7

@Component
public class ConsoleApp implements CommandLineRunner
{

    private final DrinkService drinkService;
    private final StaffService staffService;
    private final ClientService clientService;
    private final OrderService orderService;
    private final DessertService dessertService;
    private final ScheduleService staffScheduleService;
    private final OrderItemService orderItemService;

    private final Scanner scanner = new Scanner(System.in);

    @Autowired
    public ConsoleApp(
            DrinkService drinkService,
            StaffService staffService,
            ClientService clientService,
            OrderService orderService,
            DessertService dessertService,
            ScheduleService staffScheduleService,
            OrderItemService orderItemService
    ) {
        this.drinkService = drinkService;
        this.staffService = staffService;
        this.clientService = clientService;
        this.orderService = orderService;
        this.dessertService = dessertService;
        this.staffScheduleService = staffScheduleService;
        this.orderItemService = orderItemService;
    }

    @Override
    public void run(String... args) throws Exception {
        start();
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
                12. Add Coffee Order
                13. Add Dessert Order
                14. Add Schedule for Next Monday
                15. Update Schedule (Next Tuesday)
                16. Rename Drink
                17. Update Order Client
                18. Rename Dessert
                19. Delete Order by ID
                20. Delete Orders by Dessert ID
                21. Delete Schedule for Specific Day
                22. Delete Schedule Between Dates
                23. Show Orders with Specific Dessert
                24. Show Schedule for Specific Day
                25. Show Orders by Waiter
                26. Show Orders by Client
                27. Show Barista Week Schedule (by ID)
                28. Show All Baristas Week Schedule
                29. Show All Staff Week Schedule
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
                case "12" -> addCoffeeOrder();
                case "13" -> addDessertOrder();
                case "14" -> addScheduleForNextMonday();
                case "15" -> updateTuesdaySchedule();
                case "16" -> renameDrink();
                case "17" -> updateOrderClient();
                case "18" -> renameDessert();
                case "19" -> deleteOrderById();
                case "20" -> deleteOrdersByDessert();
                case "21" -> deleteScheduleByDay();
                case "22" -> deleteScheduleByRange();
                case "23" -> showOrdersWithDessert();
                case "24" -> showScheduleForDay();
                case "25" -> showOrdersByWaiter();
                case "26" -> showOrdersByClient();
                case "27" -> showBaristaWeekScheduleById();
                case "28" -> showAllBaristasWeekSchedule();
                case "29" -> showAllStaffWeekSchedule();
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

    //INSERT

    private void addCoffeeOrder() {
        System.out.println("Enter client ID (or leave blank for NULL): ");
        String input = scanner.nextLine();
        Integer clientId = input.isBlank() ? null : Integer.parseInt(input);

        System.out.println("Enter drink ID: ");
        int drinkId = Integer.parseInt(scanner.nextLine());

        System.out.println("Enter quantity: ");
        int quantity = Integer.parseInt(scanner.nextLine());

        try {
            Order order = new Order(0, clientId, java.time.LocalDateTime.now());
            orderService.addOrder(order);

            int orderId = getLastOrderId();
            orderItemService.addOrderItem(new OrderItem(0, orderId, "drink", drinkId, quantity));

            System.out.println("Coffee order added.");
        } catch (Exception e) {
            System.out.println("Error adding coffee order: " + e.getMessage());
        }
    }

    private void addDessertOrder() {
        System.out.println("Enter client ID (or leave blank for NULL): ");
        String input = scanner.nextLine();
        Integer clientId = input.isBlank() ? null : Integer.parseInt(input);

        System.out.println("Enter dessert ID: ");
        int dessertId = Integer.parseInt(scanner.nextLine());

        System.out.println("Enter quantity: ");
        int quantity = Integer.parseInt(scanner.nextLine());

        try {
            Order order = new Order(0, clientId, java.time.LocalDateTime.now());
            orderService.addOrder(order);

            int orderId = getLastOrderId();
            orderItemService.addOrderItem(new OrderItem(0, orderId, "dessert", dessertId, quantity));

            System.out.println("Dessert order added.");
        } catch (Exception e) {
            System.out.println("Error adding dessert order: " + e.getMessage());
        }
    }

    private void addScheduleForNextMonday() {
        System.out.println("Enter staff ID: ");
        int staffId = Integer.parseInt(scanner.nextLine());

        LocalDate today = LocalDate.now();
        LocalDate nextMonday = today.plusDays((8 - today.getDayOfWeek().getValue()) % 7);

        System.out.println("Enter shift (e.g., morning/evening): ");
        String shift = scanner.nextLine();

        try {
            staffScheduleService.addSchedule(new Schedule(0, staffId, nextMonday, shift));
            System.out.println("Schedule for next Monday added.");
        } catch (Exception e) {
            System.out.println("Error adding Monday schedule: " + e.getMessage());
        }
    }

    private int getLastOrderId() throws Exception {
        return orderService.getAllOrders()
                .stream()
                .mapToInt(Order::getId)
                .max()
                .orElseThrow(() -> new Exception("No orders found"));
    }


    //UPDATE

    private void updateTuesdaySchedule()
    {
        System.out.println("Enter staff ID: ");
        int staffId = Integer.parseInt(scanner.nextLine());

        LocalDate today = LocalDate.now();
        LocalDate nextTuesday = today.plusDays((9 - today.getDayOfWeek().getValue()) % 7);

        System.out.println("Enter new shift for next Tuesday: ");
        String newShift = scanner.nextLine();

        try {
            staffScheduleService.updateScheduleByDate(staffId, nextTuesday, newShift);
            System.out.println("Schedule updated.");
        } catch (Exception e) {
            System.out.println("Error updating schedule: " + e.getMessage());
        }
    }

    private void renameDrink() {
        System.out.println("Enter drink ID: ");
        int id = Integer.parseInt(scanner.nextLine());

        System.out.println("Enter new English name: ");
        String newNameEn = scanner.nextLine();

        System.out.println("Enter new local name: ");
        String newNameLocal = scanner.nextLine();

        try {
            drinkService.renameDrink(id, newNameEn, newNameLocal);
            System.out.println("Drink renamed.");          // pereimenoval Lychee Lemonade v Raspberry Lemonade
        } catch (Exception e) {
            System.out.println("Error renaming drink: " + e.getMessage());
        }
    }


    private void updateOrderClient() {
        System.out.println("Enter order ID: ");
        int orderId = Integer.parseInt(scanner.nextLine());

        System.out.println("Enter new client ID (or leave empty for NULL): ");
        String input = scanner.nextLine();
        Integer clientId = input.isBlank() ? null : Integer.parseInt(input);

        try {
            orderService.updateOrderClient(orderId, clientId);
            System.out.println("Order updated.");
        } catch (Exception e) {
            System.out.println("Error updating order: " + e.getMessage());
        }
    }

    private void renameDessert() {
        System.out.println("Enter dessert ID: ");
        int id = Integer.parseInt(scanner.nextLine());

        System.out.println("Enter new English name: ");
        String newNameEn = scanner.nextLine();

        System.out.println("Enter new local name: ");
        String newNameLocal = scanner.nextLine();

        try {
            dessertService.renameDessert(id, newNameEn, newNameLocal);
            System.out.println("Dessert renamed.");
        } catch (Exception e) {
            System.out.println("Error renaming dessert: " + e.getMessage());
        }
    }

    //DELETE

    private void deleteOrderById() {
        System.out.println("Enter order ID to delete: ");
        int id = Integer.parseInt(scanner.nextLine());

        try {
            orderService.deleteOrder(id);
            System.out.println("Order deleted.");
        } catch (Exception e) {
            System.out.println("Error deleting order: " + e.getMessage());
        }
    }

    private void deleteOrdersByDessert() {
        System.out.println("Enter dessert ID: ");
        int dessertId = Integer.parseInt(scanner.nextLine());

        try {
            orderItemService.deleteOrdersByDessert(dessertId);
            System.out.println("Orders containing this dessert deleted.");
        } catch (Exception e) {
            System.out.println("Error deleting orders by dessert: " + e.getMessage());
        }
    }

    private void deleteScheduleByDay() {
        System.out.println("Enter work date (YYYY-MM-DD): ");
        LocalDate date = LocalDate.parse(scanner.nextLine());

        try {
            staffScheduleService.deleteScheduleByDay(date);
            System.out.println("Schedule for the day deleted.");
        } catch (Exception e) {
            System.out.println("Error deleting schedule: " + e.getMessage());
        }
    }

    private void deleteScheduleByRange() {
        System.out.println("Enter start date (YYYY-MM-DD): ");
        LocalDate start = LocalDate.parse(scanner.nextLine());
        System.out.println("Enter end date (YYYY-MM-DD): ");
        LocalDate end = LocalDate.parse(scanner.nextLine());

        try {
            staffScheduleService.deleteScheduleByRange(start, end);
            System.out.println("Schedules between dates deleted.");
        } catch (Exception e) {
            System.out.println("Error deleting schedules: " + e.getMessage());
        }
    }

    //SELECT

    private void showOrdersWithDessert()
    {
        System.out.println("Enter dessert ID: ");
        int dessertId = Integer.parseInt(scanner.nextLine());

        try {
            var orders = orderItemService.findOrdersByDessert(dessertId);
            if (orders.isEmpty()) {
                System.out.println("No orders found with this dessert.");
            } else {
                orders.forEach(System.out::println);
            }
        } catch (Exception e) {
            System.out.println("Error retrieving orders: " + e.getMessage());
        }
    }

    private void showScheduleForDay() {
        System.out.println("Enter date (YYYY-MM-DD): ");
        LocalDate date = LocalDate.parse(scanner.nextLine());

        try {
            var schedules = staffScheduleService.getScheduleForDay(date);
            if (schedules.isEmpty()) {
                System.out.println("No schedules found.");
            } else {
                schedules.forEach(System.out::println);
            }
        } catch (Exception e) {
            System.out.println("Error retrieving schedule: " + e.getMessage());
        }
    }

    private void showOrdersByWaiter() {
        System.out.println("Enter waiter ID: ");
        int staffId = Integer.parseInt(scanner.nextLine());

        try {
            var orders = orderItemService.findOrdersByWaiter(staffId);
            if (orders.isEmpty()) {
                System.out.println("No orders found for this waiter.");
            } else {
                orders.forEach(System.out::println);
            }
        } catch (Exception e) {
            System.out.println("Error retrieving orders: " + e.getMessage());
        }
    }

    private void showOrdersByClient() {
        System.out.println("Enter client ID: ");
        int clientId = Integer.parseInt(scanner.nextLine());

        try {
            var orders = orderService.getOrdersByClient(clientId);
            if (orders.isEmpty()) {
                System.out.println("No orders found for this client.");
            } else {
                orders.forEach(System.out::println);
            }
        } catch (Exception e) {
            System.out.println("Error retrieving orders: " + e.getMessage());
        }
    }

    private void showBaristaWeekScheduleById() {
        System.out.println("Enter Barista ID: ");
        int id = Integer.parseInt(scanner.nextLine());
        System.out.println("Enter week start date (YYYY-MM-DD): ");
        LocalDate start = LocalDate.parse(scanner.nextLine());

        try {
            var list = staffScheduleService.getWeekScheduleForBarista(id, start);
            if (list.isEmpty()) {
                System.out.println("No schedule found.");
            } else {
                list.forEach(System.out::println);
            }
        } catch (Exception e) {
            System.out.println("Error retrieving barista schedule: " + e.getMessage());
        }
    }

    private void showAllBaristasWeekSchedule() {
        System.out.println("Enter week start date (YYYY-MM-DD): ");
        LocalDate start = LocalDate.parse(scanner.nextLine());
        try {
            var list = staffScheduleService.getWeekScheduleForAllBaristas(start);
            if (list.isEmpty()) {
                System.out.println("No schedule found.");
            } else {
                list.forEach(System.out::println);
            }
        } catch (Exception e) {
            System.out.println("Error retrieving schedule: " + e.getMessage());
        }
    }

    private void showAllStaffWeekSchedule() {
        System.out.println("Enter week start date (YYYY-MM-DD): ");
        LocalDate start = LocalDate.parse(scanner.nextLine());
        try {
            var list = staffScheduleService.getWeekScheduleForAllStaff(start);
            if (list.isEmpty()) {
                System.out.println("No schedule found.");
            } else {
                list.forEach(System.out::println);
            }
        } catch (Exception e) {
            System.out.println("Error retrieving schedule: " + e.getMessage());
        }
    }


}

