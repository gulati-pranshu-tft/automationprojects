package com.cbla.testcases.smokes;

import com.cbla.core.TestCore;
import com.cbla.pages.CBLAAddPage;
import com.cbla.pages.CBLAHomePage;
import com.cbla.pages.ExamPopUp;
import com.cbla.utils.DataUtil;
import com.cbla.utils.Utilities;
import com.cbla.utils.WebDriverUtil;
import com.relevantcodes.extentreports.ExtentTest;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.openqa.selenium.WebDriver;

import java.util.*;

public class CreationOfExamAndPaperTest extends TestCore {

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

    /*Test ID: C1658 2 */
    /*Automation Test Case ID: 1 */
    @Test(description = "Automation ID 1: Verify that there is an option to create paper is implemented in the 'Create Exam' page.")
    public void verifyCreateExamPage() {
        try {
            testName = "Automation ID 1: Verify that there is an option to create paper is implemented in the 'Create Exam' page.";
            test = logger.startTest(report, testName);
            loginCreateExamCreatePaper(test, driver);
        } catch (Throwable t) {
            reportError(driver, test, t, "Create Paper popup is not verified successfully");
        }
    }

    /*Test ID: C1658 3 */
    /*Automation Test Case ID: 2 */
    @Test(description = "Automation ID 2: A warning message should displayed when the user clicks on Save button \"Missing Marking Keys\" Yes/No with a message saying 'Changes have been successfully saved'.")
    public void verifyMissingMarkingKeysWarningMessage() {
        try {
            testName = "Automation ID 2: A warning message should displayed when the user clicks on Save button \"Missing Marking Keys\" Yes/No with a message saying 'Changes have been successfully saved'.";
            test = logger.startTest(report, testName);
            loginCreateExamCreatePaper(test, driver);

            // Create Paper Mandatory Fields
            cblaAddPage.fillCreatePaperMandatoryFields(driver, cblaAddPage.paperName(), "Listening");

            // Upload pdf and add mandatory fields
            cblaAddPage.addPdf(driver);
            cblaAddPage.uploadDoc(driver, "pdf");
            cblaAddPage.setPdfMandatoryParameters(driver, "All", DataUtil.getTestDataFromPropFile("cbla", "version"), DataUtil.getTestDataFromPropFile("cbla", "order"), DataUtil.getTestDataFromPropFile("cbla", "pdfType"));

            // Click save Button
            cblaAddPage.clickCreatePaperSaveButton();

            //Verify Missing Marking Keys Warning Message
            Assert.assertTrue(cblaAddPage.verifyMissingMarkingKeysWarningMessage(driver), "Missing Marking Keys Warning message was not verified");
            logger.logPass(test, "Verify Marking Keys Warning Message", "Marking Keys Warning verified successfully \n Warning Message is: \"Please note there is a missing marking key in this paper. Do you want to continue editing?\"");
        } catch (Throwable t) {
            reportError(driver, test, t, "'Missing Marking Keys' Warning message is not verified successfully");
        }
    }

    /*Test ID: C1658 5 */
    /*Automation Test Case ID: 3 */
    @Test(description = "Automation ID 3: The system shouldn't allow the user to upload a file other than a PDF and there should be a validation error message.")
    public void verifyNonPdfAlert() {
        try {
            testName = "Automation ID 3: The system shouldn't allow the user to upload a file other than a PDF and there should be a validation error message.";
            test = logger.startTest(report, testName);
            loginCreateExamCreatePaper(test, driver);

            cblaAddPage.fillCreatePaperMandatoryFields(driver, cblaAddPage.paperName(), "Listening");

            cblaAddPage.addPdf(driver);
            cblaAddPage.uploadDoc(driver, "audio");

            Assert.assertTrue(cblaAddPage.verifyNonPdfDocAlert(driver), "Non Pdf Doc Alert was not verified");
            logger.logPass(test, "Non PDF file alert verification", "Non PDF file alert verified successfully \n Alet: \"Booklet must be a PDF file\"");
        } catch (Throwable t) {
            reportError(driver, test, t, "Non PDF file alert verification is not verified successfully");
        }
    }

    /*Test ID: C1658 7 */
    /*Automation Test Case ID: 5 */
    @Test(description = "Automation ID 5: Browse, Delete, View, Move up and Move down actions should be available under the \"Action\" column.")
    public void verifyActionOptionsAvailable() {
        try {
            testName = "Automation ID 5: Browse, Delete, View, Move up and Move down actions should be available under the \"Action\" column.";
            test = logger.startTest(report, testName);
            loginCreateExamCreatePaper(test, driver);

            // Verify Marking Keys Action Options
            Assert.assertTrue(cblaAddPage.verifyMarkingKeysActionOptions(driver, test), "Marking Keys Action Options not visible");
            logger.logPass(test, "Marking Keys Action Buttons", "Clicked on 'Import' -> 'Choose File' to validate: 'Browse'; " +
                    "\n Clicked on 'Edit' to Validate: Delete, View, Move up and Move down actions verified successfully");
        } catch (Throwable t) {
            reportError(driver, test, t, "Browse, Delete, View, Move up and Move down actions on Marking Keys is not verified successfully");
        }
    }

    /*Test ID: C1687 8 */
    /*Automation Test Case ID: 6 */
    @Test(description = "Automation ID 6: Verify that in the Create Paper window, subtest Type drop down is a mandatory field and displays Listening/Reading/ Writing/Speaking options.")
    public void verifyPaperTypeDropdown() {
        try {
            testName = "Automation ID 6: Verify that in the Create Paper window, subtest Type drop down is a mandatory field and displays Listening/Reading/ Writing/Speaking options.";
            test = logger.startTest(report, testName);
            loginCreateExamCreatePaper(test, driver);

            // Validating paper type dropdown
            cblaAddPage.clickCreatePaperButton();
            List<String> ExpectedDropdownValues = Arrays.asList("Listening", "Reading", "Writing", "Speaking");
            List<String> dropdownList = cblaAddPage.fetchPaperTypeDropdown(driver);
            Assert.assertEquals(dropdownList, ExpectedDropdownValues, "Paper type dropdown is not validated successfully");
            logger.logPass(test, "Paper type dropdown verification", "Paper type dropdown verified successfully \n Paper Types: Listening, Reading, Writing, Speaking");
        } catch (Throwable t) {
            reportError(driver, test, t, "Create Paper -> SubTest type dropdown options is not verified successfully");
        }
    }

