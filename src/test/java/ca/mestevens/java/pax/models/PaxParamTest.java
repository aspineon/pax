package ca.mestevens.java.pax.models;

import java.util.List;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

@Test(groups = "automated")
public class PaxParamTest {
	
	private PaxParam paxParam;
	private final String paxType = "testType";
	private final String paxName = "testName";
	private final String paxDescription = "testDescription";
	private final String paxModifier = "testModifier";

	@BeforeMethod
	public void setUp() {
		paxParam = new PaxParam(paxType, paxName, paxDescription, paxModifier);
	}
	
	@AfterMethod
	public void tearDown() {
		paxParam = null;
	}
	
	@Test
	public void testToJavaStringWithAllInformation() {
		String javaString = paxParam.toJavaString();
		assertEquals(javaString, paxModifier + " " + paxType + " " + paxName);
	}
	
	@Test
	public void testToJavaStringWithNullModifier() {
		paxParam.setModifier(null);
		String javaString = paxParam.toJavaString();
		assertEquals(javaString, paxType + " " + paxName);
	}
	
	@Test
	public void testToJavaStringWithEmptyModifier() {
		paxParam.setModifier("");
		String javaString = paxParam.toJavaString();
		assertEquals(javaString, paxType + " " + paxName);
	}
	
	@Test
	public void testGetJavaDocumentationWithNullDescription() {
		paxParam.setDescription(null);
		List<String> javaDocs = paxParam.getJavaDocumentation();
		assertTrue(javaDocs.isEmpty());
	}
	
	@Test
	public void testGetJavaDocumentationWithEmptyDescription() {
		paxParam.setDescription("");
		List<String> javaDocs = paxParam.getJavaDocumentation();
		assertTrue(javaDocs.isEmpty());
	}
	
	public void testGetJavaDocumentationWithBasicDescription() {
		List<String> javaDocs = paxParam.getJavaDocumentation();
		assertEquals(javaDocs.size(), 1);
		for(String line : javaDocs) {
			assertEquals(line, " * @param " + paxName + " " + paxDescription);
		}
	}
	
	@Test
	public void testToObjcStringWithAllInformation() {
		String objcString = paxParam.toObjcString();
		assertEquals(objcString, "(" + paxType + " " + paxModifier + ")" + paxName);
	}
	
	@Test
	public void testToObjcStringWithNullModifier() {
		paxParam.setModifier(null);
		String objcString = paxParam.toObjcString();
		assertEquals(objcString, "(" + paxType + ")" + paxName);
	}
	
	@Test
	public void testToObjcStringWithEmptyModifier() {
		paxParam.setModifier("");
		String objcString = paxParam.toObjcString();
		assertEquals(objcString, "(" + paxType + ")" + paxName);
	}
	
}
