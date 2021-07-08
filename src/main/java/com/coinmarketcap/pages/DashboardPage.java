package com.coinmarketcap.pages;

import java.util.List;

import org.apache.commons.lang3.Range;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class DashboardPage extends BasePage {

	public DashboardPage(WebDriver driver) {
		super(driver);
	}

	@FindBy(xpath = "(//p[text()='Show rows'])[1]/following-sibling::div")
	private WebElement showRowsDropDown;

	@FindBy(css = "div.tippy-box>div.tippy-content button")
	private List<WebElement> showRowsDropDownOption;

	@FindBy(css = "div.main-content table>tbody>tr")
	private List<WebElement> totalRows;

	@FindBy(xpath = "//div[contains(@class, 'scroll-initial')]//button[text()='Filters']")
	private WebElement filtersColumn;

	@FindBy(css = "div[class$='cmc-body-wrapper']>div>div>ul>li:last-child")
	private WebElement addFilterButton;

	@FindBy(css = "input[data-qa-id=\"range-filter-input-min\"]")
	private WebElement minRangeTextField;

	@FindBy(css = "input[data-qa-id=\"range-filter-input-max\"]")
	private WebElement maxRangeTextField;

	@FindBy(xpath = "//button[text()='Apply Filter']")
	private WebElement applyFilterButton;

	@FindBy(xpath = "//button[text()='Show results']")
	private WebElement showResultsButton;

	@FindBy(xpath = "//button[contains(text(), 'More Filters')]")
	private WebElement moreFilter;

	@FindBy(css = "div.main-content table>thead>tr>th")
	private List<WebElement> columnNames;

	public boolean verifyFilterRange(String filterName, String startRange, String endRange) {

		waitForPageToLoad();
		wait(2);
		long sRange = Long.valueOf(startRange);
		long eRange = Long.valueOf(endRange);

		int count = 1;

		for (WebElement column : columnNames) {
			String columnName = column.getText();

			if (columnName.equalsIgnoreCase(filterName)) {
				break;
			}
			count++;
		}

		String rowXpath = "";
		if (filterName.equalsIgnoreCase("market cap")) {
			rowXpath = "//div[@class='main-content']//table/tbody/tr/td[" + count + "]//span[2]";
		} else if (filterName.equalsIgnoreCase("price")) {
			rowXpath = "//div[@class='main-content']//table/tbody/tr/td[" + count + "]//a";
		}

		List<WebElement> filterRows = driver.findElements(By.xpath(rowXpath));

		Range<Long> rangeObject = Range.between(sRange, eRange);
		int increse = 1;

		for (WebElement rowFilterValue : filterRows) {
			String value = rowFilterValue.getText();
			value = value.replaceAll("[$]*", "");
			value = value.replaceAll("[,]*", "");
			
			if (rangeObject.contains((long) Double.parseDouble(value))) {
				print("Column " + filterName + "'s " + increse + " row value --> " + value
						+ " <-- is in between the Start range: " + startRange + " and End Range: " + endRange);
				flag = true;
			} else {
				print("Column " + filterName + "'s " + increse + " row value --> " + value
						+ " <-- is not in between the Start range: " + startRange + " and End Range: " + endRange);
				return false;
			}
		}

		return flag;
	}

	public void showResults() {
		click(showResultsButton, "Clicked on Show Results Button to show the applied filter results. ");
	}

	public void applyFilter() {
		wait(3);
		click(applyFilterButton, "Clicked on Apply Filter Button to apply the Selected Filters. ");
	}

	public void enterMinAndMaxRange(String minRange, String maxRange) {
		sendKeys(minRangeTextField, minRange);
		sendKeys(maxRangeTextField, maxRange);
		applyFilter();
	}

	public void expandFilter(String filterName) {
		String filterXpath = "//button[text()='" + filterName + "']";

		WebElement filter = driver.findElement(By.xpath(filterXpath));
		wait(1);
		click(filter, "Clicked on Filter " + filterName + " On more filters pop up. ");
	}

	public void navigateToAddFilterPopp() {
		clickFileterOnColumn();
		clickOnAddFilterButton();
	}

	public void clickOnAddFilterButton() {
		wait(2);
		click(addFilterButton, "Clicked on the Add filter button. ");
	}

	public void clickFileterOnColumn() {
		click(filtersColumn, "Clicked on the Filters Button on the Header row. ");
	}

	public void selectOptionFromShowRows(String optionToSelect) {
		clickOnShowRowsDropDown();

		if (showRowsDropDownOption.size() > 0) {
			for (WebElement option : showRowsDropDownOption) {
				String optionValue = option.getText();
				if (optionValue.equalsIgnoreCase(optionToSelect)) {
					click(option, "Selected " + optionValue + " from Show Result Drop down. ");
					break;
				}
			}
		} else
			throw new RuntimeException(
					"No option to select from the Show Rows Drop down/ Show drop down is not displayed. ");
	}

	public void clickOnShowRowsDropDown() {
		click(showRowsDropDown, "Clicked on the show rows drop down. ");
	}

	public int getNumberOfRowsDisplayed() {
		waitForPageToLoad();
		scrollDown();

		int totalNumberOfROws = totalRows.size();

		print("Total number of rows displayed is ---> " + totalNumberOfROws);
		return totalNumberOfROws;
	}

}
