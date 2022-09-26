package skull.shopping.model;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class Product implements Comparable<Product> {
    boolean isSponsored;
    boolean isFullfilled;
    int discount;
    int shippingPrice;
    float price;
    float oldPrice;
    String itemName;
    String url;
    String imageUrl;
    List<String> imageUrls;
    String sellerName;

    @Override
    public int compareTo(Product o) {
        if (discount != o.discount)
            return discount - o.discount;
        if (oldPrice != o.oldPrice)
            return Float.compare(oldPrice, o.oldPrice);
        if (price != o.price)
            return Float.compare(price, o.price);
        if (!itemName.equals(o.itemName))
            return itemName.compareTo(o.itemName);
        return url.compareTo(o.url);
    }

    public boolean isValuable() {
        return (discount >= 10 && price >= 350);
    }

    public boolean goodShipping() {
        return (shippingPrice <= 1000);
    }

}
