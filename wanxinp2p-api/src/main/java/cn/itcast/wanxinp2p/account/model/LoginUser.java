package cn.itcast.wanxinp2p.account.model;

import lombok.Data;

/**
 * 当前登录用户
 */
@Data
public class LoginUser {
	/**
	 * tenantId : 1
	 * departmentId : 1
	 * payload : {"res":"res1111111"}
	 * username : admin
	 * mobile : 18611106983
	 * userAuthorities : {"ROLE1":["p1","p2"]}
	 * clientId : wanxin-p2p-web-admin
	 */

	private String tenantId;
	private String departmentId;
	private String payload;
	private String username;
	private String mobile;
	private String userAuthorities;
	private String clientId;

}
