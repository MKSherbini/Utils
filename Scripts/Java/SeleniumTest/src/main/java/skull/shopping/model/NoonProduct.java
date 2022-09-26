package skull.shopping.model;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import static java.lang.Integer.parseInt;

@Slf4j
public class NoonProduct extends Product {

    @Builder
    public NoonProduct(boolean isSponsored, boolean isFullfilled, int discount, int shippingPrice, float price, float oldPrice, String itemName, String url, String imageUrl, List<String> imageUrls, String sellerName) {
        super(isSponsored, isFullfilled, discount, shippingPrice, price, oldPrice, itemName, url, imageUrl, imageUrls, sellerName);
    }

    private static final List<String> trustedSellers = List.of("noon"
//            , "more shopping"
    );

    public boolean hasTrustedSeller() {
        return trustedSellers.contains(sellerName.toLowerCase());
    }

    public String getImageUrlMain() {
        if (imageUrls.isEmpty())
            return null;
        return imageUrls.get(0);
    }

}
