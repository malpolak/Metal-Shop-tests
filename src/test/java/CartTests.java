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
import java.util.List;

public class CartTests {

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
    static void closeBrowser() {driverChrome.quit(); }

    public void addToCart() {

        WebElement productSrebrnaMoneta5g = driverChrome.findElement(By.xpath("//a[@data-product_id='24']"));
        productSrebrnaMoneta5g.click();

    }

    @Test
    public void shouldVerifyAddingProductToCartAfterCheckingIfCartIsEmpty() {

        driverChrome.navigate().to
                ("http://serwer169007.lh.pl/autoinstalator/serwer169007.lh.pl/wordpress10772/koszyk/");

        WebElement emptyCartMessage = driverChrome.findElement(By.className("cart-empty"));
        String expectedEmptyCartMessage = "Twój koszyk aktualnie jest pusty.";
        Assertions.assertEquals(expectedEmptyCartMessage, emptyCartMessage.getText());
        WebElement mainPage = driverChrome.findElement(By.linkText("Strona główna"));
        mainPage.click();
        addToCart();

        Wait waitCart = new WebDriverWait(driverChrome, Duration.ofSeconds(10));
        waitCart.until(ExpectedConditions.visibilityOfElementLocated
                (By.xpath("//a[@title='Zobacz koszyk']")));

        WebElement cartMenuItem = driverChrome.findElement(By.id("menu-item-127"));
        cartMenuItem.click();

        WebElement removeProductSrebrnaMoneta5g = driverChrome.findElement(By.cssSelector("a[data-product_id='24']"));
        Assertions.assertTrue(removeProductSrebrnaMoneta5g.isDisplayed());

    }

    @Test
    public void shouldVerifyAddingProductToCartAndRemovingProduct() {
        addToCart();

        Wait waitCart = new WebDriverWait(driverChrome, Duration.ofSeconds(10));
        waitCart.until(ExpectedConditions.visibilityOfElementLocated
                (By.xpath("//a[@title='Zobacz koszyk']")));

        WebElement cartMenuItem = driverChrome.findElement(By.id("menu-item-127"));
        cartMenuItem.click();

        WebElement removeProductSrebrnaMoneta5g = driverChrome.findElement(By.cssSelector("a[data-product_id='24']"));
        removeProductSrebrnaMoneta5g.click();

        Wait waitConfirmationRemovalMessage = new WebDriverWait(driverChrome, Duration.ofSeconds(10));
        waitConfirmationRemovalMessage.until(ExpectedConditions.visibilityOfElementLocated
                (By.className("woocommerce-message")));

        WebElement confirmationRemovalMessage = driverChrome.findElement(By.className("woocommerce-message"));
        String expectedConfirmationRemovalMessage = "Usunięto: „Srebrna moneta 5g - UK 1980”";
        Assertions.assertTrue(confirmationRemovalMessage.getText().contains(expectedConfirmationRemovalMessage));

    }

    private void productOnSaleList(int i) {
        List<WebElement> products = driverChrome.findElements(By.cssSelector("span[class=\"onsale\"]"));
        products.get(i).click();
    }

    private int productOnSaleStringToInt(int i) {
        List<WebElement> productPrice = driverChrome.findElements(By.cssSelector("td.product-subtotal > span > bdi"));
        String prodPrice;
        prodPrice = productPrice.get(i).getText();
        String numberOnly= prodPrice.replaceAll("[^0-9]", "");
        int number = Integer.parseInt(numberOnly);
        return number;
    }

    @Test
    void shouldAddPromotedProductToCartAndVerifyPrice() {
        List<WebElement> products = driverChrome.findElements(By.cssSelector("span[class=\"onsale\"]"));
        int sum = 0;
        for (int i = 0; i < products.size(); i++) {
            productOnSaleList(i);
            WebElement addToBasket = driverChrome.findElement(By.cssSelector("button[name=\"add-to-cart\"]"));
            addToBasket.click();
            WebElement toShop = driverChrome.findElement(By.id("menu-item-124"));
            toShop.click();
        }
        WebElement shopBox = driverChrome.findElement(By.id("site-header-cart"));
        shopBox.click();
        List<WebElement> productPrice = driverChrome.findElements(By.cssSelector("td.product-subtotal > span > bdi"));
        for (int i = 0; i < productPrice.size(); i++) {
            productOnSaleStringToInt(i);
            sum += productOnSaleStringToInt(i);
        }
        WebElement totalCartPrice = driverChrome.findElement(By.cssSelector("tr.cart-subtotal > td > span > bdi"));
        String totalPrice = totalCartPrice.getText().replaceAll("[^0-9]", "");
        int totalPriceInteger = Integer.parseInt(totalPrice);
        Assertions.assertEquals(sum, totalPriceInteger);
    }

}