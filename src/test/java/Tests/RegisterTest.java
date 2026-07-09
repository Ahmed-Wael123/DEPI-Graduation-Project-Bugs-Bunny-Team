package Tests;

import com.github.javafaker.Faker;
import jdk.jfr.Description;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import Pages.SignUpLoginPage;
import Utils.AdHandler;

public class RegisterTest extends BaseTest {

    Faker faker = new Faker();
    String randomEmail;

    @BeforeMethod
    public void setup() {
        randomEmail = faker.internet().emailAddress();
    }

    @Test(priority = 1)
    @Description("Verify user can Register  ---> TestCase '1' ")
    public void validateThatUserCanRegister() {
        homePage.assertHomePageIsDisplayed();

        SignUpLoginPage signUpPage = homePage.clickSignupLoginButton();
        AdHandler.dismissAdIfPresent(driver);

        signUpPage.assertNewUserSignupIsDisplayed();
        signUpPage.signUpNameAndEmail("Hana", randomEmail);
        signUpPage.assertEnterAccountInfoIsDisplayed();
        signUpPage.enterAccountInfo("123456789", "3", "5", "2007");
        signUpPage.enterAddressInfo("Hana", "Habib", "Company", "Address 1", "Address 2", "Canada", "Ontario", "NewYork", "12345", "1234567890");
        signUpPage.assertAccountCreatedIsDisplayed();

        signUpPage.clickContinueAfterAccountCreated();
        AdHandler.dismissAdIfPresent(driver);

        signUpPage.assertLogoutBtnIsDisplayed();
        signUpPage.clickLogoutBtn();
    }

    @Test(priority = 4)
    @Description("Validate that user cannot create an account with existing email ---> TestCase '2' ")
    public void validateThatUserCannotCreateAnAccountWithExistingEmail() {
        SignUpLoginPage signUpPage = homePage.clickSignupLoginButton();
        AdHandler.dismissAdIfPresent(driver);

        signUpPage.assertNewUserSignupIsDisplayed();
        signUpPage.signUpNameAndEmail("Hana", "h@gmail");
        signUpPage.assertErrorMsgIsDisplayed();
    }

    @Test(priority = 5)
    @Description("validate that user cannot access account info page without Entering an email")
    public void validateThatUserCannotAccessAccountInfoPageWithoutEnteringAnEmail() {
        homePage.assertHomePageIsDisplayed();
        SignUpLoginPage signUpPage = homePage.clickSignupLoginButton();
        AdHandler.dismissAdIfPresent(driver);

        signUpPage.assertNewUserSignupIsDisplayed();
        signUpPage.signUpNameAndEmail("Hana", "");
        signUpPage.assertEmailErrorMissingMsg();
    }
}