package top.wecoding.iam.sdk.client;

import org.apache.hc.client5.http.auth.AuthScope;
import org.apache.hc.client5.http.auth.UsernamePasswordCredentials;
import org.apache.hc.client5.http.config.ConnectionConfig;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.DefaultAuthenticationStrategy;
import org.apache.hc.client5.http.impl.DefaultConnectionKeepAliveStrategy;
import org.apache.hc.client5.http.impl.DefaultHttpRequestRetryStrategy;
import org.apache.hc.client5.http.impl.auth.BasicCredentialsProvider;
import org.apache.hc.client5.http.impl.classic.DefaultBackoffStrategy;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder;
import org.apache.hc.core5.http.HttpHost;
import org.apache.hc.core5.http.impl.DefaultConnectionReuseStrategy;
import org.apache.hc.core5.util.Timeout;
import top.wecoding.iam.sdk.config.ClientConfiguration;

/**
 * @author Wecoding Liu
 * @since 0.8
 */
public class DefaultClientBuilder implements ClientBuilder {

	private final ClientConfiguration clientConfig = new ClientConfiguration();

	@Override
	public ClientBuilder setProxy(Proxy proxy) {
		if (proxy == null) {
			throw new IllegalArgumentException("proxy argument cannot be null.");
		}
		else {
			this.clientConfig.setProxyHost(proxy.getHost());
			this.clientConfig.setProxyPort(proxy.getPort());
			this.clientConfig.setProxyUsername(proxy.getUsername());
			this.clientConfig.setProxyPassword(proxy.getPassword());
			return this;
		}
	}

	@Override
	public ClientBuilder setRetryMaxElapsed(int maxElapsed) {
		this.clientConfig.setRetryMaxElapsed(maxElapsed);
		return this;
	}

	@Override
	public ClientBuilder setRetryMaxAttempts(int maxAttempts) {
		this.clientConfig.setRetryMaxAttempts(maxAttempts);
		return this;
	}

	@Override
	public ApiClient build() {
		HttpClientBuilder httpClientBuilder = createHttpClientBuilder(clientConfig);

		ApiClient apiClient = new ApiClient(clientConfig, httpClientBuilder.build());

		apiClient.setBasePath(clientConfig.getClientId());

		if (this.clientConfig.getProxy() != null) {
			this.setProxy(httpClientBuilder, this.clientConfig);
		}

		return apiClient;
	}

	/**
	 * Override to customize the client, allowing one to add additional interceptors.
	 * @param clientConfig the current ClientConfiguration
	 * @return an {@link HttpClientBuilder} initialized with default configuration
	 */
	protected HttpClientBuilder createHttpClientBuilder(ClientConfiguration clientConfig) {
		return HttpClients.custom()
			.setDefaultRequestConfig(createHttpRequestConfigBuilder(clientConfig).build())
			.setConnectionManager(createHttpClientConnectionManagerBuilder(clientConfig).build())
			.setRetryStrategy(new DefaultHttpRequestRetryStrategy())
			.setConnectionBackoffStrategy(new DefaultBackoffStrategy())
			.setKeepAliveStrategy(new DefaultConnectionKeepAliveStrategy())
			.setConnectionReuseStrategy(new DefaultConnectionReuseStrategy())
			.disableCookieManagement();
	}

	/**
	 * Override to customize the request config
	 * @param clientConfig the current clientConfig
	 * @return a {@link RequestConfig.Builder} initialized with default configuration
	 */
	protected RequestConfig.Builder createHttpRequestConfigBuilder(ClientConfiguration clientConfig) {
		return RequestConfig.custom()
			.setResponseTimeout(Timeout.ofSeconds(clientConfig.getConnectionTimeout()))
			.setConnectionRequestTimeout(Timeout.ofSeconds(clientConfig.getConnectionTimeout()));
	}

	/**
	 * Override to customize the connection manager, allowing the increase of max
	 * connections
	 * @param clientConfig the current clientConfig
	 * @return a {@link PoolingHttpClientConnectionManagerBuilder} initialized with
	 * default configuration
	 */
	protected PoolingHttpClientConnectionManagerBuilder createHttpClientConnectionManagerBuilder(
			ClientConfiguration clientConfig) {
		return PoolingHttpClientConnectionManagerBuilder.create()
			.setDefaultConnectionConfig(ConnectionConfig.custom()
				.setConnectTimeout(Timeout.ofSeconds(clientConfig.getConnectionTimeout()))
				.build());
	}

	private void setProxy(HttpClientBuilder clientBuilder, ClientConfiguration clientConfig) {
		clientBuilder.useSystemProperties();
		clientBuilder.setProxy(new HttpHost(clientConfig.getProxyHost(), clientConfig.getProxyPort()));
		if (clientConfig.getProxyUsername() != null) {
			BasicCredentialsProvider credentialsProvider = new BasicCredentialsProvider();
			AuthScope authScope = new AuthScope(clientConfig.getProxyHost(), clientConfig.getProxyPort());
			UsernamePasswordCredentials usernamePasswordCredentials = new UsernamePasswordCredentials(
					clientConfig.getProxyUsername(), clientConfig.getProxyPassword().toCharArray());
			credentialsProvider.setCredentials(authScope, usernamePasswordCredentials);
			clientBuilder.setDefaultCredentialsProvider(credentialsProvider);
			clientBuilder.setProxyAuthenticationStrategy(new DefaultAuthenticationStrategy());
		}
	}

}
