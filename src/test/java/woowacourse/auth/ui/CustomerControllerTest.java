package woowacourse.auth.ui;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static woowacourse.auth.Fixture.email;
import static woowacourse.auth.Fixture.nickname;
import static woowacourse.auth.Fixture.password;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import woowacourse.auth.application.AuthService;
import woowacourse.auth.application.CustomerService;
import woowacourse.auth.domain.Customer;
import woowacourse.auth.dto.SignupRequest;

@WebMvcTest(CustomerController.class)
class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CustomerService customerService;

    @MockBean
    private AuthService authService;

    @Test
    void 회원가입_요청을_정상적으로_처리한다() throws Exception {
        // given
        SignupRequest signupRequest = new SignupRequest(email, password, nickname);
        String jsonRequest = objectMapper.writeValueAsString(signupRequest);
        given(customerService.signUp(any(SignupRequest.class)))
                .willReturn(new Customer(email, nickname, password));

        // when
        mockMvc.perform(post("/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
                .andExpect(status().isCreated());
    }

    @Test
    void 이메일_널일_경우_회원가입_요청을_처리하지않는다() throws Exception {
        // given
        SignupRequest signupRequest = new SignupRequest(null, password, nickname);
        String jsonRequest = objectMapper.writeValueAsString(signupRequest);

        // when
        mockMvc.perform(post("/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
                .andExpect(status().isBadRequest());
    }

    @Test
    void 이메일_공백일_경우_회원가입_요청을_처리하지않는다() throws Exception {
        // given
        String invalidEmail = " ";
        SignupRequest signupRequest = new SignupRequest(invalidEmail, password, nickname);
        String jsonRequest = objectMapper.writeValueAsString(signupRequest);

        // when
        mockMvc.perform(post("/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
                .andExpect(status().isBadRequest());
    }

    @Test
    void 비밀번호가_널일_경우_회원가입_요청을_처리하지않는다() throws Exception {
        // given
        SignupRequest signupRequest = new SignupRequest(email, null, nickname);
        String jsonRequest = objectMapper.writeValueAsString(signupRequest);

        // when
        mockMvc.perform(post("/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
                .andExpect(status().isBadRequest());
    }

    @Test
    void 비밀번호가_공백일_경우_회원가입_요청을_처리하지않는다() throws Exception {
        // given
        SignupRequest signupRequest = new SignupRequest(email, " ", nickname);
        String jsonRequest = objectMapper.writeValueAsString(signupRequest);

        // when
        mockMvc.perform(post("/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
                .andExpect(status().isBadRequest());
    }

    @Test
    void 닉네임이_널일_경우_회원가입_요청을_처리하지않는다() throws Exception {
        // given
        SignupRequest signupRequest = new SignupRequest(email, password, null);
        String jsonRequest = objectMapper.writeValueAsString(signupRequest);

        // when
        mockMvc.perform(post("/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
                .andExpect(status().isBadRequest());
    }

    @Test
    void 닉네임이_공백일_경우_회원가입_요청을_처리하지않는다() throws Exception {
        // given
        SignupRequest signupRequest = new SignupRequest(email, password, " ");
        String jsonRequest = objectMapper.writeValueAsString(signupRequest);

        // when
        mockMvc.perform(post("/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
                .andExpect(status().isBadRequest());
    }
}
