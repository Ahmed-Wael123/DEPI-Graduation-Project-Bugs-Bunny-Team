package Pages;

import Utils.ElementActions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

public class RegisterPage {
    private final WebDriver driver;
    private final ElementActions actions;

    private final By genderRadio = By.id("id_gender1");
    private final By passwordInput = By.id("password");
    private final By daysSelect = By.id("days");
    private final By monthsSelect = By.id("months");
    private final By yearsSelect = By.id("years");
    private final By firstNameInput = By.id("first_name");
    private final By lastNameInput = By.id("last_name");
    private final By addressInput = By.id("address1");
    private final By countrySelect = By.id("country");
    private final By stateInput = By.id("state");
    private final By cityInput = By.id("city");
    private final By zipcodeField = By.id("zipcode");
    private final By mobileInput = By.id("mobile_number");
    private final By createAccountBtn = By.xpath("//button[@data-qa='create-account']");

    public RegisterPage(WebDriver driver) {
        this.driver = driver;
        this.actions = new ElementActions(driver);
    }

    public AccountPage registerAccount(String password, String name) {
        actions.click(genderRadio);
        actions.type(passwordInput, password);

        new Select(driver.findElement(daysSelect)).selectByVisibleText("15");
        new Select(driver.findElement(monthsSelect)).selectByVisibleText("July");
        new Select(driver.findElement(yearsSelect)).selectByVisibleText("1998");

        actions.type(firstNameInput, name);
        actions.type(lastNameInput, "Mohamed");
        actions.type(addressInput, "123 Test Street");

        new Select(driver.findElement(countrySelect)).selectByVisibleText("United States");

        actions.type(stateInput, "Dakahlia");
        actions.type(cityInput, "Mansoura");
        actions.type(zipcodeField, "35511");
        actions.type(mobileInput, "01000000001");

        actions.clickWithJs(createAccountBtn);
        return new AccountPage(driver);
    }
}