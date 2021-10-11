package skull.Noon;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.interactions.Actions;
import skull.Noon.model.NoonDiscount;
import skull.Noon.service.NoonDiscountService;
import skull.utils.Timer;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

@Slf4j
public class NoonScrapper implements Callable<List<NoonDiscount>> {
    private final WebDriver driver;
    private final boolean isVisible;
    private final String url;
    private final String filePath;
    private final String logID;

    private final NoonDiscountService noonDiscountService;


    NoonScrapper(boolean isVisible, String url, String filePath, String logID) {
        this.isVisible = isVisible;
        this.url = url;
        this.filePath = filePath;
        this.logID = logID;
        driver = initDriver();
        this.noonDiscountService = new NoonDiscountService(driver);
    }

    private WebDriver initDriver() {
        final WebDriver driver;
        driver = new FirefoxDriver(getFirefoxOptions());
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofMinutes(1));
        driver.get(url);
        final String currentWindowHandle = driver.getWindowHandle();
        new Actions(driver).keyDown(Keys.CONTROL).click(driver.findElement(By.tagName("body"))).perform();
        driver.switchTo().window(currentWindowHandle);
        return driver;
    }

    private FirefoxOptions getFirefoxOptions() {
        var firefoxOptions = new FirefoxOptions();
        firefoxOptions.setHeadless(!isVisible);
        return firefoxOptions;
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

}
