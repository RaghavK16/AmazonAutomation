package amazon;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;

import java.time.Duration;

public class AmazonSearchTest {

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeMethod
    public void setup() {
        // Setup ChromeDriver using WebDriverManager
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, 15);
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    private void searchAndAddToCart(String searchTerm) {
        driver.get("https://www.amazon.com");

        // Accept cookies popup if it appears (some regions)
        try {
            WebElement consentButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("sp-cc-accept")));
            consentButton.click();
        } catch (Exception e) {
            // No consent popup, proceed
        }

        // Locate the search box and enter search term
        WebElement searchBox = wait.until(ExpectedConditions.elementToBeClickable(By.id("twotabsearchtextbox")));
        searchBox.clear();
        searchBox.sendKeys(searchTerm);
        searchBox.submit();

        // Wait for the search results and click the first product link
        WebElement firstProduct = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("div[data-component-type='s-search-result'] h2 a")));
        firstProduct.click();

        // Switch to the product page tab if new tab opened (Amazon often opens in same tab)
        for (String handle : driver.getWindowHandles()) {
            driver.switchTo().window(handle);
        }

        // Wait for price. Try common price IDs on Amazon product pages.
        String priceText = "";
        try {
            WebElement priceElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("priceblock_ourprice")));
            priceText = priceElement.getText();
        } catch (Exception e1) {
            try {
                WebElement dealPriceElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("priceblock_dealprice")));
                priceText = dealPriceElement.getText();
            } catch (Exception e2) {
                priceText = "Price not found";
            }
        }

        System.out.println("Price of " + searchTerm + ": " + priceText);

        // Add to cart
        try {
            WebElement addToCartBtn = wait.until(ExpectedConditions.elementToBeClickable(By.id("add-to-cart-button")));
            addToCartBtn.click();

            // Wait for confirmation message or cart update
            wait.until(ExpectedConditions.or(
                    ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#attach-added-to-cart-message")),
                    ExpectedConditions.visibilityOfElementLocated(By.id("hlb-ptc-btn-native"))
            ));
        } catch (Exception e) {
            System.out.println("Could not add " + searchTerm + " to cart.");
        }
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
