package skull.shopping.service.scrappers;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import skull.shopping.interfaces.PageScrapperInterface;
import skull.shopping.model.Product;
import skull.shopping.service.output.ProductPrinter;
import skull.shopping.service.creator.ProductCreator;
import skull.shopping.utils.Timer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

@Slf4j
public abstract class PageByPageScrapper<T extends Product> implements Callable<List<T>>, PageScrapperInterface<List<T>> {
    protected final WebDriver driver;
    protected final String startUrl;
    @Getter
    protected final ProductCreator<T> creator;
    @Getter
    protected final ProductPrinter<T> printer;

    protected PageByPageScrapper(WebDriver driver, String startUrl, ProductCreator<T> creator, ProductPrinter<T> printer) {
        this.driver = driver;
        this.startUrl = startUrl;
        this.creator = creator;
        this.printer = printer;
    }

    public List<T> call() {
        var timer = new Timer();
        timer.mark();
        try {
            return scrapPageByPage(this);
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            return new ArrayList<>();
        } finally {
            driver.quit();
            timer.log(printer.getLogID());
        }
    }

    public List<T> scrapPageByPage(
            PageScrapperInterface<List<T>> pageScrapper) {
        driver.get(startUrl);
        var pagesCount = getPages();

        log.info("{}:scrapping {} pages from {}", printer.getLogID(), pagesCount, startUrl);
        var allDiscounts = new ArrayList<T>();
        for (var page = 1; page <= pagesCount; page++) {
            if (page > 1)
                driver.get(startUrl + "&page=" + page);
            log.info("{}: Page-{}/{}", printer.getLogID(), page, pagesCount);
            allDiscounts.addAll(pageScrapper.scrapPage());
            printer.sortAndPrint(allDiscounts);
        }
        return allDiscounts;
    }

    abstract int getPages();

}
