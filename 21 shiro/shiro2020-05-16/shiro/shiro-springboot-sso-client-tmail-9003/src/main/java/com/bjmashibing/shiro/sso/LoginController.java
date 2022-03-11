package com.bjmashibing.shiro.sso;

import com.bjmashibing.shiro.framework.utils.SsoClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Slf4j
@RestController
public class LoginController {

	@RequestMapping(value = {"/"})
	public void index(HttpServletResponse response) throws IOException {
		response.sendRedirect("/index");
	}
	// 后台主页
	@RequestMapping("/index")
	public ModelAndView index(HttpServletRequest request) {
		log.info("访问主页1");
		ModelAndView mv = new ModelAndView("/index");
		mv.addObject("logoutUrl", SsoClientUtil.getServerLogOutUrl()+"?redirectUrl="+SsoClientUtil.getRedirectUrl(request));

		return mv;
	}
	@RequestMapping("/logout")
	public void logout(HttpSession session) {
		session.invalidate();
	}

}
