package top.wecoding.iam.sdk.client;

import top.wecoding.iam.sdk.util.Classes;

/**
 * @author Wecoding Liu
 * @since 0.8
 */
public class Clients {

	public static ClientBuilder builder() {
		return Classes.newInstance("top.wecoding.iam.sdk.client.DefaultClientBuilder");
	}

}
