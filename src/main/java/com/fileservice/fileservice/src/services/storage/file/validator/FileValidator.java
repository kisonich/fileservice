package com.fileservice.fileservice.src.services.storage.file.validator;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;

@Service
public class FileValidator implements FileValidatorInterface {

    private ArrayList<ValidatorTypeInterface> validatorTypes;

    public ArrayList<ValidatorTypeInterface> getValidatorTypes() {
        return this.validatorTypes;
    }

    public FileValidator setValidatorTypes(ArrayList<ValidatorTypeInterface> validatorTypes) {
        this.validatorTypes = validatorTypes;
        return this;
    }



    @Override
    public ArrayList<String> validate(MultipartFile file) {
        ArrayList<String> errors = new ArrayList<>();
        for (ValidatorTypeInterface validatorType: validatorTypes) {
            String message = validatorType.validate(file);
            if(message != null){
                errors.add(message);
            }
        }
        return errors;
    }
}
