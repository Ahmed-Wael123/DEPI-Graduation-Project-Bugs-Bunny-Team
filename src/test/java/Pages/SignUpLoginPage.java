package Pages;

import Utils.ElementActions;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

public class SignUpLoginPage {

    private final WebDriver driver;
    private final ElementActions actions;

    private final By loginEmailInput = By.cssSelector("input[data-qa='login-email']");
    private final By loginPasswordInput = By.cssSelector("input[data-qa='login-password']");
    private final By loginButton = By.cssSelector("button[data-qa='login-button']");
    private final By loginErrorMessage = By.xpath("//p[contains(text(),'incorrect')]");
    private final By username_txtBox = By.xpath("//input[@data-qa='signup-name']");
    private final By email_txtBox = By.xpath("//input[@data-qa='signup-email']");
    private final By signUp_btn = By.xpath("//button[@data-qa='signup-button']");
    private final By maleGender_radioBtn = By.xpath("//label[@for='id_gender1']");
    private final By password_field = By.xpath("//input[@data-qa='password']");
    private final By days_dropdown = By.xpath("//select[@data-qa='days']");
    private final By months_dropdown = By.xpath("//select[@data-qa='months']");
    private final By years_dropdown = By.xpath("//select[@data-qa='years']");
    private final By newsletter_chkBox = By.xpath("//label[@for='newsletter']");
    private final By specialOffers_chkBox = By.xpath("//label[@for='optin']");
    private final By firstName_txtBox = By.xpath("//input[@data-qa='first_name']");
    private final By lastName_txtBox = By.xpath("//input[@data-qa='last_name']");
    private final By company_txtBox = By.xpath("//input[@data-qa='company']");
    private final By address1_txtBox = By.xpath("//input[@id='address1']");
    private final By address2_txtBox = By.xpath("//input[@id='address2']");
    private final By country_dropdown = By.xpath("//select[@id='country']");
    private final By state_txtBox = By.xpath("//input[@data-qa='state']");
    private final By city_txtBox = By.xpath("//input[@data-qa='city']");
    private final By zipcode_txtBox = By.xpath("//input[@data-qa='zipcode']");
    private final By mobileNumber_txtBox = By.xpath("//input[@data-qa='mobile_number']");
    private final By createAccount_btn = By.xpath("//button[@data-qa='create-account']");
    private final By accountCreated_header = By.xpath("//b[contains(text(),'Account Created')]");
    private final By error_msg = By.xpath("//form[@action='/signup']/p");
    private final By logout_btn = By.xpath("//a[@href='/logout']");
    private final By deleteAccount_btn = By.xpath("//a[@href='/delete_account']") ;
    private final By continueAfterCreate_btn = By.xpath("//a[@data-qa='continue-button']");
    private final By continueAfterDelete_btn = By.xpath("//a[@data-qa='continue-button']");
    private final By newUserSignup_header = By.xpath("//h2[contains(text(),'New User Signup')]");
    private final By enterAccountInfo_header = By.xpath("//b[normalize-space()='Enter Account Information']");
    private final By loggedInAsIndicator = By.xpath("//a[contains(text(),'Logged in as')]");
    private final By accountDeleted_header = By.xpath("(//b[normalize-space()='Account Deleted!'])[1]");
    private final By loginToYourAccount_header = By.xpath("//h2[contains(text(),'Login to your account')]");

    public SignUpLoginPage(WebDriver driver) {
        this.driver = driver;
        this.actions = new ElementActions(driver);
    }

    public Pages.HomePage loginUser(String email, String password) {
        actions.type(loginEmailInput, email);
        actions.type(loginPasswordInput, password);
        actions.click(loginButton);
        return new Pages.HomePage(driver);
    }

    public Pages.HomePage loginEmailAndPassword(String email, String password) {
        return loginUser(email, password);
    }

    public void signUpNameAndEmail(String name , String email){
        actions.type(username_txtBox, name);
        actions.type(email_txtBox, email);
        actions.click(signUp_btn);
    }

    public void enterAccountInfo(String password, String day, String month, String year) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", driver.findElement(maleGender_radioBtn));

        actions.type(password_field, password);

        new Select(driver.findElement(days_dropdown)).selectByValue(day);
        new Select(driver.findElement(months_dropdown)).selectByValue(month);
        new Select(driver.findElement(years_dropdown)).selectByValue(year);

