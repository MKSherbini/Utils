package skull.shopping.provider;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import skull.shopping.model.ScrapperConfig;
import skull.shopping.service.scrappers.impl.AmazonShippingScrapper;
import skull.shopping.service.scrappers.impl.NoonDiscountsScrapper;
import skull.shopping.service.scrappers.impl.NoonSearchScrapper;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class AsyncProvider {
    private final ObjectProvider<DriverProvider> driverProvider;
    private final ScrapperConfig scrapperConfig;

    @Async
    public void submitDiscountsScrapJob(String url, String fileName) {
        final WebDriver driver = driverProvider.getObject().getDriver();
        log.info("submitDiscountsScrapJob with driver: {}", driver.hashCode());
        new NoonDiscountsScrapper(driver, url, scrapperConfig.getOutputDir() + "\\NoonDiscounts\\" + fileName, fileName).call();
    }

    @Async
    public void submitSearchScrapJob(String url, String fileName, String... keywords) {
        submitSearchScrapJob(url, fileName, Arrays.stream(keywords).collect(Collectors.toList()));
    }

    @Async
    public void submitAmazonShippingScrapJob(String url, String fileName) {
        log.info(fileName);
        final WebDriver driver = driverProvider.getObject().getDriver();
        new AmazonShippingScrapper(driver, url, scrapperConfig.getOutputDir() + "\\AmazonJP\\" + fileName, fileName).call();
    }

    @Async
    public void submitSearchScrapJob(String url, String fileName, List<String> keywords) {
        final WebDriver driver = driverProvider.getObject().getDriver();
        log.info("submitSearchScrapJob with driver: {}", driver.hashCode());
        new NoonSearchScrapper(driver, url, scrapperConfig.getOutputDir() + "\\NoonDiscounts\\" + fileName, fileName, keywords).call();
    }


}
