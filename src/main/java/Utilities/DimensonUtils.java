package Utilities;

import com.microsoft.playwright.*;
import java.time.Duration;
import java.util.List;

public class DimensonUtils
{

    private static Playwright playwright;
    private static Browser browser;

    /**
     * Browser başlatma ve sayfa açma
     */
    public static Page setUpBrowser(String browserType, String url, boolean headless) {
        // Playwright instance oluştur
        playwright = Playwright.create();

        // Browser seçimi
        switch (browserType.toLowerCase()) {
            case "chrome":
            case "chromium":
                browser = playwright.chromium().launch(new BrowserType.LaunchOptions()
                        .setHeadless(headless)
                        .setArgs(List.of(
                                "--start-maximized",
                                "--disable-notifications",
                                "--disable-infobars"
                        )));
                break;
            case "firefox":
                browser = playwright.firefox().launch(new BrowserType.LaunchOptions()
                        .setHeadless(headless));
                break;
            case "webkit":
                browser = playwright.webkit().launch(new BrowserType.LaunchOptions()
                        .setHeadless(headless));
                break;
            default:
                throw new IllegalArgumentException("Desteklenmeyen browser: " + browserType);
        }

        // Browser context oluştur
        BrowserContext context = browser.newContext(new Browser.NewContextOptions()
                .setViewportSize(null) // Full screen
                .setIgnoreHTTPSErrors(true)
                .setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36"));

        // Yeni sayfa oluştur
        Page page = context.newPage();

        // Sayfaya git
        page.navigate(url);

        // Sayfanın tamamen yüklenmesini bekle
        WaitUtils.waitForPageToLoadCompletely(page, 30);

        return page;
    }

    /**
     * Full screen browser başlatma (varsayılan)
     */
    public static Page setUpFullScreen(String browserType, String url) {
        return setUpBrowser(browserType, url, false);
    }

    /**
     * Headless browser başlatma
     */
    public static Page setUpHeadless(String browserType, String url) {
        return setUpBrowser(browserType, url, true);
    }

    /**
     * Browser'ı kapat
     */
    public static void closeBrowser(Page page) {
        if (page != null && !page.isClosed()) {
            page.context().browser().close();
        }
        if (playwright != null) {
            playwright.close();
        }
    }

    /**
     * Sayfa yenile ve yüklenmesini bekle
     */
    public static void reloadAndWait(Page page, int timeoutSeconds) {
        page.reload();
        WaitUtils.waitForPageToLoadCompletely(page, timeoutSeconds);
    }

    /**
     * Yeni tab aç ve bekle
     */
    public static Page openNewTabAndWait(Page currentPage, String url, int timeoutSeconds) {
        Page newPage = currentPage.context().newPage();
        newPage.navigate(url);
        WaitUtils.waitForPageToLoadCompletely(newPage, timeoutSeconds);
        return newPage;
    }
}