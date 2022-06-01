package woowacourse.auth.dto;

import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import woowacourse.auth.domain.Customer;

@Getter
@AllArgsConstructor
@Builder
public class SignupRequest {

    @NotBlank
    private String email;
    @NotBlank
    private String password;
    @NotBlank
    private String nickname;

    public Customer toEntity() {
        return new Customer(email, nickname, password);
    }
}
