import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class LogInTests {

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

    public void logIn(String user, String password) {
        WebElement myAccountMenuItem = driverChrome.findElement(By.id("menu-item-125"));
        myAccountMenuItem.click();

        WebElement inputUserName = driverChrome.findElement(By.xpath("//input[@id='username']"));
        inputUserName.sendKeys(user);

        WebElement inputPassword = driverChrome.findElement(By.xpath("//input[@id='password']"));
        inputPassword.sendKeys(password);

        WebElement buttonLogIn = driverChrome.findElement(By.cssSelector("button[name='login']"));
        buttonLogIn.click();
    }

    @Test
    void shouldVerifyNegativeLogInWithoutLogin() {
        logIn("", "!PPSVXSnMB1");
        WebElement errorMessageMissingPassword = driverChrome.findElement(By.className("woocommerce-error"));
        String expectedErrorMessageMissingPassword =
                "Błąd: Nazwa użytkownika jest wymagana.";
        Assertions.assertEquals(expectedErrorMessageMissingPassword, errorMessageMissingPassword.getText());
    }

    @Test
    void shouldVerifyNegativeLogInWithoutPassword() {
        logIn("marikatest@yopmail.com", "");
        WebElement errorMessageMissingPassword = driverChrome.findElement(By.className("woocommerce-error"));
        String expectedErrorMessageMissingPassword =
                "Błąd: pole hasła jest puste.";

        Assertions.assertEquals(expectedErrorMessageMissingPassword, errorMessageMissingPassword.getText());
    }

    @Test
    void shouldVerifyNegativeLoginWithWrongPassword() {
        WebElement myAccountMenuItem = driverChrome.findElement(By.id("menu-item-125"));
        myAccountMenuItem.click();

        WebElement inputUserName = driverChrome.findElement(By.xpath("//input[@id='username']"));
        inputUserName.sendKeys("marikatest@yopmail.com");

        WebElement inputPassword = driverChrome.findElement(By.xpath("//input[@id='password']"));
        inputPassword.sendKeys("!PPSVXSnMB1.");

        WebElement buttonLogIn = driverChrome.findElement(By.cssSelector("button[name='login']"));
        buttonLogIn.click();

        WebElement errorMessageWrongPassword = driverChrome.findElement(By.className("woocommerce-error"));
        String expectedErrorMessageWrongPassword =
                "Błąd: dla adresu e-mail marikatest@yopmail.com podano nieprawidłowe hasło. Nie pamiętasz hasła?";

        Assertions.assertEquals(expectedErrorMessageWrongPassword, errorMessageWrongPassword.getText());

    }

}




