package dev.akinbobobla.dailybot;

import com.google.gson.Gson;
import dev.akinbobobla.dailybot.TeamMembersResponse.Member;
import dev.akinbobobla.dailybot.TeamMembersResponse.TeamMembersResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.stream.Collectors;

public class SlackBotTest {
    private SlackBot slackBot;
    private TeamMembers teamMembers;

    @BeforeEach
    public void setUp() {
        slackBot = new SlackBot();
        teamMembers = new TeamMembers();
    }


    @Test
    public void testBot() throws URISyntaxException, IOException, InterruptedException {
        Gson gson = new Gson();
        TeamMembersResponse teamMembersResponse;

        HttpResponse<String> response = teamMembers.getMembers();

        teamMembersResponse = gson.fromJson(response.body(), TeamMembersResponse.class);

        List<Member> realMembers = teamMembersResponse.members.subList(1, teamMembersResponse.getMembers().size());
        List<String> membersIds = realMembers.stream().filter(member -> !member.is_bot).map(member -> member.id).toList();

        slackBot.sendMessage("Heyy");

    }
}
