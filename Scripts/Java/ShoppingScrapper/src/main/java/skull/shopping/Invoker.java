package skull.shopping;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import skull.shopping.model.InvokerConfig;
import skull.shopping.provider.AsyncProvider;
import skull.shopping.service.scrappers.AmazonParallelSearchScrapper;

import javax.annotation.PostConstruct;

@Slf4j
@Component
@RequiredArgsConstructor
public class Invoker {
    private final AsyncProvider asyncProvider;

    private final InvokerConfig invokerConfig;
    private final AmazonParallelSearchScrapper amazonParallelSearchScrapper;

    @PostConstruct
    public void onStart() {
//        invokeDiscountsScrap();
//        invokeEnhancedSearch();
//        executor.getActiveCount();
        new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            invokeAmazonShippingScrap();
//            while (executor.getPoolSize() > 0) {
//                log.info(String.valueOf(executor.getPoolSize()));
//                log.info(String.valueOf(executor.getActiveCount()));
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//            applicationContext.close();
        }).start();
//        invokeAmazonShippingScrap();

    }

    private void invokeAmazonShippingScrap() {
        invokerConfig.getAppRequests().forEach(
                k -> {
                    log.info(k.toString());
//                    asyncProvider.submitAmazonShippingScrapJob(k.getUrl(), k.getFile());
                    amazonParallelSearchScrapper.scrapByPageRanges(k);
                }
        );
//        List.of(
////                "nails+clipper",
//                "wireless+gaming+mouse"
////                "fries+cutter"
////                "epomaker"
////                "keyboard+mechanical+wireless",
////                "keyboard",
////                "keyboard+mechanical",
////                "glasses",
////                "wallet+rfid",
////                "3d+printer",
////                "3d+printer+filament",
////                "WINSINN+Extruder",
////                "BMG+Extruder"
//        ).forEach(k -> {
//            log.info(k);
//            asyncProvider.submitAmazonShippingScrapJob("https://www.amazon.co.jp/s?k=" + k, k + ".html");
//        });
//        asyncProvider.submitAmazonShippingScrapJob("https://www.amazon.co.jp/s?k=wireless+mechanical+keyboard&rh=n%3A2151970051&dc&language=en&ds=v1%3AMwNgWk1omBtr%2BpaUPJ4cQxH4TXba0ofBUON3NGZi34I&qid=1664153668&rnid=2321267051&sprefix=wireless+mecha%2Caps%2C269&ref=sr_nr_n_1",
//                "category+wireless+mechanical+keyboard.html");
//        asyncProvider.submitAmazonShippingScrapJob("https://www.amazon.co.jp/s?k=spy+camera&i=electronics&crid=108X2JBVVH5M4&sprefix=spy+camera%2Celectronics%2C249&ref=nb_sb_noss_1",
//                "category+spy+camera.html");
//        asyncProvider.submitAmazonShippingScrapJob("https://www.amazon.co.jp/s?k=3d+printer&i=industrial&rh=n%3A3445393051&dc&language=en&ds=v1%3AmxaU6OmE6ICGteGheDH0%2BvFJJXoEm%2FVT47unB2XR1qg&crid=IIJ829DW70EX&qid=1664154826&rnid=3534780051&sprefix=3d+printer%2Caps%2C256&ref=sr_nr_p_n_price_fma_1",
//                "category+3d+printer.html");
//        asyncProvider.submitAmazonShippingScrapJob("https://www.amazon.co.jp/s?k=Backpack&i=shoes&rh=n%3A2016926051&dc&language=en&ds=v1%3AmTvGHool7aqKf6AHIwTBRhk0Ks25u279qZa5SeD2xPw&crid=2R5XCOWZHEO2R&qid=1664214386&rnid=2017263051&sprefix=backpack%2Caps%2C261&ref=sr_nr_p_n_price_fma_2",
//                "category+Backpack.html");
//        asyncProvider.submitAmazonShippingScrapJob("https://www.amazon.co.jp/s?k=bag&i=shoes&crid=SUDZS0R8BI14&sprefix=bag%2Cshoes%2C474&ref=nb_sb_noss_1",
//                "category+bag.html");
//        asyncProvider.submitAmazonShippingScrapJob("https://www.amazon.co.jp/s?k=glasses&i=hpc&crid=3PHJX19HN0RL9&sprefix=glasses%2Chpc%2C260&ref=nb_sb_noss_1",
//                "category+glasses.html");
//        asyncProvider.submitAmazonShippingScrapJob("https://www.amazon.co.jp/s?k=dual+extruder&i=industrial&ref=nb_sb_noss",
//                "category+dual+extruder.html");
//        asyncProvider.submitAmazonShippingScrapJob("https://www.amazon.co.jp/s?me=A2YOSOBLM6RH0D&language=en&marketplaceID=A1VC38T7YXB528",
//                "seller+epomakerjp.html");
//        asyncProvider.submitAmazonShippingScrapJob("https://www.amazon.co.jp/s?k=mechanical+blue+rgb+switch&i=computers&crid=BNU4HVZL4I&sprefix=mechanical+blue+rgb+switch%2Ccomputers%2C299&ref=nb_sb_noss",
//                "category+mechanical+blue+rgb+switch.html");

    }

    private void invokeEnhancedSearch() {
        asyncProvider.submitSearchScrapJob("https://www.noon.com/egypt-en/search/?q=boter%20samsung%20cover", "a52s_cover.html",
                "a52", "samsung");
    }

    private void invokeDiscountsScrap() {
        // https://www.noon.com/egypt-en/eg-electronics
        // https://www.noon.com/egypt-en/dailydeals-21-eg
        asyncProvider.submitDiscountsScrapJob("https://www.noon.com/egypt-en/electronics-and-mobiles/daily-deals-eg", "electronics.html");
//        asyncProvider.submitDiscountsScrapJob("https://www.noon.com/egypt-en/home-and-kitchen/daily-deals-eg", "home.html");
//        asyncProvider.submitDiscountsScrapJob("https://www.noon.com/egypt-en/tools-and-home-improvement/daily-deals-eg", "tools.html");
//        asyncProvider.submitDiscountsScrapJob("https://www.noon.com/egypt-en/sports-and-outdoors/daily-deals-eg", "sports.html");
//        asyncProvider.submitDiscountsScrapJob("https://www.noon.com/egypt-en/office-supplies/daily-deals-eg", "office.html");
    }

}
