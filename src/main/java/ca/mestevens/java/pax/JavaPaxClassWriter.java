package ca.mestevens.java.pax;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ca.mestevens.java.pax.exceptions.PaxValidationException;
import ca.mestevens.java.pax.models.PaxClass;
import ca.mestevens.java.pax.models.PaxMethod;
import ca.mestevens.java.pax.models.PaxParam;
import ca.mestevens.java.pax.models.PaxType;
import ca.mestevens.java.pax.models.PaxTypeModifier;
import ca.mestevens.java.pax.utils.StringUtils;
import ca.mestevens.java.pax.validation.JavaValidator;
import ca.mestevens.java.pax.validation.Validator;

public class JavaPaxClassWriter implements PaxClassWriter {
	
	private Map<String, PaxType> javaClassMap;
	private final StringUtils stringUtils;
	private final Validator<PaxClass> paxClassValidator;
	private final Validator<PaxMethod> paxMethodValidator;
	private final Validator<PaxParam> paxParamValidator;

	public JavaPaxClassWriter(final StringUtils stringUtils, final Map<String, PaxType> javaClassMap) {
		if (javaClassMap != null) {
			if (!javaClassMap.containsKey("string")) {
				javaClassMap.put("string", new PaxType(null, "String", PaxTypeModifier.NONE));
			}
			if (!javaClassMap.containsKey("map")) {
				javaClassMap.put("map", new PaxType("java.util", "Map", PaxTypeModifier.NONE));
			}
			if (!javaClassMap.containsKey("list")) {
				javaClassMap.put("list", new PaxType("java.util", "List", PaxTypeModifier.NONE));
			}
		}
		this.stringUtils = stringUtils;
		this.javaClassMap = javaClassMap;
		this.paxClassValidator = new JavaValidator<PaxClass>();
		this.paxMethodValidator = new JavaValidator<PaxMethod>();
		this.paxParamValidator = new JavaValidator<PaxParam>();
	}

	@Override
	public List<String> writePaxClass(final PaxClass paxClass) throws PaxValidationException {
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
			classStrings.add(" */");
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
				classStrings.add("\t */");
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
		return classStrings;
	}

	@Override
	public List<String> writePaxMethod(final PaxMethod paxMethod) throws PaxValidationException {
		paxMethodValidator.validate(paxMethod);
		List<String> methodStrings = new ArrayList<String>();
		String returnType = writePaxType(paxMethod.getReturnType());
		String methodString = "public " + returnType + " " + paxMethod.getName() + "(";
		int i = 0;
		if (paxMethod.getParams() != null) {
			for(PaxParam param : paxMethod.getParams()) {
				String paxParam = writePaxParam(param);
				if (paxParam.isEmpty()) {
					i++;
					continue;
				}
				if (i != 0) {
					methodString += ", ";
				}
				methodString += paxParam;
				i++;
			}
		}
		methodString += ");";
		methodStrings.add(methodString);
		return methodStrings;
	}

	@Override
	public String writePaxParam(final PaxParam paxParam) throws PaxValidationException {
		paxParamValidator.validate(paxParam);
		String javaString = "";
		String methodParam = writePaxType(paxParam.getType());
		String modifier = writePaxModifier(paxParam.getModifier());
		if (modifier != null && !modifier.isEmpty()) {
			javaString += modifier + " ";
		}
		javaString += methodParam + " " + paxParam.getName();
		return javaString;
	}

	@Override
	public List<String> writeClassDocumentation(final PaxClass paxClass) {
		paxClassValidator.validate(paxClass);
		List<String> classDocumentation = new ArrayList<String>();
		String description = paxClass.getDescription();
		if (description == null || description.equals("")) {
			return classDocumentation;
		}
		List<String> descriptionStrings = stringUtils.splitString(description);
		if (descriptionStrings != null) {
			classDocumentation.addAll(descriptionStrings);
		}
		return classDocumentation;
	}

