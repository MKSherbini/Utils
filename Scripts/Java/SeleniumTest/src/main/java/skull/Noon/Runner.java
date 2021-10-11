package skull.Noon;

import lombok.AllArgsConstructor;
import lombok.Data;
import net.sourceforge.tess4j.Tesseract;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Runner {
    static final ExecutorService executorService = Executors.newFixedThreadPool(5);

    public static void main(String[] args) {
        submitJob("https://www.noon.com/egypt-en/electronics-and-mobiles/daily-deals-eg?limit=200", "electronics.html");
        submitJob("https://www.noon.com/egypt-en/home-and-kitchen/daily-deals-eg?limit=200", "home.html");
        submitJob("https://www.noon.com/egypt-en/tools-and-home-improvement/daily-deals-eg?limit=200", "tools.html");
        submitJob("https://www.noon.com/egypt-en/sports-and-outdoors/daily-deals-eg?limit=200", "sports.html");
        submitJob("https://www.noon.com/egypt-en/office-supplies/daily-deals-eg?limit=200", "office.html");
        executorService.shutdown();
    }

    public static void submitJob(String url, String fileName) {
        executorService.submit(new NoonScrapper(false, url, "D:\\_Temp\\NoonDiscounts\\" + fileName, fileName));
    }
}
