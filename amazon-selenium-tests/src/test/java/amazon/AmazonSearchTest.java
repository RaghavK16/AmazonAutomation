package amazon;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class AmazonSearchTest {

    private WebDriver driver;

    @BeforeClass
    public void setup() {
        // Set up ChromeDriver
        System.setProperty("webdriver.chrome.driver", "chromedriver.exe"); // Update the path to your ChromeDriver
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    private void searchAndAddToCart(String searchTerm) {
        // Navigate to Amazon
        driver.get("https://www.amazon.in");

        // Accept cookies if shown
        try {
            WebElement acceptButton = driver.findElement(By.id("sp-cc-accept"));
            acceptButton.click();
        } catch (Exception ignored) {
        }

        // Search for the device
        WebElement searchBox = driver.findElement(By.id("twotabsearchtextbox"));
        searchBox.clear();
        searchBox.sendKeys(searchTerm);
        searchBox.submit();

        // Click first product link
    //    WebElement firstProduct = driver.findElement(By.cssSelector("div[data-component-type='s-search-result'] h2 a"));
        List<WebElement> firstProducts = driver.findElements(By.xpath("//img[@class='s-image']"));
        System.out.println("Total product images found: " + firstProducts.size());
        if (!firstProducts.isEmpty()) {
            WebElement firstProductImage = firstProducts.get(0);
            firstProductImage.click();
        }


        // Wait for the price element to be visible
        String priceText = "";
        try {
            priceText = driver.findElement(By.id("priceblock_ourprice")).getText();
        } catch (Exception e1) {
            try {
                priceText = driver.findElement(By.id("priceblock_dealprice")).getText();
            } catch (Exception e2) {
                priceText = "Price not found";
            }
        }

        System.out.println("Price of " + searchTerm + ": " + priceText);
    }

    @Test
    public void testSearchAddIPhone() {
        searchAndAddToCart("iPhone");
    }
    @Test
    public void testSearchAddGalaxy() {
        searchAndAddToCart("Galaxy");
    }

}