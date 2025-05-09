package amazon.pages;

import org.openqa.selenium.By;

public class AmazonPageLocators {

    // Locators
    public static final By ACCEPT_COOKIES_BUTTON = By.id("sp-cc-accept");
    public static final By SEARCH_BOX = By.id("twotabsearchtextbox");
    public static final By PRODUCT_IMAGES = By.xpath("//img[@class='s-image']");
    public static final By ADD_TO_CART_BUTTON = By.id("add-to-cart-button");

    // Common price selectors
    public static final By PRICE_WHOLE = By.id("priceblock_ourprice"); // Older ID
    public static final By PRICE_BLOCK = By.id("corePriceDisplay_desktop_feature_div"); // New layout
}