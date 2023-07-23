package top.wecoding.iam.sdk.client.handler;

import org.apache.hc.core5.http.ClassicHttpResponse;
import org.apache.hc.core5.http.Header;
import top.wecoding.iam.sdk.model.ApiResult;
import top.wecoding.iam.sdk.util.JacksonUtils;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Wecoding Liu
 * @since 0.8
 */
public class ApiResultResponseHandler<T> extends AbstractResponseHandler<T> {

	private static <T> ApiResult<T> convert(ClassicHttpResponse response, ApiResult<T> apiResult) {
		ApiResult<T> httpRestResult = new ApiResult<>();
		httpRestResult.setCode(apiResult.getCode());
		httpRestResult.setMsg(apiResult.getMsg());
		httpRestResult.setData(apiResult.getData());
		Header[] headers = response.getHeaders();
		Map<String, String> responseHeaders = new HashMap<>();
		for (Header header : headers) {
			responseHeaders.put(header.getName(), header.getValue());
		}
		httpRestResult.setHeaders(responseHeaders);
		return httpRestResult;
	}

	@Override
	@SuppressWarnings("unchecked")
	public T convertResult(ClassicHttpResponse response, Type responseType) throws IOException {
		InputStream body = response.getEntity().getContent();
		T extractBody = (T) JacksonUtils.toObj(body, ApiResult.class);
		return (T) convert(response, (ApiResult<T>) extractBody);
	}

}
