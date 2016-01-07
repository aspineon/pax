package ca.mestevens.java.pax.validation;

import java.util.Arrays;

import javax.validation.groups.Default;

import ca.mestevens.java.pax.validation.groups.JavaGroup;

public class JavaValidator<T> extends Validator<T> {

	public JavaValidator() {
		super(Arrays.asList(Default.class, JavaGroup.class));
	}
	
}
