package skull.shopping.service.scrappers;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import skull.shopping.model.AmazonProduct;
import skull.shopping.service.creator.AmazonProductCreator;
import skull.shopping.service.output.AmazonProductPrinter;
import skull.shopping.utils.SafeParser;
import skull.shopping.utils.SeleniumDriverUtil;

import java.util.List;
import java.util.Optional;

@Slf4j
public abstract class AmazonPageByPageScrapper extends PageByPageScrapper<AmazonProduct> {

    protected AmazonPageByPageScrapper(WebDriver driver, String startUrl, String filePath, String logID) {
        super(driver, startUrl + "&language=en", new AmazonProductCreator(driver), new AmazonProductPrinter(filePath, logID));
    }

    @Override
    int getPages() { // why stuck on 10?
        final List<By> options = List.of(
                By.xpath("//a[contains(@class,'s-pagination-next')]//preceding-sibling::span[1]"),
                By.xpath("//a[contains(@class,'s-pagination-next')]//preceding-sibling::a[1]")
        );

        return options.stream()
                .map(o -> SeleniumDriverUtil.findIfElementExists(driver, o))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(WebElement::getText)
                .mapToInt(o -> SafeParser.parseInt(o, 1))
                .max().orElse(1);
    }
}
