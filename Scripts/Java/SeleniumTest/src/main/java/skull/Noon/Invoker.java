package skull.Noon;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import skull.Noon.service.ScrapDiscounts;

import javax.annotation.PostConstruct;

@Slf4j
@Component
public class Invoker {
    private final ScrapDiscounts scrapDiscounts;

    public Invoker(ScrapDiscounts scrapDiscounts) {
        this.scrapDiscounts = scrapDiscounts;
    }

    @PostConstruct
    public void onStart() throws InterruptedException {
        invokeDiscountsScrap();
    }

    public void invokeDiscountsScrap() throws InterruptedException {
        // https://www.noon.com/egypt-en/eg-electronics
        // https://www.noon.com/egypt-en/dailydeals-21-eg
//        scrapDiscounts.submitJob("https://www.noon.com/egypt-en/electronics-and-mobiles/daily-deals-eg", "electronics.html");
//        scrapDiscounts.submitJob("https://www.noon.com/egypt-en/home-and-kitchen/daily-deals-eg", "home.html");
        scrapDiscounts.submitJob("https://www.noon.com/egypt-en/tools-and-home-improvement/daily-deals-eg", "tools.html");
        scrapDiscounts.submitJob("https://www.noon.com/egypt-en/sports-and-outdoors/daily-deals-eg", "sports.html");
//        scrapDiscounts.submitJob("https://www.noon.com/egypt-en/office-supplies/daily-deals-eg", "office.html");
    }

}
