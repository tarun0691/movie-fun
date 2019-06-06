package org.superbiz.moviefun.blobstore;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;

import static java.lang.String.format;

public class S3Store implements BlobStore {
    private final AmazonS3Client s3client;
    private final String photoStorageBucket;

    public S3Store(AmazonS3Client s3client, String photoStorageBucket) {
        this.s3client = s3client;
        this.photoStorageBucket = photoStorageBucket;
    }



    @Override
    public void put(Blob blob) throws IOException {
        s3client.putObject(photoStorageBucket,blob.name, blob.inputStream, new ObjectMetadata());
    }

    @Override
    public Optional<Blob> get(String name) throws IOException {
        s3client.getObject(photoStorageBucket, name);
        return Optional.empty();
    }

    @Override
    public void deleteAll() {
        if(photoStorageBucket != null){
            s3client.deleteBucket(photoStorageBucket);
        }
    }
}
