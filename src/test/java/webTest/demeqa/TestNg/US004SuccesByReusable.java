package webTest.demeqa.TestNg;

import Utilities.BrowserUtils;
import Utilities.ReusableMethods;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import org.testng.annotations.Test;

import java.util.List;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class US004SuccesByReusable {

    @Test
    public void test01(){
        System.out.println("US006 Test 01 Çalıştı");

        Page page = BrowserUtils.setUpFullScreen("chrome", "https://demoqa.com/checkbox");

        Locator menuOpenButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Toggle"));
        ReusableMethods.verify(() -> assertThat(menuOpenButton).isVisible());
        ReusableMethods.verify(() -> assertThat(menuOpenButton).isEnabled());
        menuOpenButton.click();

        Locator homeText = page.locator("//*[@id=\"tree-node\"]/ol/li/span/label/span[3]");
        ReusableMethods.verify(() -> assertThat(homeText).isVisible());
        ReusableMethods.verify(() -> assertThat(homeText).isEnabled());
        System.out.println(homeText.textContent());
        homeText.click();


        Locator desktopText = page.locator("//*[@id=\"tree-node\"]/ol/li/ol/li[1]/span/label/span[3]");
        ReusableMethods.verify(() -> assertThat(desktopText).isVisible());
        ReusableMethods.verify(() -> assertThat(desktopText).isEnabled());
        System.out.println(desktopText.textContent());
        desktopText.click();
        ReusableMethods.bekle((int) 0.3);
        desktopText.click();

        Locator documentText = page.locator("//*[@id=\"tree-node\"]/ol/li/ol/li[2]/span/label/span[3]");
        ReusableMethods.verify(() -> assertThat(documentText).isVisible());
        ReusableMethods.verify(() -> assertThat(documentText).isEnabled());
        System.out.println(documentText.textContent());
        documentText.click();
        ReusableMethods.bekle((int) 0.3);
        documentText.click();

        Locator downloadsText = page.locator("//*[@id=\"tree-node\"]/ol/li/ol/li[3]/span/label/span[3]");
        ReusableMethods.verify(() -> assertThat(downloadsText).isVisible());
        ReusableMethods.verify(() -> assertThat(downloadsText).isEnabled());
        System.out.println(downloadsText.textContent());
        downloadsText.click();
        ReusableMethods.bekle((int) 0.3);
        downloadsText.click();

        Locator startOfSuccesMassegeText = page.locator("//*[@id=\"result\"]/span[1]");
        String startMessage = startOfSuccesMassegeText.innerText();

        System.out.println(startOfSuccesMassegeText.innerText());

        // Testinizde:
        String allSuccessTexts = ReusableMethods.getAllTexts(page, ".text-success");
        //String allSuccessTexts = ReusableMethods.getAllTexts(page, ".text-success");
        System.out.println("Tüm seçimler: " + allSuccessTexts);

        List<String> textList = ReusableMethods.getAllTextsAsList(page, ".text-success");
        System.out.println("Liste olarak: " + textList);
    }

}
