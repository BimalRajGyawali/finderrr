package com.ncit.finder.functionality;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.*;
import java.util.stream.Stream;

import com.ncit.finder.db.Response;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

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
