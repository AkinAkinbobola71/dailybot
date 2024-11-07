package dev.akinbobobla.dailybot.TeamMember;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamMemberService {
    private final TeamMemberRepository teamMemberRepository;

    public TeamMemberService(TeamMemberRepository teamMemberRepository) {
        this.teamMemberRepository = teamMemberRepository;
    }

    public void saveTeamMember(TeamMember member) {
        teamMemberRepository.save(member);
    }

    public List<String> getTeamMembers() {
        return teamMemberRepository.findAll().stream().map(TeamMember::getSlackId).toList();
    }

    public TeamMember getTeamMemberBySlackId(String slackId) {
        return teamMemberRepository.findBySlackId(slackId).orElseThrow(() -> new IllegalArgumentException("User not found"));
    }
}
