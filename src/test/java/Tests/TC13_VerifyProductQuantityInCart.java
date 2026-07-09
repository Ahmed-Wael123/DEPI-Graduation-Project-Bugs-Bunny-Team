package Tests;

import Pages.CartPage;
import Pages.ProductDetailsPage;
import Utils.AdHandler;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.time.Duration;

public class TC13_VerifyProductQuantityInCart extends BaseTest {

    @Test
    public void verifyProductQuantityInCart() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            js.executeScript("arguments[0].click();", driver.findElement(By.xpath("//a[contains(@href, '/product_details/1')]")));
        } catch (Exception e) {
            driver.findElement(By.xpath("//a[contains(@href, '/product_details/1')]")).click();
        }
        AdHandler.dismissAdIfPresent(driver);

        ProductDetailsPage detailsPage = new ProductDetailsPage(driver);
        driver.findElement(By.id("quantity")).clear();
        driver.findElement(By.id("quantity")).sendKeys("4");

        driver.findElement(By.xpath("//button[normalize-space()='Add to cart']")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("cartModal")));

        try {
            js.executeScript("arguments[0].click();", driver.findElement(By.xpath("//u[normalize-space()='View Cart']")));
        } catch (Exception e) {
            driver.findElement(By.xpath("//u[normalize-space()='View Cart']")).click();
        }
        AdHandler.dismissAdIfPresent(driver);

        CartPage cartPage = new CartPage(driver);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("cart_info_table")));

        Assert.assertEquals(cartPage.getSingleItemQuantity(), "4", "Product quantity in cart is incorrect!");
    }
}