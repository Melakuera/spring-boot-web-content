package kg.melakuera.springwebcontent.service;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileUploadService {
	
	@Value("${upload.path}")
	private String uploadPath;
	
	public String upload(MultipartFile file) {
		// Чтобы не было коллизии
		String uuid = UUID.randomUUID().toString();
		String idFile = uuid +"-"+ file.getOriginalFilename();
		try {
			file.transferTo(new File(uploadPath+idFile));
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
		}
		return idFile;
	}	
}
