package top.wecoding.iam.sdk.client.auth;

import top.wecoding.iam.sdk.client.param.Cookie;
import top.wecoding.iam.sdk.client.param.Header;
import top.wecoding.iam.sdk.client.param.Query;

/**
 * @author Wecoding Liu
 * @since 0.8
 */
public interface Authentication {

	/**
	 * Apply authentication settings to header and query params.
	 * @param queryParams query parameters
	 * @param headerParams header parameters
	 * @param cookieParams cookie parameters
	 */
	void apply(Query queryParams, Header headerParams, Cookie cookieParams);

}
