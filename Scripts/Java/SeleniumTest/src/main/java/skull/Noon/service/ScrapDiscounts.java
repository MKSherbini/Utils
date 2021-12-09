package skull.Noon.service;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import skull.Noon.provider.DriverProvider;

@Service
@Slf4j
public class ScrapDiscounts {
    private final ObjectProvider<DriverProvider> driverProvider;
    @Value("${scrapper.output-dir}")
    private String outputDir;

    public ScrapDiscounts(ObjectProvider<DriverProvider> driverProvider) {
        this.driverProvider = driverProvider;
    }

    @Async
    public void submitJob(String url, String fileName) {
        final WebDriver driver = driverProvider.getObject().getDriver();
        log.info("submitJob with driver: {}", driver.hashCode());
        new NoonScrapper(url, "D:\\_Temp\\NoonDiscounts\\" + fileName, fileName, driver).call();
    }
}
