package shop.app;

import java.util.List;
import shop.StudentInfo;
import shop.model.DigitalProduct;
import shop.model.PhysicalProduct;
import shop.model.Product;
import shop.notification.CustomerContact;
import shop.notification.EmailChannel;
import shop.notification.NotificationChannel;
import shop.notification.NotificationService;
import shop.notification.SmsChannel;
import shop.notification.WhatsAppChannel;
import shop.payment.BankTransferPayment;
import shop.payment.CreditCardPayment;
import shop.payment.EWalletPayment;
import shop.service.CheckoutService;
import shop.shipping.ExpressShipping;

public class TugasApp {
    public static void main(String[] args) {
        Product keyboard = new PhysicalProduct("P001", "Keyboard", 160000, 500);
        Product ebook = new DigitalProduct("D001", "Java OOP", 100000, "https://example.com/ebook");

        List<Product> cart = List.of(keyboard, ebook);

        CheckoutService checkout = new CheckoutService();
        ExpressShipping shipping = new ExpressShipping();
        EWalletPayment ewallet = new EWalletPayment("DompetKu", 500000);
        BankTransferPayment transfer = new BankTransferPayment("Bank Nagari");
        CreditCardPayment creditCard = new CreditCardPayment("Hafizh Habibullah", 1000000);
        
        // t1
        System.out.println("\nTugas oleh " + StudentInfo.NAME + " (" + StudentInfo.NIM + ")\n");
        System.out.println("== T1: Total dengan biaya admin (Ekspres) ==");

        long ewalletTotal = checkout.grandTotal(cart, shipping, ewallet);
        long transferTotal = checkout.grandTotal(cart, shipping, transfer);
        long cardTotal = checkout.grandTotal(cart, shipping, creditCard);

        System.out.println(ewallet.getName() + " : Rp" + ewalletTotal
            + " (admin Rp" + ewallet.adminFee(checkout.total(cart, shipping)) + ")");
        System.out.println(transfer.getName() + " : Rp" + transferTotal
            + " (admin Rp" + transfer.adminFee(checkout.total(cart, shipping)) + ")");
        System.out.println(creditCard.getName() + " : Rp" + cardTotal
            + " (admin Rp" + creditCard.adminFee(checkout.total(cart, shipping)) + ")");
        
        // policy
        System.out.println();
        System.out.println("Kebijakan kartu kredit : " + creditCard.policy());
        System.out.println("Kebijakan e-wallet : " + ewallet.policy());

        // notif
        NotificationService notificationService = new NotificationService();
        notificationService.register(new EmailChannel());
        notificationService.register(new SmsChannel(StudentInfo.SMS_MAX_LENGTH));
        notificationService.register(new WhatsAppChannel());
        notificationService.register(new NotificationChannel() {
            @Override
            public String channelName() { return "Console Log"; }

            @Override 
            public boolean send(CustomerContact contact, String message) {
                System.out.println("[LOG] " + contact.getName() + ": " + message);
                return true;
            }
        });

        CustomerContact siti = new CustomerContact(
            "Siti Rahma", "siti@example.com", "081234567890");
        CustomerContact budi = new CustomerContact(
            "Budi", "", "+6281298765432");

        String message = "Pesanan ORD-0001 sudah dikirim dengan kurir Ekspres. "
            + "Nomor resi akan muncul di aplikasi dalam 1x24 jam. "
            + "Terima kasih telah berbelanja produk UMKM Sumatera Barat di toko kami! "
            + "Kami berharap produk yang diterima sesuai dengan harapan Anda.";

        // t2
        System.out.println("== T2: Notifikasi status pesanan ==");

        int sentToSiti = notificationService.broadcast(siti, message);
        System.out.println("Terkirim ke Siti lewat " + sentToSiti + " kanal");
        int sentToBudi = notificationService.broadcast(budi, message);
        System.out.println("Terkirim ke Budi lewat " + sentToBudi + " kanal");

        
    }
}
