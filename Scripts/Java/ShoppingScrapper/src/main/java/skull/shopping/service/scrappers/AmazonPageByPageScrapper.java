package skull.shopping.service.scrappers;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import skull.shopping.model.AmazonProduct;
import skull.shopping.service.creator.AmazonProductCreator;
import skull.shopping.service.output.AmazonProductPrinter;
import skull.shopping.utils.SafeParser;

@Slf4j
public abstract class AmazonPageByPageScrapper extends PageByPageScrapper<AmazonProduct> {

    protected AmazonPageByPageScrapper(WebDriver driver, String startUrl, String filePath, String logID) {
        super(driver, startUrl, new AmazonProductCreator(driver), new AmazonProductPrinter(filePath, logID));
    }

    @Override
    int getPages() {
        final var n1 = SafeParser.parseInt(driver.findElement(By.xpath("//a[contains(@class,'s-pagination-next')]//preceding-sibling::span[1]")).getText(), 1);
        final var n2 = SafeParser.parseInt(driver.findElement(By.xpath("//a[contains(@class,'s-pagination-next')]//preceding-sibling::a[1]")).getText(), 1);
        return Math.max(n1, n2); // why 10?
    }
}
