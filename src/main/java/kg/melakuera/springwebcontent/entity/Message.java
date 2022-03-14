package kg.melakuera.springwebcontent.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.*;
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
	private String text;
	private String tag;
	@ManyToOne
	@JoinColumn(name = "app_user_id")
	private AppUser appUser;

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
