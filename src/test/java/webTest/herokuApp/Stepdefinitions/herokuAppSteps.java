package webTest.herokuApp.Stepdefinitions;

import Utilities.BrowserUtils;
import Utilities.ReusableMethods;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import io.cucumber.java.en.Given;

public class herokuAppSteps {

    @Given("herokuApp kullanicisi drapAndDrop alistirmasini yapar")
    public void herokuappKullanicisiDrapAndDropAlistirmasiniYapar() {

        Page page = BrowserUtils.setUpFullScreen("chrome", "https://the-internet.herokuapp.com/drag_and_drop");

        ReusableMethods.bekle(3);

        Locator columA= page.locator("//*[@id='column-a']");

        Locator columB= page.locator("//*[@id='column-b']");

        Locator columAinnerText = page.locator("//*[@id=\"column-a\"]/header");
        System.out.println("Suruklme islemi oncesİ birinci kutu içindeki harf"+columAinnerText.textContent());

        String expectedText = "A";
        String actualText = columAinnerText.textContent();

        if (actualText.equals(expectedText)) {
            System.out.println("Test Başarılı");
        } else {
            System.out.println("Test Başarısız");
        }

        columA.dragTo(columB);

        expectedText = "B";
        actualText = columAinnerText.textContent();

        System.out.println("Suruklme islemi sonrasi birinci kutu içindeki harf"+columAinnerText.textContent());

        if (actualText.equals(expectedText)) {
            System.out.println("Test Başarılı");
        }
        else {
            System.out.println("Test Başarısız");
        }
    }

}
