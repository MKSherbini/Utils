package skull.shopping.page.amazon;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import skull.shopping.utils.SafeParser;
import skull.shopping.utils.SeleniumDriverUtil;

import java.util.List;
import java.util.Optional;

public class SearchPage {
    static final String CURRENCIES_PATTERN = "¥|EGP\\u00a0|AED.|\\$";

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
        final Optional<List<WebElement>> elementsExists = SeleniumDriverUtil.findIfElementsExists(webElement, By.cssSelector(".a-price .a-offscreen"));
        if (elementsExists.isEmpty()) {
            return -1;
        }
        final List<WebElement> prices = elementsExists.get();
        if (prices.size() < index + 1) return -1;
        if (index == 1 && SeleniumDriverUtil.findParent(prices.get(1)).getAttribute("data-a-strike") == null) return -1;

        String priceText = prices.get(index).getAttribute("innerText").replaceAll(CURRENCIES_PATTERN, "");
        return SafeParser.parseFloat(priceText, -1);
    }
}
