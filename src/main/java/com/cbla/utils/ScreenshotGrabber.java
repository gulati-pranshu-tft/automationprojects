package com.cbla.utils;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;

public class ScreenshotGrabber {

    public static String grabScreenshot(WebDriver driver, String screenshotFilePath, String reportImageFilePath) throws IOException {
        long l = System.currentTimeMillis();
        String fileName = "error" + l + ".jpg";
        File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(srcFile, new File(screenshotFilePath + fileName));
        return reportImageFilePath + fileName;
    }

    public static String grabBase64Screenshot(WebDriver driver) {
        TakesScreenshot newScreen = (TakesScreenshot) driver;
        String scnShot = newScreen.getScreenshotAs(OutputType.BASE64);
        return "data:image/jpg;base64, " + scnShot;
    }
}
