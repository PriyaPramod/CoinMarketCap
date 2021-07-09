package com.coinmarketcap.steps;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.Assert;

import com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter;
import com.coinmarketcap.utils.Helper;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class BackendStep {

	RequestSpecification request;
	Response response;
	Response postResponse;
	int actualStatusCode;
	JSONParser parse = new JSONParser();
	ArrayList<Object> ids = new ArrayList<Object>();
	JSONObject jsonObj;
	JSONArray jsonArray;
	String contentType;
	String c_id;

	@Given("{string} responce using end point {string}")
	public void responce_using_end_point(String restMethod, String endPoint) {
		request = Helper.getRequest();
		if (restMethod.equalsIgnoreCase("get")) {
			response = request.get(endPoint);
		} else if (restMethod.equalsIgnoreCase("post")) {
			response = request.post(endPoint);
		}
	}

	@Then("I validate the Status code for {string}")
	public void i_validate_the_status_code_for(String expectedStatusCode) {
		actualStatusCode = response.getStatusCode();
		Assert.assertEquals(actualStatusCode, Integer.parseInt(expectedStatusCode), "Failure: Actual Status Code -> "
				+ actualStatusCode + " is not matching the expected status code -> " + expectedStatusCode);
	}

	@Then("Get the Ids for {string}")
	public void get_the_ids_for(String string, DataTable currenciesTogetID) {

		try {
			jsonObj = (JSONObject) parse.parse(response.asString());
		} catch (ParseException e) {
			e.printStackTrace();
		}

		List<Map<String, String>> data = currenciesTogetID.asMaps(String.class, String.class);
		List<String> currencyNames = new ArrayList<String>();
		for (int i = 0; i < data.size(); i++) {
			currencyNames.add(data.get(i).get("Currency"));
		}

		jsonArray = (JSONArray) jsonObj.get("data");
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject jsonobj_1 = (JSONObject) jsonArray.get(i);
			if (currencyNames.contains(jsonobj_1.get("name"))) {
				ids.add(jsonobj_1.get("id"));
			}
		}
	}

	@Then("Convert the above currencies to {string}")
	public void convert_the_above_currencies_to(String conversionEndPoint, DataTable currencySymbolToConvert) {
		String expectedStatusCode = "200";

		List<Map<String, String>> data = currencySymbolToConvert.asMaps(String.class, String.class);
		String currencySymbol = data.get(0).get("ConvertCurrencySymbol");

		ExtentCucumberAdapter.addTestStepLog("Converting currency price to: " + currencySymbol);

		for (Object o : ids) {
			request = Helper.getRequest();
			int value = Integer.parseInt(o.toString());
			ExtentCucumberAdapter.addTestStepLog("Converting id: " + value);

			request.queryParam("amount", 50);
			request.queryParam("id", value);
			request.queryParam("convert", currencySymbol);

			response = request.get(conversionEndPoint);

			actualStatusCode = response.getStatusCode();

			ExtentCucumberAdapter.addTestStepLog("Able to change the currency conversion for the ID " + value);

			Assert.assertEquals(actualStatusCode, Integer.parseInt(expectedStatusCode),
					"Failure: Unable to change the currency conversion for the ID " + value);

		}
	}

	@Given("Retrieve the {string} technical documentation using {string}")
	public void retrieve_the_technical_documentation_using(String id, String endPoint) {
		request = Helper.getRequest();
		request.queryParam("id", id);

		response = request.get(endPoint);
		c_id = id;
	}

	@Then("Verify for the {string} {string} {string} {string} {string}")
	public void verify_for_the(String logo, String technicalDoc, String currency, String dateAdded, String tags) {

		JsonPath jsonPathEvaluator = response.jsonPath();

		Map<Object, Object> jsonMap = jsonPathEvaluator.getMap("data");

		@SuppressWarnings("unchecked")
		Map<String, Object> dataValues = (Map<String, Object>) jsonMap.get(c_id);

		Assert.assertEquals(dataValues.get("logo"), logo, "Failure: Logo URL: " + logo + " is not present. ");
		ExtentCucumberAdapter.addTestStepLog("Logo URL: " + logo + " is present. ");

		Assert.assertEquals(dataValues.get("symbol"), currency,
				"Failure: Currency symbol: " + currency + " is not present. ");
		ExtentCucumberAdapter.addTestStepLog("Currency: " + currency + " is present. ");

		Assert.assertEquals(dataValues.get("date_added"), dateAdded,
				"Failure: Date: " + dateAdded + " is not present. ");
		ExtentCucumberAdapter.addTestStepLog("Currency: " + currency + " is present. ");

		@SuppressWarnings("unchecked")
		Map<String, Object> urls = (Map<String, Object>) dataValues.get("urls");

		boolean flag = urls.get("technical_doc").toString().contains(technicalDoc);
		Assert.assertTrue(flag, "Failure: Technical doc: " + technicalDoc + " is not present. ");
		ExtentCucumberAdapter.addTestStepLog("Technical Doc: " + technicalDoc + " is present. ");

		boolean isExpectedTagPresent = dataValues.get("tags").toString().contains(tags);
		Assert.assertTrue(isExpectedTagPresent, "Failure: Tag: " + tags + " is not present. ");
		ExtentCucumberAdapter.addTestStepLog("Tag: " + tags + " is present. ");
	}

	@Given("Retrieve the first {string} from cryptocurrency {string} and check mineable tag associated with them and correct cryptocurrencies have been printed")
	public void retrieve_the_first_from_cryptocurrency(String Occurance, String endPoint) {

		for (int i = 1; i <= Integer.parseInt(Occurance); i++) {
			request = Helper.getRequest();
			request.queryParam("id", i);
			response = request.get(endPoint);

			JsonPath jsonPathEvaluator = response.jsonPath();

			Map<Object, Object> jsonMap = jsonPathEvaluator.getMap("data");

			@SuppressWarnings("unchecked")
			Map<String, Object> dataValues = (Map<String, Object>) jsonMap.get(String.valueOf(i));

			boolean isExpectedTagPresent = dataValues.get("tags").toString().contains("mineable");
			Assert.assertTrue(isExpectedTagPresent, "Failure: Tag: mineable is not present. ");
			ExtentCucumberAdapter.addTestStepLog("Tag: mineable is associated with the ID: " + dataValues.get("id")
					+ " and Name: " + dataValues.get("name"));

			if (!dataValues.get("symbol").toString().isEmpty()) {
				ExtentCucumberAdapter.addTestStepLog("Correct crypto currencies have been present for crypto currency: "
						+ dataValues.get("symbol") + " and Name: " + dataValues.get("name"));
			} else {
				Assert.fail("Correct crypto currencies is not present for crypto currency: " + dataValues.get("symbol")
						+ " and Name: " + dataValues.get("name"));
			}

		}
	}

}
