package learn.aws.lambda;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;

import java.io.*;

public class HelloLambda {
	public static void main(String[] args) throws IOException {
		hello();
	}
	public static String hello() throws IOException {
//		AWSCredentialsProvider acp = new ProfileCredentialsProvider("pubsap");
//		System.out.println(acp.getCredentials());
		//getS3ObjectMetadata();
		//runLocalProcess();
		copyS3ObjectMetadata();
		return "HelloLambda Ji!";
	}

	private static void runLocalProcess() throws IOException {
		String[] commands = new String[]{"aws s3 cp s3://amandeep.singh22publicissapient.com/cfn/EC2LinuxInstanceWithSecurityGroup.json ."};
		Process p = null;
		ProcessBuilder pb = new ProcessBuilder(commands);
		p = pb.start();
		BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
		String line;
		StringBuilder response = new StringBuilder();
		while((line=br.readLine())!=null){
			response.append(line);
		}
		System.out.printf("native command response - %s", response);
	}

	private static void getS3ObjectMetadata() {
		AmazonS3 s3Client = AmazonS3ClientBuilder.standard().withCredentials(new ProfileCredentialsProvider("pubsap")).build();
		String bucketName = "amandeep.singh22publicissapient.com";
		String key = "cfn/EC2LinuxInstanceWithSecurityGroup.json";
		S3Object fullObject = s3Client.getObject(new GetObjectRequest(bucketName, key));
		System.out.println("Content-Type: " + fullObject.getObjectMetadata().getContentType());
		System.out.println("Content: ");
	}

	private static void copyS3ObjectMetadata() throws IOException {
		AmazonS3 s3Client = AmazonS3ClientBuilder.standard().withCredentials(new ProfileCredentialsProvider("pubsap")).build();
		String bucketName = "amandeep.singh22publicissapient.com";
		String key = "cfn/EC2LinuxInstanceWithSecurityGroup.json";
		S3Object s3object = s3Client.getObject(new GetObjectRequest(bucketName, key));

		S3ObjectInputStream inputStream = s3object.getObjectContent();
		byte[] buffer = new byte[1024];
		int read;
		File targetFile = new File("./hello.json");
		System.out.printf("Copying to file - %s",  targetFile.getAbsoluteFile());
		OutputStream outStream = new FileOutputStream(targetFile);
		while((read = inputStream.read(buffer))!=-1) {
			outStream.write(buffer, 0, read);
		}
		//outStream.close();
		//inputStream.close();
	}




}
