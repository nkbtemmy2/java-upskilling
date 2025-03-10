import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class ExceptionHandlingDemo {

    // Method that throws a checked exception (IOException)
    public static void readFile(String filename) throws IOException {
        File file = new File(filename);
        Scanner scanner = new Scanner(file);
        try {
            while (scanner.hasNextLine()) {
                System.out.println(scanner.nextLine());
            }
        } finally {
            scanner.close();
        }
    }

    // Method that throws an unchecked exception (ArithmeticException)
    public static int divide(int a, int b) {
        return a / b; // This will throw ArithmeticException if b is 0
    }

    // Method that demonstrates handling both checked and unchecked exceptions
    public static void demonstrateExceptionHandling() {
        // Handling checked exception
        try {
            System.out.println("Attempting to read file...");
            readFile("nonexistent.txt");
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IO error occurred: " + e.getMessage());
        }

        // Handling unchecked exception
        try {
            System.out.println("Attempting division...");
            int result = divide(10, 0);
            System.out.println("Result: " + result); // This line won't execute if division by zero
        } catch (ArithmeticException e) {
            System.out.println("Arithmetic error: " + e.getMessage());
        }
    }

    // Exercise 2: Utilizing Nested Try-Catch Blocks
    public static void demonstrateNestedTryCatch() {
        try {
            System.out.println("Outer try block");
            try {
                System.out.println("Inner try block - Division operation");
                int result = 10 / 0; // Will throw ArithmeticException
            } catch (ArithmeticException e) {
                System.out.println("Inner catch block - ArithmeticException caught: " + e.getMessage());

                // Throw a new exception from inner catch
                throw new IllegalStateException("Exception thrown from inner catch", e);
            }
        } catch (IllegalStateException e) {
            System.out.println("Outer catch block - IllegalStateException caught: " + e.getMessage());
            System.out.println("Caused by: " + e.getCause());
        } finally {
            System.out.println("Outer finally block - Always executes");
        }
    }

    // Exercise 3: Releasing Resources with finally Block
    public static void readFileWithResourceHandling(String filename) {
        Scanner scanner = null;
        try {
            File file = new File(filename);
            scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                System.out.println(line);

                // Simulate an exception during processing
                if (line.contains("error")) {
                    throw new RuntimeException("Error found in file content");
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        } catch (RuntimeException e) {
            System.out.println("Error processing file: " + e.getMessage());
        } finally {
            // Close the scanner in the finally block to ensure resource release
            System.out.println("Cleaning up resources...");
            if (scanner != null) {
                scanner.close();
                System.out.println("Scanner closed successfully");
            }
        }
    }

    // Using try-with-resources (more modern approach for resource management)
    public static void readFileWithTryWithResources(String filename) {
        try (Scanner scanner = new Scanner(new File(filename))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                System.out.println(line);

                if (line.contains("error")) {
                    throw new RuntimeException("Error found in file content");
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        } catch (RuntimeException e) {
            System.out.println("Error processing file: " + e.getMessage());
        }
        // No finally needed for resource cleanup with try-with-resources
    }

    // Exercise 4: Creating a Custom Exception
    // Custom exception class
    public static class InsufficientFundsException extends Exception {
        private double amount;

        public InsufficientFundsException(double amount, double balance) {
            super(String.format("Insufficient funds: Attempted to withdraw $%.2f but balance is $%.2f",
                    amount, balance));
            this.amount = amount;
        }

        public double getAmount() {
            return amount;
        }
    }

    // BankAccount class that uses the custom exception
    public static class BankAccount {
        private double balance;
        private String accountId;

        public BankAccount(String accountId, double initialBalance) {
            this.accountId = accountId;
            this.balance = initialBalance;
        }

        public void deposit(double amount) {
            if (amount <= 0) {
                throw new IllegalArgumentException("Deposit amount must be positive");
            }
            balance += amount;
            System.out.printf("Deposited $%.2f to account %s. New balance: $%.2f%n",
                    amount, accountId, balance);
        }

        public void withdraw(double amount) throws InsufficientFundsException {
            if (amount <= 0) {
                throw new IllegalArgumentException("Withdrawal amount must be positive");
            }

            if (amount > balance) {
                throw new InsufficientFundsException(amount, balance);
            }

            balance -= amount;
            System.out.printf("Withdrew $%.2f from account %s. New balance: $%.2f%n",
                    amount, accountId, balance);
        }

        public double getBalance() {
            return balance;
        }
    }

    // Demonstrate using the custom exception
    public static void demonstrateCustomException() {
        BankAccount account = new BankAccount("ACC123456", 1000);

        try {
            System.out.println("Current balance: $" + account.getBalance());
            account.withdraw(500);
            account.deposit(200);
            account.withdraw(800); // This should cause InsufficientFundsException
        } catch (InsufficientFundsException e) {
            System.out.println("Error: " + e.getMessage());
            System.out.println("Attempted withdrawal amount: $" + e.getAmount());
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid operation: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        System.out.println("=== Exercise 1: Handling Checked and Unchecked Exceptions ===");
        demonstrateExceptionHandling();

        System.out.println("\n=== Exercise 2: Utilizing Nested Try-Catch Blocks ===");
        demonstrateNestedTryCatch();

        System.out.println("\n=== Exercise 3: Releasing Resources with finally Block ===");
        // Create a test file for demonstration
        try {
            java.io.PrintWriter writer = new java.io.PrintWriter("test.txt");
            writer.println("This is a test file");
            writer.println("This line contains an error marker");
            writer.close();

            readFileWithResourceHandling("test.txt");
            System.out.println("\nUsing try-with-resources:");
            readFileWithTryWithResources("test.txt");
        } catch (FileNotFoundException e) {
            System.out.println("Error creating test file: " + e.getMessage());
        }

        System.out.println("\n=== Exercise 4: Creating a Custom Exception ===");
        demonstrateCustomException();
    }
}