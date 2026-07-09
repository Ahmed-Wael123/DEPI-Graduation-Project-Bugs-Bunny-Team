package Pages;

import Utils.ElementActions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.List;
import java.util.stream.Collectors;

public class CartPage {

    private final WebDriver driver;
    private final ElementActions actions;

    private final By cartInfoTable = By.id("cart_info");
    private final By cartProductRows = By.cssSelector("#cart_info_table tbody tr");
    private final By cartProductNames = By.cssSelector("#cart_info_table tbody tr td.cart_description h4 a");
    private final By deleteButtons = By.cssSelector(".cart_quantity_delete");
    private final By subscriptionEmailInput = By.id("susbscribe_email");
    private final By subscribeButton = By.id("subscribe");
    private final By successMessage = By.id("success-subscribe");

    private final By proceedToCheckoutBtn = By.cssSelector(".btn-default.check_out");
    private final By registerLoginLink = By.xpath("//u[normalize-space()='Register / Login']");
    private final By quantityInCart = By.xpath("//td[@class='cart_quantity']/button");

    public CartPage(WebDriver driver) {
        this.driver = driver;
        this.actions = new ElementActions(driver);
    }

    public boolean isCartPageDisplayed() {
        return actions.isDisplayed(cartInfoTable)
                && actions.getCurrentUrl().contains("/view_cart");
    }

    public int getCartItemsCount() {
        return driver.findElements(cartProductRows).size();
    }

    public List<String> getCartProductNames() {
        return driver.findElements(cartProductNames)
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    public boolean isProductInCart(String productName) {
        return getCartProductNames().stream()
                .anyMatch(name -> name.equalsIgnoreCase(productName));
    }

    public void removeProductByIndex(int index) {
        List<WebElement> deleteButtonsList = driver.findElements(deleteButtons);

        if (index < deleteButtonsList.size()) {
            WebElement targetButton = deleteButtonsList.get(index);
            WebElement productRow = targetButton.findElement(By.xpath("./ancestor::tr"));
            targetButton.click();

            new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(10))
                    .until(org.openqa.selenium.support.ui.ExpectedConditions.stalenessOf(productRow));
        } else {
            throw new RuntimeException("The index to be deleted are not found on the cart !!");
        }
    }

    public void removeFirstProduct() {
        removeProductByIndex(0);
    }

    public void enterSubscriptionEmail(String email) {
        actions.type(subscriptionEmailInput, email);
    }

    public void clickSubscribe() {
        actions.click(subscribeButton);
    }

    public boolean isSubscriptionSuccessMsgDisplayed() {
        return actions.isDisplayed(successMessage);
    }

    public String getSingleItemQuantity() {
        return actions.getText(quantityInCart);
    }

    public void clickProceedToCheckout() {
        actions.click(proceedToCheckoutBtn);
    }

    public SignUpLoginPage clickRegisterLoginFromModal() {
        actions.click(registerLoginLink);
        return new SignUpLoginPage(driver);
    }
}