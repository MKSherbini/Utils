package skull.utils;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import skull.noon.interfaces.PageScrapperInterface;
import skull.noon.model.NoonProduct;
import skull.noon.output.FilePrinter;

import java.util.ArrayList;
import java.util.List;


// can become a strategy design pattern

@Slf4j
public class ScrapTemplates {
    private final WebDriver driver;
    private final FilePrinter filePrinter;
    private final String url;

    public ScrapTemplates(WebDriver driver, FilePrinter filePrinter, String url) {
        this.driver = driver;
        this.filePrinter = filePrinter;
        this.url = UrlManipulator.appendParamsToBase(url);
    }

    public List<NoonProduct> scrapPageByPage(PageScrapperInterface<List<NoonProduct>> pageScrapper) {
        driver.get(url);
        List<WebElement> pageLinks = driver.findElements(By.className("pageLink"));
        int pagesCount = pageLinks.size() > 1 ?
                Integer.parseInt(pageLinks.get(pageLinks.size() - 1).getText()) : 1;

        log.info("{}:scrapping {} pages from {}", filePrinter.getLogID(), pagesCount, url);
        var allDiscounts = new ArrayList<NoonProduct>();
        for (var page = 1; page <= pagesCount; page++) {
            if (page > 1)
                driver.get(url + "&page=" + page);
            log.info("{}: Page-{}/{}", filePrinter.getLogID(), page, pagesCount);
            allDiscounts.addAll(pageScrapper.scrapPage());
            filePrinter.sortAndPrint(allDiscounts);
        }
        return allDiscounts;
    }

}
