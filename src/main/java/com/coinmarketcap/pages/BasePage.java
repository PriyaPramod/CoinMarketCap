package com.coinmarketcap.pages;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter;
import com.coinmarketcap.managers.FileReaderManager;
import com.coinmarketcap.utils.IConstants;
import com.coinmarketcap.utils.Log;
import com.coinmarketcap.utils.WebDriverActions;

public class BasePage {
	protected WebDriver driver;
	protected WebDriverWait wait;
	protected JavascriptExecutor jS;

	List<String> listStr = new ArrayList<String>();
	protected boolean flag;
	protected WebDriverActions webActions;

	public BasePage(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver,
				FileReaderManager.getInstance().getConfigReader(IConstants.CONFIG).getExplicitWait());
		driver.manage().timeouts().pageLoadTimeout(
				FileReaderManager.getInstance().getConfigReader(IConstants.CONFIG).getImplicitlyWait(),
				TimeUnit.SECONDS);
		jS = (JavascriptExecutor) driver;
		webActions = new WebDriverActions(driver);
		PageFactory.initElements(driver, this);
	}

	/**
	 * This function is used to wait for element till it become visible on the UI.
	 *
	 * @param elementToWait
	 * @return boolean value
	 * @throws Exception, if the element is not visible with in specified time.
	 * @author pramod.ks
	 */
	protected boolean visibilityOfElement(WebElement elementToWait) {
		boolean flag = false;
		try {
			wait.until(ExpectedConditions.visibilityOf(elementToWait));
			flag = true;
		} catch (Exception timeoutException) {

		}
		return flag;
	}

	public void wait(int seconds) {
		try {
			Thread.sleep(seconds * 1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected void sendKeys(WebElement element, String value) {
		clearField(element);
		try {
			element.sendKeys(value);
			print("Typed the value to text box using Webdriver method sendKeys");
		} catch (Exception e) {
			try {
				jsSendKeys(element, value);
				print("Typed the value to text box using JavScript method value");
			} catch (Exception jsExp) {
				throw new RuntimeException(String.format("Error in sending [%s] to the following element", value));
			}
		}
	}

	private void clearField(WebElement element) {
		try {
			element.clear();
		} catch (Exception e) {
			System.out.print(String.format("The following element could not be cleared: [%s]", element.getText()));
		}
	}

	protected void scrollToElement(WebElement element) {
		try {
			waitForPageToLoad();
			jS.executeScript("arguments[0].scrollIntoView();", element);
		} catch (Exception e) {
			throw new RuntimeException(String.format("Failed to scroll to the element: [%s]", element));
		}
	}

	protected void scrollDown() {
		try {
			waitForPageToLoad();
			jS.executeScript("window.scrollTo(0, document.body.scrollHeight)");
		} catch (Exception e) {
			throw new RuntimeException("Failed to scroll to the element: ");
		}
	}

	protected void click(WebElement element, String comment) {
		try {
			element.click();
			print(comment);
		} catch (Exception e) {
			try {
				jsClick(element);
				print(comment);
			} catch (Exception jsExp) {
				throw new RuntimeException(String.format(
						"The following element is not clickable: [%s], using click and JavaScript click. ", element));
			}
		}
	}

	protected void jsClick(WebElement element) {
		try {
			jS.executeScript("arguments[0].click()", element);
		} catch (Exception exp) {
			throw new RuntimeException(
					String.format("The following element is not clickable: [%s] [\n%s]", element, exp.toString()));
		}
	}

	protected void jsSendKeys(WebElement element, String valueToType) {
		waitForPageToLoad();
		try {
			jS.executeScript("arguments[0].value=arguments[1]", element, valueToType);
		} catch (Exception exp) {
			throw new RuntimeException(
					String.format("unable to enter the value to text box: [%s] [\n%s]", element, exp.toString()));
		}
	}

	/**
	 * This function is used to wait for element till it become visible and click on
	 * the UI.
	 *
	 * @param eleToCheck Element
	 * @throws TimeoutException exception, if the element is not visible with in
	 *                          specified time.
	 * @author pramod.ks
	 */
	protected Boolean elementClickable(WebElement eleToCheck) {
		Boolean flag = false;
		try {
			wait.until(ExpectedConditions.elementToBeClickable(eleToCheck));
			flag = true;
		} catch (TimeoutException exp) {
			System.out.printf("%s is not visible", exp);
		}
		return flag;
	}

	public void waitForPageToLoad() {
		jS.executeScript("var callback = arguments[arguments.length - 1];" + "var xhr = new XMLHttpRequest();"
				+ "xhr.open('GET', '/Ajax_call', true);" + "xhr.onreadystatechange = function() {"
				+ "  if (xhr.readyState == 4) {" + "    callback(xhr.responseText);" + "  }" + "};" + "xhr.send();");
	}

	protected void print(String strToPrint) {
		ExtentCucumberAdapter
		.addTestStepLog("<span style=\"color: #ff0000;\"><string>Info:</strong></span>" + strToPrint);
		Log.info(strToPrint);
	}

}
