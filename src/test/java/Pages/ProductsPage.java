package Pages;

import Utils.ElementActions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.List;

public class ProductsPage {

    private final WebDriver driver;
    private final ElementActions actions;
    private final By allProductsTitle = By.xpath("//h2[@class='title text-center']");
    private final By searchInput = By.id("search_product");
    private final By searchButton = By.id("submit_search");
    private final By searchedProductsTitle = By.xpath("//h2[@class='title text-center']");
    private final By productItems = By.cssSelector(".features_items .product-image-wrapper");

    // Categories sidebar
    private final By womenCategoryLink = By.xpath("//a[@href='#Women']");
    private final By menCategoryLink = By.xpath("//a[@href='#Men']");
    private final By categoryPageTitle = By.cssSelector(".features_items h2.title");

    // Brands sidebar
    private final By brandsHeader = By.xpath("//h2[normalize-space()='Brands']");
    private final By brandsLinks = By.cssSelector(".brands_products .brands-name ul li a");
    private final By continueShoppingButton = By.cssSelector(".btn-success.close-modal");

    private final By firstProductViewButton = By.xpath("(//a[contains(text(),'View Product')])[1]");

    public ProductsPage(WebDriver driver) {
        this.driver = driver;
        this.actions = new ElementActions(driver);
    }

    public boolean isAllProductsPageVisible() {
        try {
            new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(10))
                    .until(org.openqa.selenium.support.ui.ExpectedConditions.urlContains("/products"));

            actions.waitForVisibility(allProductsTitle);
            return actions.getText(allProductsTitle).equalsIgnoreCase("All Products");
        } catch (Exception e) {
            System.out.println("Error verifying All Products page: " + e.getMessage());
            return false;
        }
    }

    // ---------- Search Products and Verify Cart After Login (Test Case 20) ----------
    public void searchProduct(String productName) {
        actions.type(searchInput, productName);
        actions.click(searchButton);
    }

    public boolean isSearchedProductsTitleVisible() {
        return actions.isDisplayed(searchedProductsTitle)
                && actions.getText(searchedProductsTitle).equalsIgnoreCase("Searched Products");
    }

    public List<WebElement> getVisibleProducts() {
        return actions.waitForAllVisible(productItems);
    }

    public int getProductsCount() {
        return getVisibleProducts().size();
    }

    public void addProductToCartByIndex(int index) {
        List<WebElement> products = getVisibleProducts();
        WebElement product = products.get(index);

        ((org.openqa.selenium.JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView({block:'center'});", product);

        WebElement addToCartBtn = product.findElement(By.cssSelector(".productinfo .add-to-cart"));

        ((org.openqa.selenium.JavascriptExecutor) driver)
                .executeScript("arguments[0].click();", addToCartBtn);

        actions.waitForVisibility(continueShoppingButton);
        actions.click(continueShoppingButton);
    }

    public void addAllVisibleProductsToCart() {
        int count = getProductsCount();
        for (int i = 0; i < count; i++) {
            addProductToCartByIndex(i);
        }
    }

    // ---------- View Category Products (Test Case 18) ----------
    public boolean areCategoriesVisibleOnSidebar() {
        return actions.isDisplayed(womenCategoryLink);
    }

    public Pages.ProductsPage clickWomenCategory() {
        WebElement element = driver.findElement(womenCategoryLink);
        ((org.openqa.selenium.JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView({block:'center'});", element);
        ((org.openqa.selenium.JavascriptExecutor) driver)
                .executeScript("arguments[0].click();", element);
        return this;
    }

    public Pages.ProductsPage clickSubCategory(String subCategoryName) {
        By subCategoryLocator = By.xpath(
                "//div[@id='Women']//a[contains(normalize-space(),'" + subCategoryName + "')]");
        WebElement element = driver.findElement(subCategoryLocator);
        ((org.openqa.selenium.JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView({block:'center'});", element);
        ((org.openqa.selenium.JavascriptExecutor) driver)
                .executeScript("arguments[0].click();", element);
        return new Pages.ProductsPage(driver);
    }

    public Pages.ProductsPage clickMenCategory() {
        new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(10))
                .until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(menCategoryLink));

        WebElement element = driver.findElement(menCategoryLink);
        ((org.openqa.selenium.JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView({block:'center'});", element);
        ((org.openqa.selenium.JavascriptExecutor) driver)
                .executeScript("arguments[0].click();", element);

        try {
            ((org.openqa.selenium.JavascriptExecutor) driver)
                    .executeScript("document.getElementById('Men').classList.add('in');");
        } catch (Exception e) {
        }
        return this;
    }

    public Pages.ProductsPage clickMenSubCategoryByIndex(int index) {
        By menSubCategoriesLocator = By.xpath("//div[@id='Men']//ul/li/a");

        new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(10))
                .until(org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated(menSubCategoriesLocator));

        List<WebElement> menSubCategories = driver.findElements(menSubCategoriesLocator);
        WebElement element = menSubCategories.get(index);

        ((org.openqa.selenium.JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView({block:'center'});", element);
        ((org.openqa.selenium.JavascriptExecutor) driver)
                .executeScript("arguments[0].click();", element);
        return new Pages.ProductsPage(driver);
    }

    public String getCategoryPageTitleText() {
        return actions.getText(categoryPageTitle);
    }

    // ---------- View & Cart Brand Products (Test Case 19) ----------
    public boolean areBrandsVisibleOnSidebar() {
        try {
            new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(10))
                    .until(org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated(brandsHeader));
            return actions.isDisplayed(brandsHeader);
        } catch (Exception e) {
            System.out.println("Remote Error: Brands sidebar header did not appear in time. " + e.getMessage());
            return false;
        }
    }

    public List<WebElement> getBrandLinks() {
        return actions.waitForAllVisible(brandsLinks);
    }

    public Pages.ProductsPage clickBrandByIndex(int index) {
        WebElement brandLink = getBrandLinks().get(index);

        ((org.openqa.selenium.JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView({block:'center'});", brandLink);

        ((org.openqa.selenium.JavascriptExecutor) driver)
                .executeScript("arguments[0].click();", brandLink);

        return new Pages.ProductsPage(driver);
    }

    public String getBrandLinkText(int index) {
        String rawText = getBrandLinks().get(index).getText().trim();
        return rawText.replaceAll("[^a-zA-Z& ]", "").trim();
    }

    // ---------- Product details navigation (Test Case 21) ----------
    public Pages.ProductDetailsPage clickViewProductByIndex(int index) {
        List<WebElement> products = getVisibleProducts();
        WebElement product = products.get(index);

        ((org.openqa.selenium.JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView({block:'center'});", product);

        WebElement viewProductLink = product.findElement(By.xpath(".//a[text()='View Product']"));
        ((org.openqa.selenium.JavascriptExecutor) driver)
                .executeScript("arguments[0].click();", viewProductLink);

        return new Pages.ProductDetailsPage(driver);
    }

    public boolean areProductsDisplayed() {
        return getProductsCount() > 0;
    }

    public boolean isAllProductsPageDisplayed() {
        return isAllProductsPageVisible();
    }

    public void clickFirstProductView() {
        clickViewProductByIndex(0);
    }

    public void searchForProduct(String productName) {
        searchProduct(productName);
    }

    public boolean isSearchedProductsTitleDisplayed() {
        return isSearchedProductsTitleVisible();
    }
}