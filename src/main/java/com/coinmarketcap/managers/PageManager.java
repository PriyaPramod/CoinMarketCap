package com.coinmarketcap.managers;

import org.openqa.selenium.WebDriver;

import com.coinmarketcap.pages.DashboardPage;


public class PageManager {
	protected WebDriver driver;

	public PageManager(WebDriver driver) {
		this.driver = driver;
	}

	private DashboardPage dashboardPage;
	

	public DashboardPage getDashboardPage() {
		return (dashboardPage == null) ? dashboardPage = new DashboardPage(this.driver) : dashboardPage;
	}

}
