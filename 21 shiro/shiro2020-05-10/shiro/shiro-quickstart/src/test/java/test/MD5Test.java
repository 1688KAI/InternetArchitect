package test;

import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.junit.jupiter.api.Test;

/**
 * <p></p>
 *
 * @author sunzhiqiang23
 * @date 2020-04-25 11:44
 */
public class MD5Test {
    @Test
    public void testMD5(){
        String password = "admin";
        String salt = "salt";
        String result = new Md5Hash(password, salt, 1).toString();
        //c657540d5b315892f950ff30e1394480
        System.out.println(result);
    }
    @Test
    public void testSimpleHash() {
        String password = "admin";
        String salt = "salt";
        SimpleHash simpleHash = new SimpleHash("MD5", password, salt, 1);
        //c657540d5b315892f950ff30e1394480
        System.out.println(simpleHash.toString());
    }

}