    /*Test ID: C1687 9 */
    /*Automation Test Case ID: 7 */
    @Test(description = "Automation ID 7: Exam manager should be able to import PDF file for Paper by clicking on \"Upload\" under the \"Actions\" column in the PDFs table and imported file should be displayed under \"File\" column of \"PDFs\" table.")
    public void verifyPdfUpload() {
        try {
            testName = "Automation ID 7: Exam manager should be able to import PDF file for Paper by clicking on \"Upload\" under the \"Actions\" column in the PDFs table and imported file should be displayed under \"File\" column of \"PDFs\" table.";
            test = logger.startTest(report, testName);
            loginCreateExamCreatePaper(test, driver);

            // Upload pdf
            cblaAddPage.addPdf(driver);
            cblaAddPage.uploadDoc(driver, "pdf");
            logger.logPass(test, "Upload pdf file", "File uploaded from: Upload -> Rad Test -> Listening Folder");

            //Validation of uploaded file name
            String expectedFileName = "Listening_Question Paper_V1.pdf";
            String fileName = cblaAddPage.fetchUploadedFileName(driver);
            Assert.assertEquals(fileName, expectedFileName);
            logger.logPass(test, "Uploaded File Verification Successful", "Uploaded File name: " + fileName);
        } catch (Throwable t) {
            reportError(driver, test, t, "PDF File upload is not verified successfully");
        }
    }

    /*Test ID: C1688 0 */
    /*Automation Test Case ID: 8 */
    @Test(description = "Automation ID 8: 'Professions' drop list should be a mandatory field and has all 12 profession options")
    public void verifyPdfProfessionDropdown() {
        try {
            testName = "Automation ID 8: 'Professions' drop list should be a mandatory field and has all 12 profession options";
            test = logger.startTest(report, testName);
            loginCreateExamCreatePaper(test, driver);

            cblaAddPage.fillCreatePaperMandatoryFields(driver, cblaAddPage.paperName(), "Listening");
            logger.logPass(test, "Create Paper Mandatory Fields", "Filled create paper mandatory fields with Listening as subtest type ");

            // Validating pdf dropdown options
            cblaAddPage.addPdf(driver);
            logger.logPass(test, "Add PDF", "Added PDF Rad-Test -> Listening");

            //Verify PDF type dropdown list
            List<String> ExpectedDropdownValues = Arrays.asList("All", "Dentistry", "Dietetics", "Medicine", "Nursing", "Occupational therapy", "Optometry", "Pharmacy", "Physiotherapy", "Podiatry", "Radiography", "Speech pathology", "Veterinary science");
            List<String> dropdownList = cblaAddPage.fetchProfessionsDropdown(driver);
            Assert.assertEquals(dropdownList.toString(), ExpectedDropdownValues.toString());
            logger.logPass(test, "Verify PDF Professions", "Verified user gets the expected dropdown options: " + dropdownList);
        } catch (Throwable t) {
            reportError(driver, test, t, "Create Paper popup is not verified successfully");
        }
    }

    /*Test ID: C1688 2 */
    /*Automation Test Case ID: 9 */
    @Test(description = "Automation ID 9: The drop list under 'Version' column should be a mandatory field and displays 4 version options")
    public void verifyPdfVersionDropdown() {
        try {
            testName = "Automation ID 9: The drop list under 'Version' column should be a mandatory field and displays 4 version options";
            test = logger.startTest(report, testName);
            loginCreateExamCreatePaper(test, driver);

            cblaAddPage.fillCreatePaperMandatoryFields(driver, cblaAddPage.paperName(), "Listening");

            // Verify version is pdf mandatory
            cblaAddPage.addPdf(driver);
            cblaAddPage.setPdfMandatoryParameters(driver, "All", "", DataUtil.getTestDataFromPropFile("cbla", "order"), DataUtil.getTestDataFromPropFile("cbla", "pdfType"));
            cblaAddPage.clickCreatePaperSaveButton();
            String toolTipError = cblaAddPage.fetchMandatoryFieldToolTip(driver).get(0);
            String expectedError = "Version is mandatory";
            Assert.assertEquals(toolTipError, expectedError);
            logger.logPass(test, "Verify Version Tool Tip error", "Verified version is a mandatory field with error: " + toolTipError);

            //Verify PDF type dropdown list
            List<String> ExpectedDropdownValues = Arrays.asList("Zone 1", "Zone 2", "Contingency 1", "Contingency 2");
            List<String> dropdownList = cblaAddPage.fetchPdfVersionDropdown(driver);
            Assert.assertEquals(dropdownList, ExpectedDropdownValues);
            logger.logPass(test, "Verify PDF Versions", "Verified user gets the expected dropdown options: " + dropdownList.toString());
        } catch (Throwable t) {
            reportError(driver, test, t, "PDF Version dropdown is not verified successfully");
        }
    }

    /*Test ID: C1688 3 */
    /*Automation Test Case ID: 10 */
    @Test(description = "Automation ID 10: Verify that the 'Order' in PDF table is a mandatory field")
    public void verifyPdfOrderIsMandatory() {
        try {
            testName = "Automation ID 10: Verify that the 'Order' in PDF table is a mandatory field";
            test = logger.startTest(report, testName);
            loginCreateExamCreatePaper(test, driver);

            cblaAddPage.fillCreatePaperMandatoryFields(driver, cblaAddPage.paperName(), "Listening");

            //Verify order error
            cblaAddPage.addPdf(driver);
            cblaAddPage.setPdfMandatoryParameters(driver, "All", DataUtil.getTestDataFromPropFile("cbla", "version"), "", DataUtil.getTestDataFromPropFile("cbla", "pdfType"));
            logger.logPass(test, "Set Pdf Mandatory Fields", "User user PDF Mandatory fields except 'Order'");

            cblaAddPage.clickCreatePaperSaveButton();
            String toolTipError = cblaAddPage.fetchMandatoryFieldToolTip(driver).get(0);
            String expectedError = "Order is mandatory";

            Assert.assertEquals(toolTipError, expectedError);
            logger.logPass(test, "Verify Order Tool Tip error", "Verified order is a mandatory field with error: " + toolTipError);
        } catch (Throwable t) {
            reportError(driver, test, t, "PDF 'Order' is not verified successfully");
        }
    }

