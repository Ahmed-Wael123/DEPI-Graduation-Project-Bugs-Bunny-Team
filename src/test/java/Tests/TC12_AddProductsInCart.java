package Tests;

import Pages.CartPage;
import Pages.ProductsPage;
import Utils.AdHandler;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.time.Duration;

public class TC12_AddProductsInCart extends BaseTest {

    @Test
    public void verifyAddProductsInCart() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        try {
            js.executeScript("arguments[0].click();", driver.findElement(By.xpath("//a[@href='/products']")));
        } catch (Exception e) {
            homePage.clickProductsButton();
        }
        AdHandler.dismissAdIfPresent(driver);

        ProductsPage productsPage = new ProductsPage(driver);

        try {
            js.executeScript("arguments[0].click();", driver.findElement(By.xpath("(//a[@data-product-id='1'])[1]")));
        } catch (Exception e) {
            driver.findElement(By.xpath("(//a[@data-product-id='1'])[1]")).click();
        }

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("cartModal")));

        try {
            js.executeScript("arguments[0].click();", driver.findElement(By.xpath("//button[normalize-space()='Continue Shopping']")));
        } catch (Exception e) {
            driver.findElement(By.xpath("//button[normalize-space()='Continue Shopping']")).click();
        }

        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("cartModal")));

        try {
            js.executeScript("arguments[0].click();", driver.findElement(By.xpath("(//a[@data-product-id='2'])[1]")));
        } catch (Exception e) {
            driver.findElement(By.xpath("(//a[@data-product-id='2'])[1]")).click();
        }

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("cartModal")));

        try {
            js.executeScript("arguments[0].click();", driver.findElement(By.xpath("//u[normalize-space()='View Cart']")));
        } catch (Exception e) {
            driver.findElement(By.xpath("//u[normalize-space()='View Cart']")).click();
        }
        AdHandler.dismissAdIfPresent(driver);

        CartPage cartPage = new CartPage(driver);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("cart_info_table")));

        Assert.assertEquals(cartPage.getCartItemsCount(), 2, "Products count in cart is mismatch!");
    }
}