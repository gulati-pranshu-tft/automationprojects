package com.cbla.utils;

import com.gargoylesoftware.htmlunit.WebClient;
import com.cbla.core.TestCore;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.io.IOException;

public class PageStatus {

    public static int ExpectedStatus = 200;
    public static WebClient webClient;


    public static int getStatusCode(String URL) throws IOException {
        WebClient webClient = new WebClient();
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        webClient.getOptions().setJavaScriptEnabled(false);
        int code = webClient.getPage(URL).getWebResponse().getStatusCode();
        webClient.close();
        return code;
    }


    //Internal Pages
    public static void verifyIntPageWithStatus(WebDriver driver, WebElement item, String URL, String elementName) throws IOException {
        WebDriverUtil.verifyElementIsDisplayed(driver, item, elementName);
        String currentHref = item.getAttribute("href");
        System.out.println("Status of " + currentHref + " is " + getStatusCode(currentHref)); //For Demo
        Assert.assertEquals(getStatusCode(currentHref), ExpectedStatus);
        Assert.assertEquals(currentHref, TestCore.baseURL + URL);
        System.out.println(elementName + " is present"); //For Demo
    }

    public static void verifyIntPageWithOutStatus(WebDriver driver, WebElement item, String URL, String elementName) {
        WebDriverUtil.verifyElementIsDisplayed(driver, item, elementName);
        String currentHref = item.getAttribute("href");
        Assert.assertEquals(currentHref, TestCore.baseURL + URL);
        System.out.println(elementName + " is present"); //For Demo

    }

    //External Pages
    public static void verifyExtPageWithStatus(WebDriver driver, WebElement item, String URL, String elementName) throws IOException {
        WebDriverUtil.verifyElementIsDisplayed(driver, item, elementName);
        String currentHref = item.getAttribute("href");
        System.out.println("Status of " + currentHref + " is " + getStatusCode(currentHref)); //For Demo
        Assert.assertEquals(getStatusCode(currentHref), ExpectedStatus);
        Assert.assertEquals(currentHref, URL);
        System.out.println(elementName + " is present"); //For Demo
    }

    public static void verifyExtPageWithOutStatus(WebDriver driver, WebElement item, String URL, String elementName) {
        WebDriverUtil.verifyElementIsDisplayed(driver, item, elementName);
        String currentHref = item.getAttribute("href");
        Assert.assertEquals(currentHref, URL);
        System.out.println(elementName + " is present"); //For Demo
    }

    //Special Case
    public static void verifyIntSpecialWithStatus(WebDriver driver, WebElement item, String URL, String elementName) throws IOException {
        WebDriverUtil.verifyElementIsDisplayed(driver, item, elementName);
        String currentHref = item.getAttribute("href");
        System.out.println("Status of " + currentHref + " is " + getStatusCode(currentHref)); //For Demo
        if (TestCore.baseURL.equals("http://qa.business.com/") || TestCore.baseURL.equals("https://staging.business.com")) {
            ExpectedStatus = 404;
        } else {
            ExpectedStatus = 200;
        }
        Assert.assertEquals(getStatusCode(currentHref), ExpectedStatus);
        //Assert.assertEquals(currentHref, TestCore.baseURL + "info/advertise/#overview");
        Assert.assertEquals(currentHref, TestCore.baseURL + URL);
        System.out.println(elementName + " is present"); //For Demo
    }


}
