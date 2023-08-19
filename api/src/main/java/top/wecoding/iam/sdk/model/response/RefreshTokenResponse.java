package top.wecoding.iam.sdk.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import top.wecoding.iam.sdk.model.TokenInfo;

/**
 * @author wecoding
 * @since 0.8
 */
@Data
public class RefreshTokenResponse {

	@JsonProperty("code")
	private int code;

	@JsonProperty("msg")
	private String msg;

	@JsonProperty("data")
	private TokenInfo tokenInfo;

}