	@Override
	public List<String> writeMethodDocumentation(final PaxMethod paxMethod) {
		paxMethodValidator.validate(paxMethod);
		String description = paxMethod.getDescription();
		String returnDescription = paxMethod.getReturnDescription();
		List<PaxParam> params = paxMethod.getParams();
		List<String> methodDocumentation = new ArrayList<String>();
		if (description != null && !description.isEmpty()) {
			List<String> splitDescription = stringUtils.splitString(description);
			if (splitDescription != null) {
				methodDocumentation.addAll(splitDescription);
			}
		}
		if (params != null) {
			for(PaxParam param : params) {
				List<String> paramDocumentation = writeParamDocumentation(param);
				if (paramDocumentation != null) {
					methodDocumentation.addAll(paramDocumentation);
				}
			}
		}
		if (returnDescription != null && !returnDescription.isEmpty()) {
			List<String> returnSplit = stringUtils.splitString("@return " + returnDescription);
			if(returnSplit != null) {
				methodDocumentation.addAll(returnSplit);
			}
		}
		return methodDocumentation;
	}

	@Override
	public List<String> writeParamDocumentation(final PaxParam paxParam) {
		paxParamValidator.validate(paxParam);
		String description = paxParam.getDescription();
		if (description == null || description.equals("")) {
			return new ArrayList<String>();
		}
		String documentation = "@param " + paxParam.getName() + " " + description;
		List<String> returnSplit = stringUtils.splitString(documentation);
		if (returnSplit == null) {
			return new ArrayList<String>();
		}
		return returnSplit;
	}

	@Override
	public String writePaxType(final String type) throws PaxValidationException {
		if (type == null || type.isEmpty()) {
			throw new PaxValidationException("Could not find java class mapping for type '" + type + "'.");
		}
		if (javaClassMap.containsKey(type)) {
			return javaClassMap.get(type).getClassName();
		} else if (type.startsWith("map<") && type.endsWith(">")) {
			String innerString = type.substring(4);
			innerString = innerString.substring(0, innerString.length() - 1);
			List<String> mapSides = splitInnerMapStringIntoTwo(innerString);
			return "Map<" + writePaxType(mapSides.get(0)) + ", " + writePaxType(mapSides.get(1)) + ">";
		} else if (type.startsWith("list<") && type.endsWith(">")) {
			String innerType = type.substring(5);
			innerType = innerType.substring(0, innerType.length() - 1);
			return "List<" + writePaxType(innerType) + ">";
		}
		return type;
	}
	
	public List<String> splitInnerMapStringIntoTwo(final String innerString) {
		String leftSide = "";
		String rightSide = "";
		int innerCount = 0;
		boolean addRightSide = false;
		for(char s : innerString.toCharArray()) {
			if (addRightSide) {
				rightSide += s;
			} else if (s == '<') {
				innerCount++;
				leftSide += s;
			} else if (s == '>') {
				innerCount--;
				leftSide += s;
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
	
	@Override
	public String writePaxModifier(final String modifier) {
		if ("const".equals(modifier)) {
			return "final";
		}
		return modifier;
	}

	@Override
	public List<String> writeImports(final PaxClass paxClass) {
		Set<String> javaImports = new HashSet<String>();
		if (paxClass.getMethods() != null) {
			for (PaxMethod method : paxClass.getMethods()) {
				javaImports.addAll(getJavaImportForType(method.getReturnType(), paxClass.getJavaMetadata().getNamespace()));
				if (method.getParams() != null) {
					for (PaxParam param : method.getParams()) {
						javaImports.addAll(getJavaImportForType(param.getType(), paxClass.getJavaMetadata().getNamespace()));
					}
				}
			}
		}
		return new ArrayList<String>(javaImports);
	}
	
	public Set<String> getJavaImportForType(final String type, final String currentNamespace) {
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
			returnSet.addAll(getJavaImportForType("map", currentNamespace));
			returnSet.addAll(getJavaImportForType(mapSides.get(0), currentNamespace));
			returnSet.addAll(getJavaImportForType(mapSides.get(1), currentNamespace));
		} else if (type.startsWith("list<") && type.endsWith(">")) {
			String innerType = type.substring(5);
			innerType = innerType.substring(0, innerType.length() - 1);
			returnSet.addAll(getJavaImportForType("list", currentNamespace));
			returnSet.addAll(getJavaImportForType(innerType, currentNamespace));
		}
		return returnSet;
	}

}
