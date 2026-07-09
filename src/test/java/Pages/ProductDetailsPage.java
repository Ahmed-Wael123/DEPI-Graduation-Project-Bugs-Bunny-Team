package Pages;

import Utils.ElementActions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ProductDetailsPage {

    private final WebDriver driver;
    private final ElementActions actions;


    private final By writeYourReviewTitle = By.xpath("//a[@href='#reviews']");
    private final By nameInput = By.id("name");
    private final By emailInput = By.id("email");
    private final By reviewTextArea = By.id("review");
    private final By submitButton = By.id("button-review");
    private final By successMessage = By.xpath("//span[text()='Thank you for your review.']");
    private final By productName = By.xpath("//div[@class='product-information']/h2");
    private final By productPrice = By.xpath("//div[@class='product-information']/span/span");

    public ProductDetailsPage(WebDriver driver) {
        this.driver = driver;
        this.actions = new ElementActions(driver);
    }

    public boolean isProductDetailsDisplayed() {
        return actions.isDisplayed(productName) && actions.isDisplayed(productPrice);
    }

    public boolean isWriteYourReviewSectionVisible() {
        actions.scrollToElement(writeYourReviewTitle);
        return actions.isDisplayed(writeYourReviewTitle);
    }

    public void enterReviewDetails(String name, String email, String review) {
        actions.type(nameInput, name);
        actions.type(emailInput, email);
        actions.type(reviewTextArea, review);
    }

    public void submitReview() {
        actions.clickWithJs(submitButton);
    }

    public boolean isReviewSuccessMessageVisible() {
        try {
            org.openqa.selenium.WebElement element = actions.waitForVisibility(successMessage);
            return element.isDisplayed() && element.getText().trim().contains("Thank you for your review");
        } catch (Exception e) {
            return false;
        }
    }
}