package com.cbla.pages;

import com.cbla.config.properties.TestConfig;
import com.cbla.core.TestCore;
import com.cbla.utils.Sleep;
import com.cbla.utils.Utilities;
import com.cbla.utils.WebDriverUtil;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;

public class ExamSearchPage extends TestCore {
    private Utilities utilities;
    @FindBy(css = ".v-menubar-menuitem.search")
    private WebElement searchMenuButton;
    @FindBy(css = ".v-menubar-popup .v-menubar-menuitem-caption")
    private List<WebElement> searchMenu;
    @FindBy(xpath = "//div[contains(text(), 'Exam')][@class='v-captiontext']")
    private WebElement searchPageTitle;
    @FindBy(xpath = "(//span[text()='Search text']//ancestor::tr//td//input)[last()]")
    private WebElement searchText;
    @FindBy(xpath = "(//span[text()='Exam ID']//ancestor::tr//td//input)[last()]")
    private WebElement searchExamId;
    @FindBy(xpath = "(//span[text()='Author']//ancestor::tr//td//input)[last()]")
    private WebElement searchExamAuthor;
    @FindBy(xpath = "(//span[text()='Name']//ancestor::tr//td//input)[last()]")
    private WebElement searchExamName;
    @FindBy(xpath = "(//span[text()='Modified by']//ancestor::tr//td//input)[last()]")
    private WebElement searchExamModifyName;
    @FindBy(xpath = "(//span[text()='Date created from']//ancestor::tr//td//input)[2]")
    private WebElement searchExamDateCreatedFrom;
    @FindBy(xpath = "(//span[text()='Date created from']//ancestor::tr//td//input)[last()]")
    private WebElement searchExamDateCreatedTo;
    @FindBy(xpath = "(//span[text()='Exam date from']//ancestor::tr//td//input)[2]")
    private WebElement searchExamDateFrom;
    @FindBy(xpath = "(//span[text()='Exam date from']//ancestor::tr//td//input)[last()]")
    private WebElement searchExamDateTo;
    @FindBy(xpath = "(//span[text()='Date modified from']//ancestor::tr//td//input)[2]")
    private WebElement searchExamDateModifiedFrom;
    @FindBy(xpath = "(//span[text()='Date modified from']//ancestor::tr//td//input)[last()]")
    private WebElement searchExamDateModifiedTo;
    @FindBy(xpath = "(//span[@class='v-checkbox v-select-option'])[3]//input")
    private WebElement searchExamStatusDraft;
    @FindBy(xpath = "(//span[@class='v-checkbox v-select-option'])[4]//input")
    private WebElement searchExamStatusPublished;
    @FindBy(xpath = "//span[text()='Search']//parent::span//parent::div[@class='v-button v-widget']")
    private WebElement searchButton;
    @FindBy(xpath = "(//div[@class='v-scrollable v-table-body-wrapper v-table-body']//td)[2]//div")
    private WebElement searchTableExamId;
    @FindBy(xpath = "(//div[@class='v-scrollable v-table-body-wrapper v-table-body']//td)[3]//div")
    private WebElement searchTableExamName;
    @FindBy(xpath = "//div[text()='Exam Test']")
    private WebElement examTestName;
    @FindBy(xpath = "(//div[@class='v-scrollable v-table-body-wrapper v-table-body']//td//span[text()='View'])[last()]")
    private WebElement searchTableViewHyperLink;
    @FindBy(xpath = "(//div[@class='v-scrollable v-table-body-wrapper v-table-body']//td)[4]//div")
    private WebElement searchExamStatus;
    @FindBy(xpath = "(//span[@class='head'])[last()]")
    private WebElement viewExamPopUpTitle;
    @FindBy(xpath = "//span[text()='Preview paper']")
    private WebElement viewPreviewPaperTitle;
    @FindBy(xpath = "//span[text()='Edit']")
    private WebElement viewPopUpEditButton;
    @FindBy(xpath = "//span[text()='Save']//parent::span//following-sibling::span")
    private WebElement viewExamPopUpSaveDropdownButton;
    @FindBy(xpath = "//div[contains(text(), 'Enter')]")
    private WebElement enterNewExamNamePopUp;
    @FindBy(xpath = "//input[@class='v-textfield v-widget v-has-width v-textfield-focus']")
    private WebElement newExamNamePopUpInput;
    @FindBy(xpath = "//div[contains(@class, 'ok v-button-ok')]")
    private WebElement newExamNamePopUpOkButton;
    @FindBy(xpath = "(//div[@class='v-window-closebox'])[last()]")
    private WebElement furthermostPopUpCloseButton;
    @FindBy(xpath = "//h1[@class='v-Notification-caption']")
    private WebElement foundResultToast;
    @FindBy(xpath = "//input[@type='text']")
    private List<WebElement> allInputBoxes;
    @FindBy(xpath = "//a[@class='g-clickable-download']")
    private WebElement previewPaperPDFName;
    @FindBy(xpath = "//input[@class='v-filterselect-input']")
    private List<WebElement> previewPaperAllData;
    @FindBy(xpath = "//span[text()='Edit paper']")
    private WebElement editPaper;
    @FindBy(xpath = "(//span[text()='Save'])[last()]//parent::span//parent::div")
    private WebElement savePaper;
    @FindBy(xpath = "(//div[@class='v-scrollable v-table-body-wrapper v-table-body']//table)[4]//tr[last()]//td[position() = (last() - 1)]")
    private WebElement secondMarkingKeyName;
    @FindBy(xpath = "(//span[following-sibling::*[descendant::text()='Sort']])[1]//span")
    private WebElement addNewKey;
    @FindBy(xpath = "(//span[text() ='Edit']//parent::span//parent::div)[last()]")
    private WebElement markingKeysEditHyperLink;
    @FindBy(css = ".v-slot.v-slot-button-10.v-slot-publish")
    private WebElement publishButton;
    @FindBy(xpath = "//div[text()='Exam Publish']")
    private WebElement publishPopupTitle;
    @FindBy(css = ".v-button.v-widget.button-0.v-button-button-0.yes.v-button-yes")
    private WebElement publishPopUpYesButton;
    @FindBy(css = ".v-button.v-widget.button-0.v-button-button-0.ok.v-button-ok")
    private WebElement publishPopUpSuccessOkButton;
    @FindBy(css = "div[class='v-slot'] div[class='v-label v-widget v-has-width']")
    private WebElement publishPopUpSuccessMessage;
    @FindBy(css = ".v-button.v-widget.button-11.v-button-button-11.un-publish.v-button-un-publish")
    private WebElement unpublishButton;
    @FindBy(css = ".lock")
    private WebElement examState;


