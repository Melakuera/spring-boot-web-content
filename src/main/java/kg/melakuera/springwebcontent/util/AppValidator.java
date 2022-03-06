package kg.melakuera.springwebcontent.util;

import org.springframework.stereotype.Component;

@Component
public class AppValidator {
	
	public boolean validateEmail(String text) {
		return text.matches("\\w+@[a-z]+\\.[a-z]+");
	}

}
