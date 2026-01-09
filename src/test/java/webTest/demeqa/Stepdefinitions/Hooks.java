package webTest.demeqa.Stepdefinitions;

import Utilities.DimensonUtils;
import Utilities.WaitUtils;
import com.microsoft.playwright.Page;
import io.cucumber.java.*;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class Hooks {

    // Test verilerini saklamak için
    private static final ThreadLocal<Map<String, Object>> testData = ThreadLocal.withInitial(HashMap::new);

    // Senaryo bazlı veriler
    private static final ThreadLocal<Scenario> currentScenario = new ThreadLocal<>();
    private static final ThreadLocal<Long> scenarioStartTime = new ThreadLocal<>();

    // Browser instance'ları (parallel test için ThreadLocal)
    private static final ThreadLocal<Page> threadLocalPage = new ThreadLocal<>();

    /**
     * Senaryo başlamadan önce (en önce çalışır)
     */
    @BeforeAll
    public static void beforeAll() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("TEST SUITE BAŞLIYOR");
        System.out.println("Tarih/Saat: " + getCurrentDateTime());
        System.out.println("=".repeat(80) + "\n");

        // Screenshots klasörünü oluştur
        createDirectories();
    }

    /**
     * Her senaryodan önce (her scenario için)
     */
    @Before(order = 0)
    public void beforeScenario(Scenario scenario) {
        // Senaryo bilgilerini kaydet
        currentScenario.set(scenario);
        scenarioStartTime.set(System.currentTimeMillis());

        // Test verilerini temizle
        testData.get().clear();

        System.out.println("\n" + "═".repeat(80));
        System.out.println("🎬 SENARYO BAŞLIYOR");
        System.out.println("═".repeat(80));
        System.out.println("📝 Senaryo   : " + scenario.getName());
        System.out.println("🏷️  Tag'ler   : " + String.join(", ", scenario.getSourceTagNames()));
        System.out.println("🆔 ID        : " + scenario.getId());
        System.out.println("⏰ Başlangıç : " + getCurrentDateTime());
        System.out.println("═".repeat(80));

        // Senaryoya özel başlangıç işlemleri
        initializeScenarioData(scenario);
    }

    /**
     * Her senaryodan önce (tag bazlı before)
     */
    @Before("@browser or @ui")
    public void beforeBrowserTests() {
        System.out.println("🌐 Browser testi başlıyor...");
    }

    @Before("@api")
    public void beforeApiTests() {
        System.out.println("🔗 API testi başlıyor...");
    }

    @Before("@database")
    public void beforeDatabaseTests() {
        System.out.println("🗄️  Database testi başlıyor...");
    }

    /**
     * Her step'ten önce (opsiyonel - debug için kullanışlı)
     */
    @BeforeStep
    public void beforeStep(Scenario scenario) {
        // Debug modunda step bilgilerini logla
        if (isDebugMode()) {
            System.out.println("  └─▶ Step çalışıyor...");
        }
    }

    /**
     * Her step'ten sonra (opsiyonel)
     */
    @AfterStep
    public void afterStep(Scenario scenario) {
        // Step screenshot'ları için (opsiyonel)
        if (isTakeStepScreenshots()) {
            takeStepScreenshot();
        }
    }

    /**
     * Her senaryodan sonra (her scenario için)
     */
    @After(order = 0)
    public void afterScenario(Scenario scenario) {
        long duration = System.currentTimeMillis() - scenarioStartTime.get();

        System.out.println("\n" + "═".repeat(80));
        System.out.println("🏁 SENARYO SONUÇLARI");
        System.out.println("═".repeat(80));

        // Senaryo durumuna göre loglama
        if (scenario.isFailed()) {
            System.out.println("❌ DURUM    : BAŞARISIZ");
            System.out.println("💥 HATA     : " + scenario.getStatus());

            // Hata durumunda ek işlemler
            onTestFailure(scenario);

        } else if (scenario.getStatus() == Status.SKIPPED) {
            System.out.println("⚠️  DURUM    : ATLANDI");

        } else {
            System.out.println("✅ DURUM    : BAŞARILI");
        }

        System.out.println("⏱️  SÜRE     : " + duration + " ms (" + String.format("%.2f", duration/1000.0) + " s)");
        System.out.println("🕐 BİTİŞ    : " + getCurrentDateTime());
        System.out.println("═".repeat(80) + "\n");

        // Browser'ı kapat
        closeBrowser();

        // Temizlik
        cleanupScenarioData();
    }

    /**
     * Tag bazlı after hook'ları
     */
    @After("@browser or @ui")
    public void afterBrowserTests() {
        System.out.println("🌐 Browser testi tamamlandı");
    }

    @After("@api")
    public void afterApiTests() {
        System.out.println("🔗 API testi tamamlandı");
    }

    @After("@database")
    public void afterDatabaseTests() {
        System.out.println("🗄️  Database testi tamamlandı");
    }

    /**
     * Tüm testler bittikten sonra
     */
    @AfterAll
    public static void afterAll() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("TEST SUITE TAMAMLANDI");
        System.out.println("Bitiş: " + getCurrentDateTime());
        System.out.println("=".repeat(80));

        // Rapor oluşturma, temizlik vs.
        generateTestReport();
    }

    // ========== UTILITY METHODS ==========

    /**
     * Browser başlatma (Hooks üzerinden)
     */
    public static Page startBrowser(String browserType, String url) {
        System.out.println("🚀 Browser başlatılıyor: " + browserType);

        Page page = DimensonUtils.setUpBrowser(browserType, url, false);
        threadLocalPage.set(page);

        // Test data'ya kaydet
        testData.get().put("currentPage", page);
        testData.get().put("currentUrl", url);

        System.out.println("📍 URL: " + url);
        WaitUtils.waitForPageToLoadCompletely(page, 30);

        return page;
    }

    /**
     * Mevcut browser'ı getir
     */
    public static Page getCurrentPage() {
        return threadLocalPage.get();
    }

    /**
     * Browser'ı kapat
     */
    public static void closeBrowser() {
        Page page = threadLocalPage.get();
        if (page != null && !page.isClosed()) {
            System.out.println("🛑 Browser kapatılıyor...");
            DimensonUtils.closeBrowser(page);
            threadLocalPage.remove();
            System.out.println("✓ Browser kapatıldı");
        }
    }

    /**
     * Hata durumunda yapılacaklar
     */
    private void onTestFailure(Scenario scenario) {
        try {
            // 1. Screenshot al
            takeFailureScreenshot(scenario);

            // 2. Page source kaydet
            savePageSource(scenario);

            // 3. Console log'larını kaydet
            saveConsoleLogs(scenario);

            // 4. Network log'larını kaydet
            saveNetworkLogs(scenario);

        } catch (Exception e) {
            System.err.println("Hata durumunda ek işlemler yapılamadı: " + e.getMessage());
        }
    }

    /**
     * Hata screenshot'ı al
     */
    private void takeFailureScreenshot(Scenario scenario) {
        Page page = getCurrentPage();
        if (page != null && !page.isClosed()) {
            try {
                String screenshotName = "FAILURE_" +
                        sanitizeFileName(scenario.getName()) + "_" +
                        getCurrentDateTime("yyyyMMdd_HHmmss") + ".png";

                String screenshotPath = "target/screenshots/failures/" + screenshotName;

                page.screenshot(new com.microsoft.playwright.Page.ScreenshotOptions()
                        .setPath(Paths.get(screenshotPath))
                        .setFullPage(true));

                System.out.println("📸 Hata screenshot'ı alındı: " + screenshotPath);

                // Senaryoya ekle (Allure raporu için)
                scenario.attach(Files.readAllBytes(Paths.get(screenshotPath)),
                        "image/png", "Failure Screenshot");

            } catch (Exception e) {
                System.err.println("Screenshot alınamadı: " + e.getMessage());
            }
        }
    }

    /**
     * Step screenshot'ı al
     */
    private void takeStepScreenshot() {
        Page page = getCurrentPage();
        if (page != null) {
            try {
                String stepScreenshotName = "STEP_" + getCurrentDateTime("HHmmss_SSS") + ".png";
                page.screenshot(new com.microsoft.playwright.Page.ScreenshotOptions()
                        .setPath(Paths.get("target/screenshots/steps/" + stepScreenshotName))
                        .setFullPage(false));
            } catch (Exception e) {
                // Step screenshot hatalarını görmezden gel
            }
        }
    }

    /**
     * Page source kaydet
     */
    private void savePageSource(Scenario scenario) {
        Page page = getCurrentPage();
        if (page != null) {
            try {
                String pageSource = page.content();
                String fileName = "PAGESOURCE_" + sanitizeFileName(scenario.getName()) + "_" +
                        getCurrentDateTime("yyyyMMdd_HHmmss") + ".html";

                String filePath = "target/debug/" + fileName;
                Files.write(Paths.get(filePath), pageSource.getBytes());

                System.out.println("📄 Page source kaydedildi: " + filePath);

            } catch (Exception e) {
                System.err.println("Page source kaydedilemedi: " + e.getMessage());
            }
        }
    }

    /**
     * Console log'larını kaydet
     */
    private void saveConsoleLogs(Scenario scenario) {
        // Playwright'ın console log'larını almak için
        // Bu kısım projenizin yapılandırmasına göre değişebilir
        System.out.println("📝 Console log'ları inceleniyor...");
    }

    /**
     * Network log'larını kaydet
     */
    private void saveNetworkLogs(Scenario scenario) {
        // Network log'ları için
        System.out.println("🌐 Network trafiği inceleniyor...");
    }

    /**
     * Senaryo verilerini başlat
     */
    private void initializeScenarioData(Scenario scenario) {
        // Senaryo başlangıç zamanı
        testData.get().put("scenarioStartTime", LocalDateTime.now());

        // Senaryo adı
        testData.get().put("scenarioName", scenario.getName());

        // Random test data oluştur (örnek)
        testData.get().put("testId", "TEST_" + System.currentTimeMillis());

        System.out.println("📊 Test ID: " + testData.get().get("testId"));
    }

    /**
     * Senaryo verilerini temizle
     */
    private void cleanupScenarioData() {
        testData.get().clear();
        currentScenario.remove();
        scenarioStartTime.remove();
    }

    /**
     * Test raporu oluştur
     */
    private static void generateTestReport() {
        System.out.println("📊 Test raporu oluşturuluyor...");
        // Allure, ExtentReports vs. entegrasyonu burada yapılabilir
    }

    /**
     * Gerekli dizinleri oluştur
     */
    private static void createDirectories() {
        String[] directories = {
                "target/screenshots",
                "target/screenshots/failures",
                "target/screenshots/steps",
                "target/screenshots/success",
                "target/debug",
                "target/logs",
                "target/reports"
        };

        for (String dir : directories) {
            File directory = new File(dir);
            if (!directory.exists()) {
                directory.mkdirs();
            }
        }
    }

    /**
     * Debug modunda mı?
     */
    private boolean isDebugMode() {
        return System.getProperty("debug", "false").equalsIgnoreCase("true");
    }

    /**
     * Step screenshot'ları alınsın mı?
     */
    private boolean isTakeStepScreenshots() {
        return System.getProperty("step.screenshots", "false").equalsIgnoreCase("true");
    }

    /**
     * Dosya adı için güvenli string
     */
    private String sanitizeFileName(String fileName) {
        return fileName.replaceAll("[^a-zA-Z0-9.-]", "_");
    }

    /**
     * Mevcut tarih-saat
     */
    private static String getCurrentDateTime() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"));
    }

    private static String getCurrentDateTime(String pattern) {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(pattern));
    }

    // ========== TEST DATA MANAGEMENT ==========

    /**
     * Test data'ya değer ekle
     */
    public static void putTestData(String key, Object value) {
        testData.get().put(key, value);
    }

    /**
     * Test data'dan değer al
     */
    public static Object getTestData(String key) {
        return testData.get().get(key);
    }

    /**
     * Test data'dan string al
     */
    public static String getTestDataAsString(String key) {
        Object value = testData.get().get(key);
        return value != null ? value.toString() : null;
    }

    /**
     * Test data'dan integer al
     */
    public static Integer getTestDataAsInt(String key) {
        Object value = testData.get().get(key);
        if (value instanceof Integer) {
            return (Integer) value;
        } else if (value instanceof String) {
            try {
                return Integer.parseInt((String) value);
            } catch (NumberFormatException e) {
                return null;
            }
        }
        return null;
    }

    /**
     * Senaryo adını getir
     */
    public static String getScenarioName() {
        Scenario scenario = currentScenario.get();
        return scenario != null ? scenario.getName() : "Unknown";
    }

    /**
     * Senaryo durumunu getir
     */
    public static boolean isScenarioFailed() {
        Scenario scenario = currentScenario.get();
        return scenario != null && scenario.isFailed();
    }
}