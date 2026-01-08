package Utilities;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.BoundingBox;

public class ReusableMethods {

    /**
     * Belirtilen saniye kadar bekler.
     * Java'nın Thread.sleep() metodunu kullanır.
     * Test otomasyonunda hard wait önerilmez ancak debug veya zorunlu hallerde kullanılabilir.
     *
     * @param saniye Beklenecek süre (saniye cinsinden)
     */
    public static void bekle(int saniye) {
        try {
            Thread.sleep(saniye * 1000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Playwright'ın kendi bekleme mekanizmasını kullanarak bekler.
     * Bu yöntem, tarayıcı üzerinde işlem yaparken daha güvenli olabilir.
     *
     * @param page   Playwright Page nesnesi
     * @param saniye Beklenecek süre (saniye cinsinden)
     */
    public static void bekle(Page page, double saniye) {
        page.waitForTimeout(saniye * 1000);
    }

    // =================================================================================================
    // SCROLL METOTLARI
    // =================================================================================================

    /**
     * Belirtilen element görünür olana kadar sayfayı kaydırır.
     * Playwright'ın `scrollIntoViewIfNeeded()` metodunu kullanır.
     *
     * @param locator Hedef elementin Locator'ı
     */
    public static void scrollToElement(Locator locator) {
        locator.scrollIntoViewIfNeeded();
    }

    /**
     * Sayfanın en altına kadar kaydırır.
     *
     * @param page Playwright Page nesnesi
     */
    public static void scrollToBottom(Page page) {
        page.evaluate("window.scrollTo(0, document.body.scrollHeight)");
    }

    /**
     * Sayfanın en üstüne kadar kaydırır.
     *
     * @param page Playwright Page nesnesi
     */
    public static void scrollToTop(Page page) {
        page.evaluate("window.scrollTo(0, 0)");
    }

    /**
     * Belirli bir piksel miktarı kadar aşağı kaydırır.
     *
     * @param page   Playwright Page nesnesi
     * @param pixels Aşağı kaydırılacak piksel miktarı
     */
    public static void scrollDown(Page page, int pixels) {
        page.evaluate("window.scrollBy(0, " + pixels + ")");
    }

    /**
     * Belirli bir piksel miktarı kadar yukarı kaydırır.
     *
     * @param page   Playwright Page nesnesi
     * @param pixels Yukarı kaydırılacak piksel miktarı
     */
    public static void scrollUp(Page page, int pixels) {
        page.evaluate("window.scrollBy(0, -" + pixels + ")");
    }

    /**
     * Belirli bir elementin merkezine kadar kaydırır.
     *
     * @param page    Playwright Page nesnesi
     * @param locator Hedef element
     */
    public static void scrollToElementCenter(Page page, Locator locator) {
        BoundingBox box = locator.boundingBox();
        if (box != null) {
            page.mouse().wheel(0, box.y + box.height / 2);
        }
    }

    /**
     * Sayfayı yavaşça aşağı kaydırır (Lazy loading tetiklemek için kullanışlıdır).
     *
     * @param page Playwright Page nesnesi
     * @param step Her adımda kaç piksel kaydırılacağı
     * @param delayMs Her adım arasındaki bekleme süresi (milisaniye)
     */
    public static void slowScrollToBottom(Page page, int step, int delayMs) {
        long height = (long) page.evaluate("document.body.scrollHeight");
        long current = 0;
        while (current < height) {
            current += step;
            page.evaluate("window.scrollTo(0, " + current + ")");
            page.waitForTimeout(delayMs);
            // Sayfa yüksekliği dinamik olarak artabilir (infinite scroll)
            height = (long) page.evaluate("document.body.scrollHeight");
        }
    }

    public static void verify(Runnable assertion) {
        try {
            assertion.run();
            System.out.println("true");
        } catch (Throwable e) {
            System.out.println("false");
            throw e;
        }
    }
}
