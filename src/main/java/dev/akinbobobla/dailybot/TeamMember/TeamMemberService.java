package dev.akinbobobla.dailybot.TeamMember;

import com.slack.api.bolt.App;
import com.slack.api.bolt.AppConfig;
import com.slack.api.methods.SlackApiException;
import com.slack.api.model.User;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class TeamMemberService {
    private final TeamMemberRepository teamMemberRepository;
    private final App app;

    public TeamMemberService(TeamMemberRepository teamMemberRepository) {
        String botToken = System.getenv("SLACK_TOKEN");
        this.teamMemberRepository = teamMemberRepository;

        if (botToken == null || botToken.isEmpty()) {
            throw new IllegalStateException("SLACK_TOKEN environment variable is not set.");
        }
        this.app = new App(AppConfig.builder().singleTeamBotToken(botToken).build());
    }

    public List<User> getSlackTeamMembers() throws SlackApiException, IOException {
        return app.client().usersList(r -> r).getMembers().stream().filter(m -> !m.isBot()).toList();
    }
}
