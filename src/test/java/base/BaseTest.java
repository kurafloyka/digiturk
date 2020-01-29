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

import static java.lang.System.getenv;

public class BaseTest {
    public static final String CHAR_LOWER = "abcdefghijklmnopqrstuvwxyz";
    public static final String CHAR_UPPER = CHAR_LOWER.toUpperCase();
    public static final String NUMBER = "0123456789";
    public static ArrayList<String> emailList = new ArrayList<>();
    public static String CSVPath = null;
    public static final String DATA_FOR_RANDOM_STRING = CHAR_LOWER + CHAR_UPPER + NUMBER;
    public static SecureRandom random = new SecureRandom();
    protected static WebDriver driver;
    protected static FluentWait<WebDriver> webDriverWait;


    @BeforeScenario
    public void setUp() throws Exception {
        String baseUrl = "https://connect-th.beinsports.com/en";



        DesiredCapabilities capabilities = DesiredCapabilities.chrome();

        if (StringUtils.isNotEmpty(getenv("key"))) {
            ChromeOptions options = new ChromeOptions();
            options.addArguments("test-type");
            options.addArguments("disable-popup-blocking");
            options.addArguments("ignore-certificate-errors");
            options.addArguments("disable-translate");
            options.addArguments("start-maximized");
            options.addArguments("--no-sandbox");
            capabilities.setCapability(ChromeOptions.CAPABILITY, options);
            capabilities.setCapability("key", System.getenv("key"));
            driver = new RemoteWebDriver(new URL("example.com"), capabilities);
            ((RemoteWebDriver) driver).setFileDetector(new LocalFileDetector());

        } else {

            String os = System.getProperty("os.name").toLowerCase();
            if(os.contains("mac")){
                System.setProperty("webdriver.chrome.driver","web_driver/chromedriver");
            }
            if(os.contains("win")){
                System.setProperty("webdriver.chrome.driver","web_driver/chromedriver.exe");
            }


            driver = new ChromeDriver();
        }
        /**driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
         driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
         driver.manage().timeouts().setScriptTimeout(30, TimeUnit.SECONDS);*/

        driver.get(baseUrl);
        driver.manage().window().fullscreen();

    }

    @AfterScenario
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }


}
