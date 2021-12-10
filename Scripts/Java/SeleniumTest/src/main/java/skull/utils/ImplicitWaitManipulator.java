package skull.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Optional;

public class ImplicitWaitManipulator {
    private ImplicitWaitManipulator() {
    }

    public static Optional<WebElement> findIfElementExists(WebDriver driver, By query) {
        var maxWait = Duration.ofSeconds(5);
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

    public static Optional<WebElement> findIfElementExists(WebDriver driver, WebElement root, By query) {
        var maxWait = Duration.ofSeconds(5);
        final Duration implicitWaitTimeout = driver.manage().timeouts().getImplicitWaitTimeout();
        driver.manage().timeouts().implicitlyWait(maxWait);
        try {
            var found = root.findElement(query);
            return Optional.of(found);
        } catch (NoSuchElementException e) {
            return Optional.empty();
        } finally {
            driver.manage().timeouts().implicitlyWait(implicitWaitTimeout);
        }
    }
}
