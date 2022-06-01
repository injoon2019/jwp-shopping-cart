package woowacourse.auth.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class EmailTest {

    @Test
    void 이메일_형식이_유효한_경우_이메일을_생성한다() {
        // given
        String validEmail = "valid@gmail.com";
        // when
        Email email = Email.of(validEmail);
        // then
        assertThat(email.getValue()).isEqualTo(validEmail);
    }

    @ParameterizedTest
    @ValueSource(strings = {"123gmail.com", "123@gmailcom", "123gamilcom"})
    void 이메일_형식이_유효하지_않은_경우_예외를_반환한다(String email) {
        assertThatThrownBy(() -> Email.of(email))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
