package skull.shopping.service.scrappers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Service;
import skull.shopping.provider.DriverProvider;
import skull.shopping.utils.Timer;

import java.util.function.Function;

@Slf4j
@Service
@RequiredArgsConstructor
public class ScrapperRunner {
    private final ObjectProvider<DriverProvider> driverProvider;

    public <R> R scrap(Function<WebDriver, R> function, String logId) {
        WebDriver driver = driverProvider.getObject().getDriver();
        var timer = new Timer();
        try {
            return function.apply(driver);
        } catch (NoSuchElementException e) {
            e.printStackTrace();
        } finally {
            driver.quit();
            timer.log(logId);
        }
        return null;
    }
}
