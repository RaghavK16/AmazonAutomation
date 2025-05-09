package amazon;

import amazon.pages.AmazonPageLocators;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.net.URL;
import java.util.List;

public class AmazonSearchTest {

    String username = "raghavkhullar16";
    String accessKey = "LT_0wIu2qOQIdc6BiX9Ifa81f7bIcZPHDqFVkWmw1KNGkymaXF";
    private WebDriver driver;

    @BeforeClass
    public void setup() throws Exception {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("browserName", "Chrome");
        caps.setCapability("version", "latest");
        caps.setCapability("platform", "macOS Sequoia");
        caps.setCapability("name", "AmazonTest");
        caps.setCapability("build", "AmazonBuild");

        driver = new RemoteWebDriver(
                new URL("https://" + username + ":" + accessKey + "@hub.lambdatest.com/wd/hub"),
                caps
        );
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    private void searchAndAddToCart(String searchTerm) throws InterruptedException {
        driver.get("https://www.amazon.com");

        // Accept cookies if shown
        try {
            WebElement acceptButton = driver.findElement(AmazonPageLocators.ACCEPT_COOKIES_BUTTON);
            acceptButton.click();
        } catch (Exception ignored) {
        }

        // Search for the device
        WebDriverWait wait = new WebDriverWait(driver, 20);
        WebElement searchBox = wait.until(ExpectedConditions.visibilityOfElementLocated(AmazonPageLocators.SEARCH_BOX));
        searchBox.clear();
        searchBox.sendKeys(searchTerm);
        searchBox.submit();

        // Click first product link
        List<WebElement> productsList = driver.findElements(AmazonPageLocators.PRODUCT_IMAGES);
        System.out.println("Total product images found: " + productsList.size());
        if (!productsList.isEmpty()) {
            WebElement firstProductImage = productsList.get(0);
            firstProductImage.click();
        }

        // Adding to Cart
        try {
            WebElement addToCartBtn = wait.until(
                    ExpectedConditions.elementToBeClickable(AmazonPageLocators.ADD_TO_CART_BUTTON)
            );
            addToCartBtn.click();
            System.out.println("Added to cart!");
        } catch (Exception e) {
            System.out.println("Add to Cart button not found or not clickable.");
        }

        // Extract price
        WebElement priceElement = null;

        try {
            priceElement = wait.until(ExpectedConditions.presenceOfElementLocated(AmazonPageLocators.PRICE_WHOLE));
        } catch (Exception ignored) {
        }

        if (priceElement == null) {
            try {
                priceElement = wait.until(ExpectedConditions.presenceOfElementLocated(AmazonPageLocators.PRICE_BLOCK));
            } catch (Exception ignored) {
            }
        }

        if (priceElement != null) {
            System.out.println("Product Price: " + priceElement.getText());
        } else {
            System.out.println("Product price not found.");
        }
    }


    @Test
    public void testSearchAddIPhone() throws InterruptedException {
        searchAndAddToCart("iPhone");
    }

    @Test
    public void testSearchAddGalaxy() throws InterruptedException {
        searchAndAddToCart("Galaxy");
    }
}
