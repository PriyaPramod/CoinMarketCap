package com.coinmarketcap.context;

import com.coinmarketcap.managers.DriverManager;
import com.coinmarketcap.managers.PageManager;

public class TestContext {

	private PageManager pageManager;
	private DriverManager driverManager;

	public TestContext() {
		driverManager = new DriverManager();
		pageManager = new PageManager(driverManager.getWebDriver());
	}

	public DriverManager getDriverManager() {
		return driverManager;
	}

	public PageManager getPage() {
		return pageManager;
	}
}
