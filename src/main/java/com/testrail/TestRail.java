package com.testrail;

import java.util.Map;

import io.restassured.response.Response;

public class TestRail extends BaseTR {

	// Query Params

	private static String GET_PROJECTS = "/api/v2/get_projects";
	private static String GET_SINGLE_PROJECT = "/api/v2/get_project/%s"; // %s substitute project ID

	// Test Case EndPonits
	private static String GET_TEST_CASE_TEMPLATE = "/api/v2/get_case_fields";
	private static String GET_TEST_CASE = "/api/v2/get_case/%s"; // %s substitute Test Case ID
	private static String ADD_TEST_CASE = "/api/v2/add_case/%s"; // %s substitute Section ID
	private static String  GET_CASE_FIELDS="/api/v2/get_case_fields";

	// Suite EndPonits

	private static String ADD_SUITE = "/api/v2/add_suite/%s"; // %s substitute project ID
	private static String ADD_SUITE_SECTION = "/api/v2/add_section/%s"; // %s substitute Project ID
	

	public TestRail() {
		baseRequest.headers("Content-Type", "application/json");
	}


	
	public Response getCaseField() {
		baseRequest.queryParam(GET_CASE_FIELDS);
		return GET();
	}
	
	public Response addSuite(String ProjectID, Map<String, String> SuiteDetails) {
		baseRequest.queryParam(String.format(ADD_SUITE, ProjectID));
		return POST(SuiteDetails);
	}

	public Response addSection(String ProjectID, Map<String, String> SectionDetails) {

		baseRequest.queryParam(String.format(ADD_SUITE_SECTION, ProjectID));
		return POST(SectionDetails);

	}

	public Response addTestCase(String SectionID, Map<String, Object> TestCaseDetails) {

		baseRequest.queryParam(String.format(ADD_TEST_CASE, SectionID));
		return POST(TestCaseDetails);
	}

}
