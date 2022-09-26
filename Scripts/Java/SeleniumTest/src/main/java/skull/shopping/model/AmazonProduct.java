package skull.shopping.model;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class AmazonProduct extends Product  {

    @Builder
    public AmazonProduct(boolean isSponsored, boolean isFullfilled, int discount, int shippingPrice, float price, float oldPrice, String itemName, String url, String imageUrl, List<String> imageUrls, String sellerName) {
        super(isSponsored, isFullfilled, discount, shippingPrice, price, oldPrice, itemName, url, imageUrl, imageUrls, sellerName);
    }

}
