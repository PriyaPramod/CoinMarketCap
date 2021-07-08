package com.coinmarketcap.managers;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;

import com.coinmarketcap.utils.IConstants;

import io.github.bonigarcia.wdm.WebDriverManager;

public class DriverManager {

	private WebDriver driver;

	public WebDriver getWebDriver() {
		if (driver == null)
			driver = createWebDriver();
		return driver;
	}

	private WebDriver createWebDriver() {

		String browserName = FileReaderManager.getInstance().getConfigReader(IConstants.CONFIG).getBrowserName();

		if (browserName.equalsIgnoreCase("chrome") || browserName.equalsIgnoreCase("ch")) {
			ChromeOptions options = new ChromeOptions();

			options.addArguments("--js-flags=--expose-gc");
			options.addArguments("--enable-precise-memory-info");
			options.addArguments("--disable-popup-blocking");
			options.addArguments("--disable-default-apps");
			options.addArguments("test-type=browser");

			Map<String, Object> prefs = new HashMap<String, Object>();
			prefs.put("credentials_enable_service", false);
			prefs.put("profile.password_manager_enabled", false);

			options.setExperimentalOption("prefs", prefs);
			options.setExperimentalOption("excludeSwitches", new String[] { "enable-automation" });

			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver(options);

		} else if (browserName.equalsIgnoreCase("firefox") || browserName.equalsIgnoreCase("ff")) {

			FirefoxProfile profile = new FirefoxProfile();
			profile.setPreference("browser.privacy.trackingprotection.menu", "always");

			FirefoxOptions firefoxOptions = new FirefoxOptions();
			firefoxOptions.setProfile(profile);
			firefoxOptions.setAcceptInsecureCerts(true);

			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver(firefoxOptions);

		}

		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(
				FileReaderManager.getInstance().getConfigReader(IConstants.CONFIG).getImplicitlyWait(),
				TimeUnit.SECONDS);
		driver.get(FileReaderManager.getInstance().getConfigReader(IConstants.CONFIG).getApplicationUrl());
		
		return driver;
	}

	public void closeBrowser() {
		driver.quit();
	}
}
