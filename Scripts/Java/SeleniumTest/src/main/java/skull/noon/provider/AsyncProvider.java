package skull.noon.provider;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import skull.noon.output.FilePrinter;
import skull.noon.service.NoonDiscountsScrapper;
import skull.noon.service.NoonSearchScrapper;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AsyncProvider {
    private final ObjectProvider<DriverProvider> driverProvider;

    @Value("${scrapper.output-dir}")
    private String outputDir;

    public AsyncProvider(ObjectProvider<DriverProvider> driverProvider) {
        this.driverProvider = driverProvider;
    }

    @Async
    public void submitDiscountsScrapJob(String url, String fileName) {
        final WebDriver driver = driverProvider.getObject().getDriver();
        log.info("submitDiscountsScrapJob with driver: {}", driver.hashCode());
        new NoonDiscountsScrapper(driver, new FilePrinter(outputDir + "\\NoonDiscounts\\" + fileName, fileName), url).call();
    }

    @Async
    public void submitSearchScrapJob(String url, String fileName, String... keywords) {
        submitSearchScrapJob(url, fileName, Arrays.stream(keywords).collect(Collectors.toList()));
    }

    @Async
    public void submitSearchScrapJob(String url, String fileName, List<String> keywords) {
        final WebDriver driver = driverProvider.getObject().getDriver();
        log.info("submitSearchScrapJob with driver: {}", driver.hashCode());
        new NoonSearchScrapper(driver, new FilePrinter(outputDir + "\\NoonSearch\\" + fileName, fileName), url, keywords).call();
    }


}
