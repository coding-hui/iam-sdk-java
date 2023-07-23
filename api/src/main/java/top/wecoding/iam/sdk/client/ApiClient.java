package top.wecoding.iam.sdk.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.client5.http.classic.methods.HttpDelete;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.classic.methods.HttpPut;
import org.apache.hc.client5.http.cookie.BasicCookieStore;
import org.apache.hc.client5.http.cookie.Cookie;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.cookie.BasicClientCookie;
import org.apache.hc.client5.http.protocol.HttpClientContext;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.io.entity.ByteArrayEntity;
import org.apache.hc.core5.http.io.entity.FileEntity;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.hc.core5.http.io.support.ClassicRequestBuilder;
import top.wecoding.iam.sdk.client.auth.ApiKeyAuth;
import top.wecoding.iam.sdk.client.auth.Authentication;
import top.wecoding.iam.sdk.client.auth.HttpBasicAuth;
import top.wecoding.iam.sdk.client.auth.HttpBearerAuth;
import top.wecoding.iam.sdk.client.handler.ResponseHandler;
import top.wecoding.iam.sdk.client.param.Header;
import top.wecoding.iam.sdk.client.param.MediaType;
import top.wecoding.iam.sdk.client.param.Query;
import top.wecoding.iam.sdk.config.ClientConfiguration;
import top.wecoding.iam.sdk.exception.ApiException;
import top.wecoding.iam.sdk.model.request.RequestHttpEntity;
import top.wecoding.iam.sdk.util.Objects;
import top.wecoding.iam.sdk.util.Strings;

import java.io.File;
import java.lang.reflect.Type;
import java.net.URI;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * IAM API client.
 *
 * @author Wecoding Liu
 * @since 0.8
 */
@Getter
@Setter
@Slf4j
public class ApiClient extends AbstractApiClient {

	private static List<String> bodyMethods = Arrays.asList("POST", "PUT", "DELETE", "PATCH");

	private String basePath;

	private CloseableHttpClient httpClient;

	private ClientConfiguration clientConfig;

	private Map<String, Authentication> authentications;

	private ObjectMapper objectMapper;

	public ApiClient() {
		this(null, HttpClients.createDefault());
	}

	public ApiClient(ClientConfiguration clientConfig, CloseableHttpClient httpClient) {
		this.objectMapper = new ObjectMapper();
		this.basePath = "https://iam.wecoding.top";
		this.clientConfig = clientConfig;
		this.httpClient = httpClient;

		this.authentications = new HashMap<>();
		this.authentications.put(ApiKeyAuth.NAME, new ApiKeyAuth("header", "Authorization"));
		this.authentications.put(HttpBasicAuth.NAME, new HttpBasicAuth());
		this.authentications.put(HttpBearerAuth.NAME, new HttpBearerAuth());
		this.authentications = Collections.unmodifiableMap(this.authentications);
	}

	/**
	 * Get authentications (key: authentication name, value: authentication).
	 * @return Map of authentication
	 */
	public Map<String, Authentication> getAuthentications() {
		return this.authentications;
	}

	/**
	 * Get authentication for the given name.
	 * @param authName The authentication name
	 * @return The authentication, null if not found
	 */
	public Authentication getAuthentication(String authName) {
		return this.authentications.get(authName);
	}

	/**
	 * Replaces authentication for the given name.
	 * @param authName The authentication name
	 * @param authentication The new implementation (must be of the same type of the
	 * existing implementation)
	 */
	public void replaceAuthentication(String authName, Authentication authentication) {
		Authentication previous = this.authentications.get(authName);
		if (previous == null) {
			throw new RuntimeException(authName + " authentication not configured!");
		}
		if (!previous.getClass().isAssignableFrom(authentication.getClass())) {
			throw new RuntimeException(
					authentication.getClass().getSimpleName() + " cannot replace authentication " + authName);
		}
		this.authentications = new HashMap<>(this.authentications);
		this.authentications.put(authName, authentication);
		this.authentications = Collections.unmodifiableMap(this.authentications);
	}

	/**
	 * Helper method to set API key value for the first API key authentication.
	 * @param apiKey the API key
	 * @return API client
	 */
	public ApiClient setApiKey(String apiKey) {
		for (Authentication auth : this.authentications.values()) {
			if (auth instanceof ApiKeyAuth) {
				((ApiKeyAuth) auth).setApiKey(apiKey);
				return this;
			}
		}
		throw new RuntimeException("No API key authentication configured!");
	}

	/**
	 * Helper method to set API key prefix for the first API key authentication.
	 * @param apiKeyPrefix API key prefix
	 * @return API client
	 */
	public ApiClient setApiKeyPrefix(String apiKeyPrefix) {
		for (Authentication auth : this.authentications.values()) {
			if (auth instanceof ApiKeyAuth) {
				((ApiKeyAuth) auth).setApiKeyPrefix(apiKeyPrefix);
				return this;
			}
		}
		throw new RuntimeException("No API key authentication configured!");
	}

