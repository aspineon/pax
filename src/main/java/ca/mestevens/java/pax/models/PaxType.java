package ca.mestevens.java.pax.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaxType {
	
	private String namespace;
	private String className;
	private PaxTypeModifier type;

}
