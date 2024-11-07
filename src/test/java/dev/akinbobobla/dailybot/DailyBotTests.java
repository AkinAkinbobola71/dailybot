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
    @Autowired
    private ExcelToDatabase excelToDatabase;

    @Test
    public void getTeamMembers() throws IOException, SlackApiException {
        List<User> teamMembers = teamMemberService.getSlackTeamMembers();
        excelToDatabase.saveToDatabase(teamMembers);
    }

    @Test
    public void findTeamMemberBySlackId() {
        System.out.println(teamMemberService.getTeamMemberBySlackId("U07T4EUHNVC"));
    }
}