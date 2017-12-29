package boot.demo;




import boot.demo.config.elasticsearch.SearchCommonMethod;
import boot.demo.config.elasticsearch.SearchEnum;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Application.class)
@SpringBootTest//指定当前类是一个测试类
public class ApplicationTests {

	private static final Log log = LogFactory.getLog(ApplicationTests.class);
//
//	@Autowired
//	private UserService userService;

	@Test
	public void test1() throws Exception {
//		User user = userService.findByName("测试");
//		System.out.println(user);
		String algorithmName = "MD5";
		String username = "zj";
		String password = "123456";
		String salt1 = username;
		String salt2 = "xiaojiang";
		int hashIterations = 2;
//		SimpleHash hash = new SimpleHash(algorithmName, password,  ByteSource.Util.bytes(salt1 + salt2), hashIterations);
//		String encodedPassword = hash.toHex();
//		System.out.println(encodedPassword);
		System.out.println( ByteSource.Util.bytes(salt1 + salt2));

	}
	@Test
	public void test2(){
		SearchHits hits = SearchCommonMethod.setQuery("name","王丹辉测试2",SearchEnum.VENDOR).getHits();
		List list = new ArrayList();
		for (SearchHit searchHit : hits) {
			Map source = searchHit.getSource();
			System.out.println(source);
		}
	}


}
