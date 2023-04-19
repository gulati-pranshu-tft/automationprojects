package com.cbla.testcases.smokes;

import com.cbla.core.TestCore;
import com.cbla.pages.CBLAAddPage;
import com.cbla.pages.CBLAHomePage;
import com.cbla.pages.ExamPopUp;
import com.cbla.utils.DataUtil;
import com.cbla.utils.Utilities;
import com.cbla.utils.WebDriverUtil;
import com.relevantcodes.extentreports.ExtentTest;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class WindowsRunnableTestCases extends TestCore {

    String testName;
    ExtentTest test;
    private String url = "";
    private CBLAHomePage cblaHomePage;
    private CBLAAddPage cblaAddPage;
    private ExamPopUp examPopUp;

    private Utilities utilities;

    @BeforeMethod
    public void setUpClass() {
        cblaHomePage = PageFactory.initElements(driver, CBLAHomePage.class);
        cblaAddPage = PageFactory.initElements(driver, CBLAAddPage.class);
        examPopUp = PageFactory.initElements(driver, ExamPopUp.class);
        utilities = new Utilities(driver);
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

    /*Test ID: C1658 6 */
    /*Automation Test Case ID: 4 */
    /* Note: Currently configured to only run on Windows */
    @Test(description = "Automation ID 4: The system shouldn't allow the user to upload a file other than a CSV file and there should be a validation error message.")
    public void verifyNonCsvMarkingKey() {
        try {
            testName = "Automation ID 4: The system shouldn't allow the user to upload a file other than a CSV file and there should be a validation error message.";
            test = logger.startTest(report, testName);
            loginCreateExamCreatePaper(test, driver);

            logger.logPass(test, "User uploads pdf file from", "A test pdf file i.e. non-csv file is uploaded");
            cblaAddPage.importMarkingKeyWrongData(driver, DataUtil.getTestDataFromPropFile("cbla", "pdfFileLocal"));

            String uploadDataErrorText = cblaAddPage.fetchUploadDataAlertText(driver);
            Assert.assertEquals(uploadDataErrorText, "Please upload some data first.");
            logger.logPass(test, "Upload data error Verification", "Marking Keys doesn't allow any file other csv. Got Upload data error: " + uploadDataErrorText + ", verified successfully when tried to click on 'Next' button");
        } catch (Throwable t) {
            reportError(driver, test, t, "Marking Keys non csv file error is not verified successfully");
        }
    }

    /*Test ID: C1688 6 */
    /*Automation Test Case ID: 13 */
    /* Note: Currently configured to only run on Windows */
    @Test(description = "Automation ID 13: Verify that user is able to choose manually enter or import marking keys upon clicking on \"Add\" under \"Action\" column in the Marking Keys table.")
    public void verifyMarkingKeyUpload() {
        try {
            testName = "Automation ID 13: Verify that user is able to choose manually enter or import marking keys upon clicking on \"Add\" under \"Action\" column in the Marking Keys table.";
            test = logger.startTest(report, testName);
            loginCreateExamCreatePaper(test, driver);

            // Create Paper Mandatory Fields
            cblaAddPage.fillCreatePaperMandatoryFields(driver, cblaAddPage.paperName(), "Listening");

            // Changes saved successfully notification verification
            cblaAddPage.addNewMarkingKeyButton(driver);
            cblaAddPage.importMarkingKeyFinishSetUp(driver, DataUtil.getTestDataFromPropFile("cbla", "csvFileValid"));
            logger.logPass(test, "Import csv file", "Successfully Imported Paper_MarkingKeys.csv");
        } catch (Throwable t) {
            reportError(driver, test, t, "Failed Paper save with CSV File");
        }
    }

    /*Test ID: C1836 9 */
    /*Automation Test Case ID: 32 */
    /* Note: Currently configured to only run on Windows */
    @Test(description = "Automation ID 32: Add HybridFile lock to prevent the update of in-used booklet template file")
    public void verifyDuplicateFileError() {
        try {
            testName = "Automation ID 32: Add HybridFile lock to prevent the update of in-used booklet template file";
            test = logger.startTest(report, testName);
            this.url = DataUtil.getTestDataFromPropFile("cbla", "CBLA_IB_URL");
            utilities.deleteAllCookiesAndNavigateToUrl(url);
            WebDriverUtil.waitForPageLoad(driver);

            //CBLA IB Login
            cblaHomePage.login(driver, DataUtil.getCredentialsFromPropFile("credentials", "username"),
                    DataUtil.getCredentialsFromPropFile("credentials", "password"));
            logger.logPass(test, "Verify Login", "User is successfully logged in to CBLA IB: " + url);

            //Navigating to resources page
            cblaAddPage.navigateToAdminMenu(driver);
            logger.logPass(test, "Verify Navigation to resources page", "User is successfully navigated to resources page");

            cblaAddPage.importDuplicatePaper(DataUtil.getTestDataFromPropFile("cbla", "samePdfFileLocal"), driver);
            logger.logPass(test, "Verify Upload of duplicate paper", "duplicate paper uploaded successfully");

            String message = cblaAddPage.getPopupMessage();
            Assert.assertEquals(message, DataUtil.getTestDataFromPropFile("cbla", "sameFileUploadError"));
        } catch (Throwable t) {
            reportError(driver, test, t, "Failed Paper save with CSV File");
        }
    }

    /*Test ID: C1659 2 */
    /*Automation Test Case ID: 39 */
    /* Note: Currently configured to only run on Windows */
    @Test(description = "Automation ID 39: Verify that the Exam Manager is able to Import Marking Keys (CSV file) of a paper.")
    public void verifyMarkingKeyCSVImport() {
        try {
            testName = "Automation ID 39: Verify that the Exam Manager is able to Import Marking Keys (CSV file) of a paper.";
            test = logger.startTest(report, testName);
            loginCreateExamCreatePaper(test, driver);

            // Changes saved successfully notification verification
            cblaAddPage.addNewMarkingKeyButton(driver);
            cblaAddPage.importMarkingKeyFinishSetUp(driver, DataUtil.getTestDataFromPropFile("cbla", "csvFileValid"));
            logger.logPass(test, "Import csv file", "Successfully Imported Paper_MarkingKeys.csv");

            //verify marking key Brief name
            String markingKeyBriefName = cblaAddPage.fetchKeyNameMarkingKey(driver);
            String expectedBriefName = "Item1";
            Assert.assertEquals(expectedBriefName, markingKeyBriefName, "Marking Keys name Invalid");
            logger.logPass(test, "Check the content under 'Keys' column", "Marking Keys Brief Name verified " + markingKeyBriefName + " Successfully");
        } catch (Throwable t) {
            reportError(driver, test, t, "CSV File not imported successfully");
        }
    }

    /*Test ID: C1659 3 */
    /*Automation Test Case ID: 40 */
    /* Note: Currently configured to only run on Windows */
    @Test(description = "Automation ID 40: Verify that the system performs validation on uploaded CSV file for Marking keys.")
    public void verifyMarkingKeyInvalidCSVImport() {
        try {
            testName = "Automation ID 40: Verify that the system performs validation on uploaded CSV file for Marking keys.";
            test = logger.startTest(report, testName);
            loginCreateExamCreatePaper(test, driver);

            // Add marking Keys
            cblaAddPage.addNewMarkingKeyButton(driver);

            //verify marking key Error Message
            String actualInvalidMarkingKeysError = cblaAddPage.fetchInvalidCSVImportMarkingKeysErrorMessage(driver, DataUtil.getTestDataFromPropFile("cbla", "csvFileInvalid"));
            String expectedInvalidMarkingKeysError = "All required columns must be mapped. Found 'Group' unmapped.";
            Assert.assertEquals(expectedInvalidMarkingKeysError, actualInvalidMarkingKeysError, "Error Message couldn't be verified");
            logger.logPass(test, "Check Error Message with invalid csv file data", "Invlaid CSV File Error Message verified Successfully: " + actualInvalidMarkingKeysError);
        } catch (Throwable t) {
            reportError(driver, test, t, "Invalid CSV File Error Message verification failed");
        }
    }

    /*Test ID: C1660 1 */
    /*Automation Test Case ID: 41 */
    /* Note: Currently configured to only run on Windows */
    @Test(description = "Automation ID 41: Verify that system automatically populates Marking Keys as per the CSV file in the Marking keys table in the Paper dialog box.")
    public void verifyMarkingKeyCSVDataIsPopulated() {
        try {
            testName = "Automation ID 41: Verify that system automatically populates Marking Keys as per the CSV file in the Marking keys table in the Paper dialog box.";
            test = logger.startTest(report, testName);
            loginCreateExamCreatePaper(test, driver);

            // Changes saved successfully notification verification
            cblaAddPage.addNewMarkingKeyButton(driver);
            cblaAddPage.importMarkingKey(driver, DataUtil.getTestDataFromPropFile("cbla", "csvFileValid"));
            logger.logPass(test, "Import csv file", "Successfully Imported Paper_MarkingKeys.csv");

            //verify marking key imported data
            Assert.assertTrue(cblaAddPage.verifyImportedData());
            logger.logPass(test, "Csv imported data verification", "Csv imported data verified Successfully");
        } catch (Throwable t) {
            reportError(driver, test, t, "CSV File not imported successfully");
        }
    }

    /*Test ID: C1660 2, C1706 2 */
    /*Automation Test Case ID: 42, 44 */
    /* Note: Currently configured to only run on Windows */
    @Test(description = "Automation ID 42: Verify that Exam Manager is able to save the Paper along with imported Marking keys. \n Automation ID 44: Verify that the Exam Manager is able to select profession and version for the new marking key")
    public void verifyMarkingKeyCSVDataSavePaper() {
        try {
            testName = "Automation ID 42: Verify that Exam Manager is able to save the Paper along with imported Marking keys. \n Automation ID 44: Verify that the Exam Manager is able to select profession and version for the new marking key";
            test = logger.startTest(report, testName);
            loginCreateExamCreatePaper(test, driver);

            // Create Paper Mandatory Fields
            cblaAddPage.fillCreatePaperMandatoryFields(driver, cblaAddPage.paperName(), "Listening");

            // Changes saved successfully notification verification
            cblaAddPage.addNewMarkingKeyButton(driver);
            cblaAddPage.importMarkingKeyFinishSetUp(driver, DataUtil.getTestDataFromPropFile("cbla", "csvFileValid"));
            logger.logPass(test, "Import csv file", "Successfully Imported Paper_MarkingKeys.csv");

            //verify marking key Brief name
            String markingKeyBriefName = cblaAddPage.fetchKeyNameMarkingKey(driver);
            String expectedBriefName = "Item1";
            Assert.assertEquals(expectedBriefName, markingKeyBriefName, "Marking Keys name Invalid");
            logger.logPass(test, "Check the content under 'Keys' column", "Marking Keys Brief Name verified " + markingKeyBriefName + " Successfully");

            /* Automation ID 44 */
            // add marking keys profession, version, score
            cblaAddPage.addMarkingKeysProfVersionMaxScore(driver, "All", DataUtil.getTestDataFromPropFile("cbla", "version"), DataUtil.getTestDataFromPropFile("cbla", "score"));

            // Click save Button
            cblaAddPage.clickCreatePaperSaveButton();

            // Changes saved successfully notification verification
            String expectedNotificationText = DataUtil.getTestDataFromPropFile("cbla", "changesSavedSuccessfullyNotification");
            String actualNotificationText = cblaAddPage.fetchSaveNotification();
            Assert.assertEquals(actualNotificationText, expectedNotificationText);
            logger.logPass(test, "Verify Exam Changes Saved Successfully", "Exam Saved with notification: " + actualNotificationText);
        } catch (Throwable t) {
            reportError(driver, test, t, "Failed Paper save with CSV File");
        }
    }

    /*Test ID: C1706 3 */
    /*Automation Test Case ID: 45 */
    /* Note: Currently configured to only run on Windows */
    @Test(description = "Automation ID 45: Verify that the system populates validation message if there is any existing marking key with same Profession and Version")
    public void verifyMultipleMarkingKeysSameData() {
        try {
            testName = "Automation ID 45: Verify that the system populates validation message if there is any existing marking key with same Profession and Version";
            test = logger.startTest(report, testName);
            loginCreateExamCreatePaper(test, driver);

            // Create Paper Mandatory Fields
            cblaAddPage.fillCreatePaperMandatoryFields(driver, cblaAddPage.paperName(), "Listening");
            logger.logPass(test, "Create Paper Mandatory Fields added", "Add paper name, date and paper type");

            // Add Marking Keys
            cblaAddPage.importMarkingKeyFinishSetUp(driver, DataUtil.getTestDataFromPropFile("cbla", "csvFileValid"));
            logger.logPass(test, "Import csv file", "Successfully Imported Paper_MarkingKeys.csv");
            cblaAddPage.addMarkingKeysProfVersionMaxScore(driver, "All", DataUtil.getTestDataFromPropFile("cbla", "version"), DataUtil.getTestDataFromPropFile("cbla", "score"));
            logger.logPass(test, "Marking keys Profession, Version and Max Score", "User adds new marking key Profession: 'All', Version: 'Zone 1', Max Score: '10'");

            // Add Same Marking Keys
            cblaAddPage.addNewMarkingKeyButton(driver);
            cblaAddPage.addMarkingKeysProfVersionMaxScore(driver, "All", DataUtil.getTestDataFromPropFile("cbla", "version"), DataUtil.getTestDataFromPropFile("cbla", "score"));
            logger.logPass(test, "Marking keys Profession, Version and Max Score", "User adds new marking key Profession: 'All', Version: 'Zone 1', Max Score: '10'");

            // Click save Button
            cblaAddPage.clickCreatePaperSaveButton();
            String toolTipError = cblaAddPage.fetchMandatoryFieldToolTip(driver).get(0);
            String expectedError = "Marking keys cannot have the same set up i.e same profession, version";

            Assert.assertEquals(toolTipError, expectedError);
            logger.logPass(test, "Verify Same Mandatory Keys Fields Tool Tip error", "Verified error: " + toolTipError);
        } catch (Throwable t) {
            reportError(driver, test, t, "Multiple Marking Keys with same fields error is not verified successfully");
        }
    }
}
