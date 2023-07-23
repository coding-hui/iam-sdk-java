package top.wecoding.iam.sdk.client.param;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author Wecoding Liu
 * @since 0.8
 */
public class Cookie {

	public static final Cookie EMPTY = Cookie.newInstance();

	private final Map<String, String> cookies;

	private Cookie() {
		cookies = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
	}

	public static Cookie newInstance() {
		return new Cookie();
	}

	/**
	 * Add the key and value to the cookie.
	 * @param key the key
	 * @param value the value
	 * @return cookie
	 */
	public Cookie addParam(String key, String value) {
		if (key != null && !key.isEmpty()) {
			cookies.put(key, value);
		}
		return this;
	}

	public Cookie build() {
		return this;
	}

	public String getValue(String key) {
		return cookies.get(key);
	}

	public Map<String, String> getCookies() {
		return cookies;
	}

	public Iterator<Map.Entry<String, String>> iterator() {
		return cookies.entrySet().iterator();
	}

	/**
	 * Add all parameters to header.
	 * @param params parameters
	 */
	public void addAll(Map<String, String> params) {
		if (params != null && !params.isEmpty()) {
			for (Map.Entry<String, String> entry : params.entrySet()) {
				addParam(entry.getKey(), entry.getValue());
			}
		}
	}

	public void clear() {
		cookies.clear();
	}

	@Override
	public String toString() {
		return "Cookie{" + "cookieToMap=" + cookies + '}';
	}

}
