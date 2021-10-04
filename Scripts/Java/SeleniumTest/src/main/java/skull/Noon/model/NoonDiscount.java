package skull.Noon.model;

import lombok.Builder;
import lombok.Data;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.stream.Collectors;

import static java.lang.Float.parseFloat;
import static java.lang.Integer.parseInt;

@Value
@Slf4j
@Builder
public class NoonDiscount implements Comparable<NoonDiscount> {
    int discount;
    String itemName;
    float price;
    float oldPrice;
    String url;
    List<String> imageUrls;
    String imageUrlMain;
    boolean isExpress;

    String sellerName;
//   float sellerRate;
//   float productRate;

    private static final List<String> trustedSellers = List.of("noon"
//            , "more shopping"
    );

    @Override
    public int compareTo(NoonDiscount o) {
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

    public boolean hasTrustedSeller() {
        return trustedSellers.contains(sellerName.toLowerCase());
    }

}
