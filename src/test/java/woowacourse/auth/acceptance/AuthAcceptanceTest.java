package woowacourse.auth.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static woowacourse.auth.Fixture.email;
import static woowacourse.auth.Fixture.password;
import static woowacourse.auth.Fixture.signupRequest;
import static woowacourse.auth.Fixture.tokenRequest;
import static woowacourse.utils.RestAssuredUtil.httpPost;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.shoppingcart.acceptance.AcceptanceTest;

@DisplayName("인증 관련 인수테스트")
@Sql("classpath:test.sql")
public class AuthAcceptanceTest extends AcceptanceTest {
    @Test
    void 아이디와_비밀번호가_일치하면_토큰을_발급한다() {
        // given
        httpPost("/customers", signupRequest);

        // when
        ExtractableResponse<Response> response = httpPost("/auth/login", tokenRequest);

        // then
        String accessToken = response.jsonPath().getString("accessToken");
        System.out.println(accessToken);
        assertThat(response.jsonPath().getString("accessToken")).isNotNull();
    }

    @Test
    void 아이디가_일치하지_않으면_토큰을_발급하지_않는다() {
        // given
        httpPost("/customers", signupRequest);

        // when
        TokenRequest invalidTokenRequest = new TokenRequest("incorretEmail@gmail.com", password);
        ExtractableResponse<Response> response = httpPost("/auth/login", invalidTokenRequest);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void 비밀번호가_일치하지_않으면_토큰을_발급하지_않는다() {
        // given
        httpPost("/customers", signupRequest);

        // when
        TokenRequest invalidTokenRequest = new TokenRequest(email, "incorrectPassword!1");
        ExtractableResponse<Response> response = httpPost("/auth/login", invalidTokenRequest);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

}
