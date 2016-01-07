package ca.mestevens.java.pax;

import java.util.List;

import ca.mestevens.java.pax.exceptions.PaxValidationException;
import ca.mestevens.java.pax.models.PaxClass;
import ca.mestevens.java.pax.models.PaxMethod;
import ca.mestevens.java.pax.models.PaxParam;

public interface PaxClassWriter {
	
	List<String> writePaxClass(final PaxClass paxClass) throws PaxValidationException;
	List<String> writePaxMethod(final PaxMethod paxMethod) throws PaxValidationException;
	String writePaxParam(final PaxParam paxParam) throws PaxValidationException;
	List<String> writeClassDocumentation(final PaxClass paxClass);
	List<String> writeMethodDocumentation(final PaxMethod paxMethod);
	List<String> writeParamDocumentation(final PaxParam paxParam);
	String writePaxType(final String type) throws PaxValidationException;
	String writePaxModifier(final String modifier);
	List<String> writeImports(final PaxClass paxClass);
	
}