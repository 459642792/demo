package boot.demo;




import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;


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
	}

}
