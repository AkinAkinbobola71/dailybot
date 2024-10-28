package dev.akinbobobla.dailybot.TeamMember;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TeamMemberRepository extends JpaRepository<TeamMember, Integer> {
    Optional<TeamMember> findBySlackId(String slackId);
}
