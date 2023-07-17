package pages;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class MojeKontoPage{

    static WebDriver driverChrome;

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

}
