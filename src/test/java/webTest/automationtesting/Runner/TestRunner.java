package webTest.automationtesting.Runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions
        (
                features = "src/test/java/webTest/automationtesting/features",
                // Glue kısmında paket ismi (package name) kullanılmalıdır.
                // Hooks sınıfı da bu paketin içinde olduğu için otomatik olarak algılanacaktır.
                glue = "webTest.automationtesting.Stepdefinitions",
                plugin = {
                        "pretty",
                        "html:target/cucumber-reports/cucumber.html",
                        "json:target/cucumber-reports/cucumber.json",
                        "junit:target/cucumber-reports/cucumber.xml"
                },
                monochrome = true,
                tags = "@MultipleUpload",
                publish = true,
                dryRun = false
        )
public class TestRunner extends AbstractTestNGCucumberTests {
}
