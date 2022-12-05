package skull.shopping.utils;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Optional;

public class SeleniumDriverUtil {

    private static final Duration maxWait = Duration.ofSeconds(1);

    private SeleniumDriverUtil() {
    }

    public static Optional<WebElement> findIfElementExists(WebDriver driver, By query) {
        final Duration implicitWaitTimeout = driver.manage().timeouts().getImplicitWaitTimeout();
        driver.manage().timeouts().implicitlyWait(maxWait);
        try {
            var explicitWait = new WebDriverWait(driver, maxWait);
            var found = explicitWait.until(
                    ExpectedConditions.presenceOfElementLocated(query));
            return Optional.of(found);
        } catch (org.openqa.selenium.TimeoutException e) {
            return Optional.empty();
        } finally {
            driver.manage().timeouts().implicitlyWait(implicitWaitTimeout);
        }
    }

    public static Optional<WebElement> findIfElementExists(WebElement root, By query) {
        WebDriver driver = ((WrapsDriver) root).getWrappedDriver();
        final Duration implicitWaitTimeout = driver.manage().timeouts().getImplicitWaitTimeout();
        driver.manage().timeouts().implicitlyWait(maxWait);
        try {
            var explicitWait = new WebDriverWait(driver, maxWait);
            var found = explicitWait.until(x -> root.findElement(query));
//            var found = root.findElement(query);
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
