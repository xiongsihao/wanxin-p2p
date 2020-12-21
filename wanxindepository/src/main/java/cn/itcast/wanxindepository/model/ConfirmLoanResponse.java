package cn.itcast.wanxindepository.model;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * <P>
 * 放款确认返回信息
 * </p>
 *
 * @author zhupeiyuan@itcast.cn
 * @since 2019-05-28
 */
@Data
@ApiModel(value = "ConfirmLoanResponse", description = "放款确认返回信息")
public class ConfirmLoanResponse extends BaseResponse {

}
