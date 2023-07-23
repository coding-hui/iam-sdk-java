package top.wecoding.iam.sdk.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import top.wecoding.iam.sdk.model.UserInfo;

/**
 * @author Wecoding Liu
 * @since 0.8
 */
@Data
public class UpdateUserResponse {

	@JsonProperty("code")
	private int code;

	@JsonProperty("msg")
	private String msg;

	@JsonProperty("data")
	private UserInfo userInfo;

}
