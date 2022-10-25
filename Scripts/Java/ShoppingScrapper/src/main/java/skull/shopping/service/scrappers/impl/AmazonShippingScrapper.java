package skull.shopping.service.scrappers.impl;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import skull.shopping.model.AmazonProduct;
import skull.shopping.model.Product;
import skull.shopping.page.amazon.SearchPage;
import skull.shopping.service.scrappers.AmazonPageByPageScrapper;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
public class AmazonShippingScrapper extends AmazonPageByPageScrapper {

    public AmazonShippingScrapper(WebDriver driver, String startUrl, String filePath, String logID) {
        super(driver, startUrl, filePath, logID);
    }

    public List<AmazonProduct> scrapPage() {
        return SearchPage.getAllElements(driver).stream()
                .map(creator::createProduct)
                .filter(Objects::nonNull)
                .filter(AmazonProduct::isValuable)
                .collect(Collectors.toList());
    }
}
