package com.ncit.finder.controllers;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
	public String handleFileUpload(HttpServletRequest request,@RequestParam("file") MultipartFile file,
			@RequestParam("bio") String bio,RedirectAttributes redirectAttributes) {
			String userId="3";
		 	UserRepository repository=new UserRepository();
		 	String fileName=file.getOriginalFilename();
		 	boolean emptyFieldsError=true;
		 	
		 	
		 	String currentDate = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		 	System.out.println("fileName="+fileName);
		 	if(!fileName.isEmpty()) {
		 
		 		String storageName=fileName.replace(fileName,FilenameUtils.getBaseName(fileName).concat(currentDate)+userId+"."+FilenameUtils.getExtension(fileName));
		 		
		 		repository.insertImage(storageName,(String) request.getSession().getAttribute("email"));
		 		//shouldve sent file name frome here so the same method could be used for diff'n path to save files. 
		 		storageService.store(file,storageName);
		 		
		 		request.getSession().setAttribute("profile_pic",storageName);
		 		
		 		emptyFieldsError=false;
		 	}
		 	if(!bio.isBlank()) {
		 		int id=(int) request.getSession().getAttribute("id");
		 		repository.insertBio(bio,id);
		 		request.getSession().setAttribute("bio",bio);
		 		emptyFieldsError=false;
		 	}
		 	if(emptyFieldsError==true) {
		 		redirectAttributes.addFlashAttribute("emptyFieldsError",emptyFieldsError);
				
		 		return "redirect:/create-profile";
		 	}
		
		return "redirect:/";
	}
	
}
