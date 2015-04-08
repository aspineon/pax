package ca.mestevens.java.pax.utils;

import java.util.HashMap;
import java.util.Map;

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
		}
		return type;
	}
	
	public static String getJavaImportForType(String type, String currentNamespace) {
		if (javaClassMap.containsKey(type)) {
			PaxType paxType = javaClassMap.get(type);
			if (paxType.getNamespace() != null && !currentNamespace.equals(paxType.getNamespace())) {
				return paxType.getNamespace() + "." + paxType.getClassName();
			}
		}
		return null;
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
		}
		return type;
	}
	
	public static String getObjcImportForType(String type) {
		if (objcClassMap.containsKey(type)) {
			return objcClassMap.get(type).getClassName() + ".h";
		}
		return null;
	}
	
}
