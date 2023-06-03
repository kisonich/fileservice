package com.fileservice.fileservice.src.services.storage.file.validator;

import org.springframework.web.multipart.MultipartFile;

public interface ValidatorTypeInterface {
    public String validate(MultipartFile file);
}
