package practice3;

import java.math.BigDecimal;

public class CalculateGrandTotal {

    private final Order order;

    public CalculateGrandTotal(Order order) {
        this.order = order;
    }

    public BigDecimal compute() {
        BigDecimal subTotal = calculateSubTotal();

        // Subtract discounts
        for (BigDecimal discount : this.order.getDiscounts()) {
            subTotal = subTotal.subtract(discount);
        }

        // calculate tax
        BigDecimal tax = subTotal.multiply(this.order.getTax());

        // calculate GrandTotal
        BigDecimal grandTotal = subTotal.add(tax);

        return grandTotal;
    }

    private BigDecimal calculateSubTotal() {
        BigDecimal subTotal = new BigDecimal(0);

        for (OrderLineItem lineItem : this.order.getOrderLineItemList()) {
            subTotal = subTotal.add(lineItem.getPrice());
        }

        return subTotal;
    }
}
