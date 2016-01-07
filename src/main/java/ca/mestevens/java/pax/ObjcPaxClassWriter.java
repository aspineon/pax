package ca.mestevens.java.pax;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ca.mestevens.java.pax.models.PaxClass;
import ca.mestevens.java.pax.models.PaxMethod;
import ca.mestevens.java.pax.models.PaxParam;
import ca.mestevens.java.pax.models.PaxType;
import ca.mestevens.java.pax.models.PaxTypeModifier;

public class ObjcPaxClassWriter implements PaxClassWriter {
	
	public Map<String, PaxType> objcClassMap;
	
	public ObjcPaxClassWriter() {
		objcClassMap = new HashMap<String, PaxType>();
		objcClassMap.put("string", new PaxType(null, "NSString", PaxTypeModifier.POINTER));
		objcClassMap.put("boolean", new PaxType(null, "BOOL", PaxTypeModifier.NONE));
		objcClassMap.put("map", new PaxType(null, "NSDictionary", PaxTypeModifier.POINTER));
		objcClassMap.put("list", new PaxType(null, "NSArray", PaxTypeModifier.POINTER));
	}
	
	public ObjcPaxClassWriter(Map<String, PaxType> objcClassMap) {
		this();
		this.objcClassMap.putAll(objcClassMap);
	}

	@Override
	public List<String> writePaxClass(PaxClass paxClass) {
		//String classString = "#import <Foundation/Foundation.h>\n\n";
		//String documentation = writeClassDocumentation(paxClass);
		//if (documentation != null) {
		//	classString += documentation;
		//}
		//classString += "@protocol " + paxClass.getObjcMetadata().getClassName() + " <NSObject>\n\n";
		//classString += "@required\n\n";
		//for(PaxMethod method : paxClass.getMethods()) {
		//	String methodDocumentation = method.getObjcDocumentation();
		//	if (methodDocumentation != null && !methodDocumentation.isEmpty()) {
		//		classString += methodDocumentation;
		//	}
		//	classString += method.toObjcString() + "\n\n";
		//}
		//classString += "@end";
		//return classString;
		return null;
	}

	@Override
	public List<String> writePaxMethod(PaxMethod paxMethod) {
		//String methodString = "- (" + writePaxType(paxMethod.getReturnType()) + ")" + paxMethod.getName();
		//int i = 0;
		//if (paxMethod.getParams() != null) {
		//	for(PaxParam param : paxMethod.getParams()) {
		//		if (i != 0) {
		//			methodString += " with" + Character.toUpperCase(param.getName().charAt(0)) + param.getName().substring(1);
		//		}
		//		methodString += ":" + param.toObjcString();
		//		i++;
		//	}
		//}
		//methodString += ";";
		//return methodString;
		return null;
	}

	@Override
	public String writePaxParam(PaxParam paxParam) {
		//String methodParam = writePaxType(paxParam.getType());
		//if (paxParam.getModifier() != null && !paxParam.getModifier().isEmpty()) {
		//	methodParam += " " + paxParam.getModifier();
		//}
		//return "(" + methodParam + ")" + paxParam.getName();
		return null;
	}

	@Override
	public List<String> writeClassDocumentation(PaxClass paxClass) {
		//if (paxClass.getDescription() == null || paxClass.getDescription().equals("")) {
		//	return null;
		//}
		//String documentation = "";
		//documentation += "/**\n";
		//final int wordsPerLine = 15;
		//List<String> descriptionStrings = new ArrayList<String>(Arrays.asList(paxClass.getDescription().split(" ")));
		//int i = 0;
		//for (String descriptionString : descriptionStrings) {
		//	if (i % wordsPerLine == 0) {
		//		documentation += " * ";
		//	}
		//	documentation += descriptionString;
		//	if (i % wordsPerLine == wordsPerLine - 1) {
		//		documentation += "\n";
		//	} else if (i < descriptionStrings.size() - 1){
		//		documentation += " ";
		//	} else {
		//		documentation += "\n";
		//	}
		//	i++;
		//}
		//documentation += " */\n";
		//return documentation;
		return null;
	}

	@Override
	public List<String> writeMethodDocumentation(PaxMethod paxMethod) {
		//String description = paxMethod.getDescription();
		//String returnDescription = paxMethod.getReturnDescription();
		//if ((description == null || description.isEmpty()) && (returnDescription == null || returnDescription.isEmpty())) {
		//	boolean parameterDocumentation = false;
		//	for (PaxParam param : paxMethod.getParams()) {
		//		if (!(param.getDescription() == null) && !param.getDescription().isEmpty()) {
		//			parameterDocumentation = true;
		//			break;
		//		}
		//	}
		//	if (!parameterDocumentation) {
		//		return null;
		//	}
		//}
		//String documentation = "";
		//documentation += "\t/**\n";
		
		//if (description != null && !description.isEmpty()) {
		//	List<String> splitDescription = PlatformTypeConverterUtil.splitString(description);
		//	for (String line : splitDescription) {
		//		documentation += "\t * " + line + "\n";
		//	}
		//}
		//if (paxMethod.getParams() != null) {
		//	for(PaxParam param : paxMethod.getParams()) {
		//		List<String> paramDocumentation = param.getJavaDocumentation();
		//		if (paramDocumentation != null && !paramDocumentation.isEmpty()) {
		//			for (String paramDoc : paramDocumentation) {
		//				documentation += "\t" + paramDoc + "\n";
		//			}
		//		}
		//	}
		//}
		//if (returnDescription != null && !returnDescription.isEmpty()) {
		//	List<String> returnSplit = PlatformTypeConverterUtil.splitString(returnDescription);
		//	boolean firstLine = true;
		//	for (String line : returnSplit) {
		//		if (firstLine) {
		//			documentation += "\t * @return ";
		//			firstLine = false;
		//		} else {
		//			documentation += "\t * ";
		//		}
		//		documentation += line + "\n";
		//	}
		//}
		//documentation += "\t */\n";
		//return documentation;
		return null;
	}

	@Override
	public List<String> writeParamDocumentation(PaxParam paxParam) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String writePaxType(String type) {
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

	@Override
	public List<String> writeImports(PaxClass paxClass) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String writePaxModifier(String modifier) {
		// TODO Auto-generated method stub
		return null;
	}

}
