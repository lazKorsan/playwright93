package webTest.demeqa.Stepdefinitions;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

public class BaseSteps {

    @Before(order = 1)
    public void beforeScenario(Scenario scenario) {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("SCENARIO: " + scenario.getName());
        System.out.println("TAGS: " + scenario.getSourceTagNames());
        System.out.println("=".repeat(60));
    }

    @Before(order = 2)
    public void setupTestData() {
        System.out.println("Setting up test data...");
    }

    @After(order = 1)
    public void takeScreenshotOnFailure(Scenario scenario) {
        if (scenario.isFailed()) {
            System.out.println("❌ SCENARIO FAILED: " + scenario.getName());
            // Buraya screenshot alma kodu eklenebilir
        }
    }

    @After(order = 2)
    public void afterScenario(Scenario scenario) {
        System.out.println("\nSCENARIO STATUS: " + scenario.getStatus());
        System.out.println("=".repeat(60) + "\n");
    }
}