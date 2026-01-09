package webTest.LoyalFriendCare.TestNG;

import Utilities.BrowserUtils;
import Utilities.TakeScreenShotsMultiFunctional;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import org.testng.annotations.Test;


import java.util.HashMap;
import java.util.Map;

public class US003TakeScreenShots {


    @Test
    public void testTakeFullPageScreenshot() {

        // Full screen chrome ile Loyalfriend
        Page page = BrowserUtils.setUp("chrome", "fullScreen", "loyalfriend");

        BrowserUtils.bekle(5);

        // Title kontrolü
        String title = page.title();
        System.out.println("Sayfa Başlığı: " + title);

        Map<Locator, String> redList = new HashMap<>();
        redList.put(page.locator("//*[@id='menu']/ul/li[1]/span/a"), "Home");
        redList.put(page.locator("//*[@id='menu']/ul/li[2]/span/a"), "About Us");

        // Yeşil çerçeveye alınacak elementler
        Map<Locator, String> greenList = new HashMap<>();
        greenList.put(page.locator("//*[@id='menu']/ul/li[4]/span/a"), "Doctors");
        greenList.put(page.locator("//*[@id='menu']/ul/li[5]/span/a"), "Medicines");

        // Gelişmiş ekran görüntüsünü al
        TakeScreenShotsMultiFunctional.takeAdvancedScreenshot(page, "MenuElementsTest", greenList, redList);



    }

}
