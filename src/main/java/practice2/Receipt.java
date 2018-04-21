package practice2;

import java.math.BigDecimal;
import java.util.List;

public class Receipt {

    private static final int SCALE_TWO = 2;

    public Receipt() {
        tax = new BigDecimal(0.1);
        tax = tax.setScale(SCALE_TWO, BigDecimal.ROUND_HALF_UP);
    }

    private BigDecimal tax;

    public double CalculateGrandTotal(List<Product> products, List<OrderItem> items) {
        BigDecimal subTotal = calculateSubtotal(products, items);

        BigDecimal discountedTotal = getDiscountedTotal(products, items, subTotal);

        BigDecimal taxPaidTotal = getTaxPaidTotal(discountedTotal);

        return getFormattedGrandTotal(taxPaidTotal);
    }

    private BigDecimal getDiscountedTotal(List<Product> products, List<OrderItem> items, BigDecimal subTotal) {
        for (Product product : products) {
            OrderItem curItem = findOrderItemByProduct(items, product);

            BigDecimal reducedPrice = reducePriceByDiscount(product, curItem);

            subTotal = subTotal.subtract(reducedPrice);
        }
        return subTotal;
    }

    private BigDecimal reducePriceByDiscount(Product product, OrderItem curItem) {
        return product.getPrice()
                .multiply(product.getDiscountRate())
                .multiply(new BigDecimal(curItem.getCount()));
    }

    private double getFormattedGrandTotal(BigDecimal grandTotal) {
        return grandTotal.setScale(SCALE_TWO, BigDecimal.ROUND_HALF_UP)
                .doubleValue();
    }

    private BigDecimal getTaxPaidTotal(BigDecimal total) {
        BigDecimal taxTotal = total.multiply(tax);
        return total.add(taxTotal);
    }


    private OrderItem findOrderItemByProduct(List<OrderItem> items, Product product) {
        OrderItem curItem = null;
        for (OrderItem item : items) {
            if (item.getCode() == product.getCode()) {
                curItem = item;
                break;
            }
        }
        return curItem;
    }

    private BigDecimal calculateSubtotal(List<Product> products, List<OrderItem> items) {
        BigDecimal subTotal = new BigDecimal(0);
        for (Product product : products) {
            OrderItem item = findOrderItemByProduct(items, product);
            BigDecimal itemTotal = product.getPrice().multiply(new BigDecimal(item.getCount()));
            subTotal = subTotal.add(itemTotal);
        }
        return subTotal;
    }
}
