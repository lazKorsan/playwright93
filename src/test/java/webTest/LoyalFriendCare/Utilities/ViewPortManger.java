package webTest.LoyalFriendCare.Utilities;

import com.microsoft.playwright.*;

import java.util.Arrays;
import java.util.Map;

public class ViewPortManger {


    public static void fullSize(String webName){

        try (Playwright playwright = Playwright.create()) {
            // 1. Tarayıcıyı fizikselName olarak tam ekran başlat
            Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions()
                    .setHeadless(false)
                    .setArgs(Arrays.asList("--start-maximized")));

            // 2. Viewport'u NULL yaparak içeriğin pencereye tam sığmasını sağla
            // Bu adım, sağda kalan login kutusunun görünmesini sağlar.
            BrowserContext context = browser.newContext(new Browser.NewContextOptions()
                    .setViewportSize(null));

            Page page = context.newPage();

            navigateToSite(page, webName);

            // Browser will close after this block due to try-with-resources.
            // If you want to keep it open for debugging, you can use page.pause();
        }

    }

    // Android ebatları için yeni method
    public static void androidView(String webName, String deviceName) {
        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions()
                    .setHeadless(false));

            // Cihaz emülasyonu
            // Playwright Java'da devices() methodu olmadığı için manuel tanımlıyoruz.
            
            Browser.NewContextOptions options = new Browser.NewContextOptions();
            
            switch (deviceName) {
                case "Pixel 7":
                    options.setUserAgent("Mozilla/5.0 (Linux; Android 13; Pixel 7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/113.0.0.0 Mobile Safari/537.36");
                    options.setViewportSize(412, 915);
                    options.setDeviceScaleFactor(3);
                    options.setIsMobile(true);
                    options.setHasTouch(true);
                    break;
                case "Pixel 7 Pro":
                    options.setUserAgent("Mozilla/5.0 (Linux; Android 13; Pixel 7 Pro) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/113.0.0.0 Mobile Safari/537.36");
                    options.setViewportSize(412, 892);
                    options.setDeviceScaleFactor(3.5);
                    options.setIsMobile(true);
                    options.setHasTouch(true);
                    break;
                case "iPhone 12":
                    options.setUserAgent("Mozilla/5.0 (iPhone; CPU iPhone OS 14_4 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/14.0.3 Mobile/15E148 Safari/604.1");
                    options.setViewportSize(390, 844);
                    options.setDeviceScaleFactor(3);
                    options.setIsMobile(true);
                    options.setHasTouch(true);
                    break;
                default:
                    System.out.println("Cihaz tanımlı değil, varsayılan ayarlar kullanılıyor: " + deviceName);
                    break;
            }

            BrowserContext context = browser.newContext(options);

            Page page = context.newPage();

            navigateToSite(page, webName);
        }
    }

    private static void navigateToSite(Page page, String webName) {
        switch (webName) {
            case "LoyalFriendCare":
                page.navigate("https://qa.loyalfriendcare.com");
                break;
            case "queryCart":
                page.navigate("https://querycart.com/#/home");
                break;
            case "instuLearn":
                page.navigate("https://qa.instulearn.com/");
                break;
            default:
                System.out.println("Site tanımlı değil: " + webName);
                break;
        }
    }
}
