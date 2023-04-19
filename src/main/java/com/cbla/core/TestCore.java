package com.cbla.core;

import com.cbla.config.properties.TestConfig;
import com.cbla.utils.*;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.Optional;
import org.testng.annotations.*;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

public class TestCore {


    public static String baseURL;
    public static String rootPath = System.getProperty("user.dir");
    public static String origBaseURL;
    protected static String environment;
    protected static TestLogger logger = new TestLogger();
    protected static ExtentReports report;
    protected static String reportPath;
    protected static String screenshotFilePath;
    protected static String reportFolder;
    protected static String reportZipFile = "TestExecutionResults.zip";
    protected static String logsFolder;
    protected static String logsZipFolder = "TestExecutionLogs.zip";
    protected static String[] emailTo;
    protected static String subject;
    protected static String message;
    protected static int passed;
    protected static int skipped;
    protected static int failed;
    protected static String reportDate;
    protected static boolean takeScreenshots;
    protected static boolean runFromJenkins;
    private static int warnings = 0;
    protected WebDriver driver = null;
    protected String testBrowser;
    protected String deviceName;
    protected String testSuiteName;
    protected String username;
    protected String password;
    protected DesiredCapabilities capabilities;

    /**
     * Function to report error
     * @param driver - Webdriver instance
     * @param test - Extent test instance
     * @param t - Throwable class instsnce
     * @param message - Error message
     */
    public static void reportError(WebDriver driver, ExtentTest test, Throwable t, String message) {
        String errorMessage = message + "<br />URL at time of error: " + driver.getCurrentUrl() + ". Please see logs for more error details. <pre>" + t.getMessage() + "</pre>";
        if (takeScreenshots) {
            String screenshot = ScreenshotGrabber.grabBase64Screenshot(driver);
            logger.logFail(test, errorMessage, t, screenshot);
        } else {
            logger.logFail(test, errorMessage, t);
        }
        ErrorCollector.addVerificationFailure(t);
    }

    /**
     * Function to report warning
     * @param driver - Webdriver instance
     * @param test - Extent test instance
     * @param t - Throwable class instsnce
     * @param message - Warning message
     */
    public static void reportWarning(WebDriver driver, ExtentTest test, Throwable t, String message) {
        String errorMessage = message + "<br />URL at time of error: " + driver.getCurrentUrl() + ".";
        if (takeScreenshots) {
            String screenshot = ScreenshotGrabber.grabBase64Screenshot(driver);
            logger.logWarning(test, t, errorMessage, screenshot);
        } else {
            logger.logWarning(test, t, errorMessage);
        }
        warnings = warnings + 1;
    }

    /**
     * Function to set passed test count
     * @param count - passed test count
     */
    public static void setPassedCount(int count) {
        passed = count;
    }

    /**
     * Function to set failed test count
     * @param count - failed test count
     */public static void setFailedCount(int count) {
        failed = count;
    }

    /**
     * Function to set skipped test count
     * @param count - skipped test count
     */
    public static void setSkippedCount(int count) {
        skipped = count;
    }

    /**
     * Function to setup report and environment
     * @param suiteName - name of suite
     * @param domainName - name of domain
     * @param testEnvironment - test environment name
     */
    @BeforeSuite
    @Parameters({"suiteName", "domainName", "testEnvironment", "regressionTest", "takeScreenshotsForErrors", "fromJenkins", "browser", "deviceName", "emailList", "passedCount", "skippedCount", "failedCount"})
    public void init(@Optional("suite name not provided") String suiteName, @Optional("domain name not provided") String domainName, @Optional("stg") String testEnvironment, @Optional("true") boolean regressionTest,
                     @Optional("true") boolean takeScreenshotsForErrors, @Optional("false") boolean fromJenkins, @Optional("headless") String browser,
                     @Optional("Desktop") String deviceName, @Optional("email not provided") String emailList, @Optional("0") String passedCount,
                     @Optional("0") String skippedCount, @Optional("0") String failedCount) throws IOException, InterruptedException {
        System.out.println("~~~ init ~~~");
        takeScreenshots = takeScreenshotsForErrors;
        runFromJenkins = fromJenkins;
        emailTo = emailList.split(",");
        Properties prop = new Properties();
        FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + File.separator + "script-resources" + File.separator + "config.properties");
        prop.load(fis);
        logger.log("Config property file loaded");
        reportDate = DateUtil.formatDate(DateUtil.getTodaysDate(), "yyyy-MM-dd-HH-mm");
        if (deviceName.equalsIgnoreCase("desktop")) {
            testSuiteName = suiteName + " - " + testEnvironment.toUpperCase() + " - " + browser;
        } else {
            testSuiteName = suiteName + " - " + testEnvironment.toUpperCase() + " - " + deviceName;
        }

