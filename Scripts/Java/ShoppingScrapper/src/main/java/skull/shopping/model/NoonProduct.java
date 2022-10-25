package skull.shopping.model;

import lombok.Builder;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import static java.lang.Integer.parseInt;

@Slf4j
@SuperBuilder
public class NoonProduct extends Product {

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
