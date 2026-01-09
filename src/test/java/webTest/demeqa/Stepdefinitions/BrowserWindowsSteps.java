package webTest.demeqa.Stepdefinitions;

import Utilities.BrowserUtils;
import Utilities.DimensonUtils;
import Utilities.WaitUtils;
import com.microsoft.playwright.Page;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.List;

public class BrowserWindowsSteps {

    private Page mainPage;
    private List<Page> openedPages = new ArrayList<>();

    // ========== BACKGROUND ==========

    @Given("user is on DemoQA Browser Windows page")
    public void userIsOnDemoQABrowserWindowsPage() {
        // Hooks üzerinden başlatılan browser'ı kullanabiliriz veya yeni başlatabiliriz
        // Burada Hooks.getCurrentPage() kontrolü yapıp yoksa yeni başlatabiliriz
        if (Hooks.getCurrentPage() != null) {
            mainPage = Hooks.getCurrentPage();
            mainPage.navigate("https://demoqa.com/browser-windows");
        } else {
            mainPage = DimensonUtils.setUpFullScreen("chrome", "https://demoqa.com/browser-windows");
        }
        
        WaitUtils.waitForPageToLoadCompletely(mainPage, 10);
        Assert.assertTrue(mainPage.title().contains("ToolsQA"),
                "Page title should contain 'ToolsQA'");
        System.out.println("✓ Navigated to DemoQA Browser Windows page");
    }

    // ========== NEW TAB SCENARIO ==========

    @When("user clicks on {string} button")
    public void userClicksOnButton(String buttonName) {
        switch (buttonName) {
            case "New Tab":
                openNewTab();
                break;
            case "New Window":
                openNewWindow();
                break;
            case "New Window Message":
                openNewWindowMessage();
                break;
            default:
                throw new IllegalArgumentException("Unknown button: " + buttonName);
        }
    }

    private void openNewTab() {
        Page newTab = mainPage.waitForPopup(() -> {
            mainPage.locator("#tabButton").click();
        });
        newTab.waitForLoadState();
        openedPages.add(newTab);
        System.out.println("✓ New tab opened: " + newTab.url());
    }

    @Then("a new tab should open")
    public void aNewTabShouldOpen() {
        Assert.assertFalse(openedPages.isEmpty(), "No new tab opened");
        Page newTab = openedPages.get(openedPages.size() - 1);
        Assert.assertNotNull(newTab, "New tab should not be null");
        Assert.assertNotEquals(mainPage.url(), newTab.url(),
                "New tab URL should be different from main page");
    }

    @And("new tab should contain {string} text")
    public void newTabShouldContainText(String expectedText) {
        Page newTab = openedPages.get(openedPages.size() - 1);
        String actualText = newTab.locator("#sampleHeading").textContent();
        Assert.assertEquals(actualText, expectedText,
                "New tab text mismatch");
        System.out.println("✓ New tab contains correct text: " + actualText);
    }

    @And("user should be able to return to main window")
    public void userShouldBeAbleToReturnToMainWindow() {
        // Close current tab/window
        Page currentPage = openedPages.get(openedPages.size() - 1);
        currentPage.close();
        openedPages.remove(currentPage);

        // Return to main window
        mainPage.bringToFront();
        BrowserUtils.bekle(1);

        System.out.println("✓ Returned to main window successfully");
    }

    // ========== NEW WINDOW SCENARIO ==========

    private void openNewWindow() {
        Page newWindow = mainPage.waitForPopup(() -> {
            mainPage.locator("#windowButton").click();
        });
        newWindow.waitForLoadState();
        openedPages.add(newWindow);
        System.out.println("✓ New window opened: " + newWindow.url());
    }

    @Then("a new window should open")
    public void aNewWindowShouldOpen() {
        Assert.assertFalse(openedPages.isEmpty(), "No new window opened");
        Page newWindow = openedPages.get(openedPages.size() - 1);
        Assert.assertTrue(newWindow.url().contains("sample"),
                "New window URL should contain 'sample'");
    }

    @And("new window URL should be correct")
    public void newWindowURLShouldBeCorrect() {
        Page newWindow = openedPages.get(openedPages.size() - 1);
        String expectedUrlPattern = ".*sample.*";
        Assert.assertTrue(newWindow.url().matches(expectedUrlPattern),
                "URL pattern mismatch. Actual: " + newWindow.url());
    }

    @And("user should be able to close new window and return to main")
    public void userShouldBeAbleToCloseNewWindowAndReturnToMain() {
        Page newWindow = openedPages.get(openedPages.size() - 1);

        // Verify window is open before closing
        Assert.assertFalse(newWindow.isClosed(), "Window should be open before closing");

        newWindow.close();
        openedPages.remove(newWindow);

        // Verify window is closed
        Assert.assertTrue(newWindow.isClosed(), "Window should be closed");

        mainPage.bringToFront();
        System.out.println("✓ New window closed and returned to main");
    }

    // ========== MESSAGE WINDOW SCENARIO ==========

    private void openNewWindowMessage() {
        Page messageWindow = mainPage.waitForPopup(() -> {
            mainPage.locator("#messageWindowButton").click();
        });
        messageWindow.waitForLoadState();
        openedPages.add(messageWindow);
        System.out.println("✓ Message window opened");
    }

