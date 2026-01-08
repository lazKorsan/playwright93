package webTest.LoyalFriendCare.TestNG;

import Utilities.BrowserUtils;
import Utilities.ReusableMethods;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import org.testng.annotations.Test;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class US004Verify {

    @Test
    public void test01(){
        System.out.println("US054 Test 01 Çalıştı");

        Page page = BrowserUtils.setUpFullScreen("chrome","https://www.ebay.com/");

        Locator signInButton = page.locator("//*[@id=\"gh\"]/nav/div[1]/span[1]/span/a");

        ReusableMethods.verify(() -> assertThat(signInButton).isVisible());

        ReusableMethods.verify(() -> assertThat(signInButton).isEnabled());
    }
}
