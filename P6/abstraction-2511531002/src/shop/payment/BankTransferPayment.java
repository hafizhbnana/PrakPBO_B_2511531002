package shop.payment;

import shop.StudentInfo;

public class BankTransferPayment implements PaymentMethod {
    private final String bankName;

    public BankTransferPayment(String bankName) {
        if(bankName == null || bankName.isBlank()) {
            throw new IllegalArgumentException ("Bank Name must not be blank");
        }
        this.bankName = bankName;
    }

    @Override
    public long adminFee(long amount) {
        return StudentInfo.TRANSFER_ADMIN_FEE;
    }

    @Override
    public String getName() {
        return "Transfer " + bankName;
    }

    public String getBankName() {
        return "Transfer " + bankName;
    }
    
    public boolean pay(long amount) {
        return amount > 0;
    }
}
