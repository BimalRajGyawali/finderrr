package com.ncit.finder.controllers;

import javax.servlet.http.HttpServletRequest;

import com.ncit.finder.functionality.StorageService;
import com.ncit.finder.repository.UserRepository;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class FileUploadController {

    private final StorageService storageService;
    private final UserRepository userRepository;

    public FileUploadController(StorageService storageService, UserRepository userRepository) {
        this.storageService = storageService;
        this.userRepository = userRepository;
    }


    @PostMapping("/update-profile")
    public String handleFileUpload(HttpServletRequest request, @RequestParam("file") MultipartFile file,
                                   @RequestParam("bio") String bio, RedirectAttributes redirectAttributes, HttpServletRequest re) {
        String fileName = file.getOriginalFilename();
        boolean emptyFieldsError = true;
        int userId = (int) request.getSession().getAttribute("id");

        String currentDate = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        System.out.println("fileName=" + fileName);
        if (fileName != null && !fileName.isEmpty()) {

            String storageName = fileName.replace(fileName, FilenameUtils.getBaseName(fileName).concat(currentDate) + userId + "." + FilenameUtils.getExtension(fileName));

            userRepository.insertImage(storageName, (String) request.getSession().getAttribute("email"));
//		 		shouldve sent file name frome here so the same method could be used for diff'n path to save files.
            storageService.store(file, storageName);
            request.getSession().setAttribute("profile_pic", storageName);


            emptyFieldsError = false;
        }

        int id = (int) request.getSession().getAttribute("id");
        boolean status = userRepository.insertBio(bio, id);
        request.getSession().setAttribute("bio", bio);
        if (status) {
            redirectAttributes.addFlashAttribute("bioupdateSuccess", status);
        } else {
            redirectAttributes.addFlashAttribute("bioupdateFailure", !status);
        }


        return "redirect:/";
    }

}
