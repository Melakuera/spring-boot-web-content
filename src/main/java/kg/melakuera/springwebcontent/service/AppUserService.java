package kg.melakuera.springwebcontent.service;

import kg.melakuera.springwebcontent.entity.AppUser;
import kg.melakuera.springwebcontent.entity.Role;
import kg.melakuera.springwebcontent.repository.AppUserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
@Log
public class AppUserService implements UserDetailsService {
	
	private final AppUserRepository appUserRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		return appUserRepository.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException(
						String.format("Пользователь с данной %s эл. почтой не найден", email)));
	}
	
	public boolean register(AppUser appUser) {
		boolean user =  appUserRepository.findByEmail(appUser.getEmail()).isPresent();
		if (user) {

			log.info("Такой пользователь уже сущесвует!");
			return false;
		}

		appUser.setPassword(bCryptPasswordEncoder.encode(appUser.getPassword()));
		appUserRepository.save(appUser);

		return true;
	}

	public List<AppUser> findAll() {
		return appUserRepository.findAll();
	}

	public AppUser findById(Long id) {
		return appUserRepository.findById(id).orElse(null);
	}

	public void update(AppUser appUser, Long id) {
		AppUser updatingAppUser = appUserRepository.findById(id).orElse(null);
		assert updatingAppUser != null;
		updatingAppUser.setFirstName(appUser.getFirstName());
		updatingAppUser.setLastName(appUser.getLastName());
		updatingAppUser.setRole(appUser.getRole());
	}
	public boolean isAdmin(AppUser appUser){
		return appUser.getRole().equals(Role.ROLE_ADMIN);
	}

}
