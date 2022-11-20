package skull.shopping.page.amazon;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import skull.shopping.utils.SafeParser;

import java.util.List;

public class SearchPage {
    static final String CURRENCIES_PATTERN = "¥|EGP\\u00a0";

    private SearchPage() {
    }

    public static List<WebElement> getAllElements(WebDriver driver) {
        return driver.findElements(By.cssSelector(".s-result-item[data-component-type='s-search-result']"));
    }

    public static String getItemName(WebElement webElement) {
        return webElement.findElement(By.cssSelector(".s-title-instructions-style")).getText();
    }

    public static String getUrl(WebElement webElement) {
        return webElement.findElement(By.cssSelector(".s-title-instructions-style a.a-text-normal")).getAttribute("href");
    }

    public static String getImageUrl(WebElement webElement) {
        return webElement.findElement(By.cssSelector(".s-product-image-container img")).getAttribute("src");
    }

    public static float getPrice(WebElement webElement) {
        return parsePrices(webElement, 0);
    }

    public static float getOldPrice(WebElement webElement) {
        return parsePrices(webElement, 1);
    }

    private static float parsePrices(WebElement webElement, int index) {
        final List<WebElement> prices = webElement.findElements(By.cssSelector(".a-price .a-offscreen"));
        if (prices.size() < index + 1) return -1;
        String priceText = prices.get(index).getAttribute("innerText").replaceAll(CURRENCIES_PATTERN, "");
        return SafeParser.parseFloat(priceText, -1);
    }
}
