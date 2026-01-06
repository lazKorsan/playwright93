package webTest.LoyalFriendCare.Runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "src/test/java/webTest/LoyalFriendCare/Features",
        glue = "webTest/LoyalFriendCare/Stepdefinitions",
        tags = "@screenShots",
        plugin = {
                "pretty",
                "html:target/cucumber-reports/cucumber.html",
                "json:target/cucumber-reports/cucumber.json"
        }
)
public class TestRunner extends AbstractTestNGCucumberTests {
}
