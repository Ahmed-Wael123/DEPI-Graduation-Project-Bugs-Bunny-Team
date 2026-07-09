package Tests;

import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.util.List;

public class HomePageTest extends BaseTest {

    @BeforeMethod
    public void preconditions() {
        List<WebElement> cards = homePage.getProductCards();
        List<WebElement> images = homePage.getProductImages();
        List<WebElement> names = homePage.getProductNames();
        List<WebElement> prices = homePage.getProductPrices();
    }

    @Test
    public void verifyHomePageElements() {
        homePage.assertHomePageIsDisplayed();
        homePage.assertAllHomePageElementsAreDisplayed();
    }

    @Test
    public void verifyProductsAreVisible() {
        homePage.AssertProductsAreVisible();
        homePage.assertAddToCartButtonsAreVisible();
        homePage.assertViewProductButtonsAreVisible();
    }
}