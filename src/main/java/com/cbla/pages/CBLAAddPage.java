package com.cbla.pages;

import com.cbla.config.properties.TestConfig;
import com.cbla.core.TestCore;
import com.cbla.utils.*;
import com.relevantcodes.extentreports.ExtentTest;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CBLAAddPage extends TestCore {

    private Utilities utilities;
    private TestDataGenerator testDataGenerator;
    private String paperType;
    @FindBy(css = ".v-menubar-menuitem.add")
    private WebElement addButton;

    @FindBy(css = ".v-menubar-menuitem.admin")
    private WebElement adminButton;

    @FindBy(css = ".v-menubar-popup .popupContent .v-menubar-menuitem:nth-child(3)")
    private WebElement examMenu;

    @FindBy(css = "div:nth-child(3) > div > div > span:nth-child(3) > span")
    private WebElement resourcesMenu;
    @FindBy(css = ".v-menubar-popup .v-menubar-menuitem-caption")
    private List<WebElement> addMenu;
    @FindBy(className = "v-window-header")
    private WebElement PopupText;
    @FindBy(xpath = "//span[text()='Create paper']")
    private WebElement createPaperButton;
    @FindBy(css = ".v-filterselect-required div")
    private WebElement paperTypeDropdownButton;
    @FindBy(css = "div[class='v-tooltip'] + div + div .v-window-header")
    private WebElement createPaperPopup;
    @FindBy(css = "#VAADIN_COMBOBOX_OPTIONLIST tr:nth-child(n+2) span")
    private List<WebElement> paperTypeDropdown;
    @FindBy(xpath = "//div[text()='PDFs']")
    private WebElement PDFTableTitle;
    @FindBy(xpath = "(//div[@class='v-gridlayout-slot content-slot 1']//input[contains(@class,'v-textfield v-widget v-textfield-required v-required v-has-width')])[2]")
    private WebElement paperNameInput;
    @FindBy(xpath = "//div[contains(text(), 'Date of paper')]/parent::div/parent::div/following-sibling::div/div/input")
    private WebElement dateOfPaperInput;
    @FindBy(xpath = "(//span[contains(text(), 'Add New')])[1]")
    private WebElement addNewPdfButton;
    @FindBy(xpath = "(//span[contains(text(), 'Add New')])[2]//parent::span//parent::div")
    private WebElement addNewMarkingKeysButton;
    @FindBy(xpath = "(//div[@class='v-csslayout v-layout v-widget pt-horizontal v-csslayout-pt-horizontal v-has-width']//div[@role='button'])[last()]")
    private WebElement uploadHyperLink;
    @FindBy(xpath = "((//div[@class='v-scrollable v-table-body-wrapper v-table-body'])[7]//tr[last()]//div[@class='v-filterselect-button'])[2]")
    private WebElement versionDropdownLocator;
    @FindBy(xpath = "(//div[@class='v-scrollable v-table-body-wrapper v-table-body'])[7]//tr[last()]//input[@class='v-textfield v-widget v-has-width']")
    private WebElement orderInputLocator;
    @FindBy(xpath = "(//div[@class='v-customcomponent v-widget v-customcomponent-required v-required v-has-width'])[last()]//input")
    private WebElement maxScoreInputLocator;
    @FindBy(xpath = "(//img[@src='/VAADIN/themes/genixcommons/icons/file_audio.png']//following-sibling::span)[2]")
    private WebElement audioFile;
    @FindBy(xpath = "(//span[contains(text(), 'Rad - Test')])[2]")
    private WebElement radTest;

    @FindBy(xpath = "(//span[contains(text(), 'Upload')])")
    private WebElement uploadButton;

    @FindBy(xpath = "(//span[contains(text(), 'Select file')])")
    private WebElement selectFileButton;

    @FindBy(className = "v-button-ok")
    private WebElement okButton;

    @FindBy(className = "v-button-yes")
    private WebElement yesButton;

    @FindBy(css = ".v-window-contents .v-label ")
    private WebElement confirmationPopup;
    @FindBy(xpath = "//div[@class='v-button v-widget button-0 v-button-button-0 ok v-button-ok']")
    private WebElement nonPdfAlertOkButton;
    @FindBy(xpath = "//div[contains(text(),'Booklet must be a PDF file')]")
    private WebElement getNonPdfAlertText;
    @FindBy(xpath = "//div[@class='v-button v-widget button-0 v-button-button-0 select v-button-select']")
    private WebElement uploadDocSelectButton;
    @FindBy(xpath = "((//div[@class='v-scrollable v-table-body-wrapper v-table-body'])[7]//tr[last()]//div[@class='v-filterselect-button'])[3]")
    private WebElement PDFTypeDropdownLocator;
    @FindBy(xpath = "((//div[@class='v-scrollable v-table-body-wrapper v-table-body'])[7]//tr[last()]//div[@class='v-filterselect-button'])[1]")
    private WebElement professionDropdownLocator;
    @FindBy(xpath = "(//div[@class='v-table-cell-wrapper'])[8]")
    private WebElement keyNameMarkingKey;
    @FindBy(xpath = "//td[contains(@class,'gwt-MenuItem')]//span")
    private List<WebElement> dropdownElements;
    @FindBy(xpath = "(//span[(text()='Listening')])")
    private WebElement uploadPaperTypeFolderListening;

    @FindBy(xpath = "(//span[contains(text(), 'Listening')])[4]")
    private WebElement resourceUploadPaperTypeFolderListening;
    @FindBy(xpath = "//span[contains(text(), 'Listening_Question Paper_V1.pdf')]")
    private WebElement listeningQPaperPdf;
    @FindBy(xpath = "//div[contains(text(), 'Create paper')]//ancestor::div[@class='popupContent']//span[contains(text(),'Save')]")
    private WebElement createPaperSaveButton;
    @FindBy(xpath = "//div[contains(text(), 'Missing Marking Keys')]//ancestor::div[@class='popupContent']//div[@class='v-label v-widget v-has-width']")
    private WebElement missingMarkingKeysWarningMessage;
    @FindBy(xpath = "(//span[contains(text(), 'Import')]//parent::span//parent::div)[last()]")
    private WebElement importHyperLinkMarkingKeys;
    @FindBy(css = ".gwt-FileUpload")
    private WebElement chooseFileMarkingKeysButton;
    @FindBy(xpath = "//input[@class='gwt-FileUpload']//following-sibling::div")
    private WebElement uploadButtonMarkingKeys;
    @FindBy(xpath = "//span[contains(text(), 'Next')]//parent::span//parent::div")
    private WebElement nextButtonMarkingKeys;
    @FindBy(xpath = "//span[@class='v-checkbox g-checkbox v-widget'][last()]")
    private WebElement lastMarkingKeyCheckBox;
    @FindBy(xpath = "//div[@class='v-button v-widget finish v-button-finish']")
    private WebElement finishButtonMarkingKeys;
    @FindBy(xpath = "//div[contains(text(), 'Information')]//ancestor::div[@class='popupContent']//div[contains(text(), 'Please')]")
    private WebElement uploadDatAlertMarkingKeys;
    @FindBy(xpath = "(//div[@class='v-filterselect v-widget v-has-width']//div)[last()]")
    private WebElement pdfProfessionDropDownButton;
    @FindBy(xpath = "(//div[@class='v-filterselect v-widget v-filterselect-required v-required v-has-width v-filterselect-prompt']//div)[last()]")
    private WebElement pdfVersionDropDownButton;
    @FindBy(xpath = "//div[@class='v-errorindicator v-errorindicator-error']")
    private List<WebElement> errorIndicator;
    @FindBy(xpath = "//div[contains(@class,'v-errormessage v-errormessage-error')]//div//div")
    private WebElement mandatoryErrorText;
    @FindBy(css = "a[class='g-clickable-download']")
    private WebElement uploadedFileName;
    @FindBy(xpath = "(//div[@class='v-window-closebox'])[last()]")
    private WebElement popUpCloseButton;
    @FindBy(xpath = "(//span[text() ='Edit']//parent::span//parent::div)[last()]")
    private WebElement markingKeysEditHyperLink;
    @FindBy(xpath = "//span[text() ='Add new key']//parent::span//parent::div")
    private WebElement addNewKeyMarkingKeys;
    @FindBy(xpath = "//span[text() ='▲']//parent::span//parent::div")
    private WebElement upActionMarkingKey;
    @FindBy(xpath = "//span[text() ='▼']//parent::span//parent::div")
    private WebElement downActionMarkingKey;
    @FindBy(xpath = "//div[@class='v-csslayout v-layout v-widget pt-horizontal v-csslayout-pt-horizontal v-has-width']//span[text() ='Delete']//parent::span//parent::div")
    private WebElement deleteActionMarkingKey;
    @FindBy(css = "tbody tr[class='v-table-row'] td:nth-child(6) .v-filterselect-button")
    private WebElement pdfTypeDrondownButton;
    @FindBy(className = "v-filterselect-suggestmenu")
    private WebElement pdfTypeDropdownMenu;
    @FindBy(xpath = "//td[contains(@class,'gwt-MenuItem')][last()]")
    private WebElement dropdownLastElements;
    @FindBy(css = ".v-filterselect-nextpage")
    private WebElement nextScrollButton;
    @FindBy(xpath = "(//div[contains(@class,'g-ckeditor-inline v-widget v-has-width cke_editable cke_editable_inline cke_contents_ltr cke_show_borders')])[last()]")
    private WebElement markingKeysInputText;
    @FindBy(xpath = "(//input[@class='v-textfield v-widget v-has-width'])[2]")
    private WebElement markingKeysScore;
    @FindBy(xpath = "(//input[@class='v-textfield v-widget v-has-width'])[3]")
    private WebElement markingKeysGroup;
    @FindBy(xpath = "(//input[@class='v-textfield v-widget v-has-width'])[4]")
    private WebElement markingKeysGroupScore;
    @FindBy(xpath = "//div[@class='v-button v-widget button-0 v-button-button-0 save v-button-save']")
    private WebElement markingKeysNewKySaveButton;
    @FindBy(xpath = "//span[text()='Edit OMR Answers']//parent::span//parent::div")
    private WebElement editOmrAnswers;
    @FindBy(xpath = "//span[text()='Auto-marking Keys']//ancestor::div[@class='v-verticallayout v-layout v-vertical v-widget v-has-width']//span[text()='Add New']//ancestor::div[@role='button']")
    private WebElement autoMarkingKeysAddNew;

    @FindBy(css = ".v-formlayout-contentcell .v-table-table td:nth-child(3) .v-slot")
    private List<WebElement> omrOptions;
    @FindBy(xpath = "(//span[text()='Auto-marking Keys']//ancestor::div[@class='v-verticallayout v-layout v-vertical v-widget v-has-width']//span[text()='Delete']//ancestor::td[@class='v-table-cell-content']//following-sibling::td[1])[last()]//input")
    private WebElement autoMarkingKeysQuestionInput;
    @FindBy(xpath ="//span[text()='Auto-marking Keys']//ancestor::div[@class='v-verticallayout v-layout v-vertical v-widget v-has-width']//span[text()='Delete']//ancestor::td[@class='v-table-cell-content']//following-sibling::td[1]//input")
    private List<WebElement> autoMarkingKeysQuestionInputs;
    @FindBy(xpath = "(//label[text()='A']//preceding-sibling::input)[last()]")
    private WebElement autoMarkingKeysOptionA;
    @FindBy(xpath = "//label[text()='B']//preceding-sibling::input//parent::span//parent::div")
    private WebElement autoMarkingKeysOptionB;
    @FindBy(xpath = "//label[text()='C']//preceding-sibling::input//parent::span//parent::div")
    private WebElement autoMarkingKeysOptionC;
    @FindBy(xpath = "//label[text()='D']//preceding-sibling::input//parent::span//parent::div")
    private WebElement autoMarkingKeysOptionD;
    @FindBy(xpath = "//div[@class='v-button v-widget button-0 v-button-button-0 save v-button-save']")
    private WebElement getAutoMarkingKeysSave;
    @FindBy(xpath = "//p[@class='v-Notification-description']")
    private WebElement errorNotification;
    @FindBy(xpath = "//p[@class='v-Notification-description']")
    private WebElement changesSavedSuccessfullyNotificatioon;
    @FindBy(xpath = "//tr[@class='v-table-row']//div[2]")
    private WebElement editOmrLink;
    @FindBy(xpath = "//div[text()='Item1']")
    private WebElement item1UploadedCsv;
    @FindBy(xpath = "//img//parent::div//parent::div//following-sibling::div[last()]")
    private WebElement invalidCsvError;

    @FindBy(className = "v-table-row-odd")
    private WebElement uploadedMarkingKeyDataRow;

    public CBLAAddPage(WebDriver driver) {
        super();
        this.utilities = new Utilities(driver);
    }

    /**
     * Function to navigate to add menu
     * @param driver - Webdriver instance
     * @param name - Name of sub-menu to navigate
     */
    public void navigateToAddMenu(WebDriver driver, String name) {
        WebDriverUtil.waitUntilVisible(driver, TestConfig.NORMAL_WAIT, addButton);
        WebDriverUtil.hoverOver(driver, addButton);
        for (WebElement element : addMenu) {
            if (element.getText().equalsIgnoreCase(name))
                WebDriverUtil.clickElement(driver, element);
        }
    }

    /**
     * Function to navigate to admin menu
     * @param driver - Webdriver instance
     */
    public void navigateToAdminMenu(WebDriver driver) {
        WebDriverUtil.waitUntilVisible(driver, TestConfig.NORMAL_WAIT, adminButton);
        WebDriverUtil.hoverOver(driver, adminButton);
        WebDriverUtil.waitUntilVisible(driver, TestConfig.NORMAL_WAIT, examMenu);
        WebDriverUtil.hoverOver(driver, examMenu);
        WebDriverUtil.waitUntilVisible(driver, TestConfig.NORMAL_WAIT, resourcesMenu);
        WebDriverUtil.clickElement(driver, resourcesMenu);
    }

    /**
     * Function to verify popup is displayed
     * @param driver - Webdriver instance
     * @param popupName - Name of papup to verify
     * @return - True/False according to visibility
     */
    public Boolean verifyPopupIsDisplayed(WebDriver driver, String popupName) {
        Boolean result = false;
        if (PopupText.getText().equalsIgnoreCase(popupName))
            result = true;
        return result;
    }

    /**
     * Function to verify create paper popup visibility
     * @return - True/False based upon visibility
     */
    public boolean verifyCreatePaperPopup() {
        return utilities.isElementExist(createPaperPopup);
    }

    /**
     * Function to click on create paper button
     */
    public void clickCreatePaperButton() {
        utilities.clickUsingJavaScript(createPaperButton);
    }

    /**
     * Function to click on save create paper button
     */
    public void clickCreatePaperSaveButton() {
        utilities.clickUsingJavaScript(createPaperSaveButton);
    }

    /**
     * Function to wait for error notification to disappear
     */
    public void waitForErrorNotificationDisappear(WebDriver driver) {
        WebDriverUtil.waitUtilInvisible(driver, TestConfig.LONG_WAIT, errorNotification);
    }

    /**
     * Function to verify missing marking key warning message
     * @param driver - Webdriver instance
     * @return - True/False based upon visibility
     */
    public Boolean verifyMissingMarkingKeysWarningMessage(WebDriver driver) throws IOException {
        WebDriverUtil.waitUntilVisible(driver, TestConfig.NORMAL_WAIT, missingMarkingKeysWarningMessage);
        Boolean result = false;
        if (missingMarkingKeysWarningMessage.getText().equalsIgnoreCase(DataUtil.getTestDataFromPropFile("cbla", "missingMarkingKeyErrorMessgae")))
            result = true;
        return result;
    }

    /**
     * Function to fetch paper type dropdown options
     * @param driver - Webdriver instance
     * @return - List of all options available
     */
    public List<String> fetchPaperTypeDropdown(WebDriver driver) {
        return utilities.fetchDropDowns(driver, paperTypeDropdownButton, paperTypeDropdown, false, nextScrollButton);
    }

    /**
     * Function to fetch profession type dropdown options
     * @param driver - Webdriver instance
     * @return - List of all options available
     */
    public List<String> fetchProfessionsDropdown(WebDriver driver) {
        return utilities.fetchDropDowns(driver, pdfProfessionDropDownButton, dropdownElements, true, nextScrollButton);
    }

    /**
     * Function to fetch pdf type dropdown options
     * @param driver - Webdriver instance
     * @return - List of all options available
     */
    public List<String> fetchPdfVersionDropdown(WebDriver driver) {
        return utilities.fetchDropDowns(driver, pdfVersionDropDownButton, dropdownElements, false, nextScrollButton);
    }

    /**
     * Function to generate a unique paper name
     * @return - Generated paper name
     */
    public String paperName() {
        return "Test" + testDataGenerator.randomInt(6000, 9500);
    }

    /**
     * Function to fill mandatory fields for create paper popup
     * @param driver - Webdriver instance
     * @param paperName - Name of paper
     * @param paperType - Type of paper
     */
    public void fillCreatePaperMandatoryFields(WebDriver driver, String paperName, String paperType) {
        WebDriverUtil.clickElement(driver, paperTypeDropdownButton);
        for (WebElement element : paperTypeDropdown) {
            if (element.getText().equalsIgnoreCase(paperType)) {
                WebDriverUtil.clickElement(driver, element);
                break;
            }
        }
        utilities.highLighterMethod(paperNameInput);
        utilities.clickUsingJavaScript(paperNameInput);
        paperNameInput.sendKeys(paperName);
        String date = testDataGenerator.formatDate(testDataGenerator.getTodaysDate(), "dd MMM yyyy HH:mm");
        dateOfPaperInput.sendKeys(date);
    }

    /**
     * Function to add pdf from web
     * @param driver - Webdriver instance
     */
    public void addPdf(WebDriver driver) {
        WebDriverUtil.waitUntilVisible(driver, TestConfig.NORMAL_WAIT, PDFTableTitle);
        WebDriverUtil.scrollToElement(driver, addNewPdfButton);
        utilities.highLighterMethod(addNewPdfButton);
        utilities.clickUsingJavaScript(addNewPdfButton);
    }

    /**
     * Function to upload pdf
     * @param driver - Webdriver instance
     * @param docType - Type of doc (pdf/csv)
     */
    public void uploadDoc(WebDriver driver, String docType) {
        Sleep.sleepthread(2);
        WebDriverUtil.waitUntilVisible(driver, TestConfig.NORMAL_WAIT, uploadHyperLink);
        utilities.highLighterMethod(uploadHyperLink);
        WebDriverUtil.clickElement(driver, uploadHyperLink);
        if (!docType.equals("pdf")) {
            if (!docType.equals("csv")) {
                WebDriverUtil.waitUntilVisible(driver, TestConfig.NORMAL_WAIT, audioFile);
                utilities.highLighterMethod(audioFile);
                utilities.clickUsingJavaScript(audioFile);
            } else {
//                WebDriverUtil.waitUntilVisible(driver, TestConfig.NORMAL_WAIT, audioFile);
            }
        } else {
            WebDriverUtil.waitUntilVisible(driver, TestConfig.NORMAL_WAIT, radTest);
            utilities.highLighterMethod(radTest);
            utilities.clickUsingJavaScript(radTest);
            WebDriverUtil.waitUntilVisible(driver, TestConfig.NORMAL_WAIT, uploadPaperTypeFolderListening);
            utilities.highLighterMethod(uploadPaperTypeFolderListening);
            utilities.clickUsingJavaScript(uploadPaperTypeFolderListening);
            WebDriverUtil.waitUntilVisible(driver, TestConfig.NORMAL_WAIT, listeningQPaperPdf);
            utilities.highLighterMethod(listeningQPaperPdf);
            utilities.clickUsingJavaScript(listeningQPaperPdf);
        }
        utilities.clickUsingJavaScript(uploadDocSelectButton);
    }

    /**
     * Function to add mandatory parameters for added pdf
     * @param driver - Webdriver instance
     * @param profession - Profession name
     * @param version - Version name
     * @param order - Order value
     * @param pdfType - Type of pdf
     */
    public void setPdfMandatoryParameters(WebDriver driver, String profession, String version, String order, String pdfType) {
        utilities.highLighterMethod(pdfProfessionDropDownButton);
        WebDriverUtil.clickElement(driver, pdfProfessionDropDownButton);
        for (WebElement element : dropdownElements) {
            try {
                if (element.getText().equalsIgnoreCase(profession)) {
                    WebDriverUtil.clickElement(driver, element);
                }
            } catch (Exception ignored) {
            }
        }
        utilities.highLighterMethod(versionDropdownLocator);
        WebDriverUtil.clickElement(driver, versionDropdownLocator);
        for (WebElement element : dropdownElements) {
            try {
                if (element.getText().equalsIgnoreCase(version)) {
                    WebDriverUtil.clickElement(driver, element);
                }
            } catch (Exception ignored) {
            }
        }
        utilities.highLighterMethod(orderInputLocator);
        orderInputLocator.sendKeys(order);
        utilities.highLighterMethod(PDFTypeDropdownLocator);
        WebDriverUtil.clickElement(driver, PDFTypeDropdownLocator);
        for (WebElement element : dropdownElements) {
            try {
                if (element.getText().equalsIgnoreCase(pdfType)) {
                    WebDriverUtil.clickElement(driver, element);
                }
            } catch (Exception ignored) {
            }
        }
    }

    /**
     * Function to add auto marking keys
     * @param driver - Webdriver instance
     * @param question - Question name
     * @param omrAnswerOption - Answer option
     */
    public void addAutoMarkingKeys(WebDriver driver, String question, String omrAnswerOption) {
        WebDriverUtil.waitUntilVisible(driver, TestConfig.NORMAL_WAIT, editOmrAnswers);
        WebDriverUtil.clickElement(driver, editOmrAnswers);
        WebDriverUtil.waitUntilVisible(driver, TestConfig.NORMAL_WAIT, autoMarkingKeysAddNew);
        WebDriverUtil.clickElement(driver, autoMarkingKeysAddNew);
        WebDriverUtil.waitUntilVisible(driver, TestConfig.NORMAL_WAIT, autoMarkingKeysAddNew);
        autoMarkingKeysQuestionInput.clear();
        autoMarkingKeysQuestionInput.sendKeys(question);
        switch (omrAnswerOption) {
            case "A":
                autoMarkingKeysOptionA.sendKeys(" ");
                break;
            case "B":
                autoMarkingKeysOptionB.sendKeys(" ");
                break;
            case "C":
                autoMarkingKeysOptionC.sendKeys(" ");
                break;
            case "D":
                autoMarkingKeysOptionD.sendKeys(" ");
                break;
        }
        WebDriverUtil.clickElement(driver, getAutoMarkingKeysSave);
    }

    /**
     * Function to add multiple omr questions
     * @param driver - Webdriver instance
     * @param question - Questions
     * @param omrAnswerOption - Answer value
     */
    public void addMultipleOMRQuestions(WebDriver driver, String question, String omrAnswerOption) {
        WebDriverUtil.waitUntilVisible(driver, TestConfig.NORMAL_WAIT, autoMarkingKeysAddNew);
        WebDriverUtil.waitUntilClickable(driver, TestConfig.NORMAL_WAIT, autoMarkingKeysAddNew);
        WebDriverUtil.clickElement(driver, autoMarkingKeysAddNew);
        WebDriverUtil.waitUntilVisible(driver, TestConfig.NORMAL_WAIT, autoMarkingKeysAddNew);
        autoMarkingKeysQuestionInput.clear();
        autoMarkingKeysQuestionInput.sendKeys(question);
        switch (omrAnswerOption) {
            case "A":
                autoMarkingKeysOptionA.sendKeys(" ");
                break;
            case "B":
                autoMarkingKeysOptionB.sendKeys(" ");
                break;
            case "C":
                autoMarkingKeysOptionC.sendKeys(" ");
                break;
            case "D":
                autoMarkingKeysOptionD.sendKeys(" ");
                break;
        }
        Sleep.sleepthread(1);
    }

    /**
     * Function to get total count of omr questions
     * @param driver - Webdriver instance
     * @return - Count of total omr questions
     */
    public int getNumberOfOMRQuestions(WebDriver driver) {
        return WebDriverUtil.fetchAllElementsText(driver, autoMarkingKeysQuestionInputs).size();
    }

    /**
     * Fucntion to verify non pdf alert message
     * @param driver - Webdriver instance
     * @return - Alert Message
     */
    public Boolean verifyNonPdfDocAlert(WebDriver driver) throws IOException {
        WebDriverUtil.waitUntilVisible(driver, TestConfig.NORMAL_WAIT, nonPdfAlertOkButton);
        return getNonPdfAlertText.getText().equalsIgnoreCase(DataUtil.getTestDataFromPropFile("cbla", "nonPDFAlertMessage"));
    }

    /**
     * Function to verify pdf is accepted as a valid input
     * @param driver - Webdriver instacne
     * @return - Alert message
     */
    public String verifyPdfIsAcceptedAsAValidInput(WebDriver driver) throws IOException {
        WebDriverUtil.waitUntilVisible(driver, TestConfig.NORMAL_WAIT, errorNotification);
        return errorNotification.getText();
    }

    /**
     * Function to import marking keys
     * @param driver - Webdriver instance
     * @param fileName - Name of file
     */
    public void importMarkingKey(WebDriver driver, String fileName) {
        utilities.clickUsingJavaScript(addNewMarkingKeysButton);
        WebDriverUtil.waitUntilVisible(driver, TestConfig.NORMAL_WAIT, importHyperLinkMarkingKeys);
        utilities.highLighterMethod(importHyperLinkMarkingKeys);
        WebDriverUtil.clickElement(driver, importHyperLinkMarkingKeys);
        WebDriverUtil.waitUntilVisible(driver, TestConfig.NORMAL_WAIT, chooseFileMarkingKeysButton);
        utilities.highLighterMethod(chooseFileMarkingKeysButton);
        utilities.clickUsingJavaScript(chooseFileMarkingKeysButton);
        utilities.uploadFile(fileName);
        Sleep.sleepthread(2);
        utilities.clickUsingJavaScript(uploadButtonMarkingKeys);
        WebDriverUtil.waitUntilVisible(driver, TestConfig.NORMAL_WAIT, item1UploadedCsv);
        WebDriverUtil.clickElement(driver, nextButtonMarkingKeys);
    }

    /**
     * Function to import wrong marking keys data
     * @param driver - Webdriver instance
     * @param fileName - Name of file
     */
    public void importMarkingKeyWrongData(WebDriver driver, String fileName) {
        utilities.clickUsingJavaScript(addNewMarkingKeysButton);
        WebDriverUtil.waitUntilVisible(driver, TestConfig.NORMAL_WAIT, importHyperLinkMarkingKeys);
        utilities.highLighterMethod(importHyperLinkMarkingKeys);
        WebDriverUtil.clickElement(driver, importHyperLinkMarkingKeys);
        WebDriverUtil.waitUntilVisible(driver, TestConfig.NORMAL_WAIT, chooseFileMarkingKeysButton);
        utilities.highLighterMethod(chooseFileMarkingKeysButton);
        utilities.clickUsingJavaScript(chooseFileMarkingKeysButton);
        utilities.uploadFile(fileName);
        Sleep.sleepthread(2);
        utilities.clickUsingJavaScript(uploadButtonMarkingKeys);
        WebDriverUtil.clickElement(driver, nextButtonMarkingKeys);
    }

    /**
     * Function to fetch invalid csv marking keys error message
     * @param driver - Webdriver instance
     * @param path - FIle path
     * @return - Alert message
     */
    public String fetchInvalidCSVImportMarkingKeysErrorMessage(WebDriver driver, String path) {
        this.importMarkingKey(driver, path);
        return invalidCsvError.getText().trim();
    }

    /**
     * Function to perform additional steps for importing markign keys
     * @param driver - Webdriver instance
     * @param path - filepath
     */
    public void importMarkingKeyFinishSetUp(WebDriver driver, String path) {
        this.importMarkingKey(driver, path);
        WebDriverUtil.waitUntilVisible(driver, TestConfig.NORMAL_WAIT, lastMarkingKeyCheckBox);
        utilities.highLighterMethod(lastMarkingKeyCheckBox);
        WebDriverUtil.clickElement(driver, lastMarkingKeyCheckBox);
        WebDriverUtil.clickElement(driver, finishButtonMarkingKeys);
    }

    /**
     * Function to click on add new marking key button
     * @param driver - Webdriver instance
     */
    public void addNewMarkingKeyButton(WebDriver driver) {
        WebDriverUtil.waitUntilVisible(driver, TestConfig.NORMAL_WAIT, addNewMarkingKeysButton);
        utilities.clickUsingJavaScript(addNewMarkingKeysButton);
    }

    /**
     * Function to add marking keys without csv
     * @param driver - Webdriver instance
     * @param data - data to add
     */
    public void addMarkingKeysWithoutCsv(WebDriver driver, String data[]) {
        Sleep.sleepthread(2);
        WebDriverUtil.waitUntilVisible(driver, TestConfig.NORMAL_WAIT, markingKeysEditHyperLink);
        utilities.highLighterMethod(markingKeysEditHyperLink);
        WebDriverUtil.clickElement(driver, markingKeysEditHyperLink);
        WebDriverUtil.waitUntilVisible(driver, TestConfig.NORMAL_WAIT, addNewKeyMarkingKeys);
        utilities.highLighterMethod(addNewKeyMarkingKeys);
        WebDriverUtil.clickElement(driver, addNewKeyMarkingKeys);
        WebDriverUtil.waitUntilVisible(driver, TestConfig.NORMAL_WAIT, markingKeysInputText);
        utilities.highLighterMethod(markingKeysInputText);
        markingKeysInputText.sendKeys(data[0]);
        WebDriverUtil.waitUntilVisible(driver, TestConfig.NORMAL_WAIT, markingKeysScore);
        utilities.highLighterMethod(markingKeysScore);
        markingKeysScore.clear();
        markingKeysScore.sendKeys(data[1]);
        WebDriverUtil.waitUntilVisible(driver, TestConfig.NORMAL_WAIT, markingKeysGroup);
        utilities.highLighterMethod(markingKeysGroup);
        markingKeysGroup.clear();
        markingKeysGroup.sendKeys(data[2]);
        WebDriverUtil.clickElement(driver, markingKeysNewKySaveButton);
    }

    /**
     * Function to copy and fetch marking key
     * @param driver - Webdriver instance
     * @param item - item name
     * @return - fetched marking keys
     */
    public String copyAndFetchNewMarkingKey(WebDriver driver, String item) {
        WebDriverUtil.waitUntilVisible(driver, TestConfig.NORMAL_WAIT, markingKeysEditHyperLink);
        utilities.highLighterMethod(markingKeysEditHyperLink);
        WebDriverUtil.clickElement(driver, markingKeysEditHyperLink);
        WebDriverUtil.waitUntilVisible(driver, TestConfig.NORMAL_WAIT, addNewKeyMarkingKeys);
        utilities.highLighterMethod(addNewKeyMarkingKeys);
        WebDriverUtil.clickElement(driver, addNewKeyMarkingKeys);
        WebDriverUtil.waitUntilVisible(driver, TestConfig.NORMAL_WAIT, markingKeysInputText);
        utilities.highLighterMethod(markingKeysInputText);
        markingKeysInputText.sendKeys(item);

        // item copied
        markingKeysInputText.sendKeys(Keys.CONTROL, "a");
        markingKeysInputText.sendKeys(Keys.CONTROL, "c");

        // add new marking key row and paste
        WebDriverUtil.waitUntilVisible(driver, TestConfig.NORMAL_WAIT, addNewKeyMarkingKeys);
        utilities.highLighterMethod(addNewKeyMarkingKeys);
        WebDriverUtil.clickElement(driver, addNewKeyMarkingKeys);
        markingKeysInputText.sendKeys(Keys.CONTROL, "v");

        // fetch the pasted key
        return markingKeysInputText.getText().trim();
    }

    /**
     * Function to add required fields for marking key
     * @param driver - Webdriver instance
     * @param profession - profession name
     * @param version - version name
     * @param maxScore - max score value
     */
    public void addMarkingKeysProfVersionMaxScore(WebDriver driver, String profession, String version, String maxScore) {
        Sleep.sleepthread(2);
        utilities.highLighterMethod(pdfVersionDropDownButton);
        WebDriverUtil.clickElement(driver, pdfVersionDropDownButton);
        for (WebElement element : dropdownElements) {
            try {
                if (element.getText().equalsIgnoreCase(version)) {
                    WebDriverUtil.clickElement(driver, element);
                }
            } catch (Exception ignored) {
            }
        }
        // add max score
        maxScoreInputLocator.sendKeys(maxScore);
        maxScoreInputLocator.sendKeys(Keys.TAB); // used so that input field identifies the input through automation
        utilities.highLighterMethod(pdfProfessionDropDownButton);
        WebDriverUtil.clickElement(driver, pdfProfessionDropDownButton);
        for (WebElement element : dropdownElements) {
            try {
                if (element.getText().equalsIgnoreCase(profession)) {
                    WebDriverUtil.clickElement(driver, element);
                }
            } catch (Exception ignored) {
            }
        }
    }

    /**
     * Function to fetch key name
     * @param driver - Webdriver instance
     * @return - keyname value
     */
    public String fetchKeyNameMarkingKey(WebDriver driver) {
        WebDriverUtil.waitUntilVisible(driver, TestConfig.NORMAL_WAIT, keyNameMarkingKey);
        return keyNameMarkingKey.getText().trim();
    }

    /**
     * Function to verify marking keys actions
     * @param driver - Webdriver instance
     * @param test - Extent reported instance
     * @return - true/false based upon visibility
     */
    public Boolean verifyMarkingKeysActionOptions(WebDriver driver, ExtentTest test) {
        Boolean result = true;
        try {
            utilities.clickUsingJavaScript(addNewMarkingKeysButton);
            WebDriverUtil.waitUntilVisible(driver, TestConfig.NORMAL_WAIT, importHyperLinkMarkingKeys);
            utilities.highLighterMethod(importHyperLinkMarkingKeys);
            WebDriverUtil.clickElement(driver, importHyperLinkMarkingKeys);
            WebDriverUtil.waitUntilVisible(driver, TestConfig.NORMAL_WAIT, chooseFileMarkingKeysButton);
            WebDriverUtil.verifyElementIsDisplayed(chooseFileMarkingKeysButton, "Choose file");
            WebDriverUtil.clickElement(driver, popUpCloseButton);
            logger.logPass(test, "Verify Browse Marking Keys", "Browse available by name 'Choose File'");
            WebDriverUtil.waitUntilVisible(driver, TestConfig.NORMAL_WAIT, markingKeysEditHyperLink);
            utilities.highLighterMethod(markingKeysEditHyperLink);
            WebDriverUtil.clickElement(driver, markingKeysEditHyperLink);
            WebDriverUtil.waitUntilVisible(driver, TestConfig.NORMAL_WAIT, addNewKeyMarkingKeys);
            utilities.highLighterMethod(addNewKeyMarkingKeys);
            WebDriverUtil.clickElement(driver, addNewKeyMarkingKeys);
            WebDriverUtil.waitUntilVisible(driver, TestConfig.NORMAL_WAIT, upActionMarkingKey);
            utilities.highLighterMethod(upActionMarkingKey);
            WebDriverUtil.verifyElementIsDisplayed(upActionMarkingKey, "Up Action Key");
            logger.logPass(test, "Verify Up Action Key Marking Keys", "Up Action Key availability verified successfully");
            WebDriverUtil.waitUntilVisible(driver, TestConfig.NORMAL_WAIT, downActionMarkingKey);
            utilities.highLighterMethod(downActionMarkingKey);
            WebDriverUtil.verifyElementIsDisplayed(downActionMarkingKey, "Down Action Key");
            logger.logPass(test, "Verify Down Action Key Marking Keys", "Down Action Key availability verified successfully");
            WebDriverUtil.waitUntilVisible(driver, TestConfig.NORMAL_WAIT, deleteActionMarkingKey);
            utilities.highLighterMethod(deleteActionMarkingKey);
            WebDriverUtil.verifyElementIsDisplayed(upActionMarkingKey, "Delete Action Key");
            logger.logPass(test, "Verify Delete Action Key Marking Keys", "Delete Action Key availability verified successfully");
        } catch (Exception e) {
            result = false;
        }
        return result;
    }

    /**
     * Function to fetch upload data alert message
     * @param driver - Webdriver instance
     * @return - alert message
     */
    public String fetchUploadDataAlertText(WebDriver driver) {
        WebDriverUtil.waitUntilVisible(driver, TestConfig.NORMAL_WAIT, uploadDatAlertMarkingKeys);
        return uploadDatAlertMarkingKeys.getText().trim();
    }

    /**
     * Function to get uploaded file name
     * @param driver - Webdriver instance
     * @return - uploaded file name
     */
    public String fetchUploadedFileName(WebDriver driver) {
        WebDriverUtil.waitUntilVisible(driver, TestConfig.NORMAL_WAIT, uploadedFileName);
        utilities.highLighterMethod(uploadedFileName);
        return uploadedFileName.getText().trim();
    }

    /**
     * Function to fetch mandatory fields message
     * @param driver - Webdriver instance
     * @return - List of error messages
     */
    public List<String> fetchMandatoryFieldToolTip(WebDriver driver) {
        List<String> errorTexts = new ArrayList<>();
        for (WebElement indicator : errorIndicator) {
            WebDriverUtil.waitUntilVisible(driver, TestConfig.NORMAL_WAIT, indicator);
            WebDriverUtil.scrollToElement(driver, indicator);
            WebDriverUtil.hoverOver(driver, indicator);
            errorTexts.add(mandatoryErrorText.getText().trim());
        }
        return errorTexts;
    }

    /**
     * Function to fetch popup error message
     * @param driver - Webdriver instance
     * @return - error message
     */
    public String fetchPopUpErrorText(WebDriver driver) {
        Sleep.sleepthread(2);
        WebDriverUtil.waitUntilVisible(driver, TestConfig.NORMAL_WAIT, errorNotification);
        return errorNotification.getText().trim();
    }

    /**
     * Function to add marking keys
     * @param driver - Webdriver instance
     */
    public void addMarkingKeys(WebDriver driver) {
        WebDriverUtil.waitUntilVisible(driver, TestConfig.NORMAL_WAIT, PDFTableTitle);
        WebDriverUtil.scrollToElement(driver, addNewMarkingKeysButton);
        utilities.highLighterMethod(addNewMarkingKeysButton);
        utilities.clickUsingJavaScript(addNewMarkingKeysButton);
    }

    /**
     * Function to fetch profession marking keys dropdowm
     * @param driver - Webdriver instance
     * @return - List of all value in profession dropdown
     */
    public List<String> fetchMarkingKeysProfessionsDropdown(WebDriver driver) {
        return utilities.fetchDropDowns(driver, pdfProfessionDropDownButton, dropdownElements, true, nextScrollButton);
    }

    /**
     * Function to fetch version marking keys dropdowm
     * @param driver - Webdriver instance
     * @return - List of all value in version dropdown
     */
    public List<String> fetchMarkingKeysVersionDropdown(WebDriver driver) {
        return utilities.fetchDropDowns(driver, pdfVersionDropDownButton, dropdownElements, false, nextScrollButton);
    }

    /**
     * Function to fetch save notification
     * @return - notification text
     */
    public String fetchSaveNotification() {
        return changesSavedSuccessfullyNotificatioon.getText().trim();
    }

    /**
     * Function to verify omr link is visible
     * @param utilities - Utilities class instance
     * @return - true/false based upon visibility
     */
    public Boolean verifyOmrLinkIsVisible(Utilities utilities) {
        return utilities.isElementExist(editOmrLink);
    }

    /**
     * Function to click on omr link
     * @param driver - Webdriver instance
     */
    public void clickOmrLink(WebDriver driver) {
        WebDriverUtil.clickElement(driver, editOmrLink);
    }

    /**
     * Function to verify omr option visibility
     * @param driver - Webdriver instance
     * @return - true/false based upon visibility
     */
    public Boolean verifyOmrOptions(WebDriver driver) {
        Boolean result = false;
        WebDriverUtil.clickElement(driver, autoMarkingKeysAddNew);
        if(omrOptions.size() > 0){
            result = omrOptions.size() == 4;
        }
        return result;
    }

    /**
     * Function to verify imported data
     * @return - true/false based upon visibility
     */
    public Boolean verifyImportedData(){
        utilities.waitForElementExists(uploadedMarkingKeyDataRow);
        return utilities.isElementExist(uploadedMarkingKeyDataRow);
    }

    /**
     * Function to import duplicate paper
     * @param fileName - duplicate filename
     * @param driver - Webdriver instance
     */
    public void importDuplicatePaper(String fileName, WebDriver driver){
        utilities.waitForElementExists(radTest);
        utilities.highLighterMethod(radTest);
        utilities.clickUsingJavaScript(radTest);
        utilities.waitForElementExists(uploadPaperTypeFolderListening);
        utilities.highLighterMethod(uploadPaperTypeFolderListening);
        WebDriverUtil.clickElement(driver,uploadPaperTypeFolderListening);
        Sleep.sleepthread(2);
        utilities.waitForElementExists(uploadButton);
        utilities.clickUsingJavaScript(uploadButton);
        utilities.waitForElementExists(selectFileButton);
        utilities.clickUsingJavaScript(selectFileButton);
        utilities.uploadFile(fileName);
        Sleep.sleepthread(5);
        WebDriverUtil.clickElement(driver,okButton);
        WebDriverUtil.clickElement(driver,yesButton);
    }

    /**
     * Function to fetch error
     * @return - error message
     */
    public String getPopupMessage(){
        Sleep.sleepthread(2);
        utilities.waitForElementExists(confirmationPopup);
        return confirmationPopup.getText();
    }
}
