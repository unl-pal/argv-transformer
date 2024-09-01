package SuperPersonality;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

public class PersonalityPerks {

    private WebDriver driver;

   @FindBy(xpath="/html/body/div/div[2]/ul/li[2]/a")
   private  WebElement updateYourProfile;
}




