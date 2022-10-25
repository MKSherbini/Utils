package skull.shopping.service.creator;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.*;
import skull.shopping.model.NoonProduct;
import skull.shopping.page.noon.ProductPage;
import skull.shopping.page.noon.SearchPage;

import java.util.ArrayList;

import static java.lang.Integer.parseInt;

@Slf4j
public class NoonProductCreator implements ProductCreator<NoonProduct> {
    private static final String EXPRESS_IMAGE_URL = "https://z.nooncdn.com/s/app/com/noon/images/fulfilment_express_v2-en.svg";
    private final WebDriver driver;

    public NoonProductCreator(WebDriver driver) {
        this.driver = driver;
    }

    @Override
    public NoonProduct createProductFromSearchPage(WebElement element) {
        final NoonProduct noonProduct = NoonProduct.builder()
                .discount(SearchPage.getDiscount(driver, element))
                .itemName(SearchPage.getItemName(element))
                .price(SearchPage.getPrice(element))
                .oldPrice(SearchPage.getOldPrice(driver, element))
                .imageUrls(SearchPage.getImageUrls(element))
                .url(SearchPage.getUrl(element))
                .build();
        log.info("createProductFromSearchPage {}", noonProduct);
        return noonProduct;
    }

    @Override
    public NoonProduct createProduct(WebElement element) {
        var productUrl = SearchPage.getUrl(element);
        var tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));
        driver.get(productUrl);

        var sellerName = ProductPage.getSellerName(driver);
        var imageUrls = ProductPage.getImageUrls(driver);
        var isExpress = ProductPage.getExpressStatus(driver);
        var itemName = ProductPage.getItemName(driver);

        driver.switchTo().window(tabs.get(0));

        return NoonProduct.builder()
                .discount(SearchPage.getDiscount(driver, element))
                .itemName(itemName)
                .price(SearchPage.getPrice(element))
                .oldPrice(SearchPage.getOldPrice(driver, element))
                .imageUrls(imageUrls)
                .sellerName(sellerName)
                .isFullfilled(isExpress)
                .url(productUrl)
                .build();
    }

    @Override
    public NoonProduct createProductIfWorth(WebElement element) {
        var productUrl = SearchPage.getUrl(element);
        final int discount = SearchPage.getDiscount(driver, element);
        final float price = SearchPage.getPrice(element);

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

        return NoonProduct.builder()
                .discount(discount)
                .itemName(itemName)
                .price(price)
                .oldPrice(SearchPage.getOldPrice(driver, element))
                .imageUrls(imageUrls)
                .sellerName(sellerName)
                .isFullfilled(isExpress)
                .url(productUrl)
                .build();
    }

    private boolean isValuable(WebElement element) {
        var discount = SearchPage.getDiscount(driver, element);
        var price = SearchPage.getPrice(element);

        return isValuable(discount, price);
    }

    private boolean isValuable(int discount, float price) {
        return NoonProduct.builder().discount(discount).price(price).build().isValuable();
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
        return NoonProduct.builder().sellerName(seller).build().hasTrustedSeller();
    }
}
