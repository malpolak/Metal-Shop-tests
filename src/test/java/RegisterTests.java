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

public class RegisterTests {


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
    void cleanCookiesAfterTest() {driverChrome.manage().deleteAllCookies();
    }

    @AfterAll
    static void closeBrowser() {driverChrome.quit();}

    @Test
    public void shouldVerifyPositiveExampleOfNewUserRegistration() {

        long timestamp = System.currentTimeMillis();

        WebElement myRegisterMenuItem = driverChrome.findElement(By.id("menu-item-146"));
        myRegisterMenuItem.click();

        String newUser = timestamp + "marika";

        WebElement inputNewUserName = driverChrome.findElement(By.xpath("//input[@id='user_login']"));
        inputNewUserName.sendKeys(newUser);

        WebElement inputNewUserEmail = driverChrome.findElement(By.xpath("//input[@id='user_email']"));
        inputNewUserEmail.sendKeys(timestamp + "marikatest@yopmail.com");

        WebElement inputNewUserPassword = driverChrome.findElement(By.xpath("//input[@id='user_pass']"));
        inputNewUserPassword.sendKeys("!PPSVXSnMB1");

        WebElement inputNewUserConfirmPassword =
                driverChrome.findElement(By.xpath("//input[@id='user_confirm_password']"));
        inputNewUserConfirmPassword.sendKeys("!PPSVXSnMB1");

        WebElement buttonSubmit = driverChrome.findElement(By.className("ur-submit-button"));
        buttonSubmit.click();

        Wait wait = new WebDriverWait(driverChrome, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ur-submit-message-node")));

        WebElement positiveRegistrationMessage = driverChrome.findElement(By.id("ur-submit-message-node"));

        String expectedPositiveRegistrationMessage = "User successfully registered.";

        Assertions.assertEquals(expectedPositiveRegistrationMessage, positiveRegistrationMessage.getText());


    }
}