package webTest.demeqa.TestNg;

import Utilities.BrowserUtils;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import org.testng.annotations.Test;


public class US002Iframe {

    @Test
    public void test01() {
        System.out.println("US056 Test 01 Çalıştı");

        Page page = BrowserUtils.setUpFullScreen("chrome", "https://demoqa.com/browser-windows");

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

    // Alternatif olarak daha temiz bir yaklaşım:
    @Test
    public void test02() {
        System.out.println("US056 Test 02 Çalıştı");

        Page mainPage = BrowserUtils.setUpFullScreen("chrome", "https://demoqa.com/browser-windows");

        try {
            // 1. Test New Tab Button
            testNewTab(mainPage);

            // 2. Test New Window Button
            testNewWindow(mainPage);

            // 3. Test New Window Message Button
            testNewWindowMessage(mainPage);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mainPage.context().browser().close();
        }
    }

    private void testNewTab(Page mainPage) throws InterruptedException {
        System.out.println("\n--- Testing New Tab ---");

        Page newTab = mainPage.waitForPopup(() -> {
            mainPage.locator("//button[@id='tabButton']").click();
        });

        newTab.waitForLoadState();
        Thread.sleep(2000);

        System.out.println("Tab URL: " + newTab.url());
        System.out.println("Tab Text: " + newTab.locator("#sampleHeading").textContent());

        newTab.close();
        mainPage.bringToFront();
        Thread.sleep(2000);
    }

    private void testNewWindow(Page mainPage) throws InterruptedException {
        System.out.println("\n--- Testing New Window ---");

        Page newWindow = mainPage.waitForPopup(() -> {
            mainPage.locator("//button[@id='windowButton']").click();
        });

        newWindow.waitForLoadState();
        Thread.sleep(2000);

        System.out.println("Window URL: " + newWindow.url());
        System.out.println("Window Text: " + newWindow.locator("#sampleHeading").textContent());

        newWindow.close();
        mainPage.bringToFront();
        Thread.sleep(2000);
    }

    private void testNewWindowMessage(Page mainPage) throws InterruptedException {
        System.out.println("\n--- Testing New Window Message ---");

        Page messageWindow = mainPage.waitForPopup(() -> {
            mainPage.locator("//button[@id='messageWindowButton']").click();
        });

        messageWindow.waitForLoadState();
        Thread.sleep(3000); // Mesaj penceresi biraz daha yavaş açılabiliyor

        // Body içindeki tüm text'i al
        String fullText = messageWindow.locator("body").innerText();
        System.out.println("Message Window URL: " + messageWindow.url());
        System.out.println("Full Message: " + fullText);

        // Sadece paragrafı almak isterseniz
        if (messageWindow.locator("p").isVisible()) {
            String paragraphText = messageWindow.locator("p").textContent();
            System.out.println("Paragraph Text: " + paragraphText);
        }

        messageWindow.close();
        mainPage.bringToFront();
        Thread.sleep(2000);
    }
}