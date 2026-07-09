package Tests;

import Driver.DriverFactory;
import Pages.HomePage;
import TestData.AppData;
import Utils.ScreenShotHandler;
import Utils.AdHandler;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

public class BaseTest {
    protected WebDriver driver;
    protected HomePage homePage;

    @Parameters({"browser", "environment"})
    @BeforeMethod(alwaysRun = true)
    public void setUp(@Optional("edge") String browser, @Optional("local") String environment) {
        DriverFactory.initDriver(browser, environment);
        driver = DriverFactory.getDriver();
        driver.get(AppData.BASE_URL);
        homePage = new HomePage(driver);
    }

    @BeforeMethod(alwaysRun = true, dependsOnMethods = "setUp")
    public void handleAds() {
        try {
            String url = driver.getCurrentUrl();
            if (url != null && !url.equals("data:,") && !url.equals("about:blank")) {
                AdHandler.dismissAdIfPresent(driver);
            }
        } catch (Exception ignored) {
        }
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {
        try {
            if (result != null && result.getStatus() == ITestResult.FAILURE) {
                ScreenShotHandler.capture(driver, result.getName());
            }
        } catch (Exception e) {
            System.out.println("Error taking screenshot: " + e.getMessage());
        } finally {
            DriverFactory.quitDriver();
            try {
                Thread.sleep(3000);
            } catch (InterruptedException ignored) {}
        }
    }
}