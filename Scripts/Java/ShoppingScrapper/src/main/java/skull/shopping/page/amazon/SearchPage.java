package skull.shopping.page.amazon;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import skull.shopping.utils.SafeParser;

import java.util.List;

public class SearchPage {

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
        final List<WebElement> prices = webElement.findElements(By.cssSelector(".a-price"));
        if (prices.size() < index + 1) return -1;
        return SafeParser.parseFloat(prices.get(index).getText().substring(1), -1);
    }
}
