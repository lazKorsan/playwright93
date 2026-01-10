package webTest.pendingPractice.TestNg;

import Utilities.BrowserUtils;
import Utilities.ReusableMethods;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import org.testng.annotations.Test;

import java.nio.file.Paths;

public class US001UpLoadFiles {

    @Test
    public void test01(){
        System.out.println("US001 Test 01 Çalıştı");

        Page page = BrowserUtils.setUpFullScreen("chrome", "https://practice.expandtesting.com/upload");

        Locator chooseFileButton = page.locator("//input[@id='fileInput']");
        ReusableMethods.scrollToElement(chooseFileButton);

        String filePath = "C:\\Users\\user\\Desktop\\UpLoadications.jpeg";
        chooseFileButton.setInputFiles(Paths.get(filePath));

        Locator upLoadButton = page.locator("//button[@id='fileSubmit']");
        upLoadButton.click();

        Locator upLoadFiesMessageContainer = page.locator("//*[@id='uploaded-files']");

        // Sadece dosya adını al
        String expectedFileName = "UpLoadications.jpeg";
        String actualFilesPath = upLoadFiesMessageContainer.textContent();

        System.out.println("Actual uploaded file: " + actualFilesPath);

        // ÇÖZÜM 1: Timestamp'i kaldır ve sadece dosya adını karşılaştır
        String actualFileName = actualFilesPath.replaceAll("^\\d+_", "");

        if (actualFileName.equals(expectedFileName)) {
            System.out.println("Test Başarılı - Dosya adı eşleşiyor");
        } else {
            System.out.println("Test Başarısız - Dosya adı eşleşmiyor");
            System.out.println("Beklenen: " + expectedFileName);
            System.out.println("Gerçek: " + actualFileName);
        }

        // ÇÖZÜM 2: Veya sadece dosya adının içerdiğini kontrol et
        if (actualFilesPath.contains(expectedFileName)) {
            System.out.println("Test Başarılı - Dosya adı içeriyor");
        } else {
            System.out.println("Test Başarısız - Dosya adı içermiyor");
        }

    }
}
