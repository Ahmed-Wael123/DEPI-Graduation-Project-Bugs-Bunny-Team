package Utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ScreenShotHandler {

    private static final String SCREENSHOTS_DIR = "screenshots";

    public static String capture(WebDriver driver, String testName) {
        try {
            Files.createDirectories(Paths.get(SCREENSHOTS_DIR));

            String timestamp = LocalDateTime.now()
                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));

            String fileName = testName + "_" + timestamp + ".png";
            File source = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            File destination = new File(SCREENSHOTS_DIR + File.separator + fileName);

            Files.copy(source.toPath(), destination.toPath());
            return destination.getAbsolutePath();

        } catch (IOException e) {
            System.out.println("Could not save screenshot: " + e.getMessage());
            return null;
        }
    }
}
