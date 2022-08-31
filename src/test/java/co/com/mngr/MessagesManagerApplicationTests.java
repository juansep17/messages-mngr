package co.com.mngr;

import com.messages.mngr.MessagesManagerApplication;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MessagesManagerApplicationTests {

	@Test
	void contextLoads() {
		String test = "Main Test";
		MessagesManagerApplication.main(new String[]{"args"});
		Assertions.assertNotNull(test);
	}

}