package Tests;

import Pages.*;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TC24_DownloadInvoiceAfterPurchaseOrder extends BaseTest {

    @Test
    public void testDownloadInvoiceAfterPurchaseOrder() {
        homePage.assertHomePageIsDisplayed();

        homePage.addProductToCartByIndex(0);
        CartPage cartPage = homePage.clickCartButton();

        Assert.assertTrue(cartPage.isCartPageDisplayed(), "Cart page is not displayed!");
        cartPage.clickProceedToCheckout();

        cartPage.clickRegisterLoginFromModal();
        String name = "InvoiceUser" + System.currentTimeMillis();
        String email = "invoice" + System.currentTimeMillis() + "@test.com";

        driver.findElement(By.xpath("//input[@data-qa='signup-name']")).sendKeys(name);
        driver.findElement(By.xpath("//input[@data-qa='signup-email']")).sendKeys(email);
        driver.findElement(By.xpath("//button[@data-qa='signup-button']")).click();

        RegisterPage registerPage = new RegisterPage(driver);
        AccountPage accountPage = registerPage.registerAccount("PassInvoice123", name);

        Assert.assertTrue(accountPage.isAccountCreatedVisible(), "Account Created is not visible!");
        accountPage.clickContinue();

        Assert.assertTrue(homePage.getLoggedInUserText().contains(name), "Username is not visible at top!");

        homePage.clickCartButton();
        cartPage.clickProceedToCheckout();

        CheckoutPage checkoutPage = new CheckoutPage(driver);
        checkoutPage.enterComment("Test Order Invoice");
        PaymentPage paymentPage = checkoutPage.clickPlaceOrder();

        paymentPage.enterPaymentDetails(name, "4111111111111111", "123", "12", "2028");
        paymentPage.clickPayAndConfirmOrder();

        Assert.assertTrue(paymentPage.isOrderSuccessMessageDisplayed(), "Success message is not displayed!");
        paymentPage.clickDownloadInvoice();
        paymentPage.clickContinue();

        homePage.clickDeleteAccountButton();
        Assert.assertTrue(accountPage.isAccountDeletedVisible(), "Account Deleted is not visible!");
        accountPage.clickContinue();
    }
}