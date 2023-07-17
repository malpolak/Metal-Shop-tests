import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ContactTests {
    static WebDriver driverChrome;

    @BeforeAll
    static void prepareBrowser() {
        System.setProperty("webdriver.http.factory", "jdk-http-client");
        WebDriverManager.chromedriver().setup();
        driverChrome = new ChromeDriver();
        driverChrome.manage().window().maximize();
        driverChrome.get("http://serwer169007.lh.pl/autoinstalator/serwer169007.lh.pl/wordpress10772/");
    }

    @BeforeEach
    void cleanCookies() {
        driverChrome.manage().deleteAllCookies();
    }

    @AfterEach
    void cleanCookiesAfterTest() {
        driverChrome.manage().deleteAllCookies();
    }

    @AfterAll
    static void closeBrowser() {
        driverChrome.quit();
    }

    @Test
    public void shouldVerifyPresenceOfLogoAndSearchBarOnLogInPage() {
        driverChrome.navigate().to
                ("http://serwer169007.lh.pl/autoinstalator/serwer169007.lh.pl/wordpress10772/kontakt/");

        WebElement inputYourName = driverChrome.findElement(By.xpath("//input[@name='your-name']"));
        inputYourName.sendKeys("Marika");

        WebElement inputYourEmail = driverChrome.findElement(By.xpath("//input[@name='your-email']"));
        inputYourEmail.sendKeys("marikatest@yopmail.com");

        WebElement inputYourSubject = driverChrome.findElement(By.xpath("//input[@name='your-subject']"));
        inputYourEmail.sendKeys("Test");

        WebElement inputYourMessage = driverChrome.findElement(By.xpath("//textarea[@name='your-message']"));
        inputYourEmail.sendKeys("Test");

        WebElement buttonSend = driverChrome.findElement(By.cssSelector(".wpcf7-submit"));
        buttonSend.click();

        Wait wait = new WebDriverWait(driverChrome, Duration.ofSeconds(15));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("wpcf7-response-output")));

        WebElement errorMessage = driverChrome.findElement(By.className("wpcf7-response-output"));

        String expectedErrorMessage = "Wystąpił problem z wysłaniem twojej wiadomości. Spróbuj ponownie później.";

        Assertions.assertEquals(expectedErrorMessage, errorMessage.getText());


    }


}
