package ca.mestevens.java.pax.utils;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

@Test(groups = "automated")
public class PlatformTypeConverterUtilTest {

	@BeforeMethod
	public void setUp() {
		
	}
	
	@AfterMethod
	public void tearDown() {
		
	}
	
	@Test
	public void testGetJavaNonExistantType() {
		assertEquals(PlatformTypeConverterUtil.getJavaType("asdf"), "asdf");
	}
	
	@Test
	public void testGetObjcNonExistantType() {
		assertEquals(PlatformTypeConverterUtil.getObjcType("asdf"), "asdf");
	}
	
	@Test
	public void testGetJavaStringType() {
		assertEquals(PlatformTypeConverterUtil.getJavaType("string"), "String");
	}
	
	@Test
	public void testGetObjcStringType() {
		assertEquals(PlatformTypeConverterUtil.getObjcType("string"), "NSString*");
	}
	
}
