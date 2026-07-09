package Pages;

import Utils.ElementActions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class TestCasesPage {
    private final WebDriver driver;
    private final ElementActions actions;

    // ---------- Locators ----------
    private final By testCasesTitle = By.xpath("//b[contains(text(),'Test Cases')]");

    // Constructor
    public TestCasesPage(WebDriver driver) {
        this.driver = driver;
        this.actions = new ElementActions(driver);
    }

    public boolean isTestCasesPageDisplayed() {
        return actions.isDisplayed(testCasesTitle);
    }
}