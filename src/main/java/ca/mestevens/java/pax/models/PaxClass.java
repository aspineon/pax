package ca.mestevens.java.pax.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ca.mestevens.java.pax.utils.PlatformTypeConverterUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaxClass {

	private String className;
	private PaxLanguageMetadata javaMetadata;
	private PaxLanguageMetadata objcMetadata;
	private String description;
	private List<PaxMethod> methods;
	
	public String toJavaString() {
		String classString = "package " + javaMetadata.getNamespace() + ";\n\n";
		String importString = getJavaImports();
		if (!importString.isEmpty()) {
			classString += importString + "\n";
		}
		String documentation = getJavaDocumentation();
		if (documentation != null) {
			classString += documentation;
		}
		classString += "public interface " + javaMetadata.getClassName() + " {\n\n";
		for(PaxMethod method : methods) {
			String methodDocumentation = method.getJavaDocumentation();
			if (methodDocumentation != null && !methodDocumentation.isEmpty()) {
				classString += methodDocumentation;
			}
			classString += "\t" + method.toJavaString() + "\n\n";
		}
		classString += "}";
		return classString;
	}
	
	public String toObjcString() {
		String classString = "@protocol " + objcMetadata.getClassName() + " <NSObject>\n\n";
		classString += "@required\n\n";
		for(PaxMethod method : methods) {
			classString += method.toObjcString() + "\n\n";
		}
		classString += "@end";
		return classString;
	}
	
	public String getJavaImports() {
		String javaImports = "";
		for(PaxMethod method : methods) {
			String returnNamespace = PlatformTypeConverterUtil.getJavaImportForType(method.getReturnType(), javaMetadata.getNamespace());
			if (returnNamespace != null) {
				String importString = "import " + returnNamespace + ";";
				if (!javaImports.contains(importString)) {
					javaImports += importString + "\n";
				}
			}
			for (PaxParam param : method.getParams()) {
				String namespace = PlatformTypeConverterUtil.getJavaImportForType(param.getType(), javaMetadata.getNamespace());
				if (namespace != null) {
					String paramImportString = "import " + namespace + ";";
					if (!javaImports.contains(paramImportString)) {
						javaImports += paramImportString + "\n";
					}
				}
			}
		}
		return javaImports;
	}
	
	public String getJavaDocumentation() {
		if (description == null || description.equals("")) {
			return null;
		}
		String documentation = "";
		documentation += "/**\n";
		final int wordsPerLine = 15;
		List<String> descriptionStrings = new ArrayList<String>(Arrays.asList(description.split(" ")));
		int i = 0;
		for (String descriptionString : descriptionStrings) {
			if (i % wordsPerLine == 0) {
				documentation += " * ";
			}
			documentation += descriptionString;
			if (i % wordsPerLine == wordsPerLine - 1) {
				documentation += "\n";
			} else if (i < descriptionStrings.size() - 1){
				documentation += " ";
			} else {
				documentation += "\n";
			}
			i++;
		}
		documentation += " */\n";
		return documentation;
	}
	
}
