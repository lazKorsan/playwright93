package webTest.LoyalFriendCare.Stepdefinitions;

import com.microsoft.playwright.Page;
import io.cucumber.java.en.Given;
import webTest.LoyalFriendCare.Utilities.BrowserUtils;
import webTest.LoyalFriendCare.Utilities.ViewPortManger;

public class LoyalSteps {

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

        Page page = BrowserUtils.setUp("chrome", "fullScreen", "loyalfriend");
    }
}
