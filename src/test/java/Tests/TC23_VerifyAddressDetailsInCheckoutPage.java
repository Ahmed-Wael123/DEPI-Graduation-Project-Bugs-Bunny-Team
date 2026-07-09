package Tests;

import Pages.*;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TC23_VerifyAddressDetailsInCheckoutPage extends BaseTest {

    @Test
    public void testVerifyAddressDetailsInCheckoutPage() {
        homePage.assertHomePageIsDisplayed();

        homePage.clickSignupLoginButton();
        String name = "User" + System.currentTimeMillis();
        String email = "checkout" + System.currentTimeMillis() + "@test.com";

        driver.findElement(By.xpath("//input[@data-qa='signup-name']")).sendKeys(name);
        driver.findElement(By.xpath("//input[@data-qa='signup-email']")).sendKeys(email);
        driver.findElement(By.xpath("//button[@data-qa='signup-button']")).click();

        RegisterPage registerPage = new RegisterPage(driver);
        AccountPage accountPage = registerPage.registerAccount("PassCheckout123", name);

        Assert.assertTrue(accountPage.isAccountCreatedVisible(), "Account Created is not visible!");
        accountPage.clickContinue();

        Assert.assertTrue(homePage.getLoggedInUserText().contains(name), "Username is not visible at top!");

        homePage.clickProductsButton();
        homePage.addProductToCartByIndex(0);
        CartPage cartPage = homePage.clickCartButton();

        Assert.assertTrue(cartPage.isCartPageDisplayed(), "Cart page is not displayed!");
        cartPage.clickProceedToCheckout();

        CheckoutPage checkoutPage = new CheckoutPage(driver);
        Assert.assertTrue(checkoutPage.isAddressDetailsDisplayed(), "Address details are not displayed!");

        homePage.clickDeleteAccountButton();
        Assert.assertTrue(accountPage.isAccountDeletedVisible(), "Account Deleted is not visible!");
        accountPage.clickContinue();
    }
}