package ca.mestevens.java.pax.utils;

import java.util.List;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

@Test(groups = "automated")
public class StringUtilsTest {
	
	public StringUtils stringUtils;
	public final int TEST_WORDS_PER_LINE = 15;

	@BeforeMethod
	public void setUp() {
		stringUtils = new StringUtils(TEST_WORDS_PER_LINE);
	}
	
	@AfterMethod
	public void tearDown() {
		stringUtils = null;
	}
	
	@Test
	public void testSplitStringWithMoreWordsPerLine() {
		String testString = "1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17";
		List<String> splitString = stringUtils.splitString(testString);
		assertEquals(splitString.size(), 2, "String should be split into two lines.");
		assertEquals(splitString.get(0), "1 2 3 4 5 6 7 8 9 10 11 12 13 14 15");
		assertEquals(splitString.get(1), "16 17");
	}
	
	@Test
	public void testSplitStringWithLessThan15Words() {
		String testString = "1 2 3 4 5 6 7 8 9 10 11 12 13 14";
		List<String> splitString = stringUtils.splitString(testString);
		assertEquals(splitString.size(), 1, "String shouldn't be split.");
		assertEquals(splitString.get(0), "1 2 3 4 5 6 7 8 9 10 11 12 13 14");
	}
	
	@Test
	public void testSplitStringWith15Words() {
		String testString = "1 2 3 4 5 6 7 8 9 10 11 12 13 14 15";
		List<String> splitString = stringUtils.splitString(testString);
		assertEquals(splitString.size(), 1, "String shouldn't be split.");
		assertEquals(splitString.get(0), "1 2 3 4 5 6 7 8 9 10 11 12 13 14 15");
	}
	
	@Test
	public void testSplitIntoMultipleLines() {
		String testString = "1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 28 29 30 31";
		List<String> splitString = stringUtils.splitString(testString);
		assertEquals(splitString.size(), 3, "String should be split into three lines.");
		assertEquals(splitString.get(0), "1 2 3 4 5 6 7 8 9 10 11 12 13 14 15");
		assertEquals(splitString.get(1), "16 17 18 19 20 21 22 23 24 25 26 27 28 29 30");
		assertEquals(splitString.get(2), "31");
	}
	
	@Test
	public void testSplitWithSpaceAtEnd() {
		String testString = "1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 ";
		List<String> splitString = stringUtils.splitString(testString);
		assertEquals(splitString.size(), 1, "String shouldn't be split.");
		assertEquals(splitString.get(0), "1 2 3 4 5 6 7 8 9 10 11 12 13 14 15");
	}
	
	@Test
	public void testSplitWithSpaceAtStart() {
		String testString = " 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15";
		List<String> splitString = stringUtils.splitString(testString);
		assertEquals(splitString.size(), 1, "String shouldn't be split.");
		assertEquals(splitString.get(0), "1 2 3 4 5 6 7 8 9 10 11 12 13 14 15");
	}
	
	@Test
	public void testSplitWithSpacesAtStartAndEnd() {
		String testString = " 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 ";
		List<String> splitString = stringUtils.splitString(testString);
		assertEquals(splitString.size(), 1, "String shouldn't be split.");
		assertEquals(splitString.get(0), "1 2 3 4 5 6 7 8 9 10 11 12 13 14 15");
	}
	
	@Test
	public void testSplitWithSpaceAtEndMultipleLines() {
		String testString = "1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 ";
		List<String> splitString = stringUtils.splitString(testString);
		assertEquals(splitString.size(), 2, "String should be split into two lines.");
		assertEquals(splitString.get(0), "1 2 3 4 5 6 7 8 9 10 11 12 13 14 15");
		assertEquals(splitString.get(1), "16 17");
	}
	
	@Test
	public void testSplitWithSpaceAtStartMultipleLines() {
		String testString = " 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17";
		List<String> splitString = stringUtils.splitString(testString);
		assertEquals(splitString.size(), 2, "String should be split into two lines.");
		assertEquals(splitString.get(0), "1 2 3 4 5 6 7 8 9 10 11 12 13 14 15");
		assertEquals(splitString.get(1), "16 17");
	}
	
	@Test
	public void testSplitWithNullString() {
		List<String> splitString = stringUtils.splitString(null);
		assertEquals(splitString.size(), 0, "Should have returned an empty array.");
	}
	
	@Test
	public void testSplitWithEmptyString() {
		List<String> splitString = stringUtils.splitString("");
		assertEquals(splitString.size(), 0, "Should have returned an empty array.");
	}
	
}
