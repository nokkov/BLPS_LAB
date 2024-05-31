package ru.nokkov.blps_lab.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.jaas.memory.InMemoryConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.security.auth.login.AppConfigurationEntry;

import java.util.Map;

@Configuration
public class JaasConfig {

    @Bean
    public javax.security.auth.login.Configuration configuration(
            UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {

        Map<String, Object> options = Map.of(
                "userDetailsService", userDetailsService,
                "passwordEncoder", passwordEncoder
        );

        AppConfigurationEntry entry = new AppConfigurationEntry(
                UserDetailsLoginModule.class.getCanonicalName(),
                AppConfigurationEntry.LoginModuleControlFlag.REQUIRED,
                options
        );

        AppConfigurationEntry[] configurationEntries = { entry };

        return new InMemoryConfiguration(Map.of("SPRINGSECURITY", configurationEntries));
    }

}
