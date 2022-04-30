package stepdefinitions;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class FrontEndTasks {
    static WebDriver driver;
    static String chromeDriverPath = "driver/chromedriver.exe";
    static WebDriverWait wait;

    static WebDriver getDriver() {
        if (driver != null) {
            return driver;
        } else {
            throw new IllegalStateException("Driver has not been initialized.");
        }
    }

    public static void init() {
        ChromeOptions op = new ChromeOptions();
        if (driver == null) {
            System.setProperty("webdriver.chrome.driver", chromeDriverPath);
            op.addArguments("--start-maximized");
            driver = new ChromeDriver(op);
        } else {
            throw new IllegalStateException("Driver has already been initialized. Quit it before using this method");
        }

        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
    }

    public static void end() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }

    @Given("^I launch Coin Market Website$")
    public void I_Launch_coinmarketcap_website() {
        init();
        driver.get("https://coinmarketcap.com/");
    }

    @When("^Select (\\d+) rows to be viewed from website$")
    public void select_rows_to_be_viewed_from_website(int rowNum) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,250)", "");

        getDriver().findElement(By.className("table-control-page-sizer")).findElement(By.tagName("div")).click();

        wait = new WebDriverWait(getDriver(), 20);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("tippy-box")));

        List<WebElement> buttonList = getDriver().findElement(By.className("tippy-box")).findElements(By.tagName("button"));
        for (WebElement button : buttonList) {
            if (button.getText().equals(rowNum)) {
                button.click();
                break;
            }
        }
    }

    @Then("^I should see 100 rows displayed on the page$")
    public void verify_that_results_are_displayed() {
        WebElement tableBody = getDriver().findElement(By.className("cmc-table")).findElement(By.tagName("tbody"));
        List<WebElement> tableBodyRows = tableBody.findElements(By.tagName("tr"));
        for (WebElement row : tableBodyRows)
            Assert.assertTrue(row.isDisplayed());
        end();
    }

    @When("^I click on filter button$")
    public void I_click_on_filter_button() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,250)", "");
        getDriver().findElement(By.className("cmc-cookie-policy-banner__close")).click();
        getDriver().findElement(By.xpath("//body/div[@id='__next']/div[1]/div[1]/div[2]/div[1]/div[1]/div[4]/div[2]/div[3]/div[2]/button[1]")).click();
    }

    @Then("^I click on Add Filter button$")
    public void I_click_on_add_Filter() {
    getDriver().findElement(By.className("VtFau")).click();
 }

    @When("^I filter records by MarketCap 1B to 10B$")
    public void I_filter_records_by_MarketCap() {
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        getDriver().findElement(By.xpath("//body/div[@id='__next']/div[1]/div[1]/div[2]/div[1]/div[1]/div[6]/div[1]/div[1]/div[2]/div[1]/div[2]/button[1]/div[1]")).click();
        getDriver().findElement(By.xpath("//button[contains(text(),'$1B - $10B')]")).click();
       // getDriver().findElement(By.xpath("//body/div[@id='__next']/div[1]/div[1]/div[2]/div[1]/div[1]/div[6]/div[1]/div[1]/div[2]/div[1]/div[2]/button[1]/div[1]")).click();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    @Then("^I click on Apply Filter button$")
    public void I_click_on_Apply_Filter_button() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,250)", "");
        getDriver().findElement(By.xpath("//button[contains(text(),'Apply Filter')]")).click();
    }

    @When("^I filter records by price 101 to 1000$")
    public void I_filter_records_by_Price() {
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        getDriver().findElement(By.xpath("//body/div[@id='__next']/div[1]/div[1]/div[2]/div[1]/div[1]/div[6]/div[1]/div[1]/div[2]/div[1]/div[3]/button[1]")).click();
        getDriver().findElement(By.xpath("//button[contains(text(),'$101 - $1,000')]")).click();
    }

 @Then("^I click on Apply Filter button on Price$")
    public void I_click_on_Apply_Filter_button_on_Price() {
        I_click_on_Apply_Filter_button();
    }

 @Then("^I click on Show result Filter button$")
    public void I_click_on_show_result_button() {
     getDriver().findElement(By.xpath("//button[contains(text(),'Show results')]")).click();
    }


}
