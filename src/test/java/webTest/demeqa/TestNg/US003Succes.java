package webTest.demeqa.TestNg;

import Utilities.BrowserUtils;
import Utilities.ReusableMethods;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import org.testng.annotations.Test;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class US003Succes {

    // ikinci test daha başarılı

    @Test
    public void succesMessage() {

        System.out.println(" succesMassege testi calisti ");

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


        //<! todo ------------------------------------------------
        Locator startOfSuccesMassegeText = page.locator("//*[@id=\"result\"]/span[1]");
        System.out.println(startOfSuccesMassegeText.innerText());

        // 1. YOL: Tüm success elementlerini al
        Locator allSuccessElements = page.locator(".text-success");
        int successCount = allSuccessElements.count();

        System.out.println("Toplam success elementi: " + successCount);

        // Tüm elementlerin metinlerini birleştir
        StringBuilder allSuccessTexts = new StringBuilder();
        for (int i = 0; i < successCount; i++) {
            String text = allSuccessElements.nth(i).textContent().trim();
            allSuccessTexts.append(text);
            if (i < successCount - 1) {
                allSuccessTexts.append(", ");
            }
        }

        System.out.println("Seçilen tüm öğeler: " + allSuccessTexts.toString());

        // 2. YOL: Direkt result span'ının tamamını al
        Locator resultSpan = page.locator("//*[@id=\"result\"]");
        System.out.println("Tam sonuç mesajı: " + resultSpan.textContent());


        //<! todo ------------------------------------------------


        // Seçilen tüm öğeler: home, desktop, notes, commands, documents, workspace, react, angular, veu, office, public, private, classified, general, downloads, wordFile, excelFile
        //Tam sonuç mesajı: You have selected :homedesktopnotescommandsdocumentsworkspacereactangularveuofficepublicprivateclassifiedgeneraldownloadswordFileexcelFile

    }

    @Test
    public void succesMessage2() {
        System.out.println(" succesMassege testi calisti ");

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

        //<! todo ------------------------------------------------
        // Dinamik olarak tüm success mesajlarını al
        Locator successMessages = page.locator("//span[@class='text-success']");
        int messageCount = successMessages.count();

        System.out.println("=== SEÇİLEN ÖĞELER ===");
        System.out.println("Toplam seçim: " + messageCount + " adet");

        // Her bir mesajı yazdır
        for (int i = 0; i < messageCount; i++) {
            String message = successMessages.nth(i).textContent();
            System.out.println((i + 1) + ". öğe: " + message);
        }

        // Tüm mesajları tek string'de birleştir
        StringBuilder combinedMessage = new StringBuilder();
        for (int i = 0; i < messageCount; i++) {
            combinedMessage.append(successMessages.nth(i).textContent());
            if (i < messageCount - 1) {
                combinedMessage.append(" ");
            }
        }

        System.out.println("Birleşik mesaj: " + combinedMessage.toString());

        // Doğrulama için
        Locator resultElement = page.locator("#result");
        String fullResult = resultElement.textContent();
        System.out.println("Tam mesaj: " + fullResult);

        // Mesajın doğru olup olmadığını kontrol et
        assertThat(resultElement).containsText("You have selected");

        // Birleşik mesaj: home desktop notes commands documents workspace react angular veu office public private classified general downloads wordFile excelFile
        //Tam mesaj: You have selected :homedesktopnotescommandsdocumentsworkspacereactangularveuofficepublicprivateclassifiedgeneraldownloadswordFileexcelFile

        System.out.println(startMessage+" "+combinedMessage.toString());

        //<! todo ------------------------------------------------


    }
}
