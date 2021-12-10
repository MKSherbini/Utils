package skull.noon.service;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import skull.noon.interfaces.PageScrapperInterface;
import skull.noon.model.NoonProduct;
import skull.noon.output.FilePrinter;
import skull.noon.page.SearchPage;
import skull.utils.ScrapTemplates;
import skull.utils.Timer;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

@Slf4j
public abstract class PageByPageScrapper implements Callable<List<NoonProduct>>, PageScrapperInterface<List<NoonProduct>> {
   protected final FilePrinter filePrinter;
   protected final ScrapTemplates scrapTemplates;
   protected final WebDriver driver;

    protected PageByPageScrapper(WebDriver driver, FilePrinter filePrinter, String url) {
        this.driver = driver;
        this.filePrinter = filePrinter;
        this.scrapTemplates = new ScrapTemplates(driver, filePrinter, url);
    }

    public List<NoonProduct> call() {
        var timer = new Timer();
        timer.mark();
        try {
            return scrapTemplates.scrapPageByPage(this);
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            return new ArrayList<>();
        } finally {
            driver.quit();
            timer.log(filePrinter.getLogID());
        }
    }
}
