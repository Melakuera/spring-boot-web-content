package kg.melakuera.springwebcontent.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import kg.melakuera.springwebcontent.entity.AppUser;
import kg.melakuera.springwebcontent.repository.AppUserRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AppUserService implements UserDetailsService {
	
	private final AppUserRepository appUserRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		return appUserRepository.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException(
						String.format("Пользователь с данной %s эл. почтой не найден", email)));
	}
	
	public String save(AppUser appUser) {
		boolean user =  appUserRepository.findByEmail(appUser.getEmail()).isPresent();
		if (user) {
			return "Такой пользователь существует";
		}
		appUser.setPassword(bCryptPasswordEncoder.encode(appUser.getPassword()));
		appUserRepository.save(appUser);
		return "Пользователь зарегистрирован";
	}
}
