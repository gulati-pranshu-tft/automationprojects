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


public class CBLAHomePage extends TestCore {

    protected Utilities utilities;
    @FindBy(xpath = "//input[@id='u']")
    private WebElement username;
    @FindBy(xpath = "//input[@id='p']")
    private WebElement passwordField;
    @FindBy(xpath = "//input[@id='s']")
    private WebElement loginButton;
    @FindBy(xpath = "//div[@class='v-menubar-popup']//span[text()='Exam']")
    private WebElement examPopupItem;
    @FindBy(xpath = "//div[@class='v-window-header']")
    private WebElement examPopup;
    @FindBy(xpath = "//div[@class='v-menubar v-widget']//span[contains(@class,'add')]")
    private WebElement addMenuItem;
    @FindBy(css = "div[class='v-tooltip'] + div + div .v-window-header")
    private WebElement createPaperPopup;
    @FindBy(xpath = "//span[text()='Create paper']")
    private WebElement createPaperButton;
    @FindBy(xpath = "//div[text()='PDFs']")
    private WebElement PDFTableTitle;
    @FindBy(xpath = "//div[text()='Marking Keys']")
    private WebElement markingKeysTableTitle;
    @FindBy(xpath = "//span[text()='Marking Keys']")
    private WebElement markingKeysWidgetTitle;
    @FindBy(xpath = "//span[text()='Add new key']")
    private WebElement addNewKeyButton;
    @FindBy(xpath = "//div[contains(@class,'g-ckeditor-inline')]")
    private WebElement markingKeyTextLocator;
    @FindBy(xpath = "(//div[contains(@class,'g-ckeditor-inline')])[2]")
    private WebElement secondRowMarkingKeyLocator;
    @FindBy(xpath = "(//td[@class='v-table-cell-content'])[12] //input")
    private WebElement scoreTextLocator;
    @FindBy(xpath = "(//td[@class='v-table-cell-content'])[19] //input")
    private WebElement secondRowScoreTextLocator;
    @FindBy(xpath = "//div[@class='v-button v-widget button-0 v-button-button-0 save v-button-save']")
    private WebElement markingKeyWidgetSaveButton;
    @FindBy(xpath = "(//span[text()='Add New'])[1]")
    private WebElement PDFAddNewButton;
    @FindBy(xpath = "(//span[text()='Add New'])[2]")
    private WebElement markingKeysAddNewButton;
    @FindBy(xpath = "//span[text()='Zone 1']")
    private WebElement zoneVersionOption;
    @FindBy(xpath = "(//div[contains(@class,'v-menubar-save')])[2]")
    private WebElement saveButton;
    @FindBy(xpath = "(//div[contains(@class,'v-errorindicator v-errorindicator-error')])[4]")
    private WebElement PDFMandatoryErrorIndicator;
    @FindBy(xpath = "//div[contains(@class,'v-errormessage v-errormessage-error')]//div//div")
    private WebElement PDFMandatoryErrorText;
    @FindBy(xpath = "//input[@class='v-textfield v-widget v-has-width']")
    private WebElement orderInputLocator;
    @FindBy(xpath = "//input[@class='v-textfield v-widget v-has-width']/../../..//preceding-sibling::td[1]//div")
    private WebElement keysDataLocator;
    @FindBy(xpath = "//p[@class='v-Notification-description']")
    private WebElement keysError;
    @FindBy(xpath = "//span[text()='Edit']")
    private WebElement editHyperlink;
    @FindBy(xpath = "//td[contains(@class,'gwt-MenuItem')]//span")
    private List<WebElement> dropdownElements;
    @FindBy(xpath = "(//div[contains(@class,'v-filterselect-required v-required v-has-width')])[1]//div[@class='v-filterselect-button']")
    private WebElement versionDropdownLocator;
    @FindBy(xpath = "(//div[contains(@class,'v-filterselect-required v-required v-has-width')])[2]//div[@class='v-filterselect-button']")
    private WebElement PDFTypeDropdownLocator;

    public CBLAHomePage(WebDriver driver) {
        super();
        this.utilities = new Utilities(driver);
    }

