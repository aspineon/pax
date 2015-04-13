package ca.mestevens.java.pax.models;

import java.util.ArrayList;
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
		if (params != null) {
			for(PaxParam param : params) {
				if (i != 0) {
					methodString += ", ";
				}
				methodString += param.toJavaString();
				i++;
			}
		}
		methodString += ");";
		return methodString;
	}
	
	public String toObjcString() {
		String methodString = "- (" + PlatformTypeConverterUtil.getObjcType(returnType) + ")" + name;
		int i = 0;
		if (params != null) {
			for(PaxParam param : params) {
				if (i != 0) {
					methodString += " with" + Character.toUpperCase(param.getName().charAt(0)) + param.getName().substring(1);
				}
				methodString += ":" + param.toObjcString();
				i++;
			}
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
			List<String> splitDescription = PlatformTypeConverterUtil.splitString(description);
			for (String line : splitDescription) {
				documentation += "\t * " + line + "\n";
			}
		}
		if (params != null) {
			for(PaxParam param : params) {
				List<String> paramDocumentation = param.getJavaDocumentation();
				if (paramDocumentation != null && !paramDocumentation.isEmpty()) {
					for (String paramDoc : paramDocumentation) {
						documentation += "\t" + paramDoc + "\n";
					}
				}
			}
		}
		if (returnDescription != null && !returnDescription.isEmpty()) {
			List<String> returnSplit = PlatformTypeConverterUtil.splitString(returnDescription);
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
	
	public String getObjcDocumentation() {
		return getJavaDocumentation().replaceAll("\t", "");
	}
	
	
	
}
