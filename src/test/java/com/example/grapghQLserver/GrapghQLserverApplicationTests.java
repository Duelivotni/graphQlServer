package com.example.grapghQLserver;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.graphQLserver.datafetchers.CustomerDataFetcher;
import com.example.graphQLserver.datastore.CustomerRepository;
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

@SpringBootTest(classes = {
	DgsAutoConfiguration.class, 
	CustomerDataFetcher.class, 
	CustomerService.class,
	CustomerRepository.class})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class GrapghQLserverApplicationTests {

	@Autowired
	DgsQueryExecutor dgsQueryExecutor;

	@Test
	@Order(1)
	void addCutomer_shouldAdd_andReturnNewCustomer() throws JSONException {
		ExecutionResult executionResult = dgsQueryExecutor.execute(
			"mutation {addCustomer(input : {fullName : \"John\"" +
			" orders : [{name : \"Potatoe\" price : 15.55}] } )" + 
			" {fullName orders {name price}}}"
			);
		String expectedResultAfterUpdate = "{\"addCustomer\":" +
		"{\"fullName\":\"John\",\"orders\":[" +
		"{\"price\":15.55,\"name\":\"Potatoe\"}" +
		"]}}";

		JSONObject actualResult = new JSONObject(executionResult.getData().toString());
		assertEquals(expectedResultAfterUpdate, actualResult.toString());
	}

	@Test
	@Order(2)
	void getAllCustomers_shouldReturnFullList_ofCustomers() throws JSONException {
		ExecutionResult executionResult = dgsQueryExecutor.execute(
			"{getAllCustomers {fullName orders {name price}}}"
			);
		String expectedResult = "{\"getAllCustomers\":[" +
			"{\"fullName\":\"John\",\"orders\":[" +
			"{\"price\":15.55,\"name\":\"Potatoe\"}" +
			"]}]}";

		JSONObject actualResult = new JSONObject(executionResult.getData().toString());
		assertEquals(expectedResult, actualResult.toString());
	}

	@Test
	@Order(3)
	void getSomeCustomers_shouldReturnCustomers_withThisOrderName() throws JSONException {
		ExecutionResult executionResult = dgsQueryExecutor.execute(
			"{getCustomersWithOrder(orderName:\"Potatoe\") {fullName orders {name price }}}"
			);
		String expectedResult = "{\"getCustomersWithOrder\":[" +
			"{\"fullName\":\"John\",\"orders\":[" +
			"{\"price\":15.55,\"name\":\"Potatoe\"}" +
			"]}]}";

		JSONObject actualResult = new JSONObject(executionResult.getData().toString());
		assertEquals(expectedResult, actualResult.toString());
	}

	@Test
	@Order(4)
	void removeCutomer_shouldDelete_andReturnCustomersList() throws JSONException {
		dgsQueryExecutor.execute(
			"mutation {addCustomer(input : {fullName : \"Bob\"" +
			" orders : [{name : \"Carrot\" price : 15.55}] } )" + 
			" {fullName orders {name price}}}"
			);

		ExecutionResult executionResultAfterDelete = dgsQueryExecutor.execute(
			"mutation {removeCustomer(customerName : \"John\") " +
			"{fullName orders { name price } } }"
			);
		String expectedResultAfterDelete = "{\"removeCustomer\":[" +
		"{\"fullName\":\"Bob\",\"orders\":[" +
		"{\"price\":15.55,\"name\":\"Carrot\"}" +
		"]}]}";

		JSONObject actualResultAfterDelete = new JSONObject(executionResultAfterDelete.getData().toString());
		assertEquals(expectedResultAfterDelete, actualResultAfterDelete.toString());
	}
}
