package skull.shopping.service.scrappers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import skull.shopping.model.AmazonProduct;
import skull.shopping.model.InvokerConfig;
import skull.shopping.model.ScrapperConfig;
import skull.shopping.provider.DriverProvider;
import skull.shopping.service.output.AmazonProductPrinter;
import skull.shopping.utils.SafeParser;
import skull.shopping.utils.SeleniumDriverUtil;
import skull.shopping.utils.Timer;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Slf4j
@Service
@RequiredArgsConstructor
public class AmazonParallelSearchScrapper {
    private final ScrapperRunner scrapperRunner;
    private final AmazonParallelSearchHelper parallelSearchHelper;
    private final ScrapperConfig scrapperConfig;

    @Async
    public List<AmazonProduct> scrapByPageRanges(InvokerConfig.AppRequest k) {
        String startUrl = k.getUrl(), filePath = scrapperConfig.getOutputDir() + k.getFile(), logId = k.getFile();
        final AmazonProductPrinter printer = new AmazonProductPrinter(filePath, logId);
        var timer = new Timer();

        var pagesCount = getPages(startUrl);
        log.info("{}:scrapping {} pages from {}", printer.getLogID(), pagesCount, startUrl);
        ArrayList<CompletableFuture<ArrayList<AmazonProduct>>> allResults = new ArrayList<>();
        for (var page = 1; page <= pagesCount; page += scrapperConfig.getBatchSize()) {
            final CompletableFuture<ArrayList<AmazonProduct>> asyncResult = parallelSearchHelper
                    .scrapPageRange(startUrl, page, Math.min(page + scrapperConfig.getBatchSize() - 1, pagesCount), pagesCount, logId);
            allResults.add(asyncResult);
        }
        CompletableFuture.allOf(allResults.toArray(new CompletableFuture[0])).join();
        var allDiscounts = allResults.stream()
                .map(arrayListCompletableFuture -> {
                    try {
                        return arrayListCompletableFuture.get();
                    } catch (InterruptedException | ExecutionException e) {
                        throw new RuntimeException(e);
                    }
                })
                .flatMap(Collection::stream)
                .sorted(Collections.reverseOrder())
                .toList();
        printer.print(allDiscounts);
        timer.log(logId);
        return allDiscounts;
    }

    int getPages(String startUrl) { // why stuck on 10?
        return scrapperRunner.scrap(driver -> {
            driver.get(startUrl);

            final List<By> options = List.of(
                    By.xpath("//a[contains(@class,'s-pagination-next')]//preceding-sibling::span[1]"),
                    By.xpath("//a[contains(@class,'s-pagination-next')]//preceding-sibling::a[1]")
            );

            return options.stream()
                    .map(o -> SeleniumDriverUtil.findIfElementExists(driver, o))
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .map(WebElement::getText)
                    .mapToInt(o -> SafeParser.parseInt(o, 1))
                    .max().orElse(1);
        }, "getPages");
    }
}
