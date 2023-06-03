package com.fileservice.fileservice.src.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;

@Configuration
public class StorageConfig {

    @Value("${storage.upload.path}")
    private String rootPath;


    @Bean
    public File rootFolder(){
        String path = rootPath;

        File rootFolder = new File(path);
        if(!rootFolder.exists()){
            boolean resultMkdir = rootFolder.mkdir();
        }
        return rootFolder;
    }
}
