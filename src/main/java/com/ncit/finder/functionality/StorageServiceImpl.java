package com.ncit.finder.functionality;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Component
public class StorageServiceImpl implements StorageService {


    @Override
    public void store(MultipartFile file, String newName) {
        String uploadPath = "src/main/webapp/resources/uploads/" + newName;
        try {
            Files.copy(file.getInputStream(), Paths.get(uploadPath), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
