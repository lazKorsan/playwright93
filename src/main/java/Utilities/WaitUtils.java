package Utilities;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.LoadState;
import com.microsoft.playwright.options.WaitForSelectorState;

import java.time.Duration;

public class WaitUtils {

    /**
     * Gelişmiş sayfa yükleme bekleme methodu
     */
    public static void waitForPageToLoadCompletely(Page page, int timeoutInSeconds) {
        long startTime = System.currentTimeMillis();
        long timeoutMillis = timeoutInSeconds * 1000L;

        try {
            System.out.println("⏳ Sayfa yüklenmesi bekleniyor: " + page.url());

            // 1. Önce DOM'un yüklenmesini bekle
            page.waitForLoadState(LoadState.DOMCONTENTLOADED,
                    new Page.WaitForLoadStateOptions()
                            .setTimeout(timeoutMillis));

            // 2. Network trafiğinin durmasını bekle
            page.waitForLoadState(LoadState.NETWORKIDLE,
                    new Page.WaitForLoadStateOptions()
                            .setTimeout(timeoutMillis));

            // 3. Sayfanın tamamen yüklenmesini bekle
            page.waitForLoadState(LoadState.LOAD,
                    new Page.WaitForLoadStateOptions()
                            .setTimeout(timeoutMillis));

            // 4. Document readyState kontrolü
            waitForDocumentReadyState(page, timeoutMillis - (System.currentTimeMillis() - startTime));

            // 5. Body elementinin görünür olmasını bekle
            waitForElementVisible(page, "body",
                    Duration.ofMillis(Math.max(1000, timeoutMillis - (System.currentTimeMillis() - startTime))));

            long elapsedTime = System.currentTimeMillis() - startTime;
            System.out.println(String.format("✓ Sayfa %d ms'de yüklendi: %s",
                    elapsedTime, page.url()));

        } catch (TimeoutError e) {
            System.err.println("❌ Sayfa yükleme timeout (" + timeoutInSeconds + "s): " + page.url());
            throw new RuntimeException("Sayfa yüklenemedi: " + page.url(), e);
        }
    }

    /**
     * Document readyState kontrolü
     */
    private static void waitForDocumentReadyState(Page page, long timeoutMillis) {
        if (timeoutMillis > 0) {
            page.waitForFunction("document.readyState === 'complete'",
                    new Page.WaitForFunctionOptions().setTimeout((double) timeoutMillis));
        }
    }

    /**
     * Elementin görünür olmasını bekler
     */
    public static Locator waitForElementVisible(Page page, String selector, Duration timeout) {
        try {
            Locator locator = page.locator(selector);
            locator.waitFor(new Locator.WaitForOptions()
                    .setState(WaitForSelectorState.VISIBLE)
                    .setTimeout(timeout.toMillis()));
            return locator;
        } catch (Exception e) {
            throw new RuntimeException("Element görünür olmadı: " + selector, e);
        }
    }

    /**
     * Elementin görünür olmasını bekler (basit versiyon)
     */
    public static void waitForElementVisible(Page page, String selector, int timeoutSeconds) {
        page.waitForSelector(selector,
                new Page.WaitForSelectorOptions()
                        .setState(WaitForSelectorState.VISIBLE)
                        .setTimeout(timeoutSeconds * 1000.0));
    }

    /**
     * Elementin tıklanabilir olmasını bekler
     */
    public static void waitForElementClickable(Page page, String selector, int timeoutSeconds) {
        // Önce görünür olmasını bekle
        waitForElementVisible(page, selector, timeoutSeconds);

        // Ek olarak enabled kontrolü
        try {
            page.waitForFunction(
                    "(selector) => { " +
                            "  const element = document.querySelector(selector); " +
                            "  return element && " +
                            "         !element.disabled && " +
                            "         element.style.display !== 'none' && " +
                            "         element.style.visibility !== 'hidden' && " +
                            "         element.style.opacity !== '0' && " +
                            "         element.getBoundingClientRect().width > 0 && " +
                            "         element.getBoundingClientRect().height > 0; " +
                            "}",
                    selector,
                    new Page.WaitForFunctionOptions().setTimeout(timeoutSeconds * 1000.0)
            );
        } catch (Exception e) {
            System.out.println("Element tıklanabilir değil: " + selector + " - " + e.getMessage());
        }
    }
}