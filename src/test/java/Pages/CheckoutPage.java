package Pages;

import Utils.ElementActions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CheckoutPage {
    private final WebDriver driver;
    private final ElementActions actions;

    private final By addressDeliveryHeader = By.id("address_delivery");
    private final By orderReviewHeader = By.xpath("//h2[normalize-space()='Review Your Order']");
    private final By commentTextArea = By.name("message");
    private final By placeOrderBtn = By.cssSelector(".btn-default.check_out");

    public CheckoutPage(WebDriver driver) {
        this.driver = driver;
        this.actions = new ElementActions(driver);
    }

    public boolean isAddressDetailsDisplayed() {
        return actions.isDisplayed(addressDeliveryHeader);
    }

    public boolean isOrderReviewDisplayed() {
        return actions.isDisplayed(orderReviewHeader);
    }

    public void enterComment(String comment) {
        actions.type(commentTextArea, comment);
    }

    public PaymentPage clickPlaceOrder() {
        actions.click(placeOrderBtn);
        return new PaymentPage(driver);
    }
}