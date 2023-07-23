package top.wecoding.iam.sdk.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Map;

/**
 * @author Wecoding Liu
 * @since 0.8
 */
@Data
public class Metadata {

	@JsonProperty("instanceId")
	private String instanceId;

	@JsonProperty("name")
	private String name;

	@JsonProperty("createdAt")
	private String createdAt;

	@JsonProperty("updatedAt")
	private String updatedAt;

	@JsonProperty("extend")
	private Map<String, Object> extend;

}
