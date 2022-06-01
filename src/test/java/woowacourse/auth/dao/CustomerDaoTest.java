package woowacourse.auth.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static woowacourse.auth.Fixture.email;
import static woowacourse.auth.Fixture.nickname;
import static woowacourse.auth.Fixture.password;

import java.util.Optional;
import javax.sql.DataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import woowacourse.auth.domain.Customer;

@JdbcTest
@DisplayName("CustomerDao 테스트")
public class CustomerDaoTest {

    @Autowired
    private DataSource dataSource;
    private CustomerDao customerDao;

    @BeforeEach
    public void setUp() {
        customerDao = new CustomerDao(dataSource);
    }

    @Test
    void 회원을_저장한다() {
        // given
        Customer customer = new Customer(email, nickname, password);

        // when
        Customer saved = customerDao.save(customer);

        // then
        assertAll(
                () -> assertThat(saved.getId()).isNotNull(),
                () -> assertThat(saved.getEmail()).isEqualTo(email),
                () -> assertThat(saved.getPassword()).isEqualTo(password),
                () -> assertThat(saved.getNickname()).isEqualTo(nickname)
        );
    }

    @Test
    void 회원을_이메일로_조회한다() {
        // given
        Customer customer = new Customer(email, nickname, password);
        Customer saved = customerDao.save(customer);

        // when
        Optional<Customer> found = customerDao.findByEmail(saved.getEmail());

        // then
        assertThat(found.get().getEmail()).isEqualTo(email);
    }
}
