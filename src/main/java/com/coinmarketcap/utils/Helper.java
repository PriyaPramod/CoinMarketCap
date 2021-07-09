package com.coinmarketcap.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.Properties;
import java.util.Random;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter;
import com.aventstack.extentreports.service.ExtentService;
import com.coinmarketcap.managers.FileReaderManager;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;

public class Helper {

	private final static Random RANDOM = new SecureRandom();
	private final static String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

	public static int getRandomInt() {
		return RandomUtils.nextInt(100, 999);
	}

	public static RequestSpecification getRequest() {
		RestAssured.baseURI = FileReaderManager.getInstance().getConfigReader(IConstants.CONFIG).getProperty("baseURI");
		RequestSpecification request = RestAssured.given();
		request.header("X-CMC_PRO_API_KEY",
				FileReaderManager.getInstance().getConfigReader(IConstants.CONFIG).getProperty("APIKey"));
		request.header("Content-Type", "application/json");

		return request;
	}

	public static int getRandomInt(int startRange, int endRange) {
		return RandomUtils.nextInt(startRange, endRange);
	}

	public static byte[] getByteScreenshot(WebDriver driver) {
		File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		byte[] fileContent = new byte[0];
		try {
			fileContent = FileUtils.readFileToByteArray(src);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return fileContent;
	}

	public static String generateRandomString(int length) {
		StringBuilder returnValue = new StringBuilder(length);
		for (int i = 0; i < length; i++) {
			returnValue.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
		}
		return new String(returnValue);
	}

	public static void addSystemInfo() {
		ExtentService.getInstance().setSystemInfo("ApplicationName",
				FileReaderManager.getInstance().getConfigReader(IConstants.CONFIG).getProperty("ProjectName"));
		ExtentService.getInstance().setSystemInfo("Author", System.getProperty("user.name"));
		ExtentService.getInstance().setSystemInfo("OperatingSystem", System.getProperty("os.name"));
		ExtentService.getInstance().setSystemInfo("BrowserName",
				FileReaderManager.getInstance().getConfigReader(IConstants.CONFIG).getProperty("BrowserName"));
	}

	public static void print(String strToPrint) {
		ExtentCucumberAdapter
				.addTestStepLog("<span style=\"color: #ff0000;\"><string>Info:</strong></span>" + strToPrint);
	}

	public static String getStrongPassword() {
		return Helper.generateRandomString(3) + Helper.getRandomInt() + "*" + Helper.generateRandomString(5);
	}

	public static String getValidRandomEMail() {
		return Helper.generateRandomString(6) + "@" + Helper.generateRandomString(4) + ".com";
	}
	
	public static void setProperty(String environmentValue) {
		Properties property = new Properties();
		FileOutputStream output;
		try {
			output = new FileOutputStream(IConstants.EnvPath);
			property.setProperty("Environment", environmentValue);
			try {
				property.store(output, "");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
