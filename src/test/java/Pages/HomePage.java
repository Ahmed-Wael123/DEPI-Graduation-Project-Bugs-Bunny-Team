package Pages;

import Utils.ElementActions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import java.time.Duration;
import java.util.List;

public class HomePage {

    private final WebDriver driver;
    private final ElementActions actions;
    private final String URL = "https://automationexercise.com/";
    private final By homePageSlider = By.id("slider");
    private final By productsNavButton = By.xpath("//a[@href='/products']");
    private final By cartNavButton = By.xpath("//a[@href='/view_cart']");
    private final By signupLoginNavButton = By.xpath("//a[@href='/login']");
    private final By featuredProductWrappers = By.cssSelector(".features_items .product-image-wrapper");
    private final By continueShoppingButton = By.cssSelector(".btn-success.close-modal");
    private final By productCards = By.xpath("//div[@class='features_items']//div[@class='col-sm-4']");
    private final By productImages = By.xpath("//div[@class='features_items']//div[@class='single-products']//img");
    private final By productNames = By.xpath("//div[@class='features_items']//div[@class='single-products']//p");
    private final By productPrices = By.xpath("//div[@class='features_items']//div[@class='single-products']//h2");
    private final By addToCartButtons = By.xpath("//a[contains(@class, 'add-to-cart')]");
    private final By viewProductButtons = By.xpath("//ul[@class='nav nav-pills nav-justified']//a");
    private final By testCasesButton = By.xpath("//a[contains(text(), 'Test Cases')]");
    private final By subscriptionEmailInput = By.id("susbscribe_email");
    private final By subscribeButton = By.id("subscribe");
    private final By successMessage = By.id("success-subscribe");

    private final By recommendedItemsTitle = By.xpath("//h2[text()='recommended items']");
    private final By recommendedAddToCartBtn = By.xpath("//div[@id='recommended-item-carousel']//div[@class='item active']//a[contains(@class,'add-to-cart')]");
    private final By viewCartLink = By.xpath("//u[text()='View Cart']");
    private final By deleteAccountNavButton = By.xpath("//a[contains(@href, '/delete_account')]");
    private final By loggedInAsText = By.xpath("//a[contains(text(),'Logged in as')]");

    private final By subscriptionTitle = By.xpath("//h2[text()='Subscription']");

    // تعديل اللوكيتور ليكون كلمة واحدة صريحة ومضمونة في الـ DOM
    private final By headerText = By.xpath("//h2[contains(text(),'Full-Fledged')]");
    private final By scrollUpArrow = By.id("scrollUp");

    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.actions = new ElementActions(driver);
    }

    public void navigate() {
        driver.get(URL);
    }

    public boolean isHomePageVisible() {
        String currentUrl = actions.getCurrentUrl();
        return actions.isDisplayed(homePageSlider)
                && (currentUrl.equals("https://automationexercise.com") || currentUrl.equals("https://automationexercise.com/"));
    }

    public void assertHomePageIsDisplayed() {
        Assert.assertTrue(isHomePageVisible(), "Home page is not displayed successfully!");
    }

    public Pages.ProductsPage clickProductsButton() {
        actions.click(productsNavButton);
        return new Pages.ProductsPage(driver);
    }

    public Pages.CartPage clickCartButton() {
        ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", driver.findElement(cartNavButton));
        return new Pages.CartPage(driver);
    }

    public Pages.SignUpLoginPage clickSignupLoginButton() {
        actions.click(signupLoginNavButton);
        return new Pages.SignUpLoginPage(driver);
    }

    public void addProductToCartByIndex(int index) {
        List<WebElement> products = actions.waitForAllVisible(featuredProductWrappers);
        WebElement product = products.get(index);
        ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", product);
        WebElement addToCartBtn = product.findElement(By.cssSelector(".productinfo .add-to-cart"));
        ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", addToCartBtn);
        actions.waitForVisibility(continueShoppingButton);
        actions.click(continueShoppingButton);
    }

    public void addFirstNProductsToCart(int count) {
        for (int i = 0; i < count; i++) {
            addProductToCartByIndex(i);
        }
    }

    public Pages.TestCasesPage clickTestCases() {
        actions.click(testCasesButton);
        return new Pages.TestCasesPage(driver);
    }

    public List<WebElement> getProductCards() {
        return driver.findElements(productCards);
    }

    public List<WebElement> getProductImages() {
        return driver.findElements(productImages);
    }

    public List<WebElement> getProductNames() {
        return driver.findElements(productNames);
    }

    public List<WebElement> getProductPrices() {
        return driver.findElements(productPrices);
    }

    public void assertAllHomePageElementsAreDisplayed() {
        Assert.assertTrue(!getProductCards().isEmpty(), "Product cards are missing from Home Page!");
    }

    public void AssertProductsAreVisible() {
        Assert.assertTrue(getProductCards().size() > 0, "Products are not visible!");
    }

    public void assertAddToCartButtonsAreVisible() {
        Assert.assertTrue(driver.findElements(addToCartButtons).size() > 0, "Add to cart buttons are missing!");
    }

    public void assertViewProductButtonsAreVisible() {
        Assert.assertTrue(driver.findElements(viewProductButtons).size() > 0, "View Product buttons are missing!");
    }

    public void enterSubscriptionEmail(String email) {
        actions.type(subscriptionEmailInput, email);
    }

    public void clickSubscribe() {
        actions.click(subscribeButton);
    }

    public boolean isSubscriptionSuccessMsgDisplayed() {
        return actions.isDisplayed(successMessage);
    }

    public boolean isRecommendedItemsVisible() {
        return actions.isDisplayed(recommendedItemsTitle);
    }

    public void addRecommendedProductToCart() {
        actions.scrollToElement(recommendedItemsTitle);
        actions.clickWithJs(recommendedAddToCartBtn);
    }

    public void clickViewCartFromModal() {
        actions.click(viewCartLink);
    }

    public void clickDeleteAccountButton() {
        actions.click(deleteAccountNavButton);
    }

    public String getLoggedInUserText() {
        return actions.getText(loggedInAsText);
    }

    public void scrollToBottom() {
        ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
    }

    public void scrollToTop() {
        ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("window.scrollTo(0, 0)");
    }

    public boolean isSubscriptionTitleVisible() {
        return actions.isDisplayed(subscriptionTitle);
    }
    public boolean isHeaderTextVisible() {
        try {
            Thread.sleep(1500);
            org.openqa.selenium.JavascriptExecutor js = (org.openqa.selenium.JavascriptExecutor) driver;
            long scrollPosition = (long) js.executeScript("return window.pageYOffset;");

            if (scrollPosition <= 200) {
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
                WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(headerText));
                return element != null;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public void clickScrollUpArrow() {
        actions.clickWithJs(scrollUpArrow);
        ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("window.scrollTo(0, 0);");
    }
}