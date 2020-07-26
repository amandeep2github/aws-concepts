package learn.aws.aws_concepts.connect;


import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.UpdateItemSpec;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
public class TestDynamoDb {
    @Test
    public void testCrud(){
        ProfileSpecificClientConnection profileSpecificClientConnection = new ProfileSpecificClientConnection();
        AmazonDynamoDB amazonDynamoDB = profileSpecificClientConnection.getDynamoDbConnection("pubsap");
        String tableName = "anything";
        AttributeValue attributeValue = new AttributeValue();
        String testValue = "Test Value";
        String testValue1 = "Test Value1";
        Item itemInsert = new Item().withPrimaryKey("record_id", 2).withString("value", testValue);
        DynamoDB dynamoDB = new DynamoDB(amazonDynamoDB);
        Table dynamoDBTable = dynamoDB.getTable(tableName);
        //insert
        dynamoDBTable.putItem(itemInsert);
        //read
        Item itemRead = dynamoDBTable.getItem("record_id", 2);
        itemRead.getString("value");
        Assertions.assertThat(itemRead.getString("value")).isEqualTo(testValue);
        Map<String, Object> updateValues = new HashMap<>();
        updateValues.put("value", testValue1);
        UpdateItemSpec updateItemSpec = new UpdateItemSpec().withPrimaryKey("record_id", 2).withValueMap(updateValues);
        dynamoDBTable.updateItem(updateItemSpec);

        itemRead = dynamoDBTable.getItem("record_id", 2);
        itemRead.getString("value");
        Assertions.assertThat(itemRead.getString("value")).isEqualTo(testValue1);



    }


}
