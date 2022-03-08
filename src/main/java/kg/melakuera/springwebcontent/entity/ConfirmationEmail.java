package kg.melakuera.springwebcontent.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;

@Data
@Entity
public class ConfirmationEmail {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String token;
	private LocalDateTime createdAt;
	private LocalDateTime expiredAt;
	private LocalDateTime confirmedAt;
	@ManyToOne
	@JoinColumn(name = "app_user_id")
	private AppUser appUser;

	public ConfirmationEmail(String token, LocalDateTime createdAt, 
			LocalDateTime expiredAt, AppUser appUser) {
		this.token = token;
		this.createdAt = createdAt;
		this.expiredAt = expiredAt;
		this.appUser = appUser;
	}
	
	
}
