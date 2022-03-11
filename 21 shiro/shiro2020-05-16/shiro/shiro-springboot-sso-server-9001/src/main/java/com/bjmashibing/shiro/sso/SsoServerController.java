package com.bjmashibing.shiro.sso;

import com.bjmashibing.shiro.framework.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
public class SsoServerController {

	// 第一个走到的方法--用于用户session验证
	@RequestMapping("/checkLogin")
	public Object checkLogin(String redirectUrl, HttpSession session, HttpServletResponse response) throws IOException {
		// 第一次从淘宝跳转过来，ticket肯定是null

		//第二次天猫来了，ticket能不能拿到？？？，如果不是cookiea，是参数传过来的呢？
		//也如果sso-server它本身能是一台机器么？？？？
		//点击退出的时候，ticket没了
		String ticket = (String) session.getAttribute("ticket");

		//1- 判断这个用户是否登录，是否拥有全局的会话
		if (StringUtils.isBlank(ticket)) {
			//没有全局就会话，去登陆页面,我从哪里来不能丢
			ModelAndView mv = new ModelAndView("/login");
			// sso-server验证不通过，跳转到验证中心（自己的）登陆页面
			mv.addObject("redirectUrl", redirectUrl);
			return mv;
		} else {
			//存在全局会话，哪里那里去？？？简单么？如何做???
			response.sendRedirect(redirectUrl + "?ticket=" + ticket);
			return null;
		}
	}

	// 用户session页面
	@RequestMapping("/login")
	public Object login(String username, String password, String redirectUrl, HttpSession session, HttpServletResponse response) throws IOException {
		ModelAndView mv = new ModelAndView("/login");
		mv.addObject("redirectUrl", redirectUrl);
		if ("system".equals(username) && "111111".equals(password)) {
			//给用户创建一个token，唯一，落库
			//如果登陆成功，服务器端需要保存
			String ticket = UUID.randomUUID().toString();

			//应该保存--redis，数据库，
			MockDb.TICKET.add(ticket);
			//2,服务器上存在会话信息
			session.setAttribute("ticket", ticket);
			//3 返回给客户
			response.sendRedirect(redirectUrl + "?ticket=" + ticket);
			return null;
		}
		System.out.println("用户名密码不正确");
		return mv;
	}

	/**
	 * 验证token是否正确
	 */
	@RequestMapping("/verify")
	public Object verify(String ticket, String logoutUrl, String jsessionId) {
		if (MockDb.TICKET.contains(ticket)) {
			System.out.println("服务器端校验成功");
			//保存用户的登出和session信息
			List<ClientInfo> clientInfos = MockDb.CLIENT_TOKEN_SESSIONID.get(ticket);
			//第一次一定是空的
			if (null == clientInfos) {
				clientInfos = new ArrayList<>();
				//将用户信息放入
				MockDb.CLIENT_TOKEN_SESSIONID.put(ticket, clientInfos);
			}
			ClientInfo clientInfo = new ClientInfo();
			clientInfo.setLogoutUrl(logoutUrl);
			clientInfo.setJsessionId(jsessionId);
			clientInfos.add(clientInfo);
			return R.ok();
		} else {
			return R.error();
		}
	}

	/**
	 * <p>注销请求，只有点击注销按钮才会注销session</p>
	 * @param session
	 * @throws IOException
	 */
	@RequestMapping("/logout")
	public ModelAndView logout(HttpSession session,String redirectUrl) throws IOException {
		session.invalidate();
		ModelAndView mv = new ModelAndView("/login");
		mv.addObject("redirectUrl", redirectUrl);
		return mv;
	}


}
