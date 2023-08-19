/*
 * Copyright 2002-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package top.wecoding.iam.sdk.api;

import top.wecoding.iam.sdk.client.ApiClient;
import top.wecoding.iam.sdk.client.param.Header;
import top.wecoding.iam.sdk.client.param.Query;
import top.wecoding.iam.sdk.exception.AuthenticationException;
import top.wecoding.iam.sdk.model.request.AuthenticationRequest;
import top.wecoding.iam.sdk.model.request.RequestHttpEntity;
import top.wecoding.iam.sdk.model.response.AuthenticationResponse;
import top.wecoding.iam.sdk.model.response.RefreshTokenResponse;
import top.wecoding.iam.sdk.model.response.UserInfoResponse;

/**
 * Authentication related API.
 *
 * @author Wecoding Liu liu
 * @since 0.8
 */
public class AuthenticationApi {

	private static final String AUTHENTICATE_API = "/api/v1/login";

	private static final String REFRESH_TOKEN_API = "/api/v1/auth/refresh-token";

	private static final String CURRENT_USER_INFO_API = "/api/v1/auth/user-info";

	private static final String REFRESH_TOKEN_HEADER = "RefreshToken";

	private final ApiClient apiClient;

	public AuthenticationApi(ApiClient apiClient) {
		this.apiClient = apiClient;
	}

	public AuthenticationResponse authenticate(String username, String password) {
		return this.authenticate(new AuthenticationRequest(username, password));
	}

	public AuthenticationResponse authenticate(AuthenticationRequest request) {
		final String[] authNames = new String[] {};

		RequestHttpEntity requestEntity = new RequestHttpEntity(Header.EMPTY, Query.EMPTY, request, authNames);
		try {
			return this.apiClient.post(AUTHENTICATE_API, requestEntity, AuthenticationResponse.class);
		}
		catch (Exception ex) {
			throw new AuthenticationException(ex);
		}
	}

	public RefreshTokenResponse refreshToken(String refreshToken) {
		final String[] authNames = new String[] {};

		Header header = Header.newInstance();

		header.addParam(REFRESH_TOKEN_HEADER, refreshToken);

		RequestHttpEntity requestEntity = new RequestHttpEntity(header, Query.EMPTY, authNames);
		try {
			return this.apiClient.get(REFRESH_TOKEN_API, requestEntity, RefreshTokenResponse.class);
		}
		catch (Exception ex) {
			throw new AuthenticationException(ex);
		}
	}

	public UserInfoResponse currentUserInfo() {
		final String[] authNames = new String[] { "basic", "bearer" };

		RequestHttpEntity requestEntity = new RequestHttpEntity(Header.EMPTY, Query.EMPTY, authNames);
		try {
			return this.apiClient.get(CURRENT_USER_INFO_API, requestEntity, UserInfoResponse.class);
		}
		catch (Exception ex) {
			throw new AuthenticationException(ex);
		}
	}

}
