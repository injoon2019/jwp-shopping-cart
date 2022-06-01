package woowacourse.auth.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class PasswordTest {

    @Test
    void 비밀번호_형식이_유효한_경우_이메일을_생성한다() {
        // given
        String validPassword = "a1234!";
        // when
        Password password = Password.of(validPassword);
        // then
        assertThat(password.getValue()).isEqualTo(validPassword);
    }

    @ParameterizedTest
    @ValueSource(strings = {"1234", "abasdas", "!@#!@#", "123d213", "asdasd!@#@", "123!@@##!1"})
    void 비밀번호_형식이_유효하지_않은_경우_예외를_반환한다(String password) {
        assertThatThrownBy(() -> Password.of(password))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
