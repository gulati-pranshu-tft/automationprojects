package com.cbla.testcases.smokes;

import com.cbla.core.TestCore;
import com.cbla.pages.CBLAHomePage;
import com.cbla.utils.DataUtil;
import com.cbla.utils.WebDriverUtil;
import com.relevantcodes.extentreports.ExtentTest;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

public class PDFTypeVerification extends TestCore {

    private String url = "";
    private CBLAHomePage cblaHomePage;
    private Properties prop;
    private ExtentTest test;
    private String testName;

    @BeforeTest
    public void startTest() {
        this.testName = "PDF Type Verification";
        test = logger.startTest(report, this.testName);
    }


    @BeforeMethod
    public void setupClass() {
        prop = new Properties();
        cblaHomePage = PageFactory.initElements(driver, CBLAHomePage.class);
    }

    /*Test ID: C1688 5 */
    /*Test ID: C1688 12 */
    @Test(description = "Verify that PDF Type dropdown is mandatory and displays all options")
    public void verifyPDFTypeDropdown() throws IOException {
        try {
            this.url = DataUtil.getTestDataFromPropFile("cbla", "CBLA_IB_URL");
            driver.get(url);
            WebDriverUtil.waitForPageLoad(driver);
            logger.logPass(test, "Verify CBLA IB URL", "Verified user is successfully navigated to CBLA IB URL: " + url);

            //CBLA IB Login
            cblaHomePage.login(driver, DataUtil.getCredentialsFromPropFile("credentials", "username"),
                    DataUtil.getCredentialsFromPropFile("credentials", "password"));
            logger.logPass(test, "Verify Login", "Verified user is successfully logged in to CBLA IB: " + url);

            //Open Create Exam Popup
            Assert.assertTrue(cblaHomePage.verifyExamPopupIsOpen(driver).equals("Create exam"), "Exam Popup could not open!");
            logger.logPass(test, "Verify Exam Popup", "Verified user can successfully open exam popup!");

            //Open create paper and verify PDF type is mandatory
            Assert.assertTrue(cblaHomePage.verifyPDFTypeIsMandatory(driver).equals("PDF Type is mandatory"), "Indicator Text mismatch!");
            logger.logPass(test, "Verify Validation Error", "Verified user gets validation error.");

            //Verify PDF type dropdown list
            List<String> dropdownList = cblaHomePage.fetchPDFTypeDropdownValues(driver);
            Assert.assertEquals(dropdownList.toString(), DataUtil.getTestDataFromPropFile("cbla", "expectedDropdownList"));
            logger.logPass(test, "Verify PDF Type Options", "Verified user gets the expected dropdown options: " + dropdownList.toString());
            logger.endTest(report, test, testName);

        } catch (Throwable t) {
            reportError(driver, test, t, "CBLA application not working as expected");
        }

    }

}
