package top.wecoding.iam.sdk.client.param;

import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.HttpHeaders;
import top.wecoding.iam.sdk.exception.ApiException;
import top.wecoding.iam.sdk.util.Strings;

import java.nio.charset.UnsupportedCharsetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author Wecoding Liu
 * @since 0.8
 */
public class Header {

	public static final Header EMPTY = Header.newInstance();

	private static final String DEFAULT_CHARSET = "UTF-8";

	private static final String DEFAULT_ENCODING = "gzip";

	private final Map<String, String> header;

	private final Map<String, List<String>> originalResponseHeader;

	private Header() {
		header = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
		originalResponseHeader = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
		addParam(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON);
		addParam(HttpHeaders.ACCEPT_CHARSET, DEFAULT_CHARSET);
	}

	public static Header newInstance() {
		return new Header();
	}

	/**
	 * Add the key and value to the header.
	 * @param key the key
	 * @param value the value
	 * @return header
	 */
	public Header addParam(String key, String value) {
		if (key != null && !key.isEmpty()) {
			header.put(key, value);
		}
		return this;
	}

	public Header build() {
		return this;
	}

	public String getValue(String key) {
		return header.get(key);
	}

	public Map<String, String> getHeader() {
		return header;
	}

	public Iterator<Map.Entry<String, String>> iterator() {
		return header.entrySet().iterator();
	}

	/**
	 * Transfer to KV part list. The odd index is key and the even index is value.
	 * @return KV string list
	 */
	public List<String> toList() {
		List<String> list = new ArrayList<>(header.size() * 2);
		Iterator<Map.Entry<String, String>> iterator = iterator();
		while (iterator.hasNext()) {
			Map.Entry<String, String> entry = iterator.next();
			list.add(entry.getKey());
			list.add(entry.getValue());
		}
		return list;
	}

	/**
	 * Add all KV list to header. The odd index is key and the even index is value.
	 * @param list KV list
	 * @return header
	 */
	public Header addAll(List<String> list) {
		if ((list.size() & 1) != 0) {
			throw new IllegalArgumentException("list size must be a multiple of 2");
		}
		for (int i = 0; i < list.size();) {
			String key = list.get(i++);
			if (Strings.isNotBlank(key)) {
				header.put(key, list.get(i++));
			}
		}
		return this;
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

	/**
	 * set original format response header.
	 *
	 * <p>
	 * Currently only corresponds to the response header of JDK.
	 * @param key original response header key
	 * @param values original response header values
	 */
	public void addOriginalResponseHeader(String key, List<String> values) {
		if (Strings.isNotBlank(key)) {
			this.originalResponseHeader.put(key, values);
			addParam(key, values.get(0));
		}
	}

	/**
	 * get original format response header.
	 *
	 * <p>
	 * Currently only corresponds to the response header of JDK.
	 * @return Map original response header
	 */
	public Map<String, List<String>> getOriginalResponseHeader() {
		return this.originalResponseHeader;
	}

	public String getCharset() {
		String acceptCharset = getValue(HttpHeaders.ACCEPT_CHARSET);
		if (acceptCharset == null) {
			String contentType = getValue(HttpHeaders.CONTENT_TYPE);
			acceptCharset = Strings.isNotBlank(contentType) ? analysisCharset(contentType) : "UTF-8";
		}
		return acceptCharset;
	}

	public ContentType getContentType() {
		String headerValue = getValue(HttpHeaders.CONTENT_TYPE);
		try {
			return ContentType.parse(headerValue);
		}
		catch (UnsupportedCharsetException e) {
			throw new ApiException("Failed to parse content type " + headerValue);
		}
	}

	public Header setContentType(String contentType) {
		if (contentType == null) {
			contentType = MediaType.APPLICATION_JSON;
		}
		return addParam(HttpHeaders.CONTENT_TYPE, contentType);
	}

	private String analysisCharset(String contentType) {
		String[] values = contentType.split(";");
		String charset = "UTF-8";
		for (String value : values) {
			if (value.startsWith("charset=")) {
				charset = value.substring("charset=".length());
			}
		}
		return charset;
	}

	public void clear() {
		header.clear();
		originalResponseHeader.clear();
	}

	@Override
	public String toString() {
		return "Header{" + "headerToMap=" + header + '}';
	}

}
