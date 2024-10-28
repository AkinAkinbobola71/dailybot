package dev.akinbobobla.dailybot;

import com.slack.api.methods.SlackApiException;
import dev.akinbobobla.dailybot.TeamMember.TeamMemberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
class DailybotApplicationTests {


    @Test
    public void testService()  {
        System.out.println("yes");
//        System.out.println(teamMemberService.getSlackTeamMembers());
    }
}
