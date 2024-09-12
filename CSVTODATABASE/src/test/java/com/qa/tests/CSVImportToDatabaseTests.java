package com.qa.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

//import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.Duration;
import java.util.concurrent.TimeUnit;


public class CSVImportToDatabaseTests {

	WebDriver driver;
	@BeforeMethod
	public void driverSetUp() {
	       	System.setProperty("webdriver.chrome.driver", "C:/Users/Saud/Git/CSVToDatabase/CSVTODATABASE/src/main/java/com/qa/chromedriver.exe");
	        driver = new ChromeDriver();
	        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
	        driver.get("http://www.facebook.com");   //url
	    }
	
	 @AfterMethod
	    public void quitDriver() {
	        driver.quit();
	    }
	 
	 
	  public void uploadCSVFile(String filePath) {
	        WebElement fileInput = driver.findElement(By.id("fileUpload")); // Assume this is the file input element
	        fileInput.sendKeys(filePath);
	        WebElement submitButton = driver.findElement(By.id("submitButton")); // Assume this is the submit button
	        submitButton.click();
	    }

	    public String getAlertMessage() {
	        WebElement messageElement = driver.findElement(By.id("message")); // Assume message element is identified by id 'message'
	        return messageElement.getText();
	    }
	    
	    
	    @Test
	    public void testValidCSVImport() {
	        uploadCSVFile("/path/to/validFile.csv");
	        
	        // Assert the success message is displayed
	        String expectedMessage = "Data imported successfully";
	        String actualMessage = getAlertMessage();
	        Assert.assertEquals(actualMessage, expectedMessage);
	    }
	    
	    
	    @Test
	    public void testInvalidDataFormatInCSV() {
	        uploadCSVFile("/path/to/invalidFormatFile.csv");

	        // Assert the error message is displayed
	        String expectedMessage = "Invalid data format detected in column 'Age'";
	        String actualMessage = getAlertMessage();
	        Assert.assertEquals(actualMessage, expectedMessage);
	    }
	    
	    
	    @Test
	    public void testIncompleteRowsInCSV() {
	        uploadCSVFile("/path/to/incompleteRows.csv");

	        // Assert the error message for missing fields is displayed
	        String expectedMessage = "Missing required fields in row 3";
	        String actualMessage = getAlertMessage();
	        Assert.assertEquals(actualMessage, expectedMessage);
	    }
	    
	    
	    @Test
	    public void testLargeCSVFileUpload() {
	        uploadCSVFile("/path/to/largeFile.csv");

	        // Assert the success message is displayed after the large file upload
	        String expectedMessage = "Data imported successfully";
	        String actualMessage = getAlertMessage();
	        Assert.assertEquals(actualMessage, expectedMessage);
	    }
	    
	    
	    @Test
	    public void testInvalidCSVFormat() {
	        uploadCSVFile("/path/to/invalidFile.txt");

	        // Assert the error message is displayed for invalid file format
	        String expectedMessage = "Invalid file format. Please upload a .csv file.";
	        String actualMessage = getAlertMessage();
	        Assert.assertEquals(actualMessage, expectedMessage);
	    }
	    
	    
	    @Test
	    public void testDatabaseConnectionError() {
	        // Assuming you simulate a DB outage before this

	        uploadCSVFile("/path/to/validFile.csv");

	        // Assert the error message is displayed when DB is unavailable
	        String expectedMessage = "Database connection error. Please try again later.";
	        String actualMessage = getAlertMessage();
	        Assert.assertEquals(actualMessage, expectedMessage);
	    }
	    
	    
	    @Test
	    public void testDuplicateDataInCSV() {
	        uploadCSVFile("/path/to/duplicateDataFile.csv");

	        // Assert the error message is displayed for duplicate entries
	        String expectedMessage = "Duplicate entries found in rows 4 and 7";
	        String actualMessage = getAlertMessage();
	        Assert.assertEquals(actualMessage, expectedMessage);
	    }
	    
	
	
}