	public <T> T get(String path, RequestHttpEntity requestEntity, Type responseType) throws Exception {
		return execute(path, HttpGet.METHOD_NAME, requestEntity, responseType);
	}

	public <T> T post(String path, RequestHttpEntity requestEntity, Type responseType) throws Exception {
		return execute(path, HttpPost.METHOD_NAME, requestEntity, responseType);
	}

	public <T> T put(String path, RequestHttpEntity requestEntity, Type responseType) throws Exception {
		return execute(path, HttpPut.METHOD_NAME, requestEntity, responseType);
	}

	public <T> T delete(String path, RequestHttpEntity requestEntity, Type responseType) throws Exception {
		return execute(path, HttpDelete.METHOD_NAME, requestEntity, responseType);
	}

	@SuppressWarnings("unchecked")
	public <T> T execute(String path, String method, RequestHttpEntity requestEntity, Type responseType)
			throws Exception {
		String url = buildUrl(path, requestEntity.getQuery().buildQueryUrl());
		if (log.isDebugEnabled()) {
			log.debug("HTTP method: {}, url: {}, body: {}", method, url, requestEntity.getBody());
		}

		// update request authentications
		requestEntity.updateParamsForAuth(this.authentications);

		ClassicRequestBuilder builder = ClassicRequestBuilder.create(method);
		builder.setUri(url);

		// build header
		Optional.ofNullable(requestEntity.getHeaders()).ifPresent(header -> {
			if (!Objects.isEmpty(header.getHeader())) {
				header.getHeader().forEach(builder::addHeader);
			}
		});

		// build cookie
		BasicCookieStore store = new BasicCookieStore();
		Optional.ofNullable(requestEntity.getCookie()).ifPresent(cookie -> {
			if (!Objects.isEmpty(cookie.getCookies())) {
				return;
			}
			cookie.getCookies().forEach((k, v) -> store.addCookie(buildCookie(k, v, builder.getUri())));
		});

		HttpClientContext context = HttpClientContext.create();
		context.setCookieStore(store);

		Object body = requestEntity.getBody();
		Query query = requestEntity.getQuery();
		ContentType contentTypeObj = Optional.ofNullable(requestEntity.getHeaders())
			.map(Header::getContentType)
			.orElse(ContentType.APPLICATION_JSON);

		if (body != null) {
			if (isBodyAllowed(method)) {
				// Add entity if we have content and a valid method
				builder.setEntity(serialize(body, query.getParams(), contentTypeObj));
			}
			else {
				throw new ApiException("method " + method + " does not support a request body");
			}
		}
		else {
			// for empty body
			builder.setEntity(new StringEntity("", contentTypeObj));
		}

		ResponseHandler<T> responseHandler = super.selectResponseHandler(responseType);

		try {
			return this.httpClient.execute(builder.build(), context, responseHandler);
		}
		catch (Exception ex) {
			if (ex instanceof ApiException) {
				throw ex;
			}
			throw new ApiException(ex.getMessage());
		}
	}

	/**
	 * Serialize the given Java object into string according the given Content-Type (only
	 * JSON is supported for now).
	 * @param obj Object
	 * @param contentType Content type
	 * @param formParams Form parameters
	 * @return Object
	 * @throws ApiException API exception
	 */
	public HttpEntity serialize(Object obj, Map<String, Object> formParams, ContentType contentType)
			throws ApiException {
		String mimeType = contentType.getMimeType();
		if (MediaType.isJsonMime(mimeType)) {
			try {
				return new StringEntity(this.objectMapper.writeValueAsString(obj), contentType);
			}
			catch (JsonProcessingException ex) {
				throw new ApiException(ex);
			}
		}
		else {
			// Handle files with unknown content type
			if (obj instanceof File) {
				return new FileEntity((File) obj, contentType);
			}
			else if (obj instanceof byte[]) {
				return new ByteArrayEntity((byte[]) obj, contentType);
			}
			throw new ApiException("Serialization for content type '" + contentType + "' not supported");
		}
	}

	protected String buildUrl(String path, String... subPaths) {
		String baseURL = this.basePath;
		StringBuilder sb = new StringBuilder();
		sb.append(baseURL).append(path);
		String pre = null;
		for (String subPath : subPaths) {
			if (Strings.isBlank(subPath)) {
				continue;
			}
			if (pre == null || !pre.endsWith("/")) {
				if (subPath.startsWith("/")) {
					sb.append(subPath);
				}
				else {
					sb.append('/').append(subPath);
				}
			}
			else {
				if (subPath.startsWith("/")) {
					sb.append(subPath.replaceFirst("\\/", ""));
				}
				else {
					sb.append(subPath);
				}
			}
			pre = subPath;
		}
		return sb.toString();
	}

	protected Cookie buildCookie(String key, String value, URI uri) {
		BasicClientCookie cookie = new BasicClientCookie(key, value);
		cookie.setDomain(uri.getHost());
		cookie.setPath("/");
		return cookie;
	}

	protected boolean isBodyAllowed(String method) {
		return bodyMethods.contains(method);
	}

}
