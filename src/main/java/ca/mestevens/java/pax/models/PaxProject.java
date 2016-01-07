package ca.mestevens.java.pax.models;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaxProject {

	@NotNull
	@Size(min = 1)
	private String javaOutputFolder;
	@NotNull
	@Size(min = 1)
	private String objcOutputFolder;
	@Valid
	@NotNull
	@Size(min = 1)
	private List<PaxClass> classes;
	
}
