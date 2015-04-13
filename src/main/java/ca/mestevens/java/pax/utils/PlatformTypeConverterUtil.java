package ca.mestevens.java.pax.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ca.mestevens.java.pax.models.PaxType;
import ca.mestevens.java.pax.models.PaxTypeModifier;

public class PlatformTypeConverterUtil {

	public static Map<String, PaxType> javaClassMap;
	static
	{
		javaClassMap = new HashMap<String, PaxType>();
		javaClassMap.put("string", new PaxType(null, "String", PaxTypeModifier.NONE));
	}
	
	public static Map<String, PaxType> objcClassMap;
	static
	{
		objcClassMap = new HashMap<String, PaxType>();
		objcClassMap.put("string", new PaxType(null, "NSString", PaxTypeModifier.POINTER));
		objcClassMap.put("boolean", new PaxType(null, "BOOL", PaxTypeModifier.NONE));
	}

	public static String getJavaType(String type) {
		if (javaClassMap.containsKey(type)) {
			return javaClassMap.get(type).getClassName();
		} else if (type.startsWith("map<") && type.endsWith(">")) {
			String innerString = type.substring(4);
			innerString = innerString.substring(0, innerString.length() - 1);
			List<String> mapSides = splitInnerMapStringIntoTwo(innerString);
			return "Map<" + getJavaType(mapSides.get(0)) + ", " + getJavaType(mapSides.get(1)) + ">";
		} else if (type.startsWith("list<") && type.endsWith(">")) {
			String innerType = type.substring(5);
			innerType = innerType.substring(0, innerType.length() - 1);
			return "List<" + getJavaType(innerType) + ">";
		}
		return type;
	}
	
	public static Set<String> getJavaImportForType(String type, String currentNamespace) {
		Set<String> returnSet = new HashSet<String>();
		if (javaClassMap.containsKey(type)) {
			PaxType paxType = javaClassMap.get(type);
			if (paxType.getNamespace() != null && !currentNamespace.equals(paxType.getNamespace())) {
				returnSet.add("import " + paxType.getNamespace() + "." + paxType.getClassName() + ";");
			}
		} else if (type.startsWith("map<") && type.endsWith(">")) {
			String innerString = type.substring(4);
			innerString = innerString.substring(0, innerString.length() - 1);
			List<String> mapSides = splitInnerMapStringIntoTwo(innerString);
			returnSet.add("import java.util.Map;");
			returnSet.addAll(getJavaImportForType(mapSides.get(0), currentNamespace));
			returnSet.addAll(getJavaImportForType(mapSides.get(1), currentNamespace));
		} else if (type.startsWith("list<") && type.endsWith(">")) {
			String innerType = type.substring(5);
			innerType = innerType.substring(0, innerType.length() - 1);
			returnSet.add("import java.util.List;");
			returnSet.addAll(getJavaImportForType(innerType, currentNamespace));
		}
		return returnSet;
	}
	
	public static String getObjcType(String type) {
		if (objcClassMap.containsKey(type)) {
			PaxType classMetadata = objcClassMap.get(type);
			String returnType = classMetadata.getClassName();
			if (classMetadata.getType() == PaxTypeModifier.POINTER) {
				returnType += "*";
			} else if (classMetadata.getType() == PaxTypeModifier.ID) {
				returnType = "id<" + returnType + ">";
			}
			return returnType;
		} else if (type.startsWith("map<") && type.endsWith(">")) {
			return "NSDictionary*";
		} else if (type.startsWith("list<") && type.endsWith(">")) {
			return "NSArray*";
		}
		return type;
	}
	
	public static String getObjcImportForType(String type) {
		if (objcClassMap.containsKey(type)) {
			return objcClassMap.get(type).getClassName() + ".h";
		}
		return null;
	}
	
	public static List<String> splitInnerMapStringIntoTwo(String innerString) {
		String leftSide = "";
		String rightSide = "";
		int innerCount = 0;
		boolean addRightSide = false;
		for(char s : innerString.toCharArray()) {
			if (addRightSide) {
				rightSide += s;
			} else if (s == '<') {
				innerCount++;
			} else if (s == '>') {
				innerCount--;
			} else if (s == ',' && innerCount == 0) {
				addRightSide = true;
			} else {
				leftSide += s;
			}
		}
		List<String> returnList = new ArrayList<String>();
		returnList.add(leftSide.trim());
		returnList.add(rightSide.trim());
		return returnList;
	}
	
	public static String getJavaModifier(String modifier) {
		if (modifier.equals("const")) {
			return "final";
		}
		return modifier;
	}
	
	public static String getObjcModifier(String modifier) {
		return modifier;
	}
	
	public static List<String> splitString(String string) {
		final int wordsPerLine = 15;
		List<String> splitDescription = new ArrayList<String>(Arrays.asList(string.split(" ")));
		List<String> returnDescriptions = new ArrayList<String>();
		int i = 0;
		String line = "";
		for (String word : splitDescription) {
			if (i % wordsPerLine == 0) {
				line = "";
			}
			line += word;
			if (i % wordsPerLine == wordsPerLine - 1) {
				returnDescriptions.add(line);
			} else if (i < splitDescription.size() - 1){
				line += " ";
			} else {
				returnDescriptions.add(line);
			}
			i++;
		}
		return returnDescriptions;
	}
	
}
