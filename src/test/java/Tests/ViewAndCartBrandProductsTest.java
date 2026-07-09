package Tests;

import Pages.ProductsPage;
import Utils.AdHandler;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ViewAndCartBrandProductsTest extends BaseTest {

    @Test(description = "TC19 - View & Cart Brand Products")
    public void viewAndCartBrandProducts() {
        ProductsPage productsPage = homePage.clickProductsButton();
        AdHandler.dismissAdIfPresent(driver);

        Assert.assertTrue(productsPage.areBrandsVisibleOnSidebar(),
                "Brands are not visible on the left sidebar.");

        String firstBrandName = productsPage.getBrandLinkText(0).toLowerCase().trim();

        productsPage = productsPage.clickBrandByIndex(0);
        AdHandler.dismissAdIfPresent(driver);

        String firstPageTitle = productsPage.getCategoryPageTitleText().toLowerCase().trim();
        Assert.assertTrue(firstPageTitle.contains(firstBrandName),
                "User was not navigated to the expected brand page. Expected to contain: " + firstBrandName + " but got: " + firstPageTitle);
        Assert.assertTrue(productsPage.areProductsDisplayed(),
                "No products were displayed on the brand page.");

        String secondBrandName = productsPage.getBrandLinkText(1).toLowerCase().trim();
        productsPage = productsPage.clickBrandByIndex(1);
        AdHandler.dismissAdIfPresent(driver);

        String secondPageTitle = productsPage.getCategoryPageTitleText().toLowerCase().trim();
        Assert.assertTrue(secondPageTitle.contains(secondBrandName),
                "User was not navigated to the second brand page. Expected to contain: " + secondBrandName + " but got: " + secondPageTitle);
        Assert.assertTrue(productsPage.areProductsDisplayed(),
                "No products were displayed on the second brand page.");
    }
}