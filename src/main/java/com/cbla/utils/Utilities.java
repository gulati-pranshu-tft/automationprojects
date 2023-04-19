package com.cbla.utils;

import com.cbla.config.properties.TestConfig;
import com.google.common.base.Throwables;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;


/***
 *
 * Description: Class contains all the reusable functions related to web driver
 */

public class Utilities {

    private static final Logger LOGGER = LogManager.getLogger(Utilities.class);

    private WebDriver driver;
    private WebDriverWait wait;
    private Actions actions;
    private JavascriptExecutor javaScript;
    private Robot robot;

    public Utilities(WebDriver driver) {
        this.driver = driver;
        actions = new Actions(driver);
        javaScript = (JavascriptExecutor) driver;
        try {
            this.robot = new Robot();
        } catch (Exception e) {}
    }

    /**
     * Function to wait for alert to appear
     * @param driver - Webdriver instance
     */
    public static Alert waitForAlertToAppear(WebDriver driver) throws InterruptedException {
        Alert alert = null;
        for (int i = 0; i < 5; i++) {
            try {
                alert = driver.switchTo().alert();
                break;
            } catch (NoAlertPresentException e) {
                Thread.sleep(1000);
                continue;
            }
        }
        return alert;
    }

    /**
     * Function to wait for url to change
     * @param driver - Webdriver instance
     * @param currentUrl - current url value
     */
    public static void waitUntilUrlChange(WebDriver driver, String currentUrl) {
        for (int i = 0; i < 10; i++) {
            String newUrl = driver.getCurrentUrl();
            if (!newUrl.equalsIgnoreCase(currentUrl)) {
                break;
            } else {
                Sleep.sleepthread(1);
            }
        }
    }

    /**
     * Function to wait for new tab to open
     * @return - true/false based upon window status
     */
    public boolean waitUntilNewWindowOpened() {
        try {
            wait = new WebDriverWait(driver, 5);
            wait.until((ExpectedCondition<Boolean>) driverObject -> driverObject.getWindowHandles().size() > 1);
            return true;
        } catch (TimeoutException ex) {
            LOGGER.debug("Wait until new window failed due to :" + Arrays.toString(ex.getStackTrace()));
        }
        return false;
    }

    /**
     * Function to switch to new window
     */
    public void switchToNewWindow() {
        String mainWindowHandle = driver.getWindowHandle();
        Iterator<String> windowsIterator;
        try {
            if (this.waitUntilNewWindowOpened()) {
                windowsIterator = driver.getWindowHandles().iterator();
                while (windowsIterator.hasNext()) {
                    String nextWindowHandle = windowsIterator.next();
                    if (!nextWindowHandle.contains(mainWindowHandle)) {
                        LOGGER.debug("Current Window handles " + driver.getWindowHandles().size());
                        LOGGER.debug("Url of the Current active window  " + driver.getCurrentUrl());
                        LOGGER.debug("Title of the Current active window " + driver.getTitle());
                        driver.switchTo().window(nextWindowHandle);
                        break;
                    }
                }
            } else {
                LOGGER.debug("No new window opens after waiting");
                throw new Exception("No new window opens after waiting");
            }
        } catch (Exception e) {
            LOGGER.debug("Error in waiting for Window handles: " + Arrays.toString(e.getStackTrace()));
        }
    }

    /**
     * Function to close new window
     */
    public void closeNewWindow() {
        String mainWindowHandle = driver.getWindowHandle();
        Iterator<String> windowsIterator;
        try {
            windowsIterator = driver.getWindowHandles().iterator();
            while (windowsIterator.hasNext()) {
                String currentWindowHandle = windowsIterator.next();
                if (!currentWindowHandle.contains(mainWindowHandle)) {
                    driver.close();
                    driver.switchTo().window(currentWindowHandle);
                    break;
                }
            }
        } catch (Exception e) {
            LOGGER.debug("Error in closing window due to: " + Arrays.toString(e.getStackTrace()));
        }
    }

