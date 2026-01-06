package webTest.TestAutamationPractice.Runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "src/test/java/webTest/TestAutamationPractice/features",
        glue = "webTest/TestAutamationPractice/Stepdefinitions",
        tags = "@formDoldurma",
        dryRun = false,
        monochrome = true,
        publish = true,
        plugin = {
                "pretty",
                "html:target/cucumber-reports/test-automation-practice.html",
                "json:target/cucumber-reports/test-automation-practice.json"
        }
)
public class TestRunner extends AbstractTestNGCucumberTests {
}
