package woowacourse.auth.application;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import woowacourse.auth.domain.Customer;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.auth.exception.InvalidCustomerException;
import woowacourse.auth.exception.InvalidLoginException;
import woowacourse.auth.service.CustomerService;
import woowacourse.auth.support.JwtTokenProvider;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

	@Mock
	private CustomerService customerService;
	@Mock
	private JwtTokenProvider tokenProvider;
	@InjectMocks
	private AuthService authService;

	@DisplayName("알맞은 정보로 로그인을 한다.")
	@Test
	void login() {
		// given
		given(customerService.findByEmail("123@gmail.com"))
			.willReturn(new Customer(1L, "123@gmail.com", "a1234!", "does"));
		given(tokenProvider.createToken("does"))
			.willReturn("access-token");

		// when
		TokenResponse response = authService.login(new TokenRequest("123@gmail.com", "a1234!"));

		// then
		assertAll(
			() -> assertThat(response.getNickname()).isEqualTo("does"),
			() -> assertThat(response.getAccessToken()).isEqualTo("access-token")
		);
	}

	@DisplayName("틀린 이메일 정보로 로그인을 하면 예외 발생")
	@Test
	void loginFailByEmail() {
		// given
		given(customerService.findByEmail("123@gmail.com"))
			.willThrow(InvalidCustomerException.class);

		// when
		assertThatThrownBy(() -> authService.login(new TokenRequest("123@gmail.com", "a1234!")))
			.isInstanceOf(InvalidCustomerException.class);
	}

	@DisplayName("비밀번호가 틀리면 예외가 발생한다.")
	@Test
	void loginFailByPassword() {
		// given
		given(customerService.findByEmail("123@gmail.com"))
			.willReturn(new Customer(1L, "123@gmail.com", "a1234!", "does"));

		// when
		assertThatThrownBy(() -> authService.login(new TokenRequest("123@gmail.com", "a1234!!!")))
			.isInstanceOf(IllegalArgumentException.class);
	}
}