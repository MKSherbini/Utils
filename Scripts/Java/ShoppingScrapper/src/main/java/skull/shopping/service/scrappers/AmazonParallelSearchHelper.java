package skull.shopping.service.scrappers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import skull.shopping.model.AmazonProduct;
import skull.shopping.page.amazon.SearchPage;
import skull.shopping.provider.DriverProvider;
import skull.shopping.service.creator.AmazonProductCreator;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AmazonParallelSearchHelper {
    private final ScrapperRunner scrapperRunner;

    @Async
    public CompletableFuture<ArrayList<AmazonProduct>> scrapPageRange(String startUrl, int start, int end, int pagesCount, String logId) {
        var allDiscounts = new ArrayList<AmazonProduct>();
        scrapperRunner.scrap(driver -> {
            final AmazonProductCreator creator = new AmazonProductCreator(driver);
            for (var page = start; page <= end; page++) {
                driver.get(startUrl + "&page=" + page);
                log.info("{}: Page-{}/{}", logId, page, pagesCount);
                allDiscounts.addAll(scrapPage(driver, creator));
            }
            return null;
        }, logId);

        return CompletableFuture.completedFuture(allDiscounts);
    }

    public List<AmazonProduct> scrapPage(WebDriver driver, AmazonProductCreator creator) {
        return SearchPage.getAllElements(driver).stream()
                .map(creator::createProductIfWorth)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}
