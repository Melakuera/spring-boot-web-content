package kg.melakuera.springwebcontent.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity(name = "confirmation_code")
public class ConfirmationCode {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String code;
	private LocalDateTime createdAt;
	private LocalDateTime expiredAt;
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "app_user_id")
	private AppUser appUser;

	public ConfirmationCode(String code, LocalDateTime createdAt,
							LocalDateTime expiredAt, AppUser appUser) {
		this.code = code;
		this.createdAt = createdAt;
		this.expiredAt = expiredAt;
		this.appUser = appUser;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
		ConfirmationCode that = (ConfirmationCode) o;
		return id != null && Objects.equals(id, that.id);
	}

	@Override
	public int hashCode() {
		return getClass().hashCode();
	}
}
