package com.fileservice.fileservice;


import com.fileservice.fileservice.src.services.storage.file.validator.FileValidatorInterface;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;


import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@SpringBootTest
@RunWith(SpringRunner.class)
public class FileValidatorTests {


    @Autowired
    FileValidatorInterface myFileValidator;

    @Test
    public void validateMixOversizeAndExtensionFile(){
        byte[] fileContent = new byte[110 * 1024]; // 110 KB
        // Create a file to upload
        MockMultipartFile bigFile = new MockMultipartFile("file", "test.exe", "text/plain", fileContent);
        ArrayList<String> validate = myFileValidator.validate(bigFile);

        assertTrue("Didn't catch the error: The File size should be less then 100kb", validate.contains("The File size should be less then 100kb"));
        assertTrue("Didn't catch the error: The File extension should be txt or csv", validate.contains("The File extension should be txt or csv"));
    }


    @Test
    public void fileExtensionValidate(){
        MockMultipartFile file = new MockMultipartFile("file", "test.exe", "text/plain", "Hello World".getBytes());
        ArrayList<String> validate = myFileValidator.validate(file);
        assertTrue("Didn't catch the error: The File extension should be txt or csv", validate.contains("The File extension should be txt or csv"));
    }

    @Test
    public void validateOversizeFile(){
        byte[] fileContent = new byte[110 * 1024]; // 110 KB
        // Create a file to upload
        MockMultipartFile bigFile = new MockMultipartFile("file", "test.txt", "text/plain", fileContent);
        ArrayList<String> validate = myFileValidator.validate(bigFile);

        assertEquals("Didn't catch the error: The File size should be less then 100kb", true, validate.contains("The File size should be less then 100kb"));
    }


    @Test
    public void validate(){
        MockMultipartFile file = new MockMultipartFile("file", "test.txt", "text/plain", "Hello World".getBytes());
        ArrayList<String> validate = myFileValidator.validate(file);
        assertEquals("Do we have an error with valid File", 0, validate.size());
    }










}
