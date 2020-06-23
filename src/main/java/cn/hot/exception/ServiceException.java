package cn.hot.exception;

/**
 * 处理业务逻辑消息异常
 */
public class ServiceException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7951037604714347151L;

	public ServiceException(String message) {
		super(message);
	}

	public ServiceException(String message, Throwable throwable) {
		super(message, throwable);
	}

}
