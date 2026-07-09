package Tests;

import Pages.CartPage;
import Pages.ProductsPage;
import Pages.SignUpLoginPage;
import TestData.TestDataProvider;
import Utils.AdHandler;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SearchProductsVerifyCartAfterLoginTest extends BaseTest {

    @Test(dataProvider = "searchKeywords",
            dataProviderClass = TestDataProvider.class,
            description = "TC20 - Search Products and Verify Cart After Login")
    public void searchProductsAndVerifyCartAfterLogin(String keyword) {

        JavascriptExecutor js = (JavascriptExecutor) driver;

        try {
            js.executeScript("arguments[0].click();", driver.findElement(By.xpath("//a[@href='/products']")));
        } catch (Exception e) {
            homePage.clickProductsButton();
        }
        AdHandler.dismissAdIfPresent(driver);

        ProductsPage productsPage = new ProductsPage(driver);
        productsPage.searchForProduct(keyword);

        Assert.assertTrue(productsPage.isSearchedProductsTitleVisible(),
                "'SEARCHED PRODUCTS' title is not visible.");

        Assert.assertTrue(productsPage.getProductsCount() > 0,
                "No products were returned for keyword: " + keyword);

        productsPage.addAllVisibleProductsToCart();

        try {
            js.executeScript("arguments[0].click();", driver.findElement(By.xpath("//a[@href='/view_cart']")));
        } catch (Exception e) {
            homePage.clickCartButton();
        }

        CartPage cartPage = new CartPage(driver);
        Assert.assertTrue(cartPage.isCartPageDisplayed(), "Cart page was not displayed.");
        Assert.assertTrue(cartPage.getCartItemsCount() > 0, "No products found in the cart.");

        try {
            js.executeScript("arguments[0].click();", driver.findElement(By.xpath("//a[@href='/login']")));
        } catch (Exception e) {
            homePage.clickSignupLoginButton();
        }
        AdHandler.dismissAdIfPresent(driver);

        SignUpLoginPage loginPage = new SignUpLoginPage(driver);
        loginPage.loginUser("ahmed7@gmail.com", "11111");
        Assert.assertTrue(loginPage.isLoggedInSuccessfully(), "Login failed with the provided credentials.");

        try {
            js.executeScript("arguments[0].click();", driver.findElement(By.xpath("//a[@href='/view_cart']")));
        } catch (Exception e) {
            homePage.clickCartButton();
        }

        CartPage cartPageAfterLogin = new CartPage(driver);
        Assert.assertTrue(cartPageAfterLogin.isCartPageDisplayed(), "Cart page was not displayed after login.");
        Assert.assertTrue(cartPageAfterLogin.getCartItemsCount() > 0,
                "Cart is empty after login; products were not preserved.");
    }
}