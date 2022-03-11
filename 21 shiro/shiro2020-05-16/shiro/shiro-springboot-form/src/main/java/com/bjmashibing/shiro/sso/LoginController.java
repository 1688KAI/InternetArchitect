package com.bjmashibing.shiro.sso;

import com.bjmashibing.shiro.shiro.CaptchaErrorException;
import com.bjmashibing.shiro.shiro.CaptchaRequiredException;
import com.bjmashibing.shiro.shiro.ShiroUtils;
import com.bjmashibing.shiro.moduler.system.entity.User;
import com.bjmashibing.shiro.moduler.system.service.UserService;
import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

@Slf4j
@RestController
public class LoginController {
	@Autowired
	private UserService userService;
	@Autowired
	private Producer producer;
	@RequestMapping(value = {"/"})
	public void index(HttpServletResponse response) throws IOException {
		response.sendRedirect("/index");
	}
	// 后台主页
	@RequestMapping("/index")
	public ModelAndView index() {
		ModelAndView mv = new ModelAndView("/index");
		User user = ShiroUtils.getUser();
		mv.addObject("user", user);
		return mv;
	}
	@RequestMapping("/refuse")
	public ModelAndView refuse() {
		ModelAndView mv = new ModelAndView("/refuse");
		return mv;
	}
	@GetMapping(value="/login")
	public ModelAndView getLogin(){
		ModelAndView mv = new ModelAndView("/login");
		return mv;
	}
	@PostMapping("/login")
	public ModelAndView login(HttpServletRequest request) {

		ModelAndView mv = new ModelAndView("/login");
		String exceptionClassName = (String) request.getAttribute("shiroLoginFailure");
		String msg = null;
		if (UnknownAccountException.class.getName().equals(exceptionClassName)) {
			msg = "用户不存在！";
		} else if (IncorrectCredentialsException.class.getName().equals(exceptionClassName)) {
			msg = "用户名或者密码错误！";
		} else if (CaptchaRequiredException.class.getName().equals(exceptionClassName)) {
			msg = "请输入验证码";
		} else if (CaptchaErrorException.class.getName().equals(exceptionClassName)) {
			msg = "验证码不正确";
		} else if (AuthenticationException.class.getName().equals(exceptionClassName)) {
			msg = "请输入密码";
		} else if (exceptionClassName != null) {
			msg = "其他错误" + exceptionClassName;
		}
		mv.addObject("msg", msg);
		return mv;
	}

	/**
	 * 验证码
	 */
	@GetMapping("captcha.jpg")
	public void captcha(HttpServletResponse response)throws IOException {
		response.setHeader("Cache-Control", "no-store, no-cache");
		response.setContentType("image/jpeg");
		//生成文字验证码
		String text = producer.createText();
		//生成图片验证码
		BufferedImage image = producer.createImage(text);
		//保存到shiro session
		ShiroUtils.setKaptcha(text);
		ServletOutputStream out = response.getOutputStream();
		ImageIO.write(image, "jpg", out);
	}

}
