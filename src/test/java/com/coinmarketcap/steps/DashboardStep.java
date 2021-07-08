package com.coinmarketcap.steps;

import org.testng.Assert;

import com.coinmarketcap.context.TestContext;
import com.coinmarketcap.pages.DashboardPage;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class DashboardStep {

	DashboardPage dashboardPage;

	public DashboardStep(TestContext testContext) {
		dashboardPage = testContext.getPage().getDashboardPage();
	}

	@When("Select Show rows dropdown value to {string}")
	public void select_show_rows_dropdown_value_to(String optionToSelect) {
		dashboardPage.selectOptionFromShowRows(optionToSelect);
	}

	@Then("Verify {string} are displayed")
	public void verify_are_displayed(String expectedRows) {
		int actualRows = dashboardPage.getNumberOfRowsDisplayed();
		Assert.assertEquals(actualRows, Integer.parseInt(expectedRows), "Failure: Expecetd number of rows: "
				+ expectedRows + " is not displayed on the page. Instead: " + expectedRows + " is displayed. ");
	}

	@When("I add filter")
	public void i_add_filter() {
		dashboardPage.navigateToAddFilterPopp();
	}

	@Given("Filter records by {string} {string} {string} and {string} {string} {string}")
	public void filter_records_by_and(String filter1, String filterOneStartRange, String filterOneEndRange,
			String filter2, String filterTwoStartRange, String filterTwoEndRange) {
		dashboardPage.expandFilter(filter1);
		dashboardPage.enterMinAndMaxRange(filterOneStartRange, filterOneEndRange);
		dashboardPage.expandFilter(filter2);
		dashboardPage.enterMinAndMaxRange(filterTwoStartRange, filterTwoEndRange);
		dashboardPage.showResults();
	}

	@Then("Verify Check records displayed on page are correct as per the filter applied {string} {string} {string} and {string} {string} {string}")
	public void verify_check_records_displayed_on_page_are_correct_as_per_the_filter_applied(String filter1,
			String filterOneStartRange, String filterOneEndRange, String filter2, String filterTwoStartRange,
			String filterTwoEndRange) {

		Assert.assertTrue(dashboardPage.verifyFilterRange(filter1, filterOneStartRange, filterOneEndRange),
				"Failure: Filter --> " + filter1
						+ " column values in some of the rows are not in the range between Start Range: "
						+ filterOneStartRange + " End Range: " + filterOneEndRange
						+ "\n Please refer the report for detailed report. ");

		Assert.assertTrue(dashboardPage.verifyFilterRange(filter2, filterTwoStartRange, filterTwoEndRange),
				"Failure: Filter --> " + filter2
						+ " column values in some of the rows are not in the range between Start Range: "
						+ filterTwoStartRange + " End Range: " + filterTwoEndRange
						+ "\n Please refer the report for detailed report. ");
	}

}
