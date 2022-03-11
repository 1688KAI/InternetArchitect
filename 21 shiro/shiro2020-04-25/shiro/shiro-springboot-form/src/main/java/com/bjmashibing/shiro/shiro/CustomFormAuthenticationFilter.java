package com.bjmashibing.shiro.shiro;

import com.google.code.kaptcha.Constants;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * <b>功能：</b>加入验证码验证，成功页面更改为配置的成功页面 <br>
 * <b>作者：</b>孙志强<br>
 * <b>日期：</b> 2016年12月29日 下午9:46:27 <br>
 *
 * @version 1.0
 **/
public class CustomFormAuthenticationFilter extends FormAuthenticationFilter {
	protected Logger log = LoggerFactory.getLogger(CustomFormAuthenticationFilter.class);

	@Override
	public boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
		String verifyCode = request.getParameter("");
		if (StringUtils.isNotBlank(verifyCode)) {
			if (!ShiroUtils.getSessionAttribute(Constants.KAPTCHA_SESSION_KEY).equals(verifyCode)) {
				throw new CaptchaRequiredException("验证码不正确");
			}
		}else{
			throw new CaptchaRequiredException("验证码为空");
		}

		boolean isAllowed = isAccessAllowed(request, response, mappedValue);
		//登录跳转
		if (isAllowed && isLoginRequest(request, response)) {
			try {
				issueSuccessRedirect(request, response);
			} catch (Exception e) {
				log.error(e.getClass().getName()+":"+e.getMessage());
			}
			return false;
		}
		return isAllowed || onAccessDenied(request, response, mappedValue);
	}

	/**
	 * 这个方法解决记住我的问题
	 */
	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
		Subject subject = getSubject(request, response);
		return subject.isAuthenticated();
	}

}
