package learn.aws.aws_concepts.s3;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.StringWriter;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.util.IOUtils;

public class TestS3 {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test 
	public void testListBuckets() {
		AWSCredentials credentials = null;
		credentials = new ProfileCredentialsProvider("asso-cert-adm").getCredentials();
		AmazonS3 s3 = new AmazonS3Client(credentials);
		for (Bucket bucket : s3.listBuckets()) {
            System.out.println(" - " + bucket.getName());
        }
	}
	
	@Test
	public void testAccessBucketByURL() {
		AWSCredentials credentials = null;
		credentials = new ProfileCredentialsProvider("asso-cert-adm").getCredentials();
		AmazonS3 s3 = new AmazonS3Client(credentials);
		String location = s3.getBucketLocation("bucket-versioning-on");//not http://bucket-versioning-on.s3.amazonaws.com
		System.out.println(location);
	}
	
	@Test
	public void testCRUD() throws IOException {
		AWSCredentials credentials = null;
		credentials = new ProfileCredentialsProvider("asso-cert-adm").getCredentials();
		AmazonS3 s3 = new AmazonS3Client(credentials);
		PutObjectResult por = s3.putObject("bucket-versioning-on", "hello-string", "Hello!");
		S3Object s3obj = s3.getObject("bucket-versioning-on", "hello-string");
		StringWriter sw = new StringWriter();
		System.out.println(IOUtils.toString(s3obj.getObjectContent()));
		ObjectMetadata objmd = s3obj.getObjectMetadata();
		//System.out.println(objmd.);
		//objmd.
		s3.deleteObject("bucket-versioning-on", "hello-string");
	}
	
	@Test
	public void testCRUDWithVersioning() throws IOException {
		AWSCredentials credentials = null;
		credentials = new ProfileCredentialsProvider("asso-cert-adm").getCredentials();
		AmazonS3 s3 = new AmazonS3Client(credentials);
		PutObjectResult por = s3.putObject("bucket-versioning-on", "hello-string", "Hello!");
		System.out.println(por.getVersionId());
		S3Object s3obj = s3.getObject("bucket-versioning-on", "hello-string");
		System.out.println(IOUtils.toString(s3obj.getObjectContent()));
		por = s3.putObject("bucket-versioning-on", "hello-string", "Hello Ji!");
		s3obj = s3.getObject("bucket-versioning-on", "hello-string");
		System.out.println(por.getVersionId());
		System.out.println(IOUtils.toString(s3obj.getObjectContent()));
		//s3.deleteObject("bucket-versioning-on", "hello-string");
	}
	
	
	

}
