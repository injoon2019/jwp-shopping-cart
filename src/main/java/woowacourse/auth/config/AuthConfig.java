package woowacourse.auth.config;

import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import woowacourse.auth.application.CustomerService;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.auth.ui.LoginArgumentResolver;
import woowacourse.auth.ui.LoginInterceptor;

@Configuration
public class AuthConfig implements WebMvcConfigurer {
    private final CustomerService customerService;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthConfig(CustomerService customerService, JwtTokenProvider jwtTokenProvider) {
        this.customerService = customerService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public void addArgumentResolvers(List argumentResolvers) {
        argumentResolvers.add(createAuthenticationPrincipalArgumentResolver());
    }

    @Bean
    public LoginArgumentResolver createAuthenticationPrincipalArgumentResolver() {
        return new LoginArgumentResolver(customerService, jwtTokenProvider);
    }

    @Bean
    public HandlerInterceptor loginInterceptor() {
        return new LoginInterceptor(jwtTokenProvider);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor())
                .excludePathPatterns("/auth/login");
    }
}
