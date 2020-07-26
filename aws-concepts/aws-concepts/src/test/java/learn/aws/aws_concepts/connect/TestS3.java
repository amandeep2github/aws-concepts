package learn.aws.aws_concepts.connect;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;


public class TestS3 {

    @Test @Ignore
    public void bucketCountFromDefaultConnection() {
        S3DefaultClientConnection s3conn = new S3DefaultClientConnection();
        List<Bucket> buckets = s3conn.getBuckets();
        for (Bucket bucket : buckets) {
            System.out.println(bucket.getName());
        }
    }

    @Test
    public void bucketCountFromProfileConnection() {
        ProfileSpecificClientConnection s3conn = new ProfileSpecificClientConnection();
        List<Bucket> buckets = s3conn.getBuckets();
        for (Bucket bucket : buckets) {
            System.out.println(bucket.getName());
        }
    }

    @Test
    public void uploadContents() {
        ProfileSpecificClientConnection s3conn = new ProfileSpecificClientConnection();
        AmazonS3 amazonS3 = s3conn.getS3Connection("pubsap");
        try (Stream<Path> walk = Files.walk(Paths.get("/Users/amandeepsingh/Documents/backuppendrive"))) {

            walk.filter(Files::isRegularFile).forEach(path -> {
                File file = new File(path.toUri());
                amazonS3.putObject("amandeep.singh22publicissapient.com", file.getName(), file);
            });

        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    @Test
    public void deleteContents() {
        ProfileSpecificClientConnection s3conn = new ProfileSpecificClientConnection();
        AmazonS3 amazonS3 = s3conn.getS3Connection("pubsap");
        //DeleteObjectsRequest deleteObjectsRequest = new DeleteObjectsRequest("amandeep.singh22publicissapient.com");
        //amazonS3.deleteObjects(deleteObjectsRequest);
        ObjectListing objectListing = amazonS3.listObjects("amandeep.singh22publicissapient.com");
        while (true) {
            Iterator<S3ObjectSummary> objIter = objectListing.getObjectSummaries().iterator();
            while (objIter.hasNext()) {
                amazonS3.deleteObject("amandeep.singh22publicissapient.com", objIter.next().getKey());
            }

            // If the bucket contains many objects, the listObjects() call
            // might not return all of the objects in the first listing. Check to
            // see whether the listing was truncated. If so, retrieve the next page of objects
            // and delete them.
            if (objectListing.isTruncated()) {
                objectListing = amazonS3.listNextBatchOfObjects(objectListing);
            } else {
                break;
            }
        }


    }

}
