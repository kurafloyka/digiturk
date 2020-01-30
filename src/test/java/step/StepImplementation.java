package step;


import base.BasePageUtil;
import base.BaseTest;
import com.thoughtworks.gauge.Step;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;


public class StepImplementation extends BaseTest {

    private final static Logger logger = Logger.getLogger(StepImplementation.class.getName());


    BasePageUtil methods;
    public static int DEFAULT_MAX_ITERATION_COUNT = 150;
    public static int DEFAULT_MILLISECOND_WAIT_AMOUNT = 100;
    private static String SAVED_ATTRIBUTE;

    public StepImplementation() {

        methods = new BasePageUtil(driver);
    }

    public static String getSavedAttribute() {
        return SAVED_ATTRIBUTE;
    }

    @Step("Switch to frame by index <index>")
    public void switchToFrameByIndex(int index) {
        methods.switchFrameByIndex(index);
    }

    @Step("Switch to frame by name <name>")
    public void switchToFrameByIndex(String name) {
        methods.switchFrameByIndex(name);
    }

    @Step("Switch to main frame")
    public void switchToMainFrame() {
        methods.switchMainFrame();
    }

    @Step("Wait <milliSeconds> milliSeconds")
    public void waitByMilliSeconds(long milliSeconds) {
        methods.waitByMilliSeconds(milliSeconds);
        logger.info(milliSeconds + " milisaniye bekledi");
    }

    @Step({"Wait <seconds> seconds", "<saniye> saniye bekle"})
    public void waitBySeconds(long seconds) {
        methods.waitBySeconds(seconds);
        logger.info(seconds + " saniye bekledi");
    }

    @Step("print page source")
    public void printPageSource() {
        System.out.println(methods.getPageSource());
        logger.info(" sayfa kaynağını görüntüledi");
    }


    @Step({"Wait for element then click <key>",
            "Elementi bekle ve sonra tıkla <key>"})
    public void checkElementExistsThenClick(String key) {
        getElementWithKeyIfExists(key);
        clickElement(key);
        logger.info(" elementi bekledi ve sonra tıkladı");

    }

    @Step({"Click to element <key>",
            "Elementine tıkla <key>"})
    public void clickElement(String key) {

        WebElement element = methods.findElement(key);
        methods.hoverElement(element);
        waitByMilliSeconds(500);


        methods.clickElement(element);
        logger.info(key + " elemente tıkladı");


    }


    @Step("SegmentAltindanIlkSecenegıSec")
    public void tikla() {
        driver.findElement(By.xpath("//*[contains(@id,'results')]/li[1]/div")).click();
    }


    @Step({"Check if element <key> exists",
            "Wait for element to load with key <key>",
            "Element var mı kontrol et <key>",
            "Elementinin yüklenmesini bekle <key>"})
    public WebElement getElementWithKeyIfExists(String key) {

        By by = methods.getBy(key);

        int loopCount = 0;
        while (loopCount < DEFAULT_MAX_ITERATION_COUNT) {
            if (driver.findElements(by).size() > 0) {
                return driver.findElement(by);
            }
            loopCount++;
            waitByMilliSeconds(DEFAULT_MILLISECOND_WAIT_AMOUNT);
            logger.info(key + " elementinin sayfada olduğu kontrol edildi");
        }
        Assert.fail("Element: '" + key + "' doesn't exist.");
        return null;
    }

    @Step("Go to <url> address")
    public void goToUrl(String url) {
        driver.get(url);
        logger.info(" verilen" + url + " adresine gitti");
    }

    @Step({"Wait for element to load with key <key>",
            "Elementinin yüklenmesini bekle key <key>"})
    public void waitElementLoadWithCss(String key) {
        int loopCount = 0;
        while (loopCount < DEFAULT_MAX_ITERATION_COUNT) {
            if (driver.findElements(methods.getBy(key)).size() > 0) {
                return;
            }
            loopCount++;
            waitByMilliSeconds(DEFAULT_MILLISECOND_WAIT_AMOUNT);
            logger.info(key + "elementinin sayfada yüklenmesi beklenildi");
        }
        Assert.fail("Element: '" + key + "' doesn't exist.");
    }

