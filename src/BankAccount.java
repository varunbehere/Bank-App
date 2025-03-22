import java.util.Random;

abstract public class BankAccount {
    protected String accountNumber;
    protected String accountHolder;
    protected double balance;
    protected int TRANSACTION_LIMIT;

    BankAccount(String accountHolder, double currentBalance, int transactionLimit) {
        this.accountHolder = accountHolder;
        this.accountNumber = generateAccountNumber();
        this.TRANSACTION_LIMIT = transactionLimit;

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

    protected void validateTransaction(double amount, String transactionType) {
        if (amount <= 0) {
            throw new IllegalArgumentException(transactionType + " amount should be greater than 0.");
        }
        else if (amount > this.TRANSACTION_LIMIT) {
            throw new IllegalArgumentException(transactionType + " limit exceeded! Maximum amount allowed: INR " + this.TRANSACTION_LIMIT);
        }
    }

    protected void deposit(double amount){
        validateTransaction(amount, "Deposit");

        this.balance += amount;
        System.out.println("INR " + amount + " deposited successfully!");
    }

    abstract protected void withdraw (double amount);
}

class SavingsAccount extends BankAccount {
    private static final float INTEREST_RATE = 3.0f;
    private static final int TRANSACTION_LIMIT = 10_000;

    SavingsAccount(String accountHolder, double balance) {
        super(accountHolder, balance, TRANSACTION_LIMIT);
    }

    @Override
    public void withdraw(double amount){
        validateTransaction(amount, "Withdraw");

        if (amount > this.balance){
            throw new InsufficientBalanceException();
        }
        else {
            this.balance -= amount;
            System.out.println("INR " + amount + " withdrawn successfully!");
        }
    }
}

class CurrentAccount extends BankAccount {
    private static final int TRANSACTION_LIMIT = 50_000;
    private static final int OVERDRAFT_LIMIT = -20_000;

    CurrentAccount(String accountHolder, double balance) {
        super(accountHolder, balance, TRANSACTION_LIMIT);
    }

    @Override
    protected void withdraw(double amount){
        validateTransaction(amount, "Withdraw");

        if (amount > this.balance){
            throw new InsufficientBalanceException();
        }
        else if (this.balance - amount < OVERDRAFT_LIMIT){
            throw new IllegalArgumentException("Overdraft Limit Exceeded! Transaction Declined!");
        }
        else {
            this.balance -= amount;
            System.out.println("INR " + amount + " withdrawn successfully!");
        }
    }
}