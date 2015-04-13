package ca.mestevens.java.pax.models;

import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

@Test(groups = "automated")
public class PaxMethodTest {
	
	private PaxMethod paxMethod;
	private final String paxName = "testName";
	private final String paxReturnType = "testReturnType";
	private final String paxReturnDescription = "testReturnDescription";
	private final String paxDescription = "testDescription";
	private final String paxParam1Type = "testParam1Type";
	private final String paxParam1Name = "testParam1Name";
	private final String paxParam1Description = "testParam1Description";
	private final String paxParam1Modifier = "testParam1Modifier";
	private final PaxParam paxParam1 = new PaxParam(paxParam1Type, paxParam1Name, paxParam1Description, paxParam1Modifier);
	private final String paxParam2Type = "testParam2Type";
	private final String paxParam2Name = "testParam2Name";
	private final String paxParam2Description = "testParam2Description";
	private final String paxParam2Modifier = "testParam2Modifier";
	private final PaxParam paxParam2 = new PaxParam(paxParam2Type, paxParam2Name, paxParam2Description, paxParam2Modifier);
	

	@BeforeMethod
	public void setUp() {
		List<PaxParam> params = new ArrayList<PaxParam>();
		params.add(paxParam1);
		params.add(paxParam2);
		paxMethod = new PaxMethod(paxName, paxReturnType, paxReturnDescription, paxDescription, params);
	}
	
	@AfterMethod
	public void tearDown() {
		paxMethod = null;
	}
	
	@Test
	public void testToJavaStringWithAllInformation() {
		String javaString = paxMethod.toJavaString();
		assertEquals(javaString, "public " + paxReturnType + " " + paxName + "(" + paxParam1.toJavaString() +
				", " + paxParam2.toJavaString() + ");");
	}
	
	@Test
	public void testToObjcStringWithAllInformation() {
		String objcString = paxMethod.toObjcString();
		assertEquals(objcString, "- (" + paxReturnType + ")" + paxName + ":" + paxParam1.toObjcString() +
				" with" + Character.toUpperCase(paxParam2Name.charAt(0)) + paxParam2Name.substring(1) + ":" + paxParam2.toObjcString() + ";");
	}
	
	@Test
	public void testGetJavaDocumentation() {
		String docString = paxMethod.getJavaDocumentation();
		assertEquals(docString, "\t/**\n\t * " + paxDescription + "\n\t" + paxParam1.getJavaDocumentation().get(0) + 
				"\n\t" + paxParam2.getJavaDocumentation().get(0) + "\n\t * @return " + paxReturnDescription + "\n\t */\n");
	}
	
	@Test
	public void testGetObjcDocumentation() {
		String docString = paxMethod.getObjcDocumentation();
		assertEquals(docString, "/**\n * " + paxDescription + "\n" + paxParam1.getJavaDocumentation().get(0) + 
				"\n" + paxParam2.getJavaDocumentation().get(0) + "\n * @return " + paxReturnDescription + "\n */\n");
	}
	
}
