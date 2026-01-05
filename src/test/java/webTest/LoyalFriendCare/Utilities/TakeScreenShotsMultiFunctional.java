package webTest.LoyalFriendCare.Utilities;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import java.io.File;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TakeScreenShotsMultiFunctional {

    /**
     * Sayfanın görünür kısmının ekran görüntüsünü alır.
     * @param page Playwright Page nesnesi
     * @param name Ekran görüntüsü dosya adı (uzantı olmadan)
     */
    public static void takeScreenshot(Page page, String name) {
        try {
            String date = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String folderPath = "test-output/screenshots/";
            
            File folder = new File(folderPath);
            if (!folder.exists()) {
                folder.mkdirs();
            }

            String path = folderPath + name + "_" + date + ".png";
            
            page.screenshot(new Page.ScreenshotOptions()
                    .setPath(Paths.get(path))
                    .setFullPage(false));
            
            System.out.println("Screenshot saved: " + path);
        } catch (Exception e) {
            System.err.println("Screenshot alınamadı: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Sayfanın tamamının (scroll dahil) ekran görüntüsünü alır.
     * @param page Playwright Page nesnesi
     * @param name Ekran görüntüsü dosya adı (uzantı olmadan)
     */
    public static void takeFullPageScreenshot(Page page, String name) {
        try {
            String date = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String folderPath = "test-output/screenshots/";
            
            File folder = new File(folderPath);
            if (!folder.exists()) {
                folder.mkdirs();
            }

            String path = folderPath + name + "_full_" + date + ".png";
            
            page.screenshot(new Page.ScreenshotOptions()
                    .setPath(Paths.get(path))
                    .setFullPage(true));
                    
            System.out.println("Full page screenshot saved: " + path);
        } catch (Exception e) {
            System.err.println("Full Page Screenshot alınamadı: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Belirli bir elementin ekran görüntüsünü alır.
     * @param locator Playwright Locator nesnesi
     * @param name Ekran görüntüsü dosya adı (uzantı olmadan)
     */
    public static void takeElementScreenshot(Locator locator, String name) {
        try {
            String date = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String folderPath = "test-output/screenshots/";
            
            File folder = new File(folderPath);
            if (!folder.exists()) {
                folder.mkdirs();
            }

            String path = folderPath + name + "_element_" + date + ".png";
            
            locator.screenshot(new Locator.ScreenshotOptions()
                    .setPath(Paths.get(path)));
                    
            System.out.println("Element screenshot saved: " + path);
        } catch (Exception e) {
            System.err.println("Element Screenshot alınamadı: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Gelişmiş ekran görüntüsü alma metodu.
     * Belirtilen elementleri yeşil veya kırmızı çerçeve içine alır, etiketler ve sayfaya QR kod ekler.
     *
     * @param page Playwright Page nesnesi
     * @param name Dosya adı
     * @param greenElements Yeşil çerçeve ve etiket eklenecek elementler (Locator -> Etiket)
     * @param redElements Kırmızı çerçeve ve etiket eklenecek elementler (Locator -> Etiket)
     */
    public static void takeAdvancedScreenshot(Page page, String name, Map<Locator, String> greenElements, Map<Locator, String> redElements) {
        try {
            // 1. QR Kod Ekle
            addQRCode(page);

            // 2. Yeşil Elementleri İşle
            if (greenElements != null) {
                for (Map.Entry<Locator, String> entry : greenElements.entrySet()) {
                    highlightElement(entry.getKey(), "green", entry.getValue());
                }
            }

            // 3. Kırmızı Elementleri İşle
            if (redElements != null) {
                for (Map.Entry<Locator, String> entry : redElements.entrySet()) {
                    highlightElement(entry.getKey(), "red", entry.getValue());
                }
            }

            // 4. Ekran Görüntüsü Al
            takeScreenshot(page, name);

        } catch (Exception e) {
            System.err.println("Gelişmiş screenshot hatası: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Sayfaya URL'in QR kodunu ekler.
     */
    public static void addQRCode(Page page) {
        try {
            String currentUrl = page.url();
            page.evaluate("url => {" +
                    "const div = document.createElement('div');" +
                    "div.style.position = 'fixed';" +
                    "div.style.right = '20px';" +
                    "div.style.top = '50%';" +
                    "div.style.transform = 'translateY(-50%)';" +
                    "div.style.zIndex = '10000';" +
                    "div.style.backgroundColor = 'white';" +
                    "div.style.padding = '10px';" +
                    "div.style.border = '2px solid black';" +
                    "div.style.boxShadow = '0 0 10px rgba(0,0,0,0.5)';" +
                    "div.innerHTML = `<img src='https://api.qrserver.com/v1/create-qr-code/?size=120x120&data=${encodeURIComponent(url)}' alt='QR Code' />`;" +
                    "document.body.appendChild(div);" +
                    "}", currentUrl);
        } catch (Exception e) {
            System.err.println("QR Kod eklenemedi: " + e.getMessage());
        }
    }

    /**
     * Elementi belirtilen renkte çerçeve içine alır ve altına metin ekler.
     */
    public static void highlightElement(Locator locator, String color, String text) {
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("color", color);
            params.put("text", text);

            locator.evaluate("(el, params) => {" +
                    "el.style.border = '4px solid ' + params.color;" +
                    "const rect = el.getBoundingClientRect();" +
                    "const label = document.createElement('div');" +
                    "label.innerText = params.text;" +
                    "label.style.position = 'absolute';" +
                    "label.style.top = (rect.bottom + window.scrollY + 5) + 'px';" +
                    "label.style.left = (rect.left + window.scrollX) + 'px';" +
                    "label.style.backgroundColor = params.color;" +
                    "label.style.color = 'white';" +
                    "label.style.padding = '4px 8px';" +
                    "label.style.borderRadius = '4px';" +
                    "label.style.fontSize = '14px';" +
                    "label.style.fontWeight = 'bold';" +
                    "label.style.zIndex = '10000';" +
                    "label.style.pointerEvents = 'none';" +
                    "document.body.appendChild(label);" +
                    "}", params);
        } catch (Exception e) {
            System.err.println("Element highlight edilemedi: " + e.getMessage());
        }
    }
}
