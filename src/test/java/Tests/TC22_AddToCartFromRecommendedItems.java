package Tests;

import Pages.CartPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TC22_AddToCartFromRecommendedItems extends BaseTest {

    @Test
    public void testAddToCartFromRecommendedItems() {
        homePage.assertHomePageIsDisplayed();

        homePage.addRecommendedProductToCart();
        homePage.clickViewCartFromModal();

        CartPage cartPage = new CartPage(driver);
        Assert.assertTrue(cartPage.isCartPageDisplayed(), "Cart page is not displayed!");
        Assert.assertTrue(cartPage.getCartItemsCount() > 0, "Product is not displayed in the cart!");
    }
}