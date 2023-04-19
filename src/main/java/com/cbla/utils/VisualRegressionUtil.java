package com.cbla.utils;

import com.cbla.core.TestCore;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.asserts.SoftAssert;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.comparison.ImageDiff;
import ru.yandex.qatools.ashot.comparison.ImageDiffer;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.Iterator;

public class VisualRegressionUtil extends TestCore {

    private String currentDir = System.getProperty("user.dir");
    private String testName;
    private String testScreenshotDirectory;
    private String parentScreenshotsLocation = currentDir + "/Screenshots/";
    private String baseScreenshotPath;
    private String actualScreenshotPath;
    private String differenceScreenshotPath;
    private File baseImageFile;
    private File actualImageFile;
    private SoftAssert softAssert = new SoftAssert();

    public void compressImages(BufferedImage image, File imageFile) throws IOException {
        OutputStream os = new FileOutputStream(imageFile);
        Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("png");
        ImageWriter writer = (ImageWriter) writers.next();
        ImageOutputStream ios = ImageIO.createImageOutputStream(os);
        writer.setOutput(ios);
        ImageWriteParam param = writer.getDefaultWriteParam();
        param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        param.setCompressionQuality(0.05f);
        writer.write(null, new IIOImage(image, null, null), param);
        os.close();
        ios.close();
        writer.dispose();
    }

    //This function creates screenshots directory and declare file paths
    public void setUpFilesAndFolders(ExtentTest test, String testName, String environment) throws IOException {
        this.testName = testName;
        this.createFolder(this.parentScreenshotsLocation);
        //Create a specific directory for a test
        testScreenshotDirectory = parentScreenshotsLocation + testName + "/";
        this.createFolder(testScreenshotDirectory);
        this.cleanScreenshotDirectory(environment);
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy_HH-mm-ss");
        Date date = new Date();
        this.declareScreenShotPaths(test, testName + "_" + environment.toUpperCase() + "_Base.png", testName + "_" + environment.toUpperCase() + "_Actual_" + formatter.format(date) + ".png", testName + "_" + environment.toUpperCase() + "_Diff_" + formatter.format(date) + ".png");
    }

    public void createFolder(String path) {
        File testDirectory = new File(path);
        if (!testDirectory.exists()) {
            if (testDirectory.mkdir()) {
                System.out.println("Directory: " + path + " is created!");
            } else {
                System.out.println("Failed to create directory: " + path);
            }
        } else {
            System.out.println("Directory already exists: " + path);
        }
    }

    public void writeScreenshotToFolder(Screenshot screenshot) throws IOException {
        compressImages(screenshot.getImage(), actualImageFile);
    }

    public void declareScreenShotPaths(ExtentTest test, String base, String actual, String diff) {
        //Base, Actual, Difference Photo Paths
        baseScreenshotPath = testScreenshotDirectory + base;
        logger.logPass(test, "Base Screenshot Path", "Base screenshot path : " + baseScreenshotPath);
        actualScreenshotPath = testScreenshotDirectory + actual;
        logger.logPass(test, "Actual Screenshot Path", "Actual screenshot path : " + actualScreenshotPath);
        differenceScreenshotPath = testScreenshotDirectory + diff;
        logger.logPass(test, "Difference Screenshot Path", "Difference screenshot path : " + differenceScreenshotPath);
        //Base, Actual Photo Files
        baseImageFile = new File(baseScreenshotPath);
        actualImageFile = new File(actualScreenshotPath);
    }

