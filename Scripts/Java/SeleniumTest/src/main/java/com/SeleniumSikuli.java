package com;

import com.until.HelperSleep;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
//import org.sikuli.api.robot.desktop.DesktopKeyboard;
import org.sikuli.hotkey.Keys;
import org.sikuli.script.Image;
import org.sikuli.script.Pattern;
import org.sikuli.script.Screen;

import java.awt.image.BufferedImage;
import java.nio.file.Paths;

public class SeleniumSikuli {

    static Screen screen = new Screen();
    //    static DesktopKeyboard keyboard = new DesktopKeyboard();
    static WebDriver driver;
    static ChromeOptions options = new ChromeOptions();
    static String baseDir = Paths.get(".").toAbsolutePath().normalize().toString();
//    static String dir = System.getProperty("user.dir")+"/images/";

//    public static void main (String [] args){
//        System.setProperty("webdriver.chrome.driver", baseDir +"\\chromedriver.exe");
//
//        options.addArguments("--no-sandbox");
//
//        driver = new ChromeDriver(options);
//        driver.manage().window().maximize();
//        driver.get("https://www.google.com");
//        HelperSleep.sleep(3000);
//
//        while(true){
//            HelperSleep.sleep(100);
//            try{
//                System.out.println("here");
//                screen.click(baseDir +"\\images\\searchpaint.png");
//                break;
//            } catch (Exception e){
//                continue;
//            }
//        }
//
//        keyboard.type("lol");
//        keyboard.keyDown(Keys.ENTER);
//        keyboard.keyUp(Keys.ENTER);
////        driver.findElement(By.name("q")).sendKeys(org.openqa.selenium.Keys.ENTER);
//
//        HelperSleep.sleep(2000);
//
//        driver.quit();
//    }

    public static void main(String[] args) throws InterruptedException {
//        System.setProperty("webdriver.chrome.driver", baseDir+"\\chromedriver.exe");
//        options.addArguments("--no-sandbox");
//        driver = new ChromeDriver(options);
        System.out.println("baseDir = " + baseDir);
//        new FirefoxOptions().addArguments("--no-sandbox")
        driver = new FirefoxDriver();
        driver.manage().window().maximize();
        driver.get("https://www.google.com");
//        HelperSleep.sleep(2000);
        while (true) {
//            HelperSleep.sleep(2000);
            try {
                System.out.println("here");
                System.out.println("screen.capture() = " + screen.capture().save(baseDir, "screen"));
                testSimilarity(baseDir + "\\pics\\dd.png");
                testSimilarity(baseDir + "\\pics\\d3.png");
                testSimilarity(Image.create(baseDir + "\\pics\\dd.png").resize(1.5f));
                testSimilarity(Image.create(baseDir + "\\pics\\d3.png").resize(.60f));
                testSimilarity(Image.create(baseDir + "\\pics\\d3.png").resize(.30f));
                screen.click(new Pattern(baseDir + "\\pics\\dd.png").similar(.3));
                break;
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
        }
        screen.type("Toqa");
        screen.keyDown(Keys.ENTER);
        screen.keyUp(Keys.ENTER);
        HelperSleep.sleep(2000);
        driver.quit();
    }

    private static void testSimilarity(BufferedImage target) {
        System.out.println("exists8 = " + screen.exists(new Pattern(target).similar(.8)));
        System.out.println("exists7 = " + screen.exists(new Pattern(target).similar(.7)));
        System.out.println("exists6 = " + screen.exists(new Pattern(target).similar(.6)));
        System.out.println("exists5 = " + screen.exists(new Pattern(target).similar(.5)));
        System.out.println("exists4 = " + screen.exists(new Pattern(target).similar(.4)));
    }

    private static void testSimilarity(String target) {
        System.out.println("exists8 = " + screen.exists(new Pattern(target).similar(.8)));
        System.out.println("exists7 = " + screen.exists(new Pattern(target).similar(.7)));
        System.out.println("exists6 = " + screen.exists(new Pattern(target).similar(.6)));
        System.out.println("exists5 = " + screen.exists(new Pattern(target).similar(.5)));
        System.out.println("exists4 = " + screen.exists(new Pattern(target).similar(.4)));
    }

}
