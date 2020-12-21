package cn.itcast.wanxindepository.model;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "DepositoryResponse", description = "存管响应信息")
public class DepositoryResponse<T> {

    @ApiModelProperty("对respData参数的签名")
    private String signature;

    @ApiModelProperty("业务数据报文")
    private T respData;

	public DepositoryResponse(T respData) {
		this.respData = respData;
	}
}
