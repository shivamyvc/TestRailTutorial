package com.testrail;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.*;

import testrail.client.*;

import org.json.simple.JSONObject;

import io.restassured.response.Response;

public class ExcelReader {
	public static final String ProjectId = "7";

	public static void main(String[] args) throws MalformedURLException, IOException, APIException {

		getExcelData("E:\\Shivam\\Downloads\\UAT Test Scripts - Inventory.xlsx");
	}

	// List<HashMap<String, String>>
	public static void getExcelData(String filePath) {

		try (FileInputStream fis = new FileInputStream(filePath); Workbook workbook = new XSSFWorkbook(fis)) {
			int sheetCounts = workbook.getNumberOfSheets();

			Sheet nsheet;
			System.out.println(sheetCounts);
			Map<String, String> suiteDetail = new HashMap<String, String>();
			Map<String, String> sectionDetail = new HashMap<String, String>();

			// Test Case Map Objects
			Map<String, Object> testDetails = new HashMap<String, Object>();
			Map<String, String> steps = new HashMap<String, String>();
			List<Map<String, String>> testSteps = new ArrayList<Map<String, String>>();

			for (int i = 1; i < sheetCounts; i++) {
				TestRail testRail = new TestRail();
				nsheet = workbook.getSheetAt(i);
				String sheetName = nsheet.getSheetName();
				Row nthROW = nsheet.getRow(2);
				String suiteTitle = nthROW.getCell(1).getStringCellValue();
				if (nthROW == null || suiteTitle.isBlank()) {
					continue;

				} else {

					// Will be executed Once Per Sheet Suite will Be created
					/* ========================================================================= */
					String suiteName = i + "_Inventory_" + suiteTitle;
					System.out.println("== Adding Test Suite:   " + suiteName + "  ==");

					suiteDetail.put("name", suiteName);
					suiteDetail.put("description", "");
					Response suiteResp = testRail.addSuite(ProjectId, suiteDetail);
					String suiteID = suiteResp.jsonPath().getString("id");
					suiteDetail.clear();
					/* ========================================================================= */
					// Will be executed Once Per Sheet Section will Be created

					sectionDetail.put("name", "Section");
					sectionDetail.put("description", "");
					sectionDetail.put("suite_id", suiteID);
					testRail = new TestRail();
					Response sectionResp = testRail.addSection(ProjectId, sectionDetail);

					String sectionID = sectionResp.jsonPath().getString("id");
					sectionDetail.clear();

					/* ======================================================================== */

					// Ready For Adding Test Case to Obtained Section
//					int testRowCount = 14;

//					try {
					for (int testRowCount = 14; testRowCount < nsheet.getLastRowNum(); testRowCount++) {
						Row testRow = nsheet.getRow(testRowCount);
						if (testRow == null || testRow.getCell(1) == null || testRow.getCell(2) == null
								|| testRow.getCell(3) == null || testRow.getCell(4) == null)
							continue;
						else if (testRow.getCell(1).getStringCellValue().isBlank()
								|| testRow.getCell(2).getStringCellValue().isBlank()
								|| testRow.getCell(3).getStringCellValue().isBlank()
								|| testRow.getCell(4).getStringCellValue().isBlank())
							continue;

						String testCaseId = testRow.getCell(1).toString();
						String testCaseAction = testRow.getCell(2).toString();
						String testNavigationStep = testRow.getCell(3).toString();
						String testExpectedResult = testRow.getCell(4).toString();
						String testTitle = sheetName + "-" + testCaseAction;

						steps.put("content", testNavigationStep);
						steps.put("expected", testExpectedResult);
						testSteps.add(steps);
						testDetails.put("title", testTitle);
						testDetails.put("template_id", 2);
						testDetails.put("type_id", 4);
						testDetails.put("custom_steps_separated", testSteps);
						testDetails.put("custom_case_custom_milestone_id", 3);
						System.out.println("=Adding Test Case:   " + testTitle + "  ===");
						testRail = new TestRail();
						Response testCaseResp = testRail.addTestCase(sectionID, testDetails);
//							testCaseResp.prettyPrint();
						testDetails.clear();
						steps.clear();
						testSteps.clear();
						if (!testCaseResp.jsonPath().getString("id").isBlank()) {
							System.out.println("================Test Case Added Scuccesfully=============");
						}

					}

//					} catch (Exception e) {
//						// TODO: handle exception
//						System.out.println("Exceptyion Sheet Number " + i + 1 + " Row Number " + testRowCount + 1);
//					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static Object getCellValue(Cell cell) {
		if (cell == null)
			return "";
		switch (cell.getCellType()) {
		case STRING:
			return cell.getStringCellValue();

		case NUMERIC:
			return DateUtil.isCellDateFormatted(cell) ? cell.getDateCellValue() : cell.getNumericCellValue();

		case BOOLEAN:
			cell.getBooleanCellValue();
		case FORMULA:
			cell.getCellFormula();
		default:
			return "";
		}
	}
}