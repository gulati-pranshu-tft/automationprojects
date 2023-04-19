package com.cbla.utils;

import com.cbla.core.TestCore;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;

import java.util.ArrayList;
import java.util.List;

public class TestStatistics implements ITestListener {

    List<ITestNGMethod> passedtests = new ArrayList<ITestNGMethod>();
    List<ITestNGMethod> failedtests = new ArrayList<ITestNGMethod>();
    List<ITestNGMethod> skippedtests = new ArrayList<ITestNGMethod>();


    @Override
    //This method will automatically be called if a test runs successfully
    public void onTestSuccess(ITestResult result) {
        //add the passed tests to the passed list
        passedtests.add(result.getMethod());
        TestCore.setPassedCount(getPassedCount());
    }

    @Override
    //This method will automatically be called if a test fails
    public void onTestFailure(ITestResult result) {
        //add the failed tests to the failed list
        failedtests.add(result.getMethod());
        TestCore.setFailedCount(getFailedCount());
    }

    @Override
    //This method will automatically be called if a test is skipped
    public void onTestSkipped(ITestResult result) {
        //add the skipped tests to the skipped list
        skippedtests.add(result.getMethod());
        TestCore.setSkippedCount(getSkippedCount());
    }

    @Override
    public void onTestStart(ITestResult result) {
        //Do nothing
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        // Do nothing
    }

    @Override
    public void onStart(ITestContext context) {
        context.getStartDate();
    }

    @Override
    public void onFinish(ITestContext context) {
        context.getEndDate();
    }

    public int getPassedCount() {
        return passedtests.size();
    }

    public int getFailedCount() {
        return failedtests.size();
    }

    public int getSkippedCount() {
        return skippedtests.size();
    }
}