    /*Test ID: C1659 1 */
    /*Automation Test Case ID: 36 */
    @Test(description = "Automation ID 36: Verify that System pops up Validation Message if mandatory fields in Marking Keys section are not filled.")
    public void verifyMarkingKeysBlankError() {
        try {
            String testName = "Automation ID 36: Verify that System pops up Validation Message if mandatory fields in Marking Keys section are not filled.";
            ExtentTest test = logger.startTest(report, testName);
            String markingKey1 = DataUtil.getTestDataFromPropFile("cbla", "markingKey1");
            String markingKey2 = DataUtil.getTestDataFromPropFile("cbla", "markingKey2");
            String score = DataUtil.getTestDataFromPropFile("cbla", "score");

            this.url = DataUtil.getTestDataFromPropFile("cbla", "CBLA_IB_URL");
            utilities.deleteAllCookiesAndNavigateToUrl(url);
            WebDriverUtil.waitForPageLoad(driver);

            //CBLA IB Login
            cblaHomePage.login(driver, DataUtil.getCredentialsFromPropFile("credentials", "username"),
                    DataUtil.getCredentialsFromPropFile("credentials", "password"));
            logger.logPass(test, "Verify Login", "User is successfully logged in to CBLA IB: " + url);

            //Open and verify Create Exam Popup
            Assert.assertTrue(cblaHomePage.verifyExamPopupIsOpen(driver).equals("Create exam"), "Exam Popup could not open!");
            logger.logPass(test, "Verify Exam Popup", "User successfully open exam popup!");

            //Open create paper and verify error pop up
            String keyError = cblaHomePage.verifyMarkingKeysError(driver, markingKey1, markingKey2, score);
            Assert.assertEquals(keyError, "Your data was not saved, please correct any errors and save again.");
            logger.logPass(test, "Verify Marking Keys Error", "Verified marking keys throws error at blank section " + keyError);
            logger.endTest(report, test, testName);
        } catch (Throwable t) {
            reportError(driver, test, t, "Making Keys Validation Message is not verified successfully");
        }
    }

    /*Test ID: C1688 5 */
    /*Automation Test Case ID: 12 */
    @Test(description = "Automation ID 12: Verify that 'PDF Type' dropdown is mandatory and displays the four options.")
    public void verifyPfdDropdownOptions() {
        try {
            String testName = "Automation ID 12: Verify that 'PDF Type' dropdown is mandatory and displays the four options.";
            test = logger.startTest(report, testName);
            loginCreateExamCreatePaper(test, driver);

            cblaAddPage.addPdf(driver);

            //Verify PDF type dropdown list
            List<String> dropdownList = cblaHomePage.fetchPDFTypeDropdownValues(driver);
            Assert.assertEquals(dropdownList.toString(), DataUtil.getTestDataFromPropFile("cbla", "expectedDropdownList"));
            logger.logPass(test, "Verify PDF Type Options", "Verified user gets the expected dropdown options: " + dropdownList.toString());
        } catch (Throwable t) {
            reportError(driver, test, t, "'PDF Type' dropdown is not verified successfully");
        }

    }

    /*Test ID: C1689 1 */
    /*Automation Test Case ID: 14 */
    @Test(description = "Automation ID 14: Verify that 'Professions' droplist is a mandatory field and displays 12 professions along with 'All' option in the 'Marking Keys' table.")
    public void verifyProfessionalDropdownOptions() {
        try {
            String testName = "Automation ID 14: Verify that 'Professions' droplist is a mandatory field and displays 12 professions along with 'All' option in the 'Marking Keys' table.";
            test = logger.startTest(report, testName);
            loginCreateExamCreatePaper(test, driver);
            cblaAddPage.addMarkingKeys(driver);

            //Verify Professional dropdown list
            List<String> dropdownList = cblaAddPage.fetchMarkingKeysProfessionsDropdown(driver);
            Assert.assertEquals(dropdownList.toString(), DataUtil.getTestDataFromPropFile("cbla", "expectedProfessionalDropdownList"));
            logger.logPass(test, "Verify Professional Options", "Verified user gets the expected dropdown options: " + dropdownList.toString());
            logger.endTest(report, test, testName);
        } catch (Throwable t) {
            reportError(driver, test, t, "Marking Keys Professions dropdown is not verified successfully");
        }
    }

    /*Test ID: C1689 2 */
    /*Automation Test Case ID: 15 */
    @Test(description = "Automation ID 15: Verify that droplist under 'Version' column is a mandatory field and displays 4 versions in the 'Marking Key' table.")
    public void verifyVersionDropdownOptions() {
        try {
            String testName = "Automation ID 15: Verify that droplist under 'Version' column is a mandatory field and displays 4 versions in the 'Marking Key' table.";
            test = logger.startTest(report, testName);
            loginCreateExamCreatePaper(test, driver);
            cblaAddPage.addMarkingKeys(driver);

            //Verify Version dropdown list
            List<String> dropdownList = cblaAddPage.fetchMarkingKeysVersionDropdown(driver);
            Assert.assertEquals(dropdownList.toString(), DataUtil.getTestDataFromPropFile("cbla", "expectedVersionDropdownList"));
            logger.logPass(test, "Verify Version Options", "Verified user gets the expected dropdown options: " + dropdownList.toString());
            logger.endTest(report, test, testName);
        } catch (Throwable t) {
            reportError(driver, test, t, "Marking Keys Version column dropdown is not verified successfully");
        }
    }

