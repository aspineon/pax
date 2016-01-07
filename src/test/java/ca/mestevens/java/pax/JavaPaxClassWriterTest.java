package ca.mestevens.java.pax;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import ca.mestevens.java.pax.exceptions.PaxValidationException;
import ca.mestevens.java.pax.models.PaxClass;
import ca.mestevens.java.pax.models.PaxLanguageMetadata;
import ca.mestevens.java.pax.models.PaxMethod;
import ca.mestevens.java.pax.models.PaxParam;
import ca.mestevens.java.pax.models.PaxType;
import ca.mestevens.java.pax.models.PaxTypeModifier;
import ca.mestevens.java.pax.utils.StringUtils;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Matchers.anyString;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

@Test(groups = "automated")
public class JavaPaxClassWriterTest {
	
	private JavaPaxClassWriter javaPaxClassWriter;
	private StringUtils mockStringUtils;
	private PaxClass paxClass;
	private PaxMethod paxMethod;
	private PaxParam paxParam;
	private PaxLanguageMetadata javaMetadata;
	private PaxType customPaxType;
	private final String PAX_CLASS_NAME = "className";
	private final String PAX_CLASS_DESCRIPTION = "description";
	private final String PAX_METHOD_NAME = "name";
	private final String PAX_METHOD_RETURN_TYPE = "returnType";
	private final String PAX_METHOD_RETURN_DESCRIPTION = "returnDescription";
	private final String PAX_METHOD_DESCRIPTION = "description";
	private final String PAX_PARAM_TYPE = "type";
	private final String PAX_PARAM_NAME = "name";
	private final String PAX_PARAM_DESCRIPTION = "description";
	private final String PAX_PARAM_MODIFIER = "modifier";
	private final String PAX_LANGUAGE_METADATA_NAMESPACE = "namespace";
	private final String PAX_LANGUAGE_METADATA_CLASS_NAME = "className";
	private final String PAX_LANGUAGE_METADATA_OUTPUT_FOLDER = "outputFolder";
	private final String PAX_CUSTOM_TYPE_NAMESPACE = "com.test.example";
	private final String PAX_CUSTOM_TYPE_JAVA_NAME = "CustomJavaClass";
	private final PaxTypeModifier PAX_CUSTOM_MODIFIER = PaxTypeModifier.NONE;

	@BeforeMethod
	public void setUp() {
		mockStringUtils = mock(StringUtils.class);
		customPaxType = new PaxType(PAX_CUSTOM_TYPE_NAMESPACE, PAX_CUSTOM_TYPE_JAVA_NAME, PAX_CUSTOM_MODIFIER);
		Map<String, PaxType> customTypeMap = new HashMap<String, PaxType>();
		customTypeMap.put("custom", customPaxType);
		javaPaxClassWriter = new JavaPaxClassWriter(mockStringUtils, customTypeMap);

		paxParam = new PaxParam(PAX_PARAM_TYPE, PAX_PARAM_NAME, PAX_PARAM_DESCRIPTION, PAX_PARAM_MODIFIER);
		paxMethod = new PaxMethod(PAX_METHOD_NAME, PAX_METHOD_RETURN_TYPE, PAX_METHOD_RETURN_DESCRIPTION, PAX_METHOD_DESCRIPTION, Arrays.asList(paxParam));
		javaMetadata = new PaxLanguageMetadata(PAX_LANGUAGE_METADATA_NAMESPACE, PAX_LANGUAGE_METADATA_CLASS_NAME, PAX_LANGUAGE_METADATA_OUTPUT_FOLDER);
		paxClass = new PaxClass(PAX_CLASS_NAME, javaMetadata, null, PAX_CLASS_DESCRIPTION, Arrays.asList(paxMethod));
	}
	
	@AfterMethod
	public void tearDown() {
		paxClass = null;
		customPaxType = null;
		javaMetadata = null;
		paxMethod = null;
		paxParam = null;
		
		javaPaxClassWriter = null;
		mockStringUtils = null;
	}
	
	/*
	public List<String> writePaxClass(PaxClass paxClass);
	 */
	@Test
	public void testWriteStringPaxType() throws PaxValidationException {
		String paxType = javaPaxClassWriter.writePaxType("string");
		assertEquals(paxType, "String", "\"string\" should map to \"String\".");
	}
	
