package cn.hot.exception;

public class EncodingException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2367946467593158744L;

	public EncodingException(String message) {
		super(message);
	}

	public EncodingException(Throwable throwable) {
		super(throwable);
	}
}
