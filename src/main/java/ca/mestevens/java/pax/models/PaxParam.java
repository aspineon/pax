package ca.mestevens.java.pax.models;

import java.util.ArrayList;
import java.util.List;

import ca.mestevens.java.pax.utils.PlatformTypeConverterUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaxParam {

	private String type;
	private String name;
	private String description;
	private String modifier;
	
	public String toJavaString() {
		String javaString = "";
		String methodParam = PlatformTypeConverterUtil.getJavaType(type);
		if (modifier != null && !modifier.isEmpty()) {
			javaString += PlatformTypeConverterUtil.getJavaModifier(modifier) + " ";
		}
		javaString += methodParam + " " + name;
		return javaString;
	}
	
	public List<String> getJavaDocumentation() {
		if (description == null || description.equals("")) {
			return new ArrayList<String>();
		}
		String documentation = "@param " + name + " " + description;
		List<String> returnSplit = PlatformTypeConverterUtil.splitString(documentation);
		int i = 0;
		for(String line : returnSplit) {
			returnSplit.set(i, " * " + line);
			i++;
		}
		return returnSplit;
	}
	
	public String toObjcString() {
		String methodParam = PlatformTypeConverterUtil.getObjcType(type);
		if (modifier != null && !modifier.isEmpty()) {
			methodParam += " " + PlatformTypeConverterUtil.getObjcModifier(modifier);
		}
		return "(" + methodParam + ")" + name;
	}
	
}
