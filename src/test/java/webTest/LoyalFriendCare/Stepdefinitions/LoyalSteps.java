package webTest.LoyalFriendCare.Stepdefinitions;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import Utilities.BrowserUtils;
import Utilities.TakeScreenShotsMultiFunctional;
import Utilities.ViewPortManger;

import java.util.HashMap;
import java.util.Map;

public class LoyalSteps {

    private Page page; // Class level variable to share state between steps

    @Given("Kullanici {string} sayfasina gider")
    public void kullaniciSayfasinaGider(String webName) {
        ViewPortManger.fullSize(webName);
    }

    @Given("Kullanici {string} sitesini Android gorunumunde acar")
    public void kullaniciSitesiniAndroidGorunumundeAcar(String webName) {
        // Varsayılan olarak Pixel 7 Pro kullanıyoruz
        ViewPortManger.androidView(webName, "Pixel 7 Pro");
    }

    @Given("user {string} sitesine gider")
    public void userSitesineGider(String siteName) {
        // BrowserUtils.setUp returns a Page object, we need to store it
        this.page = BrowserUtils.setUp("chrome", "fullScreen", siteName);
    }

    @Then("Kullanici elementlerin resmini alir")
    public void kullaniciElementlerinResminiAlir() {
        // Ensure page is initialized
        if (this.page == null) {
            throw new IllegalStateException("Page is not initialized. Please run a 'Given' step that initializes the page first.");
        }

        Map<Locator, String> redList = new HashMap<>();
        redList.put(page.locator("//*[@id='menu']/ul/li[1]/span/a"), "Home");
        redList.put(page.locator("//*[@id='menu']/ul/li[2]/span/a"), "About Us");

        // Yeşil çerçeveye alınacak elementler
        Map<Locator, String> greenList = new HashMap<>();
        greenList.put(page.locator("//*[@id='menu']/ul/li[4]/span/a"), "Doctors");
        greenList.put(page.locator("//*[@id='menu']/ul/li[5]/span/a"), "Medicines");

        // Gelişmiş ekran görüntüsünü al
        TakeScreenShotsMultiFunctional.takeAdvancedScreenshot(page, "MenuElementsTest", greenList, redList);
        
        // Clean up after test if needed, or rely on @After hook
        // BrowserUtils.tearDown(); // Optional depending on your framework structure
    }


}
