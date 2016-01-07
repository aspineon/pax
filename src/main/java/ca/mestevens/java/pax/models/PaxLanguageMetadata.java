package ca.mestevens.java.pax.models;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Wither;

@Data
@Wither
@NoArgsConstructor
@AllArgsConstructor
public class PaxLanguageMetadata {

	@NotNull
	@Size(min = 1)
	private String namespace;
	@NotNull
	@Size(min = 1)
	private String className;
	private String outputFolder;
	
}