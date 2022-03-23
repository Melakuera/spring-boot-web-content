package kg.melakuera.springwebcontent.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
public class FileService {
	
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

	public void delete(String fileName) {
		new File(uploadPath+fileName).delete();
	}
}
