package top.wecoding.iam.sdk;

import top.wecoding.iam.sdk.api.AuthenticationApi;
import top.wecoding.iam.sdk.api.UserApi;
import top.wecoding.iam.sdk.client.ApiClient;
import top.wecoding.iam.sdk.client.Clients;
import top.wecoding.iam.sdk.client.auth.HttpBearerAuth;
import top.wecoding.iam.sdk.model.Metadata;
import top.wecoding.iam.sdk.model.UserInfo;
import top.wecoding.iam.sdk.model.request.CreateUserRequest;
import top.wecoding.iam.sdk.model.response.AuthenticationResponse;
import top.wecoding.iam.sdk.model.response.CreateUserResponse;
import top.wecoding.iam.sdk.model.response.UserInfoResponse;

import java.util.UUID;

/**
 * @author Wecoding Liu
 * @since 0.8
 */
public class Quickstart {

	public static void main(String[] args) {

		final String name = "quickstart-" + UUID.randomUUID().toString().replaceAll("-", "").substring(0, 20);
		final String email = "quickstart@example.com";
		final String password = "WeCoding@.123";

		ApiClient client;

		try {
			client = Clients.builder().build();
			client.setBasePath("http://localhost:8001");

			AuthenticationApi authenticationApi = new AuthenticationApi(client);

			AuthenticationResponse authenticateResponse = authenticationApi.authenticate("ADMIN", "WECODING");
			String accessToken = authenticateResponse.getTokenInfo().getAccessToken();

			HttpBearerAuth httpBearerAuth = new HttpBearerAuth();
			httpBearerAuth.setBearerToken(accessToken);
			client.replaceAuthentication(HttpBearerAuth.NAME, httpBearerAuth);

			UserApi userApi = new UserApi(client);

			UserInfoResponse currentUserInfo = authenticationApi.currentUserInfo();
			if (currentUserInfo != null) {
				UserInfo userInfo = currentUserInfo.getUserInfo();
				println("get currentUserInfo success. userInfo = " + userInfo);
			}

			CreateUserRequest createUserReq = CreateUserRequest.builder()
				.name(name)
				.email(email)
				.alias("quickstart")
				.password(password)
				.build();
			CreateUserResponse createUserResponse = userApi.createUser(createUserReq);

			UserInfo userInfo = createUserResponse.getUserInfo();
			Metadata metadata = userInfo.getMetadata();
			String instanceId = metadata.getInstanceId();

			UserInfoResponse userInfoResponse = userApi.getUserInfo(instanceId);
			println("get user info success. userinfo = " + userInfoResponse.getUserInfo());

			userApi.disableUser(userInfo.getMetadata().getInstanceId());

			userApi.enableUser(userInfo.getMetadata().getInstanceId());

			userApi.deleteUser(instanceId);
			println("delete user success.");
		}
		catch (Exception e) {
			println(e.getMessage());
			throw e;
		}
	}

	private static void println(String message) {
		System.out.println(message);
		System.out.flush();
	}

}
