package com.ncit.finder.functionality;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.util.stream.Stream;

import com.ncit.finder.db.Response;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class StorageServiceImpl implements StorageService {


	@Override
	public void store(MultipartFile file,String newName) {
		int c;
		InputStream f;
		try {
			String uploadDir = "src/main/webapp/resources/uploads/";
		
			f = file.getInputStream();
			OutputStream outputStream=new FileOutputStream(new java.io.File(uploadDir+"/"+newName));
			
			while((c=f.read())!=-1) {
				outputStream.write(c);
			}
			
		} catch(Exception ex){
			ex.printStackTrace();
		}
	}
}
