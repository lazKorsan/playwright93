package webTest.demeqa.Runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

@CucumberOptions(
        features = "src/test/java/webTest/demeqa/features",
        glue = {"webTest/demeqa/Stepdefinitions", "Hooks"},
        plugin = {
                "pretty",
                "html:target/cucumber-reports/cucumber.html",
                "json:target/cucumber-reports/cucumber.json",
                "junit:target/cucumber-reports/cucumber.xml"
        },
        monochrome = true,
        //tags = "@BrowserWindows and not @WIP"
        //tags = "@IfarmeBasic",
        //tags ="@SuccesMessageReusable",
        tags = "@SuccesMessage",

        publish = true,
        dryRun = false

)
public class TestRunner extends AbstractTestNGCucumberTests {

    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}