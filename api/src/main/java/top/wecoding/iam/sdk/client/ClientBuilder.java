package top.wecoding.iam.sdk.client;

/**
 * @author Wecoding Liu
 * @since 0.8
 */
public interface ClientBuilder {

	/**
	 * Sets the HTTP proxy to be used when communicating with the IAM API server.
	 * @param proxy the {@code Proxy} you need to use.
	 * @return ClientBuilder
	 */
	ClientBuilder setProxy(Proxy proxy);

	/**
	 * Sets the maximum number of seconds to wait when retrying before giving up.
	 * @param maxElapsed retry max elapsed duration in seconds
	 * @return ClientBuilder
	 */
	ClientBuilder setRetryMaxElapsed(int maxElapsed);

	/**
	 * Sets the maximum number of attempts to retrying before giving up.
	 * @param maxAttempts retry max attempts
	 * @return ClientBuilder
	 */
	ClientBuilder setRetryMaxAttempts(int maxAttempts);

	ApiClient build();

}
