package com.example.digitalmuseum.service;
import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.digitalmuseum.Util.AWSUtil;
import java.io.File;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class AWSService {


    private AmazonS3 s3Client;

    Regions regions;

    private Logger logger = LoggerFactory.getLogger(AWSService.class);

    @Autowired
    public AWSService() {
        BasicAWSCredentials awsCredentials = new BasicAWSCredentials(AWSUtil.ACCESS_KEY, AWSUtil.SECRET_KEY);
        this.s3Client = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .withRegion(regions.US_EAST_2).build();
    }


    public void uploadFile(File file, String fileName) {
        try {
            String bucket = AWSUtil.BUCKET_NAME;
            PutObjectRequest request = new PutObjectRequest(AWSUtil.BUCKET_NAME,fileName,file);
            s3Client.putObject(request);
        } catch (AmazonServiceException ase) {
            logger.info("Caught an AmazonServiceException from PUT requests, rejected reasons:");
            logger.info("Error Message:    " + ase.getMessage());
            logger.info("HTTP Status Code: " + ase.getStatusCode());
            logger.info("AWS Error Code:   " + ase.getErrorCode());
            logger.info("Error Type:       " + ase.getErrorType());
            logger.info("Request ID:       " + ase.getRequestId());
            throw ase;
        } catch (AmazonClientException ace) {
            logger.info("Caught an AmazonClientException: ");
            logger.info("Error Message: " + ace.getMessage());
            throw ace;
        }
    }


    public void deleteFileFromS3Bucket(String fileName)
    {
        try {
            s3Client.deleteObject(new DeleteObjectRequest(AWSUtil.BUCKET_NAME, fileName));
        } catch (AmazonServiceException ex) {
            logger.error("error [" + ex.getMessage() + "] occurred while removing [" + fileName + "] ");
        }
    }


}
