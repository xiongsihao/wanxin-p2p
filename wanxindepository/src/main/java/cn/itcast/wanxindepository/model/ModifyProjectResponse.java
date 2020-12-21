package cn.itcast.wanxindepository.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <P>
 * 更新标的状态返回信息
 * </p>
 *
 * @author zhupeiyuan@itcast.cn
 * @since 2019-05-24
 */
@Data
@ApiModel(value = "ModifyProjectResponse", description = "更新标的状态返回信息")
public class ModifyProjectResponse extends BaseResponse {

	@ApiModelProperty("标的状态")
	private String projectStatus;

	public ModifyProjectResponse(String requestNo) {
		super(requestNo);
	}
}
