package Tests;

import Pages.ProductsPage;
import Utils.AdHandler;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ViewCategoryProductsTest extends BaseTest {

    @Test(description = "TC18 - View Category Products")
    public void viewCategoryProducts() {
        ProductsPage productsPage = homePage.clickProductsButton();
        AdHandler.dismissAdIfPresent(driver);

        Assert.assertTrue(productsPage.areCategoriesVisibleOnSidebar(),
                "Categories are not visible on the left sidebar.");

        productsPage.clickWomenCategory();
        productsPage = productsPage.clickSubCategory("Dress");
        AdHandler.dismissAdIfPresent(driver);

        String categoryTitle = productsPage.getCategoryPageTitleText();
        Assert.assertTrue(categoryTitle.toUpperCase().contains("WOMEN"),
                "Category page title does not confirm the 'Women' category: " + categoryTitle);

        productsPage.clickMenCategory();
        productsPage = productsPage.clickMenSubCategoryByIndex(0);
        AdHandler.dismissAdIfPresent(driver);

        String menCategoryTitle = productsPage.getCategoryPageTitleText();
        Assert.assertTrue(menCategoryTitle.toUpperCase().contains("MEN"),
                "User was not navigated to the Men sub-category page: " + menCategoryTitle);
    }
}