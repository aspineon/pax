package ca.mestevens.java.pax.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StringUtils {
	
	private final int wordsPerLine;
	
	public StringUtils(int wordsPerLine) {
		this.wordsPerLine = wordsPerLine;
	}
	
	public List<String> splitString(final String string) {
		if (string == null || string.isEmpty()) {
			return new ArrayList<String>();
		}
		List<String> splitDescription = new ArrayList<String>(Arrays.asList(string.trim().split(" ")));
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