    /*Test ID: C1689 3 */
    /*Automation Test Case ID: 16 */
    @Test(description = "Automation ID 16: Verify that brief content of marking keys is displayed under \"Keys\" column of \"Marking Keys\" table.")
    public void verifyBriefContentMarkingKeys() {
        try {
            testName = "Automation ID 16: Verify that brief content of marking keys is displayed under \"Keys\" column of \"Marking Keys\" table.";
            test = logger.startTest(report, testName);
            loginCreateExamCreatePaper(test, driver);

            // Add Marking Keys
            cblaAddPage.addNewMarkingKeyButton(driver);
            String[] data = {"Item1", "10", "1", "10"};
            cblaAddPage.addMarkingKeysWithoutCsv(driver, data);
            logger.logPass(test, "Add Marking keys", "User adds new marking key items: Key Name, Score, Group, Group Score");

            //verify marking key Brief name
            String markingKeyBriefName = cblaAddPage.fetchKeyNameMarkingKey(driver);
            String expectedBriefName = "Item1";

            Assert.assertEquals(expectedBriefName, markingKeyBriefName);
            logger.logPass(test, "Check the content under 'Keys' column", "Marking Keys Brief Name verified" + markingKeyBriefName + "Successfully");
        } catch (Throwable t) {
            reportError(driver, test, t, "Brief content of Marking Keys under 'keys' column is not verified successfully");
        }
    }

    /*Test ID: C1658 8 */
    /*Automation Test Case ID: 17 */
    @Test(description = "Automation ID 17: Verify that the system check and verify if all the mandatory fields are filled upon clicking the \"Save\" button on the \"create Paper\" page.")
    public void verifyErrors() {
        try {
            testName = "Automation ID 17: Verify that the system check and verify if all the mandatory fields are filled upon clicking the \"Save\" button on the \"create Paper\" page.";
            test = logger.startTest(report, testName);
            loginCreateExamCreatePaper(test, driver);

            // Create Paper Mandatory Fields
            String paperName = cblaAddPage.paperName();
            cblaAddPage.fillCreatePaperMandatoryFields(driver, paperName, " ");
            logger.logPass(test, "Add paper Mandatory Fields except paper type", "Added only Paper name and date");

            // Upload pdf and add mandatory fields
            cblaAddPage.addPdf(driver);
            cblaAddPage.uploadDoc(driver, "pdf");
            logger.logPass(test, "Upload PDF Doc", "uploads PDF doc and leaves mandatory fields empty");

            //Add Marking Keys Data
            cblaAddPage.addNewMarkingKeyButton(driver);

            String[] data = {"Item1", "10", "1", "10"};
            cblaAddPage.addMarkingKeysWithoutCsv(driver, data);
            logger.logPass(test, "Add Marking keys", "User adds new marking key items: Key Name, Score, Group, Group Score");

            // Click save Button
            cblaAddPage.clickCreatePaperSaveButton();

            // Verify Error Notification
            String actualErrorNotification = cblaAddPage.fetchPopUpErrorText(driver);
            String expectedErrorNotification = "Your data was not saved, please correct any errors and save again.";

            Assert.assertEquals(actualErrorNotification, expectedErrorNotification);
            logger.logPass(test, "Verify Error Pop Up", "Verified Notification Pop Up error name: " + actualErrorNotification);

            // Verify error indicators
            List<String> actualErrorList = cblaAddPage.fetchMandatoryFieldToolTip(driver);
            String expectedErrorList = "[Paper type is mandatory, Version is mandatory, Version is mandatory]";

            Assert.assertEquals(actualErrorList.toString(), expectedErrorList);
            logger.logPass(test, "Verify Error Indicators", "Verified mandatory Fields errors list: " + actualErrorList);
        } catch (Throwable t) {
            reportError(driver, test, t, "Mandatory fields errors for Paper Type, PDF Version and Mandatory Fields Version are not verified successfully");
        }
    }

    /*Test ID: C1688 9 */
    /*Automation Test Case ID: 18 */
    @Test(description = "Automation ID 18: Verify that system verifies if there are multiple PDFs have same set up upon clicking on \"Save\" button.")
    public void verifyMultiplePdfsSameData() {
        try {
            testName = "Automation ID 18: Verify that system verifies if there are multiple PDFs have same set up upon clicking on \"Save\" button.";
            test = logger.startTest(report, testName);
            loginCreateExamCreatePaper(test, driver);

            // Create Paper Mandatory Fields
            cblaAddPage.fillCreatePaperMandatoryFields(driver, cblaAddPage.paperName(), "Listening");

            // Upload pdf and add mandatory fields
            cblaAddPage.addPdf(driver);
            cblaAddPage.uploadDoc(driver, "pdf");
            cblaAddPage.setPdfMandatoryParameters(driver, "All", DataUtil.getTestDataFromPropFile("cbla", "version"), DataUtil.getTestDataFromPropFile("cbla", "order"), DataUtil.getTestDataFromPropFile("cbla", "pdfType"));
            logger.logPass(test, "Add PDF and mandatory Fields", "User adds PDF and all mandatory fields- Version: Zone 1, Order: 1, PDF Type: Listening Answer Booklet");

            // Upload second pdf with same data
            cblaAddPage.addPdf(driver);
            cblaAddPage.uploadDoc(driver, "pdf");
            cblaAddPage.setPdfMandatoryParameters(driver, "All", DataUtil.getTestDataFromPropFile("cbla", "version"), DataUtil.getTestDataFromPropFile("cbla", "order"), DataUtil.getTestDataFromPropFile("cbla", "pdfType"));
            logger.logPass(test, "Add Same PDF and Same Mandatory Fields", "User adds PDF and all mandatory fields- Version: Zone 1, Order: 1, PDF Type: Listening Answer Booklet");

            // Click save Button
            cblaAddPage.clickCreatePaperSaveButton();

            // Fetch for tooltip error
            String toolTipError = cblaAddPage.fetchMandatoryFieldToolTip(driver).get(0);
            String expectedError = "PDFs cannot have the same set up i.e same profession, version, order and PDF type";

            Assert.assertEquals(toolTipError, expectedError);
            logger.logPass(test, "Verify Same PDF Fields Tool Tip error", "Verified error: " + toolTipError);
        } catch (Throwable t) {
            reportError(driver, test, t, "Multiple PDFs with same fields error is not verified successfully");
        }
    }

