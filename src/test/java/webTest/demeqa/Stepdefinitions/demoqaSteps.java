package webTest.demeqa.Stepdefinitions;

import Utilities.BrowserUtils;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

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
}