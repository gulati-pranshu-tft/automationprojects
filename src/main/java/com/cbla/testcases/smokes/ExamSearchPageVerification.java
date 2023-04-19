package com.cbla.testcases.smokes;

import com.cbla.core.TestCore;
import com.cbla.pages.CBLAAddPage;
import com.cbla.pages.CBLAHomePage;
import com.cbla.pages.ExamPopUp;
import com.cbla.pages.ExamSearchPage;
import com.cbla.utils.DataUtil;
import com.cbla.utils.Utilities;
import com.cbla.utils.WebDriverUtil;
import com.relevantcodes.extentreports.ExtentTest;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ExamSearchPageVerification extends TestCore {

    String testName;
    ExtentTest test;
    private String url = "";
    private CBLAHomePage cblaHomePage;
    private CBLAAddPage cblaAddPage;
    private ExamPopUp examPopUp;
    private ExamSearchPage examSearchPage;

    private Utilities utilities;

    @BeforeMethod
    public void setUpClass() {
        cblaHomePage = PageFactory.initElements(driver, CBLAHomePage.class);
        cblaAddPage = PageFactory.initElements(driver, CBLAAddPage.class);
        examPopUp = PageFactory.initElements(driver, ExamPopUp.class);
        examSearchPage = PageFactory.initElements(driver, ExamSearchPage.class);
        utilities = new Utilities(driver);
    }

    public String createExam(ExtentTest test, WebDriver driver) {
        try {
            //Navigating to add exam popup
            cblaAddPage.navigateToAddMenu(driver, "exam");

            //Validation of add exam popup
            Assert.assertTrue(cblaAddPage.verifyPopupIsDisplayed(driver, "Create exam"), "Create exam popup is not displayed");

            //Validation of create paper popup
            cblaAddPage.clickCreatePaperButton();
            Assert.assertTrue(cblaAddPage.verifyCreatePaperPopup(), "Create paper popup is not visible");
            logger.logPass(test, "Navigate to Create Paper Pop Up", "User successfully navigates to Create Paper Pop Up");

            // Create Paper Mandatory Fields
            String paperName = cblaAddPage.paperName();
            cblaAddPage.fillCreatePaperMandatoryFields(driver, paperName, "Listening");
            logger.logPass(test, "Add paper Mandatory Fields", "Added Paper name, date and paper type");

            // Upload pdf and add mandatory fields
            cblaAddPage.addPdf(driver);
            cblaAddPage.uploadDoc(driver, "pdf");
            cblaAddPage.setPdfMandatoryParameters(driver, DataUtil.getTestDataFromPropFile("cbla", "professionAll"), DataUtil.getTestDataFromPropFile("cbla", "version"), DataUtil.getTestDataFromPropFile("cbla", "order"), DataUtil.getTestDataFromPropFile("cbla", "pdfType"));
            logger.logPass(test, "Add PDF and mandatory Fields", "User adds PDF and all mandatory fields- Version: Zone 1, Order: 1, PDF Type: Listening Answer Booklet");

            // Add Auto Marking Keys
            cblaAddPage.addAutoMarkingKeys(driver, "1", "A");
            logger.logPass(test, "Add Auto Marking Keys", "Auto Marking Keys added Question: 1, Option: A");

            //Add Marking Keys Data
            cblaAddPage.addNewMarkingKeyButton(driver);
            String[] data = DataUtil.getMarkingKeysDataFromDocFile("script-resources/Paper_MarkingKeys.docx").split(",");
            cblaAddPage.addMarkingKeysWithoutCsv(driver, data);
            logger.logPass(test, "Add Marking keys", "User adds new marking key items: Key Name, Score, Group, Group Score");
            cblaAddPage.addMarkingKeysProfVersionMaxScore(driver, DataUtil.getTestDataFromPropFile("cbla", "professionAll"), DataUtil.getTestDataFromPropFile("cbla", "version"), DataUtil.getTestDataFromPropFile("cbla", "score"));
            logger.logPass(test, "Marking keys Profession, Version and Max Score", "User adds new marking key Profession: 'All', Version: 'Zone 1', Max Score: '10'");

            // Click save Button
            cblaAddPage.clickCreatePaperSaveButton();

            // Verify attached paper name
            String expectedPaperName = "[" + paperName + "]";
            String actualPaperName = examPopUp.fetchAttachedPaperName(driver).toString();
            Assert.assertEquals(actualPaperName, expectedPaperName);
            logger.logPass(test, "Verify Paper Name in Exams Table", "Verified paper name: " + actualPaperName.toString());

            // Enter Exam Name
            String examName = examPopUp.examName();
            examPopUp.fillCreateExamMandatoryFields(examName);
            logger.logPass(test, "Enter Exam Name and Date", "Exam name: " + examName);

            // click on save exam button
            examPopUp.clickExamSaveButton(driver);

            // Changes saved successfully notification verification
            String expectedNotificationText = DataUtil.getTestDataFromPropFile("cbla", "changesSavedSuccessfullyNotification");
            String actualNotificationText = examPopUp.fetchSaveNotification();
            Assert.assertEquals(actualNotificationText, expectedNotificationText, "Success Notification not visible");
            logger.logPass(test, "Verify Exam Changes Saved Successfully", "Exam Saved with notification: " + actualNotificationText);
            return examName;
        } catch (Throwable t) {
            reportError(driver, test, t, "Save Exam is not verified successfully");
            return "";
        }
    }

    /*Test ID: C1690 0 */
    /*Automation Test Case ID: 23 */
    @Test(description = "Automation Id 23: Verify that System displays \"Enter new Exam name\" pop up upon clicking on \"Save As\".")
    public void verifyCreateExamPage() {
        try {
            testName = "Automation Id 23: Verify that System displays \"Enter new Exam name\" pop up upon clicking on \"Save As\".";
            test = logger.startTest(report, testName);
            this.url = DataUtil.getTestDataFromPropFile("cbla", "CBLA_IB_URL");
            utilities.deleteAllCookiesAndNavigateToUrl(url);
            WebDriverUtil.waitForPageLoad(driver);

            // exam id from prop file
            String examId = DataUtil.getTestDataFromPropFile("cbla", "examId");

            //CBLA IB Login
            cblaHomePage.login(driver, DataUtil.getCredentialsFromPropFile("credentials", "username"),
                    DataUtil.getCredentialsFromPropFile("credentials", "password"));
            logger.logPass(test, "Verify Login", "User is successfully logged in to CBLA IB: " + url);

            //Navigating to search exam page
            examSearchPage.navigateToSearchMenu(driver, "exams");
            Assert.assertEquals(examSearchPage.fetchSearchPageTitle(driver), "Exam Search");
            logger.logPass(test, "Verify User on Exam Search Page", "User successfully navigates to Exam Search Page");

            // Search for exam
            examSearchPage.enterExamId(examId);
            examSearchPage.clickSearchButton(driver);
            examSearchPage.waitForExamTest(driver);
            Assert.assertEquals(examSearchPage.fetchExamId(driver), examId);
            logger.logPass(test, "User Searches Exam ID", "Exam Id: " + examId + " found");

            // Click View Button and verify pop up
            Assert.assertTrue(examSearchPage.clickAndVerifyViewExam(driver));
            logger.logPass(test, "Verify User on View Exam Pop UP", "User on View exam Pop Up");

            // Click view from the attached papers table
            examSearchPage.clickViewExamPopUpEditButton(driver);

            // Click on Save As...
            examSearchPage.chooseSaveType(driver, "Save As...");
            logger.logPass(test, "User clicks Save As...", "Clicked 'Save As...' option");

            // verify pop up
            String expectedExamNamePopUp = "Enter new exam name";
            String actualExamNamePopUp = examSearchPage.fetchNewExamNamePopUp(driver);
            Assert.assertEquals(actualExamNamePopUp, expectedExamNamePopUp);
            logger.logPass(test, "Verify New Exam Name Pop UP", "Pop with title: " + actualExamNamePopUp);
        } catch (Throwable t) {
            reportError(driver, test, t, "Exam Search Enter new Exam Name Pop up verification Failed");
        }
    }

    /*Test ID: C1690 1 */
    /*Automation Test Case ID: 24 */
    @Test(description = "Automation Id 24: Verify that user is able to save a copy of exam along with its papers, PDFs and marking keys.")
    public void verifyExamCopy() {
        try {
            testName = "Automation Id 24: Verify that user is able to save a copy of exam along with its papers, PDFs and marking keys.";
            test = logger.startTest(report, testName);
            this.url = DataUtil.getTestDataFromPropFile("cbla", "CBLA_IB_URL");
            utilities.deleteAllCookiesAndNavigateToUrl(url);
            WebDriverUtil.waitForPageLoad(driver);

            // exam id from prop file
            String examId = DataUtil.getTestDataFromPropFile("cbla", "examId");

            //CBLA IB Login
            cblaHomePage.login(driver, DataUtil.getCredentialsFromPropFile("credentials", "username"),
                    DataUtil.getCredentialsFromPropFile("credentials", "password"));
            logger.logPass(test, "Verify Login", "User is successfully logged in to CBLA IB: " + url);

            //Navigating to search exam page
            examSearchPage.navigateToSearchMenu(driver, "exams");
            Assert.assertEquals(examSearchPage.fetchSearchPageTitle(driver), "Exam Search");
            logger.logPass(test, "Verify User on Exam Search Page", "User successfully navigates to Exam Search Page");

            // Search for exam
            examSearchPage.clearAllSearchInputs(driver);
            examSearchPage.enterExamId(examId);
            examSearchPage.clickSearchButton(driver);
            examSearchPage.waitForExamTest(driver);
            Assert.assertEquals(examSearchPage.fetchExamId(driver), examId);
            logger.logPass(test, "User Searches Exam ID", "Exam Id: " + examId + " found");

            // Click View Button and verify pop up
            Assert.assertTrue(examSearchPage.clickAndVerifyViewExam(driver));
            logger.logPass(test, "Verify User on View Exam Pop UP", "User on View exam Pop Up");

            // Click view from the attached papers table
            examSearchPage.clickViewExamPopUpEditButton(driver);

            // Click on Save As...
            examSearchPage.chooseSaveType(driver, "Save As...");
            logger.logPass(test, "User clicks Save As...", "Clicked 'Save As...' option");

            // verify pop up
            String expectedExamNamePopUp = "Enter new exam name";
            String actualExamNamePopUp = examSearchPage.fetchNewExamNamePopUp(driver);
            Assert.assertEquals(actualExamNamePopUp, expectedExamNamePopUp);
            logger.logPass(test, "Verify New Exam Name Pop UP", "Pop with title: " + actualExamNamePopUp);

            // Enter New Exam Name
            String newExamName = examPopUp.examName();
            examSearchPage.saveNewExamName(driver, newExamName);
            logger.logPass(test, "Entered New Exam Name", "New Exam name: " + newExamName);

            // Changes saved successfully notification verification
            String expectedNotificationText = DataUtil.getTestDataFromPropFile("cbla", "changesSavedSuccessfullyNotification");
            String actualNotificationText = examPopUp.fetchSaveNotification();
            Assert.assertEquals(actualNotificationText, expectedNotificationText);
            logger.logPass(test, "Verify Exam Changes Saved Successfully", "Exam Saved with notification: " + actualNotificationText);

            // Refresh page to go back to Search Page
            examSearchPage.refreshPage(driver);

            // Clear Entries and search for new Exam
            examSearchPage.clearAllSearchInputs(driver);
            examSearchPage.enterExamName(newExamName);
            examSearchPage.clickSearchButton(driver);
            examSearchPage.waitForFoundToast(driver);
            Assert.assertEquals(examSearchPage.fetchExamName(driver), newExamName);
            logger.logPass(test, "User Searches New Exam Name", "Exam Name: " + newExamName + " found");

            // Click View Button and verify pop up
            Assert.assertTrue(examSearchPage.clickAndVerifyViewExam(driver));
            logger.logPass(test, "Verify User on View Exam Pop UP", "User on View exam Pop Up");

            // Click View Button and verify pop up Preview paper
            Assert.assertTrue(examSearchPage.clickAndVerifyViewPaper(driver));
            logger.logPass(test, "Verify User on Preview paper Pop UP", "User on Preview paper Pop Up");

            // Verify PDF, Mandatory keys and Paper Type
            String expectedData = DataUtil.getTestDataFromPropFile("cbla", "exam275Data");
            String actualData = examSearchPage.fetchPaperTypePreviewPaperPDfMarkingKeys(driver).toString();
            Assert.assertEquals(actualData, expectedData);
            logger.logPass(test, "Verify User on Preview paper Pop UP details", "Verify PDF, Mandatory Keys, paper type with data: " + actualData);
        } catch (Throwable t) {
            reportError(driver, test, t, "Copy Exam- Name, PDF, Mandatory Keys verification Failed");
        }
    }

    /*Test ID: C1658 9 */
    /*Automation Test Case ID: 33 */
    @Test(description = "Automation Id 33: Verify that the Exam Manager is able to manually Add Marking Keys of a paper.")
    public void verifyAddMarkingKeys() {
        try {
            testName = "Automation Id 33: Verify that the Exam Manager is able to manually Add Marking Keys of a paper.";
            test = logger.startTest(report, testName);
            this.url = DataUtil.getTestDataFromPropFile("cbla", "CBLA_IB_URL");
            utilities.deleteAllCookiesAndNavigateToUrl(url);
            WebDriverUtil.waitForPageLoad(driver);

            //CBLA IB Login
            cblaHomePage.login(driver, DataUtil.getCredentialsFromPropFile("credentials", "username"),
                    DataUtil.getCredentialsFromPropFile("credentials", "password"));
            logger.logPass(test, "Verify Login", "User is successfully logged in to CBLA IB: " + url);

            // exam name by creating paper
            String examName = createExam(test, driver);

            //Navigating to search exam page
            examSearchPage.navigateToSearchMenu(driver, "exams");
            Assert.assertEquals(examSearchPage.fetchSearchPageTitle(driver), "Exam Search");
            logger.logPass(test, "Verify User on Exam Search Page", "User successfully navigates to Exam Search Page");

            // Search for exam
            examSearchPage.clearAllSearchInputs(driver);
            examSearchPage.enterExamName(examName);
            examSearchPage.clickSearchButton(driver);
            examSearchPage.waitForFoundToast(driver);
            Assert.assertEquals(examSearchPage.fetchExamName(driver), examName, "Could not find Exam Name: " + examName);
            logger.logPass(test, "User Searches Exam Name", "Exam Name: " + examName + " found");

            // Click View Button and verify pop up
            Assert.assertTrue(examSearchPage.clickAndVerifyViewExam(driver));
            logger.logPass(test, "Verify User on View Exam Pop UP", "User on View exam Pop Up");

            // Click view from the attached papers table
            examSearchPage.clickViewExamPopUpEditButton(driver);

            // Click View Button and verify pop up Preview paper
            Assert.assertTrue(examSearchPage.clickAndVerifyViewPaper(driver));
            logger.logPass(test, "Verify User on Preview paper Pop UP", "User on Preview paper Pop Up");

            // Click edit paper
            examSearchPage.clickEditPaper(driver);

            // Add Marking Keys
            cblaAddPage.addNewMarkingKeyButton(driver);
            cblaAddPage.addMarkingKeysProfVersionMaxScore(driver, DataUtil.getTestDataFromPropFile("cbla", "professionAll"), DataUtil.getTestDataFromPropFile("cbla", "versionZone2"), DataUtil.getTestDataFromPropFile("cbla", "score"));
            logger.logPass(test, "Marking keys Profession, Version and Max Score", "User adds new marking key Profession: 'All', Version: 'Zone 2', Max Score: '10'");
            String[] data = {"Item2", "10", "1", "10"};
            cblaAddPage.addMarkingKeysWithoutCsv(driver, data);
            logger.logPass(test, "Add Marking keys", "User adds new marking key items: Key Name, Score, Group, Group Score");

            // Save edited paper
            examSearchPage.savePaper(driver);

            // Verify newly added marking Key
            examSearchPage.clickViewExamPopUpEditButton(driver);
            Assert.assertTrue(examSearchPage.clickAndVerifyViewPaper(driver));
            logger.logPass(test, "Verify User on Preview paper Pop UP", "User on Preview paper Pop Up");
            examSearchPage.clickEditPaper(driver);
            String expectedMarkingKeyName = "Item2";
            String actualMarkingKeyName = examSearchPage.fetchSecondMarkingKeyName();
            Assert.assertEquals(actualMarkingKeyName, expectedMarkingKeyName, "Marking Keys could not be validated");
            logger.logPass(test, "Verify Newly Added Marking Key", "New marking Key verified: " + actualMarkingKeyName);
        } catch (Throwable t) {
            reportError(driver, test, t, "Add new Marking Keys verification Failed");
        }
    }

    /*Test ID: C1688 7 */
    /*Automation Test Case ID: 35 */
    @Test(description = "Automation Id 35: Verify that the system displays Add marking key popup")
    public void verifyAddMarkingKeysPopUp() {
        try {
            testName = "Automation Id 35: Verify that the system displays Add marking key popup";
            test = logger.startTest(report, testName);
            this.url = DataUtil.getTestDataFromPropFile("cbla", "CBLA_IB_URL");
            utilities.deleteAllCookiesAndNavigateToUrl(url);
            WebDriverUtil.waitForPageLoad(driver);

            //CBLA IB Login
            cblaHomePage.login(driver, DataUtil.getCredentialsFromPropFile("credentials", "username"),
                    DataUtil.getCredentialsFromPropFile("credentials", "password"));
            logger.logPass(test, "Verify Login", "User is successfully logged in to CBLA IB: " + url);

            // exam id from prop file
            String examId = DataUtil.getTestDataFromPropFile("cbla", "examId");

            //Navigating to search exam page
            examSearchPage.navigateToSearchMenu(driver, "exams");
            Assert.assertEquals(examSearchPage.fetchSearchPageTitle(driver), "Exam Search");
            logger.logPass(test, "Verify User on Exam Search Page", "User successfully navigates to Exam Search Page");

            // Search for exam
            examSearchPage.clearAllSearchInputs(driver);
            examSearchPage.enterExamId(examId);
            examSearchPage.clickSearchButton(driver);
            examSearchPage.waitForFoundToast(driver);
            Assert.assertEquals(examSearchPage.fetchExamId(driver), examId, "Could not find Exam Name: " + examId);
            logger.logPass(test, "User Searches Exam Id", "Exam Id: " + examId + " found");

            // Click View Button and verify pop up
            Assert.assertTrue(examSearchPage.clickAndVerifyViewExam(driver));
            logger.logPass(test, "Verify User on View Exam Pop UP", "User on View exam Pop Up");

            // Click view from the attached papers table
            examSearchPage.clickViewExamPopUpEditButton(driver);

            // Click View Button and verify pop up Preview paper
            Assert.assertTrue(examSearchPage.clickAndVerifyViewPaper(driver));
            logger.logPass(test, "Verify User on Preview paper Pop UP", "User on Preview paper Pop Up");

            // Click edit paper
            examSearchPage.clickEditPaper(driver);

            // Add Marking Keys
            String expectedMarkingKeyName = "Add new key";
            String actualMarkingKeyName = examSearchPage.fetchAddNewKeyButtonText(driver);
            Assert.assertEquals(actualMarkingKeyName, expectedMarkingKeyName, "Add new Marking Keys button not visible");
            logger.logPass(test, "Verify Add new key button", "Pop up with '" + actualMarkingKeyName + "' button verified successfully");
        } catch (Throwable t) {
            reportError(driver, test, t, "Add new Marking Keys pop up verification Failed");
        }
    }

    /*Test ID: C1688 8 */
    /*Automation Test Case ID: 37 */
    @Test(description = "Automation Id 35: Verify that Exam Manager is able to select Profession and Version for the new Marking Key")
    public void verifyAddMarkingKeysProfessionVersion() {
        try {
            testName = "Automation Id 35: Verify that Exam Manager is able to select Profession and Version for the new Marking Key";
            test = logger.startTest(report, testName);
            this.url = DataUtil.getTestDataFromPropFile("cbla", "CBLA_IB_URL");
            utilities.deleteAllCookiesAndNavigateToUrl(url);
            WebDriverUtil.waitForPageLoad(driver);

            //CBLA IB Login
            cblaHomePage.login(driver, DataUtil.getCredentialsFromPropFile("credentials", "username"),
                    DataUtil.getCredentialsFromPropFile("credentials", "password"));
            logger.logPass(test, "Verify Login", "User is successfully logged in to CBLA IB: " + url);

            // exam id from prop file
            String examId = DataUtil.getTestDataFromPropFile("cbla", "examId");
            ;

            //Navigating to search exam page
            examSearchPage.navigateToSearchMenu(driver, "exams");
            Assert.assertEquals(examSearchPage.fetchSearchPageTitle(driver), "Exam Search");
            logger.logPass(test, "Verify User on Exam Search Page", "User successfully navigates to Exam Search Page");

            // Search for exam
            examSearchPage.clearAllSearchInputs(driver);
            examSearchPage.enterExamId(examId);
            examSearchPage.clickSearchButton(driver);
            examSearchPage.waitForFoundToast(driver);
            Assert.assertEquals(examSearchPage.fetchExamId(driver), examId, "Could not find Exam Name: " + examId);
            logger.logPass(test, "User Searches Exam Id", "Exam Id: " + examId + " found");

            // Click View Button and verify pop up
            Assert.assertTrue(examSearchPage.clickAndVerifyViewExam(driver));
            logger.logPass(test, "Verify User on View Exam Pop UP", "User on View exam Pop Up");

            // Click view from the attached papers table
            examSearchPage.clickViewExamPopUpEditButton(driver);

            // Click View Button and verify pop up Preview paper
            Assert.assertTrue(examSearchPage.clickAndVerifyViewPaper(driver));
            logger.logPass(test, "Verify User on Preview paper Pop UP", "User on Preview paper Pop Up");

            // Click edit paper
            examSearchPage.clickEditPaper(driver);

            // Add Marking Keys
            cblaAddPage.addNewMarkingKeyButton(driver);
            cblaAddPage.addMarkingKeysProfVersionMaxScore(driver, DataUtil.getTestDataFromPropFile("cbla", "professionAll"), DataUtil.getTestDataFromPropFile("cbla", "versionZone2"), DataUtil.getTestDataFromPropFile("cbla", "score"));
            logger.logPass(test, "Marking keys Profession, Version and Max Score", "User adds new marking key Profession: 'All', Version: 'Zone 2', Max Score: '10'");
        } catch (Throwable t) {
            reportError(driver, test, t, "Add new Marking Keys verification Failed");
        }
    }

    /*Test ID: C1838 4 */
    /*Automation Test Case ID: 38 */
    @Test(description = "Automation Id 38: Verify that System should not gives error message when text is copied from Microsoft Word and pasted in the Marking Keys text field")
    public void verifyManualMarkingKeys() {
        try {
            testName = "Automation Id 38: Verify that System should not gives error message when text is copied from Microsoft Word and pasted in the Marking Keys text field";
            test = logger.startTest(report, testName);
            this.url = DataUtil.getTestDataFromPropFile("cbla", "CBLA_IB_URL");
            utilities.deleteAllCookiesAndNavigateToUrl(url);
            WebDriverUtil.waitForPageLoad(driver);

            //CBLA IB Login
            cblaHomePage.login(driver, DataUtil.getCredentialsFromPropFile("credentials", "username"),
                    DataUtil.getCredentialsFromPropFile("credentials", "password"));
            logger.logPass(test, "Verify Login", "User is successfully logged in to CBLA IB: " + url);

            // exam name by creating paper
            String examName = createExam(test, driver);

            //Navigating to search exam page
            examSearchPage.navigateToSearchMenu(driver, "exams");
            Assert.assertEquals(examSearchPage.fetchSearchPageTitle(driver), "Exam Search");
            logger.logPass(test, "Verify User on Exam Search Page", "User successfully navigates to Exam Search Page");

            // Search for exam
            examSearchPage.clearAllSearchInputs(driver);
            examSearchPage.enterExamName(examName);
            examSearchPage.clickSearchButton(driver);
            examSearchPage.waitForFoundToast(driver);
            Assert.assertEquals(examSearchPage.fetchExamName(driver), examName, "Could not find Exam Name: " + examName);
            logger.logPass(test, "User Searches Exam Name", "Exam Name: " + examName + " found");
        } catch (Throwable t) {
            reportError(driver, test, t, "Add new Marking Keys verification Failed");
        }
    }

    /*Test ID: C1659 6 */
    /*Automation Test Case ID: 48 */
    @Test(description = "Automation Id 48: Verify that the data validation is implemented for all data fields in exam search page.")
    public void verifyExamSearchFillAllDetails() {
        try {
            testName = "Automation Id 48: Verify that the data validation is implemented for all data fields in exam search page.";
            test = logger.startTest(report, testName);
            this.url = DataUtil.getTestDataFromPropFile("cbla", "CBLA_IB_URL");
            utilities.deleteAllCookiesAndNavigateToUrl(url);
            WebDriverUtil.waitForPageLoad(driver);

            // exam id from prop file
            String examId = DataUtil.getTestDataFromPropFile("cbla", "examId");

            //CBLA IB Login
            cblaHomePage.login(driver, DataUtil.getCredentialsFromPropFile("credentials", "username"),
                    DataUtil.getCredentialsFromPropFile("credentials", "password"));
            logger.logPass(test, "Verify Login", "User is successfully logged in to CBLA IB: " + url);

            //Navigating to search exam page
            examSearchPage.navigateToSearchMenu(driver, "exams");
            Assert.assertEquals(examSearchPage.fetchSearchPageTitle(driver), "Exam Search");
            logger.logPass(test, "Verify User on Exam Search Page", "User successfully navigates to Exam Search Page");

            // Input all Search Fields
            examSearchPage.clearAllSearchInputs(driver);
            String examData = DataUtil.getTestDataFromPropFile("cbla", "exam275SearchDetails");
            String examName = examData.split(",")[0];
            String examUserName = examData.split(",")[2];
            String examDate = examData.split(",")[3];
            String examStatus = examData.split(",")[4];
            examSearchPage.fillAllSearchFields(examName, examId, examUserName, examDate, examStatus);

            // Search for exam
            examSearchPage.clickSearchButton(driver);
            examSearchPage.waitForExamTest(driver);
            Assert.assertEquals(examSearchPage.fetchExamId(driver), examId);
            logger.logPass(test, "User Searches Exam ID", "Exam Id: " + examId + " found");

            // Click View Button and verify pop up
            Assert.assertTrue(examSearchPage.clickAndVerifyViewExam(driver));
            logger.logPass(test, "Verify User on View Exam Pop UP", "User on View exam Pop Up");
        } catch (Throwable t) {
            reportError(driver, test, t, "Exam Search Enter All Search Fields verification Failed");
        }
    }

    /*Test ID: C1659 7, C1660 0 */
    /*Automation Test Case ID: 49, 51 */
    @Test(description = "Automation Id 49: Verify that the exam manger is able to publish or unpublish an Exam. \n Automation Id 51: Verify that the system locks the paper from editing if the exam is not already locked, when the exam manager publish the exam.")
    public void verifyExamPublishUnpublish() {
        try {
            testName = "Automation Id 49: Verify that the exam manger is able to publish or unpublish an Exam. \n Automation Id 51: Verify that the system locks the paper from editing if the exam is not already locked, when the exam manager publish the exam.";
            test = logger.startTest(report, testName);
            this.url = DataUtil.getTestDataFromPropFile("cbla", "CBLA_IB_URL");
            utilities.deleteAllCookiesAndNavigateToUrl(url);
            WebDriverUtil.waitForPageLoad(driver);

            // exam id from prop file
            String examId = DataUtil.getTestDataFromPropFile("cbla", "exam3248Id");

            //CBLA IB Login
            cblaHomePage.login(driver, DataUtil.getCredentialsFromPropFile("credentials", "username"),
                    DataUtil.getCredentialsFromPropFile("credentials", "password"));
            logger.logPass(test, "Verify Login", "User is successfully logged in to CBLA IB: " + url);

            //Navigating to search exam page
            examSearchPage.navigateToSearchMenu(driver, "exams");
            Assert.assertEquals(examSearchPage.fetchSearchPageTitle(driver), "Exam Search");
            logger.logPass(test, "Verify User on Exam Search Page", "User successfully navigates to Exam Search Page");

            // Input Search Exam ID
            examSearchPage.clearAllSearchInputs(driver);
            examSearchPage.enterExamId(examId);

            // Search for exam
            examSearchPage.clickSearchButton(driver);
            examSearchPage.waitForFoundToast(driver);
            Assert.assertEquals(examSearchPage.fetchExamId(driver), examId, "Could not find Exam Id: " + examId);
            logger.logPass(test, "User Searches Exam ID", "Exam Id: " + examId + " found");

            // Click View Button and verify pop up
            Assert.assertTrue(examSearchPage.clickAndVerifyViewExam(driver));
            logger.logPass(test, "Verify User on View Exam Pop UP", "User on View exam Pop Up");

            String expectedPublishSuccessMessage = DataUtil.getTestDataFromPropFile("cbla", "examPublishSuccessMessage");
            String actualPublishSuccessMessage = "";
            String expectedUnPublishSuccessMessage = DataUtil.getTestDataFromPropFile("cbla", "examUnPublishConfirmation");
            String actualUnPublishSuccessMessage = "";
            // check for exam status
            if (examSearchPage.fetchExamStatus().equals("Draft")) {
                // Publish and Verify
                actualPublishSuccessMessage = examSearchPage.publishPaperAndFetchSuccessMessage(driver);
                Assert.assertEquals(actualPublishSuccessMessage, expectedPublishSuccessMessage, "Couldn't publish exam, expected: " + expectedPublishSuccessMessage + " got: " + actualPublishSuccessMessage);
                logger.logPass(test, "Verify User can Publish Exam", "Successfully Published Exam with message: " + actualPublishSuccessMessage);

                //Refresh and unpublish exam
                examSearchPage.refreshPage(driver);
                Assert.assertTrue(examSearchPage.clickAndVerifyViewExam(driver));
                logger.logPass(test, "Verify User on View Exam Pop UP", "User on View exam Pop Up");
                String searchExamStateActual = examSearchPage.fetchExamState();
                Assert.assertEquals(searchExamStateActual, "LOCKED", "Couldn't verify exam state as LOCKED");
                logger.logPass(test, "Verify Exam in LOCKED State", "Exam in LOCKED State after Publish");
                actualUnPublishSuccessMessage = examSearchPage.unpublishPaperAndFetchSuccessMessage(driver);
                Assert.assertEquals(actualUnPublishSuccessMessage, expectedUnPublishSuccessMessage, "Couldn't publish exam, expected: " + expectedPublishSuccessMessage + " got: " + actualUnPublishSuccessMessage);
                logger.logPass(test, "Verify User can Un-Publish Exam", "Successfully Un-Published Exam with message: " + actualUnPublishSuccessMessage);
            } else {
                // UnPublish exam
                Assert.assertTrue(examSearchPage.clickAndVerifyViewExam(driver));
                logger.logPass(test, "Verify User on View Exam Pop UP", "User on View exam Pop Up");
                actualUnPublishSuccessMessage = examSearchPage.unpublishPaperAndFetchSuccessMessage(driver);
                Assert.assertEquals(actualUnPublishSuccessMessage, expectedUnPublishSuccessMessage, "Couldn't unpublish exam, expected: " + expectedUnPublishSuccessMessage + " got: " + actualUnPublishSuccessMessage);
                logger.logPass(test, "Verify User can Un-Publish Exam", "Successfully Un-Published Exam with message: " + actualUnPublishSuccessMessage);

                // Publish and Verify
                examSearchPage.refreshPage(driver);
                actualPublishSuccessMessage = examSearchPage.publishPaperAndFetchSuccessMessage(driver);
                Assert.assertEquals(actualPublishSuccessMessage, expectedPublishSuccessMessage, "Couldn't publish exam, expected: " + expectedPublishSuccessMessage + " got: " + actualPublishSuccessMessage);
                logger.logPass(test, "Verify User can Publish Exam", "Successfully Published Exam with message: " + actualPublishSuccessMessage);
            }
        } catch (Throwable t) {
            reportError(driver, test, t, "Exam Search Publish/UnPublish verification Failed");
        }
    }
}
