package com.fileservice.fileservice.src.services.storage.file.validator;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;

@Service
public interface FileValidatorInterface {
    public ArrayList<String> validate(MultipartFile file);
}
