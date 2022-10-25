package skull.shopping.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Data
@Slf4j
@Configuration
@ConfigurationProperties("scrapper")
public class ScrapperConfig {
    private int maxShippingPrice;
    private int minDiscount;
    private String outputDir;

    @PostConstruct
    void init() {
        log.info(this.toString());

        Product.maxShippingPrice = maxShippingPrice;
        Product.minDiscount = minDiscount;
    }
}
