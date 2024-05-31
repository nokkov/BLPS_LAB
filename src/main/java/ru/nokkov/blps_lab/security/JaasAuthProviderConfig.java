package ru.nokkov.blps_lab.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.jaas.AbstractJaasAuthenticationProvider;
import org.springframework.security.authentication.jaas.AuthorityGranter;
import org.springframework.security.authentication.jaas.DefaultJaasAuthenticationProvider;
import ru.nokkov.blps_lab.user.UserRepository;

@Configuration
public class JaasAuthProviderConfig {

    @Bean
    public AbstractJaasAuthenticationProvider jaasAuthenticationProvider(
            javax.security.auth.login.Configuration configuration,
            UserRepository userRepository) {

        return new DefaultJaasAuthenticationProvider() {{
            setConfiguration(configuration);
            setAuthorityGranters(new AuthorityGranter[] {
                    new UserRepositoryAuthorityGranter(userRepository)
            });
        }};
    }
}
