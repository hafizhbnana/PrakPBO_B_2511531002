package shop.payment;

public abstract class BankTransferPayment implements PaymentMethod {
    private final String bankName;

    protected BankTransferPayment(String bankName) {
        if(bankName == null || bankName.isBlank()) {
            throw new IllegalArgumentException ("Bank Name must not be blank");
        }
        this.bankName = bankName;
    }

    public String getBankName() {
        return "Transfer " + bankName;
    }
    
    public boolean pay(long amount) {
        return amount > 0;
    }
}
