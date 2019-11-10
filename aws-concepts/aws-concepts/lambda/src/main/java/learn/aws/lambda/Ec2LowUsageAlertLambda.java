package learn.aws.lambda;

import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.cloudwatch.AmazonCloudWatch;
import com.amazonaws.services.cloudwatch.AmazonCloudWatchClient;
import com.amazonaws.services.cloudwatch.AmazonCloudWatchClientBuilder;
import com.amazonaws.services.cloudwatch.model.Dimension;
import com.amazonaws.services.cloudwatch.model.GetMetricStatisticsRequest;
import com.amazonaws.services.cloudwatch.model.GetMetricStatisticsResult;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2ClientBuilder;
import com.amazonaws.services.ec2.model.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Ec2LowUsageAlertLambda {
    public static void main(String[] args){
        describeInstances();
    }

    public static void describeInstances(){
        DescribeInstancesRequest request = new DescribeInstancesRequest();
        List<String> valuesT1 = new ArrayList<String>();
        valuesT1.add("p.s");
        Filter filter = new Filter("tag:account", valuesT1);
        final AmazonEC2 ec2 = AmazonEC2ClientBuilder.standard().withCredentials(new ProfileCredentialsProvider("pubsap")).build();
        DescribeInstancesResult result = ec2.describeInstances(request.withFilters(filter));//

        List<Reservation> reservations = result.getReservations();

        for (Reservation reservation : reservations) {
            List<Instance> instances = reservation.getInstances();

            for (Instance instance : instances) {

                System.out.println(instance.getInstanceId());
                GetMetricStatisticsRequest gmsr = new GetMetricStatisticsRequest();
                gmsr.setMetricName("CPUUtilization");
                gmsr.setPeriod(300);
                List<String> statistics = new ArrayList<>();
                statistics.add("Average");
                gmsr.setStatistics(statistics);
                Calendar cal = Calendar.getInstance();
                gmsr.setEndTime(cal.getTime());
                cal.add(Calendar.DAY_OF_MONTH, -1);
                gmsr.setStartTime(cal.getTime());
                gmsr.setNamespace("AWS/EC2");
                Dimension d = new Dimension();
                d.setName("InstanceId");
                d.setValue("i-0d78ae1dc782d0a37");
                List<Dimension> dimensions = new ArrayList<>();
                dimensions.add(d);
                gmsr.setDimensions(dimensions);
                AmazonCloudWatch amazonCloudWatch = AmazonCloudWatchClientBuilder.standard().withCredentials(new ProfileCredentialsProvider("pubsap")).build();
                GetMetricStatisticsResult gmsResult = amazonCloudWatch.getMetricStatistics(gmsr);
                gmsResult.getDatapoints().forEach(dp->{
                    System.out.println(dp.getAverage());
                });




            }
        }
    }
}
