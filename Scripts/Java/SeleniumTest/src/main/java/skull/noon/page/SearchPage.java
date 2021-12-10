package skull.noon.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import skull.utils.SafeParser;

import java.util.List;

import static java.lang.Integer.parseInt;


public class SearchPage {

    private SearchPage() {
    }

    public static List<WebElement> getAllElements(WebDriver driver) {
        return driver.findElements(By.className("productContainer"));
    }

    public static List<WebElement> getDiscountElements(WebDriver driver) {
        return driver.findElements(By.className("discount"));
    }

    public static WebElement getParentProduct(WebElement webElement) {
        return webElement.findElement(By.xpath("./ancestor-or-self::div[@class='productContainer']"));
    }

    public static String getUrl(WebElement webElement) {
        return webElement.findElement(By.tagName("a")).getAttribute("href");
    }

    public static String getItemName(WebElement webElement) {
        return webElement.findElement(By.cssSelector("div[title]")).getAttribute("title");
    }

    public static int getDiscount(WebElement webElement) {
        return SafeParser.parseInt(webElement.findElement(By.className("discount")).getText().split("%")[0], 0);
    }

    public static float getOldPrice(WebElement webElement) {
        return SafeParser.parseFloat(webElement.findElement(By.className("oldPrice")).getText().substring(4), -1);
    }

    public static float getPrice(WebElement webElement) {
        return SafeParser.parseFloat(webElement.findElement(By.cssSelector(".currency + strong")).getText(), -1);
    }

}
