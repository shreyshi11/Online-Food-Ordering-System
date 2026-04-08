import java.util.*;

// ---------------- USER CLASS ----------------
class User {
    private String name;
    private String email;
    private String password;

    public void register(Scanner sc) {
        System.out.print("Enter Name: ");
        name = sc.nextLine();
        System.out.print("Enter Email: ");
        email = sc.nextLine();
        System.out.print("Enter Password: ");
        password = sc.nextLine();
        System.out.println("Registration Successful!\n");
    }

    public boolean login(Scanner sc) {
        System.out.print("Enter Email: ");
        String e = sc.nextLine();
        System.out.print("Enter Password: ");
        String p = sc.nextLine();

        if (e.equals(email) && p.equals(password)) {
            System.out.println("Login Successful!\n");
            return true;
        } else {
            System.out.println("Invalid Credentials!\n");
            return false;
        }
    }
}

// ---------------- FOOD ITEM CLASS ----------------
class FoodItem {
    int id;
    String name;
    double price;

    public FoodItem(int id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }
}

// ---------------- RESTAURANT CLASS ----------------
class Restaurant {
    String name;
    List<FoodItem> menu = new ArrayList<>();

    public Restaurant(String name) {
        this.name = name;
    }

    public void addItem(FoodItem item) {
        menu.add(item);
    }

    public void displayMenu() {
        System.out.println("\nMenu of " + name);
        for (FoodItem item : menu) {
            System.out.println(item.id + ". " + item.name + " - Rs." + item.price);
        }
    }
}

// ---------------- CART CLASS ----------------
class Cart {
    List<FoodItem> items = new ArrayList<>();

    public void addToCart(FoodItem item) {
        items.add(item);
        System.out.println(item.name + " added to cart!");
    }

    public void showCart() {
        if (items.isEmpty()) {
            System.out.println("Cart is empty!");
            return;
        }

        double total = 0;
        System.out.println("\nYour Cart:");
        for (FoodItem item : items) {
            System.out.println(item.name + " - Rs." + item.price);
            total += item.price;
        }
        System.out.println("Total Amount: Rs." + total);
    }

    public double getTotal() {
        double total = 0;
        for (FoodItem item : items) {
            total += item.price;
        }
        return total;
    }
}

// ---------------- PAYMENT CLASS ----------------
class Payment {
    public boolean processPayment(double amount, Scanner sc) {
        System.out.println("\nTotal Amount: Rs." + amount);
        System.out.println("1. UPI");
        System.out.println("2. Debit/Credit Card");
        System.out.println("3. Cash on Delivery");
        System.out.print("Select Payment Method: ");

        int choice = sc.nextInt();
        sc.nextLine(); // clear buffer

        if (choice >= 1 && choice <= 3) {
            System.out.println("Payment Successful!");
            return true;
        } else {
            System.out.println("Payment Failed!");
            return false;
        }
    }
}

// ---------------- ORDER CLASS ----------------
class Order {
    public void placeOrder(Cart cart) {
        if (cart.getTotal() == 0) {
            System.out.println("Cart is empty! Cannot place order.");
            return;
        }
        cart.showCart();
        System.out.println("Order Placed Successfully!");
    }

    public void trackOrder() {
        System.out.println("Order Status:");
        System.out.println("Preparing → Out for Delivery → Delivered");
    }
}

// ---------------- ADMIN CLASS ----------------
class Admin {
    public void manageUsers() {
        System.out.println("Admin managing users...");
    }

    public void manageRestaurants() {
        System.out.println("Admin managing restaurants...");
    }
}

// ---------------- MAIN CLASS ----------------
public class FoodOrderingSystem {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        User user = new User();
        Cart cart = new Cart();
        Order order = new Order();
        Payment payment = new Payment();

        // Restaurant Setup
        Restaurant restaurant = new Restaurant("Food Hub");
        restaurant.addItem(new FoodItem(1, "Pizza", 250));
        restaurant.addItem(new FoodItem(2, "Burger", 120));
        restaurant.addItem(new FoodItem(3, "Pasta", 180));

        // Registration & Login
        user.register(sc);
        if (!user.login(sc)) return;

        int choice;

        do {
            System.out.println("\n===== ONLINE FOOD ORDERING SYSTEM =====");
            System.out.println("1. View Menu");
            System.out.println("2. Add to Cart");
            System.out.println("3. View Cart");
            System.out.println("4. Place Order");
            System.out.println("5. Track Order");
            System.out.println("0. Exit");

            System.out.print("Enter choice: ");
            choice = sc.nextInt();
            sc.nextLine(); // clear buffer

            switch (choice) {
                case 1:
                    restaurant.displayMenu();
                    break;

                case 2:
                    restaurant.displayMenu();
                    System.out.print("Enter Item ID: ");
                    int id = sc.nextInt();
                    sc.nextLine();

                    for (FoodItem item : restaurant.menu) {
                        if (item.id == id) {
                            cart.addToCart(item);
                        }
                    }
                    break;

                case 3:
                    cart.showCart();
                    break;

                case 4:
                    order.placeOrder(cart);
                    if (payment.processPayment(cart.getTotal(), sc)) {
                        System.out.println("Order Confirmed!");
                    } else {
                        System.out.println("Payment Failed. Try again.");
                    }
                    break;

                case 5:
                    order.trackOrder();
                    break;

                case 0:
                    System.out.println("Thank you for using the system!");
                    break;

                default:
                    System.out.println("Invalid Choice!");
            }

        } while (choice != 0);

        sc.close();
    }
}