    @Step({"Check if element <key> exists else print message <message>",
            "Element <key> var mı kontrol et yoksa hata mesajı ver <message>"})
    public void getElementWithKeyIfExistsWithMessage(String key, String message) {

        By by = methods.getBy(key);

        int loopCount = 0;
        while (loopCount < DEFAULT_MAX_ITERATION_COUNT) {
            if (driver.findElements(by).size() > 0) {
                return;
            }
            loopCount++;
            waitByMilliSeconds(DEFAULT_MILLISECOND_WAIT_AMOUNT);
        }
        Assert.fail(message);
    }


    @Step({" <key> Click on element if available",
            "Element <key> varsa tıkla"})
    public void existClickByKey(String key) {

        System.out.println("varsa tıklaya girdi");

        //  By by = methods.getBy(key);

        WebElement element;
        element = methods.findElement(key);

        if (element != null) {
            clickElement(key);
        }
    }


    @Step({"Check if element <key> not exists",
            "Element yok mu kontrol et <key>"})
    public void checkElementNotExists(String key) {
        By by = methods.getBy(key);

        int loopCount = 0;
        while (loopCount < DEFAULT_MAX_ITERATION_COUNT) {
            if (driver.findElements(by).size() == 0) {
                return;
            }
            loopCount++;
            waitByMilliSeconds(DEFAULT_MILLISECOND_WAIT_AMOUNT);
        }
        Assert.fail("Element '" + key + "' still exist.");
    }


    @Step({"Write value <text> to element <key>",
            "<text> textini elemente yaz <key>"})
    public void sendKeys(String text, String key) {
        if (!key.equals("")) {

            methods.sendKeys(key, text);
            logger.info(text + " değeri" + key + " elementine yazdı ");
        }
    }

    @Step({"Clear to element <key>",
            "temizle <key>"})
    public void clear(String key) {
        if (!key.equals("")) {

            methods.clear(key);
            logger.info(key + " elementine clear edildi ");
        }
    }

    @Step({"Click with javascript to key <key>",
            "Javascript ile key tıkla <key>"})
    public void javascriptClickerWithCss(String key) {
        Assert.assertTrue("Element bulunamadı", methods.isDisplayedBy(methods.getBy(key)));
        methods.javaScriptClicker(driver, driver.findElement(methods.getBy(key)));
        logger.info(key + " elementine javascript ile tıkladı");

    }

    @Step({"Check if current URL contains the value <expectedURL>",
            "Şuanki URL <url> değerini içeriyor mu kontrol et"})
    public void checkURLContainsRepeat(String expectedURL) {
        int loopCount = 0;
        String actualURL = "";
        while (loopCount < DEFAULT_MAX_ITERATION_COUNT) {
            actualURL = driver.getCurrentUrl();

            if (actualURL != null && actualURL.contains(expectedURL)) {
                return;
            }
            loopCount++;
            waitByMilliSeconds(DEFAULT_MILLISECOND_WAIT_AMOUNT);
            logger.info(expectedURL + " şu anki URL değerini içeriyor mu kontrol edildi");
        }
        Assert.fail(
                "Actual URL doesn't match the expected." + "Expected: " + expectedURL + ", Actual: "
                        + actualURL);
    }

    @Step({"Send TAB key to element <key>",
            "Elemente TAB keyi yolla <key>"})
    public void sendKeyToElementTAB(String key) {
        methods.sendKeys(key, Keys.TAB);
        logger.info(key + " TAB basıldı");
    }

    @Step({"Send BACKSPACE key to element <key>",
            "Elemente BACKSPACE keyi yolla <key>"})
    public void sendKeyToElementBACKSPACE(String key) {
        methods.sendKeys(key, Keys.BACK_SPACE);
        logger.info(key + " BACK_SPACE basıldı");

    }

    @Step({"Send ESCAPE key to element <key>",
            "Elemente ESCAPE keyi yolla <key>"})
    public void sendKeyToElementESCAPE(String key) {
        methods.sendKeys(key, Keys.ESCAPE);
        logger.info(key + " ESCAPE basıldı");

    }

    @Step({"Send ENTER key to element <key>", "Elemente ENTER key yolla <key>"})
    public void sendKeyToElementENTER(String key) {
        methods.sendKeys(key, Keys.ENTER);
        logger.info(key + " ENTER basıldı");
    }


