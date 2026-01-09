package webTest.demeqa.TestNg;

import Utilities.BrowserUtils;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import org.testng.annotations.Test;

public class US001Iframe {

    @Test
    public void test01(){
        System.out.println("US001 Test 01 Çalıştı");


        Page page = BrowserUtils
                .setUpFullScreen("chrome", "https://demoqa.com/browser-windows");

        try {
            // 1. NEW TAB BUTTON (Yeni Sekme)
            System.out.println("\n=== 1. NEW TAB TEST ===");

            // Yeni sekme açılmasını bekleyip açılan sayfayı al
            Page newTabPage = page.waitForPopup(() -> {
                page.getByRole(AriaRole.BUTTON,
                        new Page.GetByRoleOptions().setName("New Tab")).click();
            });

            // Yeni sekmeyi bekle
            newTabPage.waitForLoadState();
            Thread.sleep(2000); // Görsel için bekleme

            // Yeni sekmedeki yazıyı al
            String newTabText = newTabPage.locator("//h1[@id='sampleHeading']").textContent();
            String newTabUrl = newTabPage.url();

            System.out.println("New Tab URL: " + newTabUrl);
            System.out.println("New Tab Text: " + newTabText);

            // Yeni sekmeyi kapat
            newTabPage.close();
            System.out.println("New Tab kapatıldı");

            // Ana sayfaya geri dön
            page.bringToFront();
            Thread.sleep(2000);

            // 2. NEW WINDOW BUTTON (Yeni Pencere)
            System.out.println("\n=== 2. NEW WINDOW TEST ===");

            // Yeni pencere açılmasını bekle
            Page newWindowPage = page.waitForPopup(() -> {
                page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("New Window").setExact(true)).click();

            });

            // Yeni pencereyi bekle
            newWindowPage.waitForLoadState();
            Thread.sleep(2000);

            // Yeni penceredeki yazıyı ve URL'i al
            String newWindowText = newWindowPage.locator("//h1[@id='sampleHeading']").textContent();
            String newWindowUrl = newWindowPage.url();

            System.out.println("New Window URL: " + newWindowUrl);
            System.out.println("New Window Text: " + newWindowText);

            // Yeni pencereyi kapat
            newWindowPage.close();
            System.out.println("New Window kapatıldı");

            // Ana sayfaya geri dön
            page.bringToFront();
            Thread.sleep(2000);

            // 3. NEW WINDOW MESSAGE BUTTON (Mesaj Penceresi)
            System.out.println("\n=== 3. NEW WINDOW MESSAGE TEST ===");

            // Mesaj penceresi açılmasını bekle
            Page messageWindowPage = page.waitForPopup(() -> {
                page.getByRole(AriaRole.BUTTON,
                        new Page.GetByRoleOptions().setName("New Window Message")).click();
            });

            // Mesaj penceresini bekle
            messageWindowPage.waitForLoadState();
            Thread.sleep(2000);

            // Mesaj penceresindeki yazıyı al (body içindeki text)
            String messageText = messageWindowPage.locator("body").textContent();
            String messageUrl = messageWindowPage.url();

            System.out.println("Message Window URL: " + messageUrl);
            System.out.println("Message Window Text: " + messageText);



            // Mesaj penceresini kapat
            messageWindowPage.close();
            System.out.println("Message Window kapatıldı");

            // Ana sayfaya geri dön ve son işlem
            page.bringToFront();
            Thread.sleep(2000);

            System.out.println("\n=== TEST TAMAMLANDI ===");

        } catch (Exception e) {
            System.err.println("Hata oluştu: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Tarayıcıyı kapat
            if (page != null) {
                page.context().browser().close();
            }
        }
    }
}





