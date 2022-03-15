package kg.melakuera.springwebcontent.service;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileUploadService {
	
	@Value("${upload.path}")
	private String path;
	
	public String upload(MultipartFile file) {
		try {
			file.transferTo(new File(path+file.getOriginalFilename()));
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
		}
		System.out.println(file.getContentType());
		return "success";
	}	
}
