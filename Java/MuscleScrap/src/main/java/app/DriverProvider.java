package app;

import app.enums.Browser;
import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.time.Duration;

@Slf4j
public class DriverProvider {
    private boolean isVisible = true;
    @Getter
    private final WebDriver driver;

    public DriverProvider() {
        this(Browser.FIREFOX);
    }

    public DriverProvider(Browser browser) {
        switch (browser) {
            default:
            case FIREFOX:
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver(getFirefoxOptions());
                break;
            case CHROME:
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver(getChromeOptions());
                break;
        }
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
//        driver.manage().window().setSize(new Dimension(500, 1000));
    }

    private FirefoxOptions getFirefoxOptions() {
        var options = new FirefoxOptions();
        options.setHeadless(!isVisible);
        return options;
    }

    private ChromeOptions getChromeOptions() {
        var options = new ChromeOptions();
        options.setHeadless(!isVisible);
        return options;
    }

    void cleanup() {
        log.info("app.DriverProvider.cleanup");
        driver.quit();
    }
}
