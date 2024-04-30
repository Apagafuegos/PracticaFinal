package equiposService;

public class EquiposServiceException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EquiposServiceException() {
	}

	public EquiposServiceException(String message) {
		super(message);
	}

	public EquiposServiceException(Throwable cause) {
		super(cause);
	}

	public EquiposServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public EquiposServiceException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
