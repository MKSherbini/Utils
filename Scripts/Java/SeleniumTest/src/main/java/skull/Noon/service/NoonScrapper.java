package skull.Noon.service;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.*;
import org.openqa.selenium.NoSuchElementException;
import skull.Noon.output.NoonOutput;
import skull.Noon.model.NoonDiscount;
import skull.utils.Timer;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

@Slf4j
public class NoonScrapper implements Callable<List<NoonDiscount>> {
    private final String url;
    private final String filePath;
    private final String logID;

    private static final List<String> paramPreferredStores = List.of(
            "f[partner]=p_1", "f[partner]=p_9404" // sold by noon
    );
    private static final String paramItemsLimit = "limit=200";       // view 200 items per page (max)

    private final NoonDiscountService noonDiscountService;
    private final WebDriver driver;

    public NoonScrapper(String url, String filePath, String logID, WebDriver driver) {
        this.url = url;
        this.filePath = filePath;
        this.logID = logID;
        this.driver = driver;
        this.noonDiscountService = new NoonDiscountService(driver);
    }

    public List<NoonDiscount> call() {
        var timer = new Timer();
        timer.mark();
        try {
            return startScrapping();
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            return null;
        } finally {
            driver.quit();
            timer.log(logID);
        }
    }

    private List<NoonDiscount> startScrapping() {
        List<WebElement> pageLinks = driver.findElements(By.className("pageLink"));
        return scrapPageByPage(pageLinks.size() > 1 ?
                Integer.parseInt(pageLinks.get(pageLinks.size() - 1).getText()) : 1);
    }

    private List<NoonDiscount> scrapPageByPage(int pagesCount) {
        log.info("{}:scrapping {} pages", logID, pagesCount);
        var allDiscounts = new ArrayList<NoonDiscount>();
        for (var page = 1; page <= pagesCount; page++) {
            allDiscounts.addAll(scrapPage(page, pagesCount));
            sortAndPrint(allDiscounts);
        }
        return allDiscounts;
    }

    private void sortAndPrint(List<NoonDiscount> allDiscounts) {
        log.info("{}:writing pages...", logID);
        try {
            allDiscounts.sort(Collections.reverseOrder());
            NoonOutput.printToFile(filePath,
                    NoonOutput.renderHtmlOutput(allDiscounts));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<NoonDiscount> scrapPage(int pageNumber, int pagesCount) {
        driver.get(url + "&page=" + pageNumber);
        List<WebElement> discounts = driver.findElements(By.className("discount"));
        log.info("{}:discounts for Page-{}/{}: found {} elements ", logID, pageNumber, pagesCount, discounts.size());
        return discounts.stream()
                .map(noonDiscountService::createDiscountIfWorth)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public static void main(String[] args) {
        System.out.println(appendParamsToBase("https://www.noon.com/egypt-en/dailydeals-21-eg", "page=1"));
    }

    private static String appendParamsToBase(String baseurl, String... extraParams) {
        var params = new ArrayList<String>();
        params.addAll(paramPreferredStores);
        params.addAll(Arrays.asList(extraParams));
        params.add(paramItemsLimit);
        return baseurl + "?" + String.join("&", params);
    }
}
