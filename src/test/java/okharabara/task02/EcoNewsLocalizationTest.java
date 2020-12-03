package okharabara.task02;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class EcoNewsLocalizationTest {
    private final String BASE_URL = "https://ita-social-projects.github.io/GreenCityClient/#/news";
    private final Long IMPLICITLY_WAIT_SECONDS = 5L;
    private Map<String, Map<String, String>> localization;
    private WebDriver driver;
    Utils utils = new Utils();

    private void initLocalization() {
        localization = new HashMap<>();
        localization.put("Ua", getUaLocalizationData());
        localization.put("Ru", getRuLocalizationData());
        localization.put("En", getEnLocalizationData());
    }

    private Map<String, String> getUaLocalizationData() {
        Map<String, String> uaLocalizationMap = new HashMap<>();
        uaLocalizationMap.put("main-header", "Еко новини");
        uaLocalizationMap.put("div.wrapper>span", "Фільтрувати за");
        uaLocalizationMap.put("div.wrapper a.ng-star-inserted", "Реклама Події Новиини Освіта Ініціативи Лайфхаки");
        uaLocalizationMap.put("div.main-wrapper p", "40 новин знайдено");
        uaLocalizationMap.put(".sign-in-link.tertiary-global-button.last-nav-item", "Увійти");
        uaLocalizationMap.put(".sign-up-link.ng-star-inserted", "Зареєструватись");
        uaLocalizationMap.put("//div[@class = 'navigation-menu-left']/ul/li/a[contains(@href,'news')]", "Еко новини");
        uaLocalizationMap.put("//div[@class = 'navigation-menu-left']/ul/li/a[contains(@href,'tips')]", "Поради");
        uaLocalizationMap.put("//div[@class = 'navigation-menu-left']/ul/li/a[contains(@href,'map')]", "Карта");
        uaLocalizationMap.put("//div[@class = 'navigation-menu-left']/ul/li/a[contains(@href,'about')]", "Про нас");
        uaLocalizationMap.put("//div[@class = 'navigation-menu-left']/ul/li/a[contains(@href,'profile')]", "Мій кабінет");
        return uaLocalizationMap;
    }

    private Map<String, String> getRuLocalizationData() {
        Map<String, String> ruLocalizationMap = new HashMap<>();
        ruLocalizationMap.put("main-header", "Эко новости");
        ruLocalizationMap.put("div.wrapper>span", "Фильтровать по");
        ruLocalizationMap.put("div.wrapper a.ng-star-inserted", "Реклама События Новости Оброзование Инициативы Лайфхаки");
        ruLocalizationMap.put("div.main-wrapper p", "40 новостей найдено");
        ruLocalizationMap.put(".sign-in-link.tertiary-global-button.last-nav-item", "Войти");
        ruLocalizationMap.put(".sign-up-link.ng-star-inserted", "Зарегистрироваться");
        ruLocalizationMap.put("//div[@class = 'navigation-menu-left']/ul/li/a[contains(@href,'news')]", "Эко новости");
        ruLocalizationMap.put("//div[@class = 'navigation-menu-left']/ul/li/a[contains(@href,'tips')]", "Эко советы");
        ruLocalizationMap.put("//div[@class = 'navigation-menu-left']/ul/li/a[contains(@href,'map')]", "Карта");
        ruLocalizationMap.put("//div[@class = 'navigation-menu-left']/ul/li/a[contains(@href,'about')]", "О нас");
        ruLocalizationMap.put("//div[@class = 'navigation-menu-left']/ul/li/a[contains(@href,'profile')]", "Мой кабинет");
        return ruLocalizationMap;
    }

    private Map<String, String> getEnLocalizationData() {
        Map<String, String> enLocalizationMap = new HashMap<>();
        enLocalizationMap.put("main-header", "Eco news");
        enLocalizationMap.put("div.wrapper>span", "Filter by");
        enLocalizationMap.put("div.wrapper a.ng-star-inserted", "Ads Events News Education Initiatives Lifehacks");
        enLocalizationMap.put("div.main-wrapper p", "40 items found");
        enLocalizationMap.put(".sign-in-link.tertiary-global-button.last-nav-item", "Sign in");
        enLocalizationMap.put(".sign-up-link.ng-star-inserted", "Sign up");
        enLocalizationMap.put("//div[@class = 'navigation-menu-left']/ul/li/a[contains(@href,'news')]", "Eco news");
        enLocalizationMap.put("//div[@class = 'navigation-menu-left']/ul/li/a[contains(@href,'tips')]", "Tips & Tricks");
        enLocalizationMap.put("//div[@class = 'navigation-menu-left']/ul/li/a[contains(@href,'map')]", "Places");
        enLocalizationMap.put("//div[@class = 'navigation-menu-left']/ul/li/a[contains(@href,'about')]", "About us");
        enLocalizationMap.put("//div[@class = 'navigation-menu-left']/ul/li/a[contains(@href,'profile')]", "My habits");
        return enLocalizationMap;
    }

    @BeforeSuite
    public void beforeSuite() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeClass
    public void beforeClass() {
        initLocalization();
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(IMPLICITLY_WAIT_SECONDS, TimeUnit.SECONDS);
    }

    @AfterClass(alwaysRun = true)
    public void afterClass() {
        driver.quit();
    }

    @BeforeMethod
    public void beforeMethod() {
        driver.get(BASE_URL);
    }

    @AfterMethod
    public void afterMethod(ITestResult result) {
        if (!result.isSuccess()) {
            utils.takeScreenShot(result.getName());
            utils.takePageSource(result.getName());
            driver.manage().deleteAllCookies();
        }
    }

    @DataProvider
    public Object[][] dataLocalization() {
        return new Object[][]{
                {"Ua"},
                {"En"},
                {"Ru"},
        };
    }

    @Test(dataProvider = "dataLocalization")
    public void verifyEcoNewsTitle(String localizationName) {
        String expectedResult = localization.get(localizationName).get("main-header");
        utils.getChangedLanguage(localizationName).click();
        driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        WebDriverWait webDriverWait = new WebDriverWait(driver, 10);
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class = 'cont']/h1[text()='" + expectedResult + "']")));
        driver.manage().timeouts().implicitlyWait(IMPLICITLY_WAIT_SECONDS, TimeUnit.SECONDS);
        String actualResult = driver.findElement(By.className("main-header")).getText().trim();
        Assert.assertEquals(actualResult, expectedResult);
    }

    @Test(dataProvider = "dataLocalization")
    public void verifyFilterMainTitle(String localizationName) {
        String expectedResult = localization.get(localizationName).get("div.wrapper>span");
        utils.getChangedLanguage(localizationName).click();
        driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        WebDriverWait webDriverWait = new WebDriverWait(driver, 10);
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='wrapper']/span[text() = '" + expectedResult + "']")));
        driver.manage().timeouts().implicitlyWait(IMPLICITLY_WAIT_SECONDS, TimeUnit.SECONDS);
        String actualResult = driver.findElement(By.cssSelector("div.wrapper>span")).getText().trim();
        Assert.assertEquals(actualResult, expectedResult);
    }

    @Test(dataProvider = "dataLocalization")
    public void verifyItemsFound(String localizationName) {
        String expectedResult = localization.get(localizationName).get("div.main-wrapper p");
        utils.getChangedLanguage(localizationName).click();
        driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        WebDriverWait webDriverWait = new WebDriverWait(driver, 10);
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='main-wrapper']/app-remaining-count/p[contains(text(), '" + expectedResult + "')]")));
        driver.manage().timeouts().implicitlyWait(IMPLICITLY_WAIT_SECONDS, TimeUnit.SECONDS);
        String actualResult = driver.findElement(By.cssSelector("div.main-wrapper p")).getText().trim();
        Assert.assertEquals(actualResult, expectedResult);
    }

    @Test(dataProvider = "dataLocalization")
    public void verifyFilters(String localizationName) {
        List<String> expectedResult = Arrays.asList(localization.get(localizationName).get("div.wrapper a.ng-star-inserted").split(" "));
        utils.getChangedLanguage(localizationName).click();
        driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        WebDriverWait webDriverWait = new WebDriverWait(driver, 10);
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ul[@class = 'ul-eco-buttons']/a/li[contains(text(),'" + expectedResult.get(0) + "')]")));
        driver.manage().timeouts().implicitlyWait(IMPLICITLY_WAIT_SECONDS, TimeUnit.SECONDS);
        List<WebElement> filters = driver.findElements(By.cssSelector("div.wrapper a.ng-star-inserted"));
        List<String> actualResult = new ArrayList();
        for (WebElement filter : filters) {
            actualResult.add(filter.getText());
        }
        Assert.assertEquals(actualResult, expectedResult);
    }

    @Test(dataProvider = "dataLocalization")
    public void verifyEcoNewsMenu(String localizationName) {
        String expectedResult = localization.get(localizationName).get("//div[@class = 'navigation-menu-left']/ul/li/a[contains(@href,'news')]");
        utils.getChangedLanguage(localizationName).click();
        driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        WebDriverWait webDriverWait = new WebDriverWait(driver, 10);
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class = 'navigation-menu-left']/ul/li/a[contains(@href,'news')][contains(text(),'" + expectedResult + "')]")));
        driver.manage().timeouts().implicitlyWait(IMPLICITLY_WAIT_SECONDS, TimeUnit.SECONDS);
        String actualResult = driver.findElement(By.xpath("//div[@class = 'navigation-menu-left']/ul/li/a[contains(@href,'news')]")).getText().trim();
        Assert.assertEquals(actualResult, expectedResult);
    }

    @Test(dataProvider = "dataLocalization")
    public void verifyTipsMenu(String localizationName) {
        String expectedResult = localization.get(localizationName).get("//div[@class = 'navigation-menu-left']/ul/li/a[contains(@href,'tips')]");
        utils.getChangedLanguage(localizationName).click();
        driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        WebDriverWait webDriverWait = new WebDriverWait(driver, 10);
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class = 'navigation-menu-left']/ul/li/a[contains(@href,'tips')][contains(text(),'" + expectedResult + "')]")));
        driver.manage().timeouts().implicitlyWait(IMPLICITLY_WAIT_SECONDS, TimeUnit.SECONDS);
        String actualResult = driver.findElement(By.xpath("//div[@class = 'navigation-menu-left']/ul/li/a[contains(@href,'tips')]")).getText().trim();
        Assert.assertEquals(actualResult, expectedResult);
    }

    @Test(dataProvider = "dataLocalization")
    public void verifyPlacesMenu(String localizationName) {
        String expectedResult = localization.get(localizationName).get("//div[@class = 'navigation-menu-left']/ul/li/a[contains(@href,'map')]");
        utils.getChangedLanguage(localizationName).click();
        driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        WebDriverWait webDriverWait = new WebDriverWait(driver, 10);
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class = 'navigation-menu-left']/ul/li/a[contains(@href,'map')][contains(text(),'" + expectedResult + "')]")));
        driver.manage().timeouts().implicitlyWait(IMPLICITLY_WAIT_SECONDS, TimeUnit.SECONDS);
        String actualResult = driver.findElement(By.xpath("//div[@class = 'navigation-menu-left']/ul/li/a[contains(@href,'map')]")).getText().trim();
        Assert.assertEquals(actualResult, expectedResult);
    }

    @Test(dataProvider = "dataLocalization")
    public void verifyAboutUsMenu(String localizationName) {
        String expectedResult = localization.get(localizationName).get("//div[@class = 'navigation-menu-left']/ul/li/a[contains(@href,'about')]");
        utils.getChangedLanguage(localizationName).click();
        driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        WebDriverWait webDriverWait = new WebDriverWait(driver, 10);
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class = 'navigation-menu-left']/ul/li/a[contains(@href,'about')][contains(text(),'" + expectedResult + "')]")));
        driver.manage().timeouts().implicitlyWait(IMPLICITLY_WAIT_SECONDS, TimeUnit.SECONDS);
        String actualResult = driver.findElement(By.xpath("//div[@class = 'navigation-menu-left']/ul/li/a[contains(@href,'about')]")).getText().trim();
        Assert.assertEquals(actualResult, expectedResult);
    }

    @Test(dataProvider = "dataLocalization")
    public void verifyOwnHabitsMenu(String localizationName) {
        String expectedResult = localization.get(localizationName).get("//div[@class = 'navigation-menu-left']/ul/li/a[contains(@href,'profile')]");
        utils.getChangedLanguage(localizationName).click();
        driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        WebDriverWait webDriverWait = new WebDriverWait(driver, 10);
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class = 'navigation-menu-left']/ul/li/a[contains(@href,'profile')][contains(text(),'" + expectedResult + "')]")));
        driver.manage().timeouts().implicitlyWait(IMPLICITLY_WAIT_SECONDS, TimeUnit.SECONDS);
        String actualResult = driver.findElement(By.xpath("//div[@class = 'navigation-menu-left']/ul/li/a[contains(@href,'profile')]")).getText().trim();
        Assert.assertEquals(actualResult, expectedResult);
    }

    @Test(dataProvider = "dataLocalization")
    public void verifySignInLocalization(String localizationName) {
        String expectedResult = localization.get(localizationName).get(".sign-in-link.tertiary-global-button.last-nav-item");
        utils.getChangedLanguage(localizationName).click();
        driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        WebDriverWait webDriverWait = new WebDriverWait(driver, 10);
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//li[@class = 'sign-in-link tertiary-global-button last-nav-item']/a[contains(text(),'" + expectedResult + "')]")));
        driver.manage().timeouts().implicitlyWait(IMPLICITLY_WAIT_SECONDS, TimeUnit.SECONDS);
        String actualResult = driver.findElement(By.cssSelector(".sign-in-link.tertiary-global-button.last-nav-item")).getText().trim();
        Assert.assertEquals(actualResult, expectedResult);
    }

    @Test(dataProvider = "dataLocalization")
    public void verifySignUpLocalization(String localizationName) {
        String expectedResult = localization.get(localizationName).get(".sign-up-link.ng-star-inserted");
        utils.getChangedLanguage(localizationName).click();
        driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        WebDriverWait webDriverWait = new WebDriverWait(driver, 10);
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//li[@class='sign-up-link ng-star-inserted']/div/span[contains(text(),'" + expectedResult + "')]")));
        driver.manage().timeouts().implicitlyWait(IMPLICITLY_WAIT_SECONDS, TimeUnit.SECONDS);
        String actualResult = driver.findElement(By.cssSelector(".sign-up-link.ng-star-inserted")).getText().trim();
        Assert.assertEquals(actualResult, expectedResult);
    }
}