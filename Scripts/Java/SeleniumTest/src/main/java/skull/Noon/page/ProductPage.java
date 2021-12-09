package skull.Noon.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.Float.parseFloat;
import static java.lang.Integer.parseInt;

public class ProductPage {

    private ProductPage() {
    }

    public static List<String> getImageUrls(WebDriver driver) {
        return driver.findElements(By.cssSelector("img[src*='products/tr:n-t_400']")).stream()
                .map(urlElement -> urlElement.getAttribute("src"))
                .distinct()
                .collect(Collectors.toList());
    }

    public static boolean getExpressStatus(WebDriver driver) {
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

    public static String getSellerName(WebDriver driver) {
        return driver.findElement(By.cssSelector(".storeLink:not([target='_blank'])")).getText();
    }

    public static String getItemName(WebDriver driver) {
        return driver.findElement(By.xpath("/html/body/div[1]/div/section/div/div[1]/div/div[2]/div[1]/div[2]/a/div/div")).getText()
                + " " + driver.findElement(By.xpath("/html/body/div[1]/div/section/div/div[1]/div/div[2]/div[1]/div[2]/h1")).getText();
    }

}
