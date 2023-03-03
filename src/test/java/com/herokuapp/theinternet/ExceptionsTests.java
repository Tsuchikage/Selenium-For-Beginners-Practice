package com.herokuapp.theinternet;

import com.google.common.base.Verify;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;

public class ExceptionsTests {
    private WebDriver driver;

    @Parameters({"browser"})
    @BeforeMethod(alwaysRun = true)
    private void setUp(@Optional("chrome") String browser) {
        // Create driver
        switch (browser) {
            case "chrome":
                System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
                driver = new ChromeDriver();
                break;
            case "firefox":
                System.setProperty("webdriver.gecko.driver", "src/main/resources/geckodriver.exe");
                driver = new FirefoxDriver();
                break;
            default:
                System.out.println("Do not know how to start " + browser + ", starting chrome instead");
                System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
                driver = new ChromeDriver();
                break;
        }
        // Maximize browser window
        driver.manage().window().maximize();
    }

    @Test
    public void noSuchElementExceptionTest(){
        //Open page
        driver.get("https://practicetestautomation.com/practice-test-exceptions/");

        //Click Add button
        WebElement addButtonElement = driver.findElement(By.id("add_btn"));
        addButtonElement.click();

        // Implicit wait
        // driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        // Explicit wait
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement row2Input = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='rows']/div[3]/div[@class='row']/label[.='Row 2']")));

        //Verify Row 2 input field is displayed
        Assert.assertTrue(row2Input.isDisplayed(), "Row 2 is not displayed");
    }

    @Test
    public void elementNotInteractableExceptionTest(){
        //Open page
        driver.get("https://practicetestautomation.com/practice-test-exceptions/");

        //Click Add button
        WebElement addButtonElement = driver.findElement(By.id("add_btn"));
        addButtonElement.click();
        //Wait for the second row to load
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement row2Input = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='rows']/div[3]/div[@class='row']/label[.='Row 2']")));

        //Type text into the second input field
        WebElement row2 = driver.findElement(By.xpath("//div[@id='rows']/div[3]/div[@class='row']/input[@type='text']"));
        row2.sendKeys("Cheese");

        //Push Save button using locator By.name(“Save”)
        WebElement saveButtonElement = driver.findElement(By.xpath("//div[@id='rows']/div[3]/div[@class='row']/button[@id='save_btn']"));
        saveButtonElement.click();

        //Verify text saved
        //WebElement successMessage = driver.findElement(By.cssSelector("#flash"));
        WebElement confirmationMessage = driver.findElement(By.xpath("/html//div[@id='confirmation']"));
        String actualMessage = confirmationMessage.getText();
        Assert.assertEquals(actualMessage, "Row 2 was saved", "Confirmation message text is not expected");
    }

    @Test
    public void invalidElementStateExceptionTest(){

        //Open page
        driver.get("https://practicetestautomation.com/practice-test-exceptions/");

        //Clear input field
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement row1Input = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='row1']/input")));
        WebElement editButton = driver.findElement(By.id("edit_btn"));
        editButton.click();

        wait.until(ExpectedConditions.elementToBeClickable(row1Input));
        row1Input.clear();

        //Type text into the input field
        row1Input.sendKeys("Sushi");
        WebElement saveButton = driver.findElement(By.id("save_btn"));
        saveButton.click();

        //Verify text changed
        String value = row1Input.getAttribute("value");
        Assert.assertEquals(value, "Sushi", "Input 1 field value is not expected");

        // Verify text saved
        WebElement confirmationMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("confirmation")));
        String actualMessage = confirmationMessage.getText();
        Assert.assertEquals(actualMessage, "Row 1 was saved", "Confirmation message text is not expected");

    }

    @Test
    public void staleElementReferenceExceptionTest( ) {
        // Test case 4: StaleElementReferenceException
        // Open page
        driver.get("https://practicetestautomation.com/practice-test-exceptions/");

        // Push add button
        WebElement addButtonElement = driver.findElement(By.id("add_btn"));
        addButtonElement.click();

        // Verify instruction text element is no longer displayed
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        Assert.assertTrue(wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("instructions"))), "Instructions are still displayed");
    }

    @Test
    public void timeoutExceptionTest( ) {
        // Test case 5: TimeoutException
        // Open page
        driver.get("https://practicetestautomation.com/practice-test-exceptions/");

        // Click Add button
        WebElement addButtonElement = driver.findElement(By.id("add_btn"));
        addButtonElement.click();

        // Wait for 3 seconds for the second input field to be displayed
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(6));
        WebElement row2Input = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='row2']/input")));

        // Verify second input field is displayed
        Assert.assertTrue(row2Input.isDisplayed(), "Row 2 is not displayed");
    }
    @AfterMethod(alwaysRun = true)
    private void tearDown() {
        // Close browser
        driver.quit();
    }
}
