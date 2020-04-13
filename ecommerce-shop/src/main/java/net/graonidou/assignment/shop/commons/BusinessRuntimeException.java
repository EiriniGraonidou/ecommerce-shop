package net.graonidou.assignment.shop.commons;

/**
 * Convenient hierarchy definition for exceptions occurring due to not respecting the business rules. 
 * 
 * @author Eirini Graonidou
 *
 */
public class BusinessRuntimeException extends RuntimeException {

	/**
	 * default serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	public String message;
	
	public BusinessRuntimeException(String message) {
		super(message);
		this.message = message;
	}

}
