package top.wecoding.iam.sdk.api;

import top.wecoding.iam.sdk.client.ApiClient;
import top.wecoding.iam.sdk.client.param.Header;
import top.wecoding.iam.sdk.client.param.Query;
import top.wecoding.iam.sdk.exception.ApiException;
import top.wecoding.iam.sdk.model.ApiResult;
import top.wecoding.iam.sdk.model.request.CreateUserRequest;
import top.wecoding.iam.sdk.model.request.RequestHttpEntity;
import top.wecoding.iam.sdk.model.request.UpdateUserRequest;
import top.wecoding.iam.sdk.model.response.CreateUserResponse;
import top.wecoding.iam.sdk.model.response.UpdateUserResponse;
import top.wecoding.iam.sdk.model.response.UserInfoResponse;
import top.wecoding.iam.sdk.util.Strings;

/**
 * User management related API.
 *
 * @author Wecoding Liu
 * @since 0.8
 */
public class UserApi {

	private static final String API_PREFIX = "/api/v1/users";

	private final ApiClient apiClient;

	public UserApi(ApiClient apiClient) {
		this.apiClient = apiClient;
	}

	public CreateUserResponse createUser(CreateUserRequest createReq) {
		final String[] authNames = new String[] { "basic", "bearer" };

		RequestHttpEntity requestEntity = new RequestHttpEntity(Header.EMPTY, Query.EMPTY, createReq, authNames);
		try {
			return this.apiClient.post(API_PREFIX, requestEntity, CreateUserResponse.class);
		}
		catch (Exception ex) {
			throw new ApiException(ex);
		}
	}

	public UpdateUserResponse updateUser(String user, UpdateUserRequest updateReq) {
		final String[] authNames = new String[] { "basic", "bearer" };

		RequestHttpEntity requestEntity = new RequestHttpEntity(Header.EMPTY, Query.EMPTY, updateReq, authNames);

		String api = Strings.format("{}/{}", API_PREFIX, user);

		try {
			return this.apiClient.put(api, requestEntity, UpdateUserResponse.class);
		}
		catch (Exception ex) {
			throw new ApiException(ex);
		}
	}

	public UserInfoResponse getUserInfo(String userId) {
		final String[] authNames = new String[] { "basic", "bearer" };

		RequestHttpEntity requestEntity = new RequestHttpEntity(Header.EMPTY, Query.EMPTY, authNames);

		String api = Strings.format("{}/{}", API_PREFIX, userId);

		try {
			return this.apiClient.get(api, requestEntity, UserInfoResponse.class);
		}
		catch (Exception ex) {
			throw new ApiException(ex);
		}
	}

	public void deleteUser(String userId) {
		final String[] authNames = new String[] { "basic", "bearer" };

		RequestHttpEntity requestEntity = new RequestHttpEntity(Header.EMPTY, Query.EMPTY, userId, authNames);

		String api = Strings.format("{}/{}", API_PREFIX, userId);

		try {
			ApiResult<?> apiResult = this.apiClient.delete(api, requestEntity, ApiResult.class);
			if (!ApiResult.isSuccessCode(apiResult)) {
				throw new ApiException(apiResult.getCode(), apiResult.getMsg());
			}
		}
		catch (Exception ex) {
			throw new ApiException(ex);
		}
	}

}
