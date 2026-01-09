package webTest.herokuApp.Runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions
        (
                features = "src/test/java/webTest/herokuApp/features",
                glue = {"webTest/herokuApp/Stepdefinitions", "Hooks"},
                plugin = {
                        "pretty",
                        "html:target/cucumber-reports/cucumber.html",
                        "json:target/cucumber-reports/cucumber.json",
                        "junit:target/cucumber-reports/cucumber.xml"
                },
                monochrome = true,
                tags="@DragAndDrop",
                publish = true,
                dryRun = false

        )

public class TestRunner  extends AbstractTestNGCucumberTests{


}
