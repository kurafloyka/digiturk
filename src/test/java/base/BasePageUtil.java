package base;


import helper.ElementHelper;
import helper.StoreHelper;
import model.ElementInfo;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.interactions.*;


public class BasePageUtil {

    WebDriver driver;
    FluentWait<WebDriver> fluentWait;

    public BasePageUtil(WebDriver driver) {

        this.driver = driver;
        fluentWait = setFluentWait(300);
    }

    public FluentWait<WebDriver> setFluentWait(int timeout) {

        fluentWait = new FluentWait<WebDriver>(driver);
        fluentWait.withTimeout(timeout, TimeUnit.SECONDS)
                .pollingEvery(30000, TimeUnit.MILLISECONDS)
                .ignoring(NoSuchElementException.class);
        return fluentWait;


    }


    public WebElement findElement(String key) {
        By infoParam = getBy(key);
        WebElement webElement = fluentWait
                .until(ExpectedConditions.presenceOfElementLocated(infoParam));
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({behavior: 'smooth', block: 'center', inline: 'center'})",
                webElement);

        return webElement;
    }

    public List<WebElement> findElements(String key) {
        By infoParam = getBy(key);
        return driver.findElements(infoParam);
    }

    public void clickElement(WebElement element) {


        element.click();
    }

    public void clickElementBy(String key) {
        findElement(key).click();
    }

    public void hoverElement(WebElement element) {
        Actions actions = new Actions(driver);
        actions.moveToElement(element).build().perform();
    }

    public void hoverElementwithkey(String key) {
        WebElement webElement = findElement(key);
        Actions actions = new Actions(driver);
        actions.moveToElement(webElement).build().perform();
    }

    public void sendKeyESC(String key) {
        findElement(key).sendKeys(Keys.ESCAPE);

    }

    public boolean isDisplayed(WebElement element) {
        return element.isDisplayed();
    }

    public boolean isDisplayedBy(By by) {
        return driver.findElement(by).isDisplayed();
    }

    public String getPageSource() {
        return driver.switchTo().alert().getText();
    }

    public String getCurrentDateThenAddDays(int daysToAdd) {
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

        Date currentDate = new Date();

        // convert date to calendar
        Calendar c = Calendar.getInstance();
        c.setTime(currentDate);

        c.add(Calendar.DAY_OF_MONTH, daysToAdd);

        return dateFormat.format(c.getTime());
    }

    public String randomString(int stringLength) {

        Random random = new Random();
        char[] chars = "ABCDEFGHIJKLMNOPQRSTUWVXYZabcdefghijklmnopqrstuwvxyz0123456789".toCharArray();
        String stringRandom = "";
        for (int i = 0; i < stringLength; i++) {

            stringRandom = stringRandom + String.valueOf(chars[random.nextInt(chars.length)]);
        }

        return stringRandom;
    }

    public WebElement findElementWithKey(String key) {
        return findElement(key);
    }

    public String getElementText(String key) {
        return findElement(key).getText();
    }

    public String getElementAttributeValue(String key, String attribute) {
        return findElement(key).getAttribute(attribute);
    }

    public void actionSendKeys(String key, String text) {
        Actions actions = new Actions(driver);
        actions.moveToElement(findElement(key));
        actions.click();
        actions.sendKeys(text);
        actions.build().perform();
    }

    public void actionClickElement(String key) {

        Actions actions = new Actions(driver);
        actions.moveToElement(findElement(key));
        actions.click();
        actions.build().perform();
    }

    public void actionClearElement(String key) {

        WebElement element = findElement(key);
        element.clear();
        element.sendKeys("a");
        Actions actions = new Actions(driver);
        actions.sendKeys(Keys.BACK_SPACE).build().perform();


    }

    public void sendKeys(String key, String text) {

        findElement(key).sendKeys(text);
    }

    public void clear(String key) {

        findElement(key).clear();
    }

    public void sendKeys(String key, String attributeText, String text) {

        findElement(key).sendKeys(attributeText, text);
    }

    public void sendKeys(String key, Keys keys) {

        findElement(key).sendKeys(keys);
    }

    public String getText(String key) {

        return findElement(key).getText();
    }

    public String getAttribute(String key, String attribute) {

        return findElement(key).getAttribute(attribute);
    }


    public void javaScriptClicker(WebDriver driver, WebElement element) {

        JavascriptExecutor jse = ((JavascriptExecutor) driver);
        jse.executeScript("var evt = document.createEvent('MouseEvents');"
                + "evt.initMouseEvent('click',true, true, window, 0, 0, 0, 0, 0, false, false, false, false, 0,null);"
                + "arguments[0].dispatchEvent(evt);", element);
    }

    public By getBy(String key) {

        ElementInfo elementInfo = getElementInfo(key);
        return ElementHelper.getElementInfoToBy(elementInfo);
    }

    public ElementInfo getElementInfo(String key) {

        return StoreHelper.INSTANCE.findElementInfoByKey(key);
    }

    public void switchFrameByIndex(int index) {
        driver.switchTo().frame(index);

    }

    public void switchFrameByIndex(String name) {
        driver.switchTo().frame(name);
    }

    public void switchMainFrame() {
        driver.switchTo().defaultContent();
    }

    public void waitByMilliSeconds(long milliseconds) {

        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void waitBySeconds(long seconds) {

        waitByMilliSeconds(seconds * 1000);
    }

    public void waitForThePage(String key) {
        By infoParam = getBy(key);
        fluentWait
                .until(ExpectedConditions.presenceOfElementLocated(infoParam));

        fluentWait
                .until(ExpectedConditions.invisibilityOfElementLocated(infoParam));
    }

    public void waitForTheElement(String key) {
        By infoParam = getBy(key);
        fluentWait.until(ExpectedConditions.elementToBeClickable(infoParam));
    }


}