    @Step({"Check if element <key> has attribute <attribute>",
            "<key> elementi <attribute> niteliğine sahip mi"})
    public void checkElementAttributeExists(String key, String attribute) {
        WebElement element = methods.findElement(key);

        int loopCount = 0;

        while (loopCount < DEFAULT_MAX_ITERATION_COUNT) {
            if (element.getAttribute(attribute) != null) {
                return;
            }
            loopCount++;
            waitByMilliSeconds(DEFAULT_MILLISECOND_WAIT_AMOUNT);
            logger.info(key + " elementi" + attribute + "niteliğine sahip mi kontrol edildi");
        }
        Assert.fail("Element DOESN't have the attribute: '" + attribute + "'");
    }

    @Step({"Check if element <key> not have attribute <attribute>",
            "<key> elementi <attribute> niteliğine sahip değil mi"})
    public void checkElementAttributeNotExists(String key, String attribute) {
        WebElement element = methods.findElement(key);

        int loopCount = 0;

        while (loopCount < DEFAULT_MAX_ITERATION_COUNT) {
            if (element.getAttribute(attribute) == null) {
                return;
            }
            loopCount++;
            waitByMilliSeconds(DEFAULT_MILLISECOND_WAIT_AMOUNT);
        }
        Assert.fail("Element STILL have the attribute: '" + attribute + "'");
    }

    @Step({"Check if <key> element's attribute <attribute> equals to the value <expectedValue>",
            "<key> elementinin <attribute> niteliği <value> değerine sahip mi"})
    public void checkElementAttributeEquals(String key, String attribute, String expectedValue) {
        WebElement element = methods.findElement(key);

        String actualValue;
        int loopCount = 0;
        while (loopCount < DEFAULT_MAX_ITERATION_COUNT) {
            actualValue = element.getAttribute(attribute).trim();
            if (actualValue.equals(expectedValue)) {
                return;
            }
            loopCount++;
            waitByMilliSeconds(DEFAULT_MILLISECOND_WAIT_AMOUNT);
            logger.info(key + " elementi" + attribute + "niteliği" + expectedValue + " değerine sahip mi kontrol edildi");
        }
        Assert.fail("Element's attribute value doesn't match expected value");
    }

    @Step({"Check if <key> element's attribute <attribute> contains the value <expectedValue>",
            "<key> elementinin <attribute> niteliği <value> değerini içeriyor mu"})
    public void checkElementAttributeContains(String key, String attribute, String expectedValue) {
        WebElement element = methods.findElement(key);

        String actualValue;
        int loopCount = 0;
        while (loopCount < DEFAULT_MAX_ITERATION_COUNT) {
            actualValue = element.getAttribute(attribute).trim();
            if (actualValue.contains(expectedValue)) {
                return;
            }
            loopCount++;
            waitByMilliSeconds(DEFAULT_MILLISECOND_WAIT_AMOUNT);
            logger.info(key + " elementi" + attribute + "niteliği" + expectedValue + " değerine içeriyor mu kontrol edildi");

        }
        Assert.fail("Element's attribute value doesn't contain expected value");
    }

    @Step({"Write <value> to <attributeName> of element <key>",
            "<value> değerini <attribute> niteliğine <key> elementi için yaz"})
    public void setElementAttribute(String value, String attributeName, String key) {
        String attributeValue = methods.getAttribute(key, attributeName);
        methods.sendKeys(key, attributeValue, value);
        logger.info(value + " değerini" + attributeName + "niteliğine" + key + " elementi için yazdırıldı");

    }

