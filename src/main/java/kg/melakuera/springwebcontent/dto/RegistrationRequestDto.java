package kg.melakuera.springwebcontent.dto;

import lombok.Data;

@Data
public class RegistrationRequestDto {
	private String firstName;
	private String lastName;
	private String email;
	private String password;
}
