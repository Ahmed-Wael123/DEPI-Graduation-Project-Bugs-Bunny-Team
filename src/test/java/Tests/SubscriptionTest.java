package Tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import Pages.CartPage;
import Utils.AdHandler;

public class SubscriptionTest extends BaseTest {

    @Test
    public void verifySubscriptionInHomePage() {
        homePage.enterSubscriptionEmail("test@example.com");
        homePage.clickSubscribe();
        Assert.assertTrue(homePage.isSubscriptionSuccessMsgDisplayed(), "Subscription in Home failed!");
    }

    @Test
    public void verifySubscriptionInCartPage() {
        CartPage cartPage = homePage.clickCartButton();
        AdHandler.dismissAdIfPresent(driver);

        cartPage.enterSubscriptionEmail("test@example.com");
        cartPage.clickSubscribe();
        Assert.assertTrue(cartPage.isSubscriptionSuccessMsgDisplayed(), "Subscription in Cart failed!");
    }
}