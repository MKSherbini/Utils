package skull.shopping.service.scrappers.impl;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.*;
import skull.shopping.model.NoonProduct;
import skull.shopping.page.noon.SearchPage;
import skull.shopping.service.scrappers.NoonPageByPageScrapper;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class NoonDiscountsScrapper extends NoonPageByPageScrapper {

    public NoonDiscountsScrapper(WebDriver driver, String startUrl, String filePath, String logID) {
        super(driver, startUrl, filePath, logID);
    }

    public List<NoonProduct> scrapPage() {
        return SearchPage.getDiscountElements(driver).stream()
                .map(SearchPage::getParentProduct)
                .map(creator::createProductIfWorth)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}
