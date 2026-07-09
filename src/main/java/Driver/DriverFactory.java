package Driver;

import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class DriverFactory {

    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    private DriverFactory() {
        // utility class, no instances
    }

    public static void initDriver(String browserName, String executionEnvironment) {
        WebDriver webDriver = null;

        if (browserName == null) {
            browserName = "edge";
        }
        if (executionEnvironment == null) {
            executionEnvironment = "local";
        }

        String env = executionEnvironment.trim().toLowerCase();
        String browser = browserName.trim().toLowerCase();

        try {
            if (env.equals("remote")) {
                URL gridUrl = new URL("http://localhost:4444/wd/hub");

                switch (browser) {
                    case "firefox":
                        FirefoxOptions firefoxOptions = new FirefoxOptions();
                        firefoxOptions.setPageLoadStrategy(PageLoadStrategy.EAGER);
                        webDriver = new RemoteWebDriver(gridUrl, firefoxOptions);
                        break;
                    case "chrome":
                        ChromeOptions chromeOptions = getChromeOptions();
                        webDriver = new RemoteWebDriver(gridUrl, chromeOptions);
                        break;
                    case "edge":
                    default:
                        EdgeOptions edgeOptions = getEdgeOptions();
                        webDriver = new RemoteWebDriver(gridUrl, edgeOptions);
                        break;
                }
            } else {
                switch (browser) {
                    case "firefox":
                        FirefoxOptions firefoxOptions = new FirefoxOptions();
                        firefoxOptions.setPageLoadStrategy(PageLoadStrategy.EAGER);
                        webDriver = new FirefoxDriver(firefoxOptions);
                        break;
                    case "chrome":
                        webDriver = new ChromeDriver(getChromeOptions());
                        break;
                    case "edge":
                    default:
                        webDriver = new EdgeDriver(getEdgeOptions());
                        break;
                }
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Grid URL is invalid: " + e.getMessage());
        }

        webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        webDriver.manage().window().maximize();
        driver.set(webDriver);
    }

    private static EdgeOptions getEdgeOptions() {
        EdgeOptions options = new EdgeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-popup-blocking");
        options.addArguments("--disable-blink-features=AutomationControlled");
        options.setPageLoadStrategy(PageLoadStrategy.EAGER);

        return options;
    }

    private static ChromeOptions getChromeOptions() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-popup-blocking");
        options.addArguments("--disable-blink-features=AutomationControlled");
        options.setPageLoadStrategy(PageLoadStrategy.EAGER);

        return options;
    }

    public static WebDriver getDriver() {
        return driver.get();
    }

    public static void quitDriver() {
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove();
        }
    }
}