    public Screenshot takeScreenshot(WebElement element, WebDriver driver) {
        Screenshot screenshot;
        if (element == null) {
            screenshot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(1000)).takeScreenshot(driver);
        } else {
            screenshot = new AShot().takeScreenshot(driver, element);
        }
        return screenshot;
    }

    public double compareWithAShot(ExtentTest test, String expected, String actual, String difference) throws IOException {
        double diffPercentage = 0.0;
        BufferedImage expectedImage = ImageIO.read(new File(expected));
        BufferedImage actualImage = ImageIO.read(new File(actual));
        ImageDiffer imageDiffer = new ImageDiffer();
        ImageDiff imageDiff = imageDiffer.makeDiff(expectedImage, actualImage);
        if (imageDiff.hasDiff()) {
            compressImages(imageDiff.getMarkedImage(), new File(difference));
//            ImageIO.write(imageDiff.getMarkedImage(), "PNG", new File(difference));
            diffPercentage = this.calculateDiffPercentage(expectedImage, actualImage);
            test.log(LogStatus.INFO, "Diff Screenshot below: " + test.addBase64ScreenShot("data:image/jpg;base64, " + this.convertImageToBase64String(imageDiff.getMarkedImage(), "PNG")));
        }
        return diffPercentage;
    }

    public double performComparison(ExtentTest test, Screenshot elementScreenShot) throws Exception {
        double diffPercentage = 0.0;
        if (this.baseImageFile.exists()) {
            diffPercentage = this.compareWithAShot(test, this.baseScreenshotPath, this.actualScreenshotPath,
                    this.differenceScreenshotPath);
        } else {
            logger.logPass(test, "Base Screenshot Presence", "Base screenshot does not exist.");
            compressImages(elementScreenShot.getImage(), this.baseImageFile);
//            ImageIO.write(elementScreenShot.getImage(), "PNG", this.baseImageFile);
        }
        return diffPercentage;
    }

    public double compareSnapshots(WebDriver driver, ExtentTest test, String elementPath, String selectorType, String environment, String testName) throws Exception {
        this.setUpFilesAndFolders(test, testName, environment);
        WebElement element = null;
        if (selectorType != null) {
            if (selectorType.equalsIgnoreCase("css")) {
                element = driver.findElements(By.cssSelector(elementPath)).get(0);
            } else if (selectorType.equalsIgnoreCase("xpath")) {
                element = driver.findElements(By.xpath(elementPath)).get(0);
            }
        }
        Screenshot screenshot = this.takeScreenshot(element, driver);
        this.writeScreenshotToFolder(screenshot);
        return this.performComparison(test, screenshot);
    }

    public void cleanScreenshotDirectory(String environment) {
        File directory = new File(testScreenshotDirectory);
        File[] listOfFiles = directory.listFiles();
        for (int i = 0; i < listOfFiles.length; i++) {
            if (!listOfFiles[i].getName().contains(environment + "_Base")) {
                listOfFiles[i].delete();
            }
        }
    }

    public double calculateDiffPercentage(BufferedImage expectedImage, BufferedImage actualImage) {
        int width = expectedImage.getWidth();
        int height = expectedImage.getHeight();
        long differencePixel = 0;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgbA = expectedImage.getRGB(x, y);
                int rgbB = actualImage.getRGB(x, y);
                int redA = (rgbA >> 16) & 0xff;
                int greenA = (rgbA >> 8) & 0xff;
                int blueA = (rgbA) & 0xff;
                int redB = (rgbB >> 16) & 0xff;
                int greenB = (rgbB >> 8) & 0xff;
                int blueB = (rgbB) & 0xff;
                differencePixel += Math.abs(redA - redB);
                differencePixel += Math.abs(greenA - greenB);
                differencePixel += Math.abs(blueA - blueB);
            }
        }
        double total_pixels = width * height * 3;
        double avg_different_pixels = differencePixel / total_pixels;
        double percentage = (avg_different_pixels / 255) * 100;
        return percentage;
    }

    public String convertImageToBase64String(BufferedImage image, String type) {
        String base64String;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, type, bos);
            byte[] bytes = bos.toByteArray();
            base64String = Base64.getEncoder().encodeToString(bytes);
            base64String = base64String.replace(System.lineSeparator(), "");
        } catch (IOException e) {
            throw new RuntimeException();
        }
        return base64String;
    }

    public void diffLogger(ExtentTest test, double diffPercentage) {
        if (diffPercentage == 0.0) {
            logger.logPass(test, "Compare Snapshots", "No difference found between actual and expected image. Diff percentage : " + diffPercentage);
        } else if (diffPercentage > 0.0 && diffPercentage < 0.1) {
            logger.logPass(test, "Warning!", "Actual image does not match with expected image with diff percentage as : " + diffPercentage);
        } else
            logger.logFail(test, "Actual image does not match with expected image with diff percentage as : " + diffPercentage);
    }

    //Full page visual regression for landing pages
    public void performVisualRegression(ExtentTest test, double diffPercentage, VisualRegressionUtil visualRegressionUtil,
                                        String elementPath, String selectorType, String testName, String url, WebDriver driver) {
        try {
            logger.logPass(test, "Base Screenshot URL", "Base screenshot URL : " + url);
            logger.logPass(test, "Actual Screenshot URL", "Actual screenshot URL : " + driver.getCurrentUrl());
            diffPercentage = visualRegressionUtil.compareSnapshots(driver, test, elementPath, selectorType, environment, testName);
            softAssert.assertFalse(diffPercentage > 0.0);
            this.diffLogger(test, diffPercentage);
        } catch (Throwable t) {
            reportError(driver, test, t, "Page screenshot. Actual image does not match with expected image with diff percentage as : " + diffPercentage);
        }
    }
}
