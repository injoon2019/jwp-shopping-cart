package woowacourse.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import woowacourse.auth.domain.Customer;

@Getter
@AllArgsConstructor
@Builder
public class SignupRequest {
    private String email;
    private String password;
    private String nickname;

    public Customer toEntity() {
        return Customer.builder()
                .email(email)
                .password(password)
                .nickname(nickname)
                .build();
    }
}
