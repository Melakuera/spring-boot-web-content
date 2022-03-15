package kg.melakuera.springwebcontent.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import kg.melakuera.springwebcontent.service.FileUploadService;
import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class FileUploadController {
	
	private FileUploadService fileUploadService;
	
	@PostMapping("/upload")
	@ResponseBody
	public String upload(@RequestParam("file") MultipartFile file) {
		return fileUploadService.upload(file);
	}
}
