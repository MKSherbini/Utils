package skull.shopping.service.scrappers;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import skull.shopping.model.NoonProduct;
import skull.shopping.service.output.NoonProductPrinter;
import skull.shopping.service.creator.NoonProductCreator;
import skull.utils.UrlManipulator;

import java.util.List;

@Slf4j
public abstract class NoonPageByPageScrapper extends PageByPageScrapper<NoonProduct> {

    protected NoonPageByPageScrapper(WebDriver driver, String startUrl, String filePath, String logID) {
        super(driver, UrlManipulator.appendParamsToBase(startUrl), new NoonProductCreator(driver), new NoonProductPrinter(filePath, logID));
    }

    @Override
    int getPages() {
        List<WebElement> pageLinks = driver.findElements(By.className("pageLink"));
        return pageLinks.size() > 1 ?
                Integer.parseInt(pageLinks.get(pageLinks.size() - 1).getText()) : 1;
    }
}
