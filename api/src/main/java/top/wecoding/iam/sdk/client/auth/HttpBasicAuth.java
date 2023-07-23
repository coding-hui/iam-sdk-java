package top.wecoding.iam.sdk.client.auth;

import lombok.Getter;
import lombok.Setter;
import top.wecoding.iam.sdk.client.param.Cookie;
import top.wecoding.iam.sdk.client.param.Header;
import top.wecoding.iam.sdk.client.param.Query;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * @author Wecoding Liu
 * @since 0.8
 */
@Getter
@Setter
public class HttpBasicAuth implements Authentication {

	public static final String NAME = "basic";

	private String username;

	private String password;

	public HttpBasicAuth() {
	}

	public HttpBasicAuth(String username, String password) {
		this.username = username;
		this.password = password;
	}

	@Override
	public void apply(Query queryParams, Header headerParams, Cookie cookieParams) {
		if (username == null && password == null) {
			return;
		}
		String str = (username == null ? "" : username) + ":" + (password == null ? "" : password);
		headerParams.addParam("Authorization",
				"Basic " + Base64.getEncoder().encodeToString(str.getBytes(StandardCharsets.UTF_8)));
	}

}
