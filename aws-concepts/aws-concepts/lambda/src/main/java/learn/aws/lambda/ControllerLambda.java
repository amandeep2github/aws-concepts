package learn.aws.lambda;

import com.amazonaws.services.lambda.AWSLambda;
import com.amazonaws.services.lambda.AWSLambdaAsync;
import com.amazonaws.services.lambda.AWSLambdaAsyncClientBuilder;
import com.amazonaws.services.lambda.AWSLambdaClientBuilder;
import com.amazonaws.services.lambda.model.InvocationType;
import com.amazonaws.services.lambda.model.InvokeRequest;
import com.amazonaws.services.lambda.model.InvokeResult;
import com.amazonaws.services.lambda.runtime.Context;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import learn.aws.lambda.model.InvokeLambdaParameters;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.stream.IntStream;

public class ControllerLambda {
    public String executeLambdas(InvokeLambdaParameters inputParams, Context context){

        String uuid = UUID.randomUUID().toString();
        inputParams.setName(uuid);
        AWSLambdaAsync awsLambda = AWSLambdaAsyncClientBuilder.defaultClient();
        InvokeRequest invokeRequest = new InvokeRequest();
        invokeRequest.setFunctionName("WorkerLambda");
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            invokeRequest.setPayload(objectMapper.writeValueAsString(inputParams));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        System.out.printf("Invoking worker lambda %d times", inputParams.getNoOfInvocations());
        List<Future<InvokeResult>> invokeResults = new ArrayList<>();
        IntStream.range(1, inputParams.getNoOfInvocations()).forEach(ele->{

            invokeResults.add(awsLambda.invokeAsync(invokeRequest));
        });
        invokeResults.forEach(invokeFuture->{
            try {
                System.out.printf("Got invoke result for - %s", new String(invokeFuture.get().getPayload().array(), "utf-8"));
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        });

        return uuid;
    }
}
