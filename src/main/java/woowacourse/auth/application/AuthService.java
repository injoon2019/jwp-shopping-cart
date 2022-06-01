package woowacourse.auth.application;

import org.springframework.stereotype.Service;
import woowacourse.auth.domain.Customer;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.auth.exceptions.IncorrectPasswordException;
import woowacourse.auth.support.JwtTokenProvider;

@Service
public class AuthService {

    private final CustomerService customerService;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthService(CustomerService customerService, JwtTokenProvider jwtTokenProvider) {
        this.customerService = customerService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public TokenResponse login(TokenRequest request) {
        Customer customer = customerService.findCustomer(request.getEmail());
        if (!customer.isSamePassword(request.getPassword())) {
            throw new IncorrectPasswordException("비밀번호가 일치하지 않습니다.");
        }
        String token = jwtTokenProvider.createToken(request.getEmail());
        return new TokenResponse(customer.getNickname(), token);
    }
}
