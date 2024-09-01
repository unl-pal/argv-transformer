package amazon;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AmazonHomePage {

    private WebDriver driver;

    @FindBy(name="p")
    private WebElement keywordsField;

    @FindBy(xpath="//*[@id=\"search-submit\"]")
    private WebElement goButton;
}