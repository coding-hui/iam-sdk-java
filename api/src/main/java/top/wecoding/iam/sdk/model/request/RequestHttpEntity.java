package top.wecoding.iam.sdk.model.request;

import lombok.Getter;
import lombok.Setter;
import top.wecoding.iam.sdk.client.HttpClientConfig;
import top.wecoding.iam.sdk.client.auth.Authentication;
import top.wecoding.iam.sdk.client.param.Cookie;
import top.wecoding.iam.sdk.client.param.Header;
import top.wecoding.iam.sdk.client.param.Query;

import java.util.Map;

/**
 * @author Wecoding Liu
 * @since 0.8
 */
@Getter
@Setter
public class RequestHttpEntity {

	private final Header headers = Header.newInstance();

	private final HttpClientConfig httpClientConfig;

	private final Query query;

	private final Cookie cookie;

	private final Object body;

	private final String[] authNames;

	public RequestHttpEntity(Header header, Query query, String[] authNames) {
		this(null, header, query, authNames);
	}

	public RequestHttpEntity(Header header, Object body, String[] authNames) {
		this(null, header, null, null, body, authNames);
	}

	public RequestHttpEntity(Header header, Query query, Object body, String[] authNames) {
		this(null, header, query, null, body, authNames);
	}

	public RequestHttpEntity(HttpClientConfig httpClientConfig, Header header, Query query, String[] authNames) {
		this(httpClientConfig, header, query, null, null, authNames);
	}

	public RequestHttpEntity(HttpClientConfig httpClientConfig, Header header, Object body, String[] authNames) {
		this(httpClientConfig, header, null, null, body, authNames);
	}

	public RequestHttpEntity(HttpClientConfig httpClientConfig, Header header, Query query, Cookie cookie, Object body,
			String[] authNames) {
		handleHeader(header);
		this.httpClientConfig = httpClientConfig;
		this.query = query;
		this.body = body;
		this.cookie = cookie;
		this.authNames = authNames;
	}

	private void handleHeader(Header header) {
		if (header != null && !header.getHeader().isEmpty()) {
			Map<String, String> headerMap = header.getHeader();
			headers.addAll(headerMap);
		}
	}

	public boolean isEmptyBody() {
		return body == null;
	}

	/**
	 * Update query and header parameters based on authentication settings.
	 */
	public void updateParamsForAuth(Map<String, Authentication> authentications) {
		for (String authName : authNames) {
			Authentication auth = authentications.get(authName);
			if (auth == null) {
				throw new RuntimeException("Authentication undefined: " + authName);
			}
			auth.apply(query, headers, cookie);
		}
	}

}
