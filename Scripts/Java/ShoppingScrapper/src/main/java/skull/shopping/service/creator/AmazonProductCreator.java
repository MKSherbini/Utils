package skull.shopping.service.creator;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import skull.shopping.model.AmazonProduct;
import skull.shopping.model.Product;
import skull.shopping.model.SearchRelation;
import skull.shopping.page.amazon.ProductPage;
import skull.shopping.page.amazon.SearchPage;

import java.util.ArrayList;

import static skull.shopping.AppConstants.SEARCH_RELATION;

@Slf4j
public class AmazonProductCreator implements ProductCreator<AmazonProduct> {
    private final String INVALID_URL = "javascript:void(0)";
    private final WebDriver driver;

    public AmazonProductCreator(WebDriver driver) {
        this.driver = driver;
    }

    @Override
    public AmazonProduct createProduct(WebElement el) {
        var productUrl = SearchPage.getUrl(el);
        var price = SearchPage.getPrice(el);
        var oldPrice = SearchPage.getOldPrice(el);
        var discount = oldPrice == -1 ? 0 : (int) ((oldPrice - price) / oldPrice * 100);

        int shipping = -1;
        if (SEARCH_RELATION != SearchRelation.DISCOUNT) {
            var tabs = new ArrayList<>(driver.getWindowHandles());
            driver.switchTo().window(tabs.get(1));
            driver.get(productUrl);

            shipping = ProductPage.getShippingPrice(driver);

            driver.switchTo().window(tabs.get(0));
        }

        final AmazonProduct product = AmazonProduct.builder()
                .shippingPrice(shipping)
                .discount(discount)
                .itemName(SearchPage.getItemName(el))
                .price(price)
                .oldPrice(oldPrice)
                .imageUrl(SearchPage.getImageUrl(el))
                .url(productUrl)
                .build();
        log.info(product.toString());
        return product;
    }

    @Override
    public AmazonProduct createProductIfWorth(WebElement el) {
        var productUrl = SearchPage.getUrl(el);
        final String itemName = SearchPage.getItemName(el);
        var price = SearchPage.getPrice(el);
        var oldPrice = SearchPage.getOldPrice(el);
        var discount = oldPrice == -1 ? 0 : (int) ((oldPrice - price) / oldPrice * 100);

        int shipping = -1;
        if (SEARCH_RELATION != SearchRelation.DISCOUNT) {
            var tabs = new ArrayList<>(driver.getWindowHandles());
            driver.switchTo().window(tabs.get(1));
            driver.get(productUrl);

            shipping = ProductPage.getShippingPrice(driver);

            driver.switchTo().window(tabs.get(0));
        }

        if (!Product.isValuable(itemName, price, discount, shipping)) {
            log.info("rejected: {}", productUrl);
            return null;
        }
        log.info("accepted: {}", productUrl);

        return AmazonProduct.builder()
                .shippingPrice(shipping)
                .discount(discount)
                .itemName(itemName)
                .price(price)
                .oldPrice(oldPrice)
                .imageUrl(SearchPage.getImageUrl(el))
                .url(productUrl)
                .build();
    }
}
