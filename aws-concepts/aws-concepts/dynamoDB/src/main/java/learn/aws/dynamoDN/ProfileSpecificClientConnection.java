package learn.aws.dynamoDN;

import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;

import java.util.ArrayList;
import java.util.List;

public class ProfileSpecificClientConnection {

    public AmazonDynamoDB getDynamoDbConnection(String profile){
        AmazonDynamoDB amazonDynamoDB = AmazonDynamoDBClient.builder().withCredentials(new ProfileCredentialsProvider(profile)).withRegion("us-east-1").build();
        return amazonDynamoDB;
    }


}
