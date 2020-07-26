package learn.aws.aws_concepts.connect.service;



import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import org.springframework.stereotype.Service;

@Service
public class ResourceService {
	
	public AWSCredentials getCredentials(String profile){
		return new ProfileCredentialsProvider(profile).getCredentials();
	}
}
