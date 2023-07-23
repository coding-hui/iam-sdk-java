package top.wecoding.iam.sdk.client.handler;

import org.apache.hc.core5.http.ClassicHttpResponse;
import top.wecoding.iam.sdk.util.JacksonUtils;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;

/**
 * @author Wecoding Liu
 * @since 0.8
 */
public class BeanResponseHandler<T> extends AbstractResponseHandler<T> {

	@Override
	public T convertResult(ClassicHttpResponse response, Type responseType) throws IOException {
		InputStream body = response.getEntity().getContent();
		return JacksonUtils.toObj(body, responseType);
	}

}
