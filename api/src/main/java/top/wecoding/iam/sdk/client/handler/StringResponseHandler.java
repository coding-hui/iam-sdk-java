package top.wecoding.iam.sdk.client.handler;

import org.apache.hc.client5.http.ClientProtocolException;
import org.apache.hc.core5.http.ClassicHttpResponse;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * @author Wecoding Liu
 * @since 0.8
 */
public class StringResponseHandler extends AbstractResponseHandler<String> {

	@Override
	public String convertResult(ClassicHttpResponse response, Type responseType) throws IOException {
		try {
			return EntityUtils.toString(response.getEntity());
		}
		catch (final ParseException ex) {
			throw new ClientProtocolException(ex);
		}
	}

}
