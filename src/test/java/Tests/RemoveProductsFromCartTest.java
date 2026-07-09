package Tests;

import Pages.CartPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class RemoveProductsFromCartTest extends BaseTest {

    @Test(description = "TC17 - Remove Products From Cart")
    public void removeProductsFromCart() {
        Assert.assertTrue(homePage.isHomePageVisible(), "Home page was not displayed successfully.");

        homePage.addFirstNProductsToCart(2);

        CartPage cartPage = homePage.clickCartButton();

        Assert.assertTrue(cartPage.isCartPageDisplayed(), "Cart page was not displayed.");

        int itemsBeforeRemoval = cartPage.getCartItemsCount();
        Assert.assertTrue(itemsBeforeRemoval > 0, "Cart is empty, nothing was added.");

        cartPage.removeFirstProduct();

        int itemsAfterRemoval = cartPage.getCartItemsCount();
        Assert.assertEquals(itemsAfterRemoval, itemsBeforeRemoval - 1,
                "Product was not removed from the cart.");
    }
}