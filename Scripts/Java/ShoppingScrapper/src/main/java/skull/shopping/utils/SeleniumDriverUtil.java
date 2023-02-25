package skull.shopping.utils;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class SeleniumDriverUtil {

    private static final Duration maxWait = Duration.ofMillis(200);

    private SeleniumDriverUtil() {
    }

    public static Optional<WebElement> findIfElementExists(WebDriver driver, By query) {
        return findIfElementExists(driver, ExpectedConditions.presenceOfElementLocated(query));
    }


    public static Optional<WebElement> findIfElementExists(WebElement root, By query) {
        WebDriver driver = ((WrapsDriver) root).getWrappedDriver();
        return findIfElementExists(driver, x -> root.findElement(query));
    }

    public static Optional<List<WebElement>> findIfElementsExists(WebElement root, By query) {
        WebDriver driver = ((WrapsDriver) root).getWrappedDriver();
        return findIfElementExists(driver, x -> root.findElements(query));
    }

    public static <V> Optional<V> findIfElementExists(WebDriver driver, Function<WebDriver, V> isTrue) {
        final Duration implicitWaitTimeout = driver.manage().timeouts().getImplicitWaitTimeout();
        driver.manage().timeouts().implicitlyWait(maxWait);
        try {
            var explicitWait = new WebDriverWait(driver, maxWait);
            var found = explicitWait.until(isTrue);
            return Optional.of(found);
        } catch (org.openqa.selenium.TimeoutException e) {
            return Optional.empty();
        } finally {
            driver.manage().timeouts().implicitlyWait(implicitWaitTimeout);
        }
    }

    public static WebElement findParent(WebElement el) {
        return (WebElement) ((JavascriptExecutor) ((WrapsDriver) el).getWrappedDriver()).executeScript(
                "return arguments[0].parentNode;", el);
    }
}
