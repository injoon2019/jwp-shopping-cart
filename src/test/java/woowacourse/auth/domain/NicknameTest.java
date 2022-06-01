package woowacourse.auth.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class NicknameTest {

    @Test
    void 닉네임_형식이_유효한_경우_이메일을_생성한다() {
        // given
        String validNickname = "thor";
        // when
        Nickname nickname = Nickname.of(validNickname);
        // then
        assertThat(nickname.getValue()).isEqualTo(validNickname);
    }

    @ParameterizedTest
    @ValueSource(strings = {"1", "12345678901"})
    void 닉네임_형식이_유효하지_않은_경우_예외를_반환한다(String nickname) {
        assertThatThrownBy(() -> Nickname.of(nickname))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
