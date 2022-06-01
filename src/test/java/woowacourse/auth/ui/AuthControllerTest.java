package woowacourse.auth.ui;


import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static woowacourse.auth.Fixture.email;
import static woowacourse.auth.Fixture.nickname;
import static woowacourse.auth.Fixture.password;
import static woowacourse.auth.Fixture.tokenRequest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import woowacourse.auth.application.AuthService;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.dto.TokenResponse;

@WebMvcTest(AuthController.class)
@DisplayName("인증에 대한 기능")
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AuthService authService;

    @Test
    void 아이디와_비밀번호가_일치하면_토큰을_발급한다() throws Exception {
        // given
        String jsonRequest = objectMapper.writeValueAsString(tokenRequest);
        TokenResponse tokenResponse = new TokenResponse(nickname, "accessToken");
        given(authService.login(any(TokenRequest.class)))
                .willReturn(tokenResponse);

        // then
        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(tokenResponse)));
    }

    @Test
    void 이메일이_공백이면_400을_반환한다() throws Exception {
        // given
        String jsonRequest = objectMapper.writeValueAsString(new TokenRequest(" ", password));

        // then
        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
                .andExpect(status().isBadRequest());
    }
    @Test
    void 비밀번호가_공백이면_400을_반환한다() throws Exception {
        // given
        String jsonRequest = objectMapper.writeValueAsString(new TokenRequest(email, ""));

        // then
        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
                .andExpect(status().isBadRequest());
    }

}
