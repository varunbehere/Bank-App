import java.util.Random;

abstract public class BankAccount {
    protected String accountNumber;
    protected String accountHolder;
    protected double balance;

    BankAccount(String accountHolder, double currentBalance) {
        this.accountHolder = accountHolder;
        this.accountNumber = generateAccountNumber();

        if (currentBalance < 0) {
            this.balance = 0;
            System.out.println("Invalid balance, setting balance to INR 0.");
        } else {
            this.balance = currentBalance;
        }
    }

    public double getBalance() {
        return balance;
    }

    private String generateAccountNumber() {
        return "BXYZ" + String.format("%04d", new Random().nextInt(10000));
    }

    protected void displayAccountDetails (){
        System.out.println("______________________________________________________");
        System.out.println("Account Holder : " + this.accountHolder);
        System.out.println("Account Number : " + this.accountNumber);
        System.out.println("Balance : " + this.balance);
        System.out.println("______________________________________________________");
    }

    public void displayBalance() {
        System.out.println("Current A/C balance: INR " + this.balance);
    }

    abstract public void withdraw(double amount);
    abstract public void deposit(double amount);
}

class SavingsAccount extends BankAccount {
    private static final float INTEREST_RATE = 3.0f;
    private static final int TRANSACTION_LIMIT = 10_000;

    SavingsAccount(String accountHolder, double balance) {
        super(accountHolder, balance);
    }

    @Override
    public void withdraw(double amount) {
        if (amount > this.balance) {
            System.out.println("Insufficient Balance!");
        } else if (amount > TRANSACTION_LIMIT) {
            System.out.println("Withdraw amount exceeded the limit.");
            System.out.println("Withdraw limit: INR " + TRANSACTION_LIMIT);
        } else {
            this.balance -= amount;
            System.out.println("INR " + amount + " withdrawn successfully!");
        }
    }

    @Override
    public void deposit(double amount) {
        if (amount <= 0) {
            System.out.println("The amount should be greater than INR 1");
        } else if (amount > TRANSACTION_LIMIT) {
            System.out.println("The deposit amount exceeds the limit!");
            System.out.println("Deposit limit: INR " + TRANSACTION_LIMIT);
        } else {
            this.balance += amount;
            System.out.println("INR " + amount + " deposited successfully!");
        }
    }
}

// CurrentAccount
class CurrentAccount extends BankAccount {
    private static final int TRANSACTION_LIMIT = 50_000;
    private static final int OVERDRAFT_LIMIT = -20_000;

    CurrentAccount(String accountHolder, double balance) {
        super(accountHolder, balance);
    }

    @Override
    public void withdraw(double amount) {
        if (this.balance - amount < OVERDRAFT_LIMIT) {
            System.out.println("Overdraft limit exceeded! Transaction declined.");
        } else if (amount > TRANSACTION_LIMIT) {
            System.out.println("Withdraw amount exceeded the limit: INR " + TRANSACTION_LIMIT);
        } else {
            this.balance -= amount;
            System.out.println("INR " + amount + " withdrawn successfully!");
        }
    }

    @Override
    public void deposit(double amount) {
        if (amount <= 0) {
            System.out.println("The amount should be greater than INR 1.");
        } else if (amount > TRANSACTION_LIMIT) {
            System.out.println("Deposit amount exceeds the limit: INR " + TRANSACTION_LIMIT);
        } else {
            this.balance += amount;
            System.out.println("INR " + amount + " deposited successfully!");
        }
    }
}