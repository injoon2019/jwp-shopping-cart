package woowacourse.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import woowacourse.auth.domain.Customer;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SignupResponse {
    private String email;
    private String nickname;

    public SignupResponse(Customer customer) {
        this.email = customer.getEmail();
        this.nickname = customer.getNickname();
    }
}
