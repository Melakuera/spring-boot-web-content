package kg.melakuera.springwebcontent.service;

import kg.melakuera.springwebcontent.controller.MessageController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.xpath;

@SpringBootTest
@AutoConfigureMockMvc
@WithUserDetails("musabaeveldiar@gmail.com")
@TestPropertySource("application-test.properties")
@Sql(value = {"sql/create-users-before.sql", "sql/create-messages-before.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"sql/del-users-after.sql", "sql/delete-messages-after.sql"},
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class MessageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MessageController messageController;

    @Test
    void shouldAuthenticated() throws Exception {
        this.mockMvc.perform(get("/messages"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(xpath("//*[@id='navbarSupportedContent']/ul[2]/li[2]/a")
                        .string("musabaeveldiar@gmail.com"));
    }

    @Test
    void shouldShowMessages() throws Exception {
        this.mockMvc.perform(get("/messages"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(xpath("//div[@id='message-list']/div").nodeCount(4));
    }

    @Test
    void shouldFilterMessages() throws Exception {
        this.mockMvc.perform(get("/messages").param("filter", "test"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(xpath("//div[@id='message-list']/div").nodeCount(2))
                .andExpect(xpath("//div[@id='message-list']/div/div[@id=1]").exists())
                .andExpect(xpath("//div[@id='message-list']/div/div[@id=2]").exists());
    }

    @Test
    void shouldAddMessage() throws Exception {
        MockHttpServletRequestBuilder multipart = multipart("/messages/new")
                .file("file", "test".getBytes())
                .param("text", "hello from test")
                .param("tag", "important")
                .with(csrf());

        this.mockMvc.perform(multipart)
                .andDo(print())
                .andExpect(authenticated());
                /*.andExpect(xpath("//div[@id='message-list']/div").nodeCount(5))
                .andExpect(xpath("//div[@id='message-list']/div/div[@id=5]").exists())
                .andExpect(xpath("//div[@id='message-list']/div/div[@id=5]/div[1]/span")
                        .string("hello from test"))
                .andExpect(xpath("//div[@id='message-list']/div/div[@id=5]/div[1]/div/i")
                        .string("important"));*/
    }
}