	@Test
	public void testWriteMapPaxType() throws PaxValidationException {
		String paxType = javaPaxClassWriter.writePaxType("map<string, int>");
		assertEquals(paxType, "Map<String, int>", "\"map<string, int>\" should map to \"Map<String, int>\".");
	}
	
	@Test
	public void testWriteMapWithNestedMap() throws PaxValidationException {
		String paxType = javaPaxClassWriter.writePaxType("map<string, map<string, int>>");
		assertEquals(paxType, "Map<String, Map<String, int>>", "\"map<string, map<string, int>>\" should map to \"Map<String, Map<String, int>>\".");
	}
	
	@Test
	public void testWriteMapWithDoubleNestedMap() throws PaxValidationException {
		String paxType = javaPaxClassWriter.writePaxType("map<map<string, int>, map<string, int>>");
		assertEquals(paxType, "Map<Map<String, int>, Map<String, int>>", "\"map<map<string, int>, map<string, int>>\" should map to \"Map<Map<String, int>, Map<String, int>>\".");
	}
	
	public void testWriteMapWithDeeplyNestedMap() throws PaxValidationException {
		String deepMap = "map<string, ";
		for (int i = 0; i < 20; i++) {
			deepMap += "map<string, ";
		}
		deepMap += "string>";
		for (int i = 0; i < 20; i++) {
			deepMap += ">";
		}
		String paxType = javaPaxClassWriter.writePaxType(deepMap);
		deepMap = deepMap.replace('m', 'M');
		deepMap = deepMap.replace('s', 'S');
		assertEquals(paxType, deepMap, "The deeply linked map was not converted properly.");
	}
	
	@Test
	public void testWriteListPaxType() throws PaxValidationException {
		String paxType = javaPaxClassWriter.writePaxType("list<string>");
		assertEquals(paxType, "List<String>", "\"list<string>\" should map to \"List<String>\".");
	}
	
	@Test
	public void testWriteListWithDeeplyNestedLists() throws PaxValidationException {
		String deepList = "list<";
		for (int i = 0; i < 19; i++) {
			deepList += "list<";
		}
		deepList += "string";
		for (int i = 0; i < 20; i++) {
			deepList += ">";
		}
		String paxType = javaPaxClassWriter.writePaxType(deepList);
		deepList = deepList.replace('l', 'L');
		deepList = deepList.replace("string", "String");
		assertEquals(paxType, deepList, "The deeply linked list was not converted properly.");
	}
	
	@Test
	public void testWritePaxTypeNotInMap() throws PaxValidationException {
		String paxType = javaPaxClassWriter.writePaxType("long");
		assertEquals(paxType, "long", "\"long\" should map to \"long\".");
	}
	
	@Test
	public void testWriteCustomPaxType() throws PaxValidationException {
		String paxType = javaPaxClassWriter.writePaxType("custom");
		assertEquals(paxType, "CustomJavaClass", "\"custom\" should map to \"CustomJavaClass\".");
	}
	
	@Test(expectedExceptions = PaxValidationException.class)
	public void testWriteNullPaxType() throws PaxValidationException {
		javaPaxClassWriter.writePaxType(null);
	}
	
	@Test(expectedExceptions = PaxValidationException.class)
	public void testWriteEmptyPaxType() throws PaxValidationException {
		javaPaxClassWriter.writePaxType("");
	}
	
	@Test(expectedExceptions = PaxValidationException.class)
	public void testWritePaxParamWithNullType() throws PaxValidationException {
		paxParam = paxParam.withType(null);
		javaPaxClassWriter.writePaxParam(paxParam);
	}
	
	@Test(expectedExceptions = PaxValidationException.class)
	public void testWritePaxParamWithEmptyType() throws PaxValidationException {
		paxParam = paxParam.withType("");
		javaPaxClassWriter.writePaxParam(paxParam);
	}
	
	@Test
	public void testWritePaxParamWithNullModifier() throws PaxValidationException {
		paxParam = paxParam.withModifier(null);
		String paxParamString = javaPaxClassWriter.writePaxParam(paxParam);
		assertEquals(paxParamString, PAX_PARAM_TYPE + " " + PAX_PARAM_NAME, "PaxParam with null modifier should still write the parameter.");
	}
	
	@Test
	public void testWritePaxParamWithEmptyModifier() throws PaxValidationException {
		paxParam = paxParam.withModifier("");
		String paxParamString = javaPaxClassWriter.writePaxParam(paxParam);
		assertEquals(paxParamString, PAX_PARAM_TYPE + " " + PAX_PARAM_NAME, "PaxParam with empty modifier should still write the parameter.");
	}
	
