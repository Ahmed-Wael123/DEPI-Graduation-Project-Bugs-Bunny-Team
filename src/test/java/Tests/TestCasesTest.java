package Tests;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;
import org.testng.annotations.Test;
import Pages.TestCasesPage;
import Utils.AdHandler;

public class TestCasesTest extends BaseTest {

    @Test
    public void verifyTestCasesPage() {
        JavascriptExecutor js = (JavascriptExecutor) driver;

        try {
            js.executeScript("arguments[0].click();", driver.findElement(By.xpath("//a[@href='/test_cases']")));
        } catch (Exception e) {
            homePage.clickTestCases();
        }
        AdHandler.dismissAdIfPresent(driver);

        TestCasesPage testCasesPage = new TestCasesPage(driver);
        Assert.assertTrue(testCasesPage.isTestCasesPageDisplayed(), "Test Cases page is not displayed!");
    }
}