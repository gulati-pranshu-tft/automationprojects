package com.cbla.utils;

import com.cbla.config.properties.TestConfig;
import com.google.common.base.Function;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.Locatable;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.*;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class WebDriverUtil {

    public static boolean isElementPresent(WebDriver driver, String locatorType, String locator) {
        int elements = 0;
        switch (locatorType) {
            case "xpath":
                elements = driver.findElements(By.xpath(locator)).size();
                break;
            case "css":
                elements = driver.findElements(By.cssSelector(locator)).size();
                break;
            case "id":
                elements = driver.findElements(By.id(locator)).size();
                break;
        }
        if (elements < 1) {
            return false;
        } else {
            return true;
        }
    }

    public static void verifyElementIsDisplayed(WebDriver driver, WebElement element, String elementName, boolean isLazyImage) {
        try {
            verifyElementIsDisplayed(driver, element, elementName);
        } catch (AssertionError e) {
            if (isLazyImage) {
                Sleep.sleepthread(5);
                verifyElementIsDisplayed(driver, element, elementName);
            } else {
                throw e;
            }
        }
    }

    public static boolean isElementPresent(WebDriver driver, WebElement parentElement, String locatorType, String locator) {
        int elements = 0;
        switch (locatorType) {
            case "xpath":
                elements = parentElement.findElements(By.xpath(locator)).size();
                break;
            case "css":
                elements = parentElement.findElements(By.cssSelector(locator)).size();
                break;
            case "id":
                elements = parentElement.findElements(By.id(locator)).size();
                break;
        }
        if (elements < 1) {
            return false;
        } else {
            return true;
        }
    }

    public static void verifyElementIsDisplayed(WebElement element, String elementName) {
        Assert.assertTrue(element.isDisplayed(), elementName + " displayed,");
    }

    public static void verifyElementIsDisplayed(WebDriver driver, WebElement element, String elementName) {
        boolean displayed = element.isDisplayed();
        if (!displayed) {
            Assert.fail(elementName + " element is not displayed.");
        }
    }

    public static void verifyElementIsDisplayed(WebDriver driver, List<WebElement> element, String elementName) {
        Assert.assertTrue(((WebElement) element).isDisplayed(), elementName + " displayed,");
    }

    public static void verifyElementIsDisplayed(WebElement element, String elementName, boolean isLazyImage) {
        try {
            verifyElementIsDisplayed(element, elementName);
        } catch (AssertionError e) {
            if (isLazyImage) {
                Sleep.sleepthread(5);
                verifyElementIsDisplayed(element, elementName);
            } else {
                throw e;
            }
        }
    }

    public static void verifyAllElementsDisplayed(WebDriver driver, List<WebElement> elements, String elementName) {
        Assert.assertTrue(elements.size() > 0, "No elements in " + elementName + " list");
        for (int i = 0; i < elements.size(); i++) {
            WebElement element = elements.get(i);
            WebDriverUtil.verifyElementIsDisplayed(driver, element, elementName + "(" + i + ")");
        }
    }

    public static void verifyElementIsDisplayed(SoftAssert sa, WebElement element, String elementName) {
        sa.assertTrue(element.isDisplayed(), elementName + " displayed,");
    }

    public static void waitForPageLoad(WebDriver driver) {

        Wait<WebDriver> wait = new WebDriverWait(driver, 30);
        wait.until(new Function<WebDriver, Boolean>() {
            public Boolean apply(WebDriver driver) {
                System.out.println("Current Window State       : "
                        + String.valueOf(((JavascriptExecutor) driver).executeScript("return document.readyState")));
                return String
                        .valueOf(((JavascriptExecutor) driver).executeScript("return document.readyState"))
                        .equals("complete");
            }
        });
    }

    public static void verifyElementIsNotDisplayed(WebElement element, String elementName) {
        boolean displayed;
        try {
            displayed = element.isDisplayed();
        } catch (NoSuchElementException e) {
            displayed = false;
        }
        if (displayed) {
            Assert.fail(elementName + " element is displayed and should not be displayed");
        }
    }

    public static void verifyElementIsNotDisplayed(SoftAssert sa, WebElement element, String elementName) {
        boolean displayed;
        try {
            displayed = element.isDisplayed();
        } catch (NoSuchElementException e) {
            displayed = false;
        }
        if (displayed) {
            sa.fail(elementName + " element is displayed and should not be displayed");
        }
    }

    //good to use when you need to scroll to an element or hover over element
    public static void moveToElement(WebDriver driver, WebElement element) {
        Actions actions = new Actions(driver);
        actions.moveToElement(element);
        actions.perform();
    }

    public static void moveToElementWithOffset(WebDriver driver, WebElement element, int xOffset, int yOffset) {
        Actions actions = new Actions(driver);
        actions.moveToElement(element, xOffset, yOffset);
        actions.perform();
    }

    public static void scrollToElement(WebDriver driver, WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", element);
        Sleep.sleepthreadMillis(500l);
    }

    public static void scrollToElementHeight(WebDriver driver, WebElement element) {
        int loc = element.getLocation().getY();
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("scroll(0, " + loc + ");");
    }

    public static void scrollToElementHeight(WebDriver driver, WebElement element, int offset) {
        int loc = element.getLocation().getY() + offset;
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("scroll(0, " + loc + ");");
    }

    public static void clickElement(WebDriver driver, WebElement element) {
        try {
            element.click();
        } catch (WebDriverException e) {
            //If driver is not able to directly click on the element
            try {
                scrollToElementHeight(driver, element, -200);
                element.click();
            } catch (WebDriverException e2) {
                //If driver is not able to scroll to element height and click the desired element
                //scrollIntoView is used (during mobile execution)
                try {
                    scrollToElement(driver, element);
                    element.click();
                } catch (WebDriverException e3) {
                    //If element is still not clicked, then scrollBy is used.
                    JavascriptExecutor js = (JavascriptExecutor) driver;
                    js.executeScript("window.scrollBy(0,250)", "");
                    element.click();
                }
            }
        }
    }

    public static void clickElement2(WebDriver driver, WebElement element) {
        try {
            element.click();
        } catch (WebDriverException e) {
            element.sendKeys(Keys.ARROW_DOWN);
            element.sendKeys(Keys.ARROW_DOWN);
            element.click();
        }
    }

    public static void scrollBy(WebDriver driver, int scroll) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0," + scroll + ")", "");
    }

    public static void waitUntilClickable(WebDriver driver, long waitTime, WebElement element) {
        WebDriverWait wait = new WebDriverWait(driver, waitTime);
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public static void waitUntilVisible(WebDriver driver, long waitTime, WebElement element) {
        WebDriverWait wait = new WebDriverWait(driver, waitTime);
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    public static void waitUntilPresence(WebDriver driver, long waitTime, By element) {
        WebDriverWait wait = new WebDriverWait(driver, waitTime);
        wait.until(ExpectedConditions.presenceOfElementLocated(element));
    }

    public static void waitUntilNotVisible(WebDriver driver, long waitTime, WebElement element) {
        WebDriverWait wait = new WebDriverWait(driver, waitTime);
        wait.until(ExpectedConditions.not(ExpectedConditions.visibilityOf(element)));
    }

    public static void waitUtilInvisible(WebDriver driver, long waitTime, WebElement element) {
        WebDriverWait wait = new WebDriverWait(driver, waitTime);
        List<WebElement> elements = new ArrayList<WebElement>();
        elements.add(element);
        wait.until(ExpectedConditions.invisibilityOfAllElements(elements));
    }

    public static void waitUntilElementStopsMoving(WebDriver driver, WebElement element) {
        WebDriverWait wait = new WebDriverWait(driver, 2L);
        wait.until(ExpectedConditions.visibilityOf(element));
        wait.until(WebDriverUtil.elementHasStoppedMoving(element));
    }

    public static ExpectedCondition<Boolean> elementHasStoppedMoving(final WebElement element) {
        return new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                Point initialLocation = ((Locatable) element).getCoordinates().onPage();
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    //Auto-generated catch block
                    e.printStackTrace();
                }
                Point finalLocation = ((Locatable) element).getCoordinates().onPage();
                return initialLocation.equals(finalLocation);

            }
        };
    }

    public static void verifyAllElementsDisplayed(List<WebElement> elements, String elementName) {
        Assert.assertTrue(elements.size() > 0, "No elements in " + elementName + " list");
        for (int i = 0; i < elements.size(); i++) {
            WebElement element = elements.get(i);
            WebDriverUtil.verifyElementIsDisplayed(element, elementName + "(" + i + ")");
        }
    }

    public static void verifyAllElementsDisplayed(SoftAssert sa, List<WebElement> elements, String elementName) {
        sa.assertTrue(elements.size() > 0, "No elements in " + elementName + " list");
        for (int i = 0; i < elements.size(); i++) {
            WebElement element = elements.get(i);
            WebDriverUtil.verifyElementIsDisplayed(sa, element, elementName + "(" + i + ")");
        }
    }

    public static void verifyAllElementsNotDisplayed(List<WebElement> elements, String elementName) {
        for (int i = 0; i < elements.size(); i++) {
            WebElement element = elements.get(i);
            WebDriverUtil.verifyElementIsNotDisplayed(element, elementName + "(" + i + ")");
        }
    }

    public static void scrollToBottom(WebDriver driver) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
    }

    public static void scrollToTop(WebDriver driver) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0, 0);");
    }

    public static void scrollToLastParagraph(WebDriver driver) {
        List<WebElement> paragraphs = driver.findElements(By.tagName("p"));
        moveToElement(driver, paragraphs.get(paragraphs.size() - 1));
    }

    public static void hoverOver(WebDriver driver, WebElement element) {
//        new Actions(driver).moveToElement(element).pause(5000).perform();
        Actions builder = new Actions(driver);
        Actions hoverOverRegistrar = builder.moveToElement(element);
        hoverOverRegistrar.perform();
        Sleep.sleepthread(1); //For Demo
    }

    public static void hoverOverClick(WebDriver driver, WebElement element1, WebElement element2) {
        Actions builder = new Actions(driver);
        Actions hoverOverRegistrar = builder.moveToElement(element1);
        hoverOverRegistrar.perform();
        Sleep.sleepthread(1); //For Demo
        element2.click();
        Sleep.sleepthread(1); //For Demo
    }

    public static void hoverOverVerify(WebDriver driver, WebElement element1, WebElement element2, String elementName) {
        Actions builder = new Actions(driver);
        Actions hoverOverRegistrar = builder.moveToElement(element1);
        hoverOverRegistrar.perform();
        Sleep.sleepthread(2); //For Demo
        WebDriverUtil.waitUntilVisible(driver, 2, element2);
        WebDriverUtil.verifyElementIsDisplayed(element2, elementName);
        System.out.println(elementName + " is present"); //For Demo
    }

    public static void waitForOverlay(WebDriver driver, String overlayId) {
        WebDriverWait wait = new WebDriverWait(driver, 25L);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id(overlayId)));
    }

    public static void waitForOverlay(WebDriver driver, long waitTime, String overlayId) {
        WebDriverWait wait = new WebDriverWait(driver, waitTime);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id(overlayId)));
    }

    //assumes that there are only 2 different handles
    public static String getNewWindowHandle(WebDriver driver, String originalHandle) {
        Set<String> windowIds = driver.getWindowHandles();
        Iterator<String> iterate = windowIds.iterator();
        windowIds = driver.getWindowHandles();
        String handle = iterate.next(); //window id of the main window
        if (handle.equals(originalHandle)) {
            handle = iterate.next();
        }
        return handle;
    }

    public static String getBrowserName(WebDriver driver) {
        Capabilities cap = ((RemoteWebDriver) driver).getCapabilities();
        String browserName = cap.getBrowserName().toLowerCase();
        return browserName;
    }

    public static int getDeviceWidth(WebDriver driver) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        Long width = (Long) js.executeScript("return window.innerWidth");
        return width.intValue();
    }

    public static void verifyElementSpansBrowserWidth(SoftAssert sa, WebDriver driver, WebElement element, String elementName) {
        int browserWidth = getDeviceWidth(driver);
        int elementWidth = element.getSize().getWidth();
        sa.assertTrue(elementWidth <= browserWidth && elementWidth >= (browserWidth - 65), elementName + " spans width of browser (browser width: " + browserWidth + ", element width: " + elementWidth + ")");
    }

    public static void verifyElementSpansBrowserWidth(WebDriver driver, WebElement element, String elementName) {
        int browserWidth = getDeviceWidth(driver);
        int elementWidth = element.getSize().getWidth();
        Assert.assertTrue(elementWidth <= browserWidth && elementWidth >= (browserWidth - 65), elementName + " spans width of browser (browser width: " + browserWidth + ", element width: " + elementWidth + ")");
    }

    public static void verifyElementSpansElementWidth(SoftAssert sa, WebElement element, String elementName, WebElement parentElement, String parentElementName) {
        int elementWidth = element.getSize().getWidth();
        int parentElementWidth = parentElement.getSize().getWidth();
        sa.assertTrue(elementWidth <= parentElementWidth && elementWidth >= (parentElementWidth - 65), elementName + " does not span width of " + parentElementName + " (" + parentElementName + " width: " + parentElementWidth + ", element width: " + elementWidth + ")");
    }

    public static void verifyElementSpansElementWidth(WebElement element, String elementName, WebElement parentElement, String parentElementName) {
        int elementWidth = element.getSize().getWidth();
        int parentElementWidth = parentElement.getSize().getWidth();
        Assert.assertTrue(elementWidth <= parentElementWidth && elementWidth >= (parentElementWidth - 65), elementName + " does not span width of " + parentElementName + " (" + parentElementName + " width: " + parentElementWidth + ", element width: " + elementWidth + ")");
    }

    public static void verifyElementWidth(SoftAssert sa, WebElement element, String elementName, int expectedWidth) {
        int elementWidth = element.getSize().getWidth();
        sa.assertEquals(elementWidth, expectedWidth, "Width of " + elementName);
    }

    public static void verifyElementWidth(WebElement element, String elementName, int expectedWidth) {
        int elementWidth = element.getSize().getWidth();
        Assert.assertEquals(elementWidth, expectedWidth, "Width of " + elementName);
    }

    public static void verifyElementSpansMinimumWidth(SoftAssert sa, WebDriver driver, WebElement element, String elementName, int expectedMinimumWidth) {
        int elementWidth = element.getSize().getWidth();
        sa.assertTrue(elementWidth >= expectedMinimumWidth, elementName + " width smaller than expected (minimum width: " + expectedMinimumWidth + ", actual width: " + elementWidth + ")");
    }

    public static void verifyElementSpansWidth(SoftAssert sa, WebDriver driver, WebElement element, String elementName, int expectedMinimumWidth, int expectedMaximumWidth) {
        int elementWidth = element.getSize().getWidth();
        System.out.println(elementName + ": " + elementWidth);
        sa.assertTrue(elementWidth <= expectedMaximumWidth && elementWidth >= expectedMinimumWidth, elementName + " width (" + elementWidth + ") is not in the expected range (" + expectedMinimumWidth + "-" + expectedMaximumWidth + ")");
    }

    public static void verifyElementHref(SoftAssert sa, WebElement element, String elementName, String expectedURL) {
        String actualURL = element.getAttribute("href");
        sa.assertEquals(actualURL, expectedURL, elementName + " href");
    }

    public static Cookie waitforCookie(WebDriver driver, String cokkiename) {
        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                .withTimeout(30, TimeUnit.SECONDS)
                .pollingEvery(5, TimeUnit.SECONDS)
                .ignoring(NullPointerException.class);
        Cookie fl = wait.until(new java.util.function.Function<WebDriver, Cookie>() {
            public Cookie apply(WebDriver webDriver) {
                return driver.manage().getCookieNamed(cokkiename);
            }
        });
        return fl;
    }

    public static void waitforElement(WebDriver driver, WebElement Element) {
        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                .withTimeout(30, TimeUnit.SECONDS)
                .pollingEvery(5, TimeUnit.SECONDS)
                .ignoring(NoSuchElementException.class);
        wait.until(ExpectedConditions.visibilityOf(Element));
    }

    public static ArrayList getNetworkTabData(WebDriver driver) {
        String scriptToExecute = "var performance = window.performance || window.mozPerformance || window.msPerformance || window.webkitPerformance || {}; var network = performance.getEntries() || {}; return network;";
        ArrayList netData = (ArrayList) ((JavascriptExecutor) driver).executeScript(scriptToExecute);
        return netData;
    }

    public static Map getApplicationTabData(WebDriver driver) {
        String scriptToExecute = "var application = window.localStorage || {}; return application;";
        Map applicationData = (Map) ((JavascriptExecutor) driver).executeScript(scriptToExecute);
        return applicationData;
    }

    public static String getAttributeValue(WebElement element, String attribute) {
        String attributeValue = "";
        try {
            attributeValue = element.getAttribute(attribute);
            if (attributeValue == null)
                throw new NullPointerException();
        } catch (Exception e) {
            attributeValue = "";
        }
        return attributeValue;
    }

    public static List<String> fetchAllElementsText(WebDriver driver, List<WebElement> webElements) {
        waitUntilVisible(driver, TestConfig.NORMAL_WAIT, webElements.get(0));
        List<String> elementsText = new ArrayList<String>();
        webElements.stream().forEach(e -> elementsText.add(e.getText()));
        return elementsText;
    }

    public static void refreshPage(WebDriver driver) {
        driver.navigate().refresh();
    }
}
