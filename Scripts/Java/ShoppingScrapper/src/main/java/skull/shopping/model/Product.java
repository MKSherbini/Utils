package skull.shopping.model;

import lombok.*;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import static skull.shopping.AppConstants.*;

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
        return isValuable(itemName, price, discount, shippingPrice);
    }

    public static boolean isValuable(String itemName, float price, int discount, int shippingPrice) {
        if (itemName.startsWith("Sponsored")) return false;
        if (MIN_PRICE != -1 && price < MIN_PRICE) return false;
        if (MAX_PRICE != -1 && price > MAX_PRICE) return false;

        switch (SEARCH_RELATION) {
            case EITHER:
                return (discount >= MIN_DISCOUNT || shippingPrice <= MAX_SHIPPING_PRICE);
            case BOTH:
                return (discount >= MIN_DISCOUNT && shippingPrice <= MAX_SHIPPING_PRICE);
            case DISCOUNT:
                return (discount >= MIN_DISCOUNT);
            case SHIPPING:
                return (shippingPrice <= MAX_SHIPPING_PRICE);
            default:
                return false;
        }
    }


    public boolean goodShipping() {
        return (shippingPrice <= 1000);
    }

}
