package skull.Noon.service;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import skull.Noon.model.NoonDiscount;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.Float.parseFloat;
import static java.lang.Integer.parseInt;

public class NoonDiscountService {
    private static final String EXPRESS_IMAGE_URL = "https://z.nooncdn.com/s/app/com/noon/images/fulfilment_express_v2-en.svg";
    private final WebDriver driver;

    public NoonDiscountService(WebDriver driver) {
        this.driver = driver;
    }

    public NoonDiscount createDiscount(WebElement discountElement) {
        //        document.querySelectorAll(".swiper-container-initialized img[src*=product]")

        var productUrl = getUrl(discountElement);
        var tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));
        driver.get(productUrl);

        var sellerName = getSellerName();
        var imageUrls = getImageUrls();
        var isExpress = getExpressStatus();
        var itemName = getItemName();

        driver.switchTo().window(tabs.get(0));

        return NoonDiscount.builder()
                .discount(getDiscount(discountElement))
                .itemName(itemName)
                .price(getPrice(discountElement))
                .oldPrice(getOldPrice(discountElement))
                .imageUrls(imageUrls)
                .imageUrlMain(imageUrls.get(0))
                .sellerName(sellerName)
                .isExpress(isExpress)
                .url(productUrl)
                .build();
    }

    public NoonDiscount createDiscountIfWorth(WebElement discountElement) {
        var productUrl = getUrl(discountElement);
        final int discount = getDiscount(discountElement);
        final float price = getPrice(discountElement);

        if (!isValuable(discount, price)) return null;

        var tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));
        driver.get(productUrl);

        var sellerName = getSellerName();
        if (!hasTrustedSeller(sellerName)) {
            driver.switchTo().window(tabs.get(0));
            return null;
        }

        var imageUrls = getImageUrls();
        var isExpress = getExpressStatus();
        var itemName = getItemName();

        driver.switchTo().window(tabs.get(0));

        return NoonDiscount.builder()
                .discount(discount)
                .itemName(itemName)
                .price(price)
                .oldPrice(getOldPrice(discountElement))
                .imageUrls(imageUrls)
                .imageUrlMain(imageUrls.get(0))
                .sellerName(sellerName)
                .isExpress(isExpress)
                .url(productUrl)
                .build();
    }

    private List<String> getImageUrls() {
        return driver.findElements(By.cssSelector("img[src*='products/tr:n-t_400']")).stream()
                .map(urlElement -> urlElement.getAttribute("src"))
                .distinct()
                .collect(Collectors.toList());
    }

    private boolean getExpressStatus() {
        var maxWait = Duration.ofSeconds(5);
        final Duration implicitWaitTimeout = driver.manage().timeouts().getImplicitWaitTimeout();
        driver.manage().timeouts().implicitlyWait(maxWait);
        try {
            var explicitWait = new WebDriverWait(driver, maxWait);
            explicitWait.until(
                    ExpectedConditions.presenceOfElementLocated(
                            By.cssSelector(".estimator_right img[src*=fulfilment_express]")));
            return true;
        } catch (org.openqa.selenium.TimeoutException e) {
            return false;
        } finally {
            driver.manage().timeouts().implicitlyWait(implicitWaitTimeout);
        }
    }

    private int getDiscount(WebElement webElement) {
        return parseInt(webElement.getText().split("%")[0]);
    }

    private String getSellerName() {
        return driver.findElement(By.cssSelector(".storeLink:not([target='_blank'])")).getText();
    }

    private String getUrl(WebElement webElement) {
        return webElement.findElement(By.xpath("./../../../../../../..")).getAttribute("href");
    }

    private String getItemName() {
        return driver.findElement(By.xpath("/html/body/div[1]/div/section/div/div[1]/div/div[2]/div[1]/div[2]/a/div/div")).getText()
                + " " + driver.findElement(By.xpath("/html/body/div[1]/div/section/div/div[1]/div/div[2]/div[1]/div[2]/h1")).getText();
    }

    private float getOldPrice(WebElement webElement) {
        return parseFloat(webElement.findElement(By.xpath("./preceding-sibling::span")).getText().substring(4));
    }

    private float getPrice(WebElement webElement) {
        return parseFloat(webElement.findElement(By.xpath("./../preceding-sibling::div")).getText().substring(4));
    }

    public boolean isValuable(WebElement webElement) {
        var discount = getDiscount(webElement);
        var price = getPrice(webElement);

        return isValuable(discount, price);
    }

    private boolean isValuable(int discount, float price) {
        return NoonDiscount.builder().discount(discount).price(price).build().isValuable();
    }

    public boolean hasTrustedSeller(WebElement webElement) {
        var productUrl = getUrl(webElement);
        var tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));
        driver.get(productUrl);

        var seller = getSellerName();

        driver.switchTo().window(tabs.get(0));

        return hasTrustedSeller(seller);
    }

    private boolean hasTrustedSeller(String seller) {
        return NoonDiscount.builder().sellerName(seller).build().hasTrustedSeller();
    }
}
