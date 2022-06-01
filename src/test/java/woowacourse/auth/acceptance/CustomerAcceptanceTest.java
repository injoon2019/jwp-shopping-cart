package woowacourse.auth.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static woowacourse.auth.Fixture.email;
import static woowacourse.auth.Fixture.nickname;
import static woowacourse.auth.Fixture.password;
import static woowacourse.utils.RestAssuredUtil.httpPost;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.auth.dto.SignupRequest;
import woowacourse.shoppingcart.acceptance.AcceptanceTest;

@DisplayName("회원 관련 기능")
@Sql("classpath:test.sql")
public class CustomerAcceptanceTest extends AcceptanceTest {

    @Test
    void 회원가입을_정상적으로_실행한다() {
        SignupRequest signupRequest = SignupRequest.builder()
                .email(email)
                .nickname(nickname)
                .password(password)
                .build();

        ExtractableResponse<Response> response = httpPost("/customers", signupRequest);

        assertAll(
                () -> assertThat(response.jsonPath().getString("email")).isEqualTo(email),
                () -> assertThat(response.jsonPath().getString("nickname")).isEqualTo(nickname)
        );
    }

    @Test
    void 회원가입시_이메일_형식이_올바르지_않을_경우_실패한다() {
        String invalidEmail = "invalidgamil.com";

        SignupRequest signupRequest = SignupRequest.builder()
                .email(invalidEmail)
                .nickname(nickname)
                .password(password)
                .build();

        ExtractableResponse<Response> response = httpPost("/customers", signupRequest);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void 회원가입시_비밀번호_형식이_올바르지_않을_경우_실패한다() {
        String invalidPassword = "1234";

        SignupRequest signupRequest = SignupRequest.builder()
                .email(email)
                .nickname(invalidPassword)
                .password(password)
                .build();

        ExtractableResponse<Response> response = httpPost("/customers", signupRequest);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void 회원가입시_닉네임_형식이_올바르지_않을_경우_실패한다() {
        String invalidNickname = "1";

        SignupRequest signupRequest = SignupRequest.builder()
                .email(email)
                .nickname(password)
                .password(invalidNickname)
                .build();

        ExtractableResponse<Response> response = httpPost("/customers", signupRequest);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }
}
