package ca.mestevens.java.pax.validation;

import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;

import ca.mestevens.java.pax.exceptions.PaxValidationException;

public abstract class Validator<T> {

	protected javax.validation.Validator validator;
	protected final List<Class<?>> groups;
	
	protected Validator(List<Class<?>> groups) {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		this.validator = factory.getValidator();
		this.groups = groups;
	}
	
	public void validate(T object) throws PaxValidationException {
		Set<ConstraintViolation<T>> violations = validator.validate(object, groups.toArray(new Class<?>[groups.size()]));
		if (!violations.isEmpty()) {
			String exceptionMessage = object.getClass().getSimpleName() + ": ";
			for(ConstraintViolation<T> violation : violations) {
				exceptionMessage += violation.getPropertyPath().toString() + " " + violation.getMessage() + ", ";
			}
			exceptionMessage = exceptionMessage.substring(0, exceptionMessage.length() - 2);
			throw new PaxValidationException(exceptionMessage);
		}
	}
	
}