    @Step({"Write <value> to <attributeName> of element <key> with Js",
            "<value> değerini <attribute> niteliğine <key> elementi için JS ile yaz"})
    public void setElementAttributeWithJs(String value, String attributeName, String key) {
        WebElement webElement = methods.findElement(key);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].setAttribute('" + attributeName + "', '" + value + "')", webElement);
        logger.info(value + " değerini" + attributeName + "niteliğine" + key + " elementi için JS ile yazdırıldı");

    }

    @Step({"Clear text of element <key>",
            "<key> elementinin text alanını temizle"})
    public void clearInputArea(String key) {
        methods.findElement(key).clear();
        logger.info(key + " elementinin text alanını temizledi");
    }

    @Step({"Clear text of element <key> with BACKSPACE",
            "<key> elementinin text alanını BACKSPACE ile temizle"})
    public void clearInputAreaWithBackspace(String key) {
        methods.actionClearElement(key);
        logger.info(key + " elementinin text alanını BACKSPACE ile temizledi");
    }

    @Step({"Change page zoom to <value>%",
            "Sayfanın zoom değerini değiştir <value>%"})
    public void chromeZoomOut(String value) {
        JavascriptExecutor jsExec = (JavascriptExecutor) driver;
        jsExec.executeScript("document.body.style.zoom = '" + value + "%'");
        logger.info("sayfanın zoom değeri değiştirildi");
    }


    @Step({"Open new tab",
            "Yeni sekme aç"})
    public void chromeOpenNewTab() {
        ((JavascriptExecutor) driver).executeScript("window.open()");
        logger.info("yen sekme açıldı");
    }

    @Step({"Focus on tab number <number>",
            "<number> numaralı sekmeye odaklan"})//Starting from 1
    public void chromeFocusTabWithNumber(int number) {
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(number - 1));
        logger.info(number + " numaralı sekmeye odaklandı");
    }

    @Step({"Focus on last tab",
            "Son sekmeye odaklan"})
    public void chromeFocusLastTab() {
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(tabs.size() - 1));
        logger.info(" Son sekmeye odaklandı");

    }

    @Step({"Focus on frame with <key>",
            "Frame'e odaklan <key>"})
    public void chromeFocusFrameWithNumber(String key) {
        WebElement webElement = methods.findElement(key);
        driver.switchTo().frame(webElement);
    }

    @Step({"Save attribute <attribute> value of element <key>",
            "<attribute> niteliğini sakla <key> elementi için"})
    public void saveAttributeValueOfElement(String attribute, String key) {
        SAVED_ATTRIBUTE = methods.getAttribute(key, attribute);
        System.out.println("Saved attribute value is: " + SAVED_ATTRIBUTE);
    }

    @Step({"Write saved attribute value to element <key>",
            "Kaydedilmiş niteliği <key> elementine yaz"})
    public void writeSavedAttributeToElement(String key) {
        methods.sendKeys(key, SAVED_ATTRIBUTE);
    }

    @Step("Check if <int> amount element exists with same key <key>")
    public void checkElementCount(int expectedAmount, String key) {
        int loopCount = 0;

        while (loopCount < DEFAULT_MAX_ITERATION_COUNT) {
            if (expectedAmount == methods.findElements(key).size()) {
                return;
            }
            loopCount++;
            waitByMilliSeconds(DEFAULT_MILLISECOND_WAIT_AMOUNT);
        }
        Assert.fail(
                "Expected element count failed. Expected amount:" + expectedAmount + " Actual amount:"
                        + methods.findElements(key).size());
    }

    @Step({"Check if element <key> contains text <expectedText>",
            "<key> elementi <text> değerini içeriyor mu kontrol et"})
    public void checkElementContainsText(String key, String expectedText) {
        Boolean containsText = methods.getText(key).contains(expectedText);
        Assert.assertTrue("Expected text is not contained", containsText);
    }

    @Step({"Refresh page",
            "Sayfayı yenile"})
    public void refreshPage() {
        driver.navigate().refresh();
    }

    @Step({"Write random value to element <key>",
            "<key> elementine random değer yaz"})
    public void writeRandomValueToElement(String key) {
        methods.sendKeys(key, methods.randomString(15));
    }

    @Step({"Write random email to element <key>",
            "<key> elementine random email yaz"})
    public void writeRandomEmailToElement(String key) {
        methods.sendKeys(key, methods.randomString(15) + "@gmail.com");
    }

    @Step({"Write random value to element <key> starting with <text>",
            "<key> elementine <text> değeri ile başlayan random değer yaz"})
    public void writeRandomValueToElement(String key, String startingText) {
        String randomText = startingText + methods.randomString(15);
        methods.sendKeys(key, randomText);
    }

    @Step({"Print element text by key <key>",
            "Elementin text değerini yazdır key <key>"})
    public void printElementText(String key) {
        System.out.println(methods.getText(key));
    }

    @Step({"Write value <string> to element <key> with focus",
            "<string> değerini <key> elementine focus ile yaz"})
    public void sendKeysWithFocus(String text, String key) {
        methods.actionSendKeys(key, text);
    }

    @Step({"Click to element <key> with focus",
            "<key> elementine focus ile tıkla"})
    public void clickElementWithFocus(String key) {
        methods.actionClickElement(key);
    }

    @Step({"Write date with <int> days added to current date to element <key>",
            "Bugünkü tarihe <int> gün ekle ve <key> elementine yaz"})
    public void writeDateWithDaysAdded(int daysToAdd, String key) {
        if (!key.equals("")) {
            methods.sendKeys(key, methods.getCurrentDateThenAddDays(daysToAdd));
        }
    }

    @Step({"Accept Chrome alert popup",
            "Chrome uyarı popup'ını kabul et"})
    public void acceptChromeAlertPopup() {
        driver.switchTo().alert().accept();
    }

    @Step("<key> elementine kadar swipe yap")
    public void swipetoElement(String key) {

        WebElement element = methods.findElement(key);

        Actions ts = new Actions(driver);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", element);
        ts.moveToElement(element).build().perform();
        waitBySeconds(1);
        logger.info(key + "elemente kadar swipe yapıldı");


    }


    @Step("<key> elementten random sec")
    public void randomChoose(String key) {

        List<WebElement> productList = methods.findElements(key);

        final Random random = new Random();

        int sayi = random.nextInt(productList.size());

        System.out.println(productList.get(sayi).getText());
        productList.get(sayi).click();
        logger.info(key + " random seçildi");
    }

    @Step("<key> li elementin üzerine gel")
    public void hoverElementKey(String key) {
        waitBySeconds(5);
        methods.hoverElementwithkey(key);
        logger.info(key + " elementin üzerine geldi");
    }


    @Step("<kelimeBilgisi> sayfada kontrol et")
    public void sayfadaKelimeyiKontrolEt(String kelimeBilgisi) {
        logger.info("Sayfa kaynagi kontrol ediliyor.");

        waitBySeconds(5);
        Assert.assertEquals(true, driver.getPageSource().contains(kelimeBilgisi));
    }

    @Step("Scroll ile sayfayı aşağı indir")
    public void scrollToGetindex() {
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("window.scrollBy(0,100000)", "");
    }

    @Step("Scroll ile sayfayı bir miktar aşağı indir")
    public void scrollABitToGetindex() {
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("window.scrollBy(0,600)", "");
    }

    @Step("Xpath li <xpath> elementin uzerine gel")
    public void moveToElements(String xpath) {


        Actions actions = new Actions(driver);

        WebElement menuOption = driver.findElement(By.xpath(xpath));

        actions.moveToElement(menuOption).perform();
        System.out.println(xpath + " : elementin uzerine gelindi");

    }


    @Step("<key> li elemente olusan Listeyi upload et")
    public void uploadFile(String key) {

        driver.findElement(By.xpath(key))
                .sendKeys(System.getProperty("user.dir")
                        + "/" + CSVPath);


    }


    public static String generateRandomString(int length) {
        if (length < 1) throw new IllegalArgumentException();

        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {

            // 0-62 (exclusive), random returns 0-61
            int rndCharAt = random.nextInt(DATA_FOR_RANDOM_STRING.length());
            char rndChar = DATA_FOR_RANDOM_STRING.charAt(rndCharAt);

            // debug
            //System.out.format("%d\t:\t%c%n", rndCharAt, rndChar);

            sb.append(rndChar);

        }

        return sb.toString();

    }

    @Step("frame degistir")
    public void frameDegistir() {

        WebElement iFrame = driver.findElement(By.id("pagesframe"));
        driver.switchTo().frame(iFrame);
    }

    @Step("Önceki frame dön")
    public void öncekiFrame() {
        driver.switchTo().parentFrame();    // to move back to parent frame
        driver.switchTo().defaultContent(); // to move back to most parent or main frame
    }


    @Step("Sayfanın yüklenmesini bekle")
    public void waitPage() {
        methods.waitForThePage("overlay");
        waitByMilliSeconds(500);
    }

    @Step("<key> elementinin tıklanabilir olmasını bekle")
    public void waitForTheElement(String key) {
        methods.waitForTheElement(key);
        waitByMilliSeconds(500);
    }

    @Step("<xpath> test")
    public void test(String value) {


        WebElement element = driver.findElement(By.xpath(value));
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(value)));


    }
}



