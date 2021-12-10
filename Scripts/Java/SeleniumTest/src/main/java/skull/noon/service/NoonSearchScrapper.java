package skull.noon.service;


import org.openqa.selenium.WebDriver;
import skull.noon.model.NoonProduct;
import skull.noon.output.FilePrinter;
import skull.noon.page.SearchPage;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class NoonSearchScrapper extends PageByPageScrapper {
    private final NoonProductCreator noonProductCreator;
    private final List<String> keywords;

    public NoonSearchScrapper(WebDriver driver, FilePrinter filePrinter, String url, List<String> keywords) {
        super(driver, filePrinter, url);
        this.noonProductCreator = new NoonProductCreator(driver);
        this.keywords = keywords;
    }

    public List<NoonProduct> scrapPage() {
        return SearchPage.getAllElements(driver).stream()
                .map(noonProductCreator::createProductFromSearchPage)
                .filter(this::matchKeywords)
                .collect(Collectors.toList());
    }

    private boolean matchKeywords(NoonProduct noonProduct) {
        var keywordsMatched = keywords.stream().filter(s -> noonProduct.getItemName().contains(s)).collect(Collectors.toList());
        return keywordsMatched.size() == keywords.size();
    }

}
