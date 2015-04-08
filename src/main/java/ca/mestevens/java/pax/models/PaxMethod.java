package ca.mestevens.java.pax.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ca.mestevens.java.pax.utils.PlatformTypeConverterUtil;

import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PaxMethod {

	private String name;
	@SerializedName("return")
	private String returnType;
	private String returnDescription;
	private String description;
	private List<PaxParam> params;
	
	public PaxMethod() {
		params = new ArrayList<PaxParam>();
	}
	
	public String toJavaString() {
		String methodString = "public " + PlatformTypeConverterUtil.getJavaType(returnType) + " " + name + "(";
		int i = 0;
		for(PaxParam param : params) {
			if (i != 0) {
				methodString += ", ";
			}
			String methodParam = PlatformTypeConverterUtil.getJavaType(param.getType());
			methodString += methodParam + " " + param.getName();
			i++;
		}
		methodString += ");";
		return methodString;
	}
	
	public String toObjcString() {
		String methodString = "- (" + PlatformTypeConverterUtil.getObjcType(returnType) + ")" + name;
		int i = 0;
		for(PaxParam param : params) {
			String methodParam = PlatformTypeConverterUtil.getObjcType(param.getType());
			if (i != 0) {
				methodString += " with" + Character.toUpperCase(param.getName().charAt(0)) + param.getName().substring(1);
			}
			methodString += ":(" + methodParam + ")" + param.getName();
			i++;
		}
		methodString += ";";
		return methodString;
	}
	
	public String getJavaDocumentation() {
		if ((description == null || description.isEmpty()) && (returnDescription == null || returnDescription.isEmpty())) {
			boolean parameterDocumentation = false;
			for (PaxParam param : params) {
				if (!(param.getDescription() == null) && !param.getDescription().isEmpty()) {
					parameterDocumentation = true;
					break;
				}
			}
			if (!parameterDocumentation) {
				return null;
			}
		}
		String documentation = "";
		documentation += "\t/**\n";
		
		if (description != null && !description.isEmpty()) {
			List<String> splitDescription = splitString(description);
			for (String line : splitDescription) {
				documentation += "\t * " + line + "\n";
			}
		}
		for(PaxParam param : params) {
			String paramDocumentation = param.getJavaDocumentation();
			if (paramDocumentation != null && !paramDocumentation.isEmpty()) {
				documentation += "\t" + param.getJavaDocumentation();
			}
		}
		if (returnDescription != null && !returnDescription.isEmpty()) {
			List<String> returnSplit = splitString(returnDescription);
			boolean firstLine = true;
			for (String line : returnSplit) {
				if (firstLine) {
					documentation += "\t * @return ";
					firstLine = false;
				} else {
					documentation += "\t * ";
				}
				documentation += line + "\n";
			}
		}
		documentation += "\t */\n";
		return documentation;
	}
	
	private List<String> splitString(String string) {
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
