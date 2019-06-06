package org.superbiz.moviefun.blobstore;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import org.apache.tika.Tika;
import org.omg.CORBA.portable.InputStream;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import static java.lang.ClassLoader.getSystemResource;
import static java.lang.String.format;
import static java.nio.file.Files.*;
import org.apache.tika.io.IOUtils;

public class FileStore implements BlobStore {


    @Override
    public void put(Blob blob) throws IOException {

        File targetFile = new File(blob.name);


        targetFile.delete();
        targetFile.getParentFile().mkdirs();
        targetFile.createNewFile();

        try (FileOutputStream outputStream = new FileOutputStream(targetFile)) {
            IOUtils.copy(blob.inputStream, outputStream);
        }
    }

    @Override
    public Optional<Blob> get(String name) throws IOException {
        Tika tika = new Tika();
        File file = new File(name);
        return Optional.of(new Blob(name, new FileInputStream(file), tika.detect(file)));
    }

    @Override
    public void deleteAll() {
        // ...
    }
}
