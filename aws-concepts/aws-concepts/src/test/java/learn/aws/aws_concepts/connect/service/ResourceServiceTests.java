package learn.aws.aws_concepts.connect.service;

import com.amazonaws.auth.AWSCredentials;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ResourceServiceTests {

	@Autowired
	private ResourceService resourceServ;
	
	@Test
	public void test() {
		AWSCredentials awsCred = resourceServ.getCredentials("asso-cert-adm");
		System.out.println(awsCred);
	}

}
