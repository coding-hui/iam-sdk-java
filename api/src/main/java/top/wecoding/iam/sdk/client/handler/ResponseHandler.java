package top.wecoding.iam.sdk.client.handler;

import org.apache.hc.core5.http.io.HttpClientResponseHandler;

import java.lang.reflect.Type;

/**
 * @author Wecoding Liu
 * @since 0.8
 */
public interface ResponseHandler<T> extends HttpClientResponseHandler<T> {

	/**
	 * set response type.
	 * @param responseType responseType
	 */
	void setResponseType(Type responseType);

}