    /**
     * Function to login to cbla application
     * @param driver - Webdriver instance
     * @param user - username
     * @param password - password
     */
    public void login(WebDriver driver, String user, String password) {
        WebDriverUtil.waitUntilVisible(driver, TestConfig.NORMAL_WAIT, username);
        utilities.highLighterMethod(username);
        username.sendKeys(user);
        utilities.highLighterMethod(passwordField);
        passwordField.sendKeys(password);
        WebDriverUtil.clickElement(driver, loginButton);
        WebDriverUtil.waitForPageLoad(driver);
    }

    /**
     * Function to verify exam popup is visible
     * @param driver - Webdriver instance
     * @return - true/false based upon visibility
     */
    public String verifyExamPopupIsOpen(WebDriver driver) {
        WebDriverUtil.waitUntilVisible(driver, TestConfig.NORMAL_WAIT, addMenuItem);
        WebDriverUtil.hoverOver(driver, addMenuItem);
        utilities.highLighterMethod(examPopupItem);
        WebDriverUtil.clickElement(driver, examPopupItem);
        WebDriverUtil.waitUntilVisible(driver, TestConfig.NORMAL_WAIT, examPopup);
        return examPopup.getText().trim();
    }

    /**
     * Function to click on create paper button
     * @param driver - Webdriver instance
     */
    public void clickCreatePaperButton(WebDriver driver) {
        utilities = new Utilities(driver);
        utilities.clickUsingJavaScript(createPaperButton);
    }

    /**
     * Function to verify marking keys creation
     * @param driver - Webdriver instance
     * @param markingKey1 - Marking key value 1
     * @param markingKey2 - Marking key value 2
     * @param score - Score value
     */
    public String verifyMarkingKeysCreation(WebDriver driver, String markingKey1, String markingKey2, String score) throws InterruptedException {
        this.clickCreatePaperButton(driver);
        WebDriverUtil.waitUntilVisible(driver, TestConfig.NORMAL_WAIT, markingKeysTableTitle);
        WebDriverUtil.scrollToElement(driver, markingKeysAddNewButton);
        utilities.highLighterMethod(markingKeysAddNewButton);
        utilities.clickUsingJavaScript(markingKeysAddNewButton);
        utilities.highLighterMethod(editHyperlink);
        utilities.clickUsingJavaScript(editHyperlink);
        WebDriverUtil.waitUntilVisible(driver, TestConfig.NORMAL_WAIT, markingKeysWidgetTitle);
        utilities.highLighterMethod(editHyperlink);
        utilities.clickUsingJavaScript(addNewKeyButton);
        Sleep.sleepthread(2);
        utilities.highLighterMethod(markingKeyTextLocator);
        markingKeyTextLocator.sendKeys(markingKey1);
        scoreTextLocator.clear();
        Sleep.sleepthread(2);
        utilities.highLighterMethod(markingKeyTextLocator);
        scoreTextLocator.sendKeys(score);
        Sleep.sleepthread(2);
        utilities.highLighterMethod(addNewKeyButton);
        utilities.clickUsingJavaScript(addNewKeyButton);
        Sleep.sleepthread(5);
        utilities.highLighterMethod(secondRowMarkingKeyLocator);
        secondRowMarkingKeyLocator.sendKeys(markingKey2);
        secondRowScoreTextLocator.clear();
        Sleep.sleepthread(2);
        utilities.highLighterMethod(secondRowScoreTextLocator);
        secondRowScoreTextLocator.sendKeys(score);
        WebDriverUtil.waitUntilVisible(driver, TestConfig.NORMAL_WAIT, markingKeyWidgetSaveButton);
        utilities.highLighterMethod(markingKeyWidgetSaveButton);
        WebDriverUtil.clickElement(driver, markingKeyWidgetSaveButton);
        WebDriverUtil.scrollToElement(driver, keysDataLocator);
        WebDriverUtil.waitUntilVisible(driver, TestConfig.NORMAL_WAIT, keysDataLocator);
        return keysDataLocator.getText().trim();
    }

