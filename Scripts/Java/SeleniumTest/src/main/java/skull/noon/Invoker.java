package skull.noon;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import skull.noon.provider.AsyncProvider;

import javax.annotation.PostConstruct;

@Slf4j
@Component
public class Invoker {
    private final AsyncProvider asyncProvider;

    public Invoker(AsyncProvider asyncProvider) {
        this.asyncProvider = asyncProvider;
    }

    @PostConstruct
    public void onStart() throws InterruptedException {
        invokeDiscountsScrap();
        invokeEnhancedSearch();
    }

    private void invokeEnhancedSearch() {

    }

    private void invokeDiscountsScrap() {
        // https://www.noon.com/egypt-en/eg-electronics
        // https://www.noon.com/egypt-en/dailydeals-21-eg
//        scrapDiscounts.submitDiscountsScrapJob("https://www.noon.com/egypt-en/electronics-and-mobiles/daily-deals-eg", "electronics.html");
//        scrapDiscounts.submitDiscountsScrapJob("https://www.noon.com/egypt-en/home-and-kitchen/daily-deals-eg", "home.html");
        asyncProvider.submitDiscountsScrapJob("https://www.noon.com/egypt-en/tools-and-home-improvement/daily-deals-eg", "tools.html");
        asyncProvider.submitDiscountsScrapJob("https://www.noon.com/egypt-en/sports-and-outdoors/daily-deals-eg", "sports.html");
//        scrapDiscounts.submitDiscountsScrapJob("https://www.noon.com/egypt-en/office-supplies/daily-deals-eg", "office.html");
    }

}
