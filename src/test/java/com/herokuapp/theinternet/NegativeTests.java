package com.herokuapp.theinternet;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class NegativeTests {
    @Parameters({ "username", "password", "expectedMessage" })
    @Test(priority = 1, groups = { "negativeTests", "smokeTests" })
    public void negativeLoginTest(String username, String password, String expectedErrorMessage) {
        System.out.println("Starting incorrectUsernameTest with " + username + " and " + password);
        // Create driver
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
        WebDriver driver = new ChromeDriver();

        // Open test page
        String url = "http://the-internet.herokuapp.com/login";
        driver.get(url);
        System.out.println("Page is opened.");

        // Maximize browser window
        driver.manage().window().maximize();

        // Enter username
        WebElement usernameElement = driver.findElement(By.id("username"));
        usernameElement.sendKeys(username);
        // Enter password
        WebElement passwordElement = driver.findElement(By.name("password"));
        passwordElement.sendKeys(password);
        // Click login button
        WebElement logInButton = driver.findElement(By.tagName("button"));
        logInButton.click();

        // Verifications
        WebElement errorMessage = driver.findElement(By.id("flash"));
        String actualErrorMessage = errorMessage.getText();
        Assert.assertTrue(actualErrorMessage.contains(expectedErrorMessage), "Actual error message does not contain expected. \nActual: " + actualErrorMessage + "\nExpected: " + expectedErrorMessage);

        // Close browser
        driver.quit();
    }


    @Test(priority = 1, groups = { "negativeTests", "smokeTests" })
    public void incorrectUsernameTest() {
        System.out.println("Starting incorrectUsernameTest");
        // Create driver
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
        WebDriver driver = new ChromeDriver();

        // Open test page
        String url = "http://the-internet.herokuapp.com/login";
        driver.get(url);
        System.out.println("Page is opened.");

        // Maximize browser window
        driver.manage().window().maximize();

        // Enter username
        WebElement username = driver.findElement(By.id("username"));
        username.sendKeys("incorrectUsername");
        // Enter password
        WebElement password = driver.findElement(By.name("password"));
        password.sendKeys("SuperSecretPassword!");
        // Click login button
        WebElement logInButton = driver.findElement(By.tagName("button"));
        logInButton.click();

        // Verifications
        WebElement errorMessage = driver.findElement(By.id("flash"));
        String expectedErrorMessage = "Your username is invalid!";
        String actualErrorMessage = errorMessage.getText();
        Assert.assertTrue(actualErrorMessage.contains(expectedErrorMessage), "Actual error message does not contain expected. \nActual: " + actualErrorMessage + "\nExpected: " + expectedErrorMessage);

        // Close browser
        driver.quit();
    }

    @Test(priority = 2, enabled = true, groups = { "negativeTests"})
    public void incorrectPasswordTest() {
        System.out.println("Starting incorrectUsernameTest");
        // Create driver
        System.setProperty("webdriver.gecko.driver", "src/main/resources/geckodriver.exe");
        WebDriver driver = new FirefoxDriver();

        // Open test page
        String url = "http://the-internet.herokuapp.com/login";
        driver.get(url);
        System.out.println("Page is opened.");

        // Maximize browser window
        driver.manage().window().maximize();

        // Enter username
        WebElement username = driver.findElement(By.id("username"));
        username.sendKeys("tomsmith");
        // Enter password
        WebElement password = driver.findElement(By.name("password"));
        password.sendKeys("incorrectPassword");
        // Click login button
        WebElement logInButton = driver.findElement(By.tagName("button"));
        logInButton.click();

        // Verifications
        WebElement errorMessage = driver.findElement(By.id("flash"));
        String expectedErrorMessage = "Your password is invalid!";
        String actualErrorMessage = errorMessage.getText();
        Assert.assertTrue(actualErrorMessage.contains(expectedErrorMessage), "Actual error password message does not contain expected. \nActual: " + actualErrorMessage + "\nExpected: " + expectedErrorMessage);

        // Close browser
        driver.quit();
    }
}
