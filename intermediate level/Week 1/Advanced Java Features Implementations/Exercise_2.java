class BankAccount {
    private String accountNumber;
    private double balance;
    private TransactionHistory transactionHistory;

    public BankAccount(String accountNumber, double initialBalance) {
        this.accountNumber = accountNumber;
        this.balance = initialBalance;
        this.transactionHistory = new TransactionHistory();
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            transactionHistory.addTransaction("Deposit", amount);
            System.out.println("Deposited: $" + amount);
        }
    }

    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            transactionHistory.addTransaction("Withdrawal", amount);
            System.out.println("Withdrawn: $" + amount);
        } else {
            System.out.println("Insufficient funds");
        }
    }

    public void printTransactionHistory() {
        transactionHistory.printHistory();
    }

    // Inner class to encapsulate transaction data and functionality
    private class TransactionHistory {
        private java.util.List<String> transactions = new java.util.ArrayList<>();

        private void addTransaction(String type, double amount) {
            String transaction = String.format("%s: $%.2f at %s",
                    type,
                    amount,
                    java.time.LocalDateTime.now());
            transactions.add(transaction);
        }

        private void printHistory() {
            System.out.println("Transaction History for Account: " + accountNumber);
            for (String transaction : transactions) {
                System.out.println(transaction);
            }
        }
    }
}