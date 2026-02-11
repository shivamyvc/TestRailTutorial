package com.testrail;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class MainRunner {
	public static final String ProjectId="2";
	public static void main(String[] args) {
		// TODO Auto-generated method stub


		
		Map<String,String> suiteDetail= new HashMap<String, String>();
		
		suiteDetail.put("name","11-Feb-Suite Sample Suite");
		suiteDetail.put("description","");
		
		TestRail testRail= new TestRail();
//		Response suiteResp=testRail.addSuite(ProjectId, suiteDetail);
//		suiteResp.prettyPrint();
		
		
		String SuiteID="15";
		Map<String,String> sectionDetail= new HashMap<String, String>();
		
		sectionDetail.put("name","Section Name Add this ");
		sectionDetail.put("description","");
		sectionDetail.put("suite_id", SuiteID);
//		Response sectionResp=testRail.addSection(ProjectId, sectionDetail);
//		
//		sectionResp.prettyPrint();
		String SectionID="32";
		
//		
		Map<String,Object> testData= new HashMap<String, Object>();
		Map<String,String> steps= new HashMap<String, String>();
		List<Map<String, String>> testSteps= new ArrayList<Map<String,String>>();
		
		
		
		steps.put("content", "Log in using the URL with your user credentials");
		steps.put("expected", "Able to successfully log in to the application");
		testSteps.add(steps);
		testData.put("title", "Test Title");
		testData.put("template_id", 2);
		testData.put("type_id", 4);
		testData.put("custom_steps_separated", testSteps);
		testData.put("custom_case_custom_milestone_id", 3);
		
		Response testResp=testRail.addTestCase(SectionID, testData);
		testResp.prettyPrint();
		

	}

}



//RequestSpecification req= RestAssured.given();
//req.baseUri("https://techwithmavii.testrail.io/index.php").log().all()
//.queryParam("/api/v2/get_case_fields")
//.auth().preemptive().basic("janesjohnny75@gmail.com", "PrDOBQZbTuwX2P6Mgw2R-H3rhLDH0AEXjis9PiYxx")
//.get().prettyPrint();

///
///
///
