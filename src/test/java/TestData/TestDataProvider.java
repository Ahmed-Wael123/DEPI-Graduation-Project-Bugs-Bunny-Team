package TestData;

import org.testng.annotations.DataProvider;

public class TestDataProvider {


    @DataProvider(name = "searchKeywords")
    public Object[][] searchKeywords() {
        return new Object[][]{
                {"Top"},
                {"Dress"},
                {"Jean"}
        };
    }

    @DataProvider(name = "loginData")
    public Object[][] getLoginData(){
        return new Object[][]{
                {"hana@hh", "123456789"}
        };
    }
}