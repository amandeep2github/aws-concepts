package learn.aws.lambda.model;

public class InvokeLambdaParameters {
    private int noOfInvocations;
    private String name;

    public InvokeLambdaParameters() {
    }

    public int getNoOfInvocations() {
        return noOfInvocations;
    }

    public void setNoOfInvocations(int noOfInvocations) {
        this.noOfInvocations = noOfInvocations;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
