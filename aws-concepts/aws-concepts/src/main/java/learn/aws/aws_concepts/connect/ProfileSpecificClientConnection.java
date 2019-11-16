package learn.aws.aws_concepts.connect;

import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.Bucket;

import java.util.ArrayList;
import java.util.List;

public class ProfileSpecificClientConnection {


    public AmazonS3 getS3Connection(String profile){
        return AmazonS3Client.builder().withCredentials(new ProfileCredentialsProvider(profile)).withRegion("us-east-1")
                .build();
    }

    public List<Bucket> getBuckets(){
        final AmazonS3 s3 = AmazonS3Client.builder().withCredentials(new ProfileCredentialsProvider("pubsap"))
                //.withRegion(Regions.AP_SOUTH_1)
                .build();
        /*List<Bucket> buckets =
                s3.listBuckets();*/

//        return buckets;
        System.out.println(s3.doesBucketExist("amandeep.singh22publicissapient.com"));
        return new ArrayList<>();
    }

    public AmazonDynamoDB getDynamoDbConnection(String profile){
        AmazonDynamoDB amazonDynamoDB = AmazonDynamoDBClient.builder().withCredentials(new ProfileCredentialsProvider(profile)).withRegion("us-east-1").build();
        return amazonDynamoDB;
    }


}
