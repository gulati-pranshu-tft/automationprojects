package com.cbla.utils;

import com.gargoylesoftware.htmlunit.WebClient;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import java.io.IOException;

public class LinkUtils {

    public static void verifyLink(SoftAssert sa, WebElement link, String expectedUrl) {
        String actualUrl = link.getAttribute("href");
        sa.assertEquals(actualUrl, expectedUrl);
    }

    public static void verifyLink(WebElement link, String expectedUrl) {
        String actualUrl = link.getAttribute("href");
        Assert.assertEquals(actualUrl, expectedUrl);
    }

    public static void verifyLink(boolean checkStatus, SoftAssert sa, WebElement link, String expectedLinkText, String expectedUrl) throws IOException {
        WebDriverUtil.verifyElementIsDisplayed(sa, link, expectedLinkText + " link");
        if (checkStatus) {
            verifyLinkWithStatus(sa, link, expectedLinkText, expectedUrl);
        } else {
            verifyLink(sa, link, expectedLinkText, expectedUrl);
        }
    }

    public static void verifyLinkWithStatus(SoftAssert sa, WebElement link) throws IOException {
        String actualUrl = link.getAttribute("href");
        try {
            sa.assertEquals(getStatusCode(actualUrl), 200);
        } catch (Throwable t) {
            sa.fail(actualUrl + " did not return 200 status code", t);
        }
    }

    public static void verifyLinkWithStatus(SoftAssert sa, WebElement link, String expectedLinkText, String expectedUrl) throws IOException {
        String actualLinkText = link.getText();
        sa.assertEquals(actualLinkText, expectedLinkText);
        String actualUrl = link.getAttribute("href");
        sa.assertEquals(actualUrl, expectedUrl);
        try {
            sa.assertEquals(getStatusCode(actualUrl), 200);
        } catch (Throwable t) {
            sa.fail(actualUrl + " did not return 200 status code", t);
        }
    }

    public static void verifyLink(SoftAssert sa, WebElement link, String expectedLinkText, String expectedUrl) {
        String actualLinkText = link.getText();
        sa.assertEquals(actualLinkText, expectedLinkText);
        String actualUrl = link.getAttribute("href");
        sa.assertEquals(actualUrl, expectedUrl);
    }


    public static void verifyLinkWithStatus(SoftAssert sa, WebElement link, String expectedUrl) throws IOException {
        String actualUrl = link.getAttribute("href");
        sa.assertEquals(actualUrl, expectedUrl);
        try {
            sa.assertEquals(getStatusCode(actualUrl), 200);
        } catch (Throwable t) {
            sa.fail(actualUrl + " did not return 200 status code", t);
        }
    }

    public static int getStatusCode(String URL) throws IOException {
        WebClient webClient = new WebClient();
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        webClient.getOptions().setJavaScriptEnabled(false);
        int code = webClient.getPage(URL).getWebResponse().getStatusCode();
        webClient.close();
        return code;
    }
}
