package com.bjmashibing.shiro.sso;

import com.bjmashibing.shiro.framework.utils.R;
import com.bjmashibing.shiro.moduler.system.entity.User;
import com.bjmashibing.shiro.moduler.system.service.UserService;
import com.bjmashibing.shiro.shiro.ShiroUtils;
import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

@Slf4j
@RestController
public class LoginController {
	@Autowired
	private UserService userService;

	@RequestMapping(value = {"/"})
	public void index(HttpServletResponse response) throws IOException {
		response.sendRedirect("/index");
	}
	// 后台主页
	@RequestMapping("/index")
	public Object index() {
		Object principal = SecurityUtils.getSubject().getPrincipal();
		System.out.println(principal);
		return R.ok();
	}
}
