package shop.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Order {
    private final String orderId;
    private final Address shippingAddress;
    private final List<Line> lines = new ArrayList<>();

    public Order(String orderId, Address shippingAddress) {
        if (orderId == null || orderId.isBlank() || shippingAddress == null) {
            throw new IllegalArgumentException("Order ID and Address are required");
        }
        this.orderId = orderId;
        this.shippingAddress = shippingAddress;
    }

    public String getOrderId() { return orderId; }
    public Address getShippingAddress() { return shippingAddress; }

    public void addLine(Product product, int quantity) {
        lines.add(new Line(product, quantity));
    }

    public List<Line> getLines() { return Collections.unmodifiableList(lines); }

    public static class Line {
        private final Product product;
        private final int quantity;

        public Line(Product product, int quantity) {
            if (product == null || quantity <= 0) {
                throw new IllegalArgumentException("Product and quantity must be valid");
            }
            this.product = product;
            this.quantity = quantity;
        }

        public Product getProduct() { return product; }
        public int getQuantity() { return quantity; }
        public long subtotal() { return product.getFinalPrice() * quantity; }
    }

    public long getItemsTotal() {
        long total = 0;
        for (Line line : lines) {
            total += line.subtotal();
        }
        return total;
    }

    public class Receipt {
        public String toText() {
            StringBuilder receipt = new StringBuilder();
            receipt.append("\nSTRUK PESANAN ").append(Order.this.orderId).append("\n");
            
            for (Line line : lines) {
                receipt.append("- ")
                    .append(line.getProduct().getName())
                    .append(" x")
                    .append(line.getQuantity())
                    .append(" = Rp")
                    .append(line.subtotal())
                    .append("\n");
            }

            receipt.append("Total barang: Rp").append(getItemsTotal()).append("\n");
            receipt.append("Kirim ke: ").append(Order.this.shippingAddress);

            return receipt.toString();
        }
    }

    public Receipt createReceipt() { return new Receipt(); }
}
