package com.ncit.finder.functionality;

import java.nio.file.Path;
import java.util.stream.Stream;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;


public interface StorageService {
	void store(MultipartFile file,String newName);
}
