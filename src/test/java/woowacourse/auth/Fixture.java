package woowacourse.auth;

import woowacourse.auth.dto.SignupRequest;
import woowacourse.auth.dto.TokenRequest;

public class Fixture {
    public static final String email = "test@gmail.com";
    public static final String password = "a1234!";
    public static final String nickname = "testName";

    public static final SignupRequest signupRequest = new SignupRequest(email, password, nickname);
    public static final TokenRequest tokenRequest = new TokenRequest(email, password);
}
