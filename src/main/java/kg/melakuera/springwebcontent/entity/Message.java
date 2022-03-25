package kg.melakuera.springwebcontent.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Objects;


@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Message {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotBlank(message = "Пожалуйста заполните текст сообщения")
	@Length(message = "Краткость - сестра таланта", max = 1024)
	private String text;
	private String tag;
	@ManyToOne
	@JoinColumn(name = "app_user_id")
	private AppUser appUser;
	@ManyToMany
	@JoinTable(
			name = "message_like_user",
			joinColumns = @JoinColumn(name = "message_id"),
			inverseJoinColumns = @JoinColumn(name = "user_id"))
	private List<AppUser> likeUsers;

	private String fileName;

	public Message(String text, String tag, AppUser appUser) {
		this.text = text;
		this.tag = tag;
		this.appUser = appUser;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
		Message message = (Message) o;
		return id != null && Objects.equals(id, message.id);
	}

	@Override
	public int hashCode() {
		return getClass().hashCode();
	}
}