	@Test(expectedExceptions = PaxValidationException.class)
	public void testWritePaxParamWithNullModifierAndType() throws PaxValidationException {
		paxParam = paxParam.withType(null);
		paxParam = paxParam.withModifier(null);
		javaPaxClassWriter.writePaxParam(paxParam);
	}
	
	@Test(expectedExceptions = PaxValidationException.class)
	public void testWritePaxParamWithNullName() throws PaxValidationException {
		paxParam = paxParam.withName(null);
		javaPaxClassWriter.writePaxParam(paxParam);
	}
	
	@Test(expectedExceptions = PaxValidationException.class)
	public void testWritePaxParamWithEmptyName() throws PaxValidationException {
		paxParam = paxParam.withName("");
		javaPaxClassWriter.writePaxParam(paxParam);
	}
	
	@Test
	public void testWriteValidPaxParam() throws PaxValidationException {
		String paxParamString = javaPaxClassWriter.writePaxParam(paxParam);
		assertEquals(paxParamString, PAX_PARAM_MODIFIER + " " + PAX_PARAM_TYPE + " " + PAX_PARAM_NAME, "Valid PaxParam does not match the expected format.");
	}
	
	@Test
	public void testWriteImportsWithNullMethods() {
		paxClass = paxClass.withMethods(null);
		List<String> importList = javaPaxClassWriter.writeImports(paxClass);
		assertEquals(importList.size(), 0, "Import list should be empty if there are no methods.");
	}
	
	@Test
	public void testWriteImportsWithEmptyMethods() {
		paxClass = paxClass.withMethods(new ArrayList<PaxMethod>());
		List<String> importList = javaPaxClassWriter.writeImports(paxClass);
		assertEquals(importList.size(), 0, "Import list should be empty if there are no methods.");
	}
	
	@Test
	public void testWriteImportsWithNullParams() {
		paxMethod = paxMethod.withParams(null);
		paxClass = paxClass.withMethods(Arrays.asList(paxMethod));
		List<String> importList = javaPaxClassWriter.writeImports(paxClass);
		assertEquals(importList.size(), 0, "Import list should be empty if there are no parameters.");
	}
	
	@Test
	public void testWriteImportsWithEmptyParams() {
		paxMethod = paxMethod.withParams(new ArrayList<PaxParam>());
		paxClass = paxClass.withMethods(Arrays.asList(paxMethod));
		List<String> importList = javaPaxClassWriter.writeImports(paxClass);
		assertEquals(importList.size(), 0, "Import list should be empty if there are no parameters.");
	}
	
	@Test
	public void testWriteImportsWithNullParamsAndCustomReturnType() {
		paxMethod = paxMethod.withParams(null);
		paxMethod = paxMethod.withReturnType("custom");
		paxClass = paxClass.withMethods(Arrays.asList(paxMethod));
		List<String> importList = javaPaxClassWriter.writeImports(paxClass);
		assertEquals(importList.size(), 1, "Import list should have 1 item.");
		assertTrue(importList.contains("import " + customPaxType.getNamespace() + "." + customPaxType.getClassName() + ";"), "Import list should contain the CustomJavaClass import.");
	}
	
	@Test
	public void testWriteImportsWithCustomClassInList() {
		paxParam = paxParam.withType("list<custom>");
		paxMethod = paxMethod.withParams(Arrays.asList(paxParam));
		paxClass = paxClass.withMethods(Arrays.asList(paxMethod));
		List<String> importList = javaPaxClassWriter.writeImports(paxClass);
		assertEquals(importList.size(), 2, "Import list should have 2 items.");
		assertTrue(importList.contains("import java.util.List;"), "Import list should contain the list import.");
		assertTrue(importList.contains("import " + customPaxType.getNamespace() + "." + customPaxType.getClassName() + ";"), "Import list should contain the CustomJavaClass import.");
	}
	
	@Test(expectedExceptions = PaxValidationException.class)
	public void testWritePaxMethodWithNullReturnType() throws PaxValidationException {
		paxMethod = paxMethod.withReturnType(null);
		javaPaxClassWriter.writePaxMethod(paxMethod);
	}
	
