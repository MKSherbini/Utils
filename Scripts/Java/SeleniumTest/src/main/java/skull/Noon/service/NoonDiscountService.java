package skull.Noon.service;

import org.openqa.selenium.*;
import skull.Noon.model.NoonDiscount;
import skull.Noon.page.ProductPage;
import skull.Noon.page.SearchPage;

import java.util.ArrayList;

import static java.lang.Integer.parseInt;

public class NoonDiscountService {
    private static final String EXPRESS_IMAGE_URL = "https://z.nooncdn.com/s/app/com/noon/images/fulfilment_express_v2-en.svg";
    private final WebDriver driver;

    public NoonDiscountService(WebDriver driver) {
        this.driver = driver;
    }

    public NoonDiscount createDiscount(WebElement discountElement) {
        //        document.querySelectorAll(".swiper-container-initialized img[src*=product]")

        var productUrl = SearchPage.getUrl(discountElement);
        var tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));
        driver.get(productUrl);

        var sellerName = ProductPage.getSellerName(driver);
        var imageUrls = ProductPage.getImageUrls(driver);
        var isExpress = ProductPage.getExpressStatus(driver);
        var itemName = ProductPage.getItemName(driver);

        driver.switchTo().window(tabs.get(0));

        return NoonDiscount.builder()
                .discount(SearchPage.getDiscount(discountElement))
                .itemName(itemName)
                .price(SearchPage.getPrice(discountElement))
                .oldPrice(SearchPage.getOldPrice(discountElement))
                .imageUrls(imageUrls)
                .imageUrlMain(imageUrls.get(0))
                .sellerName(sellerName)
                .isExpress(isExpress)
                .url(productUrl)
                .build();
    }

    public NoonDiscount createDiscountIfWorth(WebElement discountElement) {
        var productUrl = SearchPage.getUrl(discountElement);
        final int discount = SearchPage.getDiscount(discountElement);
        final float price = SearchPage.getPrice(discountElement);

        if (!isValuable(discount, price)) return null;

        var tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));
        driver.get(productUrl);

        var sellerName = ProductPage.getSellerName(driver);
        if (!hasTrustedSeller(sellerName)) {
            driver.switchTo().window(tabs.get(0));
            return null;
        }

        var imageUrls = ProductPage.getImageUrls(driver);
        var isExpress = ProductPage.getExpressStatus(driver);
        var itemName = ProductPage.getItemName(driver);

        driver.switchTo().window(tabs.get(0));

        return NoonDiscount.builder()
                .discount(discount)
                .itemName(itemName)
                .price(price)
                .oldPrice(SearchPage.getOldPrice(discountElement))
                .imageUrls(imageUrls)
                .imageUrlMain(imageUrls.get(0))
                .sellerName(sellerName)
                .isExpress(isExpress)
                .url(productUrl)
                .build();
    }

    public boolean isValuable(WebElement webElement) {
        var discount = SearchPage.getDiscount(webElement);
        var price = SearchPage.getPrice(webElement);

        return isValuable(discount, price);
    }

    private boolean isValuable(int discount, float price) {
        return NoonDiscount.builder().discount(discount).price(price).build().isValuable();
    }

    public boolean hasTrustedSeller(WebElement webElement) {
        var productUrl = SearchPage.getUrl(webElement);
        var tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));
        driver.get(productUrl);

        var seller = ProductPage.getSellerName(driver);

        driver.switchTo().window(tabs.get(0));

        return hasTrustedSeller(seller);
    }

    private boolean hasTrustedSeller(String seller) {
        return NoonDiscount.builder().sellerName(seller).build().hasTrustedSeller();
    }
}