    /*Test ID: C1689 0 */
    /*Automation Test Case ID: 19 */
    @Test(description = "Automation ID 19: Verify that system verifies if there are multiple Marking Keys have same set up upon clicking on \"Save\" button.")
    public void verifyMultipleMarkingKeysSameData() {
        try {
            testName = "Automation ID 19: Verify that system verifies if there are multiple Marking Keys have same set up upon clicking on \"Save\" button.";
            test = logger.startTest(report, testName);
            loginCreateExamCreatePaper(test, driver);

            // Create Paper Mandatory Fields
            cblaAddPage.fillCreatePaperMandatoryFields(driver, cblaAddPage.paperName(), "Listening");
            logger.logPass(test, "Create Paper Mandatory Fields added", "Add paper name, date and paper type");

            // Upload pdf and mandatory fields
            cblaAddPage.addPdf(driver);
            cblaAddPage.uploadDoc(driver, "pdf");
            cblaAddPage.setPdfMandatoryParameters(driver, "All", DataUtil.getTestDataFromPropFile("cbla", "version"), DataUtil.getTestDataFromPropFile("cbla", "order"), DataUtil.getTestDataFromPropFile("cbla", "pdfType"));
            logger.logPass(test, "Add PDF and mandatory Fields", "User adds PDF and all mandatory fields- Version: Zone 1, Order: 1, PDF Type: Listening Answer Booklet");

            // Add Marking Keys
            cblaAddPage.addNewMarkingKeyButton(driver);
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

    /*Test ID: C1689 4 */
    /*Automation Test Case ID: 20 */
    @Test(description = "Automation ID 20: Verify that user is able to save the paper.")
    public void verifySavePaper() {
        try {
            testName = "Automation ID 20: Verify that user is able to save the paper.";
            test = logger.startTest(report, testName);
            loginCreateExamCreatePaper(test, driver);

            // Create Paper Mandatory Fields
            String paperName = cblaAddPage.paperName();
            cblaAddPage.fillCreatePaperMandatoryFields(driver, paperName, "Listening");
            logger.logPass(test, "Add paper Mandatory Fields", "Added Paper name, date and paper type");

            // Upload pdf and add mandatory fields
            cblaAddPage.addPdf(driver);
            cblaAddPage.uploadDoc(driver, "pdf");
            cblaAddPage.setPdfMandatoryParameters(driver, "All", DataUtil.getTestDataFromPropFile("cbla", "version"), DataUtil.getTestDataFromPropFile("cbla", "order"), DataUtil.getTestDataFromPropFile("cbla", "pdfType"));
            logger.logPass(test, "Add PDF and mandatory Fields", "User adds PDF and all mandatory fields- Version: Zone 1, Order: 1, PDF Type: Listening Answer Booklet");

            // Add Auto Marking Keys
            cblaAddPage.addAutoMarkingKeys(driver, "1", "A");
            logger.logPass(test, "Add Auto Marking Keys", "Auto Marking Keys added Question: 1, Option: A");

            //Add Marking Keys Data
            cblaAddPage.addNewMarkingKeyButton(driver);
            String[] data = {"Item1", "10", "1", "10"};
            cblaAddPage.addMarkingKeysWithoutCsv(driver, data);
            logger.logPass(test, "Add Marking keys", "User adds new marking key items: Key Name, Score, Group, Group Score");
            cblaAddPage.addMarkingKeysProfVersionMaxScore(driver, "All", DataUtil.getTestDataFromPropFile("cbla", "version"), DataUtil.getTestDataFromPropFile("cbla", "score"));
            logger.logPass(test, "Marking keys Profession, Version and Max Score", "User adds new marking key Profession: 'All', Version: 'Zone 1', Max Score: '10'");

            // Click save Button
            cblaAddPage.clickCreatePaperSaveButton();

            // Verify Paper name
            String expectedPaperName = "[" + paperName + "]";
            String actualPaperName = examPopUp.fetchAttachedPaperName(driver).toString();
            Assert.assertEquals(actualPaperName, expectedPaperName);
            logger.logPass(test, "Verify Paper Name in Exams Table", "Verified paper name: " + actualPaperName.toString());
        } catch (Throwable t) {
            reportError(driver, test, t, "Save paper was not verified successfully");
        }
    }

    /*Test ID: C1689 5 */
    /*Automation Test Case ID: 21 */
    @Test(description = "Automation ID 21: Verify that system verifies that all mandatory fields are filled and papers for all the four subtest types are created and attached to the Exam upon clicking on \"Save\" button on the Exam screen.")
    public void verifySavePaperAndExam() {
        try {
            testName = "Automation ID 21: Verify that system verifies that all mandatory fields are filled and papers for all the four subtest types are created and attached to the Exam upon clicking on \"Save\" button on the Exam screen.";
            test = logger.startTest(report, testName);
            this.url = DataUtil.getTestDataFromPropFile("cbla", "CBLA_IB_URL");
            utilities.deleteAllCookiesAndNavigateToUrl(url);
            WebDriverUtil.waitForPageLoad(driver);

            //CBLA IB Login
            cblaHomePage.login(driver, DataUtil.getCredentialsFromPropFile("credentials", "username"),
                    DataUtil.getCredentialsFromPropFile("credentials", "password"));
            logger.logPass(test, "Verify Login", "User is successfully logged in to CBLA IB: " + url);

            //Navigating to add exam popup
            cblaAddPage.navigateToAddMenu(driver, "exam");

            // Data to be used
            List<String> paperSubTypes = new ArrayList<>(Arrays.asList(DataUtil.getTestDataFromPropFile("cbla", "pdfSubTypes").split(",")));
            List<String> paperNames = new ArrayList<>();
            HashMap<String, String> paperTypePdfTypeMap = new HashMap<>();
            paperTypePdfTypeMap.put(paperSubTypes.get(0), "Listening Answer Booklet");
            paperTypePdfTypeMap.put(paperSubTypes.get(3), "Writing Text Booklet");
            paperTypePdfTypeMap.put(paperSubTypes.get(2), "Speaking Combination Answer Booklet");
            paperTypePdfTypeMap.put(paperSubTypes.get(1), "Parts B & C");
            String profession = "All";

            // Loop for all paper types
            for (String paperSubType : paperSubTypes) {
                //Validation of add exam popup
                Assert.assertTrue(cblaAddPage.verifyPopupIsDisplayed(driver, "Create exam"), "Create exam popup is not displayed");

                //Validation of create paper popup
                cblaAddPage.clickCreatePaperButton();
                Assert.assertTrue(cblaAddPage.verifyCreatePaperPopup(), "Create paper popup is not visible");
                logger.logPass(test, "Creating new paper", "Creating new paper for Sub Type: " + paperSubType);

                // Create Paper Mandatory Fields but leave name empty
                String paperName = cblaAddPage.paperName();
                cblaAddPage.fillCreatePaperMandatoryFields(driver, "", paperSubType);
                logger.logPass(test, "Add paper Mandatory Fields", "Added Paper date and paper type");

                // Upload pdf and add mandatory fields
                cblaAddPage.addPdf(driver);
                cblaAddPage.uploadDoc(driver, "pdf");
                if (paperSubType.equals("Writing") || paperSubType.equals("Speaking")) {
                    profession = "Dentistry";
                    cblaAddPage.setPdfMandatoryParameters(driver, profession, DataUtil.getTestDataFromPropFile("cbla", "version"),
                            DataUtil.getTestDataFromPropFile("cbla", "order"),
                            paperTypePdfTypeMap.get(paperSubType));
                } else {
                    cblaAddPage.setPdfMandatoryParameters(driver, profession, DataUtil.getTestDataFromPropFile("cbla", "version"),
                            DataUtil.getTestDataFromPropFile("cbla", "order"),
                            paperTypePdfTypeMap.get(paperSubType));
                }
                logger.logPass(test, "Add PDF and mandatory Fields", "User adds PDF and all mandatory fields- " + "Profession: " + profession + "Version: Zone 1, Order: 1, PDF Type: " + paperTypePdfTypeMap.get(paperSubType));

                // Add Auto Marking Keys
                if (paperSubType.equals("Listening") || paperSubType.equals("Reading")) {
                    cblaAddPage.addAutoMarkingKeys(driver, "1", "A");
                    logger.logPass(test, "Add Auto Marking Keys", "Auto Marking Keys added Question: 1, Option: A");
                }

                //Add Marking Keys
                cblaAddPage.addNewMarkingKeyButton(driver);

                // Click save Button
                cblaAddPage.clickCreatePaperSaveButton();

                // Verify Error Notification
                String actualErrorNotification = cblaAddPage.fetchPopUpErrorText(driver);
                String expectedErrorNotification = "Your data was not saved, please correct any errors and save again.";
                Assert.assertEquals(actualErrorNotification, expectedErrorNotification);
                logger.logPass(test, "Verify Error Pop Up", "Verified Notification Pop Up error name: " + actualErrorNotification.toString());

                // Verify error indicators
                List<String> actualErrorList = cblaAddPage.fetchMandatoryFieldToolTip(driver);
                String expectedErrorList = "[Name is mandatory, Version is mandatory]";
                Assert.assertEquals(actualErrorList.toString(), expectedErrorList);
                logger.logPass(test, "Verify Error Indicators", "Verified mandatory Fields errors list: " + actualErrorList.toString());

                // Adding marking Keys data
                String[] data = {"Item1", "10", "1", "10"};
                cblaAddPage.addMarkingKeysWithoutCsv(driver, data);
                logger.logPass(test, "Add Marking keys", "User adds new marking key items: Key Name, Score, Group, Group Score");
                if (paperSubType.equals("Writing") || paperSubType.equals("Speaking")) {
                    profession = "Dentistry";
                    cblaAddPage.addMarkingKeysProfVersionMaxScore(driver, profession, DataUtil.getTestDataFromPropFile("cbla", "version"), DataUtil.getTestDataFromPropFile("cbla", "score"));
                } else {
                    cblaAddPage.addMarkingKeysProfVersionMaxScore(driver, profession, DataUtil.getTestDataFromPropFile("cbla", "version"), DataUtil.getTestDataFromPropFile("cbla", "score"));
                }
                logger.logPass(test, "Marking keys Profession, Version and Max Score", "User adds new marking key Profession: " + profession + ", Version: 'Zone 1', Max Score: '10'");

                // Adding missing mandatory fields
                cblaAddPage.fillCreatePaperMandatoryFields(driver, paperName, paperSubType);
                logger.logPass(test, "Add paper Mandatory Fields", "Added Paper name: " + paperName + ", date and paper type");

                // Click save Button
                cblaAddPage.clickCreatePaperSaveButton();

                // Changes saved successfully notification verification
                String expectedNotificationText = DataUtil.getTestDataFromPropFile("cbla", "changesSavedSuccessfullyNotification");
                String actualNotificationText = cblaAddPage.fetchSaveNotification();
                Assert.assertEquals(actualNotificationText, expectedNotificationText);
                logger.logPass(test, "Verify Exam Changes Saved Successfully", "Exam Saved with notification: " + actualNotificationText);
                paperNames.add(paperName);
            }
            String expectedPaperName = paperNames.toString();
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
            Assert.assertEquals(actualNotificationText, expectedNotificationText);
            logger.logPass(test, "Verify Exam Changes Saved Successfully", "Exam Saved with notification: " + actualNotificationText);
        } catch (Throwable t) {
            reportError(driver, test, t, "Exam with all Sub Paper Types and Mandatory Fields verification failed");
        }
    }

    /*Test ID: C1689 7 */
    /*Automation Test Case ID: 22 */
    @Test(description = "Automation ID 22: Verify that user is able to save the Exam.")
    public void verifySaveExam() {
        try {
            testName = "Automation ID 22: Verify that user is able to save the exam.";
            test = logger.startTest(report, testName);
            loginCreateExamCreatePaper(test, driver);

            // Create Paper Mandatory Fields
            String paperName = cblaAddPage.paperName();
            cblaAddPage.fillCreatePaperMandatoryFields(driver, paperName, "Listening");
            logger.logPass(test, "Add paper Mandatory Fields", "Added Paper name, date and paper type");

            // Upload pdf and add mandatory fields
            cblaAddPage.addPdf(driver);
            cblaAddPage.uploadDoc(driver, "pdf");
            cblaAddPage.setPdfMandatoryParameters(driver, "All", DataUtil.getTestDataFromPropFile("cbla", "version"), DataUtil.getTestDataFromPropFile("cbla", "order"), DataUtil.getTestDataFromPropFile("cbla", "pdfType"));
            logger.logPass(test, "Add PDF and mandatory Fields", "User adds PDF and all mandatory fields- Version: Zone 1, Order: 1, PDF Type: Listening Answer Booklet");

            // Add Auto Marking Keys
            cblaAddPage.addAutoMarkingKeys(driver, "1", "A");
            logger.logPass(test, "Add Auto Marking Keys", "Auto Marking Keys added Question: 1, Option: A");

            //Add Marking Keys Data
            cblaAddPage.addNewMarkingKeyButton(driver);
            String[] data = {"Item1", "10", "1", "10"};
            cblaAddPage.addMarkingKeysWithoutCsv(driver, data);
            logger.logPass(test, "Add Marking keys", "User adds new marking key items: Key Name, Score, Group, Group Score");
            cblaAddPage.addMarkingKeysProfVersionMaxScore(driver, "All", DataUtil.getTestDataFromPropFile("cbla", "version"), DataUtil.getTestDataFromPropFile("cbla", "score"));
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
            Assert.assertEquals(actualNotificationText, expectedNotificationText);
            logger.logPass(test, "Verify Exam Changes Saved Successfully", "Exam Saved with notification: " + actualNotificationText);
        } catch (Throwable t) {
            reportError(driver, test, t, "Save Exam is not verified successfully");
        }
    }

    /*Test ID: C1800 7 */
    /*Automation Test Case ID: 25 */
    @Test(description = "Automation ID 25: Verify that 'Edit OMR Answers' link/button should be available for Listening 'Answer Booklet' and Reading 'Parts B&C' only")
    public void verifyOmrLinks() {
        try {
            testName = "Automation ID 25: Verify that 'Edit OMR Answers' link/button should be available for Listening 'Answer Booklet' and Reading 'Parts B&C' only";
            test = logger.startTest(report, testName);
            this.url = DataUtil.getTestDataFromPropFile("cbla", "CBLA_IB_URL");
            utilities.deleteAllCookiesAndNavigateToUrl(url);
            WebDriverUtil.waitForPageLoad(driver);

            //CBLA IB Login
            cblaHomePage.login(driver, DataUtil.getCredentialsFromPropFile("credentials", "username"),
                    DataUtil.getCredentialsFromPropFile("credentials", "password"));
            logger.logPass(test, "Verify Login", "User is successfully logged in to CBLA IB: " + url);

            //Navigating to add exam popup
            cblaAddPage.navigateToAddMenu(driver, "exam");

            // Data to be used
            List<String> paperSubType = Arrays.asList("Listening", "Reading");
            HashMap<String, String> paperTypePdfTypeMap = new HashMap<>();
            paperTypePdfTypeMap.put(paperSubType.get(0), "Listening Answer Booklet");
            paperTypePdfTypeMap.put(paperSubType.get(1), "Parts B & C");

            //Validation of add exam popup
            Assert.assertTrue(cblaAddPage.verifyPopupIsDisplayed(driver, "Create exam"), "Create exam popup is not displayed");

            //Validation of create paper popup
            cblaAddPage.clickCreatePaperButton();
            Assert.assertTrue(cblaAddPage.verifyCreatePaperPopup(), "Create paper popup is not visible");
            logger.logPass(test, "Creating new paper", "Creating new paper for Sub Type: " + paperSubType);

            // Upload pdf and add mandatory fields
            cblaAddPage.addPdf(driver);
            cblaAddPage.uploadDoc(driver, "pdf");
            for (String paper : paperSubType) {
                cblaAddPage.setPdfMandatoryParameters(driver, "All", DataUtil.getTestDataFromPropFile("cbla", "version"), DataUtil.getTestDataFromPropFile("cbla", "order"), paperTypePdfTypeMap.get(paper));
                //Verify edit omr link
                Assert.assertTrue(cblaAddPage.verifyOmrLinkIsVisible(utilities));
                logger.logPass(test, "OMR link visibility", "Omr link verified successfully for following: " + paper + " " + paperTypePdfTypeMap.get(paper));
            }
        } catch (Throwable t) {
            reportError(driver, test, t, "Exam with all Sub Paper Types and Mandatory Fields verified successfully");
        }
    }

    /*Test ID: C1800 8 */
    /*Automation Test Case ID: 26 */
    @Test(description = "Automation ID 26: Verify that System is considering 'Text Booklet Part A' as a valid PDF Type for Reading subtest type.")
    public void verifyTextBookletPdf() {
        try {
            testName = "Automation ID 26: Verify that System is considering 'Text Booklet Part A' as a valid PDF Type for Reading subtest type.";
            test = logger.startTest(report, testName);
            loginCreateExamCreatePaper(test, driver);

            // Create Paper Mandatory Fields
            cblaAddPage.fillCreatePaperMandatoryFields(driver, cblaAddPage.paperName(), "Reading");
            logger.logPass(test, "Add paper Mandatory Fields except paper type", "Added only Paper name and date");

            // Upload pdf and add mandatory fields
            cblaAddPage.addPdf(driver);
            cblaAddPage.uploadDoc(driver, "pdf");
            cblaAddPage.setPdfMandatoryParameters(driver, "All", DataUtil.getTestDataFromPropFile("cbla", "version"), DataUtil.getTestDataFromPropFile("cbla", "order"), "Text Booklet Part A");
            // Click save Button
            cblaAddPage.clickCreatePaperSaveButton();
            String successMessage = cblaAddPage.verifyPdfIsAcceptedAsAValidInput(driver);
            Assert.assertEquals(successMessage, DataUtil.getTestDataFromPropFile("cbla", "changesSavedSuccessfullyNotification"));
            logger.logPass(test, "Verify Text Booklet Part A' as a valid PDF Type for Reading subtest type", "Verified Text Booklet Part A' as a valid PDF Type for Reading subtest type and success message is: " + successMessage);
        } catch (Throwable t) {
            reportError(driver, test, t, "'Text Booklet Part A' as a valid PDF Type for Reading subtest type is not verified successfully");
        }
    }

    /*Test ID: C1801 3 */
    /*Automation Test Case ID: 29 */
    @Test(description = "Automation ID 29: Verify that exam manager is able to copy and paste markings keys in Item bank")
    public void verifyMarkingKeysCanBeCopied() {
        try {
            testName = "Automation ID 29: Verify that exam manager is able to copy and paste markings keys in Item bank";
            test = logger.startTest(report, testName);
            loginCreateExamCreatePaper(test, driver);

            // Changes saved successfully notification verification
            cblaAddPage.addNewMarkingKeyButton(driver);
            String expectedMarkingKey = "Item";
            String actualMarkingKey = cblaAddPage.copyAndFetchNewMarkingKey(driver, expectedMarkingKey);
            Assert.assertEquals(actualMarkingKey, expectedMarkingKey);
            logger.logPass(test, "Verify New Marking Key can be copied", "New copied marking key: " + actualMarkingKey);
        } catch (Throwable t) {
            reportError(driver, test, t, "Marking Key was not copied successfully");
        }
    }

    /*Test ID: C1660 3 */
    /*Automation Test Case ID: 43 */
    @Test(description = "Automation ID 43: Verify that Exam Manager is able to view all the Marking keys in the Paper.")
    public void verifyAllMarkingKeysVisible() {
        try {
            testName = "Automation ID 43: Verify that Exam Manager is able to view all the Marking keys in the Paper.";
            test = logger.startTest(report, testName);
            loginCreateExamCreatePaper(test, driver);

            //Validation of create paper popup
            cblaAddPage.clickCreatePaperButton();
            Assert.assertTrue(cblaAddPage.verifyCreatePaperPopup(), "Create paper popup is not visible");
            logger.logPass(test, "Creating new paper", "Creating new paper for Sub Type: Reading");

            // Upload pdf and add mandatory fields
            cblaAddPage.addPdf(driver);
            cblaAddPage.uploadDoc(driver, "pdf");
            cblaAddPage.setPdfMandatoryParameters(driver, "All", DataUtil.getTestDataFromPropFile("cbla", "version"), DataUtil.getTestDataFromPropFile("cbla", "order"), "Parts B & C");
            //Verify edit omr link
            Assert.assertTrue(cblaAddPage.verifyOmrLinkIsVisible(utilities));
            logger.logPass(test, "OMR link visibility", "Omr link verified successfully for 'Part B & C'");

            //click edit omr link
            cblaAddPage.clickOmrLink(driver);
            logger.logPass(test, "Click omr option", "Omr option clicked successfully");
            for (int i = 0; i < 10; i++) {
                cblaAddPage.addMultipleOMRQuestions(driver, String.valueOf(i + 1), "A");
            }

            int actualNumberOfOMRQuestions = cblaAddPage.getNumberOfOMRQuestions(driver);
            Assert.assertEquals(actualNumberOfOMRQuestions, 10, "Number of OMR Questions mismatch");
            logger.logPass(test, "Verify number of OMR Questions", "Verified number of Questions added are: " + actualNumberOfOMRQuestions);

        } catch (Throwable t) {
            reportError(driver, test, t, "All marking keys visibility failed");
        }
    }

    @Test(description = "Automation ID 53: Verify that there should be 4 options (A,B,C,D) for Auto marking Keys setup")
    public void verifyOmrOptions() {
        try {
            testName = "Automation ID 53: Verify that there should be 4 options (A,B,C,D) for Auto marking Keys setup";
            test = logger.startTest(report, testName);
            this.url = DataUtil.getTestDataFromPropFile("cbla", "CBLA_IB_URL");
            utilities.deleteAllCookiesAndNavigateToUrl(url);
            WebDriverUtil.waitForPageLoad(driver);

            //CBLA IB Login
            cblaHomePage.login(driver, DataUtil.getCredentialsFromPropFile("credentials", "username"),
                    DataUtil.getCredentialsFromPropFile("credentials", "password"));
            logger.logPass(test, "Verify Login", "User is successfully logged in to CBLA IB: " + url);

            //Navigating to add exam popup
            cblaAddPage.navigateToAddMenu(driver, "exam");

            // Data to be used
            List<String> paperSubType = Arrays.asList("Listening", "Reading");
            String paperTypePdfTypeMap = "Parts B & C";

            //Validation of add exam popup
            Assert.assertTrue(cblaAddPage.verifyPopupIsDisplayed(driver, "Create exam"), "Create exam popup is not displayed");

            //Validation of create paper popup
            cblaAddPage.clickCreatePaperButton();
            Assert.assertTrue(cblaAddPage.verifyCreatePaperPopup(), "Create paper popup is not visible");
            logger.logPass(test, "Creating new paper", "Creating new paper for Sub Type: " + paperSubType);

            // Upload pdf and add mandatory fields
            cblaAddPage.addPdf(driver);
            logger.logPass(test, "Upload pdf option", "Pdf uploaded successfully");
            cblaAddPage.uploadDoc(driver, "pdf");
            cblaAddPage.setPdfMandatoryParameters(driver, "All", DataUtil.getTestDataFromPropFile("cbla", "version"), DataUtil.getTestDataFromPropFile("cbla", "order"), paperTypePdfTypeMap);
            //click edit omr link
            cblaAddPage.clickOmrLink(driver);
            logger.logPass(test, "Click omr option", "Omr option clicked successfully");
            cblaAddPage.verifyOmrOptions(driver);
            logger.logPass(test, "4 options (A,B,C,D) for Auto marking Keys setup", "4 options (A,B,C,D) for Auto marking Keys setup are visible");

        } catch (Throwable t) {
            reportError(driver, test, t, "Exam with all Sub Paper Types and Mandatory Fields verified successfully");
        }
    }

}
