package com.fileservice.fileservice.src.services.storage.file.validator;

import org.springframework.web.multipart.MultipartFile;

public class FileTypeTxtCsvValidator  implements ValidatorTypeInterface{
    @Override
    public String validate(MultipartFile file) {
        String filename = file.getOriginalFilename();
        String[] parts = filename.split("\\.");
        String extension = parts[parts.length-1].toLowerCase();
        if (!(extension.equals("txt") || extension.equals("csv"))) { //Limit file size to 100kb
            return "The File extension should be txt or csv";
        }
        return null;
    }
}