    public ExamSearchPage(WebDriver driver) {
        super();
        this.utilities = new Utilities(driver);
    }

    /**
     * Function to navigate to search menu
     * @param driver - Webdriver instance
     */
    public void navigateToSearchMenu(WebDriver driver, String name) {
        WebDriverUtil.waitUntilVisible(driver, TestConfig.NORMAL_WAIT, searchMenuButton);
        WebDriverUtil.hoverOver(driver, searchMenuButton);
        for (WebElement element : searchMenu) {
            if (element.getText().equalsIgnoreCase(name))
                WebDriverUtil.clickElement(driver, element);
        }
    }

    /**
     * Function to fetch search page title
     * @param driver - Webdriver instance
     * @return - search page title text
     */
    public String fetchSearchPageTitle(WebDriver driver) {
        WebDriverUtil.waitUntilVisible(driver, TestConfig.NORMAL_WAIT, searchPageTitle);
        return searchPageTitle.getText().trim();
    }

    /**
     * Function to enter exam id
     * @param examId - value of examid
     */
    public void enterExamId(String examId) {
        searchExamId.sendKeys(examId);
    }

    /**
     * Function to enter exam name
     * @param examName - value of exam name
     */
    public void enterExamName(String examName) {
        searchText.sendKeys(examName);
    }

