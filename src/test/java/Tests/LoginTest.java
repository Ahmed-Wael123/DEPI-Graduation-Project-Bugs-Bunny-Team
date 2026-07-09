package Tests;

import com.github.javafaker.Faker;
import jdk.jfr.Description;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.annotations.Test;
import Pages.SignUpLoginPage;
import Utils.AdHandler;

public class LoginTest extends BaseTest {

    @Test(priority = 2)
    @Description("Validate that user can login with correct email and password ---> TestCase '3'")
    public void validateThatUserCanLoginWithCorrectEmailAndPassword() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        try {
            js.executeScript("arguments[0].click();", driver.findElement(By.xpath("//a[@href='/login']")));
        } catch (Exception e) {
            homePage.clickSignupLoginButton();
        }
        AdHandler.dismissAdIfPresent(driver);

        SignUpLoginPage signUpLogInPage = new SignUpLoginPage(driver);
        signUpLogInPage.assertLoginToYourAccountIsDisplayed();

        signUpLogInPage.loginEmailAndPassword("hana@hh", "11111");
        signUpLogInPage.assertLoggedInAsIsDisplayed();
    }

    @Test(priority = 3)
    @Description("Validate that user can Delete an existing email ---> TestCase '4'")
    public void validateThatUserCanDeleteAnExistingEmail() {
        Faker faker = new Faker();
        String randomName = faker.name().firstName();
        String randomEmail = faker.internet().safeEmailAddress();
        String randomPassword = "Password123";

        JavascriptExecutor js = (JavascriptExecutor) driver;
        try {
            js.executeScript("arguments[0].click();", driver.findElement(By.xpath("//a[@href='/login']")));
        } catch (Exception e) {
            homePage.clickSignupLoginButton();
        }
        AdHandler.dismissAdIfPresent(driver);

        SignUpLoginPage signUpLogInPage = new SignUpLoginPage(driver);

        signUpLogInPage.registerNewUser(randomName, randomEmail, randomPassword);

        try {
            js.executeScript("arguments[0].click();", driver.findElement(By.xpath("//a[@href='/login']")));
        } catch (Exception e) {
            homePage.clickSignupLoginButton();
        }
        AdHandler.dismissAdIfPresent(driver);

        signUpLogInPage.loginEmailAndPassword(randomEmail, randomPassword);
        signUpLogInPage.assertLoggedInAsIsDisplayed();

        signUpLogInPage.deleteAccount();
        AdHandler.dismissAdIfPresent(driver);

        signUpLogInPage.assertAccountDeletedIsDisplayed();
        signUpLogInPage.clickContinueAfterAccountDeleted();
    }
}