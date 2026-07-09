package Pages;

import Utils.ElementActions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class PaymentPage {
    private final WebDriver driver;
    private final ElementActions actions;

    private final By nameOnCardInput = By.xpath("//input[@data-qa='name-on-card']");
    private final By cardNumberInput = By.xpath("//input[@data-qa='card-number']");
    private final By cvcInput = By.xpath("//input[@data-qa='cvc']");
    private final By expiryMonthInput = By.xpath("//input[@data-qa='expiry-month']");
    private final By expiryYearInput = By.xpath("//input[@data-qa='expiry-year']");
    private final By payButton = By.xpath("//button[@data-qa='pay-button']");

    private final By successMessage = By.xpath("//b[contains(text(),'Order Placed')]");

    private final By downloadInvoiceBtn = By.xpath("//a[text()='Download Invoice']");
    private final By continueBtn = By.xpath("//a[text()='Continue']");

    public PaymentPage(WebDriver driver) {
        this.driver = driver;
        this.actions = new ElementActions(driver);
    }

    public void enterPaymentDetails(String name, String number, String cvc, String month, String year) {
        actions.type(nameOnCardInput, name);
        actions.type(cardNumberInput, number);
        actions.type(cvcInput, cvc);
        actions.type(expiryMonthInput, month);
        actions.type(expiryYearInput, year);
    }

    public void clickPayAndConfirmOrder() {
        actions.clickWithJs(payButton);
    }

    public boolean isOrderSuccessMessageDisplayed() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            return wait.until(ExpectedConditions.visibilityOfElementLocated(successMessage)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void clickDownloadInvoice() {
        actions.clickWithJs(downloadInvoiceBtn);
    }

    public void clickContinue() {
        actions.clickWithJs(continueBtn);
    }
}