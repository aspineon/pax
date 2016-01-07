package ca.mestevens.java.pax.exceptions;

@SuppressWarnings("serial")
public class PaxValidationException extends RuntimeException {
	
	public PaxValidationException(String message) {
		super(message);
	}
	
	public PaxValidationException(Throwable cause) {
		super(cause);
	}

}
