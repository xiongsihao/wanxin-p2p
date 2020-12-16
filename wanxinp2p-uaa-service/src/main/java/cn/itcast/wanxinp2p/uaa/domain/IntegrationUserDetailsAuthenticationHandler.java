package cn.itcast.wanxinp2p.uaa.domain;

import cn.itcast.wanxinp2p.account.model.AccountDTO;
import cn.itcast.wanxinp2p.account.model.AccountLoginDTO;
import cn.itcast.wanxinp2p.common.domain.RestResponse;
import cn.itcast.wanxinp2p.common.util.StringUtil;
import cn.itcast.wanxinp2p.uaa.agent.AccountApiAgent;
import cn.itcast.wanxinp2p.uaa.common.utils.ApplicationContextHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class IntegrationUserDetailsAuthenticationHandler {

	/**
	 * 认证处理
	 * @param domain 用户域 ，如b端用户、c端用户等
	 * @param authenticationType  认证类型，如密码认证，短信认证等
	 * @param token
	 * @return
	 */
	public UnifiedUserDetails authentication(String domain, String authenticationType,
			UsernamePasswordAuthenticationToken token) {
		
		//1. 从客户端取数据
		String username = token.getName();
		if(StringUtil.isBlank(username)){
			throw new BadCredentialsException("账户为空");
		}
		if(token.getCredentials()==null){
			throw new BadCredentialsException("密码为空");
		}
		String presentedPassword = token.getCredentials().toString();
		//2.远程调用统一账户服务进行账户密码校验
		AccountLoginDTO accountLoginDTO=new AccountLoginDTO();
		accountLoginDTO.setDomain(domain);
		accountLoginDTO.setUsername(username);
		accountLoginDTO.setMobile(username);
		accountLoginDTO.setPassword(presentedPassword);

		//获取bean对象工具类ApplicationContextHelper
		AccountApiAgent accountApiAgent=(AccountApiAgent)ApplicationContextHelper.getBean(AccountApiAgent.class);
		RestResponse<AccountDTO> restResponse =accountApiAgent.login(accountLoginDTO);

		//3.返回结果 异常处理
		if(restResponse.getCode()!=0){
			throw new BadCredentialsException("登录失败");
		}
		//4.登录成功，把用户数据封装到UnifiedUserDetails对象中
		UnifiedUserDetails unifiedUserDetails=new UnifiedUserDetails(restResponse.getResult().getUsername(),presentedPassword,AuthorityUtils.createAuthorityList());
		unifiedUserDetails.setMobile(restResponse.getResult().getMobile());
		return unifiedUserDetails;
		
	}

	private UnifiedUserDetails getUserDetails(String username) {
		Map<String, UnifiedUserDetails> userDetailsMap = new HashMap<>();
		userDetailsMap.put("admin",
				new UnifiedUserDetails("admin", "111111", AuthorityUtils.createAuthorityList("ROLE_PAGE_A", "PAGE_B")));
		userDetailsMap.put("xufan",
				new UnifiedUserDetails("xufan", "111111", AuthorityUtils.createAuthorityList("ROLE_PAGE_A", "PAGE_B")));

		userDetailsMap.get("admin").setDepartmentId("1");
		userDetailsMap.get("admin").setMobile("18611106983");
		userDetailsMap.get("admin").setTenantId("1");
		Map<String, List<String>> au1 = new HashMap<>();
		au1.put("ROLE1", new ArrayList<>());
		au1.get("ROLE1").add("p1");
		au1.get("ROLE1").add("p2");
		userDetailsMap.get("admin").setUserAuthorities(au1);
		Map<String, Object> payload1 = new HashMap<>();
		payload1.put("res", "res1111111");
		userDetailsMap.get("admin").setPayload(payload1);


		userDetailsMap.get("xufan").setDepartmentId("2");
		userDetailsMap.get("xufan").setMobile("18611106984");
		userDetailsMap.get("xufan").setTenantId("1");
		Map<String, List<String>> au2 = new HashMap<>();
		au2.put("ROLE2", new ArrayList<>());
		au2.get("ROLE2").add("p3");
		au2.get("ROLE2").add("p4");
		userDetailsMap.get("xufan").setUserAuthorities(au2);

		Map<String, Object> payload2 = new HashMap<>();
		payload2.put("res", "res222222");
		userDetailsMap.get("xufan").setPayload(payload2);

		return userDetailsMap.get(username);

	}

}