	@Test(expectedExceptions = PaxValidationException.class)
	public void testWritePaxMethodWithEmptyReturnType() throws PaxValidationException {
		paxMethod = paxMethod.withReturnType("");
		javaPaxClassWriter.writePaxMethod(paxMethod);
	}
	
	@Test(expectedExceptions = PaxValidationException.class)
	public void testWritePaxMethodWithNullName() throws PaxValidationException {
		paxMethod = paxMethod.withName(null);
		javaPaxClassWriter.writePaxMethod(paxMethod);
	}
	
	@Test(expectedExceptions = PaxValidationException.class)
	public void testWritePaxMethodWithEmptyName() throws PaxValidationException {
		paxMethod = paxMethod.withName("");
		javaPaxClassWriter.writePaxMethod(paxMethod);
	}
	
	@Test
	public void testWritePaxMethodWithNullParams() throws PaxValidationException {
		paxMethod = paxMethod.withParams(null);
		List<String> methodStrings = javaPaxClassWriter.writePaxMethod(paxMethod);
		assertEquals(methodStrings.size(), 1, "Null params should return the method signature still.");
		assertEquals(methodStrings.get(0), "public " + paxMethod.getReturnType() + " " + paxMethod.getName() + "();", "Actual and expected method signatures differ.");
	}
	
	@Test
	public void testWritePaxMethodWithEmptyParams() throws PaxValidationException {
		paxMethod = paxMethod.withParams(new ArrayList<PaxParam>());
		List<String> methodStrings = javaPaxClassWriter.writePaxMethod(paxMethod);
		assertEquals(methodStrings.size(), 1, "Null params should return the method signature still.");
		assertEquals(methodStrings.get(0), "public " + paxMethod.getReturnType() + " " + paxMethod.getName() + "();", "Actual and expected method signatures differ.");
	}
	
	@Test(expectedExceptions = PaxValidationException.class)
	public void testWritePaxMethodWithNullNameAndParams() throws PaxValidationException {
		paxMethod = paxMethod.withName(null);
		paxMethod = paxMethod.withParams(null);
		javaPaxClassWriter.writePaxMethod(paxMethod);
	}
	
	@Test
	public void testWritePaxMethodWithParams() throws PaxValidationException {
		List<String> methodStrings = javaPaxClassWriter.writePaxMethod(paxMethod);
		assertEquals(methodStrings.size(), 1, "Only the method signature should be returned.");
		assertEquals(methodStrings.get(0), "public " + paxMethod.getReturnType() + " " + paxMethod.getName() +
				"(" + paxParam.getModifier() + " " + paxParam.getType() + " " + paxParam.getName() + ");",
				"Actual and expected method signatures differ.");
	}
	
	@Test
	public void testWritePaxMethodWithCustomParams() throws PaxValidationException {
		paxParam = paxParam.withType("custom");
		paxMethod = paxMethod.withParams(Arrays.asList(paxParam));
		List<String> methodStrings = javaPaxClassWriter.writePaxMethod(paxMethod);
		assertEquals(methodStrings.size(), 1, "Only the method signature should be returned.");
		assertEquals(methodStrings.get(0), "public " + paxMethod.getReturnType() + " " + paxMethod.getName() +
				"(" + paxParam.getModifier() + " " + PAX_CUSTOM_TYPE_JAVA_NAME + " " + paxParam.getName() + ");",
				"Actual and expected method signatures differ.");
	}
	
	public void testWriteParamDocumentationWithNullDescription() {
		paxParam = paxParam.withDescription(null);
		List<String> paxParamDocumentation = javaPaxClassWriter.writeParamDocumentation(paxParam);
		assertEquals(paxParamDocumentation.size(), 0, "Documentation should be empty if null description");
		verify(mockStringUtils, never()).splitString(anyString());
	}
	
	public void testWriteParamDocumentationWithEmptyDescription() {
		paxParam = paxParam.withDescription("");
		List<String> paxParamDocumentation = javaPaxClassWriter.writeParamDocumentation(paxParam);
		assertEquals(paxParamDocumentation.size(), 0, "Documentation should be empty if empty description");
		verify(mockStringUtils, never()).splitString(anyString());
	}
	
