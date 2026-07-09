package Tests;

import Pages.CartPage;
import Pages.CheckoutPage;
import Pages.PaymentPage;
import Pages.SignUpLoginPage;
import Utils.AdHandler;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.time.Duration;

public class TC16_PlaceOrderLoginBeforeCheckout extends BaseTest {

    @Test
    public void placeOrderLoginBeforeCheckout() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        WebElement signupLoginLink = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@href='/login']")));
        js.executeScript("arguments[0].click();", signupLoginLink);
        AdHandler.dismissAdIfPresent(driver);

        SignUpLoginPage loginPage = new SignUpLoginPage(driver);
        loginPage.loginEmailAndPassword("ahmed7@gmail.com", "11111");
        AdHandler.dismissAdIfPresent(driver);

        WebElement addToCartBtn = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("(//a[@data-product-id='1'])[1]")));
        js.executeScript("arguments[0].scrollIntoView({block: 'center'});", addToCartBtn);
        js.executeScript("arguments[0].click();", addToCartBtn);

        WebElement viewCartLink = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//u[normalize-space()='View Cart']")));
        js.executeScript("arguments[0].click();", viewCartLink);
        AdHandler.dismissAdIfPresent(driver);

        WebElement proceedCheckoutBtn = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".btn-default.check_out")));
        js.executeScript("arguments[0].click();", proceedCheckoutBtn);
        AdHandler.dismissAdIfPresent(driver);

        CheckoutPage checkoutPage = new CheckoutPage(driver);
        try {
            checkoutPage.enterComment("Fast Automation TC16.");
        } catch (Exception ignored) {}

        WebElement placeOrderBtn = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[normalize-space()='Place Order']")));
        js.executeScript("arguments[0].scrollIntoView({block: 'center'});", placeOrderBtn);
        js.executeScript("arguments[0].click();", placeOrderBtn);
        AdHandler.dismissAdIfPresent(driver);

        PaymentPage paymentPage = new PaymentPage(driver);
        paymentPage.enterPaymentDetails("Ahmed Card", "1234567890123456", "321", "12", "2029");

        WebElement submitBtn = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("submit")));
        js.executeScript("arguments[0].scrollIntoView({block: 'center'});", submitBtn);
        js.executeScript("arguments[0].click();", submitBtn);
        AdHandler.dismissAdIfPresent(driver);

        boolean isPaymentDone = wait.until(ExpectedConditions.urlContains("payment_done"));
        Assert.assertTrue(isPaymentDone, "The URL does not contain 'payment_done'!");
    }
}