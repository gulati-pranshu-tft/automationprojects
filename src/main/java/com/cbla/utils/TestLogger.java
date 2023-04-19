package com.cbla.utils;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TestLogger {

    private Logger log = LogManager.getLogger("devpinoyLogger");


    public void log(String message) {
        log.debug(message);
        System.out.println(message);
    }

    public void log(ExtentTest test, String stepName, String details) {
        log.debug(details);
        System.out.println(details);
        test.log(LogStatus.INFO, stepName, details);
    }

    public void logError(String message, Throwable t) {
        log.error(message, t);
        System.out.println(message);
        t.printStackTrace();
    }

    public void logFail(ExtentTest test, String details, Throwable t) {
        log.error(details);
        System.out.println(details);
        t.printStackTrace();
        test.log(LogStatus.FAIL, details, t);
    }

    public void logFail(ExtentTest test, String details, Throwable t, String screenCapturePath) {
        log.error(details);
        System.out.println(details);
        t.printStackTrace();
        test.log(LogStatus.FAIL, details + " Screenshot below: " + test.addBase64ScreenShot(screenCapturePath), t);
    }

    public void logFail(ExtentTest test, String details, String screenCapturePath) {
        log.error(details);
        System.out.println(details);
        test.log(LogStatus.FAIL, details + " Screenshot below: " + test.addBase64ScreenShot(screenCapturePath));
    }

    public void logFail(ExtentTest test, String details) {
        log.error(details);
        System.out.println(details);
        test.log(LogStatus.FAIL, details);
    }


    public void logPass(ExtentTest test, String stepName, String details) {
        log.debug(details);
        System.out.println(details);
        test.log(LogStatus.PASS, stepName, details);
    }

    public ExtentTest startTest(ExtentReports report, String testName) {
        log.debug("******** START TEST: " + testName + " ********");
        System.out.println("******** START TEST: " + testName + " ********");
        ExtentTest test = report.startTest(testName);
        return test;
    }

    public void endTest(String testName) {
        log.debug("******** END TEST: " + testName + " ********");
        System.out.println("******** END TEST: " + testName + " ********");
    }

    public void endTest(ExtentReports report, ExtentTest test, String testName) {
        log.debug("******** END TEST: " + testName + " ********");
        System.out.println("******** END TEST: " + testName + " ********");
        report.endTest(test);
    }

    public void logSkip(ExtentTest test, String details) {
        log.debug(details);
        test.log(LogStatus.SKIP, details);
    }

    public void logWarning(ExtentTest test, String details) {
        log.debug(details);
        test.log(LogStatus.WARNING, details);
    }

    public void logWarning(ExtentTest test, Throwable t, String details) {
        log.error(details);
        System.out.println(details);
        t.printStackTrace();
        test.log(LogStatus.WARNING, details, t);
    }

    public void logWarning(ExtentTest test, Throwable t, String details, String screenCapturePath) {
        log.error(details);
        System.out.println(details);
        t.printStackTrace();
        test.log(LogStatus.WARNING, details + " Screenshot below: " + test.addBase64ScreenShot(screenCapturePath), t);
    }
}
