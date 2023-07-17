import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import java.util.List;

public class MainPageTests {

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

    private void checkPresenceOfLogoAndSearchBarOnMainPage() {

        String shopName = "Softie Metal Shop\nSklep z metalami szlachetnymi";
        WebElement logo = driverChrome.findElement(By.cssSelector(".site-header .site-branding"));
        Assertions.assertEquals(shopName, logo.getText());

        List<WebElement> elements = driverChrome.findElements(By.id("woocommerce-product-search-field-0"));
        Assertions.assertTrue(!elements.isEmpty());

    }

    @Test
    public void shouldVerifyPresenceOfLogoAndSearchBarOnMainPage() {
        checkPresenceOfLogoAndSearchBarOnMainPage();
    }

    @Test
    public void shouldVerifyPresenceOfLogoAndSearchBarOnLogInPage() {
        driverChrome.navigate().to
                ("http://serwer169007.lh.pl/autoinstalator/serwer169007.lh.pl/wordpress10772/moje-konto/");
        checkPresenceOfLogoAndSearchBarOnMainPage();
    }

    @Test
    public void shouldVerifyTransitionFromMainPageToContactPage() {
        WebElement contactMenuItem = driverChrome.findElement(By.id("menu-item-132"));
        contactMenuItem.click();

//  Probowalam zrobic po breadcrumps, ale niestety nie dziala.

//      List<WebElement> breadcrumbs = driverChrome.findElements(By.cssSelector("woocommerce-breadcrumb"));
//      List<String> expected = Arrays.asList("Strona główna", "Kontakt");
//        for (int i = 0; i < 2; i++) {
//            String breadcrumb = breadcrumbs.get(i).getText();
//            if (breadcrumb.equals(expected.get(i))) {
//                System.out.println("passed on: " + breadcrumb);
//            } else {
//                System.out.println("failed on: " + breadcrumb);
//            }
//          }
//       WebElement breadcrumbsContact = (WebElement) driverChrome.findElement(By.cssSelector("woocommerce-breadcrumb"));
//       String expectedBreadCrumbsContact = "Strona główna Kontakt";
//       Assertions.assertEquals(expectedBreadCrumbsContact, breadcrumbsContact.getText());


        WebElement contactEntryTitle = driverChrome.findElement(By.className("entry-title"));
        String expectedContactEntryTitle = "Kontakt";

        Assertions.assertEquals(expectedContactEntryTitle, contactEntryTitle.getText());

    }

    @Test
    public void shouldVerifyReturnFromLogiInPageToMainPage() {
        driverChrome.navigate().to
                ("http://serwer169007.lh.pl/autoinstalator/serwer169007.lh.pl/wordpress10772/moje-konto/");

        WebElement mainPage = driverChrome.findElement(By.linkText("Strona główna"));
        mainPage.click();

        WebElement shopTitle = driverChrome.findElement(By.className("woocommerce-products-header"));
        String expectedShopMessage = "Sklep";
        Assertions.assertEquals(expectedShopMessage, shopTitle.getText());
    }

}