    /**
     * Function to verify marking keys error
     * @param driver - Webdriver instance
     * @param markingKey1 - Marking key value 1
     * @param markingKey2 - Marking key value 2
     * @param score - Score value
     */
    public String verifyMarkingKeysError(WebDriver driver, String markingKey1, String markingKey2, String score) throws InterruptedException {
        this.clickCreatePaperButton(driver);
        WebDriverUtil.waitUntilVisible(driver, TestConfig.NORMAL_WAIT, markingKeysTableTitle);
        WebDriverUtil.scrollToElement(driver, markingKeysAddNewButton);
        utilities.highLighterMethod(markingKeysAddNewButton);
        utilities.clickUsingJavaScript(markingKeysAddNewButton);
        utilities.highLighterMethod(editHyperlink);
        utilities.clickUsingJavaScript(editHyperlink);
        WebDriverUtil.waitUntilVisible(driver, TestConfig.NORMAL_WAIT, markingKeysWidgetTitle);
        utilities.highLighterMethod(editHyperlink);
        utilities.clickUsingJavaScript(addNewKeyButton);
        Sleep.sleepthread(2);
        utilities.highLighterMethod(markingKeyTextLocator);
        WebDriverUtil.waitUntilVisible(driver, TestConfig.NORMAL_WAIT, markingKeyWidgetSaveButton);
        utilities.highLighterMethod(markingKeyWidgetSaveButton);
        WebDriverUtil.clickElement(driver, markingKeyWidgetSaveButton);
        WebDriverUtil.scrollToElement(driver, keysError);
        WebDriverUtil.waitUntilVisible(driver, TestConfig.NORMAL_WAIT, keysError);
        return keysError.getText().trim();
    }

    /**
     * Function to verify pdf type is mandotory
     * @param driver - Webdriver instance
     * @return - Alert message
     */
    public String verifyPDFTypeIsMandatory(WebDriver driver) {
        this.clickCreatePaperButton(driver);
        WebDriverUtil.waitUntilVisible(driver, TestConfig.NORMAL_WAIT, PDFTableTitle);
        WebDriverUtil.scrollToElement(driver, PDFAddNewButton);
        utilities.highLighterMethod(PDFAddNewButton);
        utilities.clickUsingJavaScript(PDFAddNewButton);
        utilities.highLighterMethod(versionDropdownLocator);
        WebDriverUtil.clickElement(driver, versionDropdownLocator);
        WebDriverUtil.waitUntilVisible(driver, TestConfig.NORMAL_WAIT, zoneVersionOption);
        utilities.highLighterMethod(zoneVersionOption);
        WebDriverUtil.clickElement(driver, zoneVersionOption);
        utilities.highLighterMethod(orderInputLocator);
        orderInputLocator.sendKeys("1");
        WebDriverUtil.scrollToElement(driver, saveButton);
        WebDriverUtil.waitUntilClickable(driver, TestConfig.NORMAL_WAIT, saveButton);
        utilities.highLighterMethod(saveButton);
        WebDriverUtil.clickElement(driver, saveButton);
        WebDriverUtil.waitUntilVisible(driver, TestConfig.NORMAL_WAIT, PDFMandatoryErrorIndicator);
        WebDriverUtil.scrollToElement(driver, PDFMandatoryErrorIndicator);
        WebDriverUtil.hoverOver(driver, PDFMandatoryErrorIndicator);
        return PDFMandatoryErrorText.getText().trim();
    }

    /**
     * Function to fetch pdf type dropdown value
     * @param driver - Webdriver instance
     * @return - List of all options in pdf type dropdown
     */
    public List<String> fetchPDFTypeDropdownValues(WebDriver driver) {
        utilities.highLighterMethod(PDFTypeDropdownLocator);
        WebDriverUtil.clickElement(driver, PDFTypeDropdownLocator);
        WebDriverUtil.waitUntilVisible(driver, TestConfig.NORMAL_WAIT, dropdownElements.get(0));
        List<String> dropdownList = new ArrayList<>();
        for (int i = 0; i < dropdownElements.size(); i++) {
            String dropdownItem = dropdownElements.get(i).getText().trim();
            dropdownList.add(dropdownItem);
        }
        dropdownList.remove(0);
        return dropdownList;
    }
}