package skull.shopping.service.scrappers.impl;


import org.openqa.selenium.WebDriver;
import skull.shopping.model.NoonProduct;
import skull.shopping.page.noon.SearchPage;
import skull.shopping.service.scrappers.NoonPageByPageScrapper;

import java.util.List;
import java.util.stream.Collectors;

public class NoonSearchScrapper extends NoonPageByPageScrapper {
    private final List<String> keywords;

    public NoonSearchScrapper(WebDriver driver, String startUrl, String filePath, String logID, List<String> keywords) {
        super(driver, startUrl, filePath, logID);
        this.keywords = keywords;
    }

    public List<NoonProduct> scrapPage() {
        return SearchPage.getAllElements(driver).stream()
                .map(creator::createProductFromSearchPage)
                .filter(this::matchKeywords)
                .collect(Collectors.toList());
    }

    private boolean matchKeywords(NoonProduct noonProduct) {
        var keywordsMatched = keywords.stream().filter(s -> noonProduct.getItemName().contains(s)).collect(Collectors.toList());
        return keywordsMatched.size() == keywords.size();
    }

}
