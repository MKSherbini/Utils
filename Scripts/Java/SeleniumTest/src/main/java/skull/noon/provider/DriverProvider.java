package skull.noon.provider;

import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.interactions.Actions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.time.Duration;

@Component
@Scope("prototype")
@Slf4j
public class DriverProvider {
    @Value("${scrapper.browser.visible}")
    private boolean isVisible;
    @Getter
    private WebDriver driver;
    private final String url = "https://www.noon.com/egypt-en/";

    @PostConstruct
    public void init() {
        WebDriverManager.firefoxdriver().setup();
        driver = new FirefoxDriver(getFirefoxOptions());
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofMinutes(1));
        driver.get(url);
        final String currentWindowHandle = driver.getWindowHandle();
        new Actions(driver).keyDown(Keys.CONTROL).click(driver.findElement(By.tagName("body"))).perform();
        driver.switchTo().window(currentWindowHandle);
    }

    private FirefoxOptions getFirefoxOptions() {
        var firefoxOptions = new FirefoxOptions();
        firefoxOptions.setHeadless(!isVisible);
        return firefoxOptions;
    }

    @PreDestroy // not called on prototype
    void cleanup() {
        log.info("DriverProvider.cleanup");
        driver.quit();
    }
}
