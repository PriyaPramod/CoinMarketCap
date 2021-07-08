package com.coinmarketcap.steps;

import com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter;
import com.coinmarketcap.context.TestContext;
import com.coinmarketcap.utils.Helper;
import com.coinmarketcap.utils.Log;

import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

public class BaseStep {

	TestContext testContext;
	Scenario scenario;

	public BaseStep(TestContext testContext) {
		this.testContext = testContext;
	}

	@Before
	public void setUp(Scenario scenario) {
		this.scenario = scenario;
		Log.startTestCase(scenario.getName());
		ExtentCucumberAdapter.addTestStepLog("***********************************************");
		ExtentCucumberAdapter.addTestStepLog("Test execution started for Scenario --> " + scenario.getName());
		ExtentCucumberAdapter.addTestStepLog("***********************************************");
	}

	@After
	public void tearDown(Scenario scenario) {
		ExtentCucumberAdapter.addTestStepLog("***********************************************");
		ExtentCucumberAdapter.addTestStepLog("Test execution completed for Scenario --> " + scenario.getName());
		ExtentCucumberAdapter.addTestStepLog("***********************************************");

		if (scenario.isFailed()) {
			scenario.attach(Helper.getByteScreenshot(testContext.getDriverManager().getWebDriver()), "image/png",
					scenario.getName());
		}
		testContext.getDriverManager().closeBrowser();
		Log.endTestCase(scenario.getName());
	}

	@AfterStep
	public void addingScreenshotAfterEveryStep() {
		scenario.attach(Helper.getByteScreenshot(testContext.getDriverManager().getWebDriver()), "image/png",
				"Screenshot" + Helper.getRandomInt(1, 10000));
	}

}
