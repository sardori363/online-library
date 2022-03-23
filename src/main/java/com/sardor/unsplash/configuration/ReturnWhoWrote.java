package com.sardor.unsplash.configuration;

import com.sardor.unsplash.entity.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing
public class ReturnWhoWrote {
    @Bean
    AuditorAware<User> auditorAware() {
        return new WhoWrote();
    } // Who wrote
}