package app;

import app.enums.Browser;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.InputStream;
import java.net.URL;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
public class Scrapper {
    private final WebDriver driver = new DriverProvider(Browser.FIREFOX).getDriver();
    private static final String username = "BuffedSkull@gmail.com";
    private static final String password = "tempPass";
    private static final String DOWNLOAD_DIR = "_downloads";
    private static final Duration maxWait = Duration.ofMillis(2000);
    private static int ignoreRefresh;
    private List<String> ignoreList = List.of(
            "https://www.bodybuilding.com/exercises/seated-dumbbell-overhead-triceps-press",
            "https://www.bodybuilding.com/exercises/fyr-medicine-ball-jumping-sumo-squat",
            "https://www.bodybuilding.com/exercises/pistol-squat"
    );


    public static void main(String[] args) {
        new Scrapper();
    }

    public Scrapper() {
        login();
//        driver.get("https://www.bodybuilding.com/exercises/finder?muscle=chest");
//        driver.get(driver.findElements(By.cssSelector(".ExCategory-results a")).get(0).getAttribute("href"));
        List.of("lats", "middle-back", "lower-back", "neck", "quadriceps", "hamstrings", "calves", "traps", "shoulders", "abdominals", "glutes", "biceps", "adductors", "abductors")
                .forEach(this::scrapPages);

        driver.quit();
    }

    private void scrapPages(String muscle) {
        String url = "https://www.bodybuilding.com/exercises/finder/%d?muscle=" + muscle;
        int page = muscle.equals("quadriceps") ? 18 : 1;
        while (true) {
            driver.get(url.formatted(page));
            final List<WebElement> elements = driver.findElements(By.cssSelector(".ExCategory-results .ExResult-cell.ExResult-cell--nameEtc"));
            if (elements.isEmpty()) break;
            page++;
            elements.stream()
                    .map(el -> el.findElement(By.cssSelector(".ExHeading.ExResult-resultsHeading a")).getAttribute("href"))
                    .toList().stream().forEach(exerciseUrl -> {
                        ignoreRefresh = 3;
                        boolean done = false;
                        while (!done) {
                            try {
                                downloadExercise(exerciseUrl, muscle);
                                done = true;
                            } catch (Exception e) {
                                log.info("exerciseUrl = " + exerciseUrl);
                                e.printStackTrace();
                                if (driver.getPageSource().contains("Whitelabel Error Page"))
                                    done = true;
                            }
                        }
                    });
        }
    }


    private void downloadExercise(String url, String muscle) {
        if (driver.getCurrentUrl().equals(url) && ignoreRefresh > 0)
            ignoreRefresh--;
        else
            driver.get(url);

        final List<WebElement> instructions = driver.findElements(By.cssSelector(".ExDetail-section.ExDetail-guide img"));
        final List<WebElement> images = driver.findElements(By.cssSelector(".ExDetail-section.ExDetail-photos img"));
        final Optional<WebElement> videoOptional = SeleniumDriverUtil.findIfElementExists(driver, By.cssSelector(".jw-video"));
        final WebElement info = driver.findElement(By.cssSelector(".ExAppContent"));
        final String infoText = info.getAttribute("innerText");
        final String exerciseName = infoText.split("\n", 2)[0];
        final String exerciseNameSafe = exerciseName.replaceAll("\\\\", "-").replaceAll("/", "-");
        if (videoOptional.isEmpty() || videoOptional.get().getAttribute("src") == null) return;
        final String videoUrl = videoOptional.get().getAttribute("src");
        if (videoUrl == null || videoUrl.isBlank()) return;
        log.info("exerciseName = " + exerciseName);
        log.info("exerciseNameSafe = " + exerciseNameSafe);

        var content = infoText +
                "\n" +
                instructions.stream().map(
                        instruction -> String.format("![](%s)", instruction.getAttribute("src"))
                ).collect(Collectors.joining("\n"));
        final Path path = Path.of(DOWNLOAD_DIR, muscle, exerciseNameSafe);
        log.info("path = " + path);
        try {
            Files.createDirectories(path);

            final String videoName = exerciseNameSafe + ".mp4";
            downloadFile(Path.of(path.toString(), videoName), videoUrl);
            var imageNames = new ArrayList<String>();
            for (int i = 0; i < images.size(); i++) {
                try {
                    final String img = String.format("%s image-%d.jpg", exerciseNameSafe, i + 1);
                    downloadFile(Path.of(path.toString(), img), images.get(i).getAttribute("src"));
                    imageNames.add(img);
                } catch (java.io.FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
            content = content.replaceAll("SHOW FEMALE IMAGES AND VIDEOS\n", "");
            content = content.replace("Benefits", "### Benefits");
            content = content.replace(exerciseName + " Images", "### " + exerciseName + " Images");
            content = content.replace(exerciseName + " Instructions", "### " + exerciseName + " Instructions");
            content = content.replace("Alternative Exercises for", "### Alternative Exercises for");
            final int imagesIndex = content.lastIndexOf("### " + exerciseName);
            log.info("content = " + content);
            log.info("imagesIndex = " + imagesIndex);
            content = "# " + content.substring(0, imagesIndex) + imageNames.stream().map(s -> String.format("![[%s]]", s)).collect(Collectors.joining("\n")) + "\n" + content.substring(imagesIndex);

            final String[] splits = content.split("\n");
            int i = 0;
            splits[0] = splits[0] + "\n" + String.format("![[%s]]", videoName);
            for (; i < splits.length; i++) {
                if (splits[i].startsWith("### Benefits")) break;
            }
            i++;
            if (i >= splits.length) i = 1;
            for (; i < splits.length; i++) {
                if (splits[i].startsWith("### ")) break;
                if (splits[i].contains("seconds of"))
                    splits[i] = "";
                else
                    splits[i] = "- " + splits[i];
            }
            for (i = 0; i < splits.length; i++) {
                if (splits[i].startsWith("Variations") || splits[i].startsWith("Caution")) {
                    splits[i] = "#### " + splits[i];
                    final int colonIndex = splits[i].indexOf(":");
                    if (splits[i].length() > colonIndex + 3) {
                        splits[i] = splits[i].substring(0, colonIndex + 2) + "\n" + splits[i].substring(colonIndex + 2);
                    }
                }
            }
            content = String.join("\n", splits);
            Files.writeString(Path.of(path.toString(), exerciseNameSafe + ".md"), content);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void downloadFile(Path path, String fileUrl) throws IOException {
        URL url = new URL(fileUrl);

        // Open a connection to the URL and get the InputStream
        try (InputStream inputStream = url.openStream()) {
            // Use Files.copy to save the InputStream to the specified path
            Files.copy(inputStream, path, StandardCopyOption.REPLACE_EXISTING);
        }
    }

    public void login() {
        driver.get("https://www.bodybuilding.com/combined-signin");
        driver.findElement(By.cssSelector("#ispbxii_1")).sendKeys(username);
        driver.findElement(By.cssSelector("form.combined-sign-in--input-form:nth-child(2) > div:nth-child(2) > div:nth-child(1) > input:nth-child(2)")).sendKeys(password);
//        driver.findElement(By.cssSelector(".combined-sign-in--button")).click();

        while (!driver.getCurrentUrl().equals("https://shop.bodybuilding.com/")) ;
    }

    private void scrollDown() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("""
                window.scrollBy(0,2000);
                """);
    }
}
