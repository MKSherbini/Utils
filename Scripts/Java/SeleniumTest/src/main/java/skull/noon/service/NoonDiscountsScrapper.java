package skull.noon.service;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.*;
import skull.noon.output.FilePrinter;
import skull.noon.model.NoonProduct;
import skull.noon.page.SearchPage;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class NoonDiscountsScrapper extends PageByPageScrapper {
    private final NoonProductCreator noonProductCreator;

    public NoonDiscountsScrapper(WebDriver driver, FilePrinter filePrinter, String url) {
        super(driver, filePrinter, url);
        this.noonProductCreator = new NoonProductCreator(driver);
    }

    public List<NoonProduct> scrapPage() {
        return SearchPage.getDiscountElements(driver).stream()
                .map(SearchPage::getParentProduct)
                .map(noonProductCreator::createProductIfWorth)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}
