package skull.shopping.page.noon;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import skull.shopping.utils.ImplicitWaitManipulator;

import java.util.List;
import java.util.stream.Collectors;

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
        return ImplicitWaitManipulator.findIfElementExists(driver,
                By.cssSelector(".estimator_right img[src*=fulfilment_express]")).isPresent();
    }

    public static String getSellerName(WebDriver driver) {
        return driver.findElement(By.cssSelector(".storeLink:not([target='_blank'])")).getText();
    }

    public static String getItemName(WebDriver driver) {
        return driver.findElement(By.xpath("/html/body/div[1]/div/section/div/div[1]/div/div[2]/div[1]/div[2]/a/div/div")).getText()
                + " " + driver.findElement(By.xpath("/html/body/div[1]/div/section/div/div[1]/div/div[2]/div[1]/div[2]/h1")).getText();
    }

}
