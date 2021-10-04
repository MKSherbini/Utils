package skull.Noon.service;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import skull.Noon.model.NoonDiscount;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.Float.parseFloat;
import static java.lang.Integer.parseInt;

@Slf4j
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
        log.info("seller: " + sellerName);

        // //img[contains(@src,'product')]
        var imageUrls = getImageUrls();
        log.info("imageUrls = " + imageUrls);

        driver.switchTo().window(tabs.get(0));

        return NoonDiscount.builder()
                .discount(getDiscount(discountElement))
                .itemName(getItemName(discountElement))
                .price(getPrice(discountElement))
                .oldPrice(getOldPrice(discountElement))
                .imageUrls(imageUrls)
                .imageUrlMain(imageUrls.get(0))
                .sellerName(sellerName)
                .isExpress(isExpress())
                .url(productUrl)
                .build();
    }

    private List<String> getImageUrls() {
        return driver.findElements(By.cssSelector("img[src*=product]")).stream()
                .map(urlElement -> urlElement.getAttribute("src"))
                .distinct()
                .collect(Collectors.toList());
    }

    private boolean isExpress() {
        try {
            var explicitWait = new WebDriverWait(driver, Duration.ofSeconds(5));
            explicitWait.until(
                    ExpectedConditions.presenceOfElementLocated(
                            By.cssSelector(".estimator_right img[src*=fulfilment_express]")));
            return true;
        } catch (org.openqa.selenium.TimeoutException e) {
            return false;
        }
    }

    public boolean isValuable(WebElement webElement) {
        var discount = getDiscount(webElement);
        var price = getPrice(webElement);

        return NoonDiscount.builder().discount(discount).price(price).build().isValuable();
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

    private String getItemName(WebElement webElement) {
        return webElement.findElement(By.xpath("./../../../preceding-sibling::div")).getText();
    }

    private float getOldPrice(WebElement webElement) {
        return parseFloat(webElement.findElement(By.xpath("./preceding-sibling::span")).getText().substring(4));
    }

    private float getPrice(WebElement webElement) {
        return parseFloat(webElement.findElement(By.xpath("./../preceding-sibling::div")).getText().substring(4));
    }
}
