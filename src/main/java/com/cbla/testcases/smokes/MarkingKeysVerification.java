package com.cbla.testcases.smokes;

import com.cbla.core.TestCore;
import com.cbla.pages.CBLAAddPage;
import com.cbla.pages.CBLAHomePage;
import com.cbla.utils.DataUtil;
import com.cbla.utils.Utilities;
import com.cbla.utils.WebDriverUtil;
import com.relevantcodes.extentreports.ExtentTest;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Properties;

public class MarkingKeysVerification extends TestCore {

    Utilities utilities;
    private String url = "";
    private CBLAHomePage cblaHomePage;
    private Properties prop;
    ExtentTest test;
    String testName;
    private CBLAAddPage cblaAddPage;

    @BeforeMethod
    public void setUpClass() {
        prop = new Properties();
        utilities = new Utilities(driver);
        cblaHomePage = PageFactory.initElements(driver, CBLAHomePage.class);
        cblaAddPage = PageFactory.initElements(driver, CBLAAddPage.class);
    }

    /**
     * Method to Login, Navigate to Exam Pop up and Click on Create Paper to Create paper Pop Up
     * @param test = Extent test
     * @param driver = WebDriver
     */
    private void loginCreateExamCreatePaper(ExtentTest test, WebDriver driver) {
        try {
            this.url = DataUtil.getTestDataFromPropFile("cbla", "CBLA_IB_URL");
            utilities.deleteAllCookiesAndNavigateToUrl(url);
            WebDriverUtil.waitForPageLoad(driver);

            //CBLA IB Login
            cblaHomePage.login(driver, DataUtil.getCredentialsFromPropFile("credentials", "username"),
                    DataUtil.getCredentialsFromPropFile("credentials", "password"));
            logger.logPass(test, "Verify Login", "User is successfully logged in to CBLA IB: " + url);

            //Navigating to add exam popup
            cblaAddPage.navigateToAddMenu(driver, "exam");

            //Validation of add exam popup
            Assert.assertTrue(cblaAddPage.verifyPopupIsDisplayed(driver, "Create exam"), "Create exam popup is not displayed");
            logger.logPass(test, "Verify Exam Pop Up", "User is successfully able to view Exam Pop Up");

            //Validation of create paper popup
            cblaAddPage.clickCreatePaperButton();
            Assert.assertTrue(cblaAddPage.verifyCreatePaperPopup(), "Create paper popup is not visible");
            logger.logPass(test, "Verify Create Paper Pop Up", "User successfully navigate to Create Paper Pop Up");
        }
        catch (Exception e) {}
    }

    /*Test ID: C1659 0 */
    /*Automation Test Case ID: 34 */
    @Test(description = "Automation ID 34: Verify that the Exam Manager is able to add additional Marking Keys to the Paper.")
    public void verifyMarkingKeysCreation() throws IOException, InterruptedException {
        try {
            testName = "Verify that the Exam Manager is able to add additional Marking Keys to the Paper.";
            test = logger.startTest(report, testName);
            String markingKey1 = DataUtil.getTestDataFromPropFile("cbla", "markingKey1");
            String markingKey2 = DataUtil.getTestDataFromPropFile("cbla", "markingKey2");
            String score = DataUtil.getTestDataFromPropFile("cbla", "score");
            this.url = DataUtil.getTestDataFromPropFile("cbla", "CBLA_IB_URL");
            utilities.deleteAllCookiesAndNavigateToUrl(url);
            WebDriverUtil.waitForPageLoad(driver);
            logger.logPass(test, "Verify CBLA IB URL", "Verified user is successfully navigated to CBLA IB URL: " + url);
            //CBLA IB Login
            cblaHomePage.login(driver, DataUtil.getCredentialsFromPropFile("credentials", "username"),
                    DataUtil.getCredentialsFromPropFile("credentials", "password"));
            logger.logPass(test, "Verify Login", "Verified user is successfully logged in to CBLA IB: " + url);

            //Open and verify Create Exam Popup
            Assert.assertTrue(cblaHomePage.verifyExamPopupIsOpen(driver).equals("Create exam"), "Exam Popup could not open!");
            logger.logPass(test, "Verify Exam Popup", "Verified user can successfully open exam popup!");

            //Open create paper and verify marking keys creation
            String keyData = cblaHomePage.verifyMarkingKeysCreation(driver, markingKey1, markingKey2, score);
            String[] keys = keyData.split("[,]", 0);
            Assert.assertEquals(keys[0], markingKey1);
            Assert.assertEquals(keys[1], markingKey2);
            logger.logPass(test, "Verify Marking Keys Creation", "Verified marking keys are created with names: " + keyData);
            logger.endTest(report, test, testName);
        } catch (Throwable t) {
            reportError(driver, test, t, "Additional marking Keys addition verification failed");
        }
    }

    /*Test ID: C1659 1 */
    /*Automation Test Case ID: 36 */
    @Test(description = "Automation ID 36: Verify that System pops up Validation Message if mandatory fields in Marking Keys section are not filled.")
    public void verifyMarkingKeysBlankError() throws IOException, InterruptedException {
        try {
            testName = "Automation ID 36: Verify that System pops up Validation Message if mandatory fields in Marking Keys section are not filled.";
            test = logger.startTest(report, testName);
            cblaHomePage = PageFactory.initElements(driver, CBLAHomePage.class);
            String markingKey1 = DataUtil.getTestDataFromPropFile("cbla", "markingKey1");
            String markingKey2 = DataUtil.getTestDataFromPropFile("cbla", "markingKey2");
            String score = DataUtil.getTestDataFromPropFile("cbla", "score");

            this.url = DataUtil.getTestDataFromPropFile("cbla", "CBLA_IB_URL");
            utilities.deleteAllCookiesAndNavigateToUrl(url);
            WebDriverUtil.waitForPageLoad(driver);
            logger.logPass(test, "Verify CBLA IB URL", "Verified user is successfully navigated to CBLA IB URL: " + url);

            //CBLA IB Login
            cblaHomePage.login(driver, DataUtil.getCredentialsFromPropFile("credentials", "username"),
                    DataUtil.getCredentialsFromPropFile("credentials", "password"));
            logger.logPass(test, "Verify Login", "Verified user is successfully logged in to CBLA IB: " + url);

            //Open and verify Create Exam Popup
            Assert.assertTrue(cblaHomePage.verifyExamPopupIsOpen(driver).equals("Create exam"), "Exam Popup could not open!");
            logger.logPass(test, "Verify Exam Popup", "Verified user can successfully open exam popup!");

            //Open create paper and verify error pop up
            String keyError = cblaHomePage.verifyMarkingKeysError(driver, markingKey1, markingKey2, score);
            Assert.assertEquals(keyError, "Your data was not saved, please correct any errors and save again.");
            logger.logPass(test, "Verify Marking Keys Error", "Verified marking keys throws error at blank section " + keyError);
            logger.endTest(report, test, testName);
        } catch (Throwable t) {
            reportError(driver, test, t, "Validation Message when mandatory fields in Marking Keys verification failed");
        }
    }
}
