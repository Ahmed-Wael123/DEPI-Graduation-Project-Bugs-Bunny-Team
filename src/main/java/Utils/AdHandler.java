package Utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.List;

public class AdHandler {

    public static void dismissAdIfPresent(WebDriver driver) {
        try {
            String currentUrl = driver.getCurrentUrl();
            if (currentUrl.contains("google_vignette") || currentUrl.contains("#google_vignette")) {
                driver.navigate().refresh();
                return;
            }

            List<WebElement> iframes = driver.findElements(By.tagName("iframe"));
            for (WebElement iframe : iframes) {
                String id = iframe.getAttribute("id");
                if (id != null && (id.contains("aswift") || id.contains("google_ads"))) {
                    driver.switchTo().frame(iframe);
                    List<WebElement> dismissButtons = driver.findElements(By.id("dismiss-button"));
                    if (!dismissButtons.isEmpty()) {
                        dismissButtons.get(0).click();
                        driver.switchTo().defaultContent();
                        break;
                    }
                    driver.switchTo().defaultContent();
                }
            }
        } catch (Exception ignored) {
            try {
                driver.switchTo().defaultContent();
            } catch (Exception ignored2) {}
        }
    }
}