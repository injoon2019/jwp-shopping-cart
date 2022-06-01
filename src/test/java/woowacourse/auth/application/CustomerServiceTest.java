package woowacourse.auth.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.BDDMockito.given;
import static woowacourse.auth.Fixture.email;
import static woowacourse.auth.Fixture.nickname;
import static woowacourse.auth.Fixture.password;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import woowacourse.auth.dao.CustomerDao;
import woowacourse.auth.domain.Customer;
import woowacourse.auth.dto.SignupRequest;

@DisplayName("회원 서비스를 테스트한다")
@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerDao customerDao;

    @InjectMocks
    private CustomerService customerService;

    @Test
    void 회원을_가입을_진행한다() {
        // given
        Customer customer = new Customer(email, nickname, password);
        Customer saved = new Customer(1L, email, nickname, password);

        SignupRequest signupRequest = new SignupRequest(email, password, nickname);
        given(customerDao.save(customer)).willReturn(saved);

        // when
        Customer saveCustomer = customerService.signUp(signupRequest);

        // then
        assertAll(
                () -> assertThat(saveCustomer.getId()).isNotNull(),
                () -> assertThat(saveCustomer.getEmail()).isEqualTo(email),
                () -> assertThat(saveCustomer.getNickname()).isEqualTo(nickname)
        );
    }
}
