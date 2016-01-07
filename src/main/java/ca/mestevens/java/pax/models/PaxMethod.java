package ca.mestevens.java.pax.models;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Wither;

@Data
@Wither
@AllArgsConstructor
public class PaxMethod {

	@NotNull
	@Size(min = 1)
	private String name;
	@SerializedName("return")
	private String returnType;
	private String returnDescription;
	private String description;
	@Valid
	private List<PaxParam> params;
	
	public PaxMethod() {
		params = new ArrayList<PaxParam>();
	}
	
}
