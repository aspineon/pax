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
public class PaxParam {

	@NotNull
	@Size(min = 1)
	private String type;
	@NotNull
	@Size(min = 1)
	private String name;
	private String description;
	private String modifier;
	
}
