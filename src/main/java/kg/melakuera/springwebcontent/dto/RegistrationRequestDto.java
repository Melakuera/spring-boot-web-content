package kg.melakuera.springwebcontent.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class RegistrationRequestDto {
	@JsonProperty("first_name")
	private String firstName;
	@JsonProperty("last_name")
	private String lastName;
	@JsonProperty("email")
	private String email;
	@JsonProperty("password")
	private String password;
}
