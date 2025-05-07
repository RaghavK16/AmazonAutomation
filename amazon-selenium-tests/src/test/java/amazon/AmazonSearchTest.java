package amazon;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
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

    private void searchAndAddToCart(String searchTerm) throws InterruptedException {
        // Navigate to Amazon
        driver.get("https://www.amazon.com");

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
        List<WebElement> productsList = driver.findElements(By.xpath("//img[@class='s-image']"));
        System.out.println("Total product images found: " + productsList.size());
        if (!productsList.isEmpty()) {
            WebElement firstProductImage = productsList.get(1);
            firstProductImage.click();
        }

        // Adding to Cart
        Thread.sleep(10000);
        WebElement addToCartBtn = driver.findElement(By.xpath("//input[@id='add-to-cart-button']"));
        addToCartBtn.click();
        System.out.println("Added to cart !");

        // Price Display
        List<WebElement> priceList = driver.findElements(By.xpath("//img[@class='s-image']"));
        System.out.println("Total product images found: " + priceList.size());
        if (!priceList.isEmpty()) {
            WebElement priceItem = priceList.get(4);
            String finalPrice = priceItem.getText();
            System.out.println("The price is - "+finalPrice);
        }

    }

    @Test
    public void testSearchAddIPhone() throws InterruptedException {
        try{
            searchAndAddToCart("iPhone");
        }
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    @Test
    public void testSearchAddGalaxy() throws InterruptedException {
        try{
            searchAndAddToCart("Galaxy");
        }
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}