package kg.melakuera.springwebcontent.service;

import org.springframework.stereotype.Service;

@Service
public class AppValidatorService {
	
	public boolean validateEmail(String text) {
		return text.matches("\\w+@[a-z]+\\.[a-z]+");
	}
}
