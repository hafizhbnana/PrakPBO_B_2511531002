package review.fixed;

import java.util.ArrayList;
import java.util.List;

interface Payable { boolean pay(long amount); }
interface Refundable { boolean refund(long amount); }
interface Shippable { long ship(int weightGrams); }
interface InvoicePrintable { String printInvoice(); }

class GiftVoucher implements Payable, InvoicePrintable {
    private long balance = 100_000;

    @Override
    public boolean pay(long amount) {
        if (amount <= 0 || amount > balance) { return false; }
        balance -= amount;
        return true;
    }

    @Override
    public String printInvoice() { return "sisa Rp" + balance; }
}

abstract class Courier implements Shippable {
    protected abstract long ratePerKg();

    @Override 
    public final long ship(int weightGrams) {
        if (weightGrams <= 0) {
            throw new IllegalArgumentException("Berat harus positif");
        }
        int kilograms = (weightGrams + 999) / 1000;
        return kilograms * ratePerKg();
    }
}

class RegularCourier extends Courier {
    @Override 
    protected long ratePerKg() { return 10_000; }
}
class ExpressCourier extends Courier {
    @Override
    protected long ratePerKg() { return 18_000; }
}
class SameDayCourier extends Courier {
    @Override 
    protected long ratePerKg() { return 30_000; }
}

class Cart {
    private final List<Item> items = new ArrayList<>();
    static class Item {
        private final String name;
        private final long price;
        private final int quantity;

        Item(String name, long price, int quantity) {
            this.name = name;
            this.price = price;
            this.quantity = quantity;
        }
        long subtotal() { return price * quantity; }
        String getName() { return name; }
    }

    void add(Item item) { items.add(item); }
    
    long total() {
        long total = 0;
        for (Item item : items) {
            total += item.subtotal();
        }
        return total;
    }
}

final class Address {
    private final String street;
    private final String city;

    Address(String street, String city) {
        this.street = street;
        this.city = city;
    }

    @Override
    public String toString() {
        return street + ", " + city;
    }
}

class SimpleOrder {
    private final String id;
    private final Address address;

    SimpleOrder(String id, Address address) {
        this.id = id;
        this.address = address;
    }

    @Override 
    public String toString() {
        return id + " -> " + address;
    }

}

interface Discount { long apply(long price); }

class MemberDiscount implements Discount {
    @Override
    public long apply(long price) {
        return price - price * 5 / 100;
    }
}

class FlashSaleDiscount implements Discount {
    @Override 
    public long apply(long price) {
        return price - price * 30 / 100;
    }
}

public class BadShopRefactored {
    public static void main(String[] args) {
        Cart cart = new Cart();
        cart.add(new Cart.Item("Kopi Gayo", 85_000, 2));

        System.out.println("Total cart: " + cart.total());

        Courier courier = new ExpressCourier();
        System.out.println("Ongkir Ekspres 2 kg: " + courier.ship(2000));

        Address padang = new Address("Jl. Sudirman 10", "Padang");
        Address bukittinggi = new Address("Jl. Sudirman 10", "Bukittinggi");

        SimpleOrder first = new SimpleOrder("ORD-1", padang);
        SimpleOrder second = new SimpleOrder("ORD-2", bukittinggi);

        System.out.println(first);
        System.out.println(second);

        Discount flashSale = new FlashSaleDiscount();
        System.out.println("Harga flash sale: " + flashSale.apply(100_000));

        GiftVoucher voucher = new GiftVoucher();
        System.out.println(
            voucher.pay(30_000) + " | " + voucher.printInvoice()
        );
    }
}
