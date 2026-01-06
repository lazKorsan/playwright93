package webTest.TestAutamationPractice.Stepdefinitions;

import com.microsoft.playwright.Page;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import webTest.TestAutamationPractice.Methods.formMethod;
import webTest.TestAutamationPractice.Utilities.BrowserUtils;

import java.text.ParseException;

import static webTest.TestAutamationPractice.Methods.formMethod.formDoldurma;

public class formSteps {

    private Page page;

    @Given("Kullanici {string} sayfasina giderR")
    public void kullaniciSayfasinaGiderR(String url) {

      page =  BrowserUtils.setUpFullScreen("Chrome", "https://testautomationpractice.blogspot.com/");
    }

    @Then("form Doldurur")
    public void formDoldurur() throws ParseException {

        // Başlatılmış olan page nesnesini metoda gönderiyoruz
        if (page != null) {
            formDoldurma(page);
        } else {
            throw new RuntimeException("Page nesnesi başlatılamadı! Lütfen önce 'Given' adımını çalıştırın.");
        }
    }
}
