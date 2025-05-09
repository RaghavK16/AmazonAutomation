package amazon;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.*;

import java.net.URL;

public class LambdaTestDemo {
    WebDriver driver;

    String username = "raghavkhullar16";
    String accessKey = "LT_0wIu2qOQIdc6BiX9Ifa81f7bIcZPHDqFVkWmw1KNGkymaXF";

    @BeforeMethod
    public void setup() throws Exception {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("browserName", "Chrome");
        caps.setCapability("version", "latest");
        caps.setCapability("platform", "Windows 10");
        caps.setCapability("name", "SimpleTest");
        caps.setCapability("build", "SimpleBuild");

        driver = new RemoteWebDriver(
                new URL("https://" + username + ":" + accessKey + "@hub.lambdatest.com/wd/hub"),
                caps
        );
    }

    @Test
    public void testLambdaTestHomePage() {
        driver.get("https://www.lambdatest.com/");
        System.out.println("Title is: " + driver.getTitle());
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
