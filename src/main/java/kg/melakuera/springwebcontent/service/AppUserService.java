package kg.melakuera.springwebcontent.service;

import kg.melakuera.springwebcontent.entity.AppUser;
import kg.melakuera.springwebcontent.entity.Role;
import kg.melakuera.springwebcontent.repository.AppUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
@Log
public class AppUserService implements UserDetailsService {

    private final AppUserRepository appUserRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AppMailSenderService appMailSenderService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return appUserRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(
                        String.format("Пользователь с данной %s эл. почтой не найден", email)));
    }

    public boolean register(AppUser appUser) {
        boolean user = appUserRepository.findByEmail(appUser.getEmail()).isPresent();
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

    public AppUser findByEmail(String email) {
        return appUserRepository.findByEmail(email).orElse(null);
    }

    public boolean isAdmin(AppUser appUser) {
        return appUser.getRole().equals(Role.ROLE_ADMIN);
    }

    public AppUser findByResetPasswordCode(String code) {
        return appUserRepository.findByResetPasswordCode(code).orElse(null);
    }


    public void update(AppUser appUser, Long id) {
        AppUser updatedAppUser = appUserRepository.findById(id).orElse(null);
        assert updatedAppUser != null;
        updatedAppUser.setFirstName(appUser.getFirstName());
        updatedAppUser.setLastName(appUser.getLastName());
    }

    public void updateByAdmin(AppUser appUser, Long id) {
        AppUser updatingAppUser = appUserRepository.findById(id).orElse(null);
        assert updatingAppUser != null;
        updatingAppUser.setFirstName(appUser.getFirstName());
        updatingAppUser.setLastName(appUser.getLastName());
        updatingAppUser.setRole(appUser.getRole());
    }

    public void resetPassword(Long id) {
        AppUser appUser = appUserRepository.findById(id).orElse(null);
        String code = UUID.randomUUID().toString();
        assert appUser != null;
        appUser.setResetPasswordCode(code);
        Thread thread = new Thread(() -> appMailSenderService.sendResetPassword(appUser));
        thread.start();

        log.info("Запрос на сброс пароля отправил пользователь с id: " + id);
    }

    public void updatePassword(AppUser appUser, String pw1, String pw2) {
        appUser.setPassword(bCryptPasswordEncoder.encode(pw1));

        log.info(String.format("Пароль пользователя %s успешно изменён", appUser.getEmail()));
    }

    public void unsubscribe(AppUser appUser, Long id) {
        AppUser foundUser = appUserRepository.findById(id).orElse(null);
        assert foundUser != null;
        foundUser.getSubscribers().remove(appUser);
    }

    public void subscribe(AppUser appUser, Long id) {
        AppUser foundUser = appUserRepository.findById(id).orElse(null);
        assert foundUser != null;
        foundUser.getSubscribers().add(appUser);
    }
}