    /**
     * Function to click on search button
     * @param driver - Webdriver instance
     */
    public void clickSearchButton(WebDriver driver) {
        WebDriverUtil.clickElement(driver, searchButton);
    }

    /**
     * Function to wait for exam test
     * @param driver - Webdriver instance
     */
    public void waitForExamTest(WebDriver driver) {
        WebDriverUtil.waitUntilVisible(driver, TestConfig.NORMAL_WAIT, examTestName);
    }

    /**
     * Function to wait for exam found toast
     * @param driver - Webdriver instance
     */
    public void waitForFoundToast(WebDriver driver) {
        WebDriverUtil.waitUntilVisible(driver, TestConfig.NORMAL_WAIT, foundResultToast);
        WebDriverUtil.hoverOver(driver, foundResultToast);
    }

    /**
     * Function to fetch exam id
     * @param driver - Webdriver instance
     * @return - exam id value
     */
    public String fetchExamId(WebDriver driver) {
        WebDriverUtil.waitUntilVisible(driver, TestConfig.NORMAL_WAIT, searchTableExamId);
        return searchTableExamId.getText().trim();
    }

    /**
     * Function to fetch exam name
     * @param driver - Webdriver instance
     * @return - exam name value
     */
    public String fetchExamName(WebDriver driver) {
        WebDriverUtil.waitUntilVisible(driver, TestConfig.NORMAL_WAIT, searchTableExamName);
        return searchTableExamName.getText().trim();
    }

    /**
     * Fucntion to click on verify and view exam
     * @param driver - Webdriver instance
     * @return - true/false based upon visibility
     */
    public Boolean clickAndVerifyViewExam(WebDriver driver) {
        WebDriverUtil.waitUntilVisible(driver, TestConfig.NORMAL_WAIT, searchTableViewHyperLink);
        WebDriverUtil.hoverOver(driver, searchTableViewHyperLink);
        utilities.clickUsingJavaScript(searchTableViewHyperLink);
        Boolean result = false;
        if (viewExamPopUpTitle.getText().trim().equals("View exam")) {
            result = true;
        }
        return result;
    }

    /**
     * Function to click on view exam popup edit button
     * @param driver - Webdriver instance
     */
    public void clickViewExamPopUpEditButton(WebDriver driver) {
        try {
            utilities.clickUsingJavaScript(viewPopUpEditButton);
        } catch (Exception ignored) {
        }
    }

    /**
     * Fucntion to click on save type button
     * @param driver - Webdriver instance
     * @param saveType - save type button value
     */
    public void chooseSaveType(WebDriver driver, String saveType) {
        WebDriverUtil.waitUntilVisible(driver, TestConfig.NORMAL_WAIT, viewExamPopUpSaveDropdownButton);
        utilities.clickUsingJavaScript(viewExamPopUpSaveDropdownButton);
        for (WebElement element : searchMenu) {
            if (element.getText().equalsIgnoreCase(saveType))
                WebDriverUtil.clickElement(driver, element);
        }
    }

    /**
     * Function to fetch new exam name popup
     * @param driver - Webdriver instance
     * @return - fetched exam name value
     */
    /**
     * Function to fetch new exam name popup value
     * @param driver - Webdriver instance
     * @return - fetched exam name value
     */
    public String fetchNewExamNamePopUp(WebDriver driver) {
        return enterNewExamNamePopUp.getText().trim();
    }

    /**
     * Function to save new exam name
     * @param driver - Webdriver instance
     * @param newExamName - new exam name value
     */
    public void saveNewExamName(WebDriver driver, String newExamName) {
        WebDriverUtil.hoverOver(driver, newExamNamePopUpOkButton);
        WebDriverUtil.waitUntilClickable(driver, TestConfig.NORMAL_WAIT, newExamNamePopUpOkButton);
        WebDriverUtil.waitUntilVisible(driver, TestConfig.NORMAL_WAIT, newExamNamePopUpInput);
        newExamNamePopUpInput.sendKeys(newExamName);
        WebDriverUtil.clickElement(driver, newExamNamePopUpOkButton);
    }

