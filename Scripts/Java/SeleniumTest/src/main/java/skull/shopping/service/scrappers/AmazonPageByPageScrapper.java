package skull.shopping.service.scrappers;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import skull.shopping.model.AmazonProduct;
import skull.shopping.service.creator.AmazonProductCreator;
import skull.shopping.service.output.AmazonProductPrinter;
import skull.utils.SafeParser;

import java.util.List;

@Slf4j
public abstract class AmazonPageByPageScrapper extends PageByPageScrapper<AmazonProduct> {

    protected AmazonPageByPageScrapper(WebDriver driver, String startUrl, String filePath, String logID) {
        super(driver, startUrl, new AmazonProductCreator(driver), new AmazonProductPrinter(filePath, logID));
    }

    @Override
    int getPages() {
        final String text = driver.findElement(By.xpath("//a[contains(@class,'s-pagination-next')]//preceding-sibling::span[1]")).getText();
        return SafeParser.parseInt(text, 1); // why 10?
    }
}
