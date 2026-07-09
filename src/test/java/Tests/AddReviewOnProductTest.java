package Tests;

import Pages.ProductDetailsPage;
import Pages.ProductsPage;
import TestData.AppData;
import Utils.AdHandler;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AddReviewOnProductTest extends BaseTest {

    @Test(description = "TC21 - Add review on product")
    public void addReviewOnProduct() {
        // 3. Click on 'Products' button
        ProductsPage productsPage = homePage.clickProductsButton();

        if (driver.getCurrentUrl().contains("google_vignette") || driver.getCurrentUrl().contains("#")) {

            AdHandler.dismissAdIfPresent(driver);

            if (driver.getCurrentUrl().contains("google_vignette")) {
                driver.get("https://automationexercise.com/products");
            }
        }

        // 4. Verify user is navigated to ALL PRODUCTS page successfully
        Assert.assertTrue(productsPage.isAllProductsPageVisible(),
                "User was not navigated to the ALL PRODUCTS page.");

        // 5. Click on 'View Product' button
        ProductDetailsPage productDetailsPage = productsPage.clickViewProductByIndex(0);

        if (driver.getCurrentUrl().contains("google_vignette")) {
            AdHandler.dismissAdIfPresent(driver);
        }

        // 6. Verify 'Write Your Review' is visible
        Assert.assertTrue(productDetailsPage.isWriteYourReviewSectionVisible(),
                "'Write Your Review' section is not visible.");

        // 7. Enter name, email and review
        productDetailsPage.enterReviewDetails(
                AppData.REVIEWER_NAME,
                AppData.REVIEWER_EMAIL,
                AppData.REVIEW_TEXT
        );

        // 8. Click 'Submit' button
        productDetailsPage.submitReview();

        // 9. Verify success message 'Thank you for your review.'
        Assert.assertTrue(productDetailsPage.isReviewSuccessMessageVisible(),
                "Success message 'Thank you for your review.' was not displayed.");
    }
}