    /**
     * Function to close popup
     * @param driver - Webdriver instance
     */
    public void closeFurthermostPopUp(WebDriver driver) {
        WebDriverUtil.waitUntilClickable(driver, TestConfig.NORMAL_WAIT, furthermostPopUpCloseButton);
        WebDriverUtil.hoverOver(driver, furthermostPopUpCloseButton);
        utilities.clickUsingJavaScript(furthermostPopUpCloseButton);
    }

    /**
     * Function to refresh page
     * @param driver - Webdriver instance
     */
    public void refreshPage(WebDriver driver) {
        WebDriverUtil.refreshPage(driver);
    }

    /**
     * Function to clear search fields
     * @param driver - Webdriver instance
     */
    public void clearAllSearchInputs(WebDriver driver) {
        WebDriverUtil.waitUntilVisible(driver, TestConfig.NORMAL_WAIT, allInputBoxes.get(0));
        for (WebElement input : allInputBoxes) {
            input.clear();
        }
    }

    /**
     * Function to verify view paper
     * @param driver - Webdriver instance
     * @return - true/false based upon visibility
     */
    public Boolean clickAndVerifyViewPaper(WebDriver driver) {
        WebDriverUtil.waitUntilVisible(driver, TestConfig.NORMAL_WAIT, searchTableViewHyperLink);
        WebDriverUtil.hoverOver(driver, searchTableViewHyperLink);
        utilities.clickUsingJavaScript(searchTableViewHyperLink);
        WebDriverUtil.waitUntilVisible(driver, TestConfig.NORMAL_WAIT, viewPreviewPaperTitle);
        Boolean result = false;
        if (viewExamPopUpTitle.getText().trim().equals("Preview paper")) {
            result = true;
        }
        return result;
    }

    /**
     * Function to fetch paper type preview pdf marking keys
     * @param driver - Webdriver instance
     * @return - List of all paper type available
     */
    public List<String> fetchPaperTypePreviewPaperPDfMarkingKeys(WebDriver driver) {
        List<String> pdfData = new ArrayList<>();
        WebDriverUtil.waitUntilVisible(driver, TestConfig.NORMAL_WAIT, previewPaperPDFName);
        pdfData.add(previewPaperPDFName.getText().trim());
        WebDriverUtil.waitUntilVisible(driver, TestConfig.NORMAL_WAIT, editPaper);
        utilities.clickUsingJavaScript(editPaper);
        WebDriverUtil.waitUntilClickable(driver, TestConfig.NORMAL_WAIT, savePaper);
        for (WebElement e : previewPaperAllData) {
            pdfData.add(e.getAttribute("value").trim());
        }
        int index = pdfData.size() - 1;
        pdfData.remove(index);
        return pdfData;
    }

    /**
     * Function to click edit paper
     * @param driver - Webdriver instance
     */
    public void clickEditPaper(WebDriver driver) {
        WebDriverUtil.waitUntilVisible(driver, TestConfig.NORMAL_WAIT, editPaper);
        utilities.clickUsingJavaScript(editPaper);
    }

    /**
     * Function to save paper
     * @param driver - Webdriver instance
     */
    public void savePaper(WebDriver driver) {
        WebDriverUtil.waitUntilClickable(driver, TestConfig.NORMAL_WAIT, savePaper);
        WebDriverUtil.clickElement(driver, savePaper);
        WebDriverUtil.waitUntilClickable(driver, TestConfig.NORMAL_WAIT, searchTableViewHyperLink);
    }

    /**
     * Function to fetch second markign key name
     * @return - fetched marking key value
     */
    public String fetchSecondMarkingKeyName() {
        return secondMarkingKeyName.getText().trim();
    }

