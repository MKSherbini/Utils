package skull.Noon;

import java.io.IOException;

public class Runner {
    public static void main(String[] args) throws IOException {
//        NoonOutput.printToFile("D:\\_Temp\\noonDiscounts.html", NoonOutput.renderHtmlOutput(new NoonScrapper(false, "https://www.noon.com/egypt-en/daily-deals-eg?limit=150")
//                .scrap()));
//        scrap("https://www.noon.com/egypt-en/electronics-and-mobiles/daily-deals-eg?limit=200", "electronics.html");
//        scrap("https://www.noon.com/egypt-en/home-and-kitchen/daily-deals-eg?limit=200", "home.html");
        scrap("https://www.noon.com/egypt-en/tools-and-home-improvement/daily-deals-eg?limit=50", "tools.html");
//        scrap("https://www.noon.com/egypt-en/sports-and-outdoors/daily-deals-eg?limit=200", "sports.html");
//        scrap("https://www.noon.com/egypt-en/office-supplies/daily-deals-eg?limit=200", "office.html");
    }

    public static void scrap(String url, String fileName) {
        new Thread(() -> {
            new NoonScrapper(true, url, "D:\\_Temp\\NoonDiscounts\\" + fileName, fileName)
                    .scrap();
        }).start();
    }
}
