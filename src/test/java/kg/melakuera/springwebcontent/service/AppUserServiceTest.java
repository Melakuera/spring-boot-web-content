package kg.melakuera.springwebcontent.service;

import kg.melakuera.springwebcontent.entity.AppUser;
import kg.melakuera.springwebcontent.entity.Role;
import kg.melakuera.springwebcontent.repository.AppUserRepository;
import org.assertj.core.api.Assertions;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

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
    void registerShouldBeSuccessfully() {
        // Mockito
        AppUser expectedAppUser = new AppUser(
                1L, "Tom", "Bob", "email@email.com", "1", null, null);

        Mockito.when(appUserRepository.findById(1L)).thenReturn(Optional.of(expectedAppUser));
        AppUser actualAppUser = appUserService.findById(1L);

        Assertions.assertThat(appUserService.findById(1L)).isEqualTo(actualAppUser);
    }
    @Test
    void isAdminShouldBeTrue() {
        // Hamcrest
        AppUser appUser = new AppUser();
        appUser.setRole(Role.ROLE_ADMIN);

        boolean res = appUserService.isAdmin(appUser);

        MatcherAssert.assertThat(res, Matchers.equalTo(true));
    }

    @Test
    void isAdminShouldBeFalse() {
        // Hamcrest
        AppUser appUser = new AppUser();
        appUser.setRole(Role.ROLE_USER);

        boolean res = appUserService.isAdmin(appUser);

        MatcherAssert.assertThat(res, Matchers.equalTo(false));
    }

    @Test
    void shouldThrowExpLoadUserByUsername() {
        // AssertJ
        Assertions.assertThatThrownBy(() ->
                        appUserService.loadUserByUsername("test@test.com"))
                .isInstanceOf(UsernameNotFoundException.class)
                .hasMessage("Пользователь с данной test@test.com эл. почтой не найден");
    }
}