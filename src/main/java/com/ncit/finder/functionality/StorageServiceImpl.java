package com.ncit.finder.functionality;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.util.stream.Stream;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class StorageServiceImpl implements StorageService {

	@Override
	public void init() {
		
	}

	@Override
	public void store(MultipartFile file,String newName) {
		int c;
		InputStream f;
		try {
			f = file.getInputStream();
			OutputStream outputStream=new FileOutputStream(new java.io.File("src/main/webapp/resources/uploads/"+newName));
			
			while((c=f.read())!=-1) {
				outputStream.write(c);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public Stream<Path> loadAll() {
		return null;
	}

	@Override
	public Path load(String filename) {
		return null;
	}

	@Override
	public Resource loadAsResource(String filename) {
		return null;
	}

	@Override
	public void deleteAll() {		
	}

	
	
}
