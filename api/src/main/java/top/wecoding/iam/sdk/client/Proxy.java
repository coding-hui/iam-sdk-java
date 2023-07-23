package top.wecoding.iam.sdk.client;

import lombok.Data;

/**
 * @author Wecoding Liu
 * @since 0.8
 */
@Data
public class Proxy {

	private String host;

	private int port;

	private String username;

	private String password;

	private boolean authenticationRequired;

	public Proxy(String host, int port, String username, String password) {
		this(host, port, username, password, true);
	}

	public Proxy(String host, int port, String username, String password, boolean authenticationRequired) {
		this.host = host;
		this.port = port;
		this.username = username;
		this.password = password;
		this.authenticationRequired = authenticationRequired;
	}

}
