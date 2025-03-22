class InsufficientBalanceException extends RuntimeException {
    public InsufficientBalanceException() {
        super("Insufficient balance for the transaction!");
    }

    public InsufficientBalanceException(String message) {
        super(message);
    }
}
