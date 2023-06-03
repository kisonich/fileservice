package com.fileservice.fileservice.src.config;

import com.fileservice.fileservice.src.services.storage.file.validator.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;

@Configuration
public class ValidatorConfig {

    @Bean
    public FileValidatorInterface myFileValidator(){
        ArrayList<ValidatorTypeInterface> validatorTypes = new ArrayList<>();
        validatorTypes.add(new FileMaxSizeHundredKB());
        validatorTypes.add(new FileTypeTxtCsvValidator());

        FileValidator fileValidator = new FileValidator();
        fileValidator.setValidatorTypes(validatorTypes);

        return fileValidator;
    }

}
