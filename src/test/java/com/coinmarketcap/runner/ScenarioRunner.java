package com.coinmarketcap.runner;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import com.coinmarketcap.utils.Helper;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

/**
 * @author PRAMOD KS
 */
@CucumberOptions(features = {
		"src/test/resources/features/" }, tags = "@CoinMarketCap", glue = "com.coinmarketcap.steps", plugin = {
				"com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:",
				"pretty" }, monochrome = true, publish = true)
public class ScenarioRunner extends AbstractTestNGCucumberTests {

	@BeforeClass

	public void beforeClass() {

	}

	@AfterClass
	public void afterClass() {
		Helper.addSystemInfo();
	}

}
