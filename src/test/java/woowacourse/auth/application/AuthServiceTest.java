package woowacourse.auth.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static woowacourse.auth.Fixture.email;
import static woowacourse.auth.Fixture.nickname;
import static woowacourse.auth.Fixture.password;
import static woowacourse.auth.Fixture.tokenRequest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import woowacourse.auth.domain.Customer;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.auth.exceptions.IncorrectPasswordException;
import woowacourse.auth.support.JwtTokenProvider;

@DisplayName("인증 서비스를 테스트한다")
@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private CustomerService customerService;
    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @InjectMocks
    private AuthService authService;

    @Test
    void 회원의_비밀번호가_일치하면_토큰을_발급한다() {
        // given
        Customer customer = new Customer(email, nickname, password);
        given(customerService.findCustomer(email))
                .willReturn(customer);
        given(jwtTokenProvider.createToken(email))
                .willReturn("accessToken");

        // when
        TokenResponse response = authService.login(tokenRequest);

        // then
        assertThat(response.getAccessToken()).isEqualTo("accessToken");
    }

    @Test
    void 회원의_비밀번호가_일치하지않으면_예외를_발생시킨다() {
        // given
        Customer customer = new Customer(email, nickname, password);
        given(customerService.findCustomer(email))
                .willReturn(customer);

        // when
        assertThatThrownBy(() -> authService.login(new TokenRequest(email, "other!123")))
                .isInstanceOf(IncorrectPasswordException.class);
    }
}
