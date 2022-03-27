package kg.melakuera.springwebcontent.service;

import kg.melakuera.springwebcontent.entity.AppUser;
import kg.melakuera.springwebcontent.entity.Role;
import kg.melakuera.springwebcontent.repository.AppUserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@SpringBootTest
public class AppUserServiceTest {

    @MockBean
    private AppUserRepository appUserRepository;
    @MockBean
    private BCryptPasswordEncoder passwordEncoder;
    @MockBean
    private AppMailSenderService appMailSenderService;
    @Autowired
    private AppUserService appUserService;

    @Test
    void isAdminShouldBeTrue() {

        AppUser appUser = new AppUser();
        appUser.setRole(Role.ROLE_ADMIN);

        boolean res = appUserService.isAdmin(appUser);

        assertThat(res).isTrue();
    }

    @Test
    void isAdminShouldBeFalse() {

        AppUser appUser = new AppUser();
        appUser.setRole(Role.ROLE_USER);

        boolean res = appUserService.isAdmin(appUser);

        assertThat(res).isFalse();
    }

    @Test
    void shouldThrowExpLoadUserByUsername() {

        assertThatThrownBy(() ->
                appUserService.loadUserByUsername("test@test.com"))
                .isInstanceOf(UsernameNotFoundException.class)
                .hasMessage("Пользователь с данной test@test.com эл. почтой не найден");
    }

    @Test
    void shouldFindUserById() {

        AppUser expectedAppUser = new AppUser(
                1L, "Tom", "Bob", "email@email.com", "1", null, null);

        when(appUserRepository.findById(1L)).thenReturn(Optional.of(expectedAppUser));
        AppUser actualAppUser = appUserService.findById(1L);

        assertThat(appUserService.findById(1L)).isEqualTo(actualAppUser);
    }

    @Test
    void shouldRegisterUser() {

        AppUser appUser = new AppUser();
        appUser.setEmail("email@email.com");
        appUser.setPassword("1111");
        appUserService.register(appUser);

        verify(passwordEncoder, times(1)).encode(anyString());
        verify(appUserRepository, times(1)).findByEmail(eq(appUser.getEmail()));
        verify(appUserRepository, times(1)).save(any(AppUser.class));
    }


    @Test
    void shouldUpdateUser() {
        AppUser appUserFromForm = new AppUser();
        appUserFromForm.setFirstName("Tom");
        appUserFromForm.setLastName("Henson");

        AppUser updatedAppUser = new AppUser();
        updatedAppUser.setFirstName("Bob");
        updatedAppUser.setLastName("Henson");

        when(appUserRepository.findById(anyLong())).thenReturn(Optional.of(updatedAppUser));

        appUserService.update(appUserFromForm, anyLong());
        assertThat(updatedAppUser).isEqualToComparingFieldByField(appUserFromForm);
    }

    @Test
    void shouldReturnFalse() {

        // Пользователь извне
        AppUser appUser = new AppUser();
        appUser.setEmail("email@email.com");
        // Пользователь который из формы
        AppUser AppUserFromForm = new AppUser();
        AppUserFromForm.setEmail("email@email.com");
        AppUserFromForm.setPassword("1111");
        /*
        Эмулируем поведение метода findByEmail(String email),
        так чтобы при передаче эл. почты юзера из формы вернулся юзер извне,
        что означает, что такой пользователь уже существует в базе данных
         */
        when(appUserRepository.findByEmail(AppUserFromForm.getEmail()))
                .thenReturn(Optional.of(appUser));
        // Executing...
        boolean res = appUserService.register(AppUserFromForm);
        // Утверждаем, что результат метода register(AppUser appUser) будет false
        assertThat(res).isFalse();
        // посему passwordEncoder.encode(String rawPassword) выполнится 0 раз
        verify(passwordEncoder, times(0)).encode(anyString());
        // и пароль юзера из формы не зашифрован
        assertThat(AppUserFromForm.getPassword()).doesNotContain("$2a$");
        // А юзер из формы не сохранится в бд
        verify(appUserRepository, times(0)).save(any(AppUser.class));
    }
}