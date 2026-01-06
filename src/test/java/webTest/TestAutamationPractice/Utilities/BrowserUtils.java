package webTest.TestAutamationPractice.Utilities;

import com.microsoft.playwright.*;

public class BrowserUtils {

    private static Browser browser;
    private static BrowserContext context;
    private static Page page;
    private static Playwright playwright;

    /**
     * Browser, context ve page'i tek methodla kurar
     */
    public static Page setUp(String browserName, String dimensionsType, String siteName) {
        // 1. Playwright instance oluştur
        playwright = Playwright.create();

        // 2. Browser'ı başlat (fullscreen için özel launch options)
        browser = launchBrowser(browserName, dimensionsType);

        // 3. Viewport boyutunu ayarla
        context = createContextWithDimensions(browser, dimensionsType);

        // 4. Yeni sayfa oluştur
        page = context.newPage();

        // 5. Siteye git
        navigateToSite(page, siteName);

        return page;
    }

    /**
     * Browser tipine göre browser'ı başlatır
     */
    private static Browser launchBrowser(String browserName, String dimensionsType) {
        BrowserType.LaunchOptions launchOptions = new BrowserType.LaunchOptions()
                .setHeadless(false);

        // FULL SCREEN için launch argümanlarını ekle
        if ("fullscreen".equalsIgnoreCase(dimensionsType)) {
            launchOptions.setArgs(java.util.Arrays.asList("--start-maximized"));
        }

        switch (browserName.toLowerCase()) {
            case "firefox":
                return playwright.firefox().launch(launchOptions);
            case "webkit":
                return playwright.webkit().launch(launchOptions);
            case "chrome":
            default:
                // Chrome için channel belirt
                return playwright.chromium().launch(
                        launchOptions.setChannel("chrome")
                );
        }
    }

    /**
     * Boyut tipine göre context oluşturur
     */
    private static BrowserContext createContextWithDimensions(Browser browser, String dimensionsType) {
        // FULL SCREEN için viewport null
        if ("fullscreen".equalsIgnoreCase(dimensionsType)) {
            return browser.newContext(new Browser.NewContextOptions()
                    .setViewportSize(null)  // Maximized için null
            );
        }

        // Diğer boyutlar için
        java.util.Map<String, Integer> dimensions = getDimensions(dimensionsType);

        return browser.newContext(new Browser.NewContextOptions()
                .setViewportSize(dimensions.get("width"), dimensions.get("height"))
        );
    }

    /**
     * Boyut tiplerini tanımlar
     */
    private static java.util.Map<String, Integer> getDimensions(String dimensionsType) {
        java.util.Map<String, Integer> dimensions = new java.util.HashMap<>();

        switch (dimensionsType.toLowerCase()) {
            case "desktop":
                dimensions.put("width", 1920);
                dimensions.put("height", 1080);
                break;
            case "tablet":
                dimensions.put("width", 768);
                dimensions.put("height", 1024);
                break;
            case "mobile":
                dimensions.put("width", 375);
                dimensions.put("height", 667);
                break;
            case "pixel7":
                dimensions.put("width", 412);
                dimensions.put("height", 915);
                break;
            case "pixel7pro":
                dimensions.put("width", 430);
                dimensions.put("height", 932);
                break;
            case "iphone12":
                dimensions.put("width", 390);
                dimensions.put("height", 844);
                break;
            case "custom":
                dimensions.put("width", 1366);
                dimensions.put("height", 768);
                break;
            default:
                dimensions.put("width", 1280);
                dimensions.put("height", 720);
        }

        return dimensions;
    }

    /**
     * SADECE FULL SCREEN için özel method (orijinal methodunuz)
     */
    public static Page setUpFullScreen(String browserName, String siteName) {
        playwright = Playwright.create();

        // Args'ı launch options'a ekle
        BrowserType.LaunchOptions launchOptions = new BrowserType.LaunchOptions()
                .setHeadless(false)
                .setArgs(java.util.Arrays.asList("--start-maximized"));

        // Browser'ı başlat
        BrowserType browserType;
        switch (browserName.toLowerCase()) {
            case "firefox":
                browserType = playwright.firefox();
                break;
            case "webkit":
                browserType = playwright.webkit();
                break;
            case "chrome":
            default:
                browserType = playwright.chromium();
                launchOptions.setChannel("chrome");
                break;
        }

        browser = browserType.launch(launchOptions);

        // Context oluştur (viewport null)
        context = browser.newContext(new Browser.NewContextOptions()
                .setViewportSize(null));

        page = context.newPage();
        navigateToSite(page, siteName);

        return page;
    }



    /**
     * Site adına göre URL'ye yönlendirir
     */
    private static void navigateToSite(Page page, String siteName) {
        String url = getUrlFromSiteName(siteName);
        page.navigate(url);
        System.out.println("✅ Navigated to: " + url);
    }

    /**
     * Site adından URL oluşturur
     */
    private static String getUrlFromSiteName(String siteName) {
        switch (siteName.toLowerCase()) {
            case "google": return "https://www.google.com";
            case "youtube": return "https://www.youtube.com";
            case "github": return "https://github.com";
            case "stackoverflow": return "https://stackoverflow.com";
            case "twitter": return "https://twitter.com";
            case "linkedin": return "https://linkedin.com";
            case "loyalfriend": return "https://qa.loyalfriendcare.com";
            default:
                return siteName.startsWith("http") ? siteName : "https://www." + siteName + ".com";
        }
    }

    // Diğer methodlar aynı kalacak...
    public static void bekle(int seconds) {
        try { Thread.sleep(seconds * 1000L); }
        catch (InterruptedException e) { Thread.currentThread().interrupt(); }
    }

    public static void takeScreenshot(String fileName) {
        if (page != null) {
            page.screenshot(new Page.ScreenshotOptions()
                    .setPath(java.nio.file.Paths.get(fileName))
                    .setFullPage(true));
            System.out.println("📸 Screenshot saved: " + fileName);
        }
    }

    public static void tearDown() {
        try {
            if (page != null && !page.isClosed()) page.close();
            if (context != null) context.close();
            if (browser != null) browser.close();
            if (playwright != null) playwright.close();
            System.out.println("✅ All resources closed.");
        } catch (Exception e) {
            System.out.println("❌ Error during teardown: " + e.getMessage());
        }
    }

}