package dev.akinbobobla.dailybot;

import com.slack.api.methods.SlackApiException;
import com.slack.api.model.User;
import dev.akinbobobla.dailybot.TeamMember.TeamMemberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;

@SpringBootTest
public class DailyBotTests {
    @Autowired
    private TeamMemberService teamMemberService;


    @Test
    public void populateDatabase() throws SlackApiException, IOException {
        List<User> members = teamMemberService.getSlackTeamMembers();
        teamMemberService.saveTeamMembers(members);
    }
}
