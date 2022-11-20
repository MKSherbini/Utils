package skull.shopping.model;

import lombok.*;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public abstract class Product implements Comparable<Product> {
    protected boolean isSponsored;
    protected boolean isFullfilled;
    protected int discount;
    protected int shippingPrice;
    protected float price;
    protected float oldPrice;
    protected String itemName;
    protected String url;
    protected String imageUrl;
    protected List<String> imageUrls;
    protected String sellerName;

    public static int maxShippingPrice;
    public static int minDiscount;
    public static SearchRelation searchRelation;
    public static int minPrice;
    public static int maxPrice;

    @Override
    public int compareTo(Product o) {
        if (discount != o.discount)
            return discount - o.discount;
        if (oldPrice != o.oldPrice)
            return Float.compare(oldPrice, o.oldPrice);
        if (price != o.price)
            return Float.compare(price, o.price);
        if (shippingPrice != o.shippingPrice)
            return o.shippingPrice - shippingPrice;
        if (!itemName.equals(o.itemName))
            return itemName.compareTo(o.itemName);
        return url.compareTo(o.url);
    }

    public boolean isValuable() {
        if (minPrice != -1 && price < minPrice) return false;
        if (maxPrice != -1 && price > maxPrice) return false;

        switch (searchRelation) {
            case EITHER:
                return (discount >= minDiscount || shippingPrice <= maxShippingPrice);
            case BOTH:
                return (discount >= minDiscount && shippingPrice <= maxShippingPrice);
            case DISCOUNT:
                return (discount >= minDiscount);
            case SHIPPING:
                return (shippingPrice <= maxShippingPrice);
            default:
                return false;
        }
    }

    public boolean goodShipping() {
        return (shippingPrice <= 1000);
    }

}
