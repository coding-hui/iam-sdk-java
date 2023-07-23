package top.wecoding.iam.sdk.config;

import lombok.Data;
import top.wecoding.iam.sdk.client.Proxy;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Wecoding Liu
 * @since 0.8
 */
@Data
public class ClientConfiguration {

	private String apiToken;

	private boolean cacheManagerEnabled;

	private long cacheManagerTtl;

	private long cacheManagerTti;

	private String clientId;

	private Set<String> scopes = new HashSet<>();

	private String privateKey;

	private String oAuth2AccessToken;

	private String kid;

	private long connectionTimeout;

	private int retryMaxElapsed;

	private int retryMaxAttempts;

	private String baseUrl;

	private int proxyPort;

	private String proxyHost;

	private String proxyUsername;

	private String proxyPassword;

	private Proxy proxy;

}