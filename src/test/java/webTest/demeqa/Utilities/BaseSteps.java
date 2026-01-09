package webTest.demeqa.Utilities;

import Utilities.BrowserUtils;
import com.microsoft.playwright.Page;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.testng.Assert;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BaseSteps {

    // Ortak kullanılacak değişkenler
    protected static Page currentPage;
    protected static String currentUrl;
    protected static Scenario currentScenario;

    // Test sonuçları için
    protected static long testStartTime;
    protected static long testEndTime;

    /**
     * Her senaryodan önce çalışır
     */
    @Before(order = 0)
    public void globalBefore(Scenario scenario) {
        currentScenario = scenario;
        testStartTime = System.currentTimeMillis();

        System.out.println("\n" + "=".repeat(80));
        System.out.println("TEST BAŞLIYOR");
        System.out.println("=".repeat(80));
        System.out.println("Senaryo    : " + scenario.getName());
        System.out.println("Tag'ler    : " + scenario.getSourceTagNames());
        System.out.println("Başlangıç  : " + getCurrentDateTime());
        System.out.println("=".repeat(80) + "\n");
    }

    /**
     * Her senaryodan sonra çalışır
     */
    @After(order = 1000)
    public void globalAfter(Scenario scenario) {
        testEndTime = System.currentTimeMillis();
        long duration = testEndTime - testStartTime;

        System.out.println("\n" + "=".repeat(80));
        System.out.println("TEST SONUÇLARI");
        System.out.println("=".repeat(80));
        System.out.println("Senaryo    : " + scenario.getName());
        System.out.println("Durum      : " + (scenario.isFailed() ? "❌ BAŞARISIZ" : "✅ BAŞARILI"));
        System.out.println("Süre       : " + duration + " ms (" + (duration/1000.0) + " saniye)");
        System.out.println("Bitiş      : " + getCurrentDateTime());



        System.out.println("=".repeat(80));

        // Browser'ı kapat
        closeBrowser();
    }

    /**
     * Browser'ı başlatma (tüm siteler için ortak)
     */
    protected Page launchBrowser(String browserType, String url, boolean headless) {
        System.out.println("🚀 Browser başlatılıyor: " + browserType);
        System.out.println("📍 URL: " + url);

        currentPage = BrowserUtils.setUp(browserType, url, String.valueOf(headless));
        currentUrl = url;

        // Sayfanın yüklenmesini bekle


        System.out.println("✓ Browser başlatıldı ve sayfa yüklendi");
        return currentPage;
    }

    /**
     * Varsayılan browser başlatma (headless false)
     */
    protected Page launchBrowser(String browserType, String url) {
        return launchBrowser(browserType, url, false);
    }

    /**
     * Chrome ile browser başlatma (en sık kullanılan)
     */
    protected Page launchChrome(String url) {
        return launchBrowser("chrome", url, false);
    }

    /**
     * Browser'ı kapat
     */
    protected void closeBrowser() {
        if (currentPage != null && !currentPage.isClosed()) {
            System.out.println("🛑 Browser kapatılıyor...");
            BrowserUtils.tearDown();
            currentPage = null;
            currentUrl = null;
            System.out.println("✓ Browser kapatıldı");
        }
    }



    /**
     * Sayfayı yenile
     */
    protected void reloadPage() {
        if (currentPage != null) {
            System.out.println("🔄 Sayfa yenileniyor...");
            currentPage.reload();

            System.out.println("✓ Sayfa yenilendi");
        }
    }

    /**
     * URL kontrolü
     */
    protected void verifyUrlContains(String expectedText) {
        String actualUrl = currentPage.url();
        Assert.assertTrue(actualUrl.contains(expectedText),
                "URL '" + expectedText + "' içermiyor. Actual URL: " + actualUrl);
        System.out.println("✓ URL kontrolü başarılı: " + expectedText);
    }

    /**
     * Title kontrolü
     */
    protected void verifyTitleContains(String expectedText) {
        String actualTitle = currentPage.title();
        Assert.assertTrue(actualTitle.contains(expectedText),
                "Title '" + expectedText + "' içermiyor. Actual Title: " + actualTitle);
        System.out.println("✓ Title kontrolü başarılı: " + expectedText);
    }

    /**
     * Element görünürlük kontrolü
     */
    protected void verifyElementVisible(String selector, String elementName) {
        boolean isVisible = currentPage.locator(selector).isVisible();
        Assert.assertTrue(isVisible, elementName + " element görünür değil");
        System.out.println("✓ " + elementName + " görünür");
    }

    /**
     * Text kontrolü
     */
    protected void verifyTextExists(String text) {
        boolean textExists = currentPage.locator("text=" + text).isVisible();
        Assert.assertTrue(textExists, "Text bulunamadı: " + text);
        System.out.println("✓ Text bulundu: " + text);
    }

    /**
     * Bekleme methodu
     */
    protected void waitForSeconds(int seconds) {
        System.out.println("⏳ " + seconds + " saniye bekleniyor...");

    }

    /**
     * Mevcut tarih-saat
     */
    private String getCurrentDateTime() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"));
    }
}