    /**
     * Function to get add new key button text
     * @param driver - Webdriver instance
     * @return - add new key button text
     */
    public String fetchAddNewKeyButtonText(WebDriver driver) {
        WebDriverUtil.waitUntilVisible(driver, TestConfig.NORMAL_WAIT, markingKeysEditHyperLink);
        utilities.highLighterMethod(markingKeysEditHyperLink);
        WebDriverUtil.clickElement(driver, markingKeysEditHyperLink);
        return addNewKey.getText().trim();
    }

    /**
     * Function to fill all search fields
     * @param examName - exam name value
     * @param examId - exam id value
     * @param examUserName - exam user name value
     * @param examDate - exam date value
     * @param examStatus - exam status value
     */
    public void fillAllSearchFields(String examName, String examId, String examUserName, String examDate, String examStatus) {
        searchText.sendKeys(examName);
        searchExamId.sendKeys(examId);
        searchExamAuthor.sendKeys(examUserName);
        searchExamName.sendKeys(examName);
        searchExamModifyName.sendKeys(examUserName);
        searchExamDateCreatedFrom.sendKeys(examDate);
        searchExamDateCreatedTo.sendKeys(examDate);
        searchExamDateFrom.sendKeys(examDate);
        searchExamDateTo.sendKeys(examDate);
        searchExamDateModifiedFrom.sendKeys(examDate);
        searchExamDateModifiedTo.sendKeys(examDate);
        if (examStatus.equals("Draft")) {
            searchExamStatusDraft.sendKeys(" ");
        }
        if (examStatus.equals("Published")) {
            searchExamStatusPublished.sendKeys(" ");
        }
    }

    /**
     * Fucntion to publish paper and get success message
     * @param driver - Webdriver instance
     * @return - success message
     */
    public String publishPaperAndFetchSuccessMessage(WebDriver driver) {
        WebDriverUtil.waitUntilClickable(driver, TestConfig.NORMAL_WAIT, viewPopUpEditButton); // verify edit button before exam is published
        try {
            WebDriverUtil.clickElement(driver, publishButton);
            WebDriverUtil.waitUntilVisible(driver, TestConfig.NORMAL_WAIT, publishPopupTitle);
            WebDriverUtil.waitUntilVisible(driver, TestConfig.NORMAL_WAIT, publishPopUpYesButton);
            WebDriverUtil.clickElement(driver, publishPopUpYesButton);
        } catch (Exception ignore) {
        }
        WebDriverUtil.waitUntilClickable(driver, TestConfig.NORMAL_WAIT, publishPopUpSuccessOkButton);
        Sleep.sleepthread(2); // same locator being used for success and previous pop-up text
        String successMessage = publishPopUpSuccessMessage.getText().trim();
        WebDriverUtil.clickElement(driver, publishPopUpSuccessOkButton);
        WebDriverUtil.waitUntilVisible(driver, TestConfig.NORMAL_WAIT, unpublishButton);
        return successMessage;
    }

    /**
     * Function to unpublish paper and fetch message
     * @param driver - Webdriver instance
     * @return - success message
     */
    public String unpublishPaperAndFetchSuccessMessage(WebDriver driver) {
        WebDriverUtil.verifyElementIsNotDisplayed(viewPopUpEditButton, "Edit"); // verify edit button is not visible after exam is published
        WebDriverUtil.clickElement(driver, unpublishButton);
        WebDriverUtil.waitUntilVisible(driver, TestConfig.NORMAL_WAIT, publishPopupTitle);
        WebDriverUtil.waitUntilVisible(driver, TestConfig.NORMAL_WAIT, publishPopUpYesButton);
        String successMessage = publishPopUpSuccessMessage.getText().trim();
        WebDriverUtil.clickElement(driver, publishPopUpYesButton);
        WebDriverUtil.waitUntilVisible(driver, TestConfig.NORMAL_WAIT, unpublishButton);
        return successMessage;
    }

    /**
     * Function to fetch exam status
     * @return - exam status
     */
    public String fetchExamStatus() {
        return searchExamStatus.getText().trim();
    }

    /**
     * Function to fetch exam state
     * @return - exam state
     */
    public String fetchExamState() {
        return examState.getText().trim();
    }
}
