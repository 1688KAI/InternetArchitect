package com.bjmashibing.shiro.sso;

import com.bjmashibing.shiro.framework.utils.R;
import com.bjmashibing.shiro.shiro.ShiroUtils;
import com.bjmashibing.shiro.moduler.system.entity.User;
import com.bjmashibing.shiro.moduler.system.service.UserService;
import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.DefaultSessionKey;
import org.apache.shiro.session.mgt.SessionKey;
import org.apache.shiro.session.mgt.eis.SessionDAO;
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

	// 用户session页面
	@RequestMapping("/login")
	public Object login(HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("/login");
		return mv;
	}
    @RequestMapping("/login_o")
    public R loginO(String username, String password, String rememberMe, String verifyCode) {
		String sessionVerifyCode = String.valueOf(ShiroUtils.getSessionAttribute(Constants.KAPTCHA_SESSION_KEY));
		if (StringUtils.isNotEmpty(sessionVerifyCode) && StringUtils.isNotEmpty(verifyCode) &&!sessionVerifyCode.equalsIgnoreCase(verifyCode)) {
			return R.error("验证码不正确");
		}
		boolean flag = false;
		if (StringUtils.isNotBlank(rememberMe) && rememberMe.equalsIgnoreCase("on")) {
			flag = true;
		}
    	//1 构造token
		UsernamePasswordToken token = new UsernamePasswordToken(username, password);
		token.setRememberMe(flag);
		String info = "";
		// 2如果输入了验证码，那么必须验证；如果没有输入验证码，则根据当前用户判断是否需要验证码。
		try {
            Subject subject = ShiroUtils.getSubject();
            subject.login(token);
        }catch (UnknownAccountException e) {
			info = "账号或密码不正确";
        }catch (IncorrectCredentialsException e) {
			info = "账号或密码不正确";
        }catch (LockedAccountException e) {
			info = "账号已被锁定,请联系管理员";
        }catch (AuthenticationException e) {
			info = "账户验证失败";
        }catch ( RuntimeException e) {
			info = e.getMessage();
		}
        if(StringUtils.isNotBlank(info)){
			return R.error(info);
		}
		return R.ok().put("data","");
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
		ShiroUtils.setSessionAttribute(Constants.KAPTCHA_SESSION_KEY, text);

		ServletOutputStream out = response.getOutputStream();
		ImageIO.write(image, "jpg", out);
	}

}
