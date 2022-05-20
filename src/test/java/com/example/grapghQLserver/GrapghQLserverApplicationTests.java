package com.example.grapghQLserver;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.graphQLserver.datafetchers.CustomerDataFetcher;
import com.example.graphQLserver.datastore.CustomerStore;
import com.netflix.graphql.dgs.DgsQueryExecutor;
import com.netflix.graphql.dgs.autoconfig.DgsAutoConfiguration;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import graphql.ExecutionResult;

@SpringBootTest(classes = {DgsAutoConfiguration.class, CustomerDataFetcher.class, CustomerStore.class})
class GrapghQLserverApplicationTests {

	@Autowired
	DgsQueryExecutor dgsQueryExecutor;

	@Test
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
}
