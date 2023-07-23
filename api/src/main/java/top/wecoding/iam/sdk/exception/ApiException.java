package top.wecoding.iam.sdk.exception;

import lombok.Getter;

import java.util.List;
import java.util.Map;

/**
 * @author Wecoding Liu
 * @since 0.8
 */
@Getter
public class ApiException extends RuntimeException {

	private int code = 0;

	private String responseBody = null;

	private Map<String, List<String>> responseHeaders = null;

	public ApiException() {
	}

	public ApiException(Throwable throwable) {
		super(throwable);
	}

	public ApiException(String message) {
		super(message);
	}

	public ApiException(String message, Throwable throwable, int code, Map<String, List<String>> responseHeaders,
			String responseBody) {
		super(message, throwable);
		this.code = code;
		this.responseHeaders = responseHeaders;
		this.responseBody = responseBody;
	}

	public ApiException(String message, int code, Map<String, List<String>> responseHeaders, String responseBody) {
		this(message, null, code, responseHeaders, responseBody);
	}

	public ApiException(int code, String message) {
		super(message);
		this.code = code;
	}

	public ApiException(int code, String message, Map<String, List<String>> responseHeaders, String responseBody) {
		this(code, message);
		this.responseHeaders = responseHeaders;
		this.responseBody = responseBody;
	}

}