	public void testWriteParamDocumentation() {
		when(mockStringUtils.splitString("@param " + paxParam.getName() + " " + paxParam.getDescription()))
			.thenReturn(Arrays.asList("@param " + paxParam.getName() + " " + paxParam.getDescription()));
		List<String> paramDocumentation = javaPaxClassWriter.writeParamDocumentation(paxParam);
		assertEquals(paramDocumentation.size(), 1, "One line of documentation should have been returned.");
		assertEquals(paramDocumentation.get(0), "@param " + paxParam.getName() + " " + paxParam.getDescription());
		verify(mockStringUtils, times(1)).splitString("@param " + paxParam.getName() + " " + paxParam.getDescription());
	}
	
	public void testWriteParamDocumentationMultipleLines() {
		when(mockStringUtils.splitString("@param " + paxParam.getName() + " " + paxParam.getDescription()))
			.thenReturn(Arrays.asList("@param " + paxParam.getName() + " " + paxParam.getDescription(), "line 2"));
		List<String> paramDocumentation = javaPaxClassWriter.writeParamDocumentation(paxParam);
		assertEquals(paramDocumentation.size(), 2, "Two lines of documentation should have been returned.");
		assertEquals(paramDocumentation.get(0), "@param " + paxParam.getName() + " " + paxParam.getDescription());
		assertEquals(paramDocumentation.get(1), "line 2");
		verify(mockStringUtils, times(1)).splitString("@param " + paxParam.getName() + " " + paxParam.getDescription());
	}
	
	public void testWriteParamDocumentationSplitStringReturnsNull() {
		when(mockStringUtils.splitString("@param " + paxParam.getName() + " " + paxParam.getDescription()))
			.thenReturn(null);
		List<String> paramDocumentation = javaPaxClassWriter.writeParamDocumentation(paxParam);
		assertEquals(paramDocumentation.size(), 0, "An empty array should have been returned.");
		verify(mockStringUtils, times(1)).splitString("@param " + paxParam.getName() + " " + paxParam.getDescription());
	}
	
	public void testWriteParamDocumentationSplitStringReturnsEmpty() {
		when(mockStringUtils.splitString("@param " + paxParam.getName() + " " + paxParam.getDescription()))
			.thenReturn(new ArrayList<String>());
		List<String> paramDocumentation = javaPaxClassWriter.writeParamDocumentation(paxParam);
		assertEquals(paramDocumentation.size(), 0, "An empty array should have been returned.");
		verify(mockStringUtils, times(1)).splitString("@param " + paxParam.getName() + " " + paxParam.getDescription());
	}
	
	public void testWriteMethodDocumentationNullDescriptionReturnDescriptionAndParams() {
		paxMethod = paxMethod.withDescription(null);
		paxMethod = paxMethod.withReturnDescription(null);
		paxMethod = paxMethod.withParams(null);
		List<String> methodDocumentation = javaPaxClassWriter.writeMethodDocumentation(paxMethod);
		assertEquals(methodDocumentation.size(), 0, "No documentation should have been returned.");
	}
	
	public void testWriteMethodDocumentationEmptyDescriptionReturnDescriptionAndParams() {
		paxMethod = paxMethod.withDescription("");
		paxMethod = paxMethod.withReturnDescription("");
		paxMethod = paxMethod.withParams(new ArrayList<PaxParam>());
		List<String> methodDocumentation = javaPaxClassWriter.writeMethodDocumentation(paxMethod);
		assertEquals(methodDocumentation.size(), 0, "No documentation should have been returned.");
	}
	
	public void testWriteMethodDocumentationWithDescription() {
		paxMethod = paxMethod.withReturnDescription("");
		paxMethod = paxMethod.withParams(new ArrayList<PaxParam>());
		when(mockStringUtils.splitString(PAX_METHOD_DESCRIPTION))
			.thenReturn(Arrays.asList(PAX_METHOD_DESCRIPTION));
		List<String> methodDocumentation = javaPaxClassWriter.writeMethodDocumentation(paxMethod);
		assertEquals(methodDocumentation.size(), 1, "One line of documentation should have been returned.");
		assertEquals(methodDocumentation.get(0), PAX_METHOD_DESCRIPTION);
		verify(mockStringUtils, times(1)).splitString(PAX_METHOD_DESCRIPTION);
	}
	
