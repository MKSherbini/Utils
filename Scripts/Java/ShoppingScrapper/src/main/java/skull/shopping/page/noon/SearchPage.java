package skull.shopping.page.noon;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import skull.shopping.utils.ImplicitWaitManipulator;
import skull.shopping.utils.SafeParser;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public static int getDiscount(WebDriver driver, WebElement webElement) {
        final Optional<WebElement> discount = ImplicitWaitManipulator.findIfElementExists(driver, webElement, By.className("discount"));
        return discount.map(element -> SafeParser.parseInt(element.getText().split("%")[0], 0)).orElse(0);
    }

    public static float getOldPrice(WebDriver driver, WebElement webElement) {
        final Optional<WebElement> discount = ImplicitWaitManipulator.findIfElementExists(driver, webElement, By.className("oldPrice"));
        return discount.map(element -> SafeParser.parseInt(element.getText().substring(4), 0)).orElse(0);
    }

    public static float getPrice(WebElement webElement) {
        return SafeParser.parseFloat(webElement.findElement(By.cssSelector(".currency + strong")).getText(), -1);
    }

    public static List<String> getImageUrls(WebElement element) {
        return element.findElements(By.cssSelector("img")).stream()
                .map(urlElement -> urlElement.getAttribute("src"))
                .distinct()
                .collect(Collectors.toList());
    }
}
