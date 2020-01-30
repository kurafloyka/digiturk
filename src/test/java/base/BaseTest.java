package base;

import com.thoughtworks.gauge.AfterScenario;
import com.thoughtworks.gauge.BeforeScenario;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.FluentWait;

import java.net.URL;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import static java.lang.System.getenv;

public class BaseTest {
    public static final String CHAR_LOWER = "abcdefghijklmnopqrstuvwxyz";
    public static final String CHAR_UPPER = CHAR_LOWER.toUpperCase();
    public static final String NUMBER = "0123456789";
    public static String CSVPath = null;
    public static final String DATA_FOR_RANDOM_STRING = CHAR_LOWER + CHAR_UPPER + NUMBER;
    public static SecureRandom random = new SecureRandom();
    protected static WebDriver driver;
    protected static FluentWait<WebDriver> webDriverWait;


    @BeforeScenario
    public void setUp() throws Exception {
        String baseUrl = "https://connect-th.beinsports.com/en";


        DesiredCapabilities capabilities = DesiredCapabilities.chrome();


            String os = System.getProperty("os.name").toLowerCase();
            if (os.contains("mac")) {
                System.setProperty("webdriver.chrome.driver", "web_driver/chromedriver");
            }
            if (os.contains("win")) {
                System.setProperty("webdriver.chrome.driver", "web_driver/chromedriver.exe");
            }


            driver = new ChromeDriver();



        driver.get(baseUrl);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(3, TimeUnit.MINUTES);
        driver.manage().timeouts().setScriptTimeout(4, TimeUnit.MINUTES);


    }

    @AfterScenario
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }


}
