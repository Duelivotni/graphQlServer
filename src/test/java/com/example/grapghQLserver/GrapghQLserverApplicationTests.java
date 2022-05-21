package com.example.grapghQLserver;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.graphQLserver.datafetchers.CustomerDataFetcher;
import com.example.graphQLserver.datastore.CustomerService;
import com.netflix.graphql.dgs.DgsQueryExecutor;
import com.netflix.graphql.dgs.autoconfig.DgsAutoConfiguration;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import graphql.ExecutionResult;

@SpringBootTest(classes = {DgsAutoConfiguration.class, CustomerDataFetcher.class, CustomerService.class})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class GrapghQLserverApplicationTests {

	@Autowired
	DgsQueryExecutor dgsQueryExecutor;

	@Test
	@Order(1)
	void getAllCustomers_shouldReturnFullList_ofCustomers() throws JSONException {
		ExecutionResult executionResult = dgsQueryExecutor.execute(
			"{getAllCustomers {fullName orders {name price}}}"
			);
		String expectedResult = "{\"getAllCustomers\":[" +
			"{\"fullName\":\"John\",\"orders\":[" +
			"{\"price\":15.55,\"name\":\"Potatoe\"}," +
			"{\"price\":16.2,\"name\":\"Cucumber\"}]}," +
			"{\"fullName\":\"Matt\",\"orders\":[" +
			"{\"price\":12.6,\"name\":\"Tomato\"},"+
			"{\"price\":16.2,\"name\":\"Carrot\"}" +
			"]}]}";

		JSONObject actualResult = new JSONObject(executionResult.getData().toString());
		assertEquals(expectedResult, actualResult.toString());
	}

	@Test
	@Order(2)
	void getSomeCustomers_shouldReturnCustomers_withThisOrderName() throws JSONException {
		ExecutionResult executionResult = dgsQueryExecutor.execute(
			"{getCustomersWithOrder(orderName:\"Potatoe\") {fullName orders {name price }}}"
			);
		String expectedResult = "{\"getCustomersWithOrder\":[" +
			"{\"fullName\":\"John\",\"orders\":[" +
			"{\"price\":15.55,\"name\":\"Potatoe\"}," +
			"{\"price\":16.2,\"name\":\"Cucumber\"}" +
			"]}]}";

		JSONObject actualResult = new JSONObject(executionResult.getData().toString());
		assertEquals(expectedResult, actualResult.toString());
	}

	@Test
	@Order(3)
	void addCutomer_shouldAdd_andReturnNewCustomer() throws JSONException {
		ExecutionResult executionResult = dgsQueryExecutor.execute(
			"mutation {addCustomer(input : {fullName : \"newCustomerName\"" +
			" orders : [{name : \"orderName\" price : 99.99}] } )" + 
			" {fullName orders {name price}}}"
			);
		String expectedResultAfterUpdate = "{\"addCustomer\":" +
		"{\"fullName\":\"newCustomerName\",\"orders\":[" +
		"{\"price\":99.99,\"name\":\"orderName\"}" +
		"]}}";

		JSONObject actualResult = new JSONObject(executionResult.getData().toString());
		assertEquals(expectedResultAfterUpdate, actualResult.toString());
	}

	@Test
	@Order(4)
	void removeCutomer_shouldDelete_andReturnCustomersList() throws JSONException {
		ExecutionResult executionResultAfterDelete = dgsQueryExecutor.execute(
			"mutation {removeCustomer(customerName : \"newCustomerName\") " +
			"{fullName orders { name price } } }"
			);
		String expectedResultAfterDelete = "{\"removeCustomer\":[" +
		"{\"fullName\":\"John\",\"orders\":[" +
		"{\"price\":15.55,\"name\":\"Potatoe\"}," +
		"{\"price\":16.2,\"name\":\"Cucumber\"}]}," +
		"{\"fullName\":\"Matt\",\"orders\":[" +
		"{\"price\":12.6,\"name\":\"Tomato\"},"+
		"{\"price\":16.2,\"name\":\"Carrot\"}" +
		"]}]}";

		JSONObject actualResultAfterDelete = new JSONObject(executionResultAfterDelete.getData().toString());
		assertEquals(expectedResultAfterDelete, actualResultAfterDelete.toString());
	}
}
