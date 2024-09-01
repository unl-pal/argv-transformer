package Various;

import org.openqa.selenium.Alert;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PerksLoginBase {
    public WebDriver driver;

    @FindBy(id="loginEmail")
    @CacheLookup
    private WebElement loginEmailAddress;

    @FindBy(id="loginPassword")
    @CacheLookup
    private WebElement loginPassword;

    @FindBy(xpath="//*[@id=\"login\"]/fieldset/div/button")
    @CacheLookup
    private WebElement userLoginButton;

    @FindBy(xpath="//*[@id=\"menu\"]/li[6]/a")
    @CacheLookup
    private WebElement logout;

    @FindBy(xpath="/html/body/div/div[2]/div/form/fieldset/div/a")
    @CacheLookup
    private WebElement lostPassword;

}