        if (runFromJenkins) {
            reportFolder = "." + File.separator + "reports" + File.separator + "TestResults";
        } else {
            reportFolder = "." + File.separator + "reports" + File.separator + "TestResults" + reportDate;
        }
        reportPath = reportFolder + File.separator + "report.html";
        screenshotFilePath = reportFolder + File.separator + "ErrorScreenshots" + File.separator;
        logsFolder = rootPath + File.separator + "logs";
        System.out.println("Root path: " + rootPath);
        System.out.println("Report Folder: " + reportFolder);

        try {
            environment = testEnvironment.toUpperCase();
            baseURL = prop.getProperty(domainName + "_" + environment + "_" + "URL");
            // For QA and UAT Environment, we change the BASE URL to include user/pass.
            // However, we need the original URL for Verifying the canonical
            origBaseURL = baseURL;
            if (environment.equals("QA") || environment.equals("UAT")) {
                username = prop.getProperty(domainName + "_" + environment + "_Username");
                password = prop.getProperty(domainName + "_" + environment + "_Password");
                baseURL = baseURL.replace("https://", "https://" + username + ":" + password + "@");
                baseURL = baseURL.replace("http://", "http://" + username + ":" + password + "@");
            }
            File f = new File(reportPath);
            if (f.exists() && f.isFile()) {
                report = new ExtentReports(reportPath, true);
            } else {
                f.getParentFile().mkdirs();
                f.createNewFile();
                report = new ExtentReports(reportPath, true);

            }
        } catch (Throwable t) {
            logger.logError("Error during init", t);
            logger.log(t.getStackTrace().toString());
        }
        fis.close();

