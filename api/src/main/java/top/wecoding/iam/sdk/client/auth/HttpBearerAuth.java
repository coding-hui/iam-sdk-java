package top.wecoding.iam.sdk.client.auth;

import lombok.Getter;
import lombok.Setter;
import top.wecoding.iam.sdk.client.param.Cookie;
import top.wecoding.iam.sdk.client.param.Header;
import top.wecoding.iam.sdk.client.param.Query;

/**
 * @author Wecoding Liu
 * @since 0.8
 */
@Getter
@Setter
public class HttpBearerAuth implements Authentication {

	public static final String NAME = "bearer";

	public static final String DEFAULT_SCHEME = "bearer";

	private final String scheme;

	private String bearerToken;

	public HttpBearerAuth() {
		this(DEFAULT_SCHEME);
	}

	public HttpBearerAuth(String scheme) {
		this.scheme = scheme;
	}

	@Override
	public void apply(Query queryParams, Header headerParams, Cookie cookieParams) {
		if (bearerToken == null) {
			return;
		}
		headerParams.addParam("Authorization", (scheme != null ? upperCaseBearer(scheme) + " " : "") + bearerToken);
	}

	private String upperCaseBearer(String scheme) {
		return ("bearer".equalsIgnoreCase(scheme)) ? "Bearer" : scheme;
	}

}
