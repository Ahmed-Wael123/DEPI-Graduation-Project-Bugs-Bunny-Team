package Tests;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;
import org.testng.annotations.Test;
import Pages.ProductsPage;
import Pages.ProductDetailsPage;
import Utils.AdHandler;

public class ProductsTest extends BaseTest {

    @Test
    public void verifyAllProductsAndDetails() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        try {
            js.executeScript("arguments[0].click();", driver.findElement(By.xpath("//a[@href='/products']")));
        } catch (Exception e) {
            homePage.clickProductsButton();
        }
        AdHandler.dismissAdIfPresent(driver);

        ProductsPage productsPage = new ProductsPage(driver);
        Assert.assertTrue(productsPage.isAllProductsPageDisplayed(), "All Products page not loaded!");

        try {
            js.executeScript("arguments[0].click();", driver.findElement(By.xpath("//a[contains(@href, '/product_details')]")));
        } catch (Exception e) {
            productsPage.clickFirstProductView();
        }
        AdHandler.dismissAdIfPresent(driver);

        ProductDetailsPage detailsPage = new ProductDetailsPage(driver);
        Assert.assertTrue(detailsPage.isProductDetailsDisplayed(), "Product details are missing!");
    }

    @Test
    public void searchProductTest() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        try {
            js.executeScript("arguments[0].click();", driver.findElement(By.xpath("//a[@href='/products']")));
        } catch (Exception e) {
            homePage.clickProductsButton();
        }
        AdHandler.dismissAdIfPresent(driver);

        ProductsPage productsPage = new ProductsPage(driver);
        productsPage.searchForProduct("Shirt");
        Assert.assertTrue(productsPage.isSearchedProductsTitleDisplayed(), "Searched products not visible!");
    }
}