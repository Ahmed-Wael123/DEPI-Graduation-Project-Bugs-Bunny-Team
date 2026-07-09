package Pages;

import Utils.ElementActions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AccountPage {
    private final WebDriver driver;
    private final ElementActions actions;

    private final By accountCreatedTitle = By.xpath("//b[text()='Account Created!']");
    private final By accountDeletedTitle = By.xpath("//b[text()='Account Deleted!']");
    private final By continueBtn = By.xpath("//a[@data-qa='continue-button']");

    public AccountPage(WebDriver driver) {
        this.driver = driver;
        this.actions = new ElementActions(driver);
    }

    public boolean isAccountCreatedVisible() {
        return actions.isDisplayed(accountCreatedTitle);
    }

    public boolean isAccountDeletedVisible() {
        return actions.isDisplayed(accountDeletedTitle);
    }

    public void clickContinue() {
        actions.clickWithJs(continueBtn);
    }
}