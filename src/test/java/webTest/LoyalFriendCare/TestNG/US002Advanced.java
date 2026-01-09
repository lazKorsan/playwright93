package webTest.LoyalFriendCare.TestNG;

import com.microsoft.playwright.Page;
import org.testng.annotations.Test;
import Utilities.BrowserUtils;

public class US002Advanced {


    @Test
    public void testFullScreenNavigation() {
        // Full screen chrome ile Loyalfriend
        Page page = BrowserUtils.setUp("chrome", "fullScreen", "loyalfriend");
        BrowserUtils.bekle(5);

        // Title kontrolü
        String title = page.title();
        System.out.println("Sayfa Başlığı: " + title);

        // Ekran görüntüsü
        BrowserUtils.takeScreenshot("loyalfriend_home.png");

        BrowserUtils.tearDown();
    }
}
