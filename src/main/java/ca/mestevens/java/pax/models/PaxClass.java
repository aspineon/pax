package ca.mestevens.java.pax.models;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import ca.mestevens.java.pax.validation.groups.JavaGroup;
import ca.mestevens.java.pax.validation.groups.ObjcGroup;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Wither;

@Data
@Wither
@NoArgsConstructor
@AllArgsConstructor
public class PaxClass {

	@NotNull
	@Size(min = 1)
	private String className;
	@Valid
	@NotNull(groups = JavaGroup.class)
	private PaxLanguageMetadata javaMetadata;
	//@Valid
	@NotNull(groups = ObjcGroup.class)
	private PaxLanguageMetadata objcMetadata;
	private String description;
	@Valid
	private List<PaxMethod> methods;
	
}
