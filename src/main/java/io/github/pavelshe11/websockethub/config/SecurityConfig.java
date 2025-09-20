package io.github.pavelshe11.websockethub.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.config.annotation.web.socket.EnableWebSocketSecurity;
import org.springframework.security.messaging.access.intercept.MessageMatcherDelegatingAuthorizationManager;
import org.springframework.security.oauth2.server.resource.web.DefaultBearerTokenResolver;

@Configuration
@EnableWebSocketSecurity
public class SecurityConfig {

    @Bean
    public AuthorizationManager<Message<?>> messageAuthorizationManager(
            MessageMatcherDelegatingAuthorizationManager.Builder messages
    ) {
        return messages
                .simpSubscribeDestMatchers("/user/queue/errors").permitAll()
//                .simpSubscribeDestMatchers("/topic/user/*/online").authenticated()
                .simpDestMatchers("/app/**").authenticated()
                .anyMessage().denyAll()
                .build();
    }

    @Bean
    public DefaultBearerTokenResolver bearerTokenResolver() {
        DefaultBearerTokenResolver resolver = new DefaultBearerTokenResolver();
        resolver.setBearerTokenHeaderName("sec-websocket-protocol");
        return resolver;
    }
}
