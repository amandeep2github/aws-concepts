package learn.aws.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import learn.aws.lambda.model.InvokeLambdaParameters;

public class WorkerLambda {
    public String executeLambdas(InvokeLambdaParameters inputParams, Context context){
        System.out.printf("Running lambda - %s, for - %s", context.getAwsRequestId(), inputParams.getName());

        return context.getAwsRequestId();
    }
}
