package com.bjmashibing.shiro.password;

import com.bjmashibing.shiro.shiro.ShiroUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.junit.jupiter.api.Test;

/**
 * <p>恢复密码</p>
 *
 * @author:孙志强
 **/
public class RsetPassword {

	@Test
	public void resetPassword() {
		String expectPassword = "111111";
		String salt = "system";
		SimpleHash passwordMd5 = new SimpleHash(ShiroUtils.hashAlgorithmName, "111111", salt, ShiroUtils.hashIterations);
		System.out.println(passwordMd5.toString());

	}

}
