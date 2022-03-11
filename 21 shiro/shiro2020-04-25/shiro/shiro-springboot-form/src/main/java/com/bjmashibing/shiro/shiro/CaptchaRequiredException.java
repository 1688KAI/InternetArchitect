package com.bjmashibing.shiro.shiro;

import org.apache.shiro.authc.AccountException;

/**
 * 需要验证码
 * @author sunzhiqiang
 */
public class CaptchaRequiredException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CaptchaRequiredException() {
		super();
	}

	public CaptchaRequiredException(String message) {
		super(message);
	}

	public CaptchaRequiredException(String message, Throwable cause) {
		super(message, cause);
	}

	public CaptchaRequiredException(Throwable cause) {
		super(cause);
	}

	protected CaptchaRequiredException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
