package skull.shopping.model;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import skull.shopping.AppConstants;

import javax.annotation.PostConstruct;

@Data
@Slf4j
@Configuration
@ConfigurationProperties("scrapper")
public class ScrapperConfig {
    private int maxShippingPrice;
    private int minDiscount;
    private SearchRelation searchRelation;
    private int minPrice;
    private int maxPrice;
    private int pageSaveFreq;
    private int batchSize;

    private String outputDir;

    @PostConstruct
    void init() {
        log.info(this.toString());

        AppConstants.MAX_SHIPPING_PRICE = maxShippingPrice;
        AppConstants.MIN_DISCOUNT = minDiscount;
        AppConstants.SEARCH_RELATION = searchRelation;
        AppConstants.MIN_PRICE = minPrice;
        AppConstants.MAX_PRICE = maxPrice;
        AppConstants.PAGE_SAVE_FREQ = pageSaveFreq;
    }
}
