package com.coinmarketcap.utils;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.Select;

import com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter;

public class WebDriverActions {
	WebDriver driver;
	Actions action;

	public WebDriverActions(WebDriver driver) {
		this.driver = driver;
		action = new Actions(driver);
	}

	public static String getBrowserVersion(WebDriver driver) {
		Capabilities caps = ((RemoteWebDriver) driver).getCapabilities();
		return caps.getVersion();
	}

	public static String getBrowserName(WebDriver driver) {
		Capabilities caps = ((RemoteWebDriver) driver).getCapabilities();
		return caps.getBrowserName();
	}

	public void saveScreenshot() {

	    String Base64StringOfScreenshot = "";
	    File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
	    byte[] fileContent = new byte[0];
	    try {
	        fileContent = FileUtils.readFileToByteArray(src);
	    } catch (IOException e) {
	        System.out.println(e.toString());
	    }
	    
	    Base64StringOfScreenshot = "data:image/png;base64," + java.util.Base64.getEncoder().encodeToString(fileContent);
	    attachScreenshotToExtent(Base64StringOfScreenshot);
	}

	public void attachScreenshotToExtent(String imagePath) {
	    try {
	        ExtentCucumberAdapter.addTestStepScreenCaptureFromPath(imagePath);
	    } catch (IOException e) {
	        System.out.println(e.toString());
	    }
	}  
	
	public void actionClick(WebElement element) {
		action.click(element).perform();
	}

	public void selectByText(WebElement element, String stringToSelectFromDropDown) {
		Select select = new Select(element);
		select.selectByVisibleText(stringToSelectFromDropDown);
	}
}
