package com.fileservice.fileservice.src.services.storage.file.validator;

import org.springframework.web.multipart.MultipartFile;

public class FileMaxSizeHundredKB implements ValidatorTypeInterface{
    @Override
    public String validate(MultipartFile file) {
        if (file.getSize() > 100000) { //Limit file size to 100kb
            return "The File size should be less then 100kb";
        }
        return null;
    }
}
