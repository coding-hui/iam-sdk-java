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
public class ApiKeyAuth implements Authentication {

	public static final String NAME = "apiKey";

	private final String location;

	private final String paramName;

	private String apiKey;

	private String apiKeyPrefix;

	public ApiKeyAuth(String location, String paramName) {
		this.location = location;
		this.paramName = paramName;
	}

	@Override
	public void apply(Query queryParams, Header headerParams, Cookie cookieParams) {
		if (apiKey == null) {
			return;
		}
		String value;
		if (apiKeyPrefix != null) {
			value = apiKeyPrefix + " " + apiKey;
		}
		else {
			value = apiKey;
		}
		if ("query".equals(location)) {
			queryParams.addParam(paramName, value);
		}
		else if ("header".equals(location)) {
			headerParams.addParam(paramName, value);
		}
		else if ("cookie".equals(location)) {
			cookieParams.addParam(paramName, value);
		}
	}

}
