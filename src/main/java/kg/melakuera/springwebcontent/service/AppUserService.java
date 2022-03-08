package kg.melakuera.springwebcontent.service;

import lombok.extern.java.Log;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kg.melakuera.springwebcontent.entity.AppUser;
import kg.melakuera.springwebcontent.entity.ConfirmationEmail;
import kg.melakuera.springwebcontent.repository.AppUserRepository;
import lombok.AllArgsConstructor;

@Service
@Transactional
@AllArgsConstructor
@Log
public class AppUserService implements UserDetailsService {
	
	private final AppUserRepository appUserRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	private final ConfirmationEmailService confirmationTokenService;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		return appUserRepository.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException(
						String.format("Пользователь с данной %s эл. почтой не найден", email)));
	}
	
	public boolean save(AppUser appUser) {
		boolean user =  appUserRepository.findByEmail(appUser.getEmail()).isPresent();
		if (user) {
			log.info("Такой пользователь уже сущесвует!");
			
			return false;
		}
		appUser.setPassword(bCryptPasswordEncoder.encode(appUser.getPassword()));
		appUserRepository.save(appUser);
		
//		String token = UUID.randomUUID().toString();
//		ConfirmationToken confirmationToken = new ConfirmationToken(
//				token, 
//				LocalDateTime.now(), 
//				LocalDateTime.now().plusMinutes(5), 
//				appUser
//				);
//		log.info(token);
//		confirmationTokenService.save(confirmationToken);
		
		return true;
	}

	public void confirm(String email) {
		AppUser appUser = appUserRepository.findByEmail(email)
				.orElseThrow(() -> new IllegalStateException(String.format("Пользователь с данной %s эл. почтой не найден", email)));
		appUser.setLocked(true);
		appUser.setEnabled(true);
	}
}
