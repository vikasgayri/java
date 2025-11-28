import java.util.*;

class Account {
    private String username;
    private String password;
    private double balance;
    private List<String> history;

    public Account(String username, String password) {
        this.username = username;
        this.password = password;
        this.balance = 0.0;
        this.history = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public boolean checkPassword(String inputPassword) {
        return this.password.equals(inputPassword);
    }

    public void deposit(double amount) {
        balance += amount;
        history.add("Deposited $" + amount);
        System.out.println("✅ $" + amount + " deposited successfully.");
    }

    public void withdraw(double amount) {
        if (amount > balance) {
            System.out.println("❌ Insufficient funds.");
        } else {
            balance -= amount;
            history.add("Withdrew $" + amount);
            System.out.println("✅ $" + amount + " withdrawn successfully.");
        }
    }

    public void transfer(Account target, double amount) {
        if (amount > balance) {
            System.out.println("❌ Insufficient funds.");
        } else {
            balance -= amount;
            target.balance += amount;
            history.add("Transferred $" + amount + " to " + target.getUsername());
            target.history.add("Received $" + amount + " from " + this.username);
            System.out.println("✅ Transferred $" + amount + " to " + target.getUsername());
        }
    }

    public void showBalance() {
        System.out.printf("💰 Current Balance: $%.2f%n", balance);
    }

    public void showHistory() {
        System.out.println("📜 Transaction History:");
        for (String record : history) {
            System.out.println(" - " + record);
        }
    }
}

class BankSystem {
    private Map<String, Account> accounts;
    private Scanner scanner;

    public BankSystem() {
        accounts = new HashMap<>();
        scanner = new Scanner(System.in);
    }

    public void createAccount() {
        System.out.print("Enter new username: ");
        String username = scanner.nextLine();

        if (accounts.containsKey(username)) {
            System.out.println("❌ Account already exists.");
            return;
        }

        System.out.print("Enter new password: ");
        String password = scanner.nextLine();

        accounts.put(username, new Account(username, password));
        System.out.println("✅ Account created successfully.");
    }

    public void login() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        Account account = accounts.get(username);

        if (account != null && account.checkPassword(password)) {
            System.out.println("🔓 Welcome back, " + username + "!");
            userMenu(account);
        } else {
            System.out.println("❌ Invalid credentials.");
        }
    }

    private void userMenu(Account account) {
        while (true) {
            System.out.println("\n--- 🏦 Banking Menu ---");
            System.out.println("1. Check Balance");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Transfer");
            System.out.println("5. Transaction History");
            System.out.println("6. Logout");

            System.out.print("Choose an option: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    account.showBalance();
                    break;
                case "2":
                    System.out.print("Enter deposit amount: ");
                    double deposit = Double.parseDouble(scanner.nextLine());
                    account.deposit(deposit);
                    break;
                case "3":
                    System.out.print("Enter withdrawal amount: ");
                    double withdraw = Double.parseDouble(scanner.nextLine());
                    account.withdraw(withdraw);
                    break;
                case "4":
                    System.out.print("Enter recipient username: ");
                    String targetUser = scanner.nextLine();
                    Account target = accounts.get(targetUser);
                    if (target != null) {
                        System.out.print("Enter amount to transfer: ");
                        double amount = Double.parseDouble(scanner.nextLine());
                        account.transfer(target, amount);
                    } else {
                        System.out.println("❌ Target account does not exist.");
                    }
                    break;
                case "5":
                    account.showHistory();
                    break;
                case "6":
                    System.out.println("🔒 Logged out.");
                    return;
                default:
                    System.out.println("❌ Invalid choice.");
            }
        }
    }

    public void start() {
        while (true) {
            System.out.println("\n===  Welcome to Java Bank ===");
            System.out.println("1. Create Account");
            System.out.println("2. Login");
            System.out.println("3. Exit");

            System.out.print("Select an option: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    createAccount();
                    break;
                case "2":
                    login();
                    break;
                case "3":
                    System.out.println("👋 Thank you for using Java Bank. Goodbye!");
                    return;
                default:
                    System.out.println("❌ Invalid option.");
            }
        }
    }
}

public class BankingSystem {
    public static void main(String[] args) {
        BankSystem bank = new BankSystem();
        bank.start();
    }
}