	public void testWriteMethodDocumentationWithReturnDescription() {
		paxMethod = paxMethod.withDescription("");
		paxMethod = paxMethod.withParams(new ArrayList<PaxParam>());
		when(mockStringUtils.splitString("@return " + PAX_METHOD_RETURN_DESCRIPTION))
			.thenReturn(Arrays.asList("@return " + PAX_METHOD_RETURN_DESCRIPTION));
		List<String> methodDocumentation = javaPaxClassWriter.writeMethodDocumentation(paxMethod);
		assertEquals(methodDocumentation.size(), 1, "One line of documentation should have been returned.");
		assertEquals(methodDocumentation.get(0), "@return " + PAX_METHOD_RETURN_DESCRIPTION);
		verify(mockStringUtils, times(1)).splitString("@return " + PAX_METHOD_RETURN_DESCRIPTION);
	}
	
	public void testWriteMethodDocumentationWithParams() {
		paxMethod = paxMethod.withDescription("");
		paxMethod = paxMethod.withReturnDescription("");
		when(mockStringUtils.splitString("@param " + PAX_PARAM_NAME + " " + PAX_PARAM_DESCRIPTION))
			.thenReturn(Arrays.asList("@param " + PAX_PARAM_NAME + " " + PAX_PARAM_DESCRIPTION));
		List<String> methodDocumentation = javaPaxClassWriter.writeMethodDocumentation(paxMethod);
		assertEquals(methodDocumentation.size(), 1, "One line of documentation should have been returned.");
		assertEquals(methodDocumentation.get(0), "@param " + PAX_PARAM_NAME + " " + PAX_PARAM_DESCRIPTION);
		verify(mockStringUtils, times(1)).splitString("@param " + PAX_PARAM_NAME + " " + PAX_PARAM_DESCRIPTION);
	}
	
	public void testWriteMethodDocumentation() {
		when(mockStringUtils.splitString(PAX_METHOD_DESCRIPTION))
			.thenReturn(Arrays.asList(PAX_METHOD_DESCRIPTION));
		when(mockStringUtils.splitString("@param " + PAX_PARAM_NAME + " " + PAX_PARAM_DESCRIPTION))
			.thenReturn(Arrays.asList("@param " + PAX_PARAM_NAME + " " + PAX_PARAM_DESCRIPTION));
		when(mockStringUtils.splitString("@return " + PAX_METHOD_RETURN_DESCRIPTION))
			.thenReturn(Arrays.asList("@return " + PAX_METHOD_RETURN_DESCRIPTION));
		List<String> methodDocumentation = javaPaxClassWriter.writeMethodDocumentation(paxMethod);
		assertEquals(methodDocumentation.size(), 3, "Three lines of documentation should have been returned.");
		assertEquals(methodDocumentation.get(0), PAX_METHOD_DESCRIPTION);
		assertEquals(methodDocumentation.get(1), "@param " + PAX_PARAM_NAME + " " + PAX_PARAM_DESCRIPTION);
		assertEquals(methodDocumentation.get(2), "@return " + PAX_METHOD_RETURN_DESCRIPTION);
		verify(mockStringUtils, times(1)).splitString(PAX_METHOD_DESCRIPTION);
		verify(mockStringUtils, times(1)).splitString("@param " + PAX_PARAM_NAME + " " + PAX_PARAM_DESCRIPTION);
		verify(mockStringUtils, times(1)).splitString("@return " + PAX_METHOD_RETURN_DESCRIPTION);
	}
	
	@Test
	public void testWriteClassDocumentationWithNullDescription() {
		paxClass = paxClass.withDescription(null);
		List<String> classDocumentation = javaPaxClassWriter.writeClassDocumentation(paxClass);
		assertEquals(classDocumentation.size(), 0, "No documentation should have been returned.");
	}
	
	@Test
	public void testWriteClassDocumentationWithEmptyDescription() {
		paxClass = paxClass.withDescription("");
		List<String> classDocumentation = javaPaxClassWriter.writeClassDocumentation(paxClass);
		assertEquals(classDocumentation.size(), 0, "No documentation should have been returned.");
	}
	
	@Test
	public void testWriteClassDocumentationWithNullSplit() {
		when(mockStringUtils.splitString(PAX_CLASS_DESCRIPTION))
			.thenReturn(null);
		List<String> classDocumentation = javaPaxClassWriter.writeClassDocumentation(paxClass);
		assertEquals(classDocumentation.size(), 0, "No documentation should have been returned.");
		verify(mockStringUtils, times(1)).splitString(PAX_CLASS_DESCRIPTION);
	}
	
