package com.reservatucancha.backend.rtc_backend.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class S3Config {

    @Value("${aws.accessKeyId}")
    private String accessKey;

    @Value("${aws.secretKey}")
    private String secretKey;


    @Bean
    public AmazonS3 s3client() {

        //Credentials
        AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);

        //Building aws client
        AmazonS3 s3client = AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(Regions.US_EAST_1)
                .build();

        //List of buckets for successful access verification
        try {
            s3client.listBuckets();
            System.out.println("Acceso a S3 exitoso"+ s3client.listBuckets()) ;
        } catch (Exception e) {
            System.err.println("Error de acceso a S3: " + e.getMessage());
        }

        System.out.println("accessKey: " + accessKey);
        System.out.println("privateKey: " + secretKey);

        return s3client;
    }
}
