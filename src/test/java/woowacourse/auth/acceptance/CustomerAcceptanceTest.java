package woowacourse.auth.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static woowacourse.auth.Fixture.email;
import static woowacourse.auth.Fixture.nickname;
import static woowacourse.auth.Fixture.password;
import static woowacourse.auth.Fixture.signupRequest;
import static woowacourse.auth.Fixture.tokenRequest;
import static woowacourse.utils.RestAssuredUtil.deleteWithToken;
import static woowacourse.utils.RestAssuredUtil.httpPost;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.auth.dto.SignupRequest;
import woowacourse.shoppingcart.acceptance.AcceptanceTest;

@DisplayName("회원 관련 기능 인수테스트")
@Sql("classpath:test.sql")
public class CustomerAcceptanceTest extends AcceptanceTest {

    @Test
    void 회원가입을_정상적으로_실행한다() {
        ExtractableResponse<Response> response = httpPost("/customers", signupRequest);

        assertAll(
                () -> assertThat(response.jsonPath().getString("email")).isEqualTo(email),
                () -> assertThat(response.jsonPath().getString("nickname")).isEqualTo(nickname)
        );
    }

    @Test
    void 중복된_이메일인_경우_회원가입에_실패한다() {
        httpPost("/customers", signupRequest);
        ExtractableResponse<Response> response = httpPost("/customers", signupRequest);
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void 회원가입시_이메일_형식이_올바르지_않을_경우_실패한다() {
        String invalidEmail = "invalidgamil.com";

        ExtractableResponse<Response> response = httpPost("/customers",
                new SignupRequest(invalidEmail, nickname, password));

        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void 회원가입시_비밀번호_형식이_올바르지_않을_경우_실패한다() {
        String invalidPassword = "1234";

        ExtractableResponse<Response> response = httpPost("/customers",
                new SignupRequest(email, invalidPassword, nickname));

        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void 회원가입시_닉네임_형식이_올바르지_않을_경우_실패한다() {
        String invalidNickname = "1";

        ExtractableResponse<Response> response = httpPost("/customers",
                new SignupRequest(email, password, invalidNickname));

        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void 토큰을_가지고_있는_경우_회원탈퇴를_할_수_있다() {
        // given
        httpPost("/customers", signupRequest);
        ExtractableResponse<Response> loginResponse = httpPost("/auth/login", tokenRequest);
        String token = loginResponse.jsonPath().getString("accessToken");

        // when
        ExtractableResponse<Response> signoutResponse = deleteWithToken("/customers", token);

        // then
        assertThat(signoutResponse.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @Test
    void 토큰이_유효하지_않은_경우_회원탈퇴를_할_수_없다() {
        // given
        httpPost("/customers", signupRequest);

        // when
        ExtractableResponse<Response> signoutResponse = deleteWithToken("/customers", "invalidToken");

        // then
        assertThat(signoutResponse.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }
}
