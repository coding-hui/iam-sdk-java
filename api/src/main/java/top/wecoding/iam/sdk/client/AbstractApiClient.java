package top.wecoding.iam.sdk.client;

import com.fasterxml.jackson.databind.JavaType;
import top.wecoding.iam.sdk.client.handler.ApiResultResponseHandler;
import top.wecoding.iam.sdk.client.handler.BeanResponseHandler;
import top.wecoding.iam.sdk.client.handler.ResponseHandler;
import top.wecoding.iam.sdk.client.handler.StringResponseHandler;
import top.wecoding.iam.sdk.util.JacksonUtils;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * For ApiClient, provide initialization and register of response converter.
 *
 * @author Wecoding Liu
 * @since 0.8
 */
@SuppressWarnings("all")
public abstract class AbstractApiClient {

	private final Map<String, ResponseHandler> responseHandlerMap = new HashMap<String, ResponseHandler>();

	public AbstractApiClient() {
		initDefaultResponseHandler();
	}

	private void initDefaultResponseHandler() {
		// init response handler
		this.responseHandlerMap.put(StringResponseHandler.class.getName(), new StringResponseHandler());
		this.responseHandlerMap.put(ApiResultResponseHandler.class.getName(), new ApiResultResponseHandler());
		this.responseHandlerMap.put(BeanResponseHandler.class.getName(), new BeanResponseHandler());
	}

	/**
	 * register customization Response Handler.
	 * @param responseHandlerType handler type
	 * @param responseHandler {@link ResponseHandler}
	 */
	public void registerResponseHandler(String responseHandlerType, ResponseHandler responseHandler) {
		this.responseHandlerMap.put(responseHandlerType, responseHandler);
	}

	/**
	 * Select a response handler by responseType.
	 * @param responseType responseType
	 * @return HttpClientResponseHandler
	 */
	protected ResponseHandler selectResponseHandler(Type responseType) {
		ResponseHandler responseHandler = null;
		if (responseType == null) {
			responseHandler = this.responseHandlerMap.get(String.class.getName());
		}
		if (responseHandler == null) {
			JavaType javaType = JacksonUtils.constructJavaType(responseType);
			String name = javaType.getRawClass().getName();
			responseHandler = this.responseHandlerMap.get(name);
		}
		// When the corresponding type of response handler cannot be obtained,
		// the default bean response handler is used
		if (responseHandler == null) {
			responseHandler = this.responseHandlerMap.get(BeanResponseHandler.class.getName());
		}
		responseHandler.setResponseType(responseType);
		return responseHandler;
	}

}
