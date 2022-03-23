package kg.melakuera.springwebcontent.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class AppUser implements UserDetails{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Size(min = 2, max = 30, message = "Длина имени не может быть меньше 2 и больше 30 символов")
	private String firstName;
	@Size(min = 2, max = 30, message = "Длина фамилии не может быть меньше 2 и больше 30 символов")
	private String lastName;
	@Email(message = "Эл. почта не соответствует формату: yourname@email.com")
	@NotBlank(message = "Эл. почта не соответствует формату: yourname@email.com")
	private String email;
	@NotBlank(message = "Пароль не может быть пустым")
	private String password;
	@Enumerated(EnumType.STRING)
	private Role role;
	private String resetPasswordCode;
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "subscriber_subscription",
			joinColumns = @JoinColumn(name = "subscription_id"),
			inverseJoinColumns = @JoinColumn(name = "subscriber_id"))
	private List<AppUser> subscribers;
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "subscriber_subscription",
			joinColumns = @JoinColumn(name = "subscriber_id"),
			inverseJoinColumns = @JoinColumn(name = "subscription_id"))
	private List<AppUser> subscriptions;
	private Boolean enabled;

	public AppUser(String firstName, String lastName, String email, String password, 
			Role role, Boolean enabled) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.role = role;
		this.enabled = enabled;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role.name());
		return Collections.singletonList(authority); 
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
		AppUser appUser = (AppUser) o;
		return id != null && Objects.equals(id, appUser.id);
	}

	@Override
	public int hashCode() {
		return getClass().hashCode();
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + "(" +
				"id = " + id + ", " +
				"firstName = " + firstName + ", " +
				"lastName = " + lastName + ", " +
				"email = " + email + ", " +
				"password = " + password + ", " +
				"role = " + role + ", " +
				"resetPasswordCode = " + resetPasswordCode + ", " +
				"enabled = " + enabled + ")";
	}
}
