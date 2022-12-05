package skull.shopping.page.amazon;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import skull.shopping.utils.SeleniumDriverUtil;
import skull.shopping.utils.SafeParser;

import java.util.Optional;

@Slf4j
public class ProductPage {
    private static final String CAN_NOT_SHIP = "This item cannot be shipped to your selected delivery location. Please choose a different delivery location.";

    private ProductPage() {
    }

    public static int getShippingPrice(WebDriver driver) {
        Optional<WebElement> el = SeleniumDriverUtil.findIfElementExists(driver, By.cssSelector("#deliveryBlockMessage"));
        if (el.isEmpty() || el.get().getText().length() > 50) return Integer.MAX_VALUE;
        var splits = el.get().getText().split(" ");
        if (splits.length == 0 || splits[0].isEmpty()) {
            el = SeleniumDriverUtil.findIfElementExists(driver, By.cssSelector("#abbreviate_shipping_accordion_head"));
            if (el.isEmpty()) return Integer.MAX_VALUE;
            splits = el.get().getText().split(" ");
            if (splits.length < 2 || splits[1].isEmpty()) return Integer.MAX_VALUE;
            return SafeParser.parseInt(splits[1].substring(1), 0);
        }
        return SafeParser.parseInt(splits[0].substring(1), 0);
    }

}