        js.executeScript("arguments[0].click();", driver.findElement(newsletter_chkBox));
        js.executeScript("arguments[0].click();", driver.findElement(specialOffers_chkBox));
    }

    public void enterAddressInfo(String firstName, String lastName, String company, String address1, String address2, String country, String state, String city, String zipCode, String mobileNumber){
        actions.type(firstName_txtBox, firstName);
        actions.type(lastName_txtBox, lastName);
        actions.type(company_txtBox, company);
        actions.type(address1_txtBox, address1);
        actions.type(address2_txtBox, address2);

        new Select(driver.findElement(country_dropdown)).selectByVisibleText(country);

        actions.type(state_txtBox, state);
        actions.type(city_txtBox, city);
        actions.type(zipcode_txtBox, zipCode);
        actions.type(mobileNumber_txtBox, mobileNumber);
        actions.click(createAccount_btn);
    }

    public void deleteAccount(){
        actions.click(deleteAccount_btn);
    }

    public Pages.HomePage clickContinueAfterAccountCreated(){
        actions.click(continueAfterCreate_btn);
        try {
            actions.waitForVisibility(logout_btn);
        } catch (Exception e) {
            driver.navigate().to("https://automationexercise.com/");
        }
        return new Pages.HomePage(driver);
    }

    public Pages.HomePage clickContinueAfterAccountDeleted(){
        actions.click(continueAfterDelete_btn);
        return new Pages.HomePage(driver);
    }

    public Pages.SignUpLoginPage clickLogoutBtn(){
        actions.click(logout_btn);
        return this;
    }

    public boolean isLoggedInSuccessfully() {
        return actions.isDisplayed(loggedInAsIndicator);
    }

    public boolean isLoginErrorVisible() {
        return actions.isDisplayed(loginErrorMessage);
    }

    public boolean isSubscriptionSuccessMsgDisplayed() {
        return actions.isDisplayed(error_msg);
    }

    public void assertNewUserSignupIsDisplayed() {
        Assert.assertTrue(actions.isDisplayed(newUserSignup_header), "New User Signup header is not displayed!");
    }

    public void assertEnterAccountInfoIsDisplayed() {
        Assert.assertTrue(actions.isDisplayed(enterAccountInfo_header), "Enter Account Information header is not displayed!");
    }

    public void assertAccountCreatedIsDisplayed() {
        Assert.assertTrue(actions.isDisplayed(accountCreated_header), "Account Created header is not displayed!");
    }

    public void assertLoggedInAsIsDisplayed() {
        Assert.assertTrue(actions.isDisplayed(loggedInAsIndicator), "'Logged in as' header is not displayed!");
    }

    public void assertAccountDeletedIsDisplayed() {
        Assert.assertTrue(actions.isDisplayed(accountDeleted_header), "Account Deleted header is not displayed!");
    }

    public void assertErrorMsgIsDisplayed() {
        Assert.assertTrue(actions.isDisplayed(error_msg), "Error message is not displayed!");
    }

    public void assertLoginToYourAccountIsDisplayed() {
        Assert.assertTrue(actions.isDisplayed(loginToYourAccount_header), "Login to your account header is not displayed!");
    }

    public void assertLogoutBtnIsDisplayed(){
        Assert.assertTrue(actions.isDisplayed(logout_btn), "Logout button is not displayed!");
    }

    public void assertEmailErrorMissingMsg(){
        JavascriptExecutor js = (JavascriptExecutor) driver;
        String Message = js.executeScript("return document.querySelector('[data-qa=\"signup-email\"]').validationMessage;").toString();
        Assert.assertEquals(Message, "Please fill out this field.", "Email error message is not displayed!");
        System.out.println(Message);
    }


    public void registerNewUser(String randomName, String randomEmail, String password) {
        signUpNameAndEmail(randomName, randomEmail);
        assertEnterAccountInfoIsDisplayed();
        enterAccountInfo(password, "10", "5", "2000");
        enterAddressInfo(randomName, "User", "Company", "Street 1", "Street 2", "India", "State", "City", "12345", "0123456789");
        assertAccountCreatedIsDisplayed();
        clickContinueAfterAccountCreated();
        clickLogoutBtn();
    }
}