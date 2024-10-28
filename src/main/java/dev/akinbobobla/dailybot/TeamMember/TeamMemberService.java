package dev.akinbobobla.dailybot.TeamMember;

import com.slack.api.bolt.App;
import com.slack.api.bolt.AppConfig;
import com.slack.api.methods.SlackApiException;
import com.slack.api.model.User;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

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
        List<User> members = app.client().usersList(r -> r).getMembers().stream().filter(m -> !m.isBot() && !m.isDeleted()).toList();
        return members.subList(1, members.size());
    }

    public void saveTeamMembers(List<User> members) {
        members.forEach(member -> {
            TeamMember teamMember = new TeamMember();

            teamMember.setFullName(member.getProfile().getRealName());
            teamMember.setEmail(member.getProfile().getEmail());
            teamMember.setSlackId(member.getId());
            teamMember.setTeam(getRandomTeams());
            teamMemberRepository.save(teamMember);
        });
    }

    private List<String> getRandomTeams() {
        List<String> teams = new java.util.ArrayList<>(List.of(
                "Engineering",
                "Sales",
                "People Experience"
        ));

        Collections.shuffle(teams);

        int numOfTeams = ThreadLocalRandom.current().nextInt(1, teams.size() + 1);
        return teams.subList(0, numOfTeams);
    }
}
