package skull.Noon.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Component;

import java.time.Duration;

import static java.lang.Float.parseFloat;
import static java.lang.Integer.parseInt;


public class SearchPage {

    private SearchPage() {
    }

    public static int getDiscount(WebElement webElement) {
        return parseInt(webElement.getText().split("%")[0]);
    }

    public static String getUrl(WebElement webElement) {
        return webElement.findElement(By.xpath("./../../../../../../..")).getAttribute("href");
    }

    public static float getOldPrice(WebElement webElement) {
        return parseFloat(webElement.findElement(By.xpath("./preceding-sibling::span")).getText().substring(4));
    }

    public static float getPrice(WebElement webElement) {
        return parseFloat(webElement.findElement(By.xpath("./../preceding-sibling::div")).getText().substring(4));
    }

    public static String getItemName(WebElement webElement) {
        return webElement.findElement(By.xpath("./../../../preceding-sibling::div")).getText();
    }
}
