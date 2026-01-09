package webTest.demeqa.Stepdefinitions;

import Utilities.BrowserUtils;
import Utilities.ReusableMethods;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.List;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class demoqaSteps {

    private Page page; // Class seviyesinde tanımlanmalı
    private Page newTabPage;
    private Page newWindowPage;
    private Page messageWindowPage;

    @Given("demoqa kullanicisi {string} sayfasina gider")
    public void demoqaKullanicisiSayfasinaGider(String url) {
        // this.page diyerek class seviyesindeki değişkene atama yapıyoruz
        this.page = BrowserUtils.setUpFullScreen("chrome", url);
    }

    @Then("demoga kullanicisi newTab butonuna basar ve acilan pencerede cikan yaziyi consola yazdiririr")
    public void demogaKullanicisiNewTabButonunaBasarVeAcilanPenceredeCikanYaziyiConsolaYazdiririr() throws InterruptedException {
        System.out.println("\n=== 1. NEW TAB TEST ===");

        // Yeni sekme açılmasını bekleyip açılan sayfayı al
        this.newTabPage = page.waitForPopup(() -> {
            page.getByRole(AriaRole.BUTTON,
                    new Page.GetByRoleOptions().setName("New Tab")).click();
        });

        // Yeni sekmeyi bekle
        newTabPage.waitForLoadState();
        Thread.sleep(2000);

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
    }

    @When("demoga kullanicisi newWindow butonuna basar ve acilan pencerede cikan yaziyi consola yazdiririr")
    public void demogaKullanicisiNewWindowButonunaBasarVeAcilanPenceredeCikanYaziyiConsolaYazdiririr() throws InterruptedException {
        System.out.println("\n=== 2. NEW WINDOW TEST ===");

        // Yeni pencere açılmasını bekle
        this.newWindowPage = page.waitForPopup(() -> {
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
    }

    @And("demoga kullanicisi newWindowMessage butonuna basar ve acilan pencerde cikan yaziyi consola yazdirir")
    public void demogaKullanicisiNewWindowMessageButonunaBasarVeAcilanPencerdeCikanYaziyiConsolaYazdirir() throws InterruptedException {
        System.out.println("\n=== 3. NEW WINDOW MESSAGE TEST ===");

        // Mesaj penceresi açılmasını bekle
        this.messageWindowPage = page.waitForPopup(() -> {
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

        // Ana sayfaya geri dön
        page.bringToFront();
        Thread.sleep(2000);
    }

    @Then("demoga kullanicisi testi bitirir")
    public void demogaKullanicisiTestiBitirir() {
        System.out.println("\n=== TEST TAMAMLANDI ===");

        // Browser'ı kapat
        if (page != null) {
            page.context().browser().close();
        }
    }

    @Given("demoqa kullanicisi consola succesMessage yazdirir")
    public void demoqaKullanicisiConsolaSuccesMessageYazdirir() {

        Page page = BrowserUtils.setUpFullScreen("chrome", "https://demoqa.com/checkbox");

        Locator menuOpenButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Toggle"));
        ReusableMethods.verify(() -> assertThat(menuOpenButton).isVisible());
        ReusableMethods.verify(() -> assertThat(menuOpenButton).isEnabled());
        menuOpenButton.click();

        Locator homeText = page.locator("//*[@id=\"tree-node\"]/ol/li/span/label/span[3]");
        ReusableMethods.verify(() -> assertThat(homeText).isVisible());
        ReusableMethods.verify(() -> assertThat(homeText).isEnabled());
        System.out.println(homeText.textContent());
        homeText.click();


        Locator desktopText = page.locator("//*[@id=\"tree-node\"]/ol/li/ol/li[1]/span/label/span[3]");
        ReusableMethods.verify(() -> assertThat(desktopText).isVisible());
        ReusableMethods.verify(() -> assertThat(desktopText).isEnabled());
        System.out.println(desktopText.textContent());
        desktopText.click();
        ReusableMethods.bekle((int) 0.3);
        desktopText.click();

        Locator documentText = page.locator("//*[@id=\"tree-node\"]/ol/li/ol/li[2]/span/label/span[3]");
        ReusableMethods.verify(() -> assertThat(documentText).isVisible());
        ReusableMethods.verify(() -> assertThat(documentText).isEnabled());
        System.out.println(documentText.textContent());
        documentText.click();
        ReusableMethods.bekle((int) 0.3);
        documentText.click();

        Locator downloadsText = page.locator("//*[@id=\"tree-node\"]/ol/li/ol/li[3]/span/label/span[3]");
        ReusableMethods.verify(() -> assertThat(downloadsText).isVisible());
        ReusableMethods.verify(() -> assertThat(downloadsText).isEnabled());
        System.out.println(downloadsText.textContent());
        downloadsText.click();
        ReusableMethods.bekle((int) 0.3);
        downloadsText.click();

        Locator startOfSuccesMassegeText = page.locator("//*[@id=\"result\"]/span[1]");
        String startMessage = startOfSuccesMassegeText.innerText();

        System.out.println(startOfSuccesMassegeText.innerText());

        // Dinamik olarak tüm success mesajlarını al
        Locator successMessages = page.locator("//span[@class='text-success']");
        int messageCount = successMessages.count();

        System.out.println("=== SEÇİLEN ÖĞELER ===");
        System.out.println("Toplam seçim: " + messageCount + " adet");

        // Her bir mesajı yazdır
        for (int i = 0; i < messageCount; i++) {
            String message = successMessages.nth(i).textContent();
            System.out.println((i + 1) + ". öğe: " + message);
        }

        // Tüm mesajları tek string'de birleştir
        StringBuilder combinedMessage = new StringBuilder();
        for (int i = 0; i < messageCount; i++) {
            combinedMessage.append(successMessages.nth(i).textContent());
            if (i < messageCount - 1) {
                combinedMessage.append(" ");
            }
        }

        System.out.println("Birleşik mesaj: " + combinedMessage.toString());

        // Doğrulama için
        Locator resultElement = page.locator("#result");
        String fullResult = resultElement.textContent();
        System.out.println("Tam mesaj: " + fullResult);

        // Mesajın doğru olup olmadığını kontrol et
        assertThat(resultElement).containsText("You have selected");

        // Birleşik mesaj: home desktop notes commands documents workspace react angular veu office public private classified general downloads wordFile excelFile
        //Tam mesaj: You have selected :homedesktopnotescommandsdocumentsworkspacereactangularveuofficepublicprivateclassifiedgeneraldownloadswordFileexcelFile

        System.out.println(startMessage+" "+combinedMessage.toString());

    }

    @Given("demoqa kullanicisi reuasableMethod ile succesMessage consola yazdirir")
    public void demoqaKullanicisiReuasableMethodIleSuccesMessageConsolaYazdirir() {

        Page page = BrowserUtils.setUpFullScreen("chrome", "https://demoqa.com/checkbox");

        Locator menuOpenButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Toggle"));
        ReusableMethods.verify(() -> assertThat(menuOpenButton).isVisible());
        ReusableMethods.verify(() -> assertThat(menuOpenButton).isEnabled());
        menuOpenButton.click();

        Locator homeText = page.locator("//*[@id=\"tree-node\"]/ol/li/span/label/span[3]");
        ReusableMethods.verify(() -> assertThat(homeText).isVisible());
        ReusableMethods.verify(() -> assertThat(homeText).isEnabled());
        System.out.println(homeText.textContent());
        homeText.click();


        Locator desktopText = page.locator("//*[@id=\"tree-node\"]/ol/li/ol/li[1]/span/label/span[3]");
        ReusableMethods.verify(() -> assertThat(desktopText).isVisible());
        ReusableMethods.verify(() -> assertThat(desktopText).isEnabled());
        System.out.println(desktopText.textContent());
        desktopText.click();
        ReusableMethods.bekle((int) 0.3);
        desktopText.click();

        Locator documentText = page.locator("//*[@id=\"tree-node\"]/ol/li/ol/li[2]/span/label/span[3]");
        ReusableMethods.verify(() -> assertThat(documentText).isVisible());
        ReusableMethods.verify(() -> assertThat(documentText).isEnabled());
        System.out.println(documentText.textContent());
        documentText.click();
        ReusableMethods.bekle((int) 0.3);
        documentText.click();

        Locator downloadsText = page.locator("//*[@id=\"tree-node\"]/ol/li/ol/li[3]/span/label/span[3]");
        ReusableMethods.verify(() -> assertThat(downloadsText).isVisible());
        ReusableMethods.verify(() -> assertThat(downloadsText).isEnabled());
        System.out.println(downloadsText.textContent());
        downloadsText.click();
        ReusableMethods.bekle((int) 0.3);
        downloadsText.click();

        Locator startOfSuccesMassegeText = page.locator("//*[@id=\"result\"]/span[1]");
        String startMessage = startOfSuccesMassegeText.innerText();

        System.out.println(startOfSuccesMassegeText.innerText());

        // Testinizde:
        String allSuccessTexts = ReusableMethods.getAllTexts(page, ".text-success");
        //String allSuccessTexts = ReusableMethods.getAllTexts(page, ".text-success");
        System.out.println("Tüm seçimler: " + allSuccessTexts);

        List<String> textList = ReusableMethods.getAllTextsAsList(page, ".text-success");
        System.out.println("Liste olarak: " + textList);


    }


}