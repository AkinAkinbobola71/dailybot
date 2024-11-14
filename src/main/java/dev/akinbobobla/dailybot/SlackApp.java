package dev.akinbobobla.dailybot;

import com.slack.api.bolt.App;
import com.slack.api.bolt.AppConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SlackApp {
    @Bean
    public App initSlackApp() {
        String botToken = System.getenv("SLACK_TOKEN");

        if (botToken == null || botToken.isEmpty()) {
            throw new IllegalArgumentException("SLACK_TOKEN environment variable is required");
        }

        return new App(AppConfig.builder().singleTeamBotToken(botToken).build());
    }
}
