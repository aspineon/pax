package ca.mestevens.java.pax.models;

import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

@Test(groups = "automated")
public class PaxClassTest {
	
	private final String className = "testClassName";
	private final PaxLanguageMetadata javaMetadata = new PaxLanguageMetadata("com.test", "TestClassName", null);
	private final PaxLanguageMetadata objcMetadata = new PaxLanguageMetadata(null, "TSTClassName", null);
	private final String description = "testDescription";
	private final String methodName = "testMethodName";
	private final String methodReturnType = "testMethodReturnType";
	private final String methodReturnDescription = "testMethodReturnDescription";
	private final String methodDescription = "testMethodDescription";
	private final PaxMethod method = new PaxMethod(methodName, methodReturnType, methodReturnDescription, methodDescription, null);
	private PaxClass paxClass;
	
	@BeforeMethod
	public void setUp() {
		List<PaxMethod> methods = new ArrayList<PaxMethod>();
		methods.add(method);
		paxClass = new PaxClass(className, javaMetadata, objcMetadata, description, methods);
	}
	
	@AfterMethod
	public void tearDown() {
		paxClass = null;
	}
	
	@Test
	public void testClassToJavaString() {
		String javaString = paxClass.toJavaString();
		String expectedString = "package com.test;\n\n";
		expectedString += "/**\n";
		expectedString += " * " + description + "\n";
		expectedString += " */\n";
		expectedString += "public interface " + javaMetadata.getClassName() + " {\n\n";
		expectedString += "\t/**\n";
		expectedString += "\t * " + methodDescription + "\n";
		expectedString += "\t * " + "@return " + methodReturnDescription + "\n";
		expectedString += "\t */\n";
		expectedString += "\tpublic " + methodReturnType + " " + methodName + "();\n\n";
		expectedString += "}";
		assertEquals(javaString, expectedString);
	}
	
	@Test
	public void testClassToObjcString() {
		String objcString = paxClass.toObjcString();
		String expectedString = "#import <Foundation/Foundation.h>\n\n";
		expectedString += "/**\n";
		expectedString += " * " + description + "\n";
		expectedString += " */\n";
		expectedString += "@protocol " + objcMetadata.getClassName() + " <NSObject>\n\n";
		expectedString += "@required\n\n";
		expectedString += "/**\n";
		expectedString += " * " + methodDescription + "\n";
		expectedString += " * " + "@return " + methodReturnDescription + "\n";
		expectedString += " */\n";
		expectedString += "- (" + methodReturnType + ")" + methodName + ";\n\n";
		expectedString += "@end";
		assertEquals(objcString, expectedString);
	}

}
