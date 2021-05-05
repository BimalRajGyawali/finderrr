package com.ncit.finder.controllers;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import com.ncit.finder.functionality.StorageService;
import com.ncit.finder.repository.UserRepository;

@Controller
public class FileUploadController {
	
	private final StorageService storageService;	
	@Autowired
	public FileUploadController(StorageService storageService) {
		this.storageService = storageService;
	}
	
	@PostMapping("/update-profile")
	public String handleFileUpload(@RequestParam("file") MultipartFile file,
			@RequestParam("bio") String bio) {
			String userId="3";
		 	UserRepository repository=new UserRepository();
		 	String fileName=file.getOriginalFilename();
		 	
		 	String currentDate = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		 	System.out.println(currentDate); 
		 
		 	String storageName=fileName.replace(fileName,FilenameUtils.getBaseName(fileName).concat(currentDate)+userId+"."+FilenameUtils.getExtension(fileName));
		 	
		 	repository.insertImageWithBio(storageName,"bimalraj269@gmail.com",bio);
	
		 	storageService.store(file,storageName);
			
		
		return "index";
	}
	
}