    @Then("a message window should open")
    public void aMessageWindowShouldOpen() {
        Assert.assertFalse(openedPages.isEmpty(), "No message window opened");
        Page messageWindow = openedPages.get(openedPages.size() - 1);
        Assert.assertNotNull(messageWindow, "Message window should not be null");
    }

    @And("message window should contain sharing message")
    public void messageWindowShouldContainSharingMessage() {
        Page messageWindow = openedPages.get(openedPages.size() - 1);
        String message = messageWindow.locator("body").innerText();

        String expectedMessage = "Knowledge increases by sharing but not by saving. Please share this website with your friends and in your organization.";

        Assert.assertTrue(message.contains("Knowledge increases by sharing"),
                "Message should contain sharing text. Actual: " + message);

        System.out.println("✓ Message window contains correct message");
        System.out.println("  Message: " + message);
    }

    @And("user should be able to close message window")
    public void userShouldBeAbleToCloseMessageWindow() {
        if (!openedPages.isEmpty()) {
            Page messageWindow = openedPages.get(openedPages.size() - 1);
            messageWindow.close();
            openedPages.remove(messageWindow);
            mainPage.bringToFront();
            System.out.println("✓ Message window closed");
        }
    }

    @And("new window should contain {string} text")
    public void newWindowShouldContainText(String expectedText) {
        Page newWindow = openedPages.get(openedPages.size() - 1);
        String actualText = newWindow.locator("#sampleHeading").textContent();
        Assert.assertEquals(actualText, expectedText, "New window text mismatch");
    }

    // ========== ALL BUTTONS SCENARIO ==========

    @When("user tests all browser window buttons")
    public void userTestsAllBrowserWindowButtons() {
        System.out.println("\n=== Testing All Browser Window Buttons ===");

        // Test New Tab
        System.out.println("1. Testing New Tab button...");
        openNewTab();
        verifySamplePage();
        closeCurrentPage();

        // Test New Window
        System.out.println("2. Testing New Window button...");
        openNewWindow();
        verifySamplePage();
        closeCurrentPage();

        // Test New Window Message
        System.out.println("3. Testing New Window Message button...");
        openNewWindowMessage();
        verifyMessageWindow();
        closeCurrentPage();

        System.out.println("✓ All buttons tested successfully");
    }

    private void verifySamplePage() {
        Page currentPage = openedPages.get(openedPages.size() - 1);
        String text = currentPage.locator("#sampleHeading").textContent();
        Assert.assertEquals(text, "This is a sample page",
                "Sample page text mismatch");
    }

    private void verifyMessageWindow() {
        Page currentPage = openedPages.get(openedPages.size() - 1);
        String text = currentPage.locator("body").innerText();
        Assert.assertTrue(text.contains("Knowledge increases by sharing"),
                "Message window text mismatch");
    }

    private void closeCurrentPage() {
        if (!openedPages.isEmpty()) {
            Page currentPage = openedPages.get(openedPages.size() - 1);
            currentPage.close();
            openedPages.remove(currentPage);
            mainPage.bringToFront();
            BrowserUtils.bekle(1);
        }
    }

    @Then("all operations should complete successfully")
    public void allOperationsShouldCompleteSuccessfully() {
        Assert.assertTrue(openedPages.isEmpty(),
                "All opened pages should be closed");
        Assert.assertFalse(mainPage.isClosed(),
                "Main page should still be open");
        System.out.println("✓ All operations completed successfully");
    }

    // ========== NEGATIVE TESTS ==========

    @When("page is loaded")
    public void pageIsLoaded() {
        // Already handled in background
    }

    @Then("{string} should be {string}")
    public void shouldBe(String element, String state) {
        // Bu metod feature dosyasındaki Scenario Outline ile uyumlu hale getirildi
        // Ancak state parametresi string olarak geliyor, switch case ile kontrol edebiliriz
        // state parametresi "visible", "enabled", "clickable", "not exist" olabilir
        
        String selector = "";
        if (element.equals("New Tab")) selector = "#tabButton";
        else if (element.equals("New Window")) selector = "#windowButton";
        else if (element.equals("New Window Message")) selector = "#messageWindowButton";
        else selector = "text=" + element;

        switch (state) {
            case "visible":
                Assert.assertTrue(mainPage.locator(selector).isVisible(),
                        element + " should be visible");
                break;
            case "enabled":
                Assert.assertTrue(mainPage.locator(selector).isEnabled(),
                        element + " should be enabled");
                break;
            case "clickable":
                Assert.assertTrue(mainPage.locator(selector).isEnabled() &&
                                mainPage.locator(selector).isVisible(),
                        element + " should be clickable");
                break;
            case "not exist":
                Assert.assertFalse(mainPage.locator(selector).isVisible(),
                        element + " should not exist");
                break;
        }
        System.out.println("✓ " + element + " is " + state);
    }

    // ========== CLEANUP ==========

    @Then("close all browser windows")
    public void closeAllBrowserWindows() {
        // Close all opened pages
        for (Page page : openedPages) {
            if (!page.isClosed()) {
                page.close();
            }
        }
        openedPages.clear();

        // Close main browser
        if (mainPage != null && !mainPage.isClosed()) {
            mainPage.context().browser().close();
        }

        System.out.println("✓ All browser windows closed");
    }
}