        System.setProperty(passedCount, String.valueOf(passed));
        System.setProperty(skippedCount, String.valueOf(skipped));
        System.setProperty(failedCount, String.valueOf(failed));
    }

    /**
     * Function to initiate browser session
     * @param browser - browser name
     * @param deviceName - device name
     * @throws IOException
     */
    @BeforeTest
    @Parameters({"browser", "deviceName"})
    public void startBrowser(@Optional("chrome") String browser, @Optional("Desktop") String deviceName) throws IOException {
        System.out.println("~~~ startBrowser ~~~ Browser: " + browser + " deviceName: " + deviceName);
        testBrowser = System.getProperty("browserName");
        this.deviceName = deviceName;
        Properties prop = new Properties();
        LoggingPreferences logPrefs = new LoggingPreferences();

        //Initialize the WebDriver
        if (deviceName.equalsIgnoreCase("Desktop")) {
            switch (testBrowser) {
                case "headless":

                    System.out.println("Trying to launch chrome: chromePath:");
                    logPrefs.enable(LogType.PERFORMANCE, Level.ALL);
                    ChromeOptions headlessChromeOptions = new ChromeOptions();
                    headlessChromeOptions.addArguments("--headless");
                    headlessChromeOptions.addArguments("--disable-notifications");
                    headlessChromeOptions.addArguments("--no-sandbox");
                    headlessChromeOptions.addArguments("--incognito");
                    headlessChromeOptions.addArguments("--start-maximized");
                    headlessChromeOptions.addArguments("window-size=1200,1100");
                    headlessChromeOptions.addArguments("--disable-blink-features=AutomationControlled");
                    headlessChromeOptions.setCapability("goog:loggingPrefs", logPrefs);
                    headlessChromeOptions.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
                    WebDriverManager.chromedriver().setup();
                    driver = new ChromeDriver(headlessChromeOptions);
                    System.out.println("Chrome Driver Info:" + driver.getTitle());
                    logger.log("Chrome headless driver initialized");
                    ((JavascriptExecutor) driver).executeScript("Object.defineProperty(navigator, 'webdriver', {get: () => undefined})");

                    String userAgent = (String) ((JavascriptExecutor) driver).executeScript("return navigator.userAgent;");
                    System.out.println("userAgent headless: " + userAgent);
                    break;
                case "firefox":
                    FirefoxOptions fo = new FirefoxOptions();
                    fo.setHeadless(false);
                    WebDriverManager.firefoxdriver().setup();
                    driver = new FirefoxDriver(fo);
                    logger.log("Firefox driver initialized");
                    break;
                case "chrome":
                    logPrefs.enable(LogType.PERFORMANCE, Level.ALL);
                    ChromeOptions chromeOptions = new ChromeOptions();
                    Map<String, Object> prefs = new HashMap<String, Object>();
                    prefs.put("credentials_enable_service", false);
                    prefs.put("password_manager_enabled", false);
                    chromeOptions.setExperimentalOption("prefs", prefs);
                    chromeOptions.addArguments("window-size=1200,1100");
                    chromeOptions.addArguments("--disable-notifications");
                    chromeOptions.setCapability("goog:loggingPrefs", logPrefs);
                    WebDriverManager.chromedriver().setup();
                    driver = new ChromeDriver(chromeOptions);
                    logger.log("Chrome driver initialized");
                    break;

                default:
                    logger.log("Invalid browser option provided");
                    break;
            }
            driver.manage().window().maximize();
        } else if (deviceName.equalsIgnoreCase("grid")) {
            switch (testBrowser) {
                case "chrome":
                    System.out.println("Trying to launch chrome: chromePath:" + System.getProperty("gridUrl"));
                    ChromeOptions headlessChromeOptions = new ChromeOptions();
                    headlessChromeOptions.addArguments("--headless");
                    headlessChromeOptions.addArguments("--disable-notifications");
                    headlessChromeOptions.addArguments("--incognito");
                    headlessChromeOptions.addArguments("--start-maximized");
                    headlessChromeOptions.addArguments("window-size=1200,1100");
                    driver = new RemoteWebDriver(new URL(System.getProperty("gridUrl")), headlessChromeOptions);
                    logger.log("Chrome headless driver initialized");
                    break;
                case "firefox":
                    FirefoxOptions headlessFirefoxOptions = new FirefoxOptions();
                    headlessFirefoxOptions.addArguments("--headless");
                    driver = new RemoteWebDriver(new URL(System.getProperty("gridUrl")), headlessFirefoxOptions);
                    logger.log("Firefox driver initialized");
                    break;
                default:
                    logger.log("Invalid browser option provided");
                    break;
            }
            driver.manage().window().maximize();
        }
        driver.manage().timeouts().pageLoadTimeout(180L, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(TestConfig.NORMAL_WAIT, TimeUnit.SECONDS);
        driver.manage().deleteAllCookies();
    }

    @AfterTest
    /**
     * Function to quit driver instance
     */
    public void quitDriver() {
        System.out.println("~~~ quitDriver ~~~");
        driver.quit();
        logger.log("Driver closed.");
    }

    /**
     * Fucntion to configure email after every suite run
     */
    @AfterSuite(alwaysRun = true)
    public void setEmailContent() {
        System.out.println("~~~ setEmailContent ~~~");
        if (failed > 0 || skipped > 0) {
            subject = TestConfig.failedSubject + TestConfig.subject + testSuiteName;
        } else {
            subject = TestConfig.successSubject + TestConfig.subject + testSuiteName;
        }
        if (warnings > 0) {
            message = TestConfig.messageBodyPt1 + testSuiteName + "<br/>"
                    + "<br/>Passed: " + passed
                    + "<br/>Failed: " + failed
                    + "<br/>Skipped: " + skipped
                    + "<br/><br/>Warnings triggered during test run. "
                    + TestConfig.messageBodyPt2;
        } else {
            message = TestConfig.messageBodyPt1 + testSuiteName + "<br/>"
                    + "<br/>Passed: " + passed
                    + "<br/>Failed: " + failed
                    + "<br/>Skipped: " + skipped
                    + "<br/><br/>" + TestConfig.messageBodyPt2;
        }
    }

    /**
     * Fucntion to configure extent report after every suite run
     */
    @AfterSuite(alwaysRun = true, dependsOnMethods = "setEmailContent")
    public void sendResultsAndTeardown() throws AddressException, MessagingException, IOException {
        System.out.println("~~~ sendResultsAndTeardown ~~~");
        report.flush();
        System.out.println(reportFolder);
        System.out.println(reportZipFile);
        System.out.println(logsFolder);
        System.out.println(logsZipFolder);
        System.out.println("Passed: " + passed);
        System.out.println("Failed: " + failed);
        System.out.println("Skipped: " + skipped);
        System.out.println("Warnings: " + warnings);
        //passedCount = this.passed;
    }

    /**
     * Function to report skipped tests
     * @param parentTest - extent test instance
     * @param testNames - name of skipped tests
     */
    public void reportSkippedTests(ExtentTest parentTest, String[] testNames) {
        for (int i = 0; i < testNames.length; i++) {
            ExtentTest test = logger.startTest(report, testNames[i]);
            logger.logSkip(test, "Test skipped due to previous failure");
            parentTest.appendChild(test);
            logger.endTest(testNames[i]);
        }
    }
}
