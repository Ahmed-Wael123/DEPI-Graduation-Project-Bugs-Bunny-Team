package Tests;

import org.testng.Assert;
import org.testng.annotations.Test;

public class TC26_ScrollUpWithoutArrowButton extends BaseTest {

    @Test
    public void testScrollUpWithoutArrowButton() {
        homePage.assertHomePageIsDisplayed();

        homePage.scrollToBottom();
        Assert.assertTrue(homePage.isSubscriptionTitleVisible(), "Subscription text is not visible at the bottom!");

        homePage.scrollToTop();

        Assert.assertTrue(homePage.isHeaderTextVisible(), "Header text is not visible at the top after scrolling up!");
    }
}