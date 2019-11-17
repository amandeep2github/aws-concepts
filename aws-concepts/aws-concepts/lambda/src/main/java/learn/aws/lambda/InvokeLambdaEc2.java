package learn.aws.lambda;

import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.lambda.AWSLambda;
import com.amazonaws.services.lambda.AWSLambdaClientBuilder;
import com.amazonaws.services.lambda.model.InvokeRequest;
import com.amazonaws.services.lambda.model.InvokeResult;

import java.nio.ByteBuffer;

public class InvokeLambdaEc2 {
    public static void main(String[] arg){
        AWSLambda awsLambda = AWSLambdaClientBuilder.standard().withCredentials(new ProfileCredentialsProvider("pubsap")).build();
        InvokeRequest invokeRequest = new InvokeRequest();
        invokeRequest.setFunctionName("HelloLambda");
        invokeRequest.setPayload("{\n" +
                " \"noOfInvocations\":10,\n" +
                " \"name\":\"Amandeep Json Program\"\n" +
                "}");
        InvokeResult invokeResult = awsLambda.invoke(invokeRequest);
        System.out.println(invokeResult.getLogResult());
        System.out.println(invokeResult.getFunctionError());
        System.out.println(invokeResult.getExecutedVersion());
        System.out.println(invokeResult.getStatusCode());
        System.out.println(invokeResult.getPayload());
        ByteBuffer byteBuffer = invokeResult.getPayload();

        String rawJson = null;

        try {
            rawJson = new String(byteBuffer.array(), "UTF-8");
        }catch (Exception e) {

        }

        System.out.println(rawJson);
    }
}
