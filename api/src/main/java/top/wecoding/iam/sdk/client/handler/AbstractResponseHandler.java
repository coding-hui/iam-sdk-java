package top.wecoding.iam.sdk.client.handler;

import org.apache.hc.core5.http.ClassicHttpResponse;
import org.apache.hc.core5.http.Header;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.HttpStatus;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import top.wecoding.iam.sdk.exception.ApiException;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Wecoding Liu
 * @since 0.8
 */
public abstract class AbstractResponseHandler<T> implements ResponseHandler<T> {

	private Type responseType;

	@Override
	public final void setResponseType(Type responseType) {
		this.responseType = responseType;
	}

	@Override
	public final T handleResponse(ClassicHttpResponse response) throws IOException {
		final HttpEntity entity = response.getEntity();
		if (response.getCode() >= HttpStatus.SC_REDIRECTION) {
			String body = null;
			try {
				body = EntityUtils.toString(entity);
			}
			catch (ParseException ignore) {
			}
			finally {
				EntityUtils.consume(entity);
			}
			throw new ApiException(response.getCode(), response.getReasonPhrase(),
					transformResponseHeaders(response.getHeaders()), body);
		}
		return entity == null ? null : convertResult(response, responseType);
	}

	/**
	 * Abstract convertResult method, Different types of converters for expansion.
	 * @param response http client response
	 * @param responseType responseType
	 * @return HttpRestResult
	 */
	public abstract T convertResult(ClassicHttpResponse response, Type responseType) throws IOException;

	protected Map<String, List<String>> transformResponseHeaders(Header[] headers) {
		Map<String, List<String>> headersMap = new HashMap<>();
		for (Header header : headers) {
			List<String> valuesList = headersMap.get(header.getName());
			if (valuesList != null) {
				valuesList.add(header.getValue());
			}
			else {
				valuesList = new ArrayList<>();
				valuesList.add(header.getValue());
				headersMap.put(header.getName(), valuesList);
			}
		}
		return headersMap;
	}

}