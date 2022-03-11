package com.bjmashibing.shiro.shiro;

import com.google.code.kaptcha.Constants;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <b>功能：</b>加入验证码验证， <br>
 * <b>作者：</b>孙志强<br>
 * <b>日期：</b> 2016年12月29日 下午9:46:27 <br>
 *
 * @version 1.0
 **/
public class CustomFormAuthenticationFilter extends FormAuthenticationFilter {
	protected Logger log = LoggerFactory.getLogger(CustomFormAuthenticationFilter.class);


	@Override
	protected boolean executeLogin(ServletRequest request,ServletResponse response) throws Exception {
		AuthenticationToken token = createToken(request, response);
		if (token == null) {
			String msg = "create AuthenticationToken error";
			throw new IllegalStateException(msg);
		}
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		String username = (String) token.getPrincipal();
		//验证码校验
		if (isCaptchaRequired(username,req, res)) {

			HttpServletRequest httpServletRequest = (HttpServletRequest) request;
			String verifyCode = httpServletRequest.getParameter("verifyCode");
			verifyCode = StringUtils.isNotBlank(verifyCode) ? verifyCode : "";

			if (!verifyCode.equalsIgnoreCase(ShiroUtils.getKaptcha())) {
				return onLoginFailure(token, new CaptchaErrorException(), request, response);
			}
		}
		return super.executeLogin(request, response);
	}

	private boolean isCaptchaRequired(String username,HttpServletRequest request,
									  HttpServletResponse response) {
		log.info("判断需不需要输入验证码==========================================");
		// 如果输入了验证码，那么必须验证；如果没有输入验证码，则根据当前用户判断是否需要验证码。
		//1- 可以加个开关，控制是否需要验证
		//2-可以连接数据库，判断用户登陆失败次数

		return true;
	}

}
