package com.cbla.pages;

import com.cbla.config.properties.TestConfig;
import com.cbla.core.TestCore;
import com.cbla.utils.TestDataGenerator;
import com.cbla.utils.Utilities;
import com.cbla.utils.WebDriverUtil;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;

public class ExamPopUp extends TestCore {
    private Utilities utilities;
    private TestDataGenerator testDataGenerator;
    private String paperType;

    @FindBy(xpath = "(//div[@class='v-gridlayout-slot content-slot 1']//table)[2]//td[3]//div")
    private List<WebElement> attachedPaperName;

    @FindBy(xpath = "//input[@class='v-textfield v-widget v-textfield-required v-required']")
    private WebElement examNameInput;

    @FindBy(xpath = "//input[@class='v-textfield v-datefield-textfield']")
    private WebElement examDateInput;

    @FindBy(css = ".v-menubar-save .v-menubar-menuitem-primary")
    private WebElement examSaveButton;

    @FindBy(xpath = "//p[@class='v-Notification-description']")
    private WebElement changesSavedSuccessfullyNotificatioon;

    public ExamPopUp(WebDriver driver) {
        super();
        this.utilities = new Utilities(driver);
    }

    /**
     * Function to fetch attahced paper name
     * @param driver - Webdriver instance
     * @return - List of all fetched paper names
     */
    public List<String> fetchAttachedPaperName(WebDriver driver) {
        WebDriverUtil.waitUntilVisible(driver, TestConfig.NORMAL_WAIT, attachedPaperName.get(0));
        List<String> attachedPaperNamesList = new ArrayList<>();
        for (WebElement element : attachedPaperName) {
            String dropdownItem = element.getText().trim();
            attachedPaperNamesList.add(dropdownItem);
        }
        return attachedPaperNamesList;
    }

    /**
     * Function to create exam name
     * @return - exam name
     */
    public String examName() {
        return "Exam" + testDataGenerator.randomInt(1000, 5000);
    }

    /**
     * Function to fill mandatory fields for create exam popup
     * @param examName - name of exam
     */
    public void fillCreateExamMandatoryFields(String examName) {
        examNameInput.sendKeys(examName);
        String date = testDataGenerator.formatDate(testDataGenerator.getTodaysDate(), "dd MMM yyyy HH:mm");
        examDateInput.sendKeys(date);
    }

    /**
     * Function to click on save exam button
     * @param driver - Webdriver instance
     */
    public void clickExamSaveButton(WebDriver driver) {
        WebDriverUtil.clickElement(driver, examSaveButton);
    }

    /**
     * Fucntion to fetch save notification
     * @return - notification text
     */
    public String fetchSaveNotification() {
        return changesSavedSuccessfullyNotificatioon.getText().trim();
    }

}
