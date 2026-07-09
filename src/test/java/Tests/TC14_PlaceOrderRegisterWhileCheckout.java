package Tests;

import com.github.javafaker.Faker;
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

public class TC14_PlaceOrderRegisterWhileCheckout extends BaseTest {

    @Test
    public void placeOrderRegisterWhileCheckout() {
        Faker faker = new Faker();
        String name = faker.name().firstName();
        String email = faker.internet().safeEmailAddress();
        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        WebElement addToCartBtn = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("(//a[@data-product-id='1'])[1]")));
        js.executeScript("arguments[0].scrollIntoView({block: 'center'});", addToCartBtn);
        js.executeScript("arguments[0].click();", addToCartBtn);

        WebElement viewCartLink = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//u[normalize-space()='View Cart']")));
        js.executeScript("arguments[0].click();", viewCartLink);
        AdHandler.dismissAdIfPresent(driver);

        WebElement proceedCheckoutBtn1 = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".btn-default.check_out")));
        js.executeScript("arguments[0].click();", proceedCheckoutBtn1);

        WebElement registerLoginLink = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//u[normalize-space()='Register / Login']")));
        js.executeScript("arguments[0].click();", registerLoginLink);
        AdHandler.dismissAdIfPresent(driver);

        WebElement nameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@data-qa='signup-name']")));
        nameInput.sendKeys(name);
        WebElement emailInput = driver.findElement(By.xpath("//input[@data-qa='signup-email']"));
        emailInput.sendKeys(email);

        WebElement signupBtn = driver.findElement(By.xpath("//button[@data-qa='signup-button']"));
        js.executeScript("arguments[0].click();", signupBtn);
        AdHandler.dismissAdIfPresent(driver);

        WebElement passwordInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("password")));
        js.executeScript("arguments[0].scrollIntoView({block: 'center'});", passwordInput);
        passwordInput.sendKeys("Pass12345");

        WebElement firstNameInput = driver.findElement(By.id("first_name"));
        firstNameInput.sendKeys(name);
        WebElement lastNameInput = driver.findElement(By.id("last_name"));
        lastNameInput.sendKeys("User");

        WebElement addressInput = driver.findElement(By.id("address1"));
        js.executeScript("arguments[0].scrollIntoView({block: 'center'});", addressInput);
        addressInput.sendKeys("123 Test Street");

        WebElement stateInput = driver.findElement(By.id("state"));
        stateInput.sendKeys("Cairo");
        WebElement cityInput = driver.findElement(By.id("city"));
        cityInput.sendKeys("Cairo");
        WebElement zipcodeInput = driver.findElement(By.id("zipcode"));
        zipcodeInput.sendKeys("12345");
        WebElement mobileInput = driver.findElement(By.id("mobile_number"));
        mobileInput.sendKeys("01234567890");

        WebElement createAccountBtn = driver.findElement(By.xpath("//button[@data-qa='create-account']"));
        js.executeScript("arguments[0].scrollIntoView({block: 'center'});", createAccountBtn);
        js.executeScript("arguments[0].click();", createAccountBtn);
        AdHandler.dismissAdIfPresent(driver);

        WebElement continueBtn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@data-qa='continue-button']")));
        js.executeScript("arguments[0].click();", continueBtn);
        AdHandler.dismissAdIfPresent(driver);

        WebElement cartMenuLink = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(@href, '/view_cart')]")));
        js.executeScript("arguments[0].click();", cartMenuLink);
        AdHandler.dismissAdIfPresent(driver);

        WebElement proceedCheckoutBtn2 = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".btn-default.check_out")));
        js.executeScript("arguments[0].click();", proceedCheckoutBtn2);
        AdHandler.dismissAdIfPresent(driver);

        CheckoutPage checkoutPage = new CheckoutPage(driver);
        try {
            checkoutPage.enterComment("Fast Automation.");
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

        SignUpLoginPage loginPage = new SignUpLoginPage(driver);
        loginPage.deleteAccount();
        AdHandler.dismissAdIfPresent(driver);
    }
}