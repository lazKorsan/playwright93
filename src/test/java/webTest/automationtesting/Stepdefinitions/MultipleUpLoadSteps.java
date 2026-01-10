package webTest.automationtesting.Stepdefinitions;

import Utilities.BrowserUtils;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import io.cucumber.java.en.Given;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MultipleUpLoadSteps {

    @Given("automationtesting kullanicisi multiplUpLoad alistirmasini uygular")
    public void automationtestingKullanicisiMultiplUpLoadAlistirmasiniUygular() throws InterruptedException {

        System.out.println("US065 Test 01 Çalıştı - Dosya yüklemek için yeni yöntem kullanıldı. Eski method kullanılmaz");

        // Tarayıcıyı başlat (BrowserUtils'in doğru çalıştığından emin olun)
        Page page = BrowserUtils.setUpFullScreen("chrome", "https://demo.automationtesting.in/FileUpload.html");

        try {
            // 1. Dosya yollarını tanımla ve MUTLAK (absolute) hale getir
            Path path1 = Paths.get("C:\\Users\\user\\Desktop\\UpLoadications 2.jpeg").toAbsolutePath();
            Path path2 = Paths.get("C:\\Users\\user\\Desktop\\UpLoadications.jpeg").toAbsolutePath();


            
            boolean file1Exists = new File(path1.toString()).exists();
            boolean file2Exists = new File(path2.toString()).exists();

            if (!file1Exists || !file2Exists) {
                System.err.println("UYARI: Belirtilen dosyalar bulunamadı!");
                if (!file1Exists) System.err.println("Bulunamadı: " + path1);
                if (!file2Exists) System.err.println("Bulunamadı: " + path2);

                 throw new RuntimeException("Hata: Dosyalar belirtilen masaüstü konumunda bulunamadı! Lütfen yolları kontrol edin: \n" + path1 + "\n" + path2);
            }

            // 3. Dosya yükleme input elementini bul (input[@type='file'] olduğundan emin olun)
            // Not: Genellikle bu tür sitelerde input id='input-4' olan element gizli değildir.
            Locator browserButton = page.locator("#input-4");

            // 4. Çoklu dosya yükleme işlemini gerçekleştir
            // Playwright Java'da birden fazla dosya Path[] dizisi olarak gönderilir.
            browserButton.setInputFiles(new Path[] { path1, path2 });

            // 5. Yükleme (Upload) butonuna tıkla
            // XPath yerine daha okunaklı bir locator seçimi (isteğe bağlı)
            Locator upLoadButton = page.locator("button.fileinput-upload-button");

            // Elementin görünür ve tıklanabilir olmasını bekle
            upLoadButton.waitFor();
            upLoadButton.click();

            // İşlemin sonucunu görmek için kısa bir bekleme (Opsiyonel)
            Thread.sleep(3000);

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {

            System.out.println("Test tamamlandı. Tarayıcı kapatılıyor...");

        }
    }
}
