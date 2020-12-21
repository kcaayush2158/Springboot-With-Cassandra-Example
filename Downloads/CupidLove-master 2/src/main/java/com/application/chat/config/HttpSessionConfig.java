package com.application.chat.config;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HttpSessionConfig {

    private static final Map<String, HttpSessionConfig> sessions = new HashMap<>();

    public List<HttpSessionConfig> getActiveSessions() {
        return new ArrayList<>(sessions.values());
    }

    @Bean
    public HttpSessionListener httpSessionListener() {
        return new HttpSessionListener() {
            @Override
            public void sessionCreated(HttpSessionEvent hse) {
                sessions.put(hse.getSession().getId(), (HttpSessionConfig) hse.getSession());
            }

            @Override
            public void sessionDestroyed(HttpSessionEvent hse) {
                sessions.remove(hse.getSession().getId());
            }
        };
    }
}
