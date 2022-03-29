package kg.melakuera.springwebcontent.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class LoginTest {

    @Autowired
    private MockMvc mockMvc;

    // По запросу /login в теле ответа должен содержатся след. строки
    @Test
    void shouldContainsStringInPage() throws Exception {
        this.mockMvc.perform(get("/login"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Авторизация")))
                .andExpect(content().string(containsString("Запомнить меня на этом компьютере")))
                .andExpect(content().string(containsString("Войти")));
    }

    // При запросе на /messages должен произойти редирект на страничку логина
    @Test
    void shouldRedirectToLoginPage() throws Exception {
        this.mockMvc.perform(get("/messages"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }

    // Авторизация по след данным должен пройти ( С использованием сторонней библиотеки ) и редиректнуть
    @Test
    void shouldLoginUser() throws Exception {
        this.mockMvc.perform(formLogin().user("musabaeveldiar@gmail.com").password("1"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }

    // Авторизация по след данным должен пройти ( Стандартный ) и редиректнуть
    @Test
    void shouldLoginUser2() throws Exception {
        this.mockMvc.perform(post("/login")
                        .param("username", "musabaeveldiar@gmail.com").param("password", "1"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }

    // Авторизация по след данным должен не пройти и редиректнуть на страничку логина
    @Test
    void shouldNotPassLogin() throws Exception {
        this.mockMvc.perform(formLogin().user("musabaeveldiar@gmail.com").password("1111"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login?error"));
    }
}