    /**
     * Function to get current url
     * @return - current url value
     */
    public String getCurrentUrl() {
        String text = "";
        try {
            text = driver.getCurrentUrl();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return text;
    }

    /**
     * Fucntion to click on element using javascript
     * @param element - Webelement
     */
    public void clickUsingJavaScript(WebElement element) {
        javaScript.executeScript("arguments[0].click();", element);
    }

    /**
     * Function to zoom using javascript
     * @param element - Webelement
     */
    public void zoomUsingJavaScript(WebElement element) {
        javaScript.executeScript("arguments[0].style.zoom='10%';", element);
    }

    /**
     * Function to get element text using java script
     * @param element - Webelement
     * @return - element text
     */
    public String getElementTextUsingJavaScript(WebElement element) {
        return String.valueOf(javaScript.executeScript(" return arguments[0].innerText;", element));
    }

    /**
     * Function to verify if element exist
     * @param element - Webelement
     * @return - true/false based upon visibility
     */
    public boolean isElementExist(WebElement element) {
        try {
            return element.isDisplayed();
        } catch (Exception e) {
            LOGGER.debug("Element is not visible : " + Throwables.getStackTraceAsString(e));
            return false;
        }
    }

    /**
     * Fucntion to wait for element to exist
     * @param elements - Webelement
     */
    public void waitForElementExists(WebElement elements) {
        try {
            wait = new WebDriverWait(this.driver, TestConfig.LONG_WAIT);
            wait.ignoring(NoSuchElementException.class).ignoring(StaleElementReferenceException.class)
                    .until(ExpectedConditions.visibilityOf(elements));
        } catch (Exception e) {
            LOGGER.debug("Unable to find the element");
        }
    }

    /**
     * Function to click if element exist
     * @param element - Webelement
     */
    public void clickIfElementExists(WebElement element) {
        this.waitForElementExists(element);
        if (this.isElementExist(element))
            element.click();
    }

    /**
     * Fucntion to naviagte without exception
     * @param url - url to navigate
     */
    public void navigateWithoutException(String url) {
        try {
            driver.get(url);
        } catch (Exception ignored) {

        }
    }

    /**
     * Fucntion to highlight element
     * @param element - Webelement
     */
    public void highLighterMethod(WebElement element) {
        javaScript.executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red;');", element);
    }

    /**
     * Function to delete all cookies before navigation
     * @param url - url to navigate
     */
    public void deleteAllCookiesAndNavigateToUrl(String url) {
        driver.manage().deleteAllCookies();
        driver.get(url);
        WebDriverUtil.waitForPageLoad(driver);
    }

    /**
     * Function to upload document
     * @param element - Webelement
     * @param filePath - filepath of file
     */
    public void uploadDocument(WebElement element, String filePath) {
        File file = new File(filePath);
        element.sendKeys(file.getAbsolutePath());
    }

    /**
     * Function to upload file
     * @param fileName - filepath
     */
    public void uploadFile(String fileName) {
        Sleep.sleepthread(2);
        String filePath = System.getProperty("user.dir") + fileName;
        StringSelection stringSelection = new StringSelection(filePath);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, stringSelection);
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);
    }

    /**
     * Function to fetch dropdown value
     * @param driver - Webdriver instance
     * @param dropdownButton - Dropdown button name
     * @param dropDown - dropdown element
     * @return - list of all dropdown elements value
     */
    public List<String> fetchDropDowns(WebDriver driver, WebElement dropdownButton, List<WebElement> dropDown, boolean scrollableDropdown, WebElement nextScrollButton) {
        WebDriverUtil.clickElement(driver, dropdownButton);
        List<String> dropDownList = new ArrayList<>();
        for (WebElement element : dropDown) {
            String dropdownItem = element.getText().trim();
            dropDownList.add(dropdownItem);
        }
        if (dropDownList.get(0).isEmpty()) {
            dropDownList.remove(0);
        }
        if (scrollableDropdown) {
            WebDriverUtil.clickElement(driver, nextScrollButton);
            try {
                WebDriverUtil.waitUntilNotVisible(driver, TestConfig.NORMAL_WAIT, nextScrollButton);
            } catch (Exception e) {
            }
            for (WebElement element : dropDown) {
                String dropdownItem = element.getText().trim();
                dropDownList.add(dropdownItem);
            }
            if (dropDownList.get(0).isEmpty()) {
                dropDownList.remove(0);
            }
        }
        return dropDownList;
    }
}