	@Test
	public void testWriteClassDocumentationWithSplit() {
		when(mockStringUtils.splitString(PAX_CLASS_DESCRIPTION))
			.thenReturn(Arrays.asList(PAX_CLASS_DESCRIPTION));
		List<String> classDocumentation = javaPaxClassWriter.writeClassDocumentation(paxClass);
		assertEquals(classDocumentation.size(), 1, "One line of documentation should have been returned.");
		assertEquals(classDocumentation.get(0), PAX_CLASS_DESCRIPTION);
		verify(mockStringUtils, times(1)).splitString(PAX_CLASS_DESCRIPTION);
	}
	
	@Test
	public void testWriteClassDocumentationWithMultipleSplit() {
		when(mockStringUtils.splitString(PAX_CLASS_DESCRIPTION))
			.thenReturn(Arrays.asList(PAX_CLASS_DESCRIPTION, "line2"));
		List<String> classDocumentation = javaPaxClassWriter.writeClassDocumentation(paxClass);
		assertEquals(classDocumentation.size(), 2, "Two lines of documentation should have been returned.");
		assertEquals(classDocumentation.get(0), PAX_CLASS_DESCRIPTION);
		assertEquals(classDocumentation.get(1), "line2");
		verify(mockStringUtils, times(1)).splitString(PAX_CLASS_DESCRIPTION);
	}
	
	/*
	 * public List<String> writePaxClass(final PaxClass paxClass) throws PaxValidationException {
		paxClassValidator.validate(paxClass);
		List<String> classStrings = new ArrayList<String>();
		classStrings.add("package " + paxClass.getJavaMetadata().getNamespace() + ";");
		classStrings.add("");
		List<String> imports = writeImports(paxClass);
		if (imports != null && imports.size() > 0) {
			classStrings.addAll(imports);
			classStrings.add("");
		}
		List<String> classDocumentation = writeClassDocumentation(paxClass);
		if (classDocumentation != null && classDocumentation.size() > 0) {
			classStrings.add("/**");
			for (String documentation : classDocumentation) {
				classStrings.add(" * " + documentation);
			}
			classStrings.add(" *//*");
		}
		classStrings.add("public interface " + paxClass.getJavaMetadata().getClassName() + " {");
		classStrings.add("");
		for (PaxMethod method : paxClass.getMethods()) {
			List<String> methodDocumentationList = writeMethodDocumentation(method);
			if (methodDocumentationList != null && methodDocumentationList.size() > 0) {
				classStrings.add("\t/**");
				for (String methodDocumentation : methodDocumentationList) {
					classStrings.add("\t * " + methodDocumentation);
				}
				classStrings.add("\t *//*");
			}
			List<String> paxMethodStringList = writePaxMethod(method);
			if (paxMethodStringList.size() > 0) {
				for (String methodString : paxMethodStringList) {
					classStrings.add("\t" + methodString);
				}
				classStrings.add("");
			}
		}
		classStrings.add("}");
		classStrings.add("");
		return classStrings;
	}
	 */
	
	@Test(expectedExceptions = PaxValidationException.class)
	public void testWritePaxClassWithNullMetadata() {
		paxClass = paxClass.withJavaMetadata(null);
		javaPaxClassWriter.writePaxClass(paxClass);
	}
	
	@Test(expectedExceptions = PaxValidationException.class)
	public void testWritePaxClassWithNullMetadataNamespace() {
		javaMetadata = javaMetadata.withNamespace(null);
		paxClass = paxClass.withJavaMetadata(javaMetadata);
		javaPaxClassWriter.writePaxClass(paxClass);
	}
	
	@Test
	public void test() {
		when(mockStringUtils.splitString(PAX_METHOD_DESCRIPTION))
		.thenReturn(Arrays.asList(PAX_METHOD_DESCRIPTION));
	when(mockStringUtils.splitString("@param " + PAX_PARAM_NAME + " " + PAX_PARAM_DESCRIPTION))
		.thenReturn(Arrays.asList("@param " + PAX_PARAM_NAME + " " + PAX_PARAM_DESCRIPTION));
	when(mockStringUtils.splitString("@return " + PAX_METHOD_RETURN_DESCRIPTION))
		.thenReturn(Arrays.asList("@return " + PAX_METHOD_RETURN_DESCRIPTION));
		List<String> strL = javaPaxClassWriter.writePaxClass(paxClass);
		for(String str : strL) {
			System.out.println(str);
		}
	}
	
}
