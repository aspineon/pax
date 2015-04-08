package ca.mestevens.java.pax.models;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaxProject {

	private String javaOutputFolder;
	private String objcOutputFolder;
	private List<PaxClass